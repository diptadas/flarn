import axios from "axios";
import store from "../store";
import router from "../router";
import {errorMessage} from './helpers';

// create http request handler
const _axios = axios.create({
    baseURL: process.env.VUE_APP_SERVER_URL,
    timeout: 1000
});

_axios.interceptors.request.use(
    function (config) {
        store.commit("ERROR", "");
        let token = localStorage.getItem("auth_token");
        if (token) {
            config.headers["Authorization"] = `Bearer ${token}`;
        }
        return config;
    },
    function (error) {
        return Promise.reject(error);
    }
);

_axios.interceptors.response.use(
    function (response) {
        return response;
    },
    function (error) {
        if (error.response) {
            if (
                error.response.status === 401 &&
                router.currentRoute.name !== "login"
            ) {
                return router.replace({name: "login"});
            }
        } else if (error.request) {
            // The request was made but no response was received
            // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
            // http.ClientRequest in node.js
            console.log(error.request);
        } else {
            // Something happened in setting up the request that triggered an Error
            console.log("Error", error.message);
        }

        console.log(error.config);

        store.commit("ERROR", errorMessage(error));
        return Promise.reject(error);
    }
);

export default _axios;