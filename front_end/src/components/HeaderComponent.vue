<template>
  <nav class="navbar navbar-expand-lg">
    <div class="container-fluid">
      <a class="navbar-brand" href="/">
        <img src="https://samcenter.vn/images/thumbs/0005426_0003182_logo%20(2).png" alt="Logo" width="120" />
      </a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarScroll"
        aria-controls="navbarScroll" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarScroll">
        <ul class="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll" style="--bs-scroll-height: 100px">
          <li class="nav-item">
            <RouterLink class="nav-link active" aria-current="page" to="/">Samsung Galaxy</RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink class="nav-link active" aria-current="page" to="/">Galaxy Tab</RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink class="nav-link active" aria-current="page" to="/">Galaxy Watch</RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink class="nav-link active" aria-current="page" to="/">Phụ kiện</RouterLink>
          </li>
        </ul>
        <form class="d-flex" role="search">
          <input class="form-control me-2" type="search" placeholder="Galaxy S25 Ultra" aria-label="Search" />
          <button class="btn btn-outline-success" type="submit">Search</button>
        </form>

        <ul class="navbar-nav ms-3">
          <li class="nav-item dropdown" @mouseover="showCart = true" @mouseleave="showCart = false">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              <i class="fa-solid fa-cart-shopping nav-icon"></i>
              <span v-if="cartQuantity > 0" class="badge">{{
                cartQuantity
              }}</span>
            </a>
            <div v-if="showCart" class="cart-dropdown">
              <div v-if="cart.length === 0" class="text-center p-2">
                Giỏ hàng trống
              </div>
              <div v-else>
                <ul class="list-group">
                  <li v-for="item in cart" :key="item.product_id"
                    class="list-group-item d-flex justify-content-between align-items-center">
                    {{ item.name }}
                    <span>{{ formatPrice(item.price) }} ₫</span>
                    <button class="btn btn-sm btn-danger" @click="removeFromCart(item.product_id)">
                      Xóa
                    </button>
                  </li>
                </ul>
                <div class="text-center p-2">
                  <strong>Tổng: {{ formatPrice(totalPrice) }} ₫</strong>
                  <button class="btn btn-primary btn-sm mt-2" @click="buyCart">
                    Mua hàng
                  </button>
                </div>
              </div>
            </div>
          </li>
          <li class="nav-item dropdown" @mouseover="showAccountDropdown = true"
            @mouseleave="showAccountDropdown = false">
            <a class="nav-link dropdown-toggle" href="#" role="button">
              Tài khoản
            </a>
            <ul class="dropdown-menu" :class="{ show: showAccountDropdown }">
              <li>
                <a class="dropdown-item" href="#" @click="showLogin">Đăng nhập</a>
              </li>
              <li>
                <a class="dropdown-item" href="#" @click="showRegister">Đăng ký</a>
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { ref, computed } from "vue";

const showCart = ref(false);
const cart = ref([
  { product_id: 1, name: "Sản phẩm A", price: 100000 },
  { product_id: 2, name: "Sản phẩm B", price: 200000 },
]);

const cartQuantity = computed(() => cart.value.length);
const totalPrice = computed(() =>
  cart.value.reduce((sum, item) => sum + item.price, 0)
);

const removeFromCart = (id) => {
  cart.value = cart.value.filter((item) => item.product_id !== id);
};

const formatPrice = (price) => {
  return price.toLocaleString("vi-VN");
};

const showLogin = () => alert("Hiển thị modal đăng nhập");
const showRegister = () => alert("Hiển thị modal đăng ký");
const buyCart = () => alert("Mua hàng thành công");
</script>

<style scoped>
.navbar {
  background-color: #1d1d1f;
}

.navbar-nav .nav-link {
  color: white !important;
  transition: color 0.3s ease;
}

.navbar-nav .nav-link:hover {
  color: #f8c146 !important;
}

.nav-icon {
  font-size: 1.3rem;
  margin-left: 15px;
  cursor: pointer;
  color: white !important;
  transition: color 0.3s ease;
}

.nav-icon:hover {
  color: #f8c146;
}

.badge {
  position: absolute;
  top: -5px;
  right: -10px;
  background: red;
  color: white;
  font-size: 12px;
  border-radius: 50%;
  padding: 3px 7px;
}

.cart-dropdown {
  position: absolute;
  right: 0;
  background: white;
  border: 1px solid #ccc;
  width: 250px;
  box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
  z-index: 10;
}

.cart-dropdown ul {
  max-height: 200px;
  overflow-y: auto;
}

.navbar .dropdown-menu {
  min-width: 150px;
  background-color: white;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15);
}
</style>