<template>
    <el-menu
      default-active="1"
      class="el-menu-vertical-demo"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
    >
    <el-menu-item index="1" @click="goToWorkSpace">工作台</el-menu-item>
      <el-submenu index="3">
        <template slot="title">菜单管理</template>
        <el-menu-item index="3-1"  @click="goToDishManagement">菜品管理</el-menu-item>
        <el-menu-item index="3-2"  @click="goToAddDish">添加菜品</el-menu-item>
      </el-submenu>
      <el-submenu index="4">
        <template slot="title">台桌管理</template>
        <el-menu-item index="4-1" @click="goToTableManagement">台桌设置</el-menu-item>
        <el-menu-item index="4-2" @click="goToAddTable">新建台桌</el-menu-item>
        <el-menu-item index="4-3">台桌设置</el-menu-item>
      </el-submenu>
      <el-submenu index="5">
        <template slot="title">员工管理</template>
      <el-menu-item index="5-1" @click="goToUserManagement">员工管理</el-menu-item>
      <el-menu-item index="5-2" @click="goToAddUser">添加员工</el-menu-item>
    </el-submenu>
      <el-submenu index="6">
        <template slot="title">订单管理</template>
        <el-menu-item index="6-1" @click="goToOrderManagement">订单列表</el-menu-item>
        <el-menu-item index="6-2" @click="goToDishChart">订单统计</el-menu-item>
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
    },
    goToAddDish(){
      if (this.$route.path === '/AddDish') {
    this.$forceUpdate();
  } else {
    this.$router.push('/AddDish');
  }
    },
    goToOrderManagement(){
      if (this.$route.path === '/IOrderManagement') {
    this.$forceUpdate();
  } else {
    this.$router.push('/IOrderManagement');
  }
    },
    goToAddUser(){
      if (this.$route.path === '/AddUser') {
    this.$forceUpdate();
  } else {
    this.$router.push('/AddUser');
  }
    },
    goToAddTable(){
      if (this.$route.path === '/AddTable') {
    this.$forceUpdate();
  } else {
    this.$router.push('/AddTable');
  }
    },
    goToDishChart(){
      if (this.$route.path === '/DishChart') {
    this.$forceUpdate();
  } else {
    this.$router.push('/DishChart');
    }
  }
    }
  }
  </script>
  
  <style scoped>
  
  </style>
  