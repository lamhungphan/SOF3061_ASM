<template>
    <table class="table table-striped">
        <thead>
            <tr>
                <th>Mã Đơn Hàng</th>
                <th>Tên Khách Hàng</th>
                <th>Tổng Tiền</th>
                <th>Ngày Đặt Hàng</th>
                <th>Trạng Thái</th>
                <th>Phí Giao Hàng</th>
                <th>Thanh Toán</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="order in orders" :key="order.id">
                <td>{{ order.id }}</td>
                <td>{{ order.emailUser }}</td>
                <td>{{ currencyFormat(order.total) }}</td>
                <td>{{ order.orderDate }}</td>
                <td>{{ order.status }}</td>
                <td>{{ currencyFormat(order.shippingFee) }}</td>
                <td>{{ order.paymentMethod }}</td>
                <td>
                    <button @click="$emit('edit', order)" class="btn btn-warning btn-sm">Edit</button>
                    <button @click="$emit('delete', order.id)" class="btn btn-danger btn-sm">Delete</button>
                </td>
            </tr>
        </tbody>
    </table>
</template>
  
  <script setup>
// Bộ lọc hiển thị giá tiền
const currencyFormat = (value) => {
  return new Intl.NumberFormat("vi-VN", { style: "currency", currency: "VND" }).format(value);
};
defineProps({
  orders: Array
});
defineEmits(["edit", "delete"]);
  </script>
  