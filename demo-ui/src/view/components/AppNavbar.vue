<template>
    <div class="navbar">
      <span>LOGO</span>
      <div class="navbar-options">
        <el-button type="text">账号设置</el-button>
        <el-button type="text">退出登录</el-button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'AppNavbar'
  }
  </script>
  
  <style scoped>
  .navbar {
    background-color: #673ab7;
    color: white;
    padding: 10px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .navbar-options el-button {
    margin-left: 20px;
    color: white;
  }
  </style>
  