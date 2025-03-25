<template>
    <div class="container mt-5">
      <div class="row justify-content-center">
        <div class="col-lg-8">
          <div class="card shadow-lg p-4">
            <h2 class="text-center mb-4 fw-bold">🛒 Thanh Toán</h2>
  
            <div class="row">
              <!-- Cột 1: Form thông tin -->
              <div class="col-md-6">
                <h4 class="fw-semibold">📋 Thông tin giao hàng</h4>
                <form @submit.prevent="submitOrder">
                  <div class="mb-3">
                    <label class="form-label fw-medium">Họ và tên</label>
                    <input type="text" v-model="customer.name" class="form-control rounded-3" placeholder="Nhập họ và tên" required />
                  </div>
                  <div class="mb-3">
                    <label class="form-label fw-medium">Số điện thoại</label>
                    <input type="text" v-model="customer.phone" class="form-control rounded-3" placeholder="Nhập số điện thoại" required />
                  </div>
                  <div class="mb-3">
                    <label class="form-label fw-medium">Địa chỉ</label>
                    <input type="text" v-model="customer.address" class="form-control rounded-3" placeholder="Nhập địa chỉ giao hàng" required />
                  </div>
                  <div class="mb-3">
                    <label class="form-label fw-medium">Phương thức thanh toán</label>
                    <select v-model="customer.paymentMethod" class="form-select rounded-3">
                      <option value="cod">💰 Thanh toán khi nhận hàng (COD)</option>
                      <option value="bank">🏦 Chuyển khoản ngân hàng</option>
                    </select>
                  </div>
                  <button type="submit" class="btn btn-primary w-100 fw-semibold rounded-3 py-2">
                    ✅ Xác nhận đơn hàng
                  </button>
                </form>
              </div>
  
              <!-- Cột 2: Giỏ hàng -->
              <div class="col-md-6">
                <h4 class="fw-semibold">🛍 Đơn hàng của bạn</h4>
                <div v-if="cartStore.cart.length > 0">
                  <CartItem v-for="item in cartStore.cart" :key="item.id" :item="item" />
                  <div class="d-flex justify-content-between align-items-center border-top pt-3 mt-3">
                    <h5 class="fw-bold">Tổng tiền:</h5>
                    <h5 class="text-success fw-bold">{{ formatPrice(cartStore.cartTotal) }}</h5>
                  </div>
                </div>
                <p v-else class="text-muted text-center mt-4">🛒 Giỏ hàng của bạn đang trống!</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from "vue";
  import { useCartStore } from "@/store/cartStore";
  import CartItem from "@/components/CartItem.vue";
  
  const cartStore = useCartStore();
  
  const customer = ref({
    name: "",
    phone: "",
    address: "",
    paymentMethod: "cod",
  });
  
  const formatPrice = (price) => price.toLocaleString("vi-VN") + "₫";
  
  const submitOrder = () => {
    if (!cartStore.cart.length) {
      alert("🛑 Giỏ hàng của bạn đang trống!");
      return;
    }
  
    alert(`✅ Đơn hàng đã xác nhận!\n💵 Tổng tiền: ${formatPrice(cartStore.cartTotal)}`);
  
    cartStore.cart = []; // Xóa giỏ hàng sau khi thanh toán
  };
  </script>
  
  <style scoped>
  .card {
    border-radius: 12px;
    transition: all 0.3s ease-in-out;
  }
  
  .card:hover {
    transform: translateY(-5px);
  }
  
  .form-control, .form-select {
    border-radius: 8px;
  }
  
  .btn-primary {
    background-color: #007bff;
    border: none;
  }
  
  .btn-primary:hover {
    background-color: #0056b3;
  }
  </style>
  