<script setup>
import { useLoginStore } from "@/store/LoginStore";
import { useRouter } from "vue-router";

const store = useLoginStore();
const router = useRouter();

async function login() {
  const success = await store.login(username.value, password.value);
  if (success) {
    if (store.role === "user") {
      router.push("/");
    } else if (store.role === "manager" || store.role === "director") {
      router.push("/admin");
    }
  } else {
    errorMessage.value = store.error;
  }
}
</script>

<template>
  <div class="login-container">
    <h2>Đăng nhập</h2>
    <form @submit.prevent="login">
      <input v-model="username" type="text" placeholder="Username" required />
      <input
        v-model="password"
        type="password"
        placeholder="Password"
        required
      />
      <button type="submit">Đăng nhập</button>
    </form>
    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
  </div>
</template>

<style scoped>
.login-container {
  width: 300px;
  margin: auto;
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
.error {
  color: red;
  margin-top: 10px;
}
</style>
