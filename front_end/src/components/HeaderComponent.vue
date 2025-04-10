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
            <RouterLink class="nav-link active" to="/">About Me</RouterLink>
          </li>
        </ul>
        <form
          class="d-flex"
          role="search"
          style="width: 30%"
          @submit="onSearch"
        >
          <input
            v-model="searchKeyword"
            class="form-control w-150 me-2"
            type="search"
            placeholder="Galaxy S25 Ultra"
            aria-label="Search"
          />
          <button class="btn btn-outline-success" type="submit">Search</button>
        </form>

        <ul class="navbar-nav ms-auto d-flex align-items-center">
          <!-- Giỏ hàng -->
          <li class="nav-item me-4">
            <RouterLink to="/cart" class="nav-link position-relative">
              <i class="bi bi-cart nav-icon"></i>
              <span v-if="cartQuantity > 0" class="badge bg-danger">
                {{ cartQuantity }}
              </span>
            </RouterLink>
          </li>

          <!-- Tài khoản -->
          <li class="nav-item me-4 dropdown">
            <a
              class="nav-link dropdown-toggle"
              href="#"
              id="accountDropdown"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              <i class="bi bi-person nav-icon"></i>
              <span v-if="loginStore.isAuthenticated">{{
                loginStore.user?.username
              }}</span>
            </a>
            <ul
              class="dropdown-menu dropdown-menu-end"
              aria-labelledby="accountDropdown"
            >
              <!-- Khi chưa đăng nhập -->
              <template v-if="!loginStore.isAuthenticated">
                <li>
                  <a
                    class="dropdown-item"
                    href="/login"
                  >
                    Đăng nhập
                  </a>
                </li>
                <li>
                  <a
                    class="dropdown-item"
                    href="/register"
                  >
                    Đăng ký
                  </a>
                </li>
                <li>
                  <a
                    class="dropdown-item"
                    href="#"
                  >
                    Quên mật khẩu
                  </a>
                </li>
              </template>

              <!-- Khi đã đăng nhập -->
              <template v-else>
                <li>
                  <a
                    class="dropdown-item"
                    href="#"
                    @click.prevent="goToOrderHistory()"
                  >
                    Lịch sử mua hàng
                  </a>
                </li>
                <li>
                  <a
                    class="dropdown-item"
                    href="/change-password"
                  >
                    Đổi mật khẩu
                  </a>
                </li>
                <li>
                  <a
                    class="dropdown-item"
                    href="/edit-profile"
                  >
                    Sửa thông tin
                  </a>
                </li>
                <li>
                  <a
                    class="dropdown-item"
                    href="#"
                    @click.prevent="handleLogout"
                  >
                    Đăng xuất
                  </a>
                </li>
              </template>
            </ul>
          </li>

          <li class="nav-item d-flex align-items-center">
            <div class="form-check form-switch m-0">
              <input
                class="form-check-input"
                type="checkbox"
                id="langSwitch"
                @change="toggleLanguage"
              />
              <label class="form-check-label text-white" for="langSwitch">
                <span id="langLabel">EN</span>
              </label>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useCartStore } from "@/store/cartStore";
import { useLoginStore } from "@/store/loginStore";
import { useProductStore } from "@/store/productStore";

const router = useRouter();
const cartStore = useCartStore();
const loginStore = useLoginStore();
const productStore = useProductStore();
const searchKeyword = ref("");

const cart = computed(() => cartStore.cart);
const cartQuantity = computed(() => cart.value.length);

const userId = computed(() => loginStore.user?.id || null);

onMounted(async () => {
  await cartStore.initializeCart(userId.value);
});

const onSearch = (e) => {
  e.preventDefault();
  if (searchKeyword.value.trim()) {
    productStore.searchProducts(searchKeyword.value.trim());
  }
};

function handleLogout() {
  loginStore.logout();
  router.push("/");
}

const goToOrderHistory = () => {
  router.push(`/order/user/${userId.value}`);
};

function toggleLanguage() {
  const checkbox = document.getElementById("langSwitch");
  const label = document.getElementById("langLabel");

  if (checkbox.checked) {
    label.textContent = "VI";
    // Thêm xử lý chuyển sang tiếng Việt tại đây
  } else {
    label.textContent = "EN";
    // Thêm xử lý chuyển sang tiếng Anh tại đây
  }
}
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
  width: 150px;
}

.form-check-label span {
  display: inline-block;
  width: 50px;
  text-align: center;
}
</style>