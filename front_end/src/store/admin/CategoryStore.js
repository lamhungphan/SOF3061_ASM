import { defineStore } from "pinia";
import axiosInstance from "@/axios/axios";

export const useCategories = defineStore("categories", {
  state: () => ({
    categories: [],
    loading: false,
    error: null,
    totalPages: 1,
  }),
  actions: {
    async getAll() {
      try {
        const response = await axiosInstance.get(`/categories`);
        if (response.data.status === 200) return response.data.data.content;
      } catch (error) {
        console.warn("Lỗi API:", error);
        return null;
      }
    },

    async retrieveCategories(page = 0, pageSize = 5) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.get(`/categories`, {
          params: { page, size: pageSize },
        });

        if (response.data.status === 200) {
          this.categories = response.data.data.content;
          this.totalPages = response.data.data.totalPages;
          return true;
        } else {
          this.error = response.data.message || "Không có danh mục";
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

    async createCategory(data) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.post("/categories", data);
        if (response.data.status === 200) {
          return { success: true, message: "Thêm danh mục thành công!" };
        } else {
          return { success: false, message: response.data.message || "Lỗi không xác định!" };
        }
      } catch (error) {
        if (error.response && error.response.status === 500) {
          return { success: false, message: "Danh mục đã tồn tại!" };
        }
        return { success: false, message: "Lỗi kết nối đến máy chủ!" };
      } finally {
        this.loading = false;
      }
    },

    async getCategoryById(categoryId) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.get(`/categories/${categoryId}`);
        if (response.data.status === 200) {
          return response.data.data;
        } else {
          this.error = response.data.message;
          return null;
        }
      } catch (error) {
        console.error("Lỗi API:", error);
        this.error = "Lỗi kết nối đến máy chủ!";
        return null;
      } finally {
        this.loading = false;
      }
    },

    async updateCategory(categoryId, data) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.put(`/categories/${categoryId}`, data);
        if (response.data.status === 200) {
          return response.data.data;
        } else {
          this.error = response.data.message;
          return null;
        }
      } catch (error) {
        console.error("Lỗi API:", error);
        this.error = "Lỗi kết nối đến máy chủ!";
        return null;
      } finally {
        this.loading = false;
      }
    },
  },
});
