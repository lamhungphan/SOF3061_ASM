import { defineStore } from "pinia";
import axiosInstance from "@/axios/axios";

export const useProducts = defineStore("products", {
  state: () => ({
    products: [],
    productDetail: null,
    loading: false,
    error: null,
    totalPages: 1,
    pageNumber: 0,
  }),

  actions: {
    async fetchProducts(page = 0, pageSize = 5) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.get(`/products`, {
          params: { page, size: pageSize },
        });

        if (response.data.status === 200) {
          this.products = response.data.data.content;
          this.totalPages = response.data.data.totalPages;
          this.pageNumber = response.data.data.pageNumber;
          return true;
        } else {
          this.error = response.data.message || "Không có sản phẩm!";
          return false;
        }
      } catch (error) {
        console.error("Lỗi API:", error);
        this.error = "Lỗi kết nối đến máy chủ!";
        return false;
      } finally {
        this.loading = false;
      }
    },

    async getProductById(productId) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.get(`/products/${productId}`);

        if (response.data.status === 200) {
          this.productDetail = response.data.data;
          return true;
        } else {
          this.error = response.data.message || "Không tìm thấy sản phẩm!";
          return false;
        }
      } catch (error) {
        console.error("Lỗi API:", error);
        this.error = "Lỗi kết nối đến máy chủ!";
        return false;
      } finally {
        this.loading = false;
      }
    },

    async create(product) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.post(`/products`, product);
        if (response.data.status === 200) {
          return { success: true, message: "Thêm sản phẩm thành công!" };
        } else {
          return {
            success: false,
            message: response.data.message || "Lỗi không xác định!",
          };
        }
      } catch (error) {
        if (error.response?.status === 400) {
          return { success: false, message: "Sản phẩm đã tồn tại!" };
        }
        return { success: false, message: "Lỗi kết nối đến máy chủ!" };
      } finally {
        this.loading = false;
      }
    },

    async update(productId, product) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.put(`/products/${productId}`, product);
        if (response.data.status === 200) {
          return { success: true, message: "Cập nhật sản phẩm thành công!" };
        } else {
          return {
            success: false,
            message: response.data.message || "Lỗi không xác định!",
          };
        }
      } catch (error) {
        if (error.response?.status === 400) {
          return { success: false, message: "Sản phẩm đã tồn tại!" };
        }
        return { success: false, message: "Lỗi kết nối đến máy chủ!" };
      } finally {
        this.loading = false;
      }
    },

    async delete(productId) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.delete(`/products/${productId}`);
        if (response.data.status === 200) {
          return { success: true, message: "Xóa sản phẩm thành công!" };
        }
        return { success: false, message: response.data.message || "Lỗi từ máy chủ" };
      } catch (error) {
        if (error.response?.status === 400) {
          return { success: false, message: "Không thể xóa sản phẩm!" };
        }
        return { success: false, message: "Lỗi kết nối đến máy chủ!" };
      } finally {
        this.loading = false;
      }
    },

    async uploadImage(productId, file) {
      this.loading = true;
      this.error = null;

      const formData = new FormData();
      formData.append("image", file);
      try {
        const response = await axiosInstance.post(
          `/products/upload-image/${productId}`,
          formData,
          {
            headers: { "Content-Type": "multipart/form-data" },
          }
        );
        if (response.data.status === 200) {
          return {
            success: true,
            message: "Đã thay đổi thành công hình ảnh sản phẩm!",
          };
        }
        return { success: false, message: "Không thể tải hình ảnh" };
      } catch (error) {
        return { success: false, message: "Lỗi kết nối đến máy chủ!" };
      } finally {
        this.loading = false;
      }
    },
  },
});
