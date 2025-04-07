<template>
  <div>
    <OrderForm :order="selectedOrder" @update="handleUpdate" />
  
    <OrderList :orders="orders" @edit="editOrder" @delete="deleteOrder" />
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import OrderForm from "@/components/admin/order/OrderForm.vue";
import OrderList from "@/components/admin/order/OrderList.vue";
import { useOrders } from "@/store/admin/OrderStore";

const ordersStore = useOrders();
const orders = ref([]);
const selectedOrder = ref({});
const currentPage = ref(1);
const totalPages = ref(0);

onMounted(() => {
  loadOrders(); 
});

const editOrder = (order) => {
  selectedOrder.value = order;
};

const deleteOrder = async (orderId) => {
  await ordersStore.cancelOrder(orderId);
  await loadOrders(currentPage.value);  
};

const handleUpdate = async (orderData) => {
  await ordersStore.updateOrderStatus(orderData.id, orderData.status, "Cập nhật trạng thái đơn hàng");
  await loadOrders(currentPage.value);
  selectedOrder.value = null;
};

const loadOrders = async (page = 1) => {
  const response = await ordersStore.fetchOrders(page);
  if (response) {
    orders.value = ordersStore.orders;
    console.log(orders.value);  // Kiểm tra dữ liệu
    totalPages.value = ordersStore.totalPages;
  }
};

</script>
