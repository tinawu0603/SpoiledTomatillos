var React = require("react");
var ReactDOM = require("react-dom");
var axios = require("axios");
import ReactStars from "react-stars";

/**
 * Represents a row of the user ratings for a movie.
 */
class RatingListing extends React.Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  /**
   * Renders an individual user rating of a movie.
   */
  render() {
    return (
      <div className="row">
        <div className="col-11">
          <div>
            <b>
              {"Rating by"} {this.props.user} {": "}
            </b>
          </div>
          <div>
            {this.props.rating} stars - <i>{this.props.review}</i>
          </div>
        </div>
      </div>
    );
  }
}

export default RatingListing;
