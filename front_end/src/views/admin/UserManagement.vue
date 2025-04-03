<template>
  <div>
    
    <UserForm
      :user="newUser"
      @update="updateUser"
      @submit="createUser"
      @reset="resetForm"
      @uploadAvatar="handleUploadAvatar"
    />

    <!-- Tìm kiếm người dùng -->
    <div class="mb-3">
      <input
        type="text"
        class="form-control"
        placeholder="Search users by name or phone number..."
        v-model="searchKeyword"
      />
    </div>

    <!-- Sử dụng component UserList và truyền dữ liệu, sự kiện -->
    <UserList
      :userList="users"
      :currentPage="currentPage"
      :totalPages="totalPages"
      @editUser="editUser"
      @deleteUser="deleteUser"
      @changePage="changePage"
    />

  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useUsers } from "@/store/admin/UserStore";
import UserForm from "@/components/admin/user/UserForm.vue";
import UserList from "@/components/admin/user/UserList.vue";

const userStore = useUsers();

const newUser = ref({
  username: "",
  password: "",
  email: "",
  phone: "",
  role: "CUSTOMER", // Mặc định là khách hàng
  avatar: null,
});

const userAvatar = ref(null);
const searchKeyword = ref("");
const currentPage = ref(userStore.pageNumber + 1); // Đồng bộ currentPage với store
const totalPages = ref(userStore.totalPages);
const users = ref(userStore.users); // Liên kết trực tiếp với store

// Tạo người dùng mới
const createUser = async (user) => {
  const result = await userStore.createUser(user);
  if (result.success) {
    await loadUsers();
    resetForm();
  } else {
    alert(result.message);
  }
};

// Cập nhật người dùng
const updateUser = async (user) => {
  await userStore.updateUser(user.id, user);
  await loadUsers();
};

// Chỉnh sửa người dùng (điền thông tin người dùng vào form)
const editUser = (user) => {
  newUser.value = { ...user };
};

// Xóa người dùng
const deleteUser = async (userId) => {
  await userStore.deleteUser(userId);
  await loadUsers();
};

// Xử lý upload avatar
const handleUploadAvatar = async (id, file) => {
  if (!id || !file) {
    console.warn("Missing ID or file when uploading avatar.");
    return;
  }
  try {
    await userStore.updateAvatar(id, file);
  } catch (error) {
    console.error("Error uploading avatar:", error);
  }
  await loadUsers();
  resetForm();
};

// Reset form
const resetForm = () => {
  newUser.value = {
    username: "",
    password: "",
    email: "",
    phone: "",
    role: "CUSTOMER",
  };
  userAvatar.value = null;
};

// Thay đổi trang (phân trang)
const changePage = async (newPage) => {
  if (newPage >= 1 && newPage <= totalPages.value) {
    currentPage.value = newPage;
    await loadUsers();
  }
};

// Tải danh sách người dùng từ store
const loadUsers = async () => {
  const success = await userStore.getUsers(currentPage.value);
  if (success) {
    totalPages.value = userStore.totalPages;
    users.value = userStore.users;
  }
};

onMounted(async () => {
  await loadUsers();
});
</script>

<style scoped>
/* Add your custom styles here */
</style>
