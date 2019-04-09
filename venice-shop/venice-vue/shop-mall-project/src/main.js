import Vue from "vue";
import App from "./App.vue";
import router from "@/router";
import store from "@/store";
import VueCookie from "vue-cookie";
import httpRequest from "@/api/request";
import { isAuth } from '@/utils'

import './assets/css/theme/green/index.css';
import './assets/css/theme/orange/index.css';
import './assets/css/theme/brown/index.css';
import './assets/css/theme/cyan/index.css';
import './assets/css/theme/gray/index.css';
import './assets/css/theme/indigo/index.css';
import './assets/css/theme/pink/index.css';
import './assets/css/theme/purple/index.css';
import './assets/css/theme/red/index.css';
import './assets/css/theme/turquoise/index.css';
import './assets/css/theme/yellow/index.css';
import "@/style/common.scss";

Vue.use(VueCookie);
Vue.config.productionTip = false;

// // 非生产环境, 适配mockjs模拟数据
// if (process.env.NODE_ENV !== "production") {
//   console.log('zx')
//   require("@/mock");
// }

// 挂载全局
Vue.prototype.$http = httpRequest // ajax请求方法
Vue.prototype.isAuth = isAuth     // 权限方法

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");

