import http from "../http-common";

function login(username, password) {
    var data = {
        username: username,
        password: password
    };
    console.log(data)
    return http
        .post("/auth/signin", data)
        .then(response => {
            console.log('response.data.token: ' + response.data.token)
        if (response.data.token) {
            console.log("acceess threw  if (response.data.token): " + data)
            localStorage.setItem('user', JSON.stringify(response.data)); // записываем данные пользователя в локальное хранилище, которое хранится в браузере
        }
        return response.data;
    });
}

function logout() {
    localStorage.removeItem('user'); // при нажатии кнопки "Выйти" удаляем данные пользователя из локального хранилища
}
function register(mail, password, username) {
    var data = {
        mail: mail,
        password: password,
        username: username
    };
    return http.post("/auth/signup", data);
}

const exportedObject = {
    login: login,
    logout: logout,
    register: register
};

export default exportedObject;
