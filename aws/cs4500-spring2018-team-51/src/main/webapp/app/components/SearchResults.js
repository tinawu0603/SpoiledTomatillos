var React = require("react");
var ReactDOM = require("react-dom");
var axios = require("axios");
import MovieListing from "./MovieListing";
const maxNumResults = 4;

/**
 * The search results page that is seen when text is typed into the search bar.
 */
class SearchResults extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      numResults: maxNumResults
    };
  }

  /**
   * Prepares this component to receive new props on re-rendering.
   * @param {*} nextProps - Returned results from search.
   */
  componentWillReceiveProps(nextProps) {
    if (nextProps.movieList.length <= this.state.numResults) {
      this.setState({
        numResults: nextProps.movieList.length
      });
    }
    if (nextProps.movieList.length > maxNumResults) {
      this.setState({
        numResults: maxNumResults
      });
    }
  }

  /**
   * Renders the search results page.
   */
  render() {
    var movieListings = [];
    for (var i = 0; i < this.state.numResults; i++) {
      if (this.props.movieList[i] != undefined) {
        var id = this.props.movieList[i].movieID;
        var year = this.props.movieList[i].year;
        var posterUrl = this.props.movieList[i].poster;
        var rating = this.props.movieList[i].rating;
        var title = this.props.movieList[i].title;
        var plot = this.props.movieList[i].plot;
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
    return <div className="container-fluid">{movieListings}</div>;
  }
}

export default SearchResults;
