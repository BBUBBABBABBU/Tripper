<template>
  <div class="update-wrapper">
    <div class="update-board">
      <form class="update-board-form" @submit.prevent="submitForm">
        <h2>여행 메이트 모집글 수정</h2>
        <div class="board-head">
          <div class="title">
            <label for="title"> </label>
            <input type="text" placeholder="제목" v-model="title" />
          </div>
          <div class="destination">
            <label for="destination"> </label>
            <input type="text" placeholder="목적지" v-model="destination" />
          </div>
          <div class="recrutiment">
            <input type="number" placeholder="인원 수" v-model="recrutiment" />
          </div>
          <div class="date">
            <label for="date"> 여행 기간 </label>
            <DatePicker v-model="range" is-range>
              <template v-slot="{ inputValue, inputEvents }">
                <div>
                  <input :value="inputValue.start" v-on="inputEvents.start" />
                  <span> ~ </span>
                  <input :value="inputValue.end" v-on="inputEvents.end" />
                </div>
              </template>
            </DatePicker>
            <hr />
          </div>
        </div>
        <div class="board-content">
          <input type="text" placeholder="글 작성" v-model="content" />
        </div>
        <button>글 수정하기</button>
        <br />
      </form>
    </div>
  </div>
</template>

<script>
import { editPost, infoPost } from "@/api/board.js";
import DatePicker from "v-calendar/lib/components/date-picker.umd";

export default {
  components: {
    DatePicker,
  },
  data() {
    return {
      id: "",
      title: "",
      destination: "",
      recrutiment: 0,
      range: {
        startDate: new Date(),
        endDate: new Date(),
      },
      content: "",
    };
  },
  methods: {
    async loadData() {
      try {
        const { data } = await infoPost(this.$route.params.id);
        this.id = data.id;
        this.title = data.title;
        this.destination = data.destination;
        this.recrutiment = data.recrutiment;
        const start = new Date(data.startDate);
        const end = new Date(data.endDate);
        this.range.startDate = start;
        this.range.endDate = end;
        this.content = data.content;
      } catch (error) {
        console.log("보드 정보 불러오기 에러");
        console.log(error);
      }
    },
    async submitForm() {
      const board = {
        id: this.id,
        title: this.title,
        destination: this.destination,
        recrutiment: this.recrutiment,
        startDate: this.range.startDate,
        endDate: this.range.endDate,
        content: this.content,
      };
      try {
        await editPost(board);
      } catch (error) {
        console.log(error);
      }
    },
  },
  created() {
    this.loadData();
  },
};
</script>

<style></style>
