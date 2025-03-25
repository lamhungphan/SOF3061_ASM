<template>
  <div id="heroBanner" class="carousel slide" data-bs-ride="carousel">
    <div class="carousel-inner">
      <div v-for="(image, index) in images" :key="index" :class="['carousel-item', { active: index === 0 }]">
        <img :src="image" class="d-block w-100" alt="Banner Image">
      </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#heroBanner" data-bs-slide="prev">
      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#heroBanner" data-bs-slide="next">
      <span class="carousel-control-next-icon" aria-hidden="true"></span>
    </button>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const images = ref([
  "https://samcenter.vn/images/uploaded/banner/2024/thang_6/tongdpc.png",
  "https://samcenter.vn/images/uploaded/banner/2024/Banner%20webFF.png",
  "https://samcenter.vn/images/uploaded/banner/2024/thang_6/s24pc.png"
]);
</script>

<style scoped>
.carousel-item img {
  height: 400px; /* Điều chỉnh chiều cao banner */
  object-fit: cover;
}
</style>


<style>
.advertising {
  position: relative;
  width: 100%;
  overflow: hidden;
  height: auto;
}

.slider {
  justify-content: center;
}

.left-ad {
  width: 100%;
  display: flex;
  justify-content: center;
}

.left-ad img {
  width: 80%;
  height: 100%;
  display: none;
}

img.displaySlide {
  display: block;
  animation: fade;
  animation-duration: 1.1s;
}

.slider button {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  font-size: 4rem;
  padding: 5px 15px;
  background-color: #f4f4f4;
  color: #777;
  border: none;
  border-radius: 10px;
  cursor: pointer;
}

.slider button:hover {
  color: #4e4d4d;
}

.prev {
  left: 14%;
}

.next {
  right: 14%;
}

@keyframes fade {
  from {
    opacity: 0.5;
  }

  to {
    opacity: 1;
  }
}
</style>