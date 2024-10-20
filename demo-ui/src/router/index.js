import Vue from 'vue';
import VueRouter from 'vue-router';
import MainContent from '@/view/components/MainContent.vue';
import DishItem from '@/view/components/dishItem.vue';
import TableManagement from '@/view/components/TableManagement.vue'
import UserManagement from '@/view/components/UserManagement.vue'
Vue.use(VueRouter);

export default new VueRouter({
  mode:'history',
  routes: [
    {
      path: '/main',
      name:'main',
      component: MainContent,
    },
    {
      path: '/dish',
      name:'DishItem',
      component: DishItem,
    },
    {
      path:'/TableManagement',
      name: 'TableManagement',
      component:TableManagement,
    },
    {
      path: '/UserManagement',
      name: 'Usermanagement',
      component:UserManagement,
    }
  ]
});
