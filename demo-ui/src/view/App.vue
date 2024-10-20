<template>
  <div class="dashboard">
    <AppNavbar />

    <!-- 页面布局 -->
    <el-row>
      <el-col :span="4">
        <SidebarMenu />
      </el-col>

      <!-- 主内容区域，router-view 用于动态显示内容 -->
      <el-col :span="20">
        <router-view /> <!-- 这里会根据路由动态渲染 MainContent -->
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
  }
};
</script>

<style>
/* 添加你的样式 */
</style>
