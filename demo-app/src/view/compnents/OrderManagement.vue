<template>
    <div class="container">
      <div class="order-page">
        <div class="shop-info">
      <img src="https://s2.loli.net/2024/10/30/tBZ5912kiNfUQDL.png"  class="shop-logo" />
      <div class="shop-details">
        <h3>美滋滋汉堡</h3>
        <p>188 8888 8888</p>
        <el-button size="mini" type="success">营业中</el-button>
      </div>
    </div>
        <el-card class="order-card" v-for="(order, index) in orders" :key="index">
          <div class="order-header">
            <div class="store-info">            
            <span class="icon">🐰</span>
            <span class="store-name">{{ order.storeName || '李子馆·辣炒鸡煲（万达金街店）' }}</span>
          </div>
          <span class="order-status" :class="{ 'status-complete': order.status === '已完成', 'status-in-progress': order.status === '进行中' }">
  {{ order.status }} <!-- 添加 {{ order.status }} 来调试 -->
</span>

        </div>
          
          <div class="dish-images">
            <el-image
              v-for="(dish, dishIndex) in order.dishes"
              :key="dishIndex"
              :src="dish.dishImage"
              class="dish-image"
              fit="cover"
            ></el-image>
          </div>
          
          <div class="order-details">
            <div class="order-time">下单：{{ order.orderTime }}</div>
            <div class="order-info">
              <span class="order-price">¥{{ order.totalAmount }}</span>
              <span class="order-quantity">共 {{ order.totalQuantity }} 件</span>
            </div>
          </div>
          
          <div class="order-actions">
            <el-button type="text" class="action-button">更多</el-button>
            <el-button type="text" class="action-button">保险服务</el-button>
            <el-button type="text" class="action-button">领神券</el-button>
            <el-button type="primary" plain class="action-button">再来一单</el-button>
          </div>
        </el-card>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        orders: [],
        selectedTableId: null,
      };
    },
 
    created() {
        const selectedTableId = localStorage.getItem('selectedTableId');
  if (selectedTableId) {
    this.selectedTableId = selectedTableId; // 赋值
  }
      axios.get(`http://localhost:80/order-detail/getAllOrder?tableId=${this.selectedTableId}`)
        .then(response => {
          if (response.data.code === '200') {
            const data = response.data.data;
            // 使用一个 map 来根据 orderId 聚合相同订单的菜品
            const ordersMap = {};
            
            data.forEach(item => {
              const orderId = item.orderId;
              
              if (!ordersMap[orderId]) {
                // 如果没有这个订单 ID，创建一个新的订单对象
                ordersMap[orderId] = {
                  storeName: '美滋滋汉堡',
                  status: item.status === "0" ? '已完成' : '进行中', 
                  orderTime: item.orderTime,
                  totalAmount: item.totalAmount,
                  totalQuantity: 0,
                  dishes: []
                };
              }
              
              // 将菜品信息添加到订单的 dishes 数组中
              ordersMap[orderId].dishes.push({
                dishImage: item.dishImage,
                dishName: item.dishName,
                quantity: item.quantity
              });
              
              // 更新订单的总数量
              ordersMap[orderId].totalQuantity += item.quantity;
            });
  
            // 将对象转为数组以便 Vue 渲染
            this.orders = Object.values(ordersMap);
          } else {
            console.error('Failed to fetch orders:', response.data.msg);
          }
        })
        .catch(error => {
          console.error('Error fetching orders:', error);
        });
    }
  };
  </script>
  
  
  <style scoped>
  .order-status {
  font-size: 14px;
  font-weight: bold;
}
.status-complete {
  color: #999 !important; /* 已完成的颜色 */
}

.status-in-progress {
  color: green !important; /* 进行中的绿色 */
}
.shop-info {
  display: flex;
  align-items: center;
  padding: 16px;
  background-color: #fff;
  border-bottom: 1px solid #ebebeb;
}

.shop-logo {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  margin-right: 16px;
}
  .container {
    display: flex;
    justify-content: center; /* 水平居中 */
    align-items: center; /* 垂直居中 */
    height: 100vh; /* 充满整个视口高度 */
    background-color: #f5f5f5; /* 背景色 */
  }
  
  .order-page {
    padding: 10px;
    width: 375px; /* 设置为标准手机宽度 */
    height: 667px; /* 设置为标准手机高度 */
    background-color: #ffffff; /* 背景色为白色 */
    overflow-y: auto; /* 允许上下滑动 */
    box-sizing: border-box; /* 确保 padding 包含在宽度和高度内 */
    border-radius: 10px; /* 添加圆角 */
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* 添加阴影 */
  }
  
  .order-card {
    margin-bottom: 10px;
    border-radius: 8px;
  }
  
  .order-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 16px;
    font-weight: bold;
    padding-bottom: 5px;
  }
  
  .store-info {
    display: flex;
    align-items: center;
  }
  
  .icon {
    font-size: 20px;
    margin-right: 5px;
  }
  
  .store-name {
    font-weight: bold;
  }
  
  .order-status {
    color: #999;
  }
  
  .dish-images {
    display: flex;
    gap: 5px;
    margin: 10px 0;
  }
  
  .dish-image {
    width: 22%;
    height: 70px;
    border-radius: 5px;
  }
  
  .order-details {
    display: flex;
    justify-content: space-between;
    font-size: 14px;
    color: #666;
  }
  
  .order-info {
    display: flex;
    gap: 10px;
  }
  
  .order-actions {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-top: 10px;
  }
  
  .action-button {
    font-size: 14px;
    padding: 0;
  }
  
  .el-button--primary {
    color: #ff8c00;
    border-color: #ff8c00;
  }
  </style>
  