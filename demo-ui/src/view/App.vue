<template>
  <div class="dashboard">
    <!-- 只在非登录页面时显示 AppNavbar 和 SidebarMenu -->
    <AppNavbar v-if="!isLoginPage" />
    
    <el-row>
      <!-- 如果不是登录页面，显示侧边菜单 -->
      <el-col v-if="!isLoginPage" :span="4">
        <SidebarMenu />
      </el-col>

      <!-- 主内容区域，router-view 用于动态显示内容 -->
      <el-col :span="isLoginPage ? 24 : 20">
        <router-view /> <!-- 这里会根据路由动态渲染内容 -->
      </el-col>
    </el-row>
  </div>
</template>

<script>
import AppNavbar from './components/AppNavbar.vue';
import SidebarMenu from './components/SidebarMenu.vue';

export default {
  components: {
    AppNavbar,
    SidebarMenu
  },
  computed: {
    // 判断是否是登录页面
    isLoginPage() {
      return this.$route.path === '/loginPage'; // 根据路径判断是否为登录页面
    }
  }
};
</script>


<style>
/* 添加你的样式 */
</style>
