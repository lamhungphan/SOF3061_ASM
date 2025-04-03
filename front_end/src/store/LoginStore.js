import { defineStore } from "pinia";
import axiosInstance from "@/axios/axios";
import { decodeJWT } from "@/utils/jwt";
import { useCartStore } from "@/store/cartStore";

export const useLoginStore = defineStore("login", {
  state: () => ({
    user: JSON.parse(localStorage.getItem("user")) || null,
    token: localStorage.getItem("token") || null,
    refreshToken: localStorage.getItem("refreshToken") || null,
    error: null,
    loading: false,
  }),
  getters: {
    isAuthenticated: (state) => !!state.token,
    role: (state) => {
      if (!state.token) return null;
      try {
        const decoded = decodeJWT(state.token);    
        let rawRole = decoded?.role;
        
        // Nếu `role` là mảng, lấy phần tử đầu tiên
        if (Array.isArray(rawRole)) {
          rawRole = rawRole.length > 0 ? rawRole[0] : null;
        }
        // Loại bỏ dấu [ ] nếu có trong chuỗi
        if (typeof rawRole === "string") {
          rawRole = rawRole.replace(/[\[\]']/g, "").trim(); 
        }
        const finalRole = rawRole ? rawRole.toLowerCase() : null;
        return finalRole;
      } catch (error) {
        console.error("Error decoding JWT:", error);
        return null;
      }
    }

    ,
    canViewManagerDashboard: (state) => {
      const canView = ["director", "staff"].includes(state.role);
      console.log("Getter canViewManagerDashboard - role:", state.role, "result:", canView);
      return canView;
    },
    isDirector: (state) => state.role === "director",
    isStaff: (state) => state.role === "staff",
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
          this.token = response.data.accessToken;
          this.refreshToken = response.data.refreshToken;
          this.user = {
            username,
            ...(response.data.user || {}),
          };

          localStorage.setItem("token", this.token);
          localStorage.setItem("refreshToken", this.refreshToken);
          localStorage.setItem("user", JSON.stringify(this.user));

          const cartStore = useCartStore();
          await cartStore.syncLocalCartToServer(this.user.id);

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
      this.user = null;
      this.token = null;
      this.refreshToken = null;
      this.error = null;
      localStorage.removeItem("token");
      localStorage.removeItem("refreshToken");
      localStorage.removeItem("user");
      localStorage.removeItem("localCart");

    },
  },
});