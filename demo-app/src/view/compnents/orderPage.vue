<template>
    <div class="food-menu">
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
        <el-main>
          <el-row :gutter="10" class="product-list">
            <el-col :span="24" v-for="(item, index) in products" :key="index">
              <el-card class="product-card">
                <div slot="header" class="product-header">
                  <span>{{ item.name }}</span>
                  <el-button type="text" icon="el-icon-plus" @click="addToCart(item)"></el-button>
                </div>
                <img :src="item.image" class="product-image" />
                <p>月售 {{ item.monthlySales }}</p>
                <p>
                  <span class="price">¥{{ item.discountedPrice }}</span>
                  <span class="original-price">¥{{ item.originalPrice }}</span>
                  <span class="discount">{{ item.discount }}折</span>
                </p>
              </el-card>
            </el-col>
          </el-row>
        </el-main>
      </el-container>
  
      <!-- 底部操作栏 -->
      <el-footer class="footer">
        <el-button type="primary" class="checkout-btn" :disabled="cart.length === 0">去下单</el-button>
      </el-footer>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        searchQuery: '',
        products: [
          {
            name: '香辣鸡腿堡',
            image: 'your-image-url',
            monthlySales: 132,
            discountedPrice: 15.8,
            originalPrice: 16.8,
            discount: '5.6',
          },
          // 更多商品信息
        ],
        cart: [],
      };
    },
    methods: {
      addToCart(item) {
        this.cart.push(item);
      },
    },
  };
  </script>
  
  <style scoped>
  .food-menu {
    max-width: 375px; /* 限制宽度为手机屏幕的大小 */
    margin: 0 auto;
    border: 1px solid #ddd; /* 边框模拟手机外壳 */
    border-radius: 10px;
    overflow: hidden;
    display: flex;
    flex-direction: column;
    height: 100vh;
    background-color: #fff;
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
  
  .product-list {
    padding: 10px;
    overflow-y: auto;
    max-height: calc(100vh - 180px); /* 根据顶部和底部内容动态调整高度 */
  }
  
  .product-card {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  
  .product-image {
    width: 80px;
    height: 80px;
    margin: 10px 0;
  }
  
  .price {
    color: #e74c3c;
    font-size: 16px;
  }
  
  .original-price {
    text-decoration: line-through;
    color: #999;
    margin-left: 5px;
    font-size: 12px;
  }
  
  .discount {
    color: #ff7f50;
    margin-left: 5px;
    font-size: 12px;
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
  