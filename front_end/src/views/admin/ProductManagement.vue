<template>
  <div>
    <!-- Hiển thị thông báo -->
    <div v-if="message" :class="`alert alert-${messageType}`" role="alert">
      {{ message }}
    </div>

    <!-- Form thêm/sửa sản phẩm -->
    <ProductForm
      :product="productSelected"
      :categories="categories"
      @add="handleAdd"
      @update="handleUpdate"
      @delete="handleDelete"
      @uploadImage="handleUploadImage"
      @reset="handleReset"
    />

    <!-- Tìm kiếm sản phẩm theo ID danh mục -->
    <div class="mb-3 d-flex gap-2">
      <input
        v-model="searchQuery"
        type="text"
        class="form-control"
        placeholder="Nhập ID danh mục..."
      />
    </div>

    <!-- Danh sách sản phẩm -->
    <div class="product-list">
      <h4 class="mb-3">Danh sách sản phẩm</h4>
      <ProductList
        :products="productStore.products"
        :pageNumber="productStore.pageNumber"
        :totalPages="productStore.totalPages"
        @edit="showEdit"
        @changePage="fetchProducts"
      />
    </div>
  </div>
</template>

<script setup>
import ProductForm from "@/components/admin/product/ProductForm.vue";
import ProductList from "@/components/admin/product/ProductList.vue";
import { computed, onMounted, ref } from "vue";
import { useCategories } from "@/store/admin/CategoryStore";
import { useProducts } from "@/store/admin/ProductStore";

// Stores
const categoryStore = useCategories();
const productStore = useProducts();

// State
const searchQuery = ref("");
const message = ref("");
const messageType = ref("");
const categories = ref([]);
const productSelected = ref({});

// Tải danh sách danh mục
const loadCategories = async () => {
  categories.value = await categoryStore.getAll();
};

// Xử lý thêm sản phẩm
const handleAdd = async (product) => {
  const response = await productStore.create(product);
  if (response) {
    message.value = response.message;
  }
  await fetchProducts();
};

// Xử lý cập nhật sản phẩm
const handleUpdate = async (productId, product) => {
  const response = await productStore.update(productId, product);
  if (response) {
    message.value = response.message;
  }
  await fetchProducts();
};

// Xử lý xóa sản phẩm
const handleDelete = async (productId) => {
  const response = await productStore.delete(productId);
  if (response) {
    message.value = response.message;
  }
  await fetchProducts();
};

// Xử lý upload ảnh
const handleUploadImage = async (productId, file) => {
  const response = await productStore.uploadImage(productId, file);
  if (response) {
    message.value = response.message;
  }
  await fetchProducts();
  productSelected.value = {};
};

// Hiển thị form sửa sản phẩm
const showEdit = (product) => {
  productSelected.value = product;
};

// Reset form
const handleReset = () => {
  productSelected.value = {};
};

// Fetch sản phẩm theo trang
const fetchProducts = async (page = 0) => {
  await productStore.fetchProducts(page);
};

// Gọi API khi component mount
onMounted(async () => {
  await fetchProducts();
  await loadCategories();
});
</script>
