<template>
  <div class="register-container">
    <form @submit.prevent="register">
      <input v-model="username" type="text" placeholder="Tên người dùng" required class="form-control" />
      <input v-model="email" type="email" placeholder="Email" required class="form-control" />
      <input v-model="password" type="password" placeholder="Mật khẩu" required class="form-control" />
      <input v-model="confirmPassword" type="password" placeholder="Nhập lại mật khẩu" required class="form-control" />
      <button type="submit" class="btn btn-success w-100">
        Đăng ký
      </button>
    </form>
    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useUsers } from "@/store/admin/UserStore";
import Swal from 'sweetalert2';

const router = useRouter();
const userStore = useUsers();
const username = ref("");
const email = ref("");
const password = ref("");
const confirmPassword = ref("");
const errorMessage = ref("");

const register = async () =>  {
  if (password.value !== confirmPassword.value) {
    errorMessage.value = "Mật khẩu nhập lại không khớp!";
    Swal.fire({
      icon: 'error',
      title: 'Đăng ký thất bại',
      text: errorMessage.value
    });
    return;
  }

  const payload = {
    username: username.value,
    email: email.value,
    password: password.value
  };

  const { success, message } = await userStore.createUser(payload);

  if (success) {
    await Swal.fire({
      icon: 'success',
      title: 'Đăng ký thành công!',
      text: 'Chào mừng bạn đến với hệ thống!',
      showConfirmButton: false,
      timer: 1500
    });
    router.push("/login");
  } else {
    errorMessage.value = message;
    Swal.fire({ icon: 'error', title: 'Đăng ký thất bại', text: message });
  }
};
</script>

<style scoped>
.register-container {
  width: 300px;
  margin: 50px auto;
  text-align: center;
}
input {
  display: block;
  width: 100%;
  margin: 10px 0;
  padding: 8px;
  border-radius: 8px;
  border: 1px solid #ccc;
}
button {
  width: 100%;
  padding: 10px;
  background-color: green;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}
button:hover {
  background-color: darkgreen;
}
.error {
  color: red;
  margin-top: 10px;
}
</style>
