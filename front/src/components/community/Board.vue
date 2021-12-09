<template>
  <li class="board-column">
    <div class="board-item">
      <div class="title-box">
        <span class="title">{{ boardItem.title }}</span> |
        <span class="destination"> 목적지 : {{ boardItem.destination }}</span> |
        <span class="recruitment">인원 수 : {{ boardItem.recrutiment }}</span>
      </div>
      <button @click="boardInfo">자세히</button>
      <span class="date"
        >{{ boardItem.startDate | formatDate }} ~
        {{ boardItem.endDate | formatDate }}</span
      >
    </div>
  </li>
</template>

<script>
export default {
  props: {
    boardItem: {
      type: Object,
      required: true,
    },
  },
  methods: {
    boardInfo() {
      this.$router.push({
        path: "/boardInfo",
        query: { boardItem: this.boardItem },
      });
    },
  },
};
</script>

<style></style>
