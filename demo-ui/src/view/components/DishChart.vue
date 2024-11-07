<template>
  <div class="chart-container">
    <v-chart :option="chartOptions" class="chart" style="height: 400px;"></v-chart>
    <v-chart :option="revenueChartOptions" class="chart" style="height: 400px;"></v-chart>
  </div>
</template>

<script>
import axios from 'axios';
import ECharts from 'vue-echarts';
import 'echarts';

export default {
  components: {
    'v-chart': ECharts
  },
  data() {
    return {
      chartOptions: {
        title: {
          text: '菜品销量统计'
        },
        tooltip: {},
        xAxis: {
          type: 'category',
          data: [] // 用于存储菜品名称
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '销量',
            type: 'bar',
            data: [] // 用于存储每月销量
          }
        ]
      },
      revenueChartOptions: {
        title: {
          text: '每天营业额统计'
        },
        tooltip: {},
        xAxis: {
          type: 'category',
          data: [] // 存储日期
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '营业额',
            type: 'line',
            data: [] // 存储每日营业额数据
          }
        ]
      }
    };
  },

  mounted() {
    this.fetchDishData();
    this.fetchAndAggregateOrders();
  },
  methods: {
    async fetchDishData() {
      try {
        const response = await axios.get('http://localhost:80/dish/gitAllDish');
        if (response.data.code === "200") {
          const dishData = response.data.data;
          const dishNames = dishData.map(dish => dish.dishName);
          const soldData = dishData.map(dish => dish.soldPerMonth);

          this.chartOptions = {
            ...this.chartOptions,
            xAxis: {
              ...this.chartOptions.xAxis,
              data: dishNames
            },
            series: [
              {
                ...this.chartOptions.series[0],
                data: soldData
              }
            ]
          };
        } else {
          console.error("获取数据失败:", response.data.msg);
        }
      } catch (error) {
        console.error("请求失败:", error);
      }
    },
    async fetchAndAggregateOrders() {
      try {
        const response = await axios.get('http://localhost/orders/getAll?page=1&limit=10');
        if (response.data.code === "0") {
          const orders = response.data.data;
          this.aggregateRevenueByDate(orders);
        } else {
          console.error("获取数据失败:", response.data.msg);
        }
      } catch (error) {
        console.error("请求失败:", error);
      }
    },
    aggregateRevenueByDate(orders) {
      const dailyRevenue = {};

      orders.forEach(order => {
        const date = order.orderTime.split(' ')[0];
        if (!dailyRevenue[date]) {
          dailyRevenue[date] = 0;
        }
        dailyRevenue[date] += order.totalAmount;
      });

      const dates = Object.keys(dailyRevenue);
      const revenueData = Object.values(dailyRevenue);

      this.revenueChartOptions = {
        ...this.revenueChartOptions,
        xAxis: {
          ...this.revenueChartOptions.xAxis,
          data: dates
        },
        series: [
          {
            ...this.revenueChartOptions.series[0],
            data: revenueData
          }
        ]
      };
    }
  }
};
</script>

<style scoped>
.chart-container {
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  gap: 20px; /* 图表之间的间距 */
  height: 100vh; /* 使容器高度占满屏幕 */
}

.chart {
  flex: 1;
  max-width: 600px; /* 限制每个图表的最大宽度 */
  height: 400px;
}
</style>
