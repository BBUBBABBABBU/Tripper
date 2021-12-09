<template>
  <div></div>
</template>

<script>
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

export default {
  data() {
    return {
      memid: "",
      message: "",
      recvList: [],
    };
  },
  created() {
    this.connect();
  },
  methods: {
    connect() {},
    send() {},
  },
};
</script>

<style></style>
