<template>
  <div>
    <HeaderComponent />
    <section class="cart-page py-5">
      <div class="container">
        <h2 class="text-center mb-4">Giỏ Hàng</h2>

        <div v-if="cart.length === 0" class="text-center empty-cart">
          <p>Giỏ hàng của bạn đang trống.</p>
          <RouterLink to="/" class="btn btn-primary"
            >Tiếp tục mua sắm</RouterLink
          >
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
              <tr v-for="item in cart" :key="item.productId">
                <td class="d-flex align-items-center gap-3">
                  <img :src="item.image" alt="Product" class="cart-img" />
                  <span class="fw-semibold">{{ item.name }}</span>
                </td>
                <td class="text-danger fw-bold">
                  {{ formatPrice(item.price) }}₫
                </td>
                <td class="text-center">
                  <div class="quantity-box">
                    <button
                      @click="decreaseQuantity(item)"
                      class="btn btn-sm btn-outline-secondary"
                    >
                      -
                    </button>
                    <span class="px-3">{{ item.quantity }}</span>
                    <button
                      @click="increaseQuantity(item)"
                      class="btn btn-sm btn-outline-secondary"
                    >
                      +
                    </button>
                  </div>
                </td>
                <td class="text-danger fw-bold">
                  {{ formatPrice(item.price * item.quantity) }}₫
                </td>
                <td>
                  <button
                    @click="removeFromCart(item.productId)"
                    class="btn btn-outline-danger btn-sm"
                  >
                    ❌
                  </button>
                </td>
              </tr>
            </tbody>
          </table>

          <div class="d-flex justify-content-between align-items-center mt-4">
            <h4 class="fw-bold">
              Tổng tiền:
              <span class="text-danger">{{ formatPrice(totalPrice) }}₫</span>
            </h4>
            <RouterLink to="/checkout" class="btn btn-success btn-lg"
              >🛍️ Thanh toán</RouterLink
            >
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import HeaderComponent from "@/components/HeaderComponent.vue";
import { computed, onMounted } from "vue";
import { useCartStore } from "@/store/cartStore";

const userId = 1; // Giả lập userId, sau này có thể lấy từ auth store
const cartStore = useCartStore();
const cart = computed(() => cartStore.cart);

onMounted(() => {
  cartStore.fetchCart(userId);
});

const formatPrice = (price) => (price ? price.toLocaleString("vi-VN") : "0");

// ✅ Tăng số lượng sản phẩm
const increaseQuantity = async (item) => {
  await cartStore.addToCart(userId, item.productId, 1);
};

// ✅ Giảm số lượng sản phẩm
const decreaseQuantity = async (item) => {
  if (item.quantity > 1) {
    await cartStore.addToCart(userId, item.productId, -1);
  } else {
    await cartStore.removeFromCart(userId, item.productId);
  }
};

// ✅ Xóa sản phẩm khỏi giỏ hàng
const removeFromCart = async (productId) => {
  await cartStore.removeFromCart(userId, productId);
};

// ✅ Tính tổng tiền
const totalPrice = computed(() => {
  return (
    cart.value?.reduce(
      (sum, item) => sum + (item.price || 0) * (item.quantity || 0),
      0
    ) || 0
  );
});
</script>

<style scoped>
.cart-img {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 8px;
}
.quantity-box {
  display: flex;
  align-items: center;
  justify-content: center;
}
.empty-cart {
  padding: 50px 0;
}
.table-hover tbody tr:hover {
  background-color: #f8f9fa;
  transition: background 0.3s ease;
}
</style>
