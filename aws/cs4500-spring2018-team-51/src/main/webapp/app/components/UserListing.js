var React = require("react");
var ReactDOM = require("react-dom");
var axios = require("axios");

/**
 * Represents a single user listed in search results or featured in notifications.
 */
class UserListing extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      recommended: false,
      rated: false
    };
  }

  /**
   * Sends a friend request from the logged in user to the user represented by this listing.
   */
  addFriend() {
    var baseURL =
      "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/addfriend/";
    var url = baseURL + this.props.email + "/" + this.props.username + "/";
    var _this = this;
    this.serverRequest = axios.post(url).then(function(response) {
      _this.props.onChange("newRequest");
    });
  }

  /**
   * Accepts a friend request from the user represented by this listing to the logged in user.
   */
  acceptRequest() {
    var baseURL =
      "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/confirmfriend/";
    var url = baseURL + this.props.email + "/" + this.props.username + "/";
    var _this = this;
    this.serverRequest = axios.post(url).then(function(response) {
      _this.props.onChange("newFriend");
    });
  }

  /**
   * Checks if the logged in user has already recommended a movie to the user represented by this listing.
   */
  checkRecommendation() {
    var baseURL =
      "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/movie/findrecommendation/";
    var url =
      baseURL +
      this.props.email +
      "/" +
      this.props.username +
      "/" +
      this.props.movieID +
      "/";
    var _this = this;
    this.serverRequest = axios.get(url).then(function(response) {
      if (response.data) {
        _this.setState({
          recommended: true
        });
      }
    });
  }

  /**
   * Checks if the logged in user has already rated a movie.
   */
  checkRating() {
    var _this = this;
    this.serverRequest = axios
      .get(
        "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/rate/" +
          this.props.email +
          "/" +
          this.props.movieID
      )
      .then(function(result) {
        if (result.data) {
          _this.setState({
            rated: true
          });
        }
      });
  }

  /**
   * Recommends a movie to the user represented by this listing.
   */
  recommend() {
    var baseURL =
      "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/movie/recommend/";
    var url =
      baseURL +
      this.props.email +
      "/" +
      this.props.username +
      "/" +
      this.props.movieID +
      "/";
    var _this = this;
    this.serverRequest = axios.post(url).then(function(response) {
      _this.checkRecommendation();
    });
  }

  /**
   * Method invoked before this component is mounted.
   */
  componentWillMount() {
    if (this.props.recommend) {
      this.checkRecommendation();
      this.checkRating();
    }
  }

  /**
   * Renders this listing of a user.
   */
  render() {
    var myProfile = false;
    if (this.props.email == this.props.username) {
      myProfile = true;
    }
    return (
      <div className="result">
        <div className="row">
          <div className="col-1">
            {!this.props.recommend && (
              <img
                className="img-fluid"
                src={
                  "https://www.shareicon.net/download/2015/10/04/111640_personal_512x512.png"
                }
              />
            )}
          </div>
          <div className="col-11">
            <b>{this.props.name}</b>
            <div className="custom-btn-group">
              {this.props.received &&
                !this.props.recommend &&
                !myProfile && (
                  <button
                    className="btn btn-secondary"
                    onClick={this.acceptRequest.bind(this)}
                  >
                    Accept Friend Request
                  </button>
                )}
              {this.props.sent &&
                !this.props.recommend &&
                !myProfile && (
                  <button className="btn btn-secondary" disabled>
                    Friend Request Sent
                  </button>
                )}
              {this.props.friends &&
                !this.props.recommend &&
                !myProfile && (
                  <button className="btn btn-secondary" disabled>
                    Friends
                  </button>
                )}
              {!this.props.received &&
                !this.props.sent &&
                !this.props.friends &&
                !myProfile &&
                !this.props.recommend && (
                  <button
                    className="btn btn-secondary"
                    onClick={this.addFriend.bind(this)}
                  >
                    + Add Friend
                  </button>
                )}
              {this.props.recommend &&
                !this.state.recommended && (
                  <button
                    className="btn btn-secondary"
                    onClick={this.recommend.bind(this)}
                  >
                    Send Recommendation
                  </button>
                )}
              {this.state.recommended && (
                <button className="btn btn-secondary" disabled>
                  Already Recommended
                </button>
              )}
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default UserListing;
