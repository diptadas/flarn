import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import store from "../store";

Vue.use(VueRouter);

function authenticated() {
  const token = localStorage.getItem("auth_token");
  if (!token) return false;

  const name = localStorage.getItem("username");
  const id = localStorage.getItem("user_id");

  store.state.token = token;
  store.state.userId = id;
  store.state.username = name;

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
        component: () => import("../views/home/Timeline.vue")
      },
      {
        path: "my-account",
        name: "my-account",
        component: () => import("../views/home/MyAccount.vue")
      },
      {
        path: "people",
        name: "people",
        component: () => import("../views/home/People.vue")
      },
      {
        path: "problems",
        name: "problems",
        component: () => import("../views/home/UserProblem.vue")
      },
      {
        path: "problems/:id",
        name: "problem-detail",
        component: () => import("../views/home/ProblemDetail.vue"),
        props: true
      },
      {
        path: "problems/sessions/:id",
        name: "problem-session",
        component: () => import("../views/home/ProblemSession.vue"),
        props: true
      },
      {
        path: "ranks",
        name: "ranks",
        component: () => import("../views/home/Ranks.vue")
      },
      {
        path: "profile",
        name: "profile",
        component: () => import("../views/home/Profile.vue")
      },
      {
        path: "people/profile/:id",
        name: "user-profile",
        props: true,
        component: () => import("../views/home/UserProfile.vue")
      },
      {
        path: "support",
        name: "support",
        component: () => import("../views/utils/Support.vue")
      },
      {
        path: "manage/problems",
        name: "manage-problems",
        component: () => import("../views/home/ManageProblem.vue")
      },
      {
        path: "manage/problems/:id",
        name: "manage-problems-detail",
        component: () => import("../views/home/ManageProblemDetail.vue"),
        props: true
      },
      {
        path: "manage/problems/edit/:id",
        name: "manage-problem-edit",
        component: () => import("../views/home/EditProblem.vue"),
        props: true
      },
      {
        path: "manage/users",
        name: "manage-users",
        component: () => import("../views/home/ManageUser.vue")
      },
      {
        path: "manage/create/problem",
        name: "create-problem",
        component: () => import("../views/home/CreateProblem.vue")
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
    component: () => import("../views/Account.vue"),
    children: [
      {
        path: "/login",
        name: "login",
        component: () => import("../views/account/Login.vue"),
        props: true
      },
      {
        path: "/register",
        name: "register",
        component: () => import("../views/account/Register.vue")
      },
      {
        path: "/confirm",
        name: "confirm",
        component: () => import("../views/account/Confirm.vue")
      },
      {
        path: "/forgot",
        name: "forgot",
        component: () => import("../views/account/Forgot.vue")
      },
      {
        path: "/recover",
        name: "recover",
        component: () => import("../views/account/Recover.vue")
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
