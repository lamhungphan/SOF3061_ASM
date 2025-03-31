<template>
  <div>
  <h3 class="mb-4">Dashboard Bán Hàng</h3>
  <div class="row">
    <div v-for="(item, index) in dashboardData" :key="index" class="col-12 col-sm-6 col-md-3">
      <div :class="['card', 'text-white', item.bgColor, 'mb-3']" style="max-width: 18rem;">
        <div class="card-header">{{ item.title }}</div>
        <div class="card-body">
          <h5 class="card-title">{{ item.value }}</h5>
          <p class="card-text">{{ item.description }}</p>
        </div>
      </div>
    </div>
    <div>
      <Chart :label="label" :data="data" :revenueByCategory="revenueByCategory" :topVipCustomers="topVipCustomers"></Chart>
    </div>
  </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import Chart from "@/components/admin/chart/Chart.vue";
import axios from "@/axios/axios"; // Import axios instance

const dashboardData = ref();
const router = useRouter();
const label = ref([]);
const data = ref([]);
const revenueByCategory = ref([]);
const topVipCustomers = ref([]);

const fetchDashboardData = async () => {
  try {
    const response = await axios.get("/store/admin");
    dashboardData.value = response.data;

    // Gọi API thống kê doanh thu theo loại hàng
    const revenueResponse = await axios.get("/admin/revenue-by-category");
    revenueByCategory.value = revenueResponse.data;

    // Gọi API danh sách 10 khách hàng VIP
    const vipResponse = await axios.get("/admin/top-vip-customers");
    topVipCustomers.value = vipResponse.data;

    label.value = response.data;
    data.value = response.data;
    console.log(label.value)
    console.log(data.value)
  } catch (error) {
    console.error("Lỗi khi lấy dữ liệu dashboard:", error);
    if (error.response?.status === 401) {
      router.push("/login"); // Chuyển hướng nếu không có quyền truy cập
    }
  }
};

onMounted(fetchDashboardData);
</script>
