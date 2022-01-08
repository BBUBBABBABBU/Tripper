<template>
  <div class="container">
    <div class="preview">
      <h3>총 예산 {{ totalAmount }} 원에서</h3>
      <h2>{{ total }} 원 남았습니다!</h2>
      <span id="today">오늘의 지출 금액 {{ today }} 원</span>
      <div class="createBtn" @click="pageLink">
        <div class="border">예산 설정</div>
      </div>
    </div>
    <UpdateBudget id="updateBudget" v-if="updateStat" @update="closeUpdate" />
    <CategoryList id="categoryList" />
  </div>
</template>

<script>
import UpdateBudget from "./UpdateBudget.vue";
import CategoryList from "./CategoryList.vue";
export default {
  components: {
    UpdateBudget,
    CategoryList,
  },
  data: () => ({
    totalAmount: 1000000,
    total: 443530,
    today: 243300,
    updateStat: 0,
  }),
  created() {
    this.total = this.comma(this.total);
    this.totalAmount = this.comma(this.totalAmount);
    this.today = this.comma(this.today);
  },
  methods: {
    //화폐 콤마 찍기
    comma(num) {
      var len, point, str;

      num = num + "";
      point = num.length % 3;
      len = num.length;

      str = num.substring(0, point);
      while (point < len) {
        if (str != "") str += ",";
        str += num.substring(point, point + 3);
        point += 3;
      }
      return str;
    },
    pageLink() {
      this.updateStat = 1;
    },
    closeUpdate(val) {
      this.updateStat = val;
    },
  },
};
</script>

<style lang="scss" scoped>
.container {
  width: 80%;
  margin: 50px auto;
  #today {
    color: #a0acbd;
    font-size: 16px;
  }
}
.createBtn {
  width: 120px;
  height: 60px;
  display: flex;
  float: right;
  position: relative;
  bottom: 40px;
  justify-content: center;
  align-items: center;
  background: #7dbeff;
  border-radius: 10px;
  color: #fff;
  z-index: 1;
  &:hover {
    cursor: pointer;
    background: #f99ec0;
    box-shadow: 1px 1px 0 rgb(0, 0, 0, 0.3);
    position: relative;
    bottom: 41px;
  }
  .border {
    border-radius: 7px;
    width: 80px;
    border: 3px solid #fff;
    padding: 10px 10px;
    font-weight: 700;
    text-align: center;
  }
}

#updateBudget {
  width: 500px;
  position: relative;
  bottom: 40px;
  z-index: 9;
}
#categoryList {
  position: absolute;
  top: 200px;
}
</style>
