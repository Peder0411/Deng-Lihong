<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2 class="logo">LOGO</h2>
      <el-form :model="loginForm" ref="loginForm" class="login-form">
        <el-form-item>
          <el-input v-model="loginForm.username" placeholder="账号" prefix-icon="el-icon-user"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input type="password" v-model="loginForm.password" placeholder="密码" prefix-icon="el-icon-lock"></el-input>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="loginForm.rememberMe">记住密码</el-checkbox>
          <a class="forgot-password" href="javascript:;" @click="showForgotPasswordDialog">忘记密码</a>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin" class="login-button">登陆</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 忘记密码弹窗 -->
    <el-dialog title="忘记密码" :visible.sync="forgotPasswordDialog" width="30%">
      <el-form :model="registerForm" ref="registerForm" class="register-form">
        
        <!-- 手机号输入框 -->
        <el-form-item>
          <el-input v-model="registerForm.email" placeholder="请输入邮箱" prefix-icon="el-icon-mobile-phone"></el-input>
        </el-form-item>
        
        <!-- 短信验证码输入框和获取验证码按钮 -->
        <el-form-item>
          <el-input v-model="registerForm.mailCode" placeholder="请输入邮箱验证码" prefix-icon="el-icon-key">
            <template slot="append">
              <el-button type="primary" @click="getSmsCode">获取验证码</el-button>
            </template>
          </el-input>
        </el-form-item>
        
        <!-- 密码输入框 -->
        <el-form-item>
          <el-input type="password" v-model="registerForm.password" placeholder="请输入密码" prefix-icon="el-icon-lock"></el-input>
        </el-form-item>
        
        <!-- 确认密码输入框 -->
        <el-form-item>
          <el-input type="password" v-model="registerForm.confirmPassword" placeholder="请确认密码" prefix-icon="el-icon-lock"></el-input>
        </el-form-item>
      </el-form>


       <!-- 弹窗底部按钮 -->
      <div style="text-align: right; margin-top: 20px;">
    <el-button @click="forgotPasswordDialog = false">取消</el-button>
    <el-button type="primary" @click="resetPassword">提交</el-button>
  </div>
    </el-dialog>
  </div>
</template>

  <script>
  import axios from 'axios';
  export default {
    data() {
      return {
        loginForm: {
          username: '',
          password: '',
          rememberMe: false
        },
        registerForm: {
        email: '',
        mailCode: '',
        password: '',
        confirmPassword: ''
      },
        forgotPasswordDialog: false, // 控制弹窗显示
      };
    },
    methods: {
      handleLogin() {
        //收集登录用的数据
        const loginData = {
      username: this.loginForm.username,
      password: this.loginForm.password
    };
    axios.post("http://localhost:80/user/login", loginData)
      .then(response => {
        if (response.data.code === "200") {
          const token = response.data.token;
          const expireTime = new Date().getTime() + 3600 * 1000; // 设置token有效期为1小时
          
          // 存储token和过期时间
          localStorage.setItem('token', token);
          localStorage.setItem('tokenExpireTime', expireTime);
          this.$router.push('/main');
        } else {
          this.$message.error(response.data.msg || '登录失败');
        }
      })
      .catch(error => {
        console.error("登录请求出错: ", error);
        this.$message.error('登录请求失败');
      });
  
      },
      showForgotPasswordDialog() {
      this.forgotPasswordDialog = true; // 显示弹窗
    },
    getSmsCode() {
    const sendData ={
      email:this.registerForm.email
    };
      console.log("获取验证码");
      axios.post("http://localhost:80/user/sendMail",sendData)
      .then(res =>{
        if(res.data.code ==="200"){
          this.$message({
            message: '成功',
            type: 'success'
          });
        }else{
          this.$message({
            message: '失败',
            type: 'error'
          });
        }
      })
      },
    resetPassword() {
      axios.post("http://localhost:80/user/rePassword",this.registerForm)
      .then(res =>{
        if(res.data.code ==="200"){
          this.$message({
            message: '成功',
            type: 'success'
          });
        }else{
          this.$message({
            message: '失败',
            type: 'error'
          });
        }
      })
      console.log("提交重置密码");
      this.forgotPasswordDialog = false; // 重置后关闭弹窗
    }
    }
  };
  </script>
  <style scoped>
  .login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-image: url('/src/image/login.png'); /* 替换为你的图片路径 */
    background-size: cover; /* 使图片覆盖整个背景 */
    background-position: center; /* 图片居中显示 */
    background-repeat: no-repeat; /* 防止图片重复 */
  }
  
  .login-card {
    padding: 40px;
    width: 300px;
    background-color: white; /* 保持登录表单卡片为白色 */
  }
  
  .logo {
    text-align: center;
    margin-bottom: 20px;
    color: #009688;
    font-size: 24px;
  }
  
  .forgot-password {
    float: right;
    color: #009688;
    font-size: 14px;
  }
  
  .login-button {
    width: 100%;
    background-color: #009688;
    border-color: #009688;
  }
  </style>
  