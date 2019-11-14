import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import store from "../store";

Vue.use(VueRouter);

function authenticated() {
  const token = localStorage.getItem("auth_token");
  if (!token) return false;

  store.state.token = token;
  return true;
}

const routes = [
  {
    path: "/",
    component: Home,
    beforeEnter: (_to, _from, next) => {
      if (!authenticated()) {
        return next({ name: "login" });
      }
      next();
    },
    children: [
      {
        path: "timeline",
        name: "timeline",
        component: () =>
          import(
            /* webpackChunkName: "timeline" */ "../views/home/Timeline.vue"
          )
      },
      {
        path: "my-account",
        name: "my-account",
        component: () =>
          import(
            /* webpackChunkName: "my-account" */ "../views/home/MyAccount.vue"
          )
      },
      {
        path: "my-problems",
        name: "my-problems",
        component: () =>
          import(
            /* webpackChunkName: "my-problems" */ "../views/home/MyProblems.vue"
          )
      },
      {
        path: "people",
        name: "people",
        component: () =>
          import(/* webpackChunkName: "people" */ "../views/home/People.vue")
      },
      {
        path: "problem",
        name: "problem",
        component: () =>
          import(/* webpackChunkName: "problem" */ "../views/home/Problem.vue")
      },
      {
        path: "ranks",
        name: "ranks",
        component: () =>
          import(/* webpackChunkName: "ranks" */ "../views/home/Ranks.vue")
      },
      {
        path: "",
        name: "home",
        redirect: { name: "timeline" }
      }
    ]
  },
  {
    path: "/account",
    name: "account",
    component: () =>
      import(/* webpackChunkName: "account" */ "../views/Account.vue"),
    children: [
      {
        path: "/login",
        name: "login",
        component: () =>
          import(/* webpackChunkName: "login" */ "../views/account/Login.vue"),
        props: true
      },
      {
        path: "/register",
        name: "register",
        component: () =>
          import(
            /* webpackChunkName: "register" */ "../views/account/Register.vue"
          )
      }
    ]
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
  linkActiveClass: "active",
  linkExactActiveClass: "exact-active"
});

export default router;
