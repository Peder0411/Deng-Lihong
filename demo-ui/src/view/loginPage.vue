<template>
  <div id="app">
    <el-container>
      <el-header>
        <el-row>
          <el-col :span="32">
            <el-form :inline="true" :model="formInline" class="demo-form-inline">
            <el-form-item>
            <el-input style="width: 700px;"
             v-model="formInline.user" placeholder="搜索"></el-input>
            </el-form-item>
            <el-form-item>
               <el-button type="primary" @click="search">搜索</el-button>
           </el-form-item>
           </el-form>
          </el-col>
        </el-row>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <el-menu>
            <el-menu-item index="1">热门</el-menu-item>
            <el-menu-item index="2">动漫</el-menu-item>
            <el-menu-item index="3">直播</el-menu-item>
            <el-menu-item index="4">视频</el-menu-item>
            <el-menu-item index="5">生活</el-menu-item>
          </el-menu>
        </el-aside>
        <el-main>
          <div class="message-list">
  <div v-for="(message, index) in messages" :key="index" class="message">
    <h3 class="message-title">{{ message.title }}</h3>
    <p class="message-content">{{ message.content }}</p>
    <div v-if="message.author" class="message-author">
      <p>作者：{{ message.author }}</p>
    </div>
    <div v-if="message.image" class="image-container">
      <img :src="message.image" class="message-image" alt="评论图片">
    </div>
    <a href="#" class="delete-link" @click.prevent="deleteMessage(index)">查看评论</a>
    <el-collapse v-model="collapseVisible">
      <el-collapse-item>
        <div v-for="(comment, index) in comments" :key="index" class="comment">
          <p>{{ comment.author }}：</p>
          <p>{{ comment.content }}</p>
          <div v-if="comment.image" class="comment-images"> <!-- 只要评论有图片就显示 -->
            <img :src="comment.image" class="comment-image" alt="评论图片">
          </div>
        </div>
      </el-collapse-item>
    </el-collapse>
</div>
</div>
<div>
       <div class="login-container">
            <el-form :model="loginForm" @submit.native.prevent="onSubmit">
              <el-form-item label="Username">
                <el-input v-model="loginForm.username" placeholder="Enter your username"></el-input>
              </el-form-item>
              <el-form-item label="Password">
                <el-input type="password" v-model="loginForm.password" placeholder="Enter your password"></el-input>
              </el-form-item>
              <el-form-item label="VerificationCode">
                <el-input v-model="loginForm.code" placeholder="Enter your Verification Code"></el-input>
                <img src="http://localhost:80/generateImageCode" alt="验证码" onclick="this.src=' http://localhost:80/generateImageCode?'+Math.random();" >
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="login">Login</el-button>
                
                <el-button @click="editDialogFormVisible = true" type="primary">
                注册
             </el-button>
              </el-form-item>
              <div>
    <el-button type="text" @click="showForm = true">忘记密码</el-button>
    <el-dialog title="忘记密码" :visible.sync="showForm" style="width: 40%; margin: 0 auto;">
      <el-form :model="formData" label-width="80px" >
        <el-form-item label="邮箱">
          <el-input v-model="formData.email"></el-input>
        </el-form-item>
        <el-form-item label="验证码">
          <el-input v-model="formData.mailCode"></el-input>
          <el-button @click="sendcode">发送验证码</el-button>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="formData.password" type="password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="confirm">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
            </el-form>
            </div>
            <div class="hot-search-list" style="right: 17px;">
            <el-list >
         <el-list-item v-for="(item, index) in hotSearchList" :key="index" class="hot-search-item">
         <div class="rank">{{ index + 1 }}</div>
        <div class="content">
          <span>{{ item.content }}</span>
        </div>
        <div class="heat">{{ item.heat }}</div>
      </el-list-item>
    </el-list>
    </div>

          </div>
          <div class="register-container">
          <el-dialog title="注册" :visible.sync="editDialogFormVisible" style="width: 40%;  margin: 0 auto;">
          <el-form :model="registerForm" :rules="registerRules" ref="registerForm">
         <el-form-item label="用户名" prop="username">
          <el-input v-model="registerForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="registerForm.password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="registerForm.confirmPassword" type="password" placeholder="请再次输入密码"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="registerForm.email" placeholder="请输入邮箱"></el-input>
          <el-button @click="sendVerificationCode" type="text">发送验证码</el-button>
        </el-form-item>
        <el-form-item label="验证码" prop="code">
          <el-input v-model="registerForm.code" placeholder="请输入验证码"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm('registerForm')">注册</el-button>
      <el-button @click="editDialogFormVisible = false">取消</el-button>
       </div>
      </el-dialog>
      </div>
          <RouterView/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'loginPage',
 
  data() {
    return {
      showForm: false,
      formData: {
        email: '',
        mailCode: '',
        password: ''
      },
      rawArray:[],
      savedIds:[],
      comments: [],
      collapseVisible: false,
      editDialogFormVisible:false,
      formInline:{
        user:''
      },
      messages: [],
      loginForm: {
        username: '',
        password: '',
        code:''
      },
      registerForm: { // 注册表单数据
        username: '',
        password: '',
        confirmPassword: '',
        email: '',
        code: ''
      },
      registerRules: { // 注册表单校验规则
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { validator: this.validateConfirmPassword, trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ],
        code: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ]
      },
      hotSearchList:[]
    };
  },
  mounted(){
    this.event();
    this.hotSearch();
  
  },
  methods: {
    sendcode(){
axios.get(`http://localhost:80/sendMail?email=${encodeURIComponent(this.formData.email)}`)
                .then((resp)=>{
                    if (resp.data.code===60000){
                        this.$message({
                            message: '成功',
                            type: 'succeed'
                        });
                    }else {
                        this.$message({
                            message: '注册失败',
                            type: 'error'
                        });
                    }
                })
            
    
    },
    chooseImage() {
      this.showForm = true;
    },
    confirm() {
      axios.post(`http://localhost:80/manager/selectByEmail?email=${this.formData.email}&mailCode=${this.formData.mailCode}&password=${this.formData.password}`)
      .then((res)=>{
        if(res.data.code===20041){
        this.showForm = false;
        this.$message({
            message: '成功',
            type: 'succeed'
          });
        }else{
          this.$message({
            message: '失败',
            type: 'error'
          });
        }
      })
      
    },
  hotSearch(){
    axios.get("http://localhost:80/hotsearch/search")
    .then((res)=>{
      if(res.data.code===20041){
        this.hotSearchList=res.data.data;
        this.$message({
            message: '成功',
            type: 'succeed'
          });
      }else{
        this.$message({
            message: '注册失败',
            type: 'error'
          });
      }
    })
  },
    deleteMessage(index) {
      axios.get(`http://localhost:80/comment?index=${index + 1}`)
      .then((res) => {
        if (res.data.code === 20041) {
          this.comments = res.data.data;
          this.$message({
            message: '成功',
            type: 'succeed'
          });
        } else {
          this.$message({
            message: '注册失败',
            type: 'error'
          });
        }
      });
  },
    login(event) {
                const username = this.loginForm.username;
                event.preventDefault(); // 阻止表单默认提交行为
                const params = new URLSearchParams();
                params.append('username', this.loginForm.username);
                params.append('password', this.loginForm.password);
                params.append('code', this.loginForm.code);

                axios.post("http://localhost:80/manager/select", params) // 修改请求 URL 以符合您的实际配置
                    .then((res) => {
                        if (res.data.code === 20051) { // 确保使用实际的成功代码
                          this.$router.push({
                                path: '/main',
                                query: {username: username } 
                                });
                            this.$message({
                                message: "登录成功",
                                type: "success"
                            });
                        } else {
                            this.$message({
                                message: "登录失败",
                                type: "error"
                            });
                        }
                    })
                    .catch(error => {
                        console.error("请求失败:", error);
                        this.$message({
                            message: "网络或服务器错误",
                            type: "error"
                        });
                    });
            },
    validateConfirmPassword(rule, value, callback) {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.registerForm.password) {
        callback(new Error('两次输入密码不一致'));
      } else {
        callback();
      }
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          
          console.log('表单校验通过，提交的数据为:', this.registerForm);
          
          this.axios.post('http://localhost:8080/manager/register',this.registerForm)
          .then((res) =>{
            if(res.data.code ===20061){
              this.$router.push('/login');
              this.$messages({
                message:'注册成功',
                type:'infor'
              });
            }else{
              this.$message({
                message:'注册失败',
                type:'info'
              })
              }
            
            });
          }
          })
        },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    search(){
      const url = `http://localhost:80/event/select?user=${this.formInline.user}`;
      axios.post(url,this.formInline).then((res)=>{
        if(res.data.code===20081){
          this.messages=res.data.data
          this.$message({
            message: '成功',
            type: 'succeed'
          });
        }else{
          this.$message({
            message: '失败',
           type: 'error'
         });
        }
      })
    },
     sendVerificationCode() {
      axios.get(`http://localhost:80/sendMail?email=${encodeURIComponent(this.registerForm.email)}`)
                .then((resp)=>{
                    if (resp.data.code===60000){
                        this.$message({
                            message: '成功',
                            type: 'succeed'
                        });
                    }else {
                        this.$message({
                            message: '注册失败',
                            type: 'error'
                        });
                    }
                })
            
    },
    event() {
    axios.get("http://localhost:80/event")
      .then((res) => {
        if (res.data.code === 20041) {
          this.messages = res.data.data;
          this.$message({
            message: '成功',
            type: 'info'
          });
        
        } else {
          this.$message({
            message: '失败',
            type: 'info'
          });
        }
      });
  },
  }
}
</script>

<style>
#app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}
.el-header {
  text-align: center;
  line-height: 60px;
  font-size: 20px;
}
.el-aside {
  color: black;
  line-height: 30px;
  text-align: center;
}
.el-main {
  padding: 10px;
  position: relative;
}

.login-container {
  position: absolute;
  top: 20px;
  right: 20px;
  width: 300px;
  background-color: white;
  padding: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
.hot-search-list {
  position: absolute;
  top:540px; /* Adjust this if necessary */
  right: 1px; /* Adjust this to position the hot search list on the right */
  width: 300px;
  background-color: white;
  padding: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.hot-search-item {
  display: flex;
  align-items: center;
  padding: 5px 0;
  font-size: 14px;
  border-bottom: 1px solid #f0f0f0;
}
.rank {
  width: 20px;
  text-align: center;
  font-weight: bold;
  color: #ff4500; /* 红色字体 */
}

.content {
  flex: 1;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.tags {
  display: flex;
  margin-left: 10px;
}

.tag {
  margin-left: 5px;
  padding: 1px 4px;
  border-radius: 3px;
  color: white;
  font-size: 10px;
}

.tag.hot {
  background-color: red;
}

.tag.new {
  background-color: pink;
}

.tag.commercial {
  background-color: blue;
}

.heat {
  margin-left: 10px;
  color: #888;
  font-size: 12px;
}
.message-list {
  width: 80%;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.message {
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 20px;
}

.message-title {
  font-size: 18px;
  font-weight: bold;
  color: #333;
  margin-bottom: 10px;
}

.message-content {
  font-size: 16px;
  color: #666;
  margin-bottom: 10px;
}

.message-author {
  font-size: 14px;
  color: #888;
  margin-bottom: 10px;
}

.image-container {
  max-width: 100%;
}

.message-image {
  display: block;
  width: 200px; /* 设置图片宽度 */
  height: auto; /* 设置图片高度自适应 */
  border-radius: 5px;
}

.comment-image {
  display: block;
  width: 200px; /* 设置图片宽度 */
  height: auto; /* 设置图片高度自适应 */
  border-radius: 5px;
}
.hot-search-list {
  position:absolute;
  right: 100px;
  max-width: 900px; /* 调整此值以设置组件的最大宽度 */
}
</style>
