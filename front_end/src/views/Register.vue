<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const username = ref("");
const email = ref("");
const password = ref("");
const confirmPassword = ref("");
const errorMessage = ref("");

const register = () => {
  if (password.value !== confirmPassword.value) {
    errorMessage.value = "Mật khẩu nhập lại không khớp!";
    return;
  }

  // Giả lập đăng ký thành công
  alert("Đăng ký thành công!");
  router.push("/login"); // Chuyển hướng đến trang đăng nhập
};
</script>

<template>
  <div class="register-container">
    <h2>Đăng ký</h2>
    <form @submit.prevent="register">
      <input v-model="username" type="text" placeholder="Tên người dùng" required />
      <input v-model="email" type="email" placeholder="Email" required />
      <input v-model="password" type="password" placeholder="Mật khẩu" required />
      <input v-model="confirmPassword" type="password" placeholder="Nhập lại mật khẩu" required />
      <button type="submit">Đăng ký</button>
    </form>
    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
  </div>
</template>

<style scoped>
.register-container {
  width: 300px;
  margin: auto;
  text-align: center;
}
input {
  display: block;
  width: 100%;
  margin: 10px 0;
  padding: 8px;
}
button {
  width: 100%;
  padding: 8px;
  background: green;
  color: white;
  border: none;
  cursor: pointer;
}
.error {
  color: red;
  margin-top: 10px;
}
</style>
