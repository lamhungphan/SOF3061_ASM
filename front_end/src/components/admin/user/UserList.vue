<template>
  <div>
    <!-- Bảng người dùng -->
    <table class="table table-striped">
      <thead>
        <tr>
          <th>ID</th>
          <th>Tên Người Dùng</th>
          <th>Email</th>
          <th>Số Điện Thoại</th>
          <th>Vai Trò</th>
          <th>Giới tính</th>
          <th>Số Đơn Hàng</th>
          <th>Số Tiền</th>
          <th>Hành Động</th>
          <th>Hình Ảnh</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(user) in userList" :key="user.id">
          <td>{{ user.id }}</td>
          <td>{{ user.fullname }}</td>
          <td>{{ user.email }}</td>
          <td>{{ user.phone }}</td>
          <td>{{ user.role === 'ADMIN' ? 'Quản trị viên' : 'Nhân viên' }}</td>
          <td>{{ user.gender === 'MALE' ? 'Nam' : 'Nữ' }}</td>
          <td>{{ user.totalOrders }}</td>
          <td>{{ user.totalPaid }} đ</td>
          <td>
            <button class="btn btn-warning btn-sm me-2" @click="editUser(user)">
              Sửa
            </button>
            <button class="btn btn-danger btn-sm" @click="deleteUser(user)">
              Xóa
            </button>
          </td>
          <td>
            <img :src="user.avatar" alt="Hình Ảnh Người Dùng" class="img-thumbnail"
              style="width: 50px; height: 50px" />
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Phân trang -->
    <div class="d-flex justify-content-center mt-3">
      <button class="btn btn-outline-primary mx-1" @click="changePage(currentPage - 1)" :disabled="currentPage === 1">
        Previous
      </button>
      <span class="mx-2">Page {{ currentPage }} / {{ totalPages }}</span>
      <button class="btn btn-outline-primary mx-1" @click="changePage(currentPage + 1)" :disabled="currentPage >= totalPages">
        Next
      </button>
    </div>
  </div>
</template>

<script setup>
// Nhận danh sách người dùng từ component cha
const props = defineProps({
  userList: Array, // Danh sách người dùng truyền vào từ cha
  currentPage: Number,
  totalPages: Number,
});

// Emit để gửi dữ liệu về component cha
const emit = defineEmits(["editUser", "deleteUser", "changePage"]);

// Xử lý khi nhấn "Sửa"
const editUser = (user) => {
  emit("editUser", user);
};

// Xử lý khi nhấn "Xóa"
const deleteUser = (user) => {
  emit("deleteUser", user.id);
};

// Phân trang
const changePage = (newPage) => {
  if (newPage >= 1 && newPage <= props.totalPages) {
    emit("changePage", newPage);
  }
};
</script>

<style scoped>
.text-success {
  color: green;
  font-weight: bold;
}

.text-danger {
  color: red;
  font-weight: bold;
}
</style>
