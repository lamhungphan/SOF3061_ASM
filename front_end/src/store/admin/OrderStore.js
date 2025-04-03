import { defineStore } from "pinia";
import axiosInstance from "@/axios/axios";

export const useOrders = defineStore("order", {
  state: () => ({
    orders: [],
    loading: false,
    error: null,
    totalPages: 1,
    pageNumber: 0,
    pageSize: 5,
  }),
  actions: {
    // Fetch all orders (GET)
    async fetchOrders(page = 1) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.get("/order", {
          params: { page: page - 1, size: this.pageSize },
        });

        if (response.data && response.data.data) {
          this.orders = response.data.data.content;  // Assuming response contains 'content'
          this.totalPages = response.data.data.totalPages;
          this.pageNumber = page;
          return { success: true };
        }
        return { success: false, message: "Không có dữ liệu đơn hàng!" };
      } catch (error) {
        this.error = error.response?.data?.message || "Lỗi lấy danh sách đơn hàng!";
        return { success: false, message: this.error };
      } finally {
        this.loading = false;
      }
    },

    // Get order detail (GET by ID)
    async getOrderById(orderId) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.get(`/order/${orderId}`);
        return response.data
          ? { success: true, data: response.data.result }
          : { success: false, message: "Không tìm thấy đơn hàng!" };
      } catch (error) {
        this.error = error.response?.data?.message || "Lỗi lấy thông tin đơn hàng!";
        return { success: false, message: this.error };
      } finally {
        this.loading = false;
      }
    },

    // Create a new order (POST)
    async createOrder(order) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.post("/order", order);
        if (response.data) {
          return {
            success: true,
            message: "Tạo đơn hàng thành công!",
            data: response.data.result,
          };
        }
        return { success: false, message: "Không thể tạo đơn hàng!" };
      } catch (error) {
        this.error = error.response?.data?.message || "Lỗi tạo đơn hàng!";
        return { success: false, message: this.error };
      } finally {
        this.loading = false;
      }
    },

    // Update order status (PUT)
    async updateOrderStatus(orderId, action, actionText) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.put(`/order/${orderId}/${action}`);
        if (response.data) {
          return {
            success: true,
            message: `${actionText} thành công!`,
            data: response.data.result,
          };
        }
        return { success: false, message: `${actionText} thất bại!` };
      } catch (error) {
        this.error = error.response?.data?.message || `${actionText} gặp lỗi!`;
        return { success: false, message: this.error };
      } finally {
        this.loading = false;
      }
    },

    // Confirm order (PUT)
    async confirmOrder(orderId) {
      return this.updateOrderStatus(orderId, "confirm", "Xác nhận đơn hàng");
    },

    // Ship order (PUT)
    async shipOrder(orderId) {
      return this.updateOrderStatus(orderId, "ship", "Chuyển đơn hàng sang trạng thái SHIPPING");
    },

    // Complete order (PUT)
    async completeOrder(orderId) {
      return this.updateOrderStatus(orderId, "complete", "Hoàn tất đơn hàng");
    },

    // Cancel order (PUT)
    async cancelOrder(orderId) {
      return this.updateOrderStatus(orderId, "cancel", "Hủy đơn hàng");
    },

    // Fetch all orders by user ID (GET)
    async fetchAllOrdersByUserId(userId) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.get(`/order/all/${userId}`);

        if (response.data) {
          this.orders = response.data || [];
          return response.data;
        }
        return { success: false, message: "Không có dữ liệu đơn hàng!" };
      } catch (error) {
        this.error = error.response?.data || "Lỗi lấy danh sách đơn hàng!";
        return { success: false, message: this.error };
      } finally {
        this.loading = false;
      }
    },
  },
});
