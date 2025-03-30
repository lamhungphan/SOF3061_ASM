<template>
  <div>
    <OrderForm :order="selectedOrder" @update="handleUpdate" />
  
    <!-- Danh sách đơn hàng -->
    <OrderList :orders="orders" @edit="editOrder" @delete="deleteOrder" />
  
    <!-- Phân trang -->
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
import { onMounted, ref } from "vue";
import OrderForm from "../components/order/OrderForm.vue";
import OrderList from "../components/order/OrderList.vue";
import { useOrders } from "../stores/OrderStore";

const ordersStore = useOrders();
const orders = ref([]);
const selectedOrder = ref({});
const currentPage = ref(1);
const totalPages = ref(0);

// Chỉnh sửa trạng thái đơn hàng
const editOrder = (order) => {
  selectedOrder.value = order;
};

// Hủy đơn hàng
const deleteOrder = async (orderId) => {
  await ordersStore.cancelOrder(orderId);
  await loadOrders(currentPage.value);
};

// Cập nhật trạng thái đơn hàng
const handleUpdate = async (orderData) => {
  await ordersStore.updateOrderStatus(orderData.id, orderData.status, "Cập nhật trạng thái đơn hàng");
  await loadOrders(currentPage.value);
  selectedOrder.value = null;
};

const loadOrders = async () => {
  const response = await ordersStore.fetchOrders(currentPage.value);
  if (response) {
    orders.value = ordersStore.orders;
    console.log(orders.value)
    totalPages.value = ordersStore.totalPages;
  }
};

const changePage = async (newPage) => {
  if (newPage >= 1 && newPage <= totalPages.value) {
    currentPage.value = newPage;
    await loadCategories();
  }
};

onMounted(() => {
  loadOrders();
});
  </script>
  