<template>
  <div class="container">
    <!-- <h2 class="text-center mb-4">Sản Phẩm Nổi Bật 🔥</h2> -->
    <div class="row">
      <div
        v-for="product in products"
        :key="product.id"
        class="col-md-3 col-sm-6 mb-4"
      >
        <div class="card product-card" @click="goToProduct(product.id)">
          <div class="image-container">
            <img
              :src="product.image"
              class="card-img-top"
              :alt="product.name"
            />
          </div>
          <div class="card-body text-center">
            <h5 class="card-title">{{ product.name }}</h5>
            <p class="text-danger">{{ formatPrice(product.price) }}₫</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, watch } from "vue";
import { useProductStore } from "@/store/productStore";
import { useRouter, useRoute } from "vue-router";

const productStore = useProductStore();
const router = useRouter(); // chuyển trang
const route = useRoute(); // để đọc thông tin route hiện tại như params, query

const products = computed(() => productStore.products);

const formatPrice = (price) => price.toLocaleString("vi-VN");

const goToProduct = (id) => {
  router.push(`/product/${id}`);
};

const loadProducts = () => {
  const categoryId = route.query.categoryId;
  if (categoryId) {
    productStore.fetchProductsByCategory(Number(categoryId));
  } else {
    productStore.fetchProducts();
  }
};

onMounted(loadProducts);

watch(
  () => route.query.categoryId,
  () => {
    loadProducts();
  }
);
</script>

<style scoped>
.product-card {
  cursor: pointer;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.product-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
}

.image-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 250px;
  background-color: #f8f9fa;
}

.card-img-top {
  max-height: 200px;
  width: auto;
  object-fit: contain;
}
</style>
