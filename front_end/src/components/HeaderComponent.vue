<template>
  <nav class="navbar navbar-expand-lg">
    <div class="container-fluid">
      <a class="navbar-brand" href="/">
        <img
          src="https://samcenter.vn/images/thumbs/0005426_0003182_logo%20(2).png"
          alt="Logo"
          width="120"
        />
      </a>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarScroll"
        aria-controls="navbarScroll"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarScroll">
        <ul class="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll">
          <li class="nav-item">
            <RouterLink class="nav-link active" to="/"
              >Samsung Galaxy</RouterLink
            >
          </li>
          <li class="nav-item">
            <RouterLink class="nav-link active" to="/">Galaxy Tab</RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink class="nav-link active" to="/">Galaxy Watch</RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink class="nav-link active" to="/">Phụ kiện</RouterLink>
          </li>
        </ul>
        <form class="d-flex" role="search">
          <input
            class="form-control me-2"
            type="search"
            placeholder="Galaxy S25 Ultra"
            aria-label="Search"
          />
          <button class="btn btn-outline-success" type="submit">Search</button>
        </form>

        <ul class="navbar-nav ms-2">
        <!-- Giỏ hàng -->
          <li class="nav-item">
            <RouterLink to="/cart" class="nav-link position-relative">
              <i class="bi bi-cart nav-icon"></i>
              <span v-if="cartQuantity > 0" class="badge bg-danger">
                {{ cartQuantity }}
              </span>
            </RouterLink>
          </li>

          <!-- Tài khoản -->
          <li class="nav-item dropdown">
            <a
              class="nav-link dropdown-toggle"
              href="#"
              id="accountDropdown"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              <i class="bi bi-person nav-icon"></i>
            </a>
            <ul
              class="dropdown-menu dropdown-menu-end"
              aria-labelledby="accountDropdown"
            >
              <li>
                <a class="dropdown-item" href="#" @click.prevent="showLogin"
                  >Đăng nhập</a
                >
              </li>
              <li>
                <a class="dropdown-item" href="#" @click.prevent="showRegister"
                  >Đăng ký</a
                >
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useCartStore } from "@/store/cartStore";

const cartStore = useCartStore();
const userId = 2; // User giả định, sau này lấy từ auth

onMounted(() => {
  cartStore.fetchCart(userId);
});

const cart = computed(() => cartStore.cart);
const cartQuantity = computed(() => cart.value.length);
const totalPrice = computed(() =>
  cart.value.reduce((sum, item) => sum + item.price * item.quantity, 0)
);

const formatPrice = (price) => price.toLocaleString("vi-VN");

const removeItem = async (productId) => {
  await cartStore.removeFromCart(userId, productId);
};

const clearCart = async () => {
  await cartStore.clearCart(userId);
};

const showLogin = () => {
  console.log("Show login modal");
  // Logic hiển thị modal đăng nhập
};

const showRegister = () => {
  console.log("Show register modal");
  // Logic hiển thị modal đăng ký
};
</script>

<style scoped>
.navbar {
  background-color: #1d1d1f;
}

.nav-link {
  color: white !important;
}

.nav-link:hover {
  color: #f8c146 !important;
}

.nav-icon {
  font-size: 1.3rem;
  color: white;
}

.nav-icon:hover {
  color: #f8c146;
}

.badge {
  position: absolute;
  top: 0;
  right: 0;
  font-size: 12px;
  padding: 3px 6px;
}

.dropdown-menu {
  width: 250px;
}
</style>