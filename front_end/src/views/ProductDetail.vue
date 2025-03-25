<template>
    <section class="product-detail py-5">
      <div class="container">
        <div v-if="product" class="row">
          <div class="col-md-6">
            <img :src="product.image" :alt="product.name" class="img-fluid rounded">
          </div>
          <div class="col-md-6">
            <h2>{{ product.name }}</h2>
            <p class="text-muted">Mã sản phẩm: {{ product.id }}</p>
            <h4 class="text-danger">{{ formatPrice(product.price) }}₫</h4>
            <p>{{ product.description }}</p>
            <div class="d-flex">
              <input type="number" v-model="quantity" min="1" class="form-control w-25 me-2">
              <button @click="addToCart" class="btn btn-primary">Thêm vào giỏ hàng</button>
            </div>
            <RouterLink to="/cart" class="btn btn-outline-success mt-3">Xem giỏ hàng</RouterLink>
          </div>
        </div>
        <div v-else class="text-center">
          <p>Sản phẩm không tồn tại.</p>
          <RouterLink to="/" class="btn btn-secondary">Quay lại trang chủ</RouterLink>
        </div>
      </div>
    </section>
  </template>
  
  <script setup>
  import { ref, computed, onMounted } from 'vue';
  import { useRoute } from 'vue-router';
  import { useCartStore } from '@/store/cartStore';
  import { useProductStore } from '@/store/productStore';
  
  const route = useRoute();
  const cartStore = useCartStore();
  const productStore = useProductStore();
  const product = ref(null);
  const quantity = ref(1);
  
  onMounted(() => {
    const productId = route.params.id;
    product.value = productStore.getProductById(productId);
  });
  
  const addToCart = () => {
    if (product.value) {
      cartStore.addToCart({ ...product.value, quantity: Number(quantity.value) });
      alert(`Đã thêm ${quantity.value} sản phẩm vào giỏ hàng!`);
    }
  };
  
  const formatPrice = (price) => {
    return price.toLocaleString('vi-VN');
  };
  </script>
  
  <style scoped>
  .img-fluid {
    max-height: 400px;
    object-fit: cover;
  }
  </style>
  