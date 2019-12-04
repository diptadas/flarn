import Vue from "vue";
import Vuex from "vuex";
import axios from "../utils/http";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        username: "",
        token: "",
        fullName: "",
        userId: "",
        error: {
            state: false,
            text: ""
        },
        user: {
            fullName: "Loading...",
            userType: "LEARNER",
            avatarLink: "https://i.ibb.co/kgLYxgp/default-profile.png"
        }
    },
    mutations: {
        SET_AUTH(state, auth) {
            state.username = auth.username;
            state.token = auth.token;
            state.userId = auth.userId;
        },
        SET_USER(state, data) {
            state.user.fullName = data.fullName;
            state.user.userType = data.userType;
            state.user.avatarLink = data.avatarLink;
        },
        SET_USERNAME(state, username) {
            state.username = username;
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
    actions: {
        getCurrentUser(context) {
            console.log('action')
            const url = "users/current";

            axios.get(url)
                .then(res => {
                    context.commit('SET_AUTH', res.data);
                    context.commit('SET_USER', res.data);
                })
                .catch(err => {
                    this.$router.replace({name: 'login'});
                });
        }
    },
    modules: {}
});
