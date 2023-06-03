import React from 'react';
import { Navigate, Link } from 'react-router-dom';

import { connect } from "react-redux";
import auth from "../../actions/auth";

class Login extends React.Component {
  constructor(props) {
    super(props);
    this.handleLogin = this.handleLogin.bind(this);
    this.onChangeName = this.onChangeName.bind(this);
    this.onChangePassword = this.onChangePassword.bind(this);

    this.state = {
      username: "",
      password: "",
      loading: undefined
    };
  }

  onChangeName(e) {
    this.setState({ username: e.target.value });
  }

  onChangePassword(e) {
    this.setState({ password: e.target.value });
  }

  handleLogin(e) {
    e.preventDefault();

    this.setState({ loading: true });

    const { dispatch } = this.props;
    
    dispatch(auth.login(this.state.username, this.state.password))
        .then(() => {
          console.log('Success login')
          window.location.reload();
        })
        .catch(() => {
            console.log('Error login')
          this.setState({ loading: false });
        });
  }

  render() {
    const { isLoggedIn, message } = this.props;

    if (isLoggedIn) {
      return <Navigate to="/profile" />;
    }

    return (
        <div className="col-md-5">
          <form onSubmit={this.handleLogin} >
            <div className="form-group mt-2">
              <input type="text" className="form-control" name="username" placeholder="Логин" value={this.state.username} onChange={this.onChangeName} required/>
            </div>
            <div className="form-group mt-2">
              <input type="password" className="form-control" name="password" placeholder="Пароль" value={this.state.password} onChange={this.onChangePassword} required/>
            </div>

            <div className="form-group mt-2">
              <button className="btn btn-primary btn-block" disabled={this.state.loading} >
                {this.state.loading && (
                    <span className="spinner-border spinner-border-sm"></span>
                )}
                <span>Войти</span>
              </button>
            </div>
            <div className="form-group mt-2">
              <Link to={`/register`}>
                Зарегистрироваться
              </Link>
            </div>
            {message && this.state.loading !== undefined && (
                <div className="form-group">
                  <div className="alert alert-danger" role="alert">
                    {message}
                  </div>
                </div>
            )}
          </form>
        </div>
    );
  }
}

// функциональность Redux: позволяет передать на перенаправляемую страницу данные (в данном случае передаются данные на страницу профиля)
function mapStateToProps(state) {
  const { isLoggedIn } = state.auth;
  const { message } = state.message;
  return {
    isLoggedIn,
    message
  };
}

export default connect(mapStateToProps)(Login);