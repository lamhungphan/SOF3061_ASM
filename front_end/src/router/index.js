import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/Home.vue'
import Cart from '@/views/Cart.vue';
import ProductDetail from '@/views/ProductDetail.vue';
import CheckoutPage from "@/views/CheckoutPage.vue";
import Login from '@/views/Login.vue';
import Register from '@/views/Register.vue';
import AdminHome from '@/views/admin/AdminHome.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'home', component: HomeView, },
    { path: '/cart', component: Cart },
    { path: '/product/:id', component: ProductDetail, props: true },
    { path: "/checkout", component: CheckoutPage, meta: { requiresAuth: true } },
    { path: '/login', name: 'login', component: Login },
    { path: '/register', name: 'register', component: Register },

    { path: '/admin', component: AdminHome, meta: { requiresAuth: true, requiresAdmin: true } },
    { path: '/:pathMatch(.*)*', redirect: '/' },
  ],
})

router.beforeEach((to, from, next) => {
  const role = localStorage.getItem("role");
  
  if (to.meta.requiresAuth && !role) {
      next("/login");
  } else if (to.meta.requiresAdmin && (role !== "manager" && role !== "director")) {
      next("/");
  } else if (to.meta.requiresDirector && role !== "director") {
      next("/admin"); 
  } else {
      next();
  }
});

export default router
