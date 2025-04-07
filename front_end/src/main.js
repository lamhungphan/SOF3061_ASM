import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia';
import axiosPlugin from "../src/axios/plugin/axiosPlugin";

import "bootstrap/dist/css/bootstrap.min.css";
import 'bootstrap-icons/font/bootstrap-icons.css'
import 'bootstrap/dist/js/bootstrap.bundle';
import Vue3Toasity from 'vue3-toastify'
import 'vue3-toastify/dist/index.css'

const app = createApp(App)

app.use(createPinia());
app.use(axiosPlugin)
app.use(router)
app.use(Vue3Toasity, {
    autoClose: 3000,
    position: "top-right"
  })
app.mount('#app')
