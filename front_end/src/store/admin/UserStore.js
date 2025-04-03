import { defineStore } from "pinia";
import axiosInstance from "@/axios/axios";

export const useUsers = defineStore("users", {
  state: () => ({
    users: [],
    loading: false,
    error: null,
    totalPages: 1,
    pageNumber: 0,
    pageSize: 5,
  }),
  actions: {
    handleApiError(error) {
      console.error("API Error:", error);
      this.error = "Connection error with the server!";
    },

    // Get list of users
    async getUsers(page = 1) {
      this.loading = true;
      this.error = null;
      try {
        // API call to get the list of users
        const response = await axiosInstance.get(`/account?page=${page - 1}&size=${this.pageSize}`);

        if (response.data.data.content) {
          this.users = response.data.data.content;
          this.totalPages = response.data.data.totalPages;
          this.pageNumber = page;
          return true;
        } else {
          this.error = "No users found";
          return false;
        }
      } catch (error) {
        this.handleApiError(error);
        return false;
      } finally {
        this.loading = false;
      }
    },

    // Get detail of a specific user
    async getUserDetail(userId) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.get(`/account/${userId}`);
        return response.data.data || null;
      } catch (error) {
        this.handleApiError(error);
        return null;
      } finally {
        this.loading = false;
      }
    },

    // Create a new user
    async createUser(data) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.post("/account", data);
        if (response.data.code === 0) {
          return { success: true, message: "User created successfully!" };
        } else {
          return {
            success: false,
            message: response.data.message || "Unknown error!",
          };
        }
      } catch (error) {
        this.handleApiError(error);
        return { success: false, message: "Connection error with the server!" };
      } finally {
        this.loading = false;
      }
    },

    // Update an existing user
    async updateUser(userId, data) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.put(`/account/${userId}`, data);
        if (response.data.code === 0) {
          return { success: true, message: "User updated successfully!" };
        } else {
          return {
            success: false,
            message: response.data.message || "Unknown error!",
          };
        }
      } catch (error) {
        this.handleApiError(error);
        return { success: false, message: "Connection error with the server!" };
      } finally {
        this.loading = false;
      }
    },

    // Delete an existing user
    async deleteUser(userId) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.delete(`/account/${userId}`);
        if (response.data.code === 0) {
          return { success: true, message: "User deleted successfully!" };
        } else {
          return {
            success: false,
            message: response.data.message || "Unknown error!",
          };
        }
      } catch (error) {
        this.handleApiError(error);
        return { success: false, message: "Connection error with the server!" };
      } finally {
        this.loading = false;
      }
    },
  },
});
