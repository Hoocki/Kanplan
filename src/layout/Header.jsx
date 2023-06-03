import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';

import auth from '../actions/auth';

class Header extends React.Component {
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);
  }

  logOut() {
    this.props.dispatch(auth.logout());
    window.location.reload();
  }

  render() {
    const { user: currentUser } = this.props;

    // Check if the user is authenticated
    if (currentUser) {
      return (
        <nav className="navbar navbar-project navbar-expand-lg navbar-light" style={{ background: '#64bbe3' }}>
          <div className="ms-3">
            <Link className="navbar-brand" to="/myBoards">Мои доски</Link>
          </div>
          <div className="ml-auto">
            <Link className="navbar-brand btn" to="/profile">{currentUser.mail}</Link>
            <button className="navbar-brand btn" onClick={this.logOut}>Выйти</button>
          </div>
        </nav>
      );
    }

    // User is not authenticated
    return (
      <nav className="navbar navbar-project navbar-expand-lg navbar-light" style={{ background: '#64bbe3' }}>
        <div className="ml-auto">
          <Link to="/register" className="nav-link navbar-brand btn navbar-brand-button">Регистрация</Link>          
        </div>
        <div className="ml-auto">          
          <Link to="/login" className="nav-link navbar-brand btn navbar-brand-button">Вход в систему</Link>
        </div>
      </nav>
    );
  }
}

function mapStateToProps(state) {
  const { user } = state.auth;
  return {
    user
  };
}

export default connect(mapStateToProps)(Header);
