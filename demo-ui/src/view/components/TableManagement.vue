<template>
  <div class="table-management">
    <!-- 筛选区域 -->
    <el-form :inline="true" :model="formInline" class="filter-form">
      <el-form-item label="桌号">
  <el-input v-model="formInline.tableNumber" placeholder="请输入桌号"></el-input>
</el-form-item>
      <el-form-item label="位置">
        <el-select v-model="formInline.tableAddress" placeholder="请选择">
          <el-option label="大厅" value="大厅"></el-option>
          <el-option label="包厢" value="包厢"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="使用状态">
        <el-select v-model="formInline.status" placeholder="请选择">
          <el-option label="使用" value="1"></el-option>
          <el-option label="空闲" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSearch">查询</el-button>
      </el-form-item>
    </el-form>


    <!-- 数据表格 -->
    <el-table :data="tableData" stripe style="width: 200%">
      <el-table-column prop="id" label="编号" width="100"></el-table-column>
      <el-table-column prop="tableNumber" label="桌号" width="100"></el-table-column>
      <el-table-column label="二维码" width="120">
            <template slot-scope="scope">
              <img :src="scope.row.tableImage" alt="" class="table-image"/>
            </template>
          </el-table-column>
      <el-table-column prop="tableAddress" label="桌位" width="120"></el-table-column>
      <el-table-column prop="tableType" label="桌型" width="100"></el-table-column>
      <el-table-column prop="status" label="桌台状态" width="120">
        <template slot-scope="scope">
  <el-tag :type="scope.row.status === 1 ? 'success' : 'warning'">
    {{ scope.row.status === 1 ? '使用中' : '空闲' }}
  </el-tag>
</template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
  <template slot-scope="scope">
    <div style="display: flex; gap: 4px;">
      <el-button type="warning" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
      <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
    </div>
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
        tableNumber: '',
        tableAddress: '',
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
      axios.get("http://localhost:80/tableInfo/selectTable",{params})
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
      axios.post("http://localhost:80/tableInfo/selectAllByConditions",this.formInline)
      .then((res) => {
        if (res.data.code === "200") {
          this.tableData = res.data.data;
          this.$message({
            message: '查询成功',
            type: 'success'
          });
        } else {
          this.$message({
            message: '查询失败',
            type: 'error'
          });
        }
      });
    },
    handleView(row) {
      console.log('查看:', row);
    },
    handleEdit(row) {
      console.log('编辑:', row);
    },
    handleDelete(row) {
      axios.delete(`http://localhost:80/tableInfo/delete?id=${row.id}`)
      .then((res) => {
        if (res.data.code === "200") {
          this.selectAllTable();
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
   
   
  },
  mounted() {
    // 初始时计算total总数
    this.total = this.tableData.length;
    this.selectAllTable()
  }
};
</script>

  
  <style scoped>
 .table-image {
  width: 50px;
  height: 50px;
  object-fit: cover;
}
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
  