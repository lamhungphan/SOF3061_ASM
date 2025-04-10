<template>
  <div class="container mt-5">
    <h2 class="text-center mb-4">📋 Danh sách đơn hàng</h2>

    <div v-if="orders.length">
      <ul class="list-group">
        <div
          v-for="order in orders"
          :key="order.id"
          class="d-flex justify-content-center"
        >
          <li class="list-group-item mb-2 p-3" style="width: 50%">
            <div class="d-flex justify-content-between">
              <div>
                <p class="mb-1"><strong>Mã:</strong> {{ order.id }}</p>
                <p class="mb-1">
                  <strong>Tổng:</strong> {{ formatPrice(order.totalPrice) }}
                </p>
              </div>
              <div>
                <p class="mb-1">
                  <strong>Trạng thái:</strong> {{ order.status }}
                </p>
                <p class="mb-1">
                  <strong>Ngày:</strong> {{ formatDate(order.orderDate) }}
                </p>
              </div>
            </div>
            <div class="text-end mt-2">
              <button
                class="btn btn-sm btn-primary"
                @click="viewOrderDetails(order.id)"
              >
                Xem chi tiết
              </button>
            </div>
          </li>
        </div>
      </ul>

    </div>
    <p v-else class="text-center text-muted">Không có đơn hàng nào.</p>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useOrderStore } from "@/store/orderStore";
import { useLoginStore } from "@/store/loginStore";
import { onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";

const orderStore = useOrderStore();
const loginStore = useLoginStore();
const router = useRouter();
const route = useRoute();

const orders = orderStore.orders;

onMounted(async () => {
  const userId = route.params.id;
  await orderStore.fetchOrdersByUser(userId);
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