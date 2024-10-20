<template>
  <div class="dish-management">
      <!-- 主内容 -->
      <el-col :span="20"> <!-- 这里的20是为了占满剩下的部分 -->
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
        <el-table :data="dishes" stripe style="width: 100%; margin-top: 20px;">
          <el-table-column prop="id" label="编号" width="100"></el-table-column>
          <el-table-column label="菜品图片" width="120">
            <template slot-scope="scope">
              <img :src="scope.row.image" alt="" class="dish-image"/>
            </template>
          </el-table-column>
          <el-table-column prop="kind" label="种类"></el-table-column>
          <el-table-column prop="dishName" label="菜品名称"></el-table-column>
          <el-table-column prop="price" label="价格"></el-table-column>
          <el-table-column prop="originalPrice" label="原价"></el-table-column>
          <el-table-column prop="discount" label="折扣" width="80"></el-table-column>
          <el-table-column prop="soldPerMonth" label="月销售" width="100"></el-table-column>
          <el-table-column label="售罄状态" width="100">
            <template slot-scope="scope">
              <el-checkbox v-model="scope.row.soldOut"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="120">
            <template slot-scope="scope">
              <el-button type="text" @click="view(scope.row)">查看</el-button>
              <el-button type="text" @click="edit(scope.row)">编辑</el-button>
              <el-button type="text" @click="deleteDish(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <!-- 分页 -->
        <div class="pagination">
          <el-pagination
            background
            layout="total, prev, pager, next"
            :total="dishes.length"
            :page-size="10"
            @current-change="handlePageChange"
          ></el-pagination>
        </div>
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
        { value: '1', label: '热菜' },
        { value: '2', label: '凉菜' },
        // 更多分类
      ],
      dishes: [
        { id: 1000001, name: '宫保鸡丁', price: '¥100.00', image: '', tag: { upper: false, recommend: false }, order: 1, stock: 100, soldOut: false },
        // 更多菜品
      ],
      total: 135 // 总菜品数量
    }
  },
  methods: {
    //查询所有菜品
    selectAllDish() {
    axios.get("http://localhost:80/dish/getAll")
      .then((res) => {
        if (res.data.code === "200") {
          this.dishes = res.data.data;
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
    search() {
      // 查询逻辑
    },
    handlePageChange(page) {
    // eslint-disable-next-line
    console.log("当前页:", page);
  },
  view(row) {
    // eslint-disable-next-line
    console.log("查看", row);
  },
  edit(row) {
    // eslint-disable-next-line
    console.log("编辑", row);
  },
  deleteDish(row) {
    // eslint-disable-next-line
    console.log("删除", row);
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
</style>
