import Vue from "vue";
import App from "./App.vue";
import router from "@/router/index";
import store from "@/store/index";
import DatePicker from "v-calendar/lib/components/date-picker.umd";

Vue.component("date-picker", DatePicker);

import DragItDude from "vue-drag-it-dude";
import axios from "axios";

import { formatDate } from "@/utils/filters";

Vue.filter("formatDate", formatDate);
Vue.config.productionTip = false;

new Vue({
  render: (h) => h(App),
  router: router,
  store: store,
}).$mount("#app");

Vue.component("vue-drag-it-dude", DragItDude);
