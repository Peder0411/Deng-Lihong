<template>
  <div class="table-management">
    <!-- 筛选区域 -->
    <el-form :inline="true" :model="formInline" class="filter-form">
      <el-form-item label="桌位">
        <el-select v-model="formInline.table" placeholder="请选择">
          <el-option label="桌位 1" value="1"></el-option>
          <el-option label="桌位 2" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="桌型">
        <el-select v-model="formInline.type" placeholder="请选择">
          <el-option label="4人桌" value="4"></el-option>
          <el-option label="6人桌" value="6"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="使用状态">
        <el-select v-model="formInline.status" placeholder="请选择">
          <el-option label="使用" value="used"></el-option>
          <el-option label="空闲" value="free"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSearch">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <div class="operation-buttons">
      <el-button type="primary" @click="manageTables">桌位管理</el-button>
      <el-button type="warning" @click="manageTypes">桌型管理</el-button>
      <el-button type="success" @click="createNew">新建</el-button>
    </div>

    <!-- 数据表格 -->
    <el-table :data="tableData" stripe style="width: 200%">
      <el-table-column prop="id" label="编号" width="100"></el-table-column>
      <el-table-column prop="tableNumber" label="桌号" width="100"></el-table-column>
      <el-table-column label="二维码" width="120">
            <template slot-scope="scope">
              <img :src="scope.row.image" alt="" class="table-image"/>
            </template>
          </el-table-column>
      <el-table-column prop="tableAddress" label="桌位" width="120"></el-table-column>
      <el-table-column prop="tableType" label="桌型" width="100"></el-table-column>
      <el-table-column prop="status" label="桌台状态" width="120">
        <template slot-scope="scope">
  <span :style="{ color: scope.row.status === '使用中' ? 'red' : (scope.row.status === '空闲' ? 'green' : 'black') }">
    {{ scope.row.status }}
  </span>
</template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="handleView(scope.row)">查看</el-button>
          <el-button type="text" size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="text" size="small" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      style="margin-top: 20px;">
    </el-pagination>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      formInline: {
        table: '',
        type: '',
        status: ''
      },
      tableData: [
      ],
      currentPage: 1,
      pageSize: 10,
      total: 0,
    };
  },
  computed: {
    paginatedData() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.tableData.slice(start, end);
    }
  },
  methods: {
    selectAllTable(){
      const params ={
      page: this.currentPage,
      limit: this.pageSize
    }
      axios.get("http://localhost:80/table-info/selectTable",{params})
      .then((res) => {
        if (res.data.code === "0") {
          this.tableData= res.data.data;
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
    handleSizeChange(val) {
      this.pageSize = val;
      this.handleCurrentChange(1); // 改变页数后回到第一页
    },
    handleCurrentChange(val) {
      this.currentPage = val;
    },
    onSearch() {
      // 实现筛选逻辑，重新获取数据并更新total
      // 假设筛选结果保存在 this.tableData 中
      this.total = this.tableData.length;
    },
    handleView(row) {
      console.log('查看:', row);
    },
    handleEdit(row) {
      console.log('编辑:', row);
    },
    handleDelete(row) {
      console.log('删除:', row);
    },
    manageTables() {
      console.log('桌位管理');
    },
    manageTypes() {
      console.log('桌型管理');
    },
    createNew() {
      console.log('新建桌位');
    }
  },
  mounted() {
    // 初始时计算total总数
    this.total = this.tableData.length;
    this.selectAllTable()
  }
};
</script>

  
  <style scoped>
  .table-management {
    padding: 20px;
  }
  
  .filter-form {
    margin-bottom: 20px;
  }
  
  .operation-buttons {
    margin-bottom: 20px;
  }
  
  .operation-buttons .el-button {
    margin-right: 10px;
  }
  </style>
  