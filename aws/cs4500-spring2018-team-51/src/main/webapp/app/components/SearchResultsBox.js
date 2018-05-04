var React = require("react");
var ReactDOM = require("react-dom");
var axios = require("axios");
import MovieListing from "./MovieListing";
import SearchResults from "./SearchResults.js";
import UserResults from "./UserResults.js";

/**
 * Represents a section in the search results, either users or movies.
 */
class SearchResultsBox extends React.Component {
  constructor(props) {
    super(props);
  }

  /**
   * Renders this section of search results.
   */
  render() {
    return (
      <div className="searchContainer col-12">
        <div className="col-10">
          {this.props.type == "movies" &&
            this.props.list.length > 0 && (
              <div>
                <h3>Movies</h3>
                <SearchResults
                  movieList={this.props.list}
                  email={this.props.email}
                />
              </div>
            )}
          {this.props.type == "users" &&
            this.props.list.length > 0 && (
              <div>
                <h3>People</h3>
                <UserResults
                  users={this.props.list}
                  email={this.props.email}
                  friends={this.props.friends}
                  receivedRequests={this.props.receivedRequests}
                  sentRequests={this.props.sentRequests}
                  onChange={this.props.onChange}
                  recommend={false}
                />
              </div>
            )}
        </div>
      </div>
    );
  }
}

export default SearchResultsBox;
