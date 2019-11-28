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
    store.commit("ERROR", "");
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
    if (error.response) {
      if (
        error.response.status === 401 &&
        router.currentRoute.name !== "login"
      ) {
        return router.replace({ name: "login" });
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
    if (error.response.status < 500) {
      mess = "We are having problems connecting to the server";
    } else if (error.response.status < 600) {
      mess = "Our server seems to be down.. Hold on tight!";
    }
  } else if (error.request) {
    mess = "There seems to be a problem with our servers...";
  } else {
    mess = "Unknown error occured";
  }
  return mess;
}

_axios.prototype.errorMessage = errorMessage;

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
