var React = require("react");
var ReactDOM = require("react-dom");
var axios = require("axios");
import MovieListing from "./MovieListing";

/**
 * The home page that is seen immediately after logging in or creating an account.
 */
class Home extends React.Component {
  constructor(props) {
    super(props);
  }

  /**
   * Renders this home page.
   */
  render() {
    var movieListings = [];
    for (var i = 0; i < 3; i++) {
      if (this.props.topMovies[i] != undefined) {
        var id = this.props.topMovies[i].movieID;
        var year = this.props.topMovies[i].year;
        var posterUrl = this.props.topMovies[i].poster;
        var rating = this.props.topMovies[i].rating;
        var title = this.props.topMovies[i].title;
        var plot = this.props.topMovies[i].plot;
        movieListings.push(
          <MovieListing
            key={id}
            movieID={id}
            plot={plot}
            title={title}
            year={year}
            posterUrl={posterUrl}
            rating={rating}
            email={this.props.email}
          />
        );
      }
    }
    return (
      <div className="container-fluid">
        <h3>Recommended for You</h3>
        {this.props.recommendation == "NONE" && (
          <h6>
            <i>
              We can't recommend a movie for you until you rate some movies
              you've already seen!
            </i>
          </h6>
        )}
        {this.props.recommendation != "NONE" && (
          <section>
            <h6>
              <i>
                Based on your previous ratings, we think you'll love this movie
              </i>
            </h6>
            <MovieListing
              key={this.props.recommendation.id}
              movieID={this.props.recommendation.movieID}
              plot={this.props.recommendation.plot}
              title={this.props.recommendation.title}
              year={this.props.recommendation.year}
              posterUrl={this.props.recommendation.poster}
              rating={this.props.recommendation.rating}
              email={this.props.email}
            />
          </section>
        )}
        <br />
        <h3>Top Movies</h3>
        <h6>
          <i>These are the best movies around, according to our users</i>
        </h6>
        {movieListings}
      </div>
    );
  }
}

export default Home;
