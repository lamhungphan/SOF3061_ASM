<template>
  <div
    class="container-fluid"
    v-if="loginStore.isAuthenticated && loginStore.canViewManagerDashboard"
  >
    <div class="row">
      <!-- Vertical Sidebar -->
      <div class="col-md-2 bg-dark text-light vh-100 p-3 d-flex flex-column">
        <h6>
          <span><i class="fa-solid fa-user"></i></span> Xin chào:
          <span class="text-warning">{{
            loginStore.user?.username || "User"
          }}</span>
        </h6>

        <nav class="nav flex-column nav-pills">
          <a class="nav-link active" data-bs-toggle="pill" href="#tab1"
            >Dashboard</a
          >
          <a class="nav-link" data-bs-toggle="pill" href="#tab2">Đơn hàng</a>
          <a class="nav-link" data-bs-toggle="pill" href="#tab3">Người dùng</a>
          <a class="nav-link" data-bs-toggle="pill" href="#tab4">Sản phẩm</a>
          <a class="nav-link" data-bs-toggle="pill" href="#tab5">Ngành hàng</a>
        </nav>

        <button class="btn btn-primary mt-auto" @click="handleLogout">
          <i class="fa-solid fa-right-from-bracket"></i> Đăng xuất
        </button>
      </div>

      <!-- Content Area -->
      <div class="col-md-10 p-4 bg-dark bg-opacity-10">
        <div class="tab-content">
          <div class="tab-pane fade show active" id="tab1">
            <Dashboard />
          </div>
          <div class="tab-pane fade" id="tab2">
            <Order />
          </div>
          <div class="tab-pane fade" id="tab3">
            <UserManagement />
          </div>
          <div class="tab-pane fade" id="tab4">
            <ProductManagement />
          </div>
          <div class="tab-pane fade" id="tab5">
            <CategoryManagement />
          </div>
        </div>
      </div>
    </div>
  </div>
  <div v-else class="text-center mt-5">
    <h3>Bạn không có quyền truy cập!</h3>
    <button class="btn btn-primary" @click="router.push('/login')">
      Đăng nhập
    </button>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import { useLoginStore } from "@/store/loginStore";
import Dashboard from "@/views/admin/Dashboard.vue";
import Order from "@/views/admin/Order.vue";
import UserManagement from "@/views/admin/UserManagement.vue";
import ProductManagement from "@/views/admin/ProductManagement.vue";
import CategoryManagement from "@/views/admin/CategoryManagement.vue";
import { onMounted } from "vue";

const router = useRouter();
const loginStore = useLoginStore();

onMounted(() => {
  if (!loginStore.isAuthenticated || !loginStore.canViewManagerDashboard) {
    console.log("hello admin");
  }
});

const handleLogout = () => {
  loginStore.logout();
  router.push("/");
};
</script>