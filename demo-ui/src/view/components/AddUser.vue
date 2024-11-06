<template>
  <el-form :model="user" :rules="rules" ref="userForm" label-width="120px" class="user-form">
  
    <el-form-item label="姓名" prop="name">
      <el-input v-model="user.name" placeholder="请输入姓名"></el-input>
    </el-form-item>
    <el-form-item label="用户名" prop="username">
      <el-input v-model="user.username" placeholder="请输入用户名"></el-input>
    </el-form-item>
    <el-form-item label="岗位名称" prop="post">
      <el-input v-model="user.post" placeholder="请输入岗位名称"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="user.password" type="password" placeholder="请输入密码"></el-input>
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
      <el-input v-model="user.email" placeholder="请输入邮箱"></el-input>
    </el-form-item>
    <el-form-item label="电话" prop="phone">
      <el-input v-model="user.phone" placeholder="请输入电话"></el-input>
    </el-form-item>
    <el-form-item label="地址" prop="address">
      <el-input v-model="user.address" placeholder="请输入地址"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm">提交</el-button>
      <el-button @click="resetForm">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      user: {
        username: '',
        password: '',
        email: '',
        phone: '',
        address: '',
        post:'',
        name:''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '邮箱格式不正确', trigger: ['blur', 'change'] }
        ],
        phone: [
          { required: true, message: '请输入电话', trigger: 'blur' }
        ],
        address: [
          { required: true, message: '请输入地址', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    submitForm() {
      axios.post("http://localhost:80/user/addUser",this.user)
    .then((res) => {
        if (res.data.code === "200") {
          this.$message({
            message: '成功',
            type: 'success'
          });
        
        } else {
          this.$message({
            message: '失败',
            type: 'error'
          });
        }
      })
    },
    resetForm() {
      this.$refs.userForm.resetFields();
    }
  }
};
</script>

<style scoped>
.user-form {
  max-width: 500px;
  margin: 20px auto;
}
</style>
