<template>
  <div class="board">
    <h2>{{ boardItem.title }}</h2>
    <h4>목적지 : {{ boardItem.destination }}</h4>
    <div>
      {{ boardItem.startDate | formatDate }} -
      {{ boardItem.endDate | formatDate }}
    </div>
    <div>
      {{ boardItem.content }}
    </div>
    <button @click="addLikeButton">좋아요</button>
    <button @click="subLikeButton">좋아요 취소</button>
    <button @click="updateBoard">글 수정</button>
    <button @click="deleteBoard">글 삭제</button>
  </div>
</template>

<script>
import { addLikes, subLikes, deletePost } from "@/api/board.js";
export default {
  data() {
    return {
      boardItem: this.$route.query.boardItem,
    };
  },
  methods: {
    addLikeButton() {
      const id = this.boardItem.id;
      addLikes(id);
    },
    subLikeButton() {
      const id = this.boardItem.id;
      subLikes(id);
    },
    updateBoard() {
      this.$router.push(`/board/update/${this.boardItem.id}`);
    },
    deleteBoard() {
      deletePost(this.id);
    },
    addHits() {},
  },
  created() {
    this.addHits();
  },
};
</script>

<style></style>
