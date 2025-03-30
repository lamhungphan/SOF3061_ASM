<script setup>
import { RouterView } from "vue-router";
</script>

<template>
  <router-view :key="$route.fullPath"></router-view>
</template>

<style scoped>
</style>
