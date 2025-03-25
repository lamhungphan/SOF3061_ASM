import { defineStore } from 'pinia';
import { ref, computed } from 'vue';

export const useProductStore = defineStore('product', () => {
  const products = ref([]);

  const fetchProducts = async () => {
    try {
      const response = await fetch('/api/products'); // Đổi URL API nếu cần
      const data = await response.json();
      products.value = data;
    } catch (error) {
      console.error('Lỗi khi tải danh sách sản phẩm:', error);
    }
  };

  const getProductById = computed(() => (id) => {
    return products.value.find((product) => product.id === id);
  });

  return { products, fetchProducts, getProductById };
});
