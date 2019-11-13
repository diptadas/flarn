import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

Vue.config.productionTip = false

// Boostrap JS Files
import jQuery from "jquery";
window.jQuery = window.$ = jQuery
import Popper from 'popper.js'
window.Popper = Popper
import 'bootstrap'
import Headroom from "headroom.js";
window.Headroom = Headroom;
require('./assets/js/argon.js')

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
