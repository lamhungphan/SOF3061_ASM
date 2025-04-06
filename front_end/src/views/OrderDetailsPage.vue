<template>
  <div class="order-detail-container container mt-5" v-if="order">
    <h2 class="text-center mb-4">🧾 Chi tiết đơn hàng #{{ order.id }}</h2>

    <div class="info-box card p-3 mb-4">
      <p>
        <strong>Trạng thái:</strong>
        <span :class="statusClass(order.status)">{{ order.status }}</span>
      </p>
      <p><strong>Tổng tiền:</strong> {{ formatPrice(order.totalPrice) }}</p>
      <p><strong>Ngày tạo:</strong> {{ formatDate(order.orderDate) }}</p>
    </div>

    <div v-if="order.orderDetails?.length">
      <h4 class="mt-4 mb-2">📦 Danh sách sản phẩm:</h4>
      <ul class="item-list list-group">
        <li
          v-for="item in order.orderDetails"
          :key="item.id"
          class="list-group-item mb-2"
        >
          <p>
            <strong>Tên sản phẩm:</strong>
            {{ item.product?.name || "Không xác định" }}
          </p>
          <p><strong>Số lượng:</strong> {{ item.quantity }}</p>
          <p><strong>Đơn giá:</strong> {{ formatPrice(item.price) }}</p>
        </li>
      </ul>
    </div>

    <router-link to="/orders" class="btn btn-secondary mt-3"
      >← Quay lại danh sách</router-link
    >
  </div>
  <div v-else class="text-center mt-5">
    <p>Đang tải đơn hàng...</p>
  </div>
</template>

<script setup>
import { computed, watch } from "vue";
import { useOrderStore } from "@/store/orderStore";
import { onMounted } from "vue";
import { useRoute } from "vue-router";

const orderStore = useOrderStore();
const route = useRoute();
const orderId = route.params.id;
const order = computed(() => orderStore.currentOrder);

// onMounted(() => {
//   orderStore.fetchOrderById(orderId);
// });

watch(
  () => route.params.id,
  async (id) => {
    if (id) {
      await orderStore.fetchOrderById(id);
    } else {
      console.warn("⚠️ Không tìm thấy orderId trong route.params");
    }
  },
  { immediate: true }
);

const formatPrice = (price) => {
  return price?.toLocaleString("vi-VN") + "₫";
};

const formatDate = (date) => {
  return date ? new Date(date).toLocaleString("vi-VN") : "Không có";
};

const statusClass = (status) => {
  switch (status) {
    case "PENDING":
      return "status-pending";
    case "COMPLETED":
      return "status-completed";
    case "CANCELLED":
      return "status-cancelled";
    default:
      return "";
  }
};
</script>

<style scoped>
.status-pending {
  color: orange;
}
.status-completed {
  color: green;
}
.status-cancelled {
  color: red;
}
</style>