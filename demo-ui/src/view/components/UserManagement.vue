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
      <el-table-column label="是否在职" width="100">
          <template v-slot="scope">
            <el-switch
              v-model="scope.row.status"
              :active-value="1"
              :inactive-value="0"
              @change="handleStatusChange(scope.row)"
            >
              <span class="switch-text active-text">在职</span>
              <span class="switch-text inactive-text">请假</span>
            </el-switch>
          </template>
        </el-table-column>
h
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
          this.employees = res.data.data.data.map(user => ({
        ...user,
        status: Number(user.status) // 转换 status 为数字类型
      }));
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
          this.employees = res.data.data.data.map(user => ({
        ...user,
        status: Number(user.status) // 转换 status 为数字类型
      }));
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
    handleSizeChange(size) {
      this.pageSize = size;
      this.selectAllUser()
    },
    handleCurrentChange(page) {
      this.currentPage = page;
      this.selectAllUser()
    },
    handleStatusChange(row) {
    this.employees.id = row.id;
    this.employees.status = row.status;
      this.updateStatusInDatabase();
    },
    updateStatusInDatabase() {
      axios.put(`http://localhost:80/user/updateById?id=${this.employees.id}&status=${this.employees.status}`, null, {
  headers: {
    "Content-Type": "application/json"
  }
})
     .then(res =>{
        if(res.data.code ==="200"){
          this.selectAllUser()
          this.$message({
            message: '成功',
            type: 'success'
          });
        }else{
          this.$message({
            message: '失败',
            type: 'error'
          });
        }
      })
    }
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
.switch-text {
  display: inline-block;            /* 让文本块显示为行内块元素 */
  transform: rotate(90deg);        /* 旋转 90 度 */
  transform-origin: left bottom;    /* 旋转的基点 */
  white-space: nowrap;              /* 不换行 */
  margin: 0 5px;                   /* 添加适当的左右间距 */
  font-size: 12px;                  /* 根据需要调整字体大小 */
  color: #409EFF;                   /* 根据需要调整字体颜色 */
}

.el-switch__label {
  display: none;                    /* 隐藏原本的标签 */
}
.switch-text {
  display: none;
}
.el-switch.is-active .active-text,
.el-switch:not(.is-active) .inactive-text {
  display: inline;
}
</style>
