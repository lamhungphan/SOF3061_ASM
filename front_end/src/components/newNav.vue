<!-- src/components/newNav.vue -->
<template>
    <nav class="navbar navbar-expand-lg bg-body-tertiary">
      <div class="container-fluid">
        <a class="navbar-brand" href="#">Navbar scroll</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarScroll" aria-controls="navbarScroll" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarScroll">
          <ul class="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll" style="--bs-scroll-height: 100px;">
            <li class="nav-item">
              <a class="nav-link active" aria-current="page" href="#">Home</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Link</a>
            </li>
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                Link
              </a>
              <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="#">Action</a></li>
                <li><a class="dropdown-item" href="#">Another action</a></li>
                <li><hr class="dropdown-divider"></li>
                <li><a class="dropdown-item" href="#">Something else here</a></li>
              </ul>
            </li>
            <li class="nav-item">
              <a class="nav-link disabled" aria-disabled="true">Link</a>
            </li>
          </ul>
          
          <div class="d-flex align-items-center">
            <!-- Button giỏ hàng -->
            <button class="btn btn-outline-primary me-2 position-relative" type="button">
              <i class="bi bi-cart"></i>
              <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                3
                <span class="visually-hidden">items in cart</span>
              </span>
            </button>
  
            <!-- Button tài khoản với dropdown -->
            <div class="dropdown me-2">
              <button class="btn btn-outline-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                <i class="bi bi-person"></i>
              </button>
              <ul class="dropdown-menu dropdown-menu-end">
                <li><a class="dropdown-item" href="#">Đăng ký</a></li>
                <li><a class="dropdown-item" href="#">Đăng nhập</a></li>
                <li><hr class="dropdown-divider"></li>
                <li><a class="dropdown-item" href="#">Đăng xuất</a></li>
              </ul>
            </div>
  
            <form class="d-flex" role="search">
              <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
              <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
          </div>
        </div>
      </div>
    </nav>
  </template>
  
  <script>
  export default {
    name: 'NewNav',
    mounted() {
      // Kiểm tra xem nút có được render không
      console.log('Dropdown elements:', document.querySelectorAll('.dropdown-toggle'))
    }
  }
  </script>
  
  <style scoped>
  /* Custom styles if needed */
  </style>