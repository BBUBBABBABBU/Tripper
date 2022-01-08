<template>
  <div class="container">
    <div class="border">
      <div id="cancel" @click="close"><i class="fas fa-times"></i></div>
      <div class="menu">
        <div class="deposit" @click="depositActive">입금</div>
        <div class="withdraw" @click="withdrawActive">지출</div>
      </div>
      <div>
        <input id="title" placeholder="제목을 입력하세요" type="text" />
        <input
          v-model="deposit_money"
          type="text"
          placeholder="금액을 입력하세요"
          @input="(e) => (deposit_money = changeNum(e.target.value))"
        /><span class="one">\</span>
      </div>
      <label>카테고리</label>
      <div class="deposit_container">
        <div class="category bed" @click="categoryActive">
          <div class="icon fas fa-bed"></div>
          <div class="label">숙소</div>
        </div>
        <div class="category meal" @click="categoryActive">
          <div class="icon fas fa-utensils"></div>
          <div class="label">식비</div>
        </div>
        <div class="category traffic" @click="categoryActive">
          <div class="icon fas fa-bus"></div>
          <div class="label">교통</div>
        </div>
        <div class="category play" @click="categoryActive">
          <div class="icon fab fa-gratipay"></div>
          <div class="label">체험</div>
        </div>
        <div class="category etc" @click="categoryActive">
          <div class="icon fas fa-plus"></div>
          <div class="label">기타</div>
        </div>
      </div>
      <div id="okay" @click="saveBudget">확인</div>
    </div>
  </div>
</template>

<script>
export default {
  data: () => ({
    stat: 1,
    deposit_money: "",
  }),
  watch: {
    deposit_money() {},
  },
  methods: {
    changeNum: function (value) {
      return (value = this.comma(this.uncomma(value)));
    },
    comma(str) {
      str = String(str);
      return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, "$1,");
    },
    uncomma(str) {
      str = String(str);
      return str.replace(/[^\d]+/g, "");
    },
    depositActive() {
      const deposit = document.querySelector(".deposit");
      const withdraw = document.querySelector(".withdraw");
      deposit.classList.add("active");
      withdraw.classList.remove("active");
    },
    withdrawActive() {
      const deposit = document.querySelector(".deposit");
      const withdraw = document.querySelector(".withdraw");
      deposit.classList.remove("active");
      withdraw.classList.add("active");
    },
    categoryActive(e) {
      const category = document.querySelectorAll(".category");
      category.forEach((el) => {
        el.classList.remove("active");
      });
      e.currentTarget.className += " active";
    },
    close() {
      this.$emit("update", 0);
    },
    saveBudget() {
      //api  연동
      this.$emit("update", 0);
    },
  },
  filters: {
    inputNumberFormat(val) {
      return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
};
</script>

<style lang="scss" scoped>
.container {
  height: 450px;
  background: #7dbeff;
  border-radius: 20px;
  padding: 10px;
  border: 4px solid #e6f4f1;
  .border {
    height: 94%;
    margin: auto auto;
    padding: 10px;
    border-radius: 10px;
    border: 4px solid #fff;

    #cancel {
      position: inherit;
      float: right;
      &:hover {
        cursor: pointer;
      }
    }
    .menu {
      display: flex;
      width: 88%;
      margin: 5px auto;
      div {
        display: flex;
        width: 6rem;
        height: 3rem;
        margin-right: 10px;
        background: rgba(226, 107, 128, 0.5);
        border-radius: 20px;
        align-items: center;
        justify-content: center;
        font-size: 1.1rem;
        font-weight: 700;
        color: #fff;
      }
      .deposit {
        background: rgba(86, 162, 54, 0.5);
      }
      .deposit.active {
        position: relative;
        top: 1px;
        background: rgba(86, 162, 54, 1);
        box-shadow: 0 2px 0 2px rgba(114, 113, 113, 0.3);
      }
      .withdraw.active {
        position: relative;
        top: 1px;
        background: rgba(226, 107, 128, 1);
        box-shadow: 0 2px 0 2px rgba(114, 113, 113, 0.3);
      }
    }
    .one {
      font-size: 1.5rem;
      margin-left: 10px;
      position: relative;
      bottom: 58px;
      left: 410px;
      opacity: 0.7;
    }
    .deposit_container {
      display: flex;
      justify-content: space-around;
      align-items: center;
      width: 84%;
      padding: 0 20px;
      height: 100px;
      bottom: 15px;
      border-radius: 25px;
      border: 1px solid #fff;
      position: relative;
      margin: 0 auto;
      background-color: rgba(238, 238, 238, 0.5);
      .category {
        display: flex;
        position: relative;
        top: 4px;
        flex-direction: column;
        align-items: center;
        align-items: center;
        width: 80px;
        height: 80px;
      }
      .category:hover {
        cursor: pointer;
        position: relative;
        bottom: 1px;
      }
      .icon {
        font-size: 25px;
        text-align: center;
        border: 1x solid grey;
        padding: 10px;
      }
      .label {
        text-align: center;
        font-size: 14px;
      }
    }
    #okay {
      display: flex;
      width: 80%;
      height: 50px;
      border-radius: 30px;
      padding: 0 30px;
      font-size: 20px;
      font-weight: 700;
      align-items: center;
      justify-content: center;
      background: #eee;
      color: #7dbeff;
      margin: 0 auto;
      &:hover {
        cursor: pointer;
      }
    }
  }
}
input {
  display: block;
  width: 80%;
  margin: 20px auto;
  background-color: rgba(238, 238, 238, 0.5);
  color: #3c4856;
  border: 1px solid #fff;
  border-radius: 30px;
  padding: 0px 30px;
  border-bottom: 2px solid #eee;
  height: 40px;
  color: #000;
  font-size: 20px;
  &:focus {
    outline: none;
  }
}
label {
  position: relative;
  bottom: 25px;
  left: 30px;
  color: #fff;
  font-size: 1.2rem;
}
i {
  background-color: white;
  border-radius: 50%;
  border: 1x solid grey;
  padding: 10px;
  position: relative;
  bottom: 1px;
}
.category.active {
  opacity: 1;
  background: #4089c6;
  color: #fff;
  border-radius: 50%;
}
</style>
