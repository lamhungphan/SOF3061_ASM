import { defineStore } from "pinia";
import axiosInstance from "@/axios/axios";

export const useLoginStore = defineStore("login", {
    state: () => ({
        user: null,
        token: localStorage.getItem("token") || null,
        role: localStorage.getItem("role") || null,
        error: null,
        loading: false,
    }),
    actions: {
        async login(username, password) {
            this.loading = true;
            this.error = null;
            try {
                const response = await axiosInstance.post("/auth/token", { username, password });
                if (response.data.code === 0 && response.data.result.authenticated) {
                    this.token = response.data.result.token;
                    this.role = response.data.result.role;
                    localStorage.setItem("token", this.token);
                    localStorage.setItem("role", this.role);
                    this.user = { username };
                    return true;
                } else {
                    this.error = "Sai tên đăng nhập hoặc mật khẩu!";
                    return false;
                }
            } catch (error) {
                this.error = "Lỗi kết nối đến máy chủ!";
                return false;
            } finally {
                this.loading = false;
            }
        },
        logout() {
            this.user = null;
            this.token = null;
            localStorage.removeItem("token");
            localStorage.removeItem("user");
        },
        checkLogin() {
            this.token = localStorage.getItem("token");
            if (this.token) {
                this.user = { email: "admin" }; // Giả lập thông tin người dùng
            }
        },
    },
});
