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
      <el-table :data="dishes" stripe style="width: 100%; margin-top: 20px;">
        <el-table-column prop="id" label="编号" width="100"></el-table-column>
        <el-table-column label="菜品图片" width="120">
          <template v-slot="scope">
            <img :src="scope.row.image" alt="" class="dish-image" />
          </template>
        </el-table-column>
        <el-table-column prop="kind" label="种类"></el-table-column>
        <el-table-column prop="dishName" label="菜品名称"></el-table-column>
        <el-table-column prop="price" label="价格"></el-table-column>
        <el-table-column prop="originalPrice" label="原价"></el-table-column>
        <el-table-column prop="discount" label="折扣" width="80"></el-table-column>
        <el-table-column prop="soldPerMonth" label="月销售" width="100"></el-table-column>
        
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
        <el-table-column label="操作" width="120">
          <template v-slot="scope">
            <el-button type="text" @click="view(scope.row)">查看</el-button>
            <el-button type="text" @click="edit(scope.row)">编辑</el-button>
            <el-button type="text" @click="deleteDish(scope.row)">删除</el-button>
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
      tableData: [
        { id: 1, status: 1 }, // 1 表示上架
        { id: 2, status: 0 }, // 0 表示下架
        // 其他数据...
      ],
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
          this.dishes = res.data.data;
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
  },
  handleStatusChange(row) {
      // 根据切换的状态，进行相应的处理，比如更新数据库
      const newStatus = row.status; // 1 或 0
      console.log(`Table ID: ${row.id}, New Status: ${newStatus}`);
      
      // 这里可以调用 API 更新数据库中的状态
      // 例如：
      // this.updateStatusInDatabase(row.id, newStatus);
    },
    updateStatusInDatabase() {
      // 这里写你的 API 调用逻辑
      // axios.post('/api/update-status', { id, status })
      //   .then(response => {
      //     console.log('状态更新成功', response);
      //   })
      //   .catch(error => {
      //     console.error('状态更新失败', error);
      //   });
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
