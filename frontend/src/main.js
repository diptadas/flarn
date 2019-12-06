import Vue from "vue";
import Hashids from "hashids";
import AsyncComputed from "vue-async-computed";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import Empty from "./layouts/utils/Empty";
import underscore from 'underscore';
import axios from "./utils/http";
import {errorMessage} from './utils/helpers';

// Boostrap JS Files
import jQuery from "jquery";
import Popper from "popper.js";
import "bootstrap";
import Headroom from "headroom.js";
// Vue Validation
import {ValidationObserver, ValidationProvider} from "vee-validate";
// Import validation rules
import "./utils/form-validation";

// Register it globally
// main.js or any entry file.
Vue.component("ValidationProvider", ValidationProvider);
Vue.component("ValidationObserver", ValidationObserver);

Vue.config.productionTip = false;

window.jQuery = window.$ = jQuery;

window.Popper = Popper;

window.Headroom = Headroom;
require("./assets/js/argon.js");


Vue.prototype.$http = axios;

// Hashing URLS for display
Vue.prototype.$hash = new Hashids();

Vue.prototype.$_ = underscore;

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

Vue.prototype.$merge = (baseValue, value) => {
    // merge arrays
    if (Array.isArray(baseValue) && Array.isArray(value)) {
        for (let i = 0; i < value.length; i) {
            let val = Vue.prototype.$merge(baseValue[i], value[i]);
            if (val === undefined) continue;
            if (baseValue[i]) Vue.set(baseValue, i, val);
            else baseValue.push(val);
        }
    }
    // merge objects
    else if (
        value &&
        baseValue &&
        typeof value === "object" &&
        typeof object === "object"
    ) {
        for (let key of Object.keys(value)) {
            let val = Vue.prototype.$merge(baseValue[key], value[key]);
            if (val === undefined) continue;
            Vue.set(baseValue, key, val);
        }
    }
    // return value
    else if (value !== undefined) return value;
    else return baseValue;
};

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount("#app");
