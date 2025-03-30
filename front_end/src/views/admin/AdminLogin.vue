<template>
  <div class="container d-flex justify-content-center align-items-center vh-100">
    <div class="card p-4 shadow-lg" style="max-width: 400px; width: 100%;">
      <h2 class="text-center mb-4">Admin Login</h2>
      <form @submit.prevent="handleLogin">
        <div class="mb-3">
          <label class="form-label">Email:</label>
          <input type="email" class="form-control" v-model="email" required />
        </div>

        <div class="mb-3">
          <label class="form-label">Password:</label>
          <input type="password" class="form-control" v-model="password" required />
        </div>

        <button class="btn btn-primary w-100" type="submit" :disabled="loginStore.loading">
          {{ loginStore.loading ? "Logging in..." : "Login" }}
        </button>

        <p v-if="loginStore.error" class="text-danger text-center mt-3">
          {{ loginStore.error }}
        </p>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useLoginStore } from "@/stores/LoginStore";

const email = ref("admin@gmail.com");
const password = ref("admin");
const loginStore = useLoginStore();
const router = useRouter();

// 🛑 Kiểm tra nếu đã đăng nhập thì tự động chuyển hướng
// onMounted(() => {
//   const token = localStorage.getItem("token");
//   if (token) {
//     router.push("/admin"); // Nếu có token thì chuyển hướng đến trang admin
//   } else {
//     router.push("/admin");
//   }
// });

// const handleLogin = async () => {
//   const success = await loginStore.login(email.value, password.value);
//   if (success) {
//     router.push("/admin");
//   }
// };
</script>
