<template>
  <div class="login-container">
    <h2>Đăng nhập</h2>

    <form @submit.prevent="login">
      <input v-model="username" type="text" placeholder="Username" required />
      <input v-model="password" type="password" placeholder="Password" required />
      <button type="submit" :disabled="store.loading">
        {{ store.loading ? "Đang đăng nhập..." : "Đăng nhập" }}
      </button>
    </form>

    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>

    <p v-if="store.isAuthenticated">
      Đã đăng nhập với {{ store.user.username }}. 
      <a href="#" @click.prevent="router.push('/')">Về trang chủ</a>
    </p>
  </div>
</template>

<script setup>
import { useLoginStore } from "@/store/LoginStore";
import { ref } from "vue";
import { useRouter } from "vue-router";

const store = useLoginStore();
const router = useRouter();
const username = ref("");
const password = ref("");
const errorMessage = ref("");

async function login() {
  const success = await store.login(username.value, password.value);
  if (success) {
    if (store.isAdmin) {
      router.push("/admin");
    } else {
      router.push("/");
    }
  } else {
    errorMessage.value = store.error;
  }
}
</script>

<style scoped>
.login-container {
  width: 300px;
  margin: 50px auto;
  text-align: center;
}
input {
  display: block;
  width: 100%;
  margin: 10px 0;
  padding: 8px;
}
button {
  width: 100%;
  padding: 8px;
  background: blue;
  color: white;
  border: none;
  cursor: pointer;
}
button:disabled {
  background: gray;
  cursor: not-allowed;
}
.error {
  color: red;
  margin-top: 10px;
}
</style>