<template>
  <div class="container">
    <h2 id="title">내 여행</h2>
    <div class="createBtn" @click="createTrip">
      <div class="border">여행 생성</div>
    </div>
    <TripList />
    <CreateTrip
      class="createTrip"
      v-if="createStat"
      @changeCreateStat="changeCreateStat"
    />
  </div>
</template>

<script>
import TripList from "@/components/scheduler/TripList.vue";
import CreateTrip from "@/components/scheduler/CreateTrip.vue";
import store from "@/store/index.js";

export default {
  components: {
    TripList,
    CreateTrip,
  },
  data: () => ({
    createStat: false,
    memId: store.getters["getMemId"],
  }),
  methods: {
    changeCreateStat(val) {
      this.createStat = val;
    },
    createTrip() {
      this.createStat = true;
    },
  },
};
</script>

<style lang="scss" scoped>
.container {
  position: relative;
  width: 100%;
  height: 100vh;
  overflow: hidden;
  #title {
    text-align: center;
  }
  .createBtn {
    width: 120px;
    height: 60px;
    display: flex;
    position: relative;
    right: 20px;
    bottom: 10px;
    justify-content: center;
    align-items: center;
    background: #7dbeff;
    border-radius: 10px;
    color: #fff;
    float: right;
    z-index: 1;
    &:hover {
      cursor: pointer;
      background: #f99ec0;
      box-shadow: 1px 1px 0 rgb(0, 0, 0, 0.3);
      position: relative;
      bottom: 9px;
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
}
</style>
