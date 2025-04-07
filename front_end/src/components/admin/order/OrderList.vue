<template>
  <div>
    <table class="table table-bordered text-center">
      <thead>
        <tr>
          <th>ID Đơn Hàng</th>
          <th>Khách Hàng</th>
          <th>Trạng Thái</th>
          <th>Giá</th>
          <th>Ngày Tạo</th>
          <th>Hành Động</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="order in orderStore.orders" :key="order.id">
          <td>{{ order.id }}</td>
          <td>{{ order.user.fullName }}</td>
          <td>{{ order.status }}</td>
          <td>{{ order.totalPrice.toLocaleString() }} đ</td>
          <td>{{ new Date(order.orderDate).toLocaleDateString() }}</td>
          <td>
            <button
              type="button"
              @click="selected(order)"
              class="btn btn-sm btn-primary me-1"
            >
              Xem
            </button>
            <button
              v-if="order.status === 'PENDING'"
              type="button"
              @click="confirmOrder(order.id)"
              class="btn btn-sm btn-success me-1"
            >
              Xác Nhận
            </button>
            <button
              v-if="order.status === 'CONFIRMED'"
              type="button"
              @click="shipOrder(order.id)"
              class="btn btn-sm btn-warning me-1"
            >
              Giao Hàng
            </button>
            <button
              v-if="order.status === 'SHIPPED'"
              type="button"
              @click="completeOrder(order.id)"
              class="btn btn-sm btn-success"
            >
              Hoàn Thành
            </button>
            <button
              type="button"
              @click="cancelOrder(order.id)"
              class="btn btn-sm btn-danger ms-1"
            >
              Hủy
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Pagination -->
    <div class="d-flex justify-content-center mt-3" v-if="orderStore.totalPages > 1">
      <button
        class="btn btn-outline-primary me-2"
        :disabled="orderStore.pageNumber === 0"
        @click="changePage(orderStore.pageNumber - 1)"
      >
        ← Trước
      </button>
      <span>Trang {{ orderStore.pageNumber + 1 }} / {{ orderStore.totalPages }}</span>
      <button
        class="btn btn-outline-primary ms-2"
        :disabled="orderStore.pageNumber === orderStore.totalPages - 1"
        @click="changePage(orderStore.pageNumber + 1)"
      >
        Sau →
      </button>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from "vue";
import { useOrders } from "@/store/admin/OrderStore";

const orderStore = useOrders();
const emits = defineEmits(["edit"]);

function selected(order) {
  emits("edit", order);
}

const confirmOrder = async (orderId) => {
  await orderStore.confirmOrder(orderId);
};

const shipOrder = async (orderId) => {
  await orderStore.shipOrder(orderId);
};

const completeOrder = async (orderId) => {
  await orderStore.completeOrder(orderId);
};

const cancelOrder = async (orderId) => {
  await orderStore.cancelOrder(orderId);
};

const changePage = async (newPage) => {
  if (newPage >= 0 && newPage < orderStore.totalPages) {
    await orderStore.fetchOrders(newPage);
  }
};

onMounted(async () => {
  await orderStore.fetchOrders(1);
});
</script>

<style scoped>
.table-img {
  width: 50px;
  height: 50px;
  object-fit: cover;
}
</style>
