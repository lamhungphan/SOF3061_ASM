<template>
  <div>
    <table class="table table-bordered text-center">
      <thead>
        <tr>
          <th>Hình</th>
          <th>Tên</th>
          <th>Mô Tả</th>
          <th>Giá</th>
          <th>Danh Mục</th>
          <th>Hành Động</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in products" :key="product.id">
          <td>
            <img :src="product.image" class="table-img" />
          </td>
          <td>{{ product.name }}</td>
          <td>{{ product.description }}</td>
          <td>{{ product.price.toLocaleString() }} đ</td>
          <td>{{ product.categoryId }}</td>
          <td>
            <button
              type="button"
              @click="selected(product)"
              class="btn btn-sm btn-primary me-1"
            >
              Sửa
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Phân trang -->
    <div class="d-flex justify-content-center mt-3" v-if="totalPages > 1">
      <button
        class="btn btn-outline-primary me-2"
        :disabled="pageNumber === 0"
        @click="changePage(pageNumber - 1)"
      >
        ← Trước
      </button>
      <span>Trang {{ pageNumber + 1 }} / {{ totalPages }}</span>
      <button
        class="btn btn-outline-primary ms-2"
        :disabled="pageNumber === totalPages - 1"
        @click="changePage(pageNumber + 1)"
      >
        Sau →
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";

const props = defineProps({
  products: Array,
  pageNumber: Number,
  totalPages: Number,
});
const emits = defineEmits(["edit", "changePage"]);

function selected(product) {
  emits("edit", product);
}

function changePage(page) {
  emits("changePage", page);
}
</script>

<style scoped>
.table-img {
  width: 50px;
  height: 50px;
  object-fit: cover;
}
</style>
