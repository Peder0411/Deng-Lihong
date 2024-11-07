<template>
  <div class="order-page">
    <!-- 过滤条件 -->
    <el-form :inline="true" :model="filterForm" class="filter-form">
      <el-form-item label="下单时间">
        <el-date-picker
          v-model="filterForm.orderTime"
          type="datetime"
          placeholder="选择日期时间"
          format="yyyy-MM-dd HH:mm:ss"
          value-format="yyyy-MM-dd HH:mm:ss"
        ></el-date-picker>
      </el-form-item>

      <el-form-item label="桌号">
        <el-input v-model="filterForm.tableNumber" placeholder="请输入桌号"></el-input>
      </el-form-item>

      <el-form-item label="订单状态">
        <el-select v-model="filterForm.status" placeholder="请选择">
          <el-option label="已结账" value="1"></el-option>
          <el-option label="未结账" value="0"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="searchOrders">查询</el-button>
        <el-button type="success" @click="exportOrders">导出</el-button>
      </el-form-item>
    </el-form>

    <!-- 订单表格 -->
    <el-table :data="orders" style="width: 100%">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="orderId" label="订单编号" width="80"></el-table-column>
      <el-table-column prop="peopleCount" label="用餐人数" width="80"></el-table-column>
      <el-table-column prop="waiter" label="服务人员" width="80"></el-table-column>
      <el-table-column prop="totalAmount" label="订单金额" width="80"></el-table-column>
      <el-table-column prop="tableNumber" label="桌号" width="80"></el-table-column>
      <el-table-column prop="orderTime" label="下单时间" width="130"></el-table-column>
      <el-table-column prop="paymentTime" label="支付时间" width="130"></el-table-column>
      <el-table-column prop="completionTime" label="完成时间" width="130"></el-table-column>
      <el-table-column prop="notes" label="备注" width="80"></el-table-column>
      <el-table-column prop="status" label="订单状态" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : 'warning'">
            {{ scope.row.status === 1 ? '已结账' : '未结账' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="220" align="center">
        <template slot-scope="scope">
          <div style="display: flex; gap: 8px;">
            <el-button type="info" size="small" @click="viewOrder(scope.row)">查看</el-button>
            <el-button type="success" size="small" @click="updateStatus(scope.row)">完成</el-button>
            <el-button type="primary" size="small" @click="editOrder(scope.row)">编辑</el-button>
          </div>
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
      @current-change="handlePageChange"
    ></el-pagination>

    <!-- 编辑订单弹窗 -->
    <el-dialog title="编辑订单" :visible.sync="editDialogVisible">
      <el-form :model="editForm">
        <el-form-item label="服务人员">
          <el-input v-model="editForm.waiter"></el-input>
        </el-form-item>
        <el-form-item label="支付时间">
          <el-date-picker
            v-model="editForm.paymentTime"
            type="datetime"
            placeholder="选择支付时间"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="完成时间">
          <el-date-picker
            v-model="editForm.completionTime"
            type="datetime"
            placeholder="选择完成时间"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
          ></el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveEdit">保存</el-button>
      </div>
    </el-dialog>
        <!-- 查看订单详情弹窗 -->
        <el-dialog title="订单详情" :visible.sync="orderDetailsDialogVisible">
      <el-table :data="orderDetails" style="width: 100%">
        <el-table-column prop="orderId" label="订单编号" width="100"></el-table-column>
        <el-table-column prop="dishId" label="菜品编号" width="100"></el-table-column>
        <el-table-column prop="dishName" label="菜品名称" width="200"></el-table-column>
        <el-table-column prop="dishPrice" label="菜品价格" width="100"></el-table-column>
        <el-table-column prop="dishImage" label="菜品图片" width="120">
          <template slot-scope="scope">
            <img :src="scope.row.dishImage" alt="菜品图片" style="width: 50px; height: 50px;" />
          </template>
        </el-table-column>
        <el-table-column prop="quantity" label="数量" width="100"></el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="orderDetailsDialogVisible = false">关闭</el-button>
      </div>
    </el-dialog>
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
      orderDetailsDialogVisible: false,
      orderDetails: [],
      editDialogVisible: false,
      editForm: {
        tableId: '',
        peopleCount: '',
        waiter: '',
        totalAmount: '',
        notes: ''
      }
    };
  },
  methods: {
    selectAllOrder() {
      const params = {
        page: this.currentPage,
        limit: this.pageSize
      };
      axios.get("http://localhost:80/orders/getAll", { params }).then((res) => {
        if (res.data.code === "0") {
          this.orders = res.data.data;
          this.totalOrders = res.data.count;
          this.$message({ message: '成功', type: 'success' });
        } else {
          this.$message({ message: '失败', type: 'error' });
        }
      });
    },
    searchOrders() {
      axios.post("http://localhost:80/orders/selectByConditions", this.filterForm, {
        headers: { 'Content-Type': 'application/json' }
      }).then((res) => {
        if (res.data.code === "200") {
          this.orders = res.data.data;
          this.$message({ message: '成功', type: 'success' });
        } else {
          this.$message({ message: '失败', type: 'error' });
        }
      });
    },
    exportOrders() {
      console.log('导出订单');
    },
    viewOrder(order) {
      this.orderDetailsDialogVisible = true; // 打开详情弹窗
      // 获取订单详情
      axios.get(`http://localhost:80/order-detail/getByOrderId?tableId=${order.orderId}`).then((res) => {
        if (res.data.code === "200") {
          this.orderDetails = res.data.data; // 设置菜品信息
        } else {
          this.$message({ message: '获取订单详情失败', type: 'error' });
        }
      });
    },
    updateStatus(row) {
      axios.put(`http://localhost:80/orders/updateStatus?id=${row.orderId}&tableNumber=${row.tableNumber}`).then((res) => {
        if (res.data.code === "200") {
          this.selectAllOrder();
          this.$message({ message: '成功', type: 'success' });
        } else {
          this.$message({ message: '失败', type: 'error' });
        }
      });
    },
    handlePageChange(page) {
      this.currentPage = page;
      this.selectAllOrder();
    },
    editOrder(order) {
      this.editForm = { ...order };
      this.editDialogVisible = true;
    },
    saveEdit() {
      axios.put(`http://localhost:80/orders/updateBy`, this.editForm).then((res) => {
        if (res.data.code === "200") {
          this.selectAllOrder();
          this.editDialogVisible = false;
          this.$message({ message: '编辑成功', type: 'success' });
        } else {
          this.$message({ message: '编辑失败', type: 'error' });
        }
      });
    }
  },
  mounted() {
    this.selectAllOrder();
  }
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
