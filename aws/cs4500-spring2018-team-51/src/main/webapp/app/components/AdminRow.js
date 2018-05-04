var React = require("react");
var ReactDOM = require("react-dom");
var axios = require("axios");

/**
 * Represents a row of the table of users featured on the admin page.
 */
class AdminRow extends React.Component {
  constructor(props) {
    super(props);
  }

  /**
   * Makes the user represented in this row an admin user.
   */
  makeAdmin() {
    var baseURL =
      "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/makeAdmin/";
    var url = baseURL + this.props.email + "/" + this.props.username + "/";
    var _this = this;
    this.serverRequest = axios.post(url).then(function(response) {});
  }

  /**
   * Removes the user represented in this row.
   */
  removeUser() {
    var baseURL =
      "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/deleteuser/";
    var url = baseURL + this.props.email + "/" + this.props.username + "/";
    var _this = this;
    this.serverRequest = axios.post(url).then(function(response) {});
  }

  /**
   * Renders this row of the user table on the admin page.
   */
  render() {
    return (
      <tr>
        <td>{this.props.firstName}</td>
        <td>{this.props.lastName}</td>
        <td>{this.props.username}</td>
        <td>
          <button
            onClick={this.makeAdmin.bind(this)}
            href="#"
            className="btn btn-secondary"
          >
            Make Admin
          </button>
        </td>
        <td>
          <button
            onClick={this.removeUser.bind(this)}
            href="#"
            className="btn btn-secondary"
          >
            Remove User
          </button>
        </td>
      </tr>
    );
  }
}

export default AdminRow;
