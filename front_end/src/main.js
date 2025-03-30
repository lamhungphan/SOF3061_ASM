import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia';
import { BootstrapVue3 } from 'bootstrap-vue-3';

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import 'bootstrap-icons/font/bootstrap-icons.css'

const app = createApp(App)
app.use(createPinia()); 
app.use(BootstrapVue3);
app.use(router)
app.mount('#app')
