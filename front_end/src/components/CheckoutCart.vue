<template>
  <div>
    <div class="d-flex justify-content-between align-items-center border-bottom py-2">
      <div class="d-flex gap-3 align-items-center">
        <img :src="fullItem.image" alt="..." width="60" style="object-fit: cover;" />
        <div>
          <h6 class="mb-0">{{ fullItem.name }}</h6>
          <small>{{ fullItem.quantity }} x {{ formatPrice(fullItem.price) }}</small>
        </div>
      </div>
      <div>
        <strong>{{ formatPrice(fullItem.price * fullItem.quantity) }}</strong>
        <button class="btn btn-sm btn-danger ms-2" @click="handleRemove">Xóa</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, watchEffect } from "vue";
import { useCartStore } from "@/store/cartStore";
import { useProductStore } from "@/store/productStore";

const props = defineProps(["item", "isLastItem", "isShowTotal"]);
const emit = defineEmits(["updateTotal"]);

const cartStore = useCartStore();
const productStore = useProductStore();
const userId = localStorage.getItem("userId");

const product = computed(() => {
  return productStore.products.find(p => p.id === props.item.productId) || {};
});

const fullItem = computed(() => ({
  ...props.item,
  name: product.value.name || "Sản phẩm không xác định",
  price: product.value.price || 0,
  image: product.value.image || "img/default-product.jpg", // Hình ảnh mặc định
  quantity: props.item.quantity || 1,
}));

const formatPrice = (price) => {
  if (typeof price !== "number") return "0₫";
  return price.toLocaleString("vi-VN") + "₫";
};

const handleRemove = () => {
  const productId = props.item.productId || props.item.id;
  cartStore.removeFromCart(userId, productId);
};

// Watch để tính tổng tiền mỗi khi giỏ hàng thay đổi
watchEffect(() => {
  const total = fullItem.value.price * fullItem.value.quantity;
  emit("updateTotal", total); // Gửi tổng tiền lên component cha
});
</script>
