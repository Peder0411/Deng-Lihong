<template>
  <div>
    <el-form ref="addForm" :model="form" :rules="rules" label-width="120px" class="demo-ruleForm">
      <el-form-item label="菜品名称" prop="dishName">
        <el-input v-model="form.dishName" placeholder="请输入菜品名称"></el-input>
      </el-form-item>

      <!-- 本地上传图片 -->
      <el-form-item label="上传图片" prop="image">
        <el-upload
         action="http://127.0.0.1:5000/upload"
         method="POST"  
         list-type="picture-card"
         :on-preview="handlePictureCardPreview"
         :on-remove="handleRemove"
         :on-success="handleSuccess"
         :file-list="fileList"
        >
          <i class="el-icon-plus"></i>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
      </el-form-item>
      <el-form-item label="描述" prop="kind">
        <el-input v-model="form.kind" placeholder="请输入菜品类型"></el-input>
      </el-form-item>

      <el-form-item label="原价" prop="originalPrice">
        <el-input v-model="form.originalPrice" placeholder="请输入原价" type="number"></el-input>
      </el-form-item>

      <el-form-item label="每月销量" prop="soldPerMonth">
        <el-input v-model="form.soldPerMonth" placeholder="请输入每月销量" type="number"></el-input>
      </el-form-item>

      <el-form-item label="折扣" prop="discount">
        <el-input v-model="form.discount" placeholder="请输入折扣"></el-input>
      </el-form-item>

      <el-form-item label="状态" prop="status">
        <el-select v-model="form.status" placeholder="请选择状态">
          <el-option label="上架" value="1"></el-option>
          <el-option label="下架" value="0"></el-option>
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
        dishName: '',
        image: '',  // 用来存储上传后的图片URL
        price: '',
        kind: '',
        originalPrice: '',
        soldPerMonth: '',
        discount: '',
        status: '',
      },
      fileList: [], // 文件上传列表
      dialogImageUrl: '',
      dialogVisible: false,
      rules: {
        dishName: [{ required: true, message: '请输入菜品名称', trigger: 'blur' }],
        price: [{ required: true, message: '请输入价格', trigger: 'blur' }],
        kind: [{ required: true, message: '请输入菜品类型', trigger: 'blur' }],
        originalPrice: [{ required: true, message: '请输入原价', trigger: 'blur' }],
        soldPerMonth: [{ required: true, message: '请输入每月销量', trigger: 'blur' }],
        discount: [{ required: true, message: '请输入折扣', trigger: 'blur' }],
        status: [{ required: true, message: '请选择状态', trigger: 'change' }],
        image: [{ required: true, message: '请上传图片', trigger: 'change' }],
      },
    };
  },
  methods: {
    onSubmit() {
    axios.post("http://localhost:80/dish/addDish",this.form)
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
      this.fileList = [];
    },
    handleRemove(file, fileList) {
      this.fileList = fileList;
      console.log('文件移除', file, fileList);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleSuccess(response, file, fileList) {
      this.form.image = response.image; // 上传成功后将图片的URL存储在form中
      this.fileList = fileList;
    }
  },
};
</script>

<style scoped>
.demo-ruleForm {
  max-width: 600px;
  margin: 20px auto;
}
</style>
