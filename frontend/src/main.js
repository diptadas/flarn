import Vue from "vue";
import axios from "axios";
import Hashids from "hashids";
import AsyncComputed from "vue-async-computed";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import Empty from "./views/utils/Empty";
// Boostrap JS Files
import jQuery from "jquery";
import Popper from "popper.js";
import "bootstrap";
import Headroom from "headroom.js";

Vue.config.productionTip = false;

window.jQuery = window.$ = jQuery;

window.Popper = Popper;

window.Headroom = Headroom;
require("./assets/js/argon.js");

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

function errorMessage(error) {
    let mess = "";
    if (error.response) {

      if(error.response.data.error) {
        return error.response.data.error;
      }

      if(error.response.data && error.response.data.errors.length) {
        return error.response.data.errors[0].message
      }

        const status = error.response.status;
        console.log(status);
        if (status < 500) {
            if (status === 403) {
                mess = "User with username seems to exit";
            } else {
                mess = "We are having problems connecting to the server";
            }
        } else if (status < 600) {
            mess = "Our server seems to be down.. Hold on tight!";
        }
    } else if (error.request) {
        mess = "There seems to be a problem with our servers...";
    } else {
        mess = "Unknown error occurred";
    }
    return mess;
}

Vue.prototype.$http = _axios;

// Hashing URLS for display
Vue.prototype.$hash = new Hashids();

Vue.use(AsyncComputed);

Vue.mixin({
    data() {
        return {
            publicPath: process.env.BASE_URL
        };
    },
    components: {
        Empty
    },
    methods: {
        errorMessage: errorMessage
    }
});

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount("#app");
