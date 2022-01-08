<template>
  <div class="container">
    <div class="item" v-for="item in items" :key="item.item_id">
      <div class="time">{{ item.timeStamp }}</div>
      <div class="content">{{ item.content }}</div>
      <div class="expenditure">{{ item.expenditure }}원</div>
    </div>
  </div>
</template>

<script>
export default {
  data: () => ({
    items: [
      {
        item_id: 1,
        content: "명동칼국수",
        expenditure: 23000,
        timeStamp: "2021-12-23 10:34:00",
        category_id: 2,
      },
      {
        item_id: 2,
        content: "별다방",
        expenditure: 11700,
        timeStamp: "2021-12-23 11:59:00",
        category_id: 2,
      },
    ],
  }),
};
</script>

<style lang="scss" scoped>
.container {
  width: 90%;
}
.item {
  display: flex;
  margin-bottom: 10px;
  border: 4px solid #fff;
  background: rgba(255, 255, 255, 0.5);
  padding: 10px 10px;
  border-radius: 15px;
  justify-content: space-between;
  .content,
  .expenditure {
    font-size: 1.2rem;
  }
}
</style>
