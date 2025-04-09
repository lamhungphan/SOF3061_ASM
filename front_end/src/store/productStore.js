import { defineStore } from 'pinia';
import { ref } from 'vue';
import axiosInstance from "@/axios/axios";

const SUPABASE_URL = "https://wdkyyamhfjpwjcysnemg.supabase.co";
const BUCKET_NAME = "uploads";

export const useProductStore = defineStore('product', () => {
  const products = ref([]);
  const productDetail = ref(null);

  const formatProduct = (product) => ({
    ...product,
    image: product.image
      ? `${SUPABASE_URL}/storage/v1/object/public/${BUCKET_NAME}/${product.image}`
      : "no img",
    publishDate: product.publishDate || "Chưa cập nhật",
  });

  const fetchProducts = async () => {
    try {
      const response = await axiosInstance.get(`/products`);
      const productList = response.data?.data?.content || [];
      products.value = productList.map(formatProduct);
    } catch (error) {
      console.error('Lỗi khi tải danh sách sản phẩm:', error.response?.data || error.message);
    }
  };

  const fetchProductById = async (id) => {
    try {
      const response = await axiosInstance.get(`/products/${id}`);
      const productData = response.data?.data;
      productDetail.value = productData ? formatProduct(productData) : null;
    } catch (error) {
      console.error(`Lỗi khi tải sản phẩm có ID ${id}:`, error.response?.data || error.message);
    }
  };

  const fetchProductsByCategory = async (categoryId) => {
    try {
      const response = await axiosInstance.get(`/products/category/${categoryId}`);
      const productList = response.data?.data?.content || [];
      products.value = productList.map(product => ({
        ...product,
        image: product.image ? `${SUPABASE_URL}/storage/v1/object/public/${BUCKET_NAME}/${product.image}` : 'no img',
        publishDate: product.publishDate || "Chưa cập nhật"
      }));
    } catch (error) {
      console.error('Lỗi khi tải sản phẩm theo danh mục:', error.response?.data || error.message);
    }
  };   

  const searchProducts = async (keyword) => {
    try {
      const response = await axiosInstance.get(`/products/search`, {
        params: { keyword }
      });
      const productList = response.data?.data?.content || [];
      products.value = productList.map(formatProduct);
    } catch (error) {
      console.error(`Lỗi khi tìm kiếm sản phẩm với từ khóa "${keyword}":`, error.response?.data || error.message);
    }
  };

  return {
    products,
    productDetail,
    fetchProducts,
    fetchProductById,
    fetchProductsByCategory,
    searchProducts,
  };
});
