import Vue from "vue";
import axios from "axios";
import Hashids from "hashids";
import AsyncComputed from "vue-async-computed";
import App from "./App.vue";
import router from "./router";
import store from "./store";

Vue.config.productionTip = false;

// Boostrap JS Files
import jQuery from "jquery";
window.jQuery = window.$ = jQuery;
import Popper from "popper.js";
window.Popper = Popper;
import "bootstrap";
import Headroom from "headroom.js";
window.Headroom = Headroom;
require("./assets/js/argon.js");

// create http request handler
const _axios = axios.create({
  baseURL: process.env.VUE_APP_SERVER_URL,
  timeout: 1000
});

_axios.interceptors.request.use(
  function(config) {
    let token = localStorage.getItem("auth_token");
    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`;
    }
    return config;
  },
  function(error) {
    return Promise.reject(error);
  }
);

_axios.interceptors.response.use(
  function(response) {
    return response;
  },
  function(error) {
    if (error.response.status === 401) {
      return router.replace({ name: "login" });
    }

    if (error.response) {
      // The request was made and the server responded with a status code
      // that falls out of the range of 2xx
      console.log(error.response.data);
      console.log(error.response.status);
      console.log(error.response.headers);
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
    return Promise.reject(error);
  }
);

Vue.prototype.$http = _axios;

// Hashing URLS for display
Vue.prototype.$hash = new Hashids();

Vue.use(AsyncComputed);

Vue.mixin({
  data() {
    return {
      publicPath: process.env.BASE_URL
    };
  }
});

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
