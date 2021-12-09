<template>
  <li class="board-column">
    <div class="board-item">
      <div class="title-box">
        <span class="title">{{ boardItem.title }}</span>
        <span class="destination"> 목적지 : {{ boardItem.destination }}</span>
        <span class="date"
          >{{ boardItem.startDate | formatDate }} -
          {{ boardItem.endDate | formatDate }}</span
        >
        <span class="recruitment">인원 수 : {{ boardItem.recrutiment }}</span>
      </div>
      <div class="content">
        {{ boardItem.content }}
      </div>
      <button @click="boardInfo">자세히</button>
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
        path: `/board/${this.boardItem.id}`,
        query: { boardItem: this.boardItem },
      });
    },
  },
};
</script>

<style></style>
