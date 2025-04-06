<template>
  <div class="container mt-5">
    <h2 class="text-center mb-4">📋 Danh sách đơn hàng</h2>
    <div v-if="orders.length">
      <ul class="list-group">
        <li v-for="order in orders" :key="order.id" class="list-group-item mb-3">
          <p><strong>Mã đơn hàng:</strong> {{ order.id }}</p>
          <p><strong>Tổng tiền:</strong> {{ formatPrice(order.totalPrice) }}</p>
          <p><strong>Trạng thái:</strong> {{ order.status }}</p>
          <p><strong>Ngày tạo:</strong> {{ formatDate(order.orderDate) }}</p>
          <button class="btn btn-primary" @click="viewOrderDetails(order.id)">
            Xem chi tiết
          </button>
        </li>
      </ul>
      <!-- Phân trang -->
      <nav class="mt-4">
        <ul class="pagination justify-content-center">
          <li class="page-item" :class="{ disabled: currentPage === 1 }">
            <button class="page-link" @click="fetchOrders(currentPage - 1)">Trước</button>
          </li>
          <li v-for="page in totalPages" :key="page" class="page-item" :class="{ active: currentPage === page }">
            <button class="page-link" @click="fetchOrders(page)">{{ page }}</button>
          </li>
          <li class="page-item" :class="{ disabled: currentPage === totalPages }">
            <button class="page-link" @click="fetchOrders(currentPage + 1)">Sau</button>
          </li>
        </ul>
      </nav>
    </div>
    <p v-else class="text-center text-muted">Không có đơn hàng nào.</p>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useOrderStore } from "@/store/orderStore";
import { onMounted } from "vue";
import { useRouter } from "vue-router";

const orderStore = useOrderStore();
const router = useRouter();
const currentPage = ref(1);
const totalPages = ref(1);

const orders = orderStore.orders;

const fetchOrders = async (page) => {
  try {
    const pageData = await orderStore.fetchOrders({ page, size: 5 });
    currentPage.value = page;
    totalPages.value = pageData.totalPages;
  } catch (error) {
    console.error("Error fetching orders:", error);
  }
};

onMounted(() => {
  fetchOrders(1);
});

const viewOrderDetails = (orderId) => {
  router.push(`/order-detail/${orderId}`);
};

const formatPrice = (price) => {
  return price?.toLocaleString("vi-VN") + "₫";
};

const formatDate = (date) => {
  return date ? new Date(date).toLocaleString("vi-VN") : "Không có";
};
</script>