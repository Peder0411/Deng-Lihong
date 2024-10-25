<template>
    <div class="order-page">
      <!-- 过滤条件 -->
      <el-form :inline="true" :model="filterForm" class="filter-form">
        <el-form-item label="下单时间">
          <el-date-picker
            v-model="filterForm.orderTime"
            type="daterange"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        
        <el-form-item label="桌号">
          <el-input v-model="filterForm.tableNumber" placeholder="请输入桌号"></el-input>
        </el-form-item>
  
        <el-form-item label="订单状态">
          <el-select v-model="filterForm.status" placeholder="请选择">
            <el-option label="全部" value=""></el-option>
            <el-option label="已结账" value="paid"></el-option>
            <el-option label="未结账" value="unpaid"></el-option>
          </el-select>
        </el-form-item>
  
        <el-form-item>
          <el-button type="primary" @click="searchOrders">查询</el-button>
          <el-button type="success" @click="exportOrders">导出</el-button>
        </el-form-item>
      </el-form>
  
      <!-- 订单表格 -->
      <el-table
        :data="orders"
        style="width: 100%">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="orderId" label="订单编号" width="100"></el-table-column>
        <el-table-column prop="peopleCount" label="用餐人数" width="100"></el-table-column>
        <el-table-column prop="waiter" label="服务人员" width="100"></el-table-column>
        <el-table-column prop="totalAmount" label="订单金额" width="100"></el-table-column>
        <el-table-column prop="tableNumber" label="桌号" width="80"></el-table-column>
        <el-table-column prop="orderTime" label="下单时间" width="130"></el-table-column>
        <el-table-column prop="paymentTime" label="支付时间" width="130"></el-table-column>
        <el-table-column prop="completionTime" label="完成时间" width="130"></el-table-column>
        <el-table-column prop="notes" label="备注" width="100"></el-table-column>
        <el-table-column prop="status" label="订单状态" width="100">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status === 'paid' ? 'success' : 'warning'">
              {{ scope.row.status === 'paid' ? '已结账' : '未结账' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100">
          <template slot-scope="scope">
            <el-button type="text" @click="viewOrder(scope.row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <!-- 分页 -->
      <el-pagination
      background
      layout="prev, pager, next, jumper"
      :total="totalOrders"
      :current-page="currentPage"
      :page-size="pageSize"
      @current-change="handlePageChange">
    </el-pagination>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  export default {
    data() {
      return {
        filterForm: {
          orderTime: '',
          tableNumber: '',
          status: ''
        },
        orders: [], // 存储订单数据
        currentPage: 1,
      pageSize: 10,
      totalOrders: 0,
      };
    },
    methods: {
      //查询所有
      selectAllOrder(){
      const params ={
      page: this.currentPage,
      limit: this.pageSize
    }
      axios.get("http://localhost:80/orders/getAll",{params})
      .then((res) => {
        if (res.data.code === "0") {
          this.orders= res.data.data;
          this.total = res.data.count; 
          this.$message({
            message: '成功',
            type: 'success'
          });
        
        } else {
          this.$message({
            message: '失败',
            type: 'error'
          });
        }
      });

    },
      // 查询订单
      searchOrders() {
        // 模拟请求，实际需调用后端接口
        this.orders = [
          { orderNumber: '1000001', orderTime: '2023-10-23 12:00', totalAmount: '¥450.00', tableNumber: '001', status: 'paid' },
          // 其他订单数据
        ];
        this.totalOrders = this.orders.length;
      },
      // 导出订单
      exportOrders() {
        console.log('导出订单');
        // 实际导出操作
      },
      // 查看订单详情
      viewOrder(order) {
        console.log('查看订单详情', order);
        // 实际的查看逻辑
      },
      // 分页切换
      handlePageChange(page) {
      this.currentPage = page;
      this.selectAllOrder();
    }
    },
    mounted(){
   this.selectAllOrder()
  
  },
  };
  </script>
  
  <style scoped>
  .order-page {
    padding: 20px;
  }
  
  .filter-form {
    margin-bottom: 20px;
  }
  </style>
  