import { defineStore } from 'pinia';
import { ref } from 'vue';
import axiosInstance from "@/axios/axios"; 

const SUPABASE_URL = "https://wdkyyamhfjpwjcysnemg.supabase.co";
const BUCKET_NAME = "uploads";

export const useProductStore = defineStore('product', () => {
  const products = ref([]);
  const productDetail = ref(null);

  const fetchProducts = async () => {
    try {
      const response = await axiosInstance.get(`/products`);
      const productList = response.data?.data?.content || [];
      products.value = productList.map(product => ({
        ...product,
        image: product.image ? `${SUPABASE_URL}/storage/v1/object/public/${BUCKET_NAME}/${product.image}` : 'no img',
        publishDate: product.publishDate || "Chưa cập nhật"
      }));
    } catch (error) {
      console.error('Lỗi khi tải danh sách sản phẩm:', error.response?.data || error.message);
    }
  };


  const fetchProductById = async (id) => {
    try {
      const response = await axiosInstance.get(`/products/${id}`);
      const productData = response.data?.data;

      if (productData) {
        console.log("Product data received:", productData); // Kiểm tra dữ liệu API
        productDetail.value = {
          ...productData,
          image: productData.image
            ? `${SUPABASE_URL}/storage/v1/object/public/${BUCKET_NAME}/${productData.image}`
            : "no img",
          publishDate: productData.publishDate || "Chưa cập nhật",
        };
      } else {
        console.error("Không tìm thấy sản phẩm:", productData);
      }
    } catch (error) {
      console.error(`Lỗi khi tải sản phẩm có ID ${id}:`, error.response?.data || error.message);
    }
  };

  return { products, fetchProducts, productDetail, fetchProductById };
});
