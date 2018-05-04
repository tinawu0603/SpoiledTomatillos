var React = require("react");
var ReactDOM = require("react-dom");
var axios = require("axios");
import UserListing from "./UserListing";
const maxNumResults = 4;

/**
 * The search results section for users that is seen when text is typed into the search bar.
 */
class UserResults extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      numResults: maxNumResults,
      friends: [],
      receieved: [],
      sent: [],
      username: "",
      weCool: false
    };
  }

  /**
   * Checks if the logged in user is friends with the given user.
   * @param {*} value - A User.
   */
  checkFriend(value) {
    var allFriends = this.props.friends;
    if (allFriends != undefined) {
      for (var i = 0; i < allFriends.length; i++) {
        var curUser = allFriends[i].email;
        if (value == curUser) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Checks if the logged in user is has received a friend request from the given user. 
   * @param {*} value - A User.
   */
  checkReceived(value) {
    var allReceived = this.props.receivedRequests;
    if (allReceived != undefined) {
      for (var i = 0; i < allReceived.length; i++) {
        var curUser = allReceived[i].user1Email;
        if (value == curUser) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Checks if the logged in user has sent a friend request to the given user.
   * @param {*} value - A User.
   */
  checkSent(value) {
    var allSent = this.props.sentRequests;
    if (allSent != undefined) {
      for (var i = 0; i < allSent.length; i++) {
        var curUser = allSent[i].user2Email;
        if (value == curUser) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Prepares this component to receive new props on re-rendering.
   * @param {*} nextProps - Returned results from search.
   */
  componentWillReceiveProps(nextProps) {
    if (nextProps.users.length <= this.state.numResults) {
      this.setState({
        numResults: nextProps.users.length
      });
    }
    if (nextProps.users.length > maxNumResults) {
      this.setState({
        numResults: maxNumResults
      });
    }
  }

  /**
   * Renders the search results pertaining to users.
   */
  render() {
    var users = [];
    for (var i = 0; i < this.state.numResults; i++) {
      if (this.props.users[i] != undefined) {
        var id = this.props.users[i].id;
        var name = this.props.users[i].fullName;
        var username = this.props.users[i].email;
        var friends = this.checkFriend(username);
        var received = false;
        var sent = false;
        if (!friends) {
          received = this.checkReceived(username);
        }
        if (!received && !friends) {
          sent = this.checkSent(username);
        }
        users.push(
          <UserListing
            key={id}
            userid={id}
            name={name}
            username={username}
            email={this.props.email}
            friends={friends}
            received={received}
            sent={sent}
            onChange={this.props.onChange}
            recommend={this.props.recommend}
            movieID={this.props.movieID}
          />
        );
      }
    }
    return <div className="container-fluid">{users}</div>;
  }
}

export default UserResults;
