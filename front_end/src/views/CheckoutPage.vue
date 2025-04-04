<template>
  <div>
    <HeaderComponent />
    <div class="container mt-5">
      <div class="row justify-content-center">
        <div class="col-lg-10">
          <div class="card shadow-lg p-4">
            <h2 class="text-center mb-4 fw-bold">🛒 Thanh Toán</h2>

            <div class="row">
              <!-- Cột 1: Form thông tin -->
              <div class="col-md-4">
                <h4 class="fw-semibold">📋 Thông tin giao hàng</h4>
                <form @submit.prevent="submitOrder">
                  <div class="mb-3">
                    <label class="form-label fw-medium">Họ và tên</label>
                    <input
                      type="text"
                      v-model="customer.name"
                      class="form-control rounded-3"
                      placeholder="Nhập họ và tên"
                      required
                    />
                  </div>
                  <div class="mb-3">
                    <label class="form-label fw-medium">Số điện thoại</label>
                    <input
                      type="text"
                      v-model="customer.phone"
                      class="form-control rounded-3"
                      placeholder="Nhập số điện thoại"
                      required
                    />
                  </div>
                  <div class="mb-3">
                    <label class="form-label fw-medium">Địa chỉ</label>
                    <input
                      type="text"
                      v-model="customer.address"
                      class="form-control rounded-3"
                      placeholder="Nhập địa chỉ giao hàng"
                      required
                    />
                  </div>
                  <div class="mb-3">
                    <label class="form-label fw-medium"
                      >Phương thức thanh toán</label
                    >
                    <select
                      v-model="customer.paymentMethod"
                      class="form-select rounded-3"
                    >
                      <option value="cod">
                        💰 Thanh toán khi nhận hàng (COD)
                      </option>
                      <option value="bank">🏦 Chuyển khoản ngân hàng</option>
                    </select>
                  </div>
                </form>
              </div>

              <!-- Cột 2: Giỏ hàng -->
              <div class="col-md-8">
                <h4 class="fw-semibold">🧾 Đơn hàng của bạn</h4>
                <div v-if="cartStore.cart.length > 0">
                  <CartItem
                    v-for="(item, index) in cartStore.cart"
                    :key="item.id"
                    :item="item"
                    :is-last-item="index === cartStore.cart.length - 1"
                    :is-show-total="true"
                    @updateTotal="updateTotal"
                  />
                </div>
                <p v-else class="text-muted text-center mt-4">
                  🛒 Giỏ hàng của bạn đang trống!
                </p>
              </div>
            </div>

            <!-- Phần gắn cố định -->
            <div class="d-flex justify-content-between align-items-center mt-3">
              <div class="fw-bold">
                Tổng tiền: {{ formatPrice(totalPrice) }}
              </div>
              <button
                type="submit"
                class="btn btn-success fw-semibold rounded-3 py-2"
              >
                Thanh toán
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import HeaderComponent from "@/components/HeaderComponent.vue";
import CartItem from "@/components/CheckoutCart.vue";
import { ref, computed, onMounted } from "vue";
import { useCartStore } from "@/store/cartStore";
import { useProductStore } from "@/store/productStore";

const cartStore = useCartStore();
const productStore = useProductStore();
const userId = localStorage.getItem("userId");

const customer = ref({
  name: "",
  phone: "",
  address: "",
  paymentMethod: "cod",
});

const totalPrice = computed(() => {
  return cartStore.cart.reduce((sum, item) => {
    const product = productStore.products.find((p) => p.id === item.productId);
    return sum + (product?.price || 0) * (item.quantity || 0);
  }, 0);
});

// Khởi tạo dữ liệu khi component được mount
onMounted(async () => {
  await Promise.all([
    cartStore.initializeCart(userId), // Tải giỏ hàng
    productStore.fetchProducts(), // Tải danh sách sản phẩm
  ]);
});

const formatPrice = (price) => {
  if (typeof price !== "number") return "0₫";
  return price.toLocaleString("vi-VN") + "₫";
};

const submitOrder = () => {
  if (!cartStore.cart.length) {
    alert("🛑 Giỏ hàng của bạn đang trống!");
    return;
  }

  alert(
    `✅ Đơn hàng đã xác nhận!\n💵 Tổng tiền: ${formatPrice(totalPrice.value)}`
  );
  cartStore.cart = []; // Xóa giỏ hàng sau khi thanh toán
};
</script>

<style scoped>
.card {
  border-radius: 12px;
  transition: all 0.3s ease-in-out;
}

.form-control,
.form-select {
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