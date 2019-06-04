import axios from 'axios'
axios.defaults.timeout = 5000;
// axios.defaults.withCredentials = true;

let url = 'http://qiao2019.xyz:8080/fangyuan'
const interApi = {
    tagDistrict: `${url}/tag/district`,

    homepage: `${url}/home/homepage`,
    login: `${url}/login/do_login`,
    logout: `${url}/login/do_logout`,
    register: `${url}/register/do_register`,

    search: `${url}/room/search`,
    recommend: `${url}/room/recommend`,
    history: `${url}/room/browser_history`,
    detail: `${url}/room/detail`
}
let tagDistrict = function () {
    return new Promise((resolve, reject) => {
        axios.post(interApi.tagDistrict).then((res) => {
            resolve(res);
        }).catch((err) => {
            reject(err);
        })
    })
}

let homepage = function () {
    return new Promise((resolve, reject) => {
        axios.get(interApi.homepage).then((res) => {
            resolve(res);
        }).catch((err) => {
            reject(err);
        })
    })
}
// 登录注册
let login = function (username, password) {
    return new Promise((resolve, reject) => {
        axios.post(interApi.login, {
            username: username,
            password: password
        }).then((res) => {
            resolve(res);
        }).catch((err) => {
            reject(err);
        })
    })
}
let register = function (username, password) {
    return new Promise((resolve, reject) => {
        axios.post(interApi.register, {
            username: username,
            password: password
        }).then((res) => {
            resolve(res);
        }).catch((err) => {
            reject(err);
        })
    })
}
// 登出
let logout = function (token) {
    console.log(token);
    return new Promise((resolve, reject) => {
        axios.get(interApi.logout, { params: { token: token } }).then((res) => {
            resolve(res);
        }).catch((err) => {
            reject(err);
        })
    })
}





let search = function (token, searchInfo) {
    return new Promise((resolve, reject) => {
        axios.get(interApi.search, { parmas: { token: token, searchInfo: searchInfo } }).then((res) => {
            resolve(res);
        }).catch((err) => {
            reject(err);
        })
    })
}
let recommend = function (token) {
    return new Promise((resolve, reject) => {
        axios.get(interApi.recommend, { params: { token: token } }).then((res) => {
            resolve(res);
        }).catch((err) => {
            reject(err);
        })
    })
}
let history = function (token) {
    return new Promise((resolve, reject) => {
        axios.get(interApi.history, { params: { token: token } }).then((res) => {
            resolve(res);
        }).catch((err) => {
            reject(err);
        })
    })
}

let detail = function (token, id) {
    return new Promise((resolve, reject) => {
        axios.get(interApi.detail, { params: { token: token, roomId: id } }).then((res) => {
            resolve(res);
        }).catch((err) => {
            reject(err);
        })
    })
}
export default {
    tagDistrict,
    homepage,
    login,
    logout,
    register,
    search,
    recommend,
    history,
    detail,
}