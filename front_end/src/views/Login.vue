<template>
  <div class="login-container">
    <h2 class="mb-4"></h2>

    <form @submit.prevent="login">
      <div class="mb-3">
        <input v-model="username" type="text" class="form-control" placeholder="Tên người dùng" required />
      </div>
      <div class="mb-3">
        <input v-model="password" type="password" class="form-control" placeholder="Mật khẩu" required />
      </div>
      <button type="submit" class="btn btn-warning w-100" :disabled="store.loading">
        {{ store.loading ? "Đang đăng nhập..." : "Đăng nhập" }}
      </button>
    </form>

    <p v-if="store.isAuthenticated">
      Đã đăng nhập với {{ store.user.username }}.
      <a href="#" @click.prevent="router.push('/')">Về trang chủ</a>
    </p>
  </div>
</template>

<script setup>
import { useLoginStore } from "@/store/loginStore";
import { ref } from "vue";
import { useRouter } from "vue-router";
import Swal from 'sweetalert2';


const store = useLoginStore();
const router = useRouter();
const username = ref("");
const password = ref("");


async function login() {
  const success = await store.login(username.value, password.value);

  if (success) {
    await Swal.fire({
      icon: 'success',
      title: 'Đăng nhập thành công!',
      text: `Chào mừng, ${store.user?.username || "bạn"}!`,
      showConfirmButton: false,
      timer: 1500,
    });

    if (store.canViewManagerDashboard) {
      router.push("/admin");
    } else {
      router.push("/");
    }
  } else {
    Swal.fire({
      icon: 'error',
      title: 'Đăng nhập thất bại',
      text: store.error || 'Vui lòng kiểm tra lại tài khoản!',
    });
  }
}
</script>

<style scoped>
.login-container {
  width: 100%;
  max-width: 400px;
  margin: 50px auto;
  padding: 30px;
  border-radius: 8px;
  background-color: #f8f9fa;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h2 {
  font-size: 24px;
  font-weight: bold;
  color: #333;
}

.form-control {
  border-radius: 8px;
  padding: 10px;
}

button {
  padding: 10px;
  background-color: #ffc107;
  color: white;
  border: none;
  border-radius: 8px;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.mb-3 {
  margin-bottom: 1.5rem;
}

a {
  color: #007bff;
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}
</style>
