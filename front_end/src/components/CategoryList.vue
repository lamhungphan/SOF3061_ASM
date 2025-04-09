<template>
  <section class="product-category py-5">
    <div class="container">
      <div class="row">
        <div 
          v-for="(category, index) in categories" 
          :key="index" 
          class="col-md-3 col-sm-6 mb-4"
        >
          <div class="card shadow-sm text-center category-card" @click="goToCategory(category.id)"          >

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
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();
const categories = ref([]);


const fetchCategories = async () => {
  try {
    const response = await axios.get("http://localhost:8080/categories", {
      headers: {
        "Content-Type": "application/json",
      },
    });

    categories.value = response.data?.data?.content || [];
  } catch (error) {
    console.error("Lỗi khi tải danh mục sản phẩm:", error);
  }
};

onMounted(fetchCategories);

const goToCategory = (categoryId) => {
  router.push({ path: '/', query: { categoryId } });
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
  background-color: #f8f9fa;
}

.card img {
  max-height: 150px;
  width: auto;
}
</style>
