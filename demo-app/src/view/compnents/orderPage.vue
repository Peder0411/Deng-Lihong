<template>
  <div class="food-menu">
    <div class="shop-info">
      <img src="https://s2.loli.net/2024/10/30/tBZ5912kiNfUQDL.png"  class="shop-logo" />
      <div class="shop-details">
        <h3>美滋滋汉堡</h3>
        <p>188 8888 8888</p>
        <el-button size="mini" type="success">营业中</el-button>
      </div>
    </div>
    <!-- 顶部导航栏 -->
    <el-header class="header">
      <span>扫码点餐</span>
      <el-input
        placeholder="搜索商品"
        prefix-icon="el-icon-search"
        class="search-bar"
        v-model="searchQuery"
      ></el-input>
    </el-header>

    <el-container class="main-content">
      <!-- 侧边菜单栏 -->
      <el-aside width="100px" class="sidebar">
        <el-menu default-active="1" class="menu">
          <el-menu-item index="1">热销榜</el-menu-item>
          <el-menu-item index="2">折扣</el-menu-item>
          <el-menu-item index="3">汉堡包</el-menu-item>
          <el-menu-item index="4">鸡肉卷</el-menu-item>
          <el-menu-item index="5">饮品</el-menu-item>
          <el-menu-item index="6">双人套餐</el-menu-item>
          <el-menu-item index="7">单人套餐</el-menu-item>
          <el-menu-item index="8">全家桶</el-menu-item>
          <el-menu-item index="9">营养包</el-menu-item>
        </el-menu>
      </el-aside>

      <!-- 产品列表 -->
      <el-main class="scrollable">
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
          <!-- 添加“+”号按钮 -->
          <el-button type="success" icon="el-icon-plus" size="mini"></el-button>
        </div>
      </el-main>
    </el-container>

    <!-- 底部操作栏 -->
    <el-footer class="footer">
  <div class="total-price">
    <span>总价: ￥{{ totalPrice }}</span>
  </div>
  <el-button type="primary" class="checkout-btn" :disabled="cart.length === 0">去下单</el-button>
</el-footer>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      searchQuery: '',
      menuItems: [],
      cart: [],
    };
  },
  computed: {
  totalPrice() {
    return this.menuItems.reduce((total, item) => total + item.price * item.quantity, 0).toFixed(2);
  }
},
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
    },
    addToCart(item) {
      const menuId = item.id;
      console.log("Selected menu ID:", menuId);
      this.cart.push(item);
    },
  },
  mounted() {
    this.gitAllDish();
  },
};
</script>

<style scoped>
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
.food-menu {
  max-width: 375px;
  margin: 0 auto;
  border: 1px solid #ddd;
  border-radius: 10px;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: #fff;
  
  /* 隐藏滚动条 */
  scrollbar-width: none; /* 针对 Firefox */
}

.header {
  display: flex;
  align-items: center;
  padding: 10px;
  font-size: 18px;
  border-bottom: 1px solid #eee;
}

.search-bar {
  margin-left: 10px;
  flex: 1;
}

.sidebar {
  background-color: #f7f7f7;
  padding: 5px 0;
}

.menu {
  font-size: 14px;
}

.scrollable {
  overflow-y: auto; /* 使内容可以上下滚动 */
  max-height: calc(100vh - 120px); 
  scrollbar-width: none;/* 保留底部和顶部空间 */
}

.menu-item {
  align-items: center;
  justify-content: space-between;
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
  margin-right: 6px; /* 减小图片与内容之间的间距 */
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

.footer {
  display: flex;
  justify-content: center;
  padding: 10px;
  border-top: 1px solid #eee;
}

.checkout-btn {
  width: 90%;
}
</style>
