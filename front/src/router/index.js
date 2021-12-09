import Vue from "vue";
import VueRouter from "vue-router";
import store from "@/store/index";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    redirect: store.state.loginSuccess ? "/main" : "/auth",
  },
  {
    path: "/main",
    name: "main",
    component: () => import("@/views/MainPage.vue"),
  },
  {
    path: "/auth",
    name: "auth",
    component: () => import("@/views/LoginPage.vue"),
  },
  {
    path: "/signup",
    name: "signup",
    component: () => import("@/views/SignupPage.vue"),
  },
  {
    path: "/findroom",
    name: "findroom",
    component: () => import("@/views/FindRoomPage.vue"),
    // meta: {
    //   auth: true,
    // },
  },
  {
    path: "/community",
    name: "community",
    component: () => import("@/views/CommunityPage.vue"),
    meta: {
      auth: true,
    },
  },
  {
    path: "/writeboard",
    component: () => import("@/views/WriteBoardPage.vue"),
    // meta: {
    //   auth: true,
    // },
  },
  {
    path: "/board/:id",
    component: () => import("@/components/community/BoardInfo.vue"),
    meta: {
      auth: true,
    },
  },
  {
    path: "/board/update/:id",
    component: () => import("@/components/community/BoardUpdate.vue"),
  },
  {
    path: "*",
    component: () => import("@/views/NotFoundPage.vue"),
  },
];

const router = new VueRouter({
  mode: "history",
  routes,
});

router.beforeEach((to, from, next) => {
  if (to.meta.auth) {
    store.getters.isLogin ? next() : next("auth");
  } else {
    next();
  }
});

export default router;
