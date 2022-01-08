<template>
  <drag-it-dude class="form" @submit.prevent="submitForm">
    <div class="els">
      <h2>스케줄 추가</h2>
      <input
        class="elInput"
        type="text"
        id="name"
        placeholder="일정명"
        v-model="list.name"
      />
      <date-picker
        class="elPicker"
        v-model="list.start_time"
        type="datetime"
        placeholder="시작시간"
      ></date-picker>
      <date-picker
        class="elPicker"
        v-model="list.end_time"
        type="datetime"
        placeholder="종료시간"
      ></date-picker>
      <!-- <textarea
        class="elArea elInput"
        type=""
        id="memo"
        placeholder="자유롭게 내용을 작성해보세요 :)"
        v-model="list.content"
      /> -->
      <button class="btn" type="submit" @click="create">생성하기</button>
    </div>
  </drag-it-dude>
</template>

<script>
import DragItDude from "vue-drag-it-dude";
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/index.css";
import { createTrip } from "@/api/trip.js";
export default {
  data: () => ({
    list: {
      name: "",
      start_time: "",
      end_time: "",
    },
  }),
  components: {
    DragItDude,
    DatePicker,
  },
  methods: {
    async create() {
      //데이터 전송
      const tripData = {
        name: this.list.name,
        start_time: this.list.start_time,
        end_time: this.list.end_time,
      };
      try {
        await createTrip(tripData);
      } catch (err) {
        console.log(err);
      }
      this.$emit("changeCreateStat", false);
    },
  },
};
</script>

<style lang="scss" scoped>
.form {
  height: 480px;
  z-index: 4;
  background: #d8f3ff;
  border-radius: 10px;
  border: 3px solid #fff;
  box-shadow: 0 20px 20px rgba(0, 0, 0, 0.08);
}
h2 {
  text-align: center;
  color: #59a6e9;
  text-shadow: 2px 2px 2px #fff;
}

.els {
  width: 400px;
  margin: 0 auto;

  .elInput {
    display: block;
    width: 376px;
    border-radius: 25px;
    border: #d8f3ff 2px solid;
    &:focus {
      border-color: #59a6e9;
      outline: none;
    }
  }
  .elArea {
    margin-top: 20px;
    line-height: 3;
    border: none;
    resize: none;
    border-radius: 25px;
    &:focus {
      border-color: #59a6e9;
      outline: none;
    }
  }
  .elPicker {
    height: 50px;
    width: 100%;
  }
}

.btn {
  color: #fff;
  width: 400px;
  height: 45px;
  font-size: 17px;
  border-radius: 25px;
  left: 0;
  right: 0;
  padding: 8px 12px;
  margin: 20px auto;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.1);
  &:hover {
    cursor: pointer;
    box-shadow: 1px 1px 0 rgb(0, 0, 0, 0.3);
    position: relative;
    top: 1px;
  }
}

.mx-datepicker-range {
  display: block;
  width: 400px;
  height: 30px;
}
</style>
