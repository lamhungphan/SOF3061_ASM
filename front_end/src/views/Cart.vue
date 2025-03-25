<template>
  <section class="cart-page py-5">
    <div class="container">
      <h2 class="text-center mb-4">🛒 Giỏ Hàng</h2>

      <div v-if="cart.length === 0" class="text-center empty-cart">
        <p>Giỏ hàng của bạn đang trống.</p>
        <RouterLink to="/" class="btn btn-primary">Tiếp tục mua sắm</RouterLink>
      </div>

      <div v-else>
        <table class="table table-hover align-middle">
          <thead class="table-light">
            <tr>
              <th>Sản phẩm</th>
              <th>Giá</th>
              <th class="text-center">Số lượng</th>
              <th>Thành tiền</th>
              <th>Hành động</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(item, index) in cart" :key="index">
              <td class="d-flex align-items-center gap-3">
                <img :src="item.image" alt="Product" class="cart-img" />
                <span class="fw-semibold">{{ item.name }}</span>
              </td>
              <td class="text-danger fw-bold">{{ formatPrice(item.price) }}₫</td>
              <td class="text-center">
                <div class="quantity-box">
                  <button @click="decreaseQuantity(index)" class="btn btn-sm btn-outline-secondary">-</button>
                  <span class="px-3">{{ item.quantity }}</span>
                  <button @click="increaseQuantity(index)" class="btn btn-sm btn-outline-secondary">+</button>
                </div>
              </td>
              <td class="text-danger fw-bold">{{ formatPrice(item.price * item.quantity) }}₫</td>
              <td>
                <button @click="removeFromCart(index)" class="btn btn-outline-danger btn-sm">
                  ❌
                </button>
              </td>
            </tr>
          </tbody>
        </table>

        <!-- Tổng tiền + Nút thanh toán -->
        <div class="d-flex justify-content-between align-items-center mt-4">
          <h4 class="fw-bold">
            Tổng tiền: <span class="text-danger">{{ formatPrice(totalPrice) }}₫</span>
          </h4>
          <RouterLink to="/checkout" class="btn btn-success btn-lg">🛍️ Thanh toán</RouterLink>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { computed } from "vue";
import { useCartStore } from "@/store/cartStore";

const cartStore = useCartStore();
const cart = computed(() => cartStore.cart);

const formatPrice = (price) => price.toLocaleString("vi-VN");

const increaseQuantity = (index) => {
  cart.value[index].quantity++;
  cartStore.saveCart();
};

const decreaseQuantity = (index) => {
  if (cart.value[index].quantity > 1) {
    cart.value[index].quantity--;
    cartStore.saveCart();
  }
};

const removeFromCart = (index) => {
  cartStore.removeItem(index);
};
</script>

<style scoped>
/* Hình ảnh sản phẩm */
.cart-img {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 8px;
}

/* Hộp số lượng */
.quantity-box {
  display: flex;
  align-items: center;
  justify-content: center;
}

/* Giỏ hàng trống */
.empty-cart {
  padding: 50px 0;
}

/* Hiệu ứng hover bảng */
.table-hover tbody tr:hover {
  background-color: #f8f9fa;
  transition: background 0.3s ease;
}
</style>
