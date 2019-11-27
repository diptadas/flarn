import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    username: "",
    token: "",
    userId: "",
    currentHomePahe: "Timeline",
    error: {
      state: false,
      text: ""
    }
  },
  mutations: {
    SET_AUTH(state, auth) {
      state.username = auth.username;
      state.token = auth.token;
      state.userId = auth.userId;
    },
    LOGOUT(state) {
      state.username = "";
      state.token = "";
    },
    SET_HOMEPAGE(state, name) {
      state.currentHomePahe = name;
    },
    SET_ERROR(state, mess) {
      state.error.text = mess;
      state.error.state = true;
    },
    HIDE_ERROR(state) {
      state.error.state = false;
    },
    ERROR(state, mess) {
      state.error.text = mess;
      state.error.state = true;
    }
  },
  actions: {},
  modules: {}
});
