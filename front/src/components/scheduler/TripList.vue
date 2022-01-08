<template>
  <div class="container" @click="pageLink">
    <div class="tripList" v-for="(trip, index) in trips" :key="index">
      <div class="border">
        <div class="context">
          <h3>{{ trip.title }}</h3>
          <div class="memo">{{ trip.memo }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import store from "@/store/index.js";
import { tripList } from "@/api/trip.js";
export default {
  created() {
    this.fetctTrips;
  },
  data: () => ({
    memId: store.getters["getMemId"],
    trips: "",
  }),
  methods: {
    async fetctTrips() {
      this.trips = await tripList(this.memId);
    },
    pageLink() {
      this.$router.push({ path: "schedule-list" });
      //해당 여행의 id 값과 관련해서 함께 푸시
      //router.push({path:'schedule-list', query:{id:'13234}'});
    },
  },
};
</script>

<style lang="scss" scoped>
.container {
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  overflow-x: none;
}
.tripList {
  margin: 20px 0px 20px 30px;
  border-radius: 25px;
  width: 340px;
  height: 250px;
  background: #7dbeff;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1;
  .border {
    width: 300px;
    height: 210px;
    border-radius: 20px;
    border: 4px solid #fff;
    background: #dae8e6;
    padding: 10px 10px;
    text-align: center;
    background-image: url("C:/Tripper/front/src/assets/jeju.jpg");
    background-size: cover;
  }
  &:hover {
    cursor: pointer;
    box-shadow: 1px 1px 0 rgb(0, 0, 0, 0.3);
    position: relative;
    top: 1px;
    background: #f99ec0;
  }
}
</style>
