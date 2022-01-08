import Vue from "vue";
import App from "./App.vue";
import router from "@/router/index";
import store from "@/store/index";
import DatePicker from "v-calendar/lib/components/date-picker.umd";
import DragItDude from "vue-drag-it-dude";
import VueDaumPostcode from "vue-daum-postcode";

import { formatDate } from "@/utils/filters";

Vue.filter("formatDate", formatDate);
Vue.config.productionTip = false;

new Vue({
  render: (h) => h(App),
  router: router,
  store: store,
}).$mount("#app");

Vue.component("date-picker", DatePicker);
Vue.component("vue-drag-it-dude", DragItDude);
Vue.component("vue-daum-postcode", VueDaumPostcode);
