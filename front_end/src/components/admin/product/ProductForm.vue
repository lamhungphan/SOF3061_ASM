<template>
  <!-- Form Thêm / Sửa sản phẩm -->
  <form class="mb-3 p-3 border rounded shadow-sm bg-light">
    <div class="row g-2">
      <div class="col-md-4 d-flex flex-column align-items-center">
        <div class="image-preview-large border rounded mb-2">
          <img :src="product.image" id="file" alt="img" class="preview-large-img" />
        </div>
        <input type="file" id="fileUpload" class="d-none" @change="uploadImage" />
        <label for="fileUpload" class="btn btn-outline-primary btn-sm mb-2">Chọn Hình</label>
      </div>
  
      <div class="col-md-8">
        <div class="mb-2">
          <label for="productName" class="form-label">Tên Sản Phẩm</label>
          <input v-model="product.name" type="text" class="form-control" id="productName" required />
        </div>
        <div class="mb-2">
          <label for="productPrice" class="form-label">Giá Sản Phẩm</label>
          <input v-model="product.price" type="number" class="form-control" id="productPrice" required min="0" />
        </div>
        <div class="mb-2">
          <label for="productCategory" class="form-label">Danh Mục</label>
          <input v-model="selectedCategoryName" type="text" class="form-control" id="productCategory" required readonly
            @click="toggleDropdown" />
  
          <ul v-if="showDropdown" class="list-group mt-1">
            <li v-for="category in categories" :key="category.id" class="list-group-item list-group-item-action"
              @click="selectCategory(category)">
              {{ category.name }}
            </li>
          </ul>
        </div>
        <div class="mb-2">
          <label for="productCategory" class="form-label">Size</label>
  
        </div>
        <div class="mb-2">
          <label for="productDescription" class="form-label">Mô Tả</label>
          <textarea class="form-control" id="productDescription"></textarea>
        </div>
      </div>
    </div>
  
    <div class="d-flex justify-content-end mt-2 gap-2">
      <button type="reset" class="btn btn-warning" @click="resetForm">Đặt Lại</button>
      <button type="button" class="btn btn-primary" @click="add">Thêm</button>
      <button type="button" class="btn btn-primary" @click="update">Cập nhật</button>
      <button type="button" class="btn btn-primary" @click="remove">Xoá</button>
    </div>
  </form>
</template>

<script setup>
import { ref, computed } from 'vue';
const props = defineProps({
  product: Object,
  categories: Array,
})
const showDropdown = ref(false);

const toggleDropdown = () => {
  showDropdown.value = !showDropdown.value;
};
const selectCategory = (category) => {
  props.product.categoryId = category.id;
  showDropdown.value = false;
};
const selectedCategoryName = computed(() => {
  return props.product.categoryId
    ? props.categories.find(c => c.id === props.product.categoryId)?.name || ""
    : "";
});

const emits = defineEmits(['add', 'update', 'delete', 'uploadImage', 'reset'])

function add() {
  emits('add', props.product);
}

function update() {
  emits('update', props.product.id, props.product);
}

function remove() {
  emits('delete', props.product.id)
}

function resetForm() {
  emits('reset')
}


function uploadImage(event) {
  const file = event.target.files?.[0];
  if (!file) {
    console.warn("Chưa chọn file hoặc file không hợp lệ.");
    return;
  }

  if (!props.product?.id) {
    console.error("Thiếu thông tin productId để upload avatar.");
    return;
  }
  const formData = new FormData();
  formData.append('image', file);
  console.log("Gửi dữ liệu upload:", {
    product: props.product.id,
    file: file.name,
    fileType: file.type,
    fileSize: file.size,
  });

  emits('uploadImage', props.product.id, formData);
}

</script>
<style scoped>
.list-group {
  position: absolute;
  width: 100%;
  z-index: 1000;
  background: white;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.list-group-item {
  cursor: pointer;
}

.list-group-item:hover {
  background-color: #f0f0f0;
}
</style>