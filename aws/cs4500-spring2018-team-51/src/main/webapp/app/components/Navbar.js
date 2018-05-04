var React = require("react");
var ReactDOM = require("react-dom");
var axios = require("axios");

/**
 * Represents the navbar always present at the top of the application.
 */
class Navbar extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      searchTerm: ""
    };
    this.changeTerm = this.changeTerm.bind(this);
  }

  /**
   * Handles a change in the text inputted into the search bar.
   * @param {*} event - The user's input into the search bar.
   */
  changeTerm(event) {
    this.setState({ searchTerm: event.target.value });
    this.props.onChange(event.target.value);
  }

  /**
   * Logs out the user currently logged in.
   */
  logOut() {
    var baseURL =
      "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/logout/";
    var url = baseURL + this.props.email + "/";
    var _this = this;
    this.serverRequest = axios
    .post(url)
    .then(response => {
      this.props.onChange("logMeOutBaby");
    })
  }

  /**
   * Triggers the loading of the admin page if the "Admin" link is clicked.
   */
  adminTime() {
    this.setState({
      searchTerm: ""
    });
    this.props.onChange("adminTime");
  }

  /**
   * Triggers the loading of the profile page if the "My Profile" link is clicked. 
   */
  userTime() {
    this.setState({
      searchTerm: ""
    });
    this.props.onChange("userTime");
  }

  /**
   * Triggers the loading of the home page if the Spoiled Tomatillos logo is clicked.
   */
  homeTime() {
    this.setState({
      searchTerm: ""
    });
    this.props.onChange("homeTime");
  }

  /**
   * Renders the navbar component.
   */
  render() {
    var navStyle = {
      backgroundColor: "#ff0000"
    };
    return (
      <nav
        className="navbar navbar-light bg-faded rounded navbar-toggleable-md"
        style={navStyle}
      >
        <button
          className="navbar-toggler navbar-toggler-right"
          type="button"
          data-toggle="collapse"
          data-target="#containerNavbar"
          aria-controls="containerNavbar"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon" />
        </button>
        <a className="navbar-brand" href="#" onClick={this.homeTime.bind(this)}>
          <img
            className="img-fluid navbar-brand"
            src={"https://imgur.com/9tku7By.png"}
          />
        </a>

        <div className="collapse navbar-collapse" id="containerNavbar">
          <form className="form-inline my-2 my-md-0 mr-auto ml-auto">
            <input
              type="text"
              value={this.state.searchTerm}
              onChange={this.changeTerm}
              className="form-control mr-sm-2"
              placeholder="Search"
            />
          </form>
          <ul className="navbar-nav">
            <li className="nav-item dropdown">
              <a className="nav-link dropdown-toggle" data-toggle="dropdown">
                {this.props.email}
              </a>
              <div className="dropdown-menu">
                <a
                  onClick={this.userTime.bind(this)}
                  className="dropdown-item"
                  href="#"
                >
                  My Profile
                </a>
                {this.props.adminStatus && (
                  <a
                    onClick={this.adminTime.bind(this)}
                    className="dropdown-item"
                    href="#"
                  >
                    Admin
                  </a>
                )}
                <a
                  onClick={this.logOut.bind(this)}
                  className="dropdown-item"
                  href="#"
                >
                  Log Out
                </a>
              </div>
            </li>
          </ul>
        </div>
      </nav>
    );
  }
}

export default Navbar;
