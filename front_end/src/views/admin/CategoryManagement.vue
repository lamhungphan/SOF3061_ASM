<template>
  <div>  
  <div v-if="message" :class="`alert alert-${messageType}`" role="alert">
    {{ message }}
  </div>
  
  <!-- Tìm kiếm danh mục theo ID -->
  <div class="mb-3 d-flex gap-2">
    <input v-model="searchQuery" type="text" class="form-control" placeholder="Nhập ID ngành hàng..." />
  </div>
  
  <!-- Form Thêm / Sửa danh mục -->
  <CategoryForm :category="editingCategory" @add="handleAdd" @update="handleUpdate" @remove="handleRemove" />
  
  <!-- Danh sách danh mục -->
  <div class="category-list">
    <CategoryList :categories="filteredCategories" @edit="showEdit" />
  
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
import { ref, onMounted, computed } from "vue";
import { useCategories } from "@/store/admin/CategoryStore";
import CategoryForm from "@/components/admin/category/CategoryForm.vue";
import CategoryList from "@/components/admin/category/CategoryList.vue";
const categoryStore = useCategories();

const searchQuery = ref("");
const message = ref("");
const messageType = ref("");

const categories = ref([]);

const editingCategory = ref({ id: null, name: "" });

const currentPage = ref(1);

const totalPages = ref(1);

const showEdit = (category) => {
  editingCategory.value = { ...category };
};

const handleAdd = async (category) => {
  const success = await categoryStore.createCategories(category);
  message.value = success ? "Thêm danh mục thành công!" : "Lỗi khi thêm danh mục!";
  messageType.value = success ? "success" : "danger";
  await loadCategories();
  resetMessage();
};

const handleUpdate = async (category) => {
  const success = await categoryStore.updateCategory(category.id, category);
  message.value = success ? "Cập nhật danh mục thành công!" : "Lỗi khi cập nhật danh mục!";
  messageType.value = success ? "success" : "danger";
  await loadCategories();
  resetMessage();
};

const handleRemove = async (categoryId) => {
  const success = await categoryStore.deleteCategory(categoryId);
  message.value = success ? "Xóa danh mục thành công!" : "Lỗi khi xóa danh mục!";
  messageType.value = success ? "success" : "danger";
  await loadCategories();
  resetMessage();
};

const changePage = async (newPage) => {
  if (newPage >= 1 && newPage <= totalPages.value) {
    currentPage.value = newPage;
    await loadCategories();
  }
};

const loadCategories = async () => {
  const success = await categoryStore.retrieveCategories(currentPage.value);
  if (success) {
    categories.value = categoryStore.categories;
    totalPages.value = categoryStore.totalPages;
  }
};

const filteredCategories = computed(() => {
  return categories.value.filter(category =>
    category.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
    (category.id === parseInt(searchQuery.value))
  );
});

const resetMessage = () => {
  setTimeout(() => (message.value = ""), 3000);
};

onMounted(loadCategories);
</script>

<style>
.category-list {
  margin-top: 20px;
}
</style>
