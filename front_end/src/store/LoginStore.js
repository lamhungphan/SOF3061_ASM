import { defineStore } from "pinia";
import axiosInstance from "@/axios/axios";

export const useLoginStore = defineStore("login", {
  state: () => ({
    user: JSON.parse(localStorage.getItem("user")) || null,
    token: localStorage.getItem("token") || null,
    role: localStorage.getItem("role") || null,
    refreshToken: localStorage.getItem("refreshToken") || null,
    error: null,
    loading: false,
  }),
  getters: {
    // Cú pháp getter theo Pinia
    isAuthenticated: (state) => !!state.token,
    isAdmin: (state) => ["admin", "manager", "director"].includes(state.role),
  },
  actions: {
    async login(username, password) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInstance.post("/auth/access-token", {
          username,
          password,
        });

        if (response.data.accessToken) {
          // Lưu tất cả dữ liệu từ response
          this.token = response.data.accessToken;
          this.refreshToken = response.data.refreshToken;
          this.role = response.data.role || "admin"; // Đảm bảo luôn có role || mặc định
          this.user = {
            username,
            // Có thể thêm các thông tin khác từ response nếu backend trả về
            ...(response.data.user || {}),
          };

          // Lưu vào localStorage
          localStorage.setItem("token", this.token);
          localStorage.setItem("refreshToken", this.refreshToken);
          localStorage.setItem("role", this.role);
          localStorage.setItem("user", JSON.stringify(this.user));

          return true;
        } else {
          this.error = "Sai tên đăng nhập hoặc mật khẩu!";
          return false;
        }
      } catch (error) {
        console.error("Login error:", error);
        this.error = error.response?.data?.message || "Lỗi kết nối đến máy chủ!";
        return false;
      } finally {
        this.loading = false;
      }
    },
    logout() {
      // Xóa tất cả dữ liệu liên quan
      this.user = null;
      this.token = null;
      this.role = null;
      this.refreshToken = null;
      this.error = null;
      localStorage.removeItem("token");
      localStorage.removeItem("refreshToken");
      localStorage.removeItem("role");
      localStorage.removeItem("user");
    },
  },
});