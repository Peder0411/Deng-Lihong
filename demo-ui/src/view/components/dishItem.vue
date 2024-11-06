<template>
  <div class="dish-management">
    <!-- 主内容 -->
    <el-col :span="20">
      <!-- 查询栏 -->
      <el-row :gutter="20" class="filter-row">
        <el-col :span="8">
          <el-input v-model="filters.name" placeholder="菜品名称"></el-input>
        </el-col>
        <el-col :span="8">
          <el-select v-model="filters.category" placeholder="请选择菜品分类">
            <el-option
              v-for="item in categories"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-col>
        <el-col :span="8">
          <el-button type="primary" @click="search">查询</el-button>
        </el-col>
      </el-row>
      <!-- 表格 -->
      <el-table :data="dishes" stripe style=" margin-top: 20px;">
        <el-table-column prop="id" label="编号" width="70"></el-table-column>
        <el-table-column label="菜品图片" width="120">
          <template v-slot="scope">
            <img :src="scope.row.image" alt="" class="dish-image" />
          </template>
        </el-table-column>
        <el-table-column prop="kind" label="描述" width="150"></el-table-column>
        <el-table-column prop="dishName" label="菜品名称"></el-table-column>
        <el-table-column prop="price" label="价格"></el-table-column>
        <el-table-column prop="originalPrice" label="原价"></el-table-column>
        <el-table-column prop="discount" label="折扣" width="80"></el-table-column>
        <el-table-column prop="soldPerMonth" label="月销售" width="80"></el-table-column>
        
        <!-- 上下架列 -->
        <el-table-column label="上下架" width="100">
          <template v-slot="scope">
            <el-switch
              v-model="scope.row.status"
              :active-value="1"
              :inactive-value="0"
              @change="handleStatusChange(scope.row)"
            >
              <span class="switch-text active-text">上架</span>
              <span class="switch-text inactive-text">下架</span>
            </el-switch>
          </template>
        </el-table-column>

        <!-- 操作列 -->
        <el-table-column label="操作" width="130" align="center">
  <template slot-scope="scope">
    <div style="display: flex; gap: 4px;">
      <el-button type="warning" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
      <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
    </div>
  </template>
</el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
        background
        layout="prev, pager, next, jumper"
        :total="totalDishes"
        :current-page="currentPage"
        :page-size="pageSize"
        @current-change="handlePageChange"
      ></el-pagination>
    </el-col>
  </div>
</template>
<script>
import axios from 'axios';
export default {
  name:'dishItem',
  data() {
    return {
      filters: {
        name: '',
        category: ''
      },
      categories: [
        { value: '热菜', label: '热菜' },
        { value: '凉菜', label: '凉菜' },
        // 更多分类
      ],
      dishes: [
        { id: 1000001, name: '宫保鸡丁', price: '¥100.00', image: '', tag: { upper: false, recommend: false }, order: 1, stock: 100, soldOut: false },
        // 更多菜品
      ],
      total: 135 ,// 总菜品数量
      currentPage: 1,
      pageSize: 10,
    }
  },
  methods: {
    //查询所有菜品
    selectAllDish() {
      const params ={
      page: this.currentPage,
      limit: this.pageSize
    }
    axios.get("http://localhost:80/dish/getAll",{params})
      .then((res) => {
        if (res.data.code === "0") {
          this.dishes = res.data.data.map(dish => ({
        ...dish,
        status: Number(dish.status) // 转换 status 为数字类型
      }));
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
search() {
      const selectData = {
    dishName: this.filters.name,
    kind: this.filters.category
  }
axios.post(`http://localhost:80/dish/selectDish?page=${this.currentPage}&limit=${this.pageSize}`, 
    selectData, 
    {
        headers: {
            'Content-Type': 'application/json'
        }
    })
      .then((res) => {
        if (res.data.code === "0") {
          this.dishes = res.data.data.map(dish => ({
        ...dish,
        status: Number(dish.status) // 转换 status 为数字类型
      }));
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
      // 查询逻辑
    },
    handlePageChange(page) {
      this.currentPage = page;
      this.selectAllDish();
    },
  edit(row) {
    // eslint-disable-next-line
    console.log("编辑", row);
  },
  deleteDish(row) {
    axios.delete(`http://localhost:80/dish/delete?id=${row.id}`)
      .then((res) => {
        if (res.data.code === "200") {
          this.selectAllDish();
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
    this.dishes.id = row.id;
    this.dishes.status = row.status;
      // 根据切换的状态，进行相应的处理，比如更新数据库

      this.updateStatusInDatabase();
    },
    updateStatusInDatabase() {
      axios.put(`http://localhost:80/dish/updateById?id=${this.dishes.id}&status=${this.dishes.status}`, null, {
  headers: {
    "Content-Type": "application/json"
  }
})
     .then(res =>{
        if(res.data.code ==="200"){
          this.selectAllDish()
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
   this.selectAllDish()
  
  },
}
</script>

<style scoped>
.filter-row {
  margin-bottom: 20px;
}

.dish-image {
  width: 50px;
  height: 50px;
  object-fit: cover;
}

.pagination {
  margin-top: 20px;
  text-align: center;
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
