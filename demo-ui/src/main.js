// main.js
import Vue from 'vue';
import App from './view/App.vue';
import router from './router'; // 导入路由器配置
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.config.productionTip = false;
Vue.use(ElementUI);

new Vue({
  router, // 使用导入的路由器配置
  render: h => h(App)
}).$mount('#app');
