<template>
  <div>
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
import { useLoginStore } from "@/store/loginStore";
import Chart from "@/components/admin/chart/Chart.vue";
import axios from "@/axios/axios";

// const loginStore = useLoginStore();
// const dashboardData = ref();
// const router = useRouter();
// const label = ref([]);
// const data = ref([]);
// const revenueByCategory = ref([]);
// const topVipCustomers = ref([]);

// const fetchDashboardData = async () => {
//   try {
//     const config = {
//       headers: {
//         Authorization: `Bearer ${loginStore.token}`, // Thêm token vào header
//       },
//     };
//     const response = await axios.get("/store/admin", config);
//     dashboardData.value = response.data;

//     const revenueResponse = await axios.get("/admin/revenue-by-category", config);
//     revenueByCategory.value = revenueResponse.data;

//     const vipResponse = await axios.get("/admin/top-vip-customers", config);
//     topVipCustomers.value = vipResponse.data;

//     label.value = response.data;
//     data.value = response.data;
//   } catch (error) {
//     console.error("Lỗi khi lấy dữ liệu dashboard:", error);
//     if (error.response?.status === 401) {
//       loginStore.logout();
//       router.push("/login");
//     }
//   }
// };

// onMounted(() => {
//   if (loginStore.isAdmin) {
//     fetchDashboardData();
//   } else {
//     router.push("/login");
//   }
// });
</script>