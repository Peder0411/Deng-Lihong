<template>
  <div class="app-container">
    <!-- 店铺信息 -->
    <div class="shop-info">
      <img src="https://s2.loli.net/2024/10/30/tBZ5912kiNfUQDL.png"  class="shop-logo" />
      <div class="shop-details">
        <h3>美滋滋汉堡</h3>
        <p>188 8888 8888</p>
        <el-button size="mini" type="success">营业中</el-button>
      </div>

    </div>

    <!-- 快捷功能按钮 -->
    <div class="quick-actions">
      <el-row :gutter="10">
        <el-col :span="12">
          <el-button class="quick-button" type="danger">扫码点餐</el-button>
        </el-col>
        <el-col :span="12">
          <el-button class="quick-button" type="success">优惠券</el-button>
        </el-col>
        <el-col :span="12">
          <el-button class="quick-button" type="success">排队取号</el-button>
        </el-col>
        <el-col :span="12">
          <el-button class="quick-button" type="warning">餐桌预约</el-button>
        </el-col>
      </el-row>
    </div>

    <!-- 菜品列表 -->
    <div class="menu-item" v-for="item in menuItems" :key="item.id">
  <img :src="item.image" class="menu-image" />
  <div class="menu-details">
    <h5>{{ item.dishName }}</h5>
    <p>{{ item.kind.join(', ') }}</p>
    <div class="menu-soldPerMonth">月售 {{ item.soldPerMonth }}</div>
    <div class="menu-pricing">
      <span class="current-price">¥{{ item.price }}</span>
      <span class="original-price">¥{{ item.originalPrice }}</span>
      <span class="discount">{{ item.discount }}</span>
    </div>
    <p :class="{'status-on': item.status === '在售', 'status-off': item.status === '停售'}">{{ item.status }}</p>

  </div>
</div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      menuItems: []
    }
  },
  /**
   * 
   */
   methods: {
    gitAllDish() {
    axios.get("http://localhost:80/dish/getAllDish")
      .then((res) => {
        if (res.data.code === "200") {
          this.menuItems = res.data.data.map(item => {
            // 将 kind 转换为数组，并格式化 status
            return {
              ...item,
              kind: item.kind.split("、"),
              status: item.status === "1" ? "在售" : "停售"
            };
          });
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
      })
      .catch((error) => {
        this.$message({
          message: `请求失败: ${error.message}`,
          type: 'error'
        });
      });
  }

   },
   mounted(){
   this.gitAllDish()
  
  },
}
</script>

<style>
.status-on {
  color: green;
}

.status-off {
  color: gray;
}

.app-container {
  max-width: 375px; /* 模拟手机屏幕宽度 */
  margin: 0 auto;
  background-color: #f5f5f5;
  min-height: 100vh;
  padding-bottom: 16px;
}

.header {
  background-color: #fff;
  padding: 0 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #e0e0e0;
}

.shop-info {
  display: flex;
  align-items: center;
  padding: 16px;
  background-color: #fff;
  margin-top: 8px;
}

.shop-logo {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  margin-right: 16px;
}

.shop-details h3 {
  margin: 0;
  font-size: 18px;
}

.shop-more {
  margin-left: auto;
}

.quick-actions {
  border-radius: 10px;
  padding: 16px;
}

.quick-button {
  width: 100%;
  height: 60px;
  border-radius: 10px;
}

.menu-section {
  padding: 16px;
  background-color: #fff;
  margin-top: 8px;
}

.menu-item {
  display: flex;
  margin-bottom: 12px; /* 减小项之间的间距 */
  background-color: #fff;
  padding: 12px; /* 缩小整体内边距 */
  border-radius: 6px; /* 圆角稍微缩小 */
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1); /* 缩小阴影 */
}

.menu-image {
  width: 60px; /* 减小图片宽度 */
  height: 60px; /* 减小图片高度 */
  border-radius: 6px;
  margin-right: 12px; /* 减小图片与内容之间的间距 */
}

.menu-details h5 {
  font-size: 14px; /* 菜品名字体大小 */
  margin: 0 0 4px 0; /* 减小标题下边距 */
}

.menu-details p,
.menu-soldPerMonth,
.menu-pricing span {
  font-size: 12px; /* 菜品信息字体大小 */
}

.current-price {
  font-size: 14px;
  color: red;
  font-weight: bold;
}

.original-price {
  font-size: 12px;
  text-decoration: line-through;
  color: #999;
  margin-left: 4px;
}

.discount {
  font-size: 12px;
  color: red;
  margin-left: 4px;
}

</style>
