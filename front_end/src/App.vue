<script setup>
import { RouterView } from 'vue-router'
import HeaderComponent from './components/HeaderComponent.vue'
import FooterComponent from './components/FooterComponent.vue'
import 'bootstrap'

</script>

<template>
  <div class="container-fluid">
    <HeaderComponent />
    <div class="row">
      <div class="col">
        <router-view :key="$route.fullPath"></router-view>
      </div>
    </div>
    <FooterComponent />
  </div>
</template>

<style scoped>
/* Add any custom styles here */
</style>
