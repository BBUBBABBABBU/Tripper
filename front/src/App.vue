<template>
  <div id="app">
    <app-Header />
    <router-view />
    <app-footer />
  </div>
</template>

<script>
import AppHeader from "@/components/common/AppHeader";
import AppFooter from "@/components/common/AppFooter";

export default {
  name: "App",
  components: {
    AppHeader,
    AppFooter,
  },
};
</script>

<style>
@import "./css/common.css";
@import "./css/community.css";
@import "./css/auth.css";
</style>
