<template>
  <div>
    <HeaderComponent />
    <section class="cart-page py-5">
      <div class="container">
        <div v-if="cart.length === 0" class="text-center empty-cart">
          <p>Giỏ hàng của bạn đang trống.</p>
          <RouterLink to="/" class="btn btn-primary">Tiếp tục mua sắm</RouterLink>
        </div>

        <div v-else>
          <table class="table table-hover align-middle">
            <thead class="table-light">
              <tr>
                <th>Sản phẩm</th>
                <th>Tên sản phẩm</th>
                <th>Giá</th>
                <th class="text-center">Số lượng</th>
                <th>Thành tiền</th>
                <th>Hành động</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in enrichedCart" :key="item.productId">
                <td class="d-flex align-items-center gap-3">
                  <img :src="item.image" alt="image" class="cart-img" @error="handleImageError($event, item)" />
                  <span class="fw-semibold">{{ item.name }}</span>
                </td>
                <td>{{ item.name }}</td>
                <td class="text-danger fw-bold">
                  {{ formatPrice(item.price) }}₫
                </td>
                <td class="text-center">
                  <div class="quantity-box">
                    <button @click="decrease(item)" class="btn btn-sm btn-outline-secondary">-</button>
                    <span class="px-3">{{ item.quantity }}</span>
                    <button @click="increase(item)" class="btn btn-sm btn-outline-secondary">+</button>
                  </div>
                </td>
                <td class="text-danger fw-bold">
                  {{ formatPrice(item.price * item.quantity) }}₫
                </td>
                <td>
                  <button @click="remove(item.productId)" class="btn btn-outline-danger btn-sm">❌</button>
                </td>
              </tr>
            </tbody>
          </table>

          <div class="d-flex justify-content-between align-items-center mt-4">
            <h4 class="fw-bold">
              Tổng tiền: <span class="text-danger">{{ formatPrice(total) }}₫</span>
            </h4>
            <RouterLink to="/checkout" class="btn btn-success btn-lg">🛍️ Thanh toán</RouterLink>
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
import { useLoginStore } from "@/store/loginStore";
import { useProductStore } from "@/store/productStore";

const loginStore = useLoginStore();
const cartStore = useCartStore();
const productStore = useProductStore();

const userId = computed(() => loginStore.user?.id || null);
const cart = computed(() => cartStore.cart);

// Kết hợp dữ liệu giỏ hàng với sản phẩm
const enrichedCart = computed(() => {
  return cart.value.map(item => {
    const product = productStore.products.find(p => p.id === item.productId) || {};
    return {
      ...item,
      name: product.name || item.name || "Unknown Product",
      image: product.image || item.image || "img",
      price: product.price || item.price || 0,
    };
  });
});

onMounted(async () => {
  cartStore.initializeCart(userId.value);
  await productStore.fetchProducts();
});

const formatPrice = (price) => (price ? price.toLocaleString("vi-VN") : "0");

const increase = async (item) => {
  await cartStore.addToCart(userId.value, item.productId, 1);
};

const decrease = async (item) => {
  if (item.quantity > 1) {
    await cartStore.addToCart(userId.value, item.productId, -1);
  } else {
    await cartStore.removeFromCart(userId.value, item.productId);
  }
};

const remove = async (productId) => {
  await cartStore.removeFromCart(userId.value, productId);
};

const total = computed(() => {
  return enrichedCart.value.reduce((sum, item) => sum + (item.price || 0) * (item.quantity || 0), 0) || 0;
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
