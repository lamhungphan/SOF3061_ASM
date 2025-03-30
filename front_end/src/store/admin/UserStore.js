import { defineStore } from "pinia";
import axiosInstance from "@/axios/axios";

export const useUsers = defineStore("users", {
  state: () => ({
    users: [],
    loading: false,
    error: null,
    totalPages: null,
  }),
  actions: {
    handleApiError(error) {
      console.error("Lỗi API:", error);
      this.error = "Lỗi kết nối đến máy chủ!";
    },

    async getUsers(page = 1) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.get(`/store/users?page=${page}`);

        if (response.data.content) {
          this.users = response.data.content;
          this.totalPages = response.data.totalPages;
          return true;
        } else {
          this.error = "Không tìm thấy máy chủ";
          return false;
        }
      } catch (error) {
        this.handleApiError(error);
        return false;
      } finally {
        this.loading = false;
      }
    },

    async createUsers(data) {
      this.loading = true;
      this.error = null;
      console.log("Dữ liệu gửi lên server:", data);
      try {
        const response = await axiosInstance.post("/store/users", data);
        if (response.data.code === 0) {
          return { success: true, message: "Thêm danh mục thành công!" };
        } else {
          return {
            success: false,
            message: response.data.message || "Lỗi không xác định!",
          };
        }
      } catch (error) {
        this.handleApiError(error);
        return { success: false, message: "Lỗi kết nối đến máy chủ!" };
      } finally {
        this.loading = false;
      }
    },

    async updateUser(userId, data) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.put(
          `/store/users/${userId}`,
          data
        );
        if (response.data.code === 0) {
          return { success: true, message: "Thêm danh mục thành công!" };
        } else {
          return {
            success: false,
            message: response.data.message || "Lỗi không xác định!",
          };
        }
      } catch (error) {
        this.handleApiError(error);
        return { success: false, message: "Lỗi kết nối đến máy chủ!" };
      } finally {
        this.loading = false;
      }
    },
    async updateAvatar(userId, file) {
      this.loading = true;
      this.error = null;
      let response;
      try {
        const formData = new FormData();
        formData.append("avatar", file.get("avatar"));
        response = await axiosInstance.post(
          `/store/users/upload-avatar/${userId}`,
          formData,
          {
            headers: {
              "Content-Type": "multipart/form-data",
            },
          }
        );
        if (response.data.code === 0) {
          return response.data.result;
        } else {
          this.error = response.data.message;
          return this.error;
        }
      } catch (error) {
        alert("Lỗi API:", error.message);
        return null;
      } finally {
        this.loading = false;
      }
    },
    async deleteUser(userId) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.delete(`/store/users/${userId}`);
        if (response.data.code === 0) {
          return response.data.result;
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
    setUsers(users) {
      this.users = users;
    },
  },
});
