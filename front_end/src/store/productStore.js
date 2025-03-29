import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useProductStore = defineStore('product', () => {
  const products = ref([]);
  const productDetail = ref(null);

  const fetchProducts = async () => {
    try {
      const response = await fetch('http://localhost:8080/products');
      const data = await response.json();
      products.value = data.data.content.map(product => ({
        ...product,
        image: product.image ? `http://localhost:8080/images/${product.image}` : 'https://via.placeholder.com/200',
        publishDate: product.publishDate ? product.publishDate : "Chưa cập nhật"
      }));
    } catch (error) {
      console.error('Lỗi khi tải danh sách sản phẩm:', error);
    }
  };

  const fetchProductById = async (id) => {
    try {
      const response = await fetch(`http://localhost:8080/products/${id}`);
      const data = await response.json();
      productDetail.value = {
        ...data,
        image: data.image ? `http://localhost:8080/images/${data.image}` : 'https://via.placeholder.com/200',
        publishDate: data.publishDate ? data.publishDate : "Chưa cập nhật"
      };
    } catch (error) {
      console.error('Lỗi khi tải sản phẩm:', error);
    }
  };

  return { products, fetchProducts, productDetail, fetchProductById };
});
