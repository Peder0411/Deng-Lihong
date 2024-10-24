import Vue from 'vue';
import VueRouter from 'vue-router';
import MainContent from '@/view/components/MainContent.vue';
import DishItem from '@/view/components/dishItem.vue';
import TableManagement from '@/view/components/TableManagement.vue';
import UserManagement from '@/view/components/UserManagement.vue';
import loginPage from '@/view/components/loginPage.vue';
import AddDish from '@/view/components/AddDish.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    redirect: '/loginPage' // 默认重定向到登录页面
  },
  {
    path: '/loginPage',
    name: 'loginPage',
    component: loginPage,
  },
  {
    path: '/main',
    name: 'main',
    component: MainContent,
    // 添加 meta 信息，表示需要登录
    meta: { requiresAuth: true }
  },
  {
    path: '/dish',
    name: 'DishItem',
    component: DishItem,
    meta: { requiresAuth: true }
  },
  {
    path: '/TableManagement',
    name: 'TableManagement',
    component: TableManagement,
    meta: { requiresAuth: true }
  },
  {
    path: '/UserManagement',
    name: 'UserManagement',
    component: UserManagement,
    meta: { requiresAuth: true }
  },
  {
    path: '/AddDish',
    name: 'AddDish',
    component:AddDish ,
    meta: { requiresAuth: true }
  }

];

const router = new VueRouter({
  mode: 'history',
  routes
});

// 路由守卫，检查是否已登录
router.beforeEach((to, from, next) => {
  const isLoggedIn = !!localStorage.getItem('token'); // 假设用 token 判断登录状态
  if (to.path !== '/loginPage' && !isLoggedIn) {
    next('/loginPage'); // 未登录时跳转到登录页
  } else {
    next(); // 已登录或者在访问登录页时放行
  }
});

export default router;
