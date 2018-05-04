var React = require("react");
var ReactDOM = require("react-dom");
var axios = require("axios");
import SearchResults from "./SearchResults.js";
import UserResults from "./UserResults.js";
import SearchResultsBox from "./SearchResultsBox.js";
import MovieListing from "./MovieListing";
import Navbar from "./Navbar";
import LandingPage from "./LandingPage";
import ReactStars from "react-stars";
import AdminPage from "./AdminPage";
import ProfilePage from "./ProfilePage";
import Home from "./Home";

/**
 * Main class that runs the entire app.
 */
class Main extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      movies: [],
      users: [],
      searchTerm: "",
      isLoggedIn: false,
      email: "",
      adminStatus: false,
      adminTime: false,
      allUsers: [],
      userTime: false,
      friends: [],
      receivedRequests: [],
      sentRequests: [],
      user: "",
      homeTime: true,
      recommendation: "",
      topMovies: []
    };
    this.handleNavbarChange = this.handleNavbarChange.bind(this);
    this.updateSearchTerm = this.updateSearchTerm.bind(this);
    this.handleFriendChange = this.handleFriendChange.bind(this);
  }

  /**
   * Uses the search term to update the list of movies and people to reflect user input into search bar.
   */
  updateSearchTerm() {
    if (this.refs.searchBox1) {
      if (this.state.searchTerm.length == 0) {
        this.setState({
          movies: []
        });
      } else {
        var _this = this;
        this.serverRequest = axios
          .get(
            "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/movie/title/" +
              this.state.searchTerm
          )
          .then(function(result) {
            _this.setState({
              movies: result.data
            });
          });
      }
    }

    if (this.refs.searchBox2) {
      if (this.state.searchTerm.length == 0) {
        this.setState({
          users: []
        });
      } else {
        this.serverRequest = axios
          .get(
            "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/searchUser/" +
              this.state.searchTerm
          )
          .then(function(result) {
            _this.setState({
              users: result.data
            });
          });
      }
    }
  }

  /**
   * Handles different actions in the navbar including logging out, changing pages, and searching.
   * @param {String} value - Determines what action has been triggered.
   */
  handleNavbarChange(value) {
    if (value == "logMeOutBaby") {
      this.setState({
        email: "",
        isLoggedIn: false
      });
    }
    if (value == "adminTime") {
      this.setState({
        searchTerm: "",
        adminTime: true,
        userTime: false,
        homeTime: false
      });
      this.updateSearchTerm();
      return;
    }
    if (value == "userTime") {
      this.getUser();
      this.setState({
        searchTerm: "",
        adminTime: false,
        userTime: true,
        homeTime: false
      });
      this.updateSearchTerm();
      return;
    }
    if (value == "homeTime") {
      this.setState({
        searchTerm: "",
        adminTime: false,
        userTime: false,
        homeTime: true
      });
      this.updateSearchTerm();
      return;
    }
    this.setState(
      {
        adminTime: false,
        userTime: false,
        homeTime: false,
        searchTerm: value
      },
      () => {
        this.updateSearchTerm();
      }
    );
  }

  /**
   * Handles a user logging in.
   * @param {String} email - Email of user to log in.
   */
  handleLoginChange(email) {
    this.setState(
      {
        email: email
      },
      function() {
        this.checkAdmin();
        this.setState({
          isLoggedIn: true,
          homeTime: true
        });
        this.getFriends();
        this.getReceived();
        this.getSent();
        this.getRecommendation();
        this.getTopMovies();
      }
    );
  }

  /**
   * Handles button clicks for sending and accepting friend requests.
   * @param {String} value - Determines what friending action has been triggered.
   */
  handleFriendChange(value) {
    if (value == "newRequest") {
      this.getSent();
    }
    if (value == "newFriend") {
      this.getFriends();
      this.getReceived();
    }
  }

  /**
   * Checks to see if the logged in user is an admin.
   */
  checkAdmin() {
    var _this = this;
    this.serverRequest = axios
      .get(
        "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/userauthorized/" +
          this.state.email +
          "/"
      )
      .then(function(result) {
        _this.setState({
          adminStatus: result.data
        });
        if (_this.state.adminStatus) {
          _this.getAllUsers();
        }
      });
  }

  /**
   * Gets all the users on the website, to be displayed if the logged in user is an admin.
   */
  getAllUsers() {
    var _this = this;
    this.serverRequest = axios
      .get(
        "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/allusers/" +
          _this.state.email +
          "/"
      )
      .then(function(result) {
        _this.setState({
          allUsers: result.data
        });
      });
  }

  /**
   * Gets a list of the friends of the logged in user.
   */
  getFriends() {
    var _this = this;
    this.serverRequest = axios
      .get(
        "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/findFriends/" +
          this.state.email +
          "/"
      )
      .then(function(result) {
        _this.setState({
          friends: result.data
        });
      });
  }

  /**
   * Gets a list of the pending friend requests received by the logged in user.
   */
  getReceived() {
    var _this = this;
    this.serverRequest = axios
      .get(
        "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/getReceived/" +
          this.state.email +
          "/"
      )
      .then(function(result) {
        _this.setState({
          receivedRequests: result.data
        });
      });
  }

  /**
   * Gets a list of the pending friend requests sent by the logged in user.
   */
  getSent() {
    var _this = this;
    this.serverRequest = axios
      .get(
        "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/getSent/" +
          this.state.email +
          "/"
      )
      .then(function(result) {
        _this.setState({
          sentRequests: result.data
        });
      });
  }

  /**
   * Gets the current User from the email used to log in.
   */
  getUser() {
    var _this = this;
    this.serverRequest = axios
      .get(
        "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/findEmail/" +
          _this.state.email +
          "/"
      )
      .then(function(result) {
        _this.setState({
          user: result.data
        });
      });
  }

  /**
   * Gets a recommended movie from the system specifically for the logged in user.
   */
  getRecommendation() {
    console.log(this.state.email);
    var _this = this;
    this.serverRequest = axios
      .get(
        "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/movie/recommend/" +
          this.state.email +
          "/"
      )
      .then(function(result) {
        _this.setState({
          recommendation: result.data
        })
      })
      .catch(function(error) {
        _this.setState({
          recommendation: "NONE"
        })
      })
  };

  /**
   * Gets a list of the top movies based on all users' ratings.
   */
  getTopMovies() {
    var _this = this;
    this.serverRequest = axios
      .get(
        "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/movie/topRated"
      )
      .then(function(result) {
        _this.setState({
          topMovies: result.data
        });
      });
  }

  /**
   * Renders the entire application.
   */
  render() {
    if (this.state.isLoggedIn) {
      return (
        <section>
          <Navbar
            searchTerm={this.state.searchTerm}
            action={this.handleNavbarChange}
            onChange={this.handleNavbarChange}
            email={this.state.email}
            adminStatus={this.state.adminStatus}
          />
          {this.state.adminTime && (
            <AdminPage
              email={this.state.email}
              allUsers={this.state.allUsers}
            />
          )}
          {this.state.userTime && (
            <ProfilePage
              email={this.state.email}
              user={this.state.user}
              receivedRequests={this.state.receivedRequests}
              onChange={this.handleFriendChange}
            />
          )}
          {this.state.homeTime && (
            <Home
              email={this.state.email}
              recommendation={this.state.recommendation}
              topMovies={this.state.topMovies}
            />
          )}
          {!this.state.adminTime &&
            !this.state.userTime &&
            !this.state.homeTime && (
              <section>
                <SearchResultsBox
                  ref="searchBox1"
                  type="movies"
                  list={this.state.movies}
                  email={this.state.email}
                />
                <SearchResultsBox
                  ref="searchBox2"
                  type="users"
                  list={this.state.users}
                  email={this.state.email}
                  friends={this.state.friends}
                  receivedRequests={this.state.receivedRequests}
                  sentRequests={this.state.sentRequests}
                  onChange={this.handleFriendChange}
                />
              </section>
            )}
        </section>
      );
    } else {
      return (
        <section>
          <LandingPage
            action={this.handleLoginChange.bind(this)}
            onChange={this.handleLoginChange.bind(this)}
          />
        </section>
      );
    }
  }
}

ReactDOM.render(<Main />, document.getElementById("app"));
