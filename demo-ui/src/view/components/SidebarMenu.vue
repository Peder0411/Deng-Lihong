<template>
    <el-menu
      default-active="1"
      class="el-menu-vertical-demo"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
    >
    <el-menu-item index="1" @click="goToWorkSpace">工作台</el-menu-item>
    
      <el-submenu index="2">
        <template slot="title">商户管理</template>
        <el-menu-item index="2-1">商户管理</el-menu-item>
        <el-menu-item index="2-2">商户审核</el-menu-item>
      </el-submenu>
      <el-submenu index="3">
        <template slot="title">菜单管理</template>
        <el-menu-item index="3-1"  @click="goToDishManagement">菜品管理</el-menu-item>
        <el-menu-item index="3-2">分类管理</el-menu-item>
      </el-submenu>
      <el-submenu index="4">
        <template slot="title">台桌管理</template>
        <el-menu-item index="4-1" @click="goToTableManagement">台桌设置</el-menu-item>
        <el-menu-item index="4-2">桌型管理</el-menu-item>
        <el-menu-item index="4-3">台桌设置</el-menu-item>
      </el-submenu>
      <el-menu-item index="5" @click="goToUserManagement">员工管理</el-menu-item>
      <el-submenu index="6">
        <template slot="title">订单管理</template>
        <el-menu-item index="6-1">订单列表</el-menu-item>
        <el-menu-item index="6-2">订单统计</el-menu-item>
      </el-submenu>
    </el-menu>
  </template>
  
  <script>
  export default {
    name: 'SidebarMenu',
    methods:{
    goToDishManagement() {
      if (this.$route.path === '/dish') {
    this.$forceUpdate();
  } else {
    this.$router.push('/dish');
  }
    },
    goToWorkSpace(){
      if (this.$route.path === '/main') {
    this.$forceUpdate();
  } else {
    this.$router.push('/main');
  }
    },
    goToTableManagement(){
      if (this.$route.path === '/TableManagement') {
    this.$forceUpdate();
  } else {
    this.$router.push('/TableManagement');
  }
},
    goToUserManagement(){
      if (this.$route.path === '/UserManagement') {
    this.$forceUpdate();
  } else {
    this.$router.push('/UserManagement');
  }
    }
  }
}
  </script>
  
  <style scoped>
  
  </style>
  