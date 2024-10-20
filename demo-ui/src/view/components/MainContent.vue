<template>
    <el-row class="main-content">
      <el-row class="status-summary" :gutter="20">
        <el-col :span="8">
          <el-card class="status-card">
            <div class="status-title">全部</div>
            <div class="status-value">50</div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card class="status-card">
            <div class="status-title">使用中</div>
            <div class="status-value">30</div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card class="status-card">
            <div class="status-title">闲置</div>
            <div class="status-value">20</div>
          </el-card>
        </el-col>
      </el-row>
  
      <!-- 最新消息 -->
      <div class="latest-updates">
        <el-table :data="latestOrders">
          <el-table-column prop="time" label="时间" width="120"></el-table-column>
          <el-table-column prop="table" label="桌号" width="120"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button @click="viewOrder(scope.row)" type="text">查看</el-button>
              <el-button @click="printReceipt(scope.row)" type="text">打印小票</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
  
      <!-- 台桌信息 -->
      <div class="table-summary">
        <el-row :gutter="20">
          <el-col :span="6" v-for="table in tables" :key="table.id">
            <el-card class="table-card">
              <div v-if="table.status === 'occupied'" class="table-status occupied">使用中</div>
              <div v-else class="table-status available">空闲中</div>
              <div class="table-title">{{ table.name }}</div>
              <div class="table-details">{{ table.details }}</div>
              <el-button type="primary" size="small" @click="viewTable(table)">查看</el-button>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </el-row>
  </template>
  
  <script>
  export default {
    data() {
      return {
        latestOrders: [
          { time: '12:01', table: '桌001', order: '下单' },
          { time: '12:01', table: '桌002', order: '下单' },
        ],
        tables: [
          { id: 1, name: '001号桌', details: '2人桌', status: 'occupied' },
          { id: 2, name: '002号桌', details: '5人桌', status: 'available' },
          { id: 3, name: '005号桌', details: '8人桌', status: 'occupied' },
        ]
      };
    },
    methods: {
      viewOrder(order) {
        console.log("查看订单: ", order);
      },
      printReceipt(order) {
        console.log("打印小票: ", order);
      },
      viewTable(table) {
        console.log("查看桌子详情: ", table);
      }
    }
  };
  </script>
  
  <style scoped>
  .main-content {
    padding: 20px;
  }
  .status-summary .status-card {
    text-align: center;
    background-color: #f0f0f5;
  }
  
  .status-card .status-title {
    font-size: 18px;
    font-weight: bold;
  }
  
  .status-card .status-value {
    font-size: 36px;
    color: #409EFF;
  }
  
  .latest-updates {
    margin: 20px 0;
  }
  
  .table-summary {
    margin-top: 20px;
  }
  
  .table-card {
    position: relative;
    text-align: center;
  }
  
  .table-status {
    position: absolute;
    top: 10px;
    right: 10px;
    background-color: #f56c6c;
    color: white;
    padding: 2px 10px;
    border-radius: 3px;
  }
  
  .table-status.available {
    background-color: #67C23A;
  }
  
  .table-title {
    font-size: 20px;
    margin-top: 20px;
  }
  
  .table-details {
    color: #999;
  }
  </style>
  