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
          <el-button type="success" icon="el-icon-plus" size="mini" @click="addToCart(item)"></el-button>
        </div>
      </el-main>
    </el-container>
    <!-- 底部操作栏 -->
    <el-footer class="footer">
    <span class="original-price">原价：￥{{ originalPrice }}</span>
    <span class="discounted-price">到手约：￥{{ totalPrice }}</span>
    <el-button type="primary" class="checkout-btn" :disabled="cart.length === 0" @click=" isDialogVisible = true;">去下单</el-button>
</el-footer>
  <!-- 弹窗 -->
  <el-dialog  :visible.sync="isDialogVisible" width="25%" :modal-append-to-body="false">

    <div class="date-time">{{ currentDateTime }}</div>
      <!-- 商品列表 -->
      <div v-for="(item, index) in cart" :key="index" class="cart-item">
        <img :src="item.image" class="item-image" alt="商品图片" />
        <div class="item-info">
          <p>{{ item.dishName }}</p>
          <p>{{ item.kind }}</p>
          <p class="price">¥{{ item.price }}</p>
          <p class="original-price">¥{{ item.originalPrice }}</p>
        </div>
        <div class="item-quantity">
  <el-button size="mini" @click="decreaseQuantity(item)">
    <i class="el-icon-minus"></i>
  </el-button>
  <span>{{ item.quantity }}</span>
  <el-button size="mini" @click="increaseQuantity(item)">
    <i class="el-icon-plus"></i> 
  </el-button>
</div>
      </div>
      <div class="input-area">
    <el-input
      v-model="inputValue"
      placeholder="备注"
      clearable
      @keyup.enter="handleEnter"
    ></el-input>
  </div>
      <div class="checkout">
        <span class="discounted-price">到手约：￥{{ totalPrice }}</span>
        <span class="original-price">￥{{ originalPrice }}</span>
        <el-button type="primary" @click="confirmOrder">去结算</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios';


export default {
  data() {
    return {
      inputValue: '' ,
      currentDateTime: '',
      searchQuery: '',
      menuItems: [],
      cart: [],
      selectedTableId: null,
      isDialogVisible: false,
      guestCount: null,
    };
  },
  computed: {
    totalPrice() {
      return this.cart.reduce((total, item) => total + item.price * item.quantity, 0).toFixed(2);
    },
    originalPrice() {
    return this.cart.reduce((total, item) => total + item.originalPrice * item.quantity, 0).toFixed(2);
  }
},
  methods: {
    handleEnter() {
      // 处理输入框中的回车事件
      console.log(this.inputValue); // 在控制台输出输入的值
      this.inputValue = ''; // 清空输入框
    },
    updateDateTime() {
      const now = new Date();
      this.currentDateTime = this.formatDate(now); // 格式化日期和时间
    },
    formatDate(date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0'); // 月份从 0 开始
      const day = String(date.getDate()).padStart(2, '0');
      const hours = String(date.getHours()).padStart(2, '0');
      const minutes = String(date.getMinutes()).padStart(2, '0');
      const seconds = String(date.getSeconds()).padStart(2, '0');
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`; // 格式化输出
    },
    decreaseQuantity(item){
      if (item.quantity > 1) {
        item.quantity -= 1;
      } else {
        // 如果数量为 0，则从购物车中移除商品
        this.cart = this.cart.filter(cartItem => cartItem.id !== item.id);
      }
      axios.delete(`http://localhost:80/cart/delete?dishId=${item.dishId}`)
  .then((res)=>{
        if (res.data.code === "200") {
          this.getAllOrderDetail();
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
    },
    increaseQuantity(item) {
  axios.get(`http://localhost:80/cart/save?dishId=${item.dishId}`)
    .then((res) => {
      if (res.data.code === "200") {
        item.quantity += 1;
        this.getAllOrderDetail();
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
    getAllOrderDetail(){
      axios.get("http://localhost:80/cart/selectAll")
      .then((res)=>{
        if(res.data.code === "200"){
          this.cart = res.data.data.map(item => ({
  ...item,
  dishPrice: parseFloat(item.dishPrice), // 转换为数字
  dishOriginalPrice: parseFloat(item.dishOriginalPrice) // 转换为数字
}));
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
    },
    confirmOrder() {
      this.isDialogVisible = false; 
      const confirData = {
        tableId: this.selectedTableId,
        peopleCount: this.guestCount,
        totalAmount: this.totalPrice,
        status: 0,
        orderTime: this.currentDateTime,
        notes:this. inputValue
      }
      axios.post("http://localhost:80/orders/saveAll",confirData)
      .then((res)=>{
        if (res.data.code === "200") {
          this.$router.push({ path: '/' });
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
    
    },
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
  const cartItem = this.cart.find(cartItem => cartItem.id === item.id);
  // 判断商品是否已存在购物车中，如果存在则数量增加
  if (cartItem) {
    cartItem.quantity += 1;
  } else {
    this.cart.push({ ...item, quantity: 1 });
  }
  const insertData ={
   tableId: this.selectedTableId,
   dishId: item.id,
   quantity: cartItem ? cartItem.quantity : 1 
  }
  axios.post("http://localhost:80/cart/InsertCart",insertData)
  .then((res)=>{
        if (res.data.code === "200") {
          this.$message({
            message: '成功',
            type: 'success'
          }); 
          this.getAllOrderDetail();
        }else{
          this.$message({
            message: '失败',
            type: 'error'
          });
        }
      })
    }
  },

  mounted() {
    setInterval(this.updateDateTime, 1000);
    this.gitAllDish();
    const selectedTableId = localStorage.getItem('selectedTableId');
  if (selectedTableId) {
    this.selectedTableId = selectedTableId; // 赋值
  }
  const guestCount = localStorage.getItem('guestCount');
  if (guestCount) {
    this.guestCount = guestCount; // 赋值
  }
  },
};
</script>

<style scoped>
.date-time {
  text-align: center;
  font-weight: bold;
  margin-bottom: 10px;
}
.cart-item {
  display: flex;
  align-items: center; /* 垂直居中对齐 */
  justify-content: space-between; /* 均匀分布内容 */
}

.item-info {
  flex-grow: 1; /* 允许内容区域占用剩余空间 */
  padding: 0 10px; /* 添加适当的内边距 */
}

.item-quantity {
  display: flex;
  align-items: center; /* 确保加减按钮与数量垂直居中对齐 */
}

.item-quantity el-button {
  margin: 0 5px; /* 调整按钮之间的间距 */
}

.price, .original-price {
  line-height: 1.5; /* 确保价格文本的行高一致 */
}


.fixed-size-dialog {
  max-width: 375px;
  width: 90%; /* 自动适应屏幕宽度 */
}

.cart-summary {
  display: flex;
  justify-content: space-between;
  padding: 10px;
  font-size: 16px;
}

.item-image {
  width: 50px;
  height: 50px;
  margin-right: 10px;
}


.price {
  color: #f56c6c;
}
.checkout {
  padding: 10px;
  text-align: center;
}

@media (max-width: 768px) {
  /* 更小屏幕的适配 */
  .cart-summary, .cart-item p, .price, .item-quantity {
    font-size: 12px;
  }

  .item-image {
    width: 40px;
    height: 40px;
  }
}

.original-price {
  text-decoration: line-through;
  color: #888;
  margin-right: 8px;
}

.discounted-price {
  color: #f00;
  font-weight: bold;
  font-size: 12px
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
