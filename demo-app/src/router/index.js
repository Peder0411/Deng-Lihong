import Vue from 'vue';
import Router from 'vue-router';
import firstPage from '@/view/compnents/firstPage.vue';
import orderPage from '@/view/compnents/orderPage.vue';
Vue.use(Router);

const routes = [
  {
    path: '/',
    component: firstPage // 主页
  },
  {
    path: '/order',
    component: orderPage // 订单页面
  }
];

const router = new Router({
  mode: 'history', // 使用 HTML5 历史模式
  routes
});

export default router;
