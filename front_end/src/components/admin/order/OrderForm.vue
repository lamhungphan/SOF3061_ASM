<template>
    <form @submit.prevent="submitForm">
      <div class="row">
        <!-- Thông tin đơn hàng chỉ hiển thị, không cho chỉnh sửa -->
        <div class="col-md-6 mb-3">
          <label class="form-label">Tên Khách Hàng</label>
          <input :value="order.rFirstname" type="text" class="form-control" disabled />
        </div>
        <div class="col-md-6 mb-3">
          <label class="form-label">Tổng Tiền</label>
          <input :value="order.total" type="number" class="form-control" disabled />
        </div>
        <div class="col-md-6 mb-3">
          <label class="form-label">Ngày Đặt Hàng</label>
          <input :value="order.orderDate" type="date" class="form-control" disabled />
        </div>
  
        <!-- Chỉ cho phép chỉnh sửa trạng thái -->
        <div class="col-md-6 mb-3">
          <label for="orderStatus" class="form-label">Trạng Thái</label>
          <select v-model="orderData.status" class="form-control">
            <option value="PENDING">Đang xử lý</option>
            <option value="COMPLETED">Hoàn thành</option>
            <option value="CANCELLED">Đã hủy</option>
          </select>
        </div>
        <div class="col-md-6 mb-3">
          <label for="paymentStatus" class="form-label">Thanh Toán</label>
          <select v-model="orderData.paymentStatus" class="form-control">
            <option value="UNPAID">Chưa thanh toán</option>
            <option value="PAID">Đã thanh toán</option>
          </select>
        </div>
        <div class="col-md-6 mb-3">
          <label for="deliveryStatus" class="form-label">Giao Hàng</label>
          <select v-model="orderData.deliveryStatus" class="form-control">
            <option value="PENDING">Chưa giao</option>
            <option value="SHIPPED">Đã giao</option>
            <option value="DELIVERED">Giao thành công</option>
          </select>
        </div>
      </div>
      <button type="submit" class="btn btn-primary me-2">Cập nhật</button>
    </form>
  </template>
  
  <script setup>
  import { ref, watch } from "vue";
  
  const props = defineProps({
    order: Object
  });
  const emit = defineEmits(["update"]);
  
  // Sao chép dữ liệu từ props sang orderData
  const orderData = ref({ ...props.order });
  
  // Khi order prop thay đổi, cập nhật lại orderData
  watch(() => props.order, (newOrder) => {
    orderData.value = { ...newOrder };
  }, { deep: true });
  
  // Gửi dữ liệu đã cập nhật đến trang cha
  const submitForm = () => emit("update", { ...orderData.value });
  </script>
  