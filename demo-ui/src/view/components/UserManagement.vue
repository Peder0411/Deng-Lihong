<template>
  <div>
    <!-- 搜索栏 -->
    <el-form :inline="true" size="small" class="demo-form-inline">
      <el-form-item label="姓名">
        <el-input v-model="searchName" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="岗位">
        <el-select v-model="selectedPosition" placeholder="请选择">
          <el-option label="前台经理" value="前台经理"></el-option>
          <el-option label="收银" value="收银"></el-option>
          <el-option label="服务员" value="服务员"></el-option>
          <el-option label="传菜员" value="传菜员"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSearch">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- 员工列表 -->
    <el-table :data="employees" style="width: 100%">
      <el-table-column prop="username" label="姓名"></el-table-column>
      <el-table-column prop="post" label="岗位名称"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="phone" label="电话号码"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column prop="status" label="状态" width="120">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" @change="handleStatusChange(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      layout="total, sizes, prev, pager, next, jumper"
      :total="employees.length"
      :current-page="currentPage"
      :page-size="pageSize"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    ></el-pagination>
  </div>
</template>
<script>
import axios from 'axios';
export default {
  data() {
    return {
      searchName: '',
      selectedPosition: '',
      currentPage: 1,
      pageSize: 10,
      employees: [
      ],
    };
  },
  methods: {

  selectAllUser() {
    axios.get("http://localhost:80/user/getAllUser")
      .then((res) => {
        if (res.data.code === "200") {
          this.employees = res.data.data;
          this.$message({
            message: '成功',
            type: 'info'
          });
        
        } else {
          this.$message({
            message: '失败',
            type: 'info'
          });
        }
      });
  },
    handleSearch() {
      // 执行查询操作
      this.currentPage = 1;
    },
    handleEdit(row) {
      // 编辑操作
      console.log('编辑:', row);
    },
    handleDelete(row) {
      // 删除操作
      this.employees = this.employees.filter(emp => emp !== row);
    },
    handleStatusChange(row) {
      // 处理启用状态切换
      console.log('状态变化:', row);
    },
    handleSizeChange(size) {
      this.pageSize = size;
    },
    handleCurrentChange(page) {
      this.currentPage = page;
    },
  },
    mounted(){
      this.selectAllUser()
    },
  
};
</script>
<style scoped>
.demo-form-inline {
  margin-bottom: 20px;
}
</style>
