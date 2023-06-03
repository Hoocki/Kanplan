import React from 'react';
import { Navigate } from 'react-router-dom';

import { connect } from "react-redux";
import auth from "../../actions/auth";


class Register extends React.Component {
  constructor(props) {
    super(props);

    this.handleRegister = this.handleRegister.bind(this);
    this.onChangeUsername = this.onChangeUsername.bind(this);
    this.onChangeMail = this.onChangeMail.bind(this);
    this.onChangePassword = this.onChangePassword.bind(this);

    this.state = {
      mail: "",
      username: "",
      password: "",
      successful: undefined
    };
  }

  onChangeUsername(e) {
    this.setState({username: e.target.value});
  }

  onChangeMail(e) {
    this.setState({mail: e.target.value});
  }

  onChangePassword(e) {
    this.setState({ password: e.target.value });
  }

  handleRegister(e) {
    e.preventDefault();

    this.setState({successful: false});
    const { dispatch } = this.props;

    dispatch(
        auth.register(this.state.mail, this.state.password, this.state.username)
    )
        .then(() => {
          this.setState({successful: true});
          // Авторизация прошла успешно, переходим к странице входа в систему
          window.location.reload();
        })
        .catch(() => {
          console.log('User: ' + this.state.mail + ', ' + this.state.password + ', ' + this.state.username)
          this.setState({successful: false });
        });
  }

  render() {
    const { isRegistered, message } = this.props;

    if (isRegistered) {
      return <Navigate to="/login" />;
    }
    return (
        <div className="col-md-5">
          <form onSubmit={this.handleRegister}>
            <div className="form-group mt-2">
              <input type="text" className="form-control" name="mail" placeholder="Почта" value={this.state.mail} onChange={this.onChangeMail} required/>
            </div>
            <div className="form-group mt-2">
              <input type="text" className="form-control" name="username" placeholder="Логин" value={this.state.username} onChange={this.onChangeUsername} required/>
            </div>
            <div className="form-group mt-2">
              <input type="password" className="form-control" name="password" placeholder="Пароль" value={this.state.password} onChange={this.onChangePassword} required/>
            </div>
            <div className="form-group mt-2">
              <button className="btn btn-primary btn-block">Зарегистрировать</button>
            </div>
            {message && this.state.successful !== undefined && (
                <div className="form-group">
                  <div className={ this.state.successful ? "alert alert-success" : "alert alert-danger" } role="alert">
                    {message}
                  </div>
                </div>
            )}
          </form>
        </div>
    )}
}

function mapStateToProps(state) {
  const { isRegistered } = state.auth;
  const { message } = state.message;
  return {
    isRegistered,
    message
  };
}

export default connect(mapStateToProps)(Register);