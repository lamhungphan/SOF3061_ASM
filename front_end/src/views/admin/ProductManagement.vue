<template>
  <div>
  <h3 class="">Quản Lý Hàng Hóa</h3>
  <div v-if="message" :class="`alert alert-${messageType}`" role="alert">
    {{ message }}
  </div>
  <ProductForm :product="prodcutSelected" :categories="categories" @add="handleAdd" @update="handleUpdate"
    @delete="handleDelete" @uploadImage="handleUploadImage" @reset="handleReset"></ProductForm>
  <!-- Tìm kiếm danh mục theo ID -->
  <div class="mb-3 d-flex gap-2">
    <input v-model="searchQuery" type="text" class="form-control" placeholder="Nhập ID danh mục..." />
  </div>
  
  <!-- Danh sách sản phẩm -->
  <div class="product-list">
    <h4 class="mb-3">Danh Sách Sản Phẩm</h4>
    <ProductList :products="productListFillter" @edit="showEdit"></ProductList>
    <!-- Phân trang -->
    <div class="d-flex justify-content-center mt-3">
      <button class="btn btn-outline-primary mx-1" @click="changePage(currentPage - 1)" :disabled="currentPage === 1">
        Trước
      </button>
      <span class="mx-2">Trang {{ currentPage }} / {{ totalPages }}</span>
      <button class="btn btn-outline-primary mx-1" @click="changePage(currentPage + 1)"
        :disabled="currentPage >= totalPages">
        Sau
      </button>
    </div>
  </div>
</div>
</template>
<script setup>
import ProductForm from '@/components/admin/product/ProductForm.vue';
import ProductList from '@/components/admin/product/ProductList.vue';
import { computed, onMounted, ref } from 'vue';
import { useCategories } from '@/store/admin/CategoryStore';
import { useProducts } from '@/store/admin/ProductStore';

const categoryStore = useCategories();

const productStore = useProducts();

const searchQuery = ref("");

const message = ref("");

const messageType = ref("");

const currentPage = ref(1);

const totalPages = ref(1);

const categories = ref([]);

const prodcutSelected = ref({
});

const loadProducts = async () => {
  const response = await productStore.fetchProducts(currentPage.value);
  if (response) {
    totalPages.value = productStore.totalPage;
  }
}

const handleAdd = async (product) => {
  const response = await productStore.create(product);
  if (response) {
    message.value = response.message;
  }
  await loadProducts();
}

const handleUpdate = async (productId, product) => {
  const response = await productStore.update(productId, product);
  if (response) {
    message.value = response.message;
  }
  await loadProducts();

}

const handleDelete = async (productId) => {
  const response = await productStore.delete(productId);
  if (response) {
    message.value = response.message;
  }
  await loadProducts();

}

const handleReset = () => {
  prodcutSelected.value = {};
}

function showEdit(product) {
  prodcutSelected.value = product;
}

const loadCategories = async () => {
  const response = await categoryStore.getAll();
  if (response) {
    categories.value = response;
  }
};

const changePage = async (newPage) => {
  if (newPage >= 1 && newPage <= totalPages.value) {
    currentPage.value = newPage;
    await loadProducts();
  }
};
const handleUploadImage = async (productId, file) => {
  const response = await productStore.uploadImage(productId, file);
  if (response) {
    message.value = response.message;
  }
  await loadProducts();
  prodcutSelected.value = {};
}

const productListFillter = computed(() => {
  return productStore.products;
  // return productStore.products.filter(product => product.name.toLowerCase().includes(searchQuery.value.toLowerCase()))
})

onMounted(async () => {
  await loadProducts();
  await loadCategories();
})

</script>
<style>
.image-preview-large {
  width: 180px;
  height: 180px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f8f9fa;
  overflow: hidden;
}

.preview-large-img {
  max-width: 100%;
  max-height: 100%;
  object-fit: cover;
}

.table-img {
  width: 50px;
  height: 50px;
  object-fit: cover;
}
</style>
