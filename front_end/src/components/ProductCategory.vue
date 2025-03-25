<template>
  <section class="product-category py-5">
    <div class="container">
      <h2 class="text-center mb-4">Danh Mục Sản Phẩm</h2>
      <div class="row">
        <div 
          v-for="(category, index) in categories" 
          :key="index" 
          class="col-md-3 col-sm-6 mb-4"
        >
          <div class="card shadow-sm text-center category-card" @click="goToCategory(category.link)">
            <div class="image-container">
              <img :src="category.image" class="card-img-top" :alt="category.name">
            </div>
            <div class="card-body">
              <h5 class="card-title">{{ category.name }}</h5>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const categories = ref([
  { name: "Điện thoại", image: "https://samcenter.vn/images/thumbs/0010679_samsung-galaxy-s25-256gb_420.png", link: "/products?category=phone" },
  { name: "Máy tính bảng", image: "https://samcenter.vn/images/thumbs/0009001_samsung-galaxy-tab-s10-12gb256gb_420.jpeg", link: "/products?category=tablet" },
  { name: "Đồng hồ", image: "https://samcenter.vn/images/thumbs/0007268_samsung-galaxy-watch-ultra-lte-47mm_420.png", link: "/products?category=watch" },
  { name: "Phụ kiện", image: "https://samcenter.vn/images/thumbs/0007289_samsung-galaxy-buds3_420.png", link: "/products?category=accessories" }
]);

const goToCategory = (link) => {
  router.push(link);
};
</script>

<style scoped>
.category-card {
  cursor: pointer;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.category-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
}

.image-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 200px;
  background-color: #f8f9fa; /* Màu nền nhạt để làm nổi bật hình ảnh */
}

.card img {
  max-height: 150px;
  width: auto;
}
</style>
