<template>
  <div>
    <div class="id-password-find-wrapper">
      <div class="id-password-find-wrapper-wd">
        <form class="id-find-form" @submit.prevent="findId">
          <h3>아이디 찾기</h3>
          <input type="text" placeholder="이름" />
          <input type="text" placeholder="전화번호" />
          <br /><button>아이디 찾기</button>
        </form>
        <hr />
        <form class="password-find-form" @submit.prevent="passwordReplace">
          <h3>비밀번호 찾기</h3>
          <input type="text" placeholder="이름" />
          <input type="text" placeholder="전화번호" />
          <input type="text" placeholder="아이디" />
          <br /><button>비밀번호 재설정</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import {} from "@/api/auth";

export default {
  data() {
    return {
      username: "",
      phone: "",
      memId: "",
      email: "",
    };
  },
  methods: {
    findId() {},
    passwordReplace() {},
  },
};
</script>

<style></style>
