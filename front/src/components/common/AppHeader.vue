<template>
  <header>
    <div class="logo-space">
      <router-link v-if="!this.$store.state.loginSuccess" to="/" class="logo"
        ><span>TRIPPER</span></router-link
      >
      <router-link v-else to="/community" class="logo"
        ><span>TRIPPER</span></router-link
      >
    </div>
    <div class="menu-bar">
      <div class="menu-guest" v-if="!this.$store.state.loginSuccess">
        <router-link to="/auth"> 로그인</router-link> |
        <router-link to="/signup"> 회원가입</router-link>
      </div>
      <div class="menu-client" v-else>
        <router-link to="/community"> 커뮤니티</router-link> |
        <router-link to="/findroom"> 호텔</router-link>
      </div>
    </div>
  </header>
</template>

<script>
export default {};
</script>

<style scoped>
header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 30px;
  background-color: #7dbeff;
}

.logo {
  color: white;
  font-size: 30px;
  font-weight: 800;
}
.menu-bar {
  color: white;
  font-size: 15px;
  font-weight: 400;
}
.menu-bar a {
  color: white;
  font-size: 15px;
  font-weight: 400;
  text-decoration: none;
}
</style>
