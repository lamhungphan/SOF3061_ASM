<template>
  <div>
    <HeaderComponent />
  <div class="container">
    <div v-if="product" class="row">
      <div class="col-md-6">
        <img :src="product.image" class="img-fluid product-image" :alt="product.name">
      </div>
      <div class="col-md-6">
        <h2>{{ product.name }}</h2>
        <p class="text-muted">
          Ngày phát hành: {{ product.publishDate || "Chưa cập nhật" }}
        </p>
        <p class="text-danger h4">{{ formattedPrice }}</p>
        <p><strong>Mô tả:</strong> {{ product.description || "Không có mô tả" }}</p>
        <p><strong>Kho:</strong> {{ product.quantity || "Không xác định" }} sản phẩm</p>
        <button class="btn btn-primary" @click="addToCart(product.id)">Thêm vào giỏ hàng 🛒</button>
      </div>
    </div>
    <div v-else>
      <p class="text-center">Đang tải sản phẩm...</p>
    </div>
  </div>
</div>
</template>

<script setup>
import HeaderComponent from "@/components/HeaderComponent.vue";
import { computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useProductStore } from '@/store/productStore';
import { useCartStore } from '@/store/cartStore';

const route = useRoute();
const productStore = useProductStore();
const cartStore = useCartStore();

const product = computed(() => productStore.productDetail || {});

const formattedPrice = computed(() => 
  product.value?.price ? product.value.price.toLocaleString('vi-VN') + "₫" : "Chưa cập nhật"
);

onMounted(async () => {
  await productStore.fetchProductById(route.params.id);
});

const addToCart = async (productId) => {
  const userId = 1; // Tạm thời gán cứng, sau này thay bằng user đăng nhập
  const quantity = 1; // Mặc định thêm 1 sản phẩm

  await cartStore.addToCart(userId, productId, quantity);
};
</script>

<style scoped>
.product-image {
  max-width: 100%;
  height: auto;
  border-radius: 10px;
}

.container {
  padding-top: 20px;
}

h2 {
  color: #333;
}
</style>
