<template>
    <div>
      <el-form ref="addForm" :model="form" :rules="rules" label-width="120px" class="demo-ruleForm">
        <el-form-item label="桌号" prop="tableNumber">
          <el-input v-model="form.tableNumber" placeholder="请输入桌号"></el-input>
        </el-form-item>
  
        <el-form-item label="位置" prop="location">
          <el-input v-model="form.location" placeholder="请输入位置"></el-input>
        </el-form-item>
  
        <el-form-item label="二维码" prop="qrCode">
          <el-upload
            action="http://127.0.0.1:5000/upload"  
            method="POST"
            list-type="picture-card"
            :on-success="handleQrCodeSuccess"
            :file-list="qrCodeFileList"
            :on-preview="handleQrCodePreview"
            :on-remove="handleQrCodeRemove"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="qrCodeDialogVisible">
            <img width="100%" :src="qrCodeDialogImageUrl" alt="">
          </el-dialog>
        </el-form-item>
  
        <el-form-item label="桌型" prop="tableRow">
          <el-input v-model="form.tableType" placeholder="请输入桌型"></el-input>
        </el-form-item>
  
        <el-form-item label="使用状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择使用状态">
            <el-option label="使用中" value="1"></el-option>
            <el-option label="未使用" value="0"></el-option>
          </el-select>
        </el-form-item>
  
        <el-form-item>
          <el-button type="primary" @click="onSubmit">提交</el-button>
          <el-button @click="onReset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        form: {
          tableNumber: '',
          location: '',
          qrCode: '', // 存储二维码图片的URL
          tableRow: '',
          status: '',
        },
        qrCodeFileList: [], // 二维码文件列表
        qrCodeDialogImageUrl: '',
        qrCodeDialogVisible: false,
        rules: {
          tableNumber: [{ required: true, message: '请输入桌号', trigger: 'blur' }],
          location: [{ required: true, message: '请输入位置', trigger: 'blur' }],
          qrCode: [{ required: true, message: '请上传二维码', trigger: 'change' }],
          tableRow: [{ required: true, message: '请输入桌行', trigger: 'blur' }],
          status: [{ required: true, message: '请选择使用状态', trigger: 'change' }],
        },
      };
    },
    methods: {
      onSubmit() {
        axios.post("http://localhost:80/tableInfo/save", this.form)
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
          });
      },
      onReset() {
        this.$refs.addForm.resetFields();
        this.qrCodeFileList = []; // 清空二维码文件列表
      },
      handleQrCodeSuccess(response, file, fileList) {
        this.form.qrCode = response.image; // 假设响应中有二维码图片的URL
        this.qrCodeFileList = fileList;
      },
      handleQrCodePreview(file) {
        this.qrCodeDialogImageUrl = file.url || URL.createObjectURL(file.raw);
        this.qrCodeDialogVisible = true;
      },
      handleQrCodeRemove(file, fileList) {
        this.qrCodeFileList = fileList;
        this.form.qrCode = ''; // 移除二维码时清空URL
      },
    },
  };
  </script>
  
  <style scoped>
  .demo-ruleForm {
    max-width: 600px;
    margin: 20px auto;
  }
  </style>
  