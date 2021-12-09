<template>
  <div>
    <form class="form" @submit.prevent="submitForm">
      <div>
        <input
          type="text"
          id="id"
          placeholder="아이디"
          v-model="userid"
        /><br />
        <input
          type="password"
          id="password"
          placeholder="비밀번호"
          v-model="password"
        /><br />
        <button class="btn" type="submit">로그인하기</button>
      </div>
      <hr class="hr-login" />
      <div class="find-idpassword">
        <span>아이디, 비밀번호 찾기</span> |
        <span>회원가입</span>
      </div>
    </form>
  </div>
</template>

<script>
import { loginUser } from "@/api/auth.js";

export default {
  data() {
    return {
      userid: "",
      password: "",
    };
  },
  methods: {
    async submitForm() {
      const formData = {
        memId: this.userid,
        password: this.password,
      };
      try {
        const { data } = await loginUser(formData);
        this.$store.dispatch("setToken", data.token);
        this.$store.dispatch("loginSuccess", data.memId);
        this.$router.push("/community");
      } catch (error) {
        console.log(error);
      }
    },
  },
  computed: {},
};
</script>

<style scoped>
.hr-login {
  width: 480px;
  opacity: 0.5;
}
.find-idpassword {
  color: grey;
  opacity: 0.6;
}
span:hover {
  opacity: 1;
}
</style>
