import { defineStore } from "pinia";
import axiosInstance from "@/axios/axios";

export const useProducts = defineStore("products", {
  state: () => ({
    products: [],
    loading: false,
    error: null,
    totalPage: 1,
  }),
  actions: {
    async fetchProducts(page) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.get(
          `/store/products?page=${page}`
        );
        if (response) {
          this.products = response.data.content;
          this.totalPage = response.data.totalPages;
          return true;
          
        }
        return false;
      } catch (error) {
        this.error = error.message;
        console.warn(this.error);
      }
    },
    async create(product) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.post(`/store/products`, product);
        if (response.data.code === 0) {
          return { success: true, message: "Thêm sản phẩm thành công!" };
        } else {
          return {
            success: false,
            message: response.data.message || "Lỗi không xác định!",
          };
        }
      } catch (error) {
        if (error.response && error.response.status === 404) {
          return { success: false, message: "Danh mục đã tồn tại!" };
        }
        return { success: false, message: "Lỗi kết nối đến máy chủ!" };
      }
    },
    async update(productId, product) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.put(
          `/store/products/${productId}`,
          product
        );
        if (response.data.code === 0) {
          return { success: true, message: "Cập nhật sản phẩm thành công!" };
        } else {
          return {
            success: false,
            message: response.data.message || "Lỗi không xác định!",
          };
        }
      } catch (error) {
        if (error.response && error.response.status === 400) {
          return { success: false, message: "Sản phẩm đã tồn tại!" };
        }
        return { success: false, message: "Lỗi kết nối đến máy chủ!" };
      }
    },
    async delete(productId) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.delete(
          `/store/products/${productId}`
        );
        if (response.data.code === 0) {
          return { success: true, message: "Xóa sản phẩm thành công!" };
        }
        return { success: false, message: "Lỗi từ máy chủ" };
      } catch (error) {
        if (error.response && error.response.status === 400) {
          return { success: false, message: "Danh mục đã tồn tại!" };
        }
        return { success: false, message: "Lỗi kết nối đến máy chủ!" };
      }
    },
    async uploadImage(productId, file) {
      this.loading = true;
      this.error = null;

      const formData = new FormData();
      formData.append("image", file.get("image"));
      try {
        const response = await axiosInstance.post(
          `/store/products/upload-image/${productId}`,
          formData,
          {
            headers: {
              "Content-Type": "multipart/form-data",
            },
          }
        );
        if (response.data.code === 0) {
          return {
            success: true,
            message: "Đã thay đổi thành công hình ảnh sản phẩm!",
          };
        }
        return { success: false, message: "Không thể tải hình ảnh" };
      } catch (error) {
        return { success: false, message: "Lỗi kết nối đến máy chủ!" };
      }
    },
  },
});
