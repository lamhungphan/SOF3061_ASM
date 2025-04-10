import { createRouter, createWebHistory } from 'vue-router'
import { useLoginStore } from "@/store/loginStore";

import HomeView from '../views/Home.vue'
import Cart from '@/views/Cart.vue';
import ProductDetail from '@/views/ProductDetail.vue';
import CheckoutPage from "@/views/CheckoutPage.vue";
import Login from '@/views/Login.vue';
import Register from '@/views/Register.vue';
import AdminHome from '@/views/admin/AdminHome.vue';
import OrderPage from '@/views/OrderPage.vue';
import OrderDetailsPage from '@/views/OrderDetailsPage.vue';
import ChangePassword from '@/views/ChangePassword.vue';
import EditProfile from '@/views/EditProfile.vue';


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'home', component: HomeView, },
    { path: '/cart', component: Cart },
    { path: '/product/:id', component: ProductDetail, props: true },
    { path: "/checkout", component: CheckoutPage, meta: { requiresAuth: true } },
    { path: '/login', name: 'login', component: Login },
    { path: '/register', name: 'register', component: Register },
    { path: '/change-password', name: 'change_password', component: ChangePassword },
    { path: '/edit-profile', name: 'edit-profile', component: EditProfile },
    { path: '/admin', name: 'admin', component: AdminHome, meta: { requiresAuth: true, requiresAdmin: true } },
    { path: '/order/user/:id', component: OrderPage },
    { path: '/order-detail/:id', component: OrderDetailsPage },

    { path: '/:pathMatch(.*)*', redirect: '/' },
  ],
})

router.beforeEach((to, from, next) => {
  const loginStore = useLoginStore();

  if (to.meta.requiresAuth && !loginStore.isAuthenticated) {
    next("/login");
    return;
  }

  if (to.meta.requiresAdmin && (!loginStore.isAuthenticated || !loginStore.canViewManagerDashboard)) {
    next("/login");
    return;
  }
  
  next();
});

export default router
