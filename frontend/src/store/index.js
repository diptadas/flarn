import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    globalButton: "login",
    username: "",
    token: ""
  },
  mutations: {
    SET_GLOBAL_BUTTON(state, newState) {
      state.globalButton = newState;
    },
    SET_AUTH(state, auth) {
      state.username = auth.username;
      state.token = auth.token;
    },
    LOGOUT(state) {
      state.username = "";
      state.token = "";
    }
  },
  actions: {},
  modules: {}
});
