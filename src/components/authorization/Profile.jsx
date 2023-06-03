import React from 'react';
import { Navigate } from 'react-router-dom';
import { connect } from "react-redux";

class Profile extends React.Component {

  render() {
    const { user: currentUser } = this.props;

    if (!currentUser) {
      return <Navigate to="/login" />;
    }

    return (
      <div className="container">
        <header>
          <h3>
            Профиль <strong>{currentUser.mail}</strong>
          </h3>
        </header>
        <p>
            <strong>Почта: </strong>
            {currentUser.mail}
        </p>
        <p>
            <strong>Имя: </strong>
            {currentUser.username}
        </p>
      </div>
    );
  }
}

function mapStateToProps(state) {
  const { user } = state.auth;
  return {
    user
  };
}

export default connect(mapStateToProps)(Profile);