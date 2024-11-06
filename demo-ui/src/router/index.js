import Vue from 'vue';
import VueRouter from 'vue-router';
import MainContent from '@/view/components/MainContent.vue';
import DishItem from '@/view/components/dishItem.vue';
import TableManagement from '@/view/components/TableManagement.vue';
import UserManagement from '@/view/components/UserManagement.vue';
import loginPage from '@/view/components/loginPage.vue';
import AddDish from '@/view/components/AddDish.vue';
import IOrderManagement from '@/view/components/IOrderManagement.vue';
import AddUser from '@/view/components/AddUser.vue';
import AddTable from '@/view/components/AddTable.vue';
import DishChart from '@/view/components/DishChart.vue';
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
    component: AddDish,
    meta: { requiresAuth: true }
  },
  {
    path: '/IOrderManagement',
    name: 'IOrderManagement',
    component: IOrderManagement,
    meta: { requiresAuth: true }
  },
  {
    path: '/AddUser',
    name: 'AddUser',
    component: AddUser,
    meta: { requiresAuth: true }
  },
  {
    path: '/AddTable',
    name: 'AddTable',
    component: AddTable,
    meta: { requiresAuth: true }
  },
  {
    path: '/DishChart',
    name: 'DishChart',
    component: DishChart,
    meta: { requiresAuth: true }
  }
];

const router = new VueRouter({
  mode: 'history',
  routes
});

// 路由守卫，检查是否已登录
function checkTokenValidity() {
  const token = localStorage.getItem('token');
  const tokenExpireTime = localStorage.getItem('tokenExpireTime');
  const currentTime = new Date().getTime();
  
  if (token && tokenExpireTime) {
    if (currentTime < tokenExpireTime) {
      return true; // token 有效
    } else {
      localStorage.removeItem('token');
      localStorage.removeItem('tokenExpireTime');
      return false; // token 过期
    }
  }
  return false; // 无 token
}

// 路由守卫，检查是否已登录
router.beforeEach((to, from, next) => {
  const isLoggedIn = checkTokenValidity();

  if (to.path === '/loginPage' && isLoggedIn) {
    next('/main'); // 已登录，重定向到主页面
  } else if (to.matched.some(record => record.meta.requiresAuth) && !isLoggedIn) {
    next('/loginPage'); // 未登录，跳转到登录页
  } else {
    next(); // 放行
  }
});

export default router;
