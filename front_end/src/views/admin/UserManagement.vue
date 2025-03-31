<template>
  <div>
  <h3 class="text-center mb-4">Quản Lý Đăng Ký Người Dùng</h3>
  
  <!-- Component Form nhập thông tin người dùng -->
  <UserForm :user="nguoiDungMoi" @update="capNhatNguoiDung" @submit="themNguoiDung" @reset="datLaiForm"
    @uploadAvatar="handleUploadAvatar" />
  
  <!-- Tìm kiếm Người Dùng -->
  <div class="mb-3">
    <input type="text" class="form-control" placeholder="Tìm kiếm người dùng theo tên hoặc số điện thoại..."
      v-model="tuKhoaTimKiem" />
  </div>
  
  <!-- Component Bảng danh sách người dùng -->
  <UserList :danhSachNguoiDung="users" @editUser="chinhSuaNguoiDung" @deleteUser="xoaNguoiDung" />
  
  <div class="d-flex justify-content-center mt-3">
    <button class="btn btn-outline-primary mx-1" @click="changePage(currentPage - 1)" :disabled="currentPage === 1">
      Trước
    </button>
    <span class="mx-2">Trang {{ currentPage }} / {{ totalPages }}</span>
    <button class="btn btn-outline-primary mx-1" @click="changePage(currentPage + 1)"
      :disabled="currentPage >= totalPages">
      Sau
    </button>
  </div>
</div>
</template>
<script setup>
import { ref, onMounted } from "vue";
import { useUsers } from "@/store/admin/UserStore";
import { useOrders } from "@/store/admin/OrderStore";
import UserForm from "@/components/admin/user/UserForm.vue";
import UserList from "@/components/admin/user/UserList.vue";

const userStore = useUsers();
const orderStore = useOrders();

const nguoiDungMoi = ref({
  email: "",
  avatar: null,
});

const hinhAnhNguoiDung = ref(null);
const tuKhoaTimKiem = ref("");
const currentPage = ref(1);
const totalPages = ref(1);
const users = ref([])



const themNguoiDung = async (nguoiDung) => {
  const result = await userStore.createUsers(nguoiDung);
  if (result.success) {
    await loadUser();
    datLaiForm();
  } else {
    alert(result.message);
  }
};

const capNhatNguoiDung = async (nguoiDung) => {
  await userStore.updateUser(nguoiDung.id, nguoiDung);
  await loadUser();
};

const chinhSuaNguoiDung = (nguoiDung) => {
  nguoiDungMoi.value = { ...nguoiDung };
};

const xoaNguoiDung = async (nguoiDung) => {
  await userStore.deleteUser(nguoiDung.id);
  await loadUser();
};

const handleUploadAvatar = async (id, file) => {
  if (!id || !file) {
    console.warn("Thiếu ID hoặc file khi upload avatar.");
    return;
  }
  try {
    await userStore.updateAvatar(id, file);
  } catch (error) {
    console.error("Lỗi khi upload avatar:", error);
  }
  await loadUser();
  datLaiForm();
};

const datLaiForm = () => {
  nguoiDungMoi.value = {
    tenNguoiDung: "",
    matKhau: "",
    email: "",
    soDienThoai: "",
    soDonHang: 0,
    tongSoTien: 0,
  };
  hinhAnhNguoiDung.value = null;
};

const changePage = async (newPage) => {
  if (newPage >= 1 && newPage <= totalPages.value) {
    currentPage.value = newPage;
    await loadUser();
  }
};

const loadUser = async () => {
  const success = await userStore.getUsers(currentPage.value);
  if (success) {
    totalPages.value = userStore.totalPages;

    const updatedUsers = await Promise.all(
      userStore.users.map(async (user) => {
        const orders = await orderStore.fetchAllOrdersByUserId(user.id);
        return {
          ...user,
          totalOrders: orders ? orders.length : 0,
          totalPaid: orders ? orders.reduce((acc, order) => acc + order.total, 0) : 0,
        };
      })
    );
    userStore.setUsers(updatedUsers);
    users.value = updatedUsers;
  }
};

onMounted(async () => {
  await loadUser();
});
</script>
