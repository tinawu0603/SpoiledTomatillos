var React = require("react");
var ReactDOM = require("react-dom");
var axios = require("axios");
import AdminRow from "./AdminRow";

/**
 * The admin that is seen by admin users only.
 */
class AdminPage extends React.Component {
    constructor(props) {
      super(props);

    }

    /**
     * Renders the admin page.
     */
    render() {
        var users = [];
        for (var i = 0; i < this.props.allUsers.length; i++) {
            var firstName = this.props.allUsers[i].firstName;
            var lastName = this.props.allUsers[i].lastName;
            var username = this.props.allUsers[i].email;
            users.push(
            <AdminRow
                key={username}
                firstName={firstName}
                lastName={lastName}
                username={username}
                email={this.props.email}
            />
            );
          }
        return (
            <div className="container-fluid">
                <h3>Admin Features</h3>
                <p>Thank you for helping to keep things running smoothly here at Spoiled Tomatillos. Please do not let this administrative power go to your head.</p>
                <h5>All Users</h5>
                <table className="table-bordered">
                    <thead>
                        <tr>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Email</th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {users}
                    </tbody>
                </table>
            </div>
        )
    }
}

export default AdminPage;