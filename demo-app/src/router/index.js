import Vue from 'vue';
import Router from 'vue-router';
import firstPage from '@/view/compnents/firstPage.vue';
import orderPage from '@/view/compnents/orderPage.vue';
import TableReservation from '@/view/compnents/TableReservation.vue';
import OrderManagement from '@/view/compnents/OrderManagement.vue';


Vue.use(Router);

const routes = [
  {
    path: '/',
    component: firstPage // 主页
  },
  {
    path: '/order',
    name: 'order',
    component: orderPage // 订单页面
  },
  {
    path: '/TableReservation', // 需要以 / 开头
    name: 'TableReservation',
    component: TableReservation // 单个组件应该使用 component，而不是 components
  },
  {
    path: '/OrderManagement', // 需要以 / 开头
    name: 'OrderManagement',
    component: OrderManagement // 单个组件应该使用 component，而不是 components
  }
  
];

const router = new Router({
  mode: 'history', // 使用 HTML5 历史模式
  routes
});

export default router;
