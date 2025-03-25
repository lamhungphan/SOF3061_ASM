import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/Home.vue'
import Cart from '@/views/Cart.vue';
import ProductDetail from '@/views/ProductDetail.vue';
import CheckoutPage from "@/views/CheckoutPage.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'home', component: HomeView,},
    { path: '/cart', component: Cart },
    { path: '/product/:id', component: ProductDetail, props: true },
    { path: "/checkout", component: CheckoutPage },

  ],
})

export default router
