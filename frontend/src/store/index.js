import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    globalButton: "login"
  },
  mutations: {
    SET_GLOBAL_BUTTON(state, newState) {
      state.globalButton = newState;
    }
  },
  actions: {},
  modules: {}
});
