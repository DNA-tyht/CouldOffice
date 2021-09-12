import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import ElementPlus from "element-plus";
import "element-plus/lib/theme-chalk/index.css";
import "font-awesome/css/font-awesome.css";

import {postRequest} from "@/network/request";
import {getRequest} from "@/network/request";
import {putRequest} from "@/network/request";
import {deleteRequest} from "@/network/request";
import {downloadRequest} from "@/network/download";
import {initMenu} from "@/network/menus";

const app = createApp(App);
//代替properties
app.config.globalProperties.postRequest = postRequest;
app.config.globalProperties.getRequest = getRequest;
app.config.globalProperties.putRequest = putRequest;
app.config.globalProperties.deleteRequest = deleteRequest;
app.config.globalProperties.downloadRequest = downloadRequest;

//全局前置守卫
router.beforeEach((to, from, next) => {
  if (window.sessionStorage.getItem("tokenStr")) {
    initMenu(router, store);
    if (!window.sessionStorage.getItem("user")) {
      return postRequest("/admin/info").then(resp => {
        if (resp) {
          window.sessionStorage.setItem("user", JSON.stringify(resp));
          next();
        }
      });
    }
  }
  next();
});

app.use(store).use(router).use(ElementPlus);
app.mount('#app');