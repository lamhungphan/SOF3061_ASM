<template>
  <div>
    <ChartControl @updateChart="updateChart" />
    <component :is="chartComponent" :chartData="dataChange" />
  </div>

  <!-- <div>
  <h4>Thống kê doanh thu theo loại hàng</h4>
  <BarChart :chartData="revenueChartData" />

  <h4>Top 10 khách hàng VIP</h4>
  <PieChart :chartData="vipChartData" />
</div> -->

</template>

<script setup>
import ChartControl from "../chart/ChartControll.vue";
import { Chart, registerables } from "chart.js";
import { ref, computed } from "vue";
import {
  DoughnutChart,
  PieChart,
  LineChart,
  BarChart,
  PolarAreaChart,
} from "vue-chart-3";

Chart.register(...registerables);

// Xác định props
const props = defineProps({
  label: Array,
  data: Array,
  revenueByCategory: Array,
  topVipCustomers: Array,
});

// Loại biểu đồ hiện tại
const chartType = ref("line");

// Dữ liệu mặc định cho biểu đồ
const chartData = ref({
  labels: ["1", "2", "3", "4"],
  datasets: [
    {
      label: "data 2",
      data: [10, 12, 22, 40],
      borderColor: "black",
      backgroundColor: ["red", "blue", "gray", "yellow"],
      tension: 0.1,
    },
    {
      label: "data 3",
      data: [10, 22, 22, 40],
      borderColor: "black",
      backgroundColor: ["red", "blue", "gray", "yellow"],
      tension: 0.1,
    },
    {
      label: "data 4",
      data: [1, 22, 32, 40],
      borderColor: "black",
      backgroundColor: ["red", "blue", "gray", "yellow"],
      tension: 0.1,
    },
    {
      label: "data 1",
      data: [1, 2, 2, 40],
      borderColor: "black",
      backgroundColor: ["red", "blue", "gray", "yellow"],
      tension: 0.1,
    },
  ],
});

// Dữ liệu mặc định cho biểu đồ chính
const dataChange = computed(() => {
  if (!props.label || !props.data || props.data.length === 0) {
    return {
      labels: ["1", "2", "3", "4"],
      datasets: [
        {
          label: "Dữ liệu mẫu",
          data: [10, 12, 22, 40],
          borderColor: "black",
          backgroundColor: ["red", "blue", "gray", "yellow"],
          tension: 0.1,
        },
      ],
    };
  }
  return {
    labels: props.label.map(i => i.title),
    datasets: [
      {
        label: props.label[0]?.title || "Dữ liệu",
        data: props.data.map(i => i.value),
        borderColor: "black",
        backgroundColor: ["red", "blue", "gray", "yellow"],
        tension: 0.1,
      },
    ],
  };
});

// Dữ liệu doanh thu theo loại hàng
const revenueChartData = computed(() => {
  if (!props.revenueByCategory || props.revenueByCategory.length === 0) {
    return { labels: [], datasets: [] };
  }

  return {
    labels: props.revenueByCategory.map(i => i.category),
    datasets: [
      {
        label: "Doanh thu theo loại hàng",
        data: props.revenueByCategory.map(i => i.revenue),
        backgroundColor: ["red", "blue", "green", "yellow", "purple", "orange", "cyan", "pink"],
      },
    ],
  };
});

// Dữ liệu khách hàng VIP
const vipChartData = computed(() => {
  if (!props.topVipCustomers || props.topVipCustomers.length === 0) {
    return { labels: [], datasets: [] };
  }

  return {
    labels: props.topVipCustomers.map(i => i.name),
    datasets: [
      {
        label: "Khách hàng VIP",
        data: props.topVipCustomers.map(i => i.total_spent),
        backgroundColor: ["orange", "cyan", "pink", "purple", "lime", "red", "blue", "green"],
      },
    ],
  };
});


// Chọn component biểu đồ tương ứng
const chartComponent = computed(() => {
  switch (chartType.value) {
    case "bar":
      return BarChart;
    case "pie":
      return PieChart;
    case "doughnut":
      return DoughnutChart;
    case "polar":
      return PolarAreaChart;
    default:
      return LineChart;
  }
});

function updateChart(type) {
  chartType.value = type;
}
</script>
