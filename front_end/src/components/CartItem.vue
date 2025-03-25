<template>
    <div class="d-flex justify-content-between align-items-center border-bottom py-2">
      <div>
        <h6 class="mb-0">{{ item.name }}</h6>
        <small>{{ item.quantity }} x {{ formatPrice(item.price) }}</small>
      </div>
      <div>
        <strong>{{ formatPrice(item.price * item.quantity) }}</strong>
        <button class="btn btn-sm btn-danger ms-2" @click="cartStore.removeFromCart(item.id)">Xóa</button>
      </div>
    </div>
  </template>
  
  <script setup>
  import { useCartStore } from "@/store/cartStore";
  
  const props = defineProps(["item"]);
  const cartStore = useCartStore();
  
  const formatPrice = (price) => price.toLocaleString("vi-VN") + "₫";
  </script>
  