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
      const decoded = decodeJWT(state.token);
      const rawRole = Array.isArray(decoded?.role) ? decoded.role[0] : decoded?.role;
      const finalRole = rawRole ? rawRole.toLowerCase() : null;
      console.log("Getter role - decoded:", decoded, "finalRole:", finalRole);
      return finalRole;
    },
    canViewManagerDashboard: (state) => {
      const canView = ["director", "staff"].includes(state.role);
      console.log("Getter canViewManagerDashboard - role:", state.role, "result:", canView);
      return canView;
    },
    isDirector: (state) => state.role === "director",
    isStaff: (state) => state.role === "staff",
    canViewManagerDashboard: (state) => ["director", "staff"].includes(state.role),
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

          console.log("Decoded JWT:", decodeJWT(this.token));
          console.log("Role in store:", this.role); 
          console.log("Can view admin in store:", this.canViewManagerDashboard); 
         
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