var React = require("react");
var ReactDOM = require("react-dom");
var axios = require("axios");
import UserListing from "./UserListing";
import MovieListing from "./MovieListing";

/**
 * The profile page for the logged in user.
 */
class ProfilePage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      recommendations: [],
      users: []
    };

    var _this = this;
    this.serverRequest = axios
      .get(
        "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/movie/getAllRecommendations/" +
          this.props.email +
          "/"
      )
      .then(function(result) {
        _this.setState({
          recommendations: result.data
        });
      });

    var potentialFriends = [];
    for (var i = 0; i < this.props.receivedRequests.length; i++) {
      var pfEmail = this.props.receivedRequests[i].user1Email;
      this.serverRequest = axios
        .get(
          "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/findEmail/" +
            pfEmail +
            "/"
        )
        .then(function(result) {
          potentialFriends.push(result.data);
          _this.setState({
            users: potentialFriends
          });
        });
    }
  }

  /**
   * Renders the profile page.
   */
  render() {
    var noRequests = true;
    var noRecommendations = true;
    var requests = [];
    var recommendations = [];
    var recs = [];
    for (var i = 0; i < this.state.users.length; i++) {
      noRequests = false;
      var id = this.state.users[this.state.users.length - i - 1].id;
      var name = this.state.users[this.state.users.length - i - 1].fullName;
      var username = this.state.users[this.state.users.length - i - 1].email;
      requests.push(
        <UserListing
          key={i}
          userid={id}
          name={name}
          username={username}
          email={this.props.email}
          friends={false}
          received={true}
          sent={false}
          onChange={this.props.onChange}
          recommend={false}
          movieID={0}
        />
      );
    }
    for (var i = 0; i < this.state.recommendations.length; i++) {
      noRecommendations = false;
      var rec = this.state.recommendations[
        this.state.recommendations.length - i - 1
      ].n.sender;
      var mov = this.state.recommendations[
        this.state.recommendations.length - i - 1
      ].m;
      var key = this.state.recommendations.length - i - 1;

      recommendations.push(<p>{rec + " recommended:"}</p>);
      recommendations.push(
        <MovieListing
          key={key}
          movieID={mov.movieID}
          plot={mov.plot}
          title={mov.title}
          year={mov.year}
          posterUrl={mov.poster}
          rating={mov.rating}
          email={this.props.email}
        />
      );
      console.log(rec);
    }
    return (
      <div className="container-fluid">
        <div className="row">
          <div className="col-2">
            <img
              className="img-fluid"
              src={
                "https://www.shareicon.net/download/2015/10/04/111640_personal_512x512.png"
              }
            />
          </div>
          <div className="col-10">
            <br />
            <br />
            <h4>
              {this.props.user.firstName} {this.props.user.lastName}
            </h4>
            {/* <div className="custom-btn-group">
                        <button className="btn btn-secondary">Edit Profile</button>
                    </div> */}
          </div>
        </div>

        <div>
          <h5>Notifications</h5>
          <h6>Friend Requests</h6>
          {noRequests && (
            <p>
              <i>No friend requests.</i>
            </p>
          )}
          {!noRequests && <section>{requests}</section>}
          <h6>Recommendations</h6>
          {noRecommendations && (
            <p>
              <i>No recommendations.</i>
            </p>
          )}
          {!noRecommendations && <section>{recommendations}</section>}
        </div>
      </div>
    );
  }
}

export default ProfilePage;
