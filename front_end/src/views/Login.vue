<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const username = ref("");
const password = ref("");
const errorMessage = ref("");

// async function login() {
//   errorMessage.value = "";
//   try {
//     const response = await fetch("http://localhost:8080/auth/access-token", {
//       method: "POST",
//       headers: { "Content-Type": "application/json" },
//       body: JSON.stringify({ username: username.value, password: password.value }),
//     });

//     if (!response.ok) {
//       throw new Error("Đăng nhập thất bại");
//     }

//     const data = await response.json();
//     localStorage.setItem("accessToken", data.accessToken);
//     localStorage.setItem("refreshToken", data.refreshToken);

//     router.push("/");
//   } catch (error) {
//     errorMessage.value = error.message;
//   }
// }
</script>

<template>
  <div class="login-container">
    <h2>Đăng nhập</h2>
    <form @submit.prevent="login">
      <input v-model="username" type="text" placeholder="Username" required />
      <input v-model="password" type="password" placeholder="Password" required />
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
