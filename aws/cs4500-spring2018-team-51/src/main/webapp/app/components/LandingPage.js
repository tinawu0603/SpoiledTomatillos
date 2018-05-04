var React = require("react");
var ReactDOM = require("react-dom");
var axios = require("axios");

/**
 * The landing page that is seen before logging in or creating an account.
 */
class LandingPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      loginEmail: "",
      loginPassword: "",
      loginError: "",
      registrationError: ""
    };
  }

  /**
   * Handles user input into the first name field of registration.
   * @param {String} event - the inputted first name.
   */
  changeFirstName(event) {
    this.setState({
      firstName: event.target.value
    });
  }

  /**
   * Handles user input into the last name field of registration.
   * @param {String} event - the inputted last name.
   */
  changeLastName(event) {
    this.setState({
      lastName: event.target.value
    });
  }

  /**
   * Handles user input into the email field of registration.
   * @param {String} event - the inputted email.
   */
  changeEmail(event) {
    this.setState({
      email: event.target.value
    });
  }

  /**
   * Handles user input into the password field of registration.
   * @param {String} event - the inputted password.
   */
  changePassword(event) {
    this.setState({
      password: event.target.value
    });
  }

  /**
   * Handles user input into the email field of logging in.
   * @param {String} event - the inputted email.
   */
  changeLoginEmail(event) {
    this.setState({
      loginEmail: event.target.value
    });
  }

  /**
   * Handles user input into the password field of logging in.
   * @param {String} event - the inputted password.
   */
  changeLoginPassword(event) {
    this.setState({
      loginPassword: event.target.value
    });
  }

  /**
   * Creates an account using the user inputted first name, last name, email, and password, if valid.
   */
  createAccount(e) {
    e.preventDefault();
    var url =
      "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/user/insert/";
    var user = {
      firstName: this.state.firstName,
      lastName: this.state.lastName,
      email: this.state.email,
      password: this.state.password
    };
    var _this = this;
    this.serverRequest = axios
      .post(url, user)
      .then(response => {
        _this.props.onChange(_this.state.email);
      })
      .catch(function(error) {
        if (error.response) {
          var message = error.response.data.message;
          _this.setState({
            registrationError: message
          });
          return;
        }
        return;
      });
  }

  /**
   * Logs in a user using the inputted email and password, if valid.
   */
  login() {
    var url =
      "http://ec2-18-219-31-161.us-east-2.compute.amazonaws.com:8080/api/login/";
    var user = {
      email: this.state.loginEmail,
      password: this.state.loginPassword
    };
    var _this = this;
    this.serverRequest = axios
      .post(url, user)
      .then(response => {
        console.log(response);
        this.props.onChange(this.state.loginEmail);
      })
      .catch(function(error) {
        if (error.response) {
          console.log("ERROR!");
          console.log(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);
          var message = error.response.data.message;
          _this.setState({
            loginError: message
          });
          return;
        }
        return;
      });
  }

  /**
   * Renders the landing page.
   */
  render() {
    var navStyle = {
      backgroundColor: "#ff0000"
    };
    return (
      <section>
        <div
          className="modal fade"
          id="signInModal"
          tabindex="-1"
          role="dialog"
          aria-labelledby="signInModal"
          aria-hidden="true"
        >
          <div className="modal-dialog" role="document">
            <div className="modal-content">
              <div className="modal-header">
                <h3 className="modal-title" id="exampleModalLabel">
                  Sign in to Spoiled Tomatillos
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
                <div className="form-group">
                  <input
                    type="email"
                    value={this.state.loginEmail}
                    onChange={this.changeLoginEmail.bind(this)}
                    className="form-control"
                    id="userEmail"
                    placeholder="Email"
                  />
                </div>
                <div className="form-group">
                  <input
                    type="password"
                    value={this.state.loginPassword}
                    onChange={this.changeLoginPassword.bind(this)}
                    className="form-control"
                    id="userPassword"
                    placeholder="Password"
                  />
                </div>
                <button
                  onClick={this.login.bind(this)}
                  type="submit"
                  className="btn btn-secondary btn-block"
                  data-dismiss="modal"
                >
                  Sign In
                </button>
              </div>
            </div>
          </div>
        </div>
        <nav
          className="navbar navbar-primary rounded navbar-toggleable-md"
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
          <img
            className="img-fluid navbar-brand"
            src={"https://imgur.com/9tku7By.png"}
          />

          <div
            className="collapse navbar-collapse justify-content-end"
            id="containerNavbar"
          >
            <ul className="navbar-nav">
              <li className="nav-item">
                <a
                  className="nav-link"
                  href="#"
                  data-toggle="modal"
                  data-target="#signInModal"
                >
                  Sign In
                </a>
              </li>
            </ul>
          </div>
        </nav>

        <div className="container">
          <br />
          <div className="row">
            <div className="col-6 landing-info">
              <h1>Spoiled Tomatillos</h1>
              <h4>Bridging the chasm between Netflix and Facebook</h4>
              <p>
                Welcome to the social media platform for movie lovers. Spoiled
                Tomatillos makes it easy to find new movies and share movies you
                love with friends.
              </p>
            </div>
            <div className="col-6 registration">
              <div className="subsection">
                {this.state.loginError != "" && (
                  <div className="alert alert-danger">
                    {this.state.loginError}
                  </div>
                )}
                <h4>Register to join Spoiled Tomatillos</h4>
                <form>
                  <div className="form-group">
                    <input
                      className="form-control"
                      value={this.state.firstName}
                      onChange={this.changeFirstName.bind(this)}
                      id="firstName"
                      aria-describedby="firstNameHelp"
                      placeholder="First name"
                    />
                  </div>
                  <div className="form-group">
                    <input
                      className="form-control"
                      value={this.state.lastName}
                      onChange={this.changeLastName.bind(this)}
                      id="lastName"
                      aria-describedby="lastNameHelp"
                      placeholder="Last name"
                    />
                  </div>
                  <div className="form-group">
                    <input
                      type="email"
                      className="form-control"
                      value={this.state.email}
                      onChange={this.changeEmail.bind(this)}
                      id="email"
                      aria-describedby="emailHelp"
                      placeholder="Email"
                    />
                  </div>
                  <div className="form-group">
                    <input
                      type="password"
                      className="form-control"
                      value={this.state.password}
                      onChange={this.changePassword.bind(this)}
                      id="password"
                      placeholder="Password"
                    />
                  </div>
                  {this.state.registrationError != "" && (
                    <div className="alert alert-danger">
                      {this.state.registrationError}
                    </div>
                  )}
                  <button
                    onClick={this.createAccount.bind(this)}
                    type="submit"
                    className="btn btn-secondary btn-block"
                  >
                    Register
                  </button>
                </form>
              </div>
              <br />
              <p>
                Already have an account?{" "}
                <a href="#" data-toggle="modal" data-target="#signInModal">
                  Sign in
                </a>
              </p>
            </div>
          </div>
        </div>
      </section>
    );
  }
}

export default LandingPage;
