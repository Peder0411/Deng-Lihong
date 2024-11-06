<template>
  <div>
    <v-chart :option="chartOptions" style="width: 600px; height: 400px;"></v-chart>
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
      }
    };
  },
  mounted() {
    this.fetchDishData();
  },
  methods: {
    async fetchDishData() {
      try {
        // 从后端获取数据
        const response = await axios.get('http://localhost:80/dish/gitAllDish');
        if (response.data.code === "200") {
          const dishData = response.data.data;
          // 提取菜品名称和销量数据
          const dishNames = dishData.map(dish => dish.dishName);
          const soldData = dishData.map(dish => dish.soldPerMonth);

          // 更新 chartOptions 数据，使图表重新渲染
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
    }
  }
};
</script>

  