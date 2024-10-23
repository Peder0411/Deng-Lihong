<template>
  <div>
    <!-- 搜索栏 -->
    <el-form :inline="true" size="small" class="demo-form-inline">
      <el-form-item label="姓名">
        <el-input v-model="name" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="岗位">
        <el-select v-model="post" placeholder="请选择">
          <el-option label="经理" value="经理"></el-option>
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
      <el-table-column prop="name" label="姓名"></el-table-column>
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
      :total="total"
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
      name: '',
      post: '',
      currentPage: 1,
      pageSize: 5,
      employees: [
      ],
    };
  },
  methods: {


//查询所有
  selectAllUser() {
    axios.get("http://localhost:80/user/getAllUser", {
      params:{
        currentPage: this.currentPage,  
        pageSize: this.pageSize         
      }
    })
      .then((res) => {
        if (res.data.code === "200") {
          this.employees = res.data.data.data;
          this.total = res.data.data.total; 
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
   // 执行查询操作
    handleSearch() {
      this.currentPage = 1;
      const selectData ={
        name: this.name,
        post: this.post,
        currentPage: this.currentPage,
        pageSize:this.pageSize
      }
      axios.post("http://localhost:80/user/selectByConditions",selectData,{
        headers: {
            'Content-Type': 'application/json' // 确保设置请求头
        }
      })
      .then((res) => {
        if (res.data.code === "200") {
          this.employees = res.data.data.data;
          this.total = res.data.data.total; 
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
    handleEdit(row) {
      // 编辑操作
      console.log('编辑:', row);
    },
    // 删除操作
    handleDelete(row) {
      this.employees = this.employees.filter(emp => emp !== row);
      axios.delete(`http://localhost:80/user/delete/${row.id}`)
      .then((res) => {
        if (res.data.code === "200") {
          this.$router.push('/userManagement');
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
    handleStatusChange(row) {
      // 处理启用状态切换
      console.log('状态变化:', row);
    },
    handleSizeChange(size) {
      this.pageSize = size;
      this.selectAllUser()
    },
    handleCurrentChange(page) {
      this.currentPage = page;
      this.selectAllUser()
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
