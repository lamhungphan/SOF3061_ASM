<template>
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
      <tr v-for="(nguoiDung) in danhSachNguoiDung" :key="nguoiDung.id">
        <td>{{ nguoiDung.id }}</td>
        <td>{{ nguoiDung.fullname }}</td>
        <td>{{ nguoiDung.email }}</td>
        <td>{{ nguoiDung.phone }}</td>
        <td>{{ nguoiDung.role ? 'Quản trị viên': 'Khách hàng'}}</td>
        <td>{{ nguoiDung.gender ? 'Nam': 'Nữ'}}</td>
        <td>{{ nguoiDung.totalOrders }}</td>
        <td>{{ nguoiDung.totalPaid }} đ</td>
        <td>
          <button class="btn btn-warning btn-sm me-2" @click="chinhSuaNguoiDung(nguoiDung)">
            Sửa
          </button>
          <button class="btn btn-danger btn-sm" @click="xoaNguoiDung(nguoiDung)">
            Xóa
          </button>
        </td>
        <td>
          <img :src="nguoiDung.avatar" alt="Hình Ảnh Người Dùng" class="img-thumbnail"
            style="width: 50px; height: 50px" />
        </td>
      </tr>
    </tbody>
  </table>
</template>
  
  <script setup>

// Nhận danh sách người dùng từ component cha
const props = defineProps({
  danhSachNguoiDung: Array, // Danh sách người dùng truyền vào từ cha
});

// Emit để gửi dữ liệu về component cha
const emit = defineEmits(["editUser", "deleteUser"]);


// Xử lý khi nhấn "Sửa"
const chinhSuaNguoiDung = (nguoiDung) => {
  emit("editUser", nguoiDung);
};

// Xử lý khi nhấn "Xóa"
const xoaNguoiDung = (nguoiDung) => {
  emit("deleteUser", nguoiDung.id);
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
  