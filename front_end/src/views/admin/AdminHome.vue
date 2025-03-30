<template>
  <div class="container-fluid">
    <div class="row">
      <!-- Vertical Sidebar -->
      <div class="col-md-2 bg-dark text-light vh-100 p-3 d-flex flex-column">
        <h5><span><i class="fa-solid fa-bars"></i></span> Admin Panel</h5>
        <h6><span><i class="fa-solid fa-user"></i></span> Xin chào:
          <span class="text-warning">{{ loginStore.user?.email || "Admin" }}</span>
        </h6>

        <nav class="nav flex-column nav-pills">
          <a class="nav-link active" data-bs-toggle="pill" href="#tab1"> Dashboard</a>
          <a class="nav-link" data-bs-toggle="pill" href="#tab2"> Đơn hàng</a>
          <a class="nav-link" data-bs-toggle="pill" href="#tab3"> Thành viên</a>
          <a class="nav-link" data-bs-toggle="pill" href="#tab4"> Hàng hoá</a>
          <a class="nav-link" data-bs-toggle="pill" href="#tab5"> Danh Mục</a>
        </nav>

        <!-- 🛑 Nút Đăng xuất -->
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
            <Order/>
          </div>
          <div class="tab-pane fade" id="tab3">
            <UserManagement />
          </div>
          <div class="tab-pane fade" id="tab4">
            <ProductManagement />
          </div>
          <div class="tab-pane fade" id="tab5">
            <CategoryManagement/>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import { useLoginStore } from "@/stores/LoginStore";
import Dashboard from '@/views/Dashboard.vue';
import Order from '@/views/Order.vue';
import UserManagement from '@/views/UserManagement.vue';
import ProductManagement from '@/views/ProductManagement.vue';
import {onMounted} from "vue";
import CategoryManagement from "@/views/CategoryManagement.vue";

const router = useRouter();
const loginStore = useLoginStore();

const handleLogout = () => {
  loginStore.logout();
  router.push("/login"); // 🛑 Chuyển hướng sau khi đăng xuất
};

// 🛑 Kiểm tra nếu đã đăng nhập thì tự động chuyển hướng
onMounted(() => {
  const token = localStorage.getItem("token");
  if (token) {
    router.push("/admin"); // Nếu có token thì chuyển hướng đến trang admin
  } else {
    router.push("/login");
  }
});
</script>
