var React = require("react");
var ReactDOM = require("react-dom");
var axios = require("axios");
import ReactStars from "react-stars";
import AllRatings from "./AllRatings";
import UserResults from "./UserResults";

/**
 * Represents a single movie listed in search results or featured on the home page.
 */
class MovieListing extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      rating: 0,
      review: "",
      friends: []
    };

    var _this = this;
    this.serverRequest = axios
      .get(
        "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/findFriends/" +
          this.props.email +
          "/"
      )
      .then(function(result) {
        _this.setState({
          friends: result.data
        });
      });
  }

  /**
   * Changes the logged in user's rating of the current movie to the given Number.
   * @param {Number} event - the user's rating of this movie on a scale from 0 to 10.
   */
  changeRating(event) {
    this.setState({
      rating: event
    });
  }

  /**
   * Changes the logged in user's review of the current movie to the inputted text.
   * @param {String} event - the user's written review of this movie.
   */
  changeReview(event) {
    this.setState({
      review: event.target.value
    });
  }

  /**
   * Checks if the logged in user has already rated this movie.
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
          _this.getRating();
        }
      });
  }

  /**
   * Gets the logged in user's rating and review of this movie if they have already rated it.
   */
  getRating() {
    var _this = this;
    this.serverRequest = axios
      .get(
        "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/getrating/" +
          this.props.email +
          "/" +
          this.props.movieID
      )
      .then(function(result) {
        _this.setState({
          rating: result.data.rating,
          review: result.data.review
        });
      });
  }

  /**
   * Sends the logged in user's rating and review of this movie.
   */
  rate() {
    console.log(this.state.review);
    var url =
      "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/rate/";
    var rating = {
      name: this.props.email,
      movieID: this.props.movieID,
      rating: this.state.rating,
      review: this.state.review
    };
    var _this = this;
    this.serverRequest = axios.post(url, rating).then(function(response) {
      _this.ratings.getReviews();
    });
    this.checkRating();
  }

  /**
   * Gets the friends of the logged in user so that the user can recommend this movie to any of them.
   */
  getFriends() {
    var _this = this;
    this.serverRequest = axios
      .get(
        "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/findFriends/" +
          this.props.email +
          "/"
      )
      .then(function(result) {
        _this.setState({
          friends: result.data
        });
      });
  }

  /**
   * Renders this movie listing.
   */
  render() {
    var numUsers = this.state.friends.length;
    var users = this.state.friends;
    var expandName = "#" + this.props.movieID + "expand";
    var expandID = this.props.movieID + "expand";
    return (
      <div className="result">
        <div
          className="modal fade"
          id={this.props.movieID + "RateModal"}
          tabindex="-1"
          role="dialog"
          aria-labelledby={this.props.movieID + "RateModal"}
          aria-hidden="true"
        >
          <div className="modal-dialog" role="document">
            <div className="modal-content">
              <div className="modal-header">
                <h3 className="modal-title" id="exampleModalLabel">
                  Rate{" "}
                  <i>
                    {this.props.title} {"("}
                    {this.props.year}
                    {")"}
                  </i>
                </h3>
                <button
                  type="button"
                  className="close"
                  data-dismiss="modal"
                  aria-label="Close"
                >
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div className="modal-body">
                <div className="rating">
                  <ReactStars
                    count={10}
                    value={this.state.rating}
                    onChange={this.changeRating.bind(this)}
                    size={30}
                    color2={"#ffd700"}
                    half={true}
                  />
                  <div className="form-group">
                    <textarea
                      value={this.state.review}
                      onChange={this.changeReview.bind(this)}
                      className="form-control"
                      placeholder="Write your review here"
                    />
                  </div>
                </div>
                <button
                  onClick={this.rate.bind(this)}
                  type="submit"
                  className="btn btn-secondary btn-block"
                  data-dismiss="modal"
                >
                  Submit
                </button>
              </div>
            </div>
          </div>
        </div>

        <div
          className="modal fade"
          id={this.props.movieID + "RecommendModal"}
          tabindex="-2"
          role="dialog"
          aria-labelledby={this.props.movieID + "RecomendModal"}
          aria-hidden="true"
        >
          <div className="modal-dialog" role="document">
            <div className="modal-content">
              <div className="modal-header">
                <h3 className="modal-title" id="exampleModalLabel">
                  Recommend{" "}
                  <i>
                    {this.props.title} {"("}
                    {this.props.year}
                    {")"}
                  </i>
                  {" to a friend"}
                </h3>
                <button
                  type="button"
                  className="close"
                  data-dismiss="modal"
                  aria-label="Close"
                >
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div className="modal-body">
                <UserResults
                  users={users}
                  email={this.props.email}
                  friends={true}
                  receivedRequests={false}
                  sentRequests={false}
                  recommend={true}
                  movieID={this.props.movieID}
                />
              </div>
            </div>
          </div>
        </div>

        <div className="row">
          <div className="col-1">
            <img className="img-fluid" src={this.props.posterUrl} />
          </div>
          <div className="col-11">
            <b>
              {this.props.title} {"("}
              {this.props.year}
              {")"}
            </b>
            <div className="custom-btn-group">
              <button
                onClick={this.checkRating.bind(this)}
                href="#"
                data-toggle="modal"
                data-target={"#" + this.props.movieID + "RateModal"}
                className="btn btn-secondary"
              >
                {"Rate"}
              </button>
              <button
                onClick={this.getFriends.bind(this)}
                className="btn btn-secondary"
                href="#"
                data-toggle="modal"
                data-target={"#" + this.props.movieID + "RecommendModal"}
              >
                {"Recommend"}
              </button>
              <button
                data-toggle="collapse"
                data-target={expandName}
                className="btn btn-secondary"
                role="button"
                aria-expanded="false"
              >
                {"See Ratings"}
              </button>
            </div>
            <div>
              {"Avg. User Rating: "} {this.props.rating}
            </div>
            <div>{this.props.plot}</div>
            <div className="collapse multi-collapse" id={expandID}>
              <AllRatings
                ref={instance => {
                  this.ratings = instance;
                }}
                movieID={this.props.movieID}
              />
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default MovieListing;
