<template>
    <div class="container mt-5">
      <div class="row justify-content-center">
        <div class="col-lg-6">
          <div class="card shadow-lg p-4">
            <h2 class="text-center mb-4 fw-bold">Đổi Mật Khẩu</h2>
            
            <form @submit.prevent="changePassword">
              <!-- Mật khẩu cũ -->
              <div class="mb-3">
                <label for="oldPassword" class="form-label">Mật khẩu cũ</label>
                <input
                  type="password"
                  id="oldPassword"
                  v-model="oldPassword"
                  class="form-control"
                  required
                />
              </div>
  
              <!-- Mật khẩu mới -->
              <div class="mb-3">
                <label for="newPassword" class="form-label">Mật khẩu mới</label>
                <input
                  type="password"
                  id="newPassword"
                  v-model="newPassword"
                  class="form-control"
                  required
                />
              </div>
  
              <!-- Xác nhận mật khẩu mới -->
              <div class="mb-3">
                <label for="confirmPassword" class="form-label">Xác nhận mật khẩu mới</label>
                <input
                  type="password"
                  id="confirmPassword"
                  v-model="confirmPassword"
                  class="form-control"
                  required
                />
              </div>
  
              <!-- Nút gửi -->
              <button type="submit" class="btn btn-primary">Đổi Mật Khẩu</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
import { ref } from "vue";
import { useUsers } from "@/store/admin/UserStore";
import { toast } from "vue3-toastify";

const usersStore = useUsers();

const oldPassword = ref("");
const newPassword = ref("");
const confirmPassword = ref("");

const user = JSON.parse(localStorage.getItem("user"));
const userId = user?.id;

const changePassword = async () => {
  if (newPassword.value !== confirmPassword.value) {
    toast.error("Mật khẩu mới và xác nhận không khớp!");
    return;
  }

  const { success, message } = await usersStore.changePassword(
  oldPassword.value,
  newPassword.value
);

  if (success) {
    toast.success(message);
    oldPassword.value = newPassword.value = confirmPassword.value = "";
  } else {
    toast.error(message);
  }
};
</script>

  
  <style scoped>
  .card {
    border-radius: 12px;
  }
  
  .form-control {
    border-radius: 8px;
  }
  
  .btn-primary {
    background-color: #007bff;
    border: none;
  }
  
  .btn-primary:hover {
    background-color: #0056b3;
  }
  </style>
  