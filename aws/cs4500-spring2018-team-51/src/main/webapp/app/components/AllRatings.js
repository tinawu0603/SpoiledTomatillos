var React = require("react");
var ReactDOM = require("react-dom");
var axios = require("axios");
import ReactStars from "react-stars";
import RatingListing from "./RatingListing";

/**
 * Represents the list of all user reviews of a movie.
 */
class AllRatings extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      reviews: []
    };
  }

  /**
   * Gets all reviews of a movie once this component has mounted. 
   */
  componentDidMount() {
    this.getReviews();
  }

  /**
   * Gets all user reviews of a movie.
   */
  getReviews() {
    var _this = this;
    this.serverRequest = axios
      .get(
        "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/getallratings/" +
          this.props.movieID
      )
      .then(function(result) {
        _this.setState({
          reviews: result.data
        });
      });
  }

  /**
   * Renders this list of user reviews of a movie.
   */
  render() {
    var ratings = [];
    for (var i = 0; i < this.state.reviews.length; i++) {
      var id = this.state.reviews[i].movie_ID;
      var rating = this.state.reviews[i].rating;
      var review = this.state.reviews[i].review;
      var user = this.state.reviews[i].name;
      ratings.push(
        <RatingListing
          key={i}
          movieID={id}
          user={user}
          rating={rating}
          review={review}
        />
      );
    }
    return <div className="container-fluid">{ratings}</div>;
  }
}

export default AllRatings;
