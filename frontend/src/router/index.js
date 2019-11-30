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
        path: "stories",
        name: "stories",
        component: () => import("../layouts/home/Stories.vue")
      },
      {
        path: "activities",
        name: "activities",
        component: () => import("../layouts/home/Activities.vue")
      },
      {
        path: "people",
        name: "people",
        component: () => import("../layouts/home/People.vue")
      },
      {
        path: "problems",
        name: "problems",
        component: () => import("../layouts/home/UserProblem.vue")
      },
      {
        path: "problems/:id",
        name: "problem-detail",
        component: () => import("../layouts/home/ProblemDetail.vue"),
        props: true
      },
      {
        path: "problems/sessions/:id",
        name: "problem-session",
        component: () => import("../layouts/home/ProblemSession.vue"),
        props: true
      },
      {
        path: "ranks",
        name: "ranks",
        component: () => import("../layouts/home/Ranks.vue")
      },
      {
        path: "profile",
        name: "profile",
        component: () => import("../layouts/home/Profile.vue")
      },
      {
        path: "people/profile/:id",
        name: "user-profile",
        props: true,
        component: () => import("../layouts/home/UserProfile.vue")
      },
      {
        path: "support",
        name: "support",
        component: () => import("../layouts/utils/Support.vue")
      },
      {
        path: "manage/problems",
        name: "manage-problems",
        component: () => import("../layouts/home/ManageProblem.vue")
      },
      {
        path: "manage/problems/:id",
        name: "manage-problems-detail",
        component: () => import("../layouts/home/ManageProblemDetail.vue"),
        props: true
      },
      {
        path: "manage/problems/edit/:id",
        name: "manage-problem-edit",
        component: () => import("../layouts/home/EditProblem.vue"),
        props: true
      },
      {
        path: "manage/users",
        name: "manage-users",
        component: () => import("../layouts/home/ManageUser.vue")
      },
      {
        path: "manage/create/problem",
        name: "create-problem",
        component: () => import("../layouts/home/CreateProblem.vue")
      },
      {
        path: "manage/categories",
        name: "manage-categories",
        component: () => import("../layouts/home/ManageCategory.vue")
      },
      {
        path: "/about",
        name: "about",
        component: () => import("../layouts/utils/About.vue")
      },
      {
        path: "/",
        name: "home",
        redirect: { name: "stories" }
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
        component: () => import("../layouts/account/Login.vue"),
        props: true
      },
      {
        path: "/register",
        name: "register",
        component: () => import("../layouts/account/Register.vue")
      },
      {
        path: "/confirm",
        name: "confirm",
        component: () => import("../layouts/account/Confirm.vue")
      },
      {
        path: "/forgot",
        name: "forgot",
        component: () => import("../layouts/account/Forgot.vue")
      },
      {
        path: "/recover",
        name: "recover",
        component: () => import("../layouts/account/Recover.vue")
      },
      {
        path: "/404",
        name: "404",
        component: () => import("../layouts/utils/404.vue")
      },
      {
        path: "support",
        name: "acc-support",
        component: () => import("../layouts/utils/Support.vue")
      },
      {
        path: "about",
        name: "acc-about",
        component: () => import("../layouts/utils/About.vue")
      }
    ]
  },
  {
    path: "*",
    redirect: { name: "404" }
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
