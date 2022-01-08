<template>
  <div class="container">
    <div class="category">
      <div class="menu">
        <div>
          <i class="icon fas fa-bed"></i>
          숙소
        </div>
        <i class="down fas fa-angle-down"></i>
      </div>
      <div class="berth">
        <CategoryItem />
      </div>
    </div>
    <div class="meal category">
      <div class="menu">
        <div>
          <i class="icon fas fa-utensils"></i>
          식비
        </div>
        <i class="down fas fa-angle-down"></i>
      </div>
      <div class="meal"></div>
    </div>
    <div class="category">
      <div class="menu">
        <div>
          <i class="icon fas fa-bus"></i>
          교통
        </div>
        <i class="down fas fa-angle-down"></i>
      </div>
      <div class="traffic"></div>
    </div>
    <div class="category">
      <div class="menu">
        <div>
          <i class="icon fab fa-gratipay"></i>
          체험
        </div>
        <i class="down fas fa-angle-down"></i>
      </div>
      <div class="experience"></div>
    </div>
    <div class="category">
      <div class="menu">
        <div>
          <i class="icon fas fa-plus"></i>
          기타
        </div>
        <i class="down fas fa-angle-down"></i>
      </div>
      <div class="etc"></div>
    </div>
  </div>
</template>

<script>
import CategoryItem from "./CategoryItem.vue";
export default {
  components: {
    CategoryItem,
  },
};
</script>

<style lang="scss" scoped>
.container {
  width: 97%;
  .category {
    background: #eee;
    min-height: 70px;
    border-radius: 20px;
    padding: 0px 20px 15px 20px;
    z-index: 2;
    margin: 0 auto 20px auto;
    .menu {
      i {
        margin-right: 20px;
      }
      display: flex;
      align-items: center;
      justify-content: space-between;
      font-size: 1.5rem;
      height: 70px;
      color: #3c4856;
      width: 97%;
      .down {
        text-align: right;
        width: 20px;
      }
      &:hover {
        cursor: pointer;
      }
    }
  }
}
</style>
