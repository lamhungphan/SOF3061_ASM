import { defineStore } from "pinia";
import axiosInstance from "@/axios/axios";

export const useLoginStore = defineStore("login", {
    state: () => ({
        user: null,
        token: localStorage.getItem("token") || null,
        error: null,
        loading: false,
    }),
    actions: {
        async login(email, password) {
            this.loading = true;
            this.error = null;
            try {
                const response = await axiosInstance.post("/store/auth/token", { email, password });
                if (response.data.code === 0 && response.data.result.authenticated) {
                    this.token = response.data.result.token;
                    localStorage.setItem("token", this.token);
                    this.user = { email };
                    return true; // Đăng nhập thành công
                } else {
                    this.error = "Sai email hoặc mật khẩu!";
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
                this.user = { email: "admin@gmail.com" }; // Giả lập thông tin người dùng
            }
        },
    },
});
