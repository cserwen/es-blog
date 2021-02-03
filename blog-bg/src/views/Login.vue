<template>
  <div id="login">
    <div id="input-card">
      <el-input placeholder="请输入账号" v-model="username" clearable class="input-form"></el-input>
      <el-input placeholder="请输入密码" v-model="password" show-password class="input-form"></el-input>
      <el-button style="width: 100%; margin: 5px 0" type="primary" @click="login">登录</el-button>
    </div>
  </div>
</template>

<script>
import {ElMessage} from "element-plus";
import md5 from 'js-md5';

export default {
  name: "Login",
  data() {
    return {
      username: '',
      password:''
    }
  },
  methods: {
    login() {
      this.axios.post("/user/login", {
        "username": this.username,
        "passwd": md5(this.password)
      }, ).then(res => {
        let code = res.data.code
        if (code === 1000){
          ElMessage.error({
            showClose: true,
            message: res.data.data})
          this.$router.push({name: 'Login'})
        } else {
          localStorage.setItem("token", res.data.data.token)
          ElMessage.success({
            showClose: true,
            message: "登陆成功"})
          this.$router.push({name: 'Home'})
        }
      }).catch(error => {
        ElMessage.error({
          showClose: true,
          message: "服务器异常"})
      })
    }
  }
}
</script>

<style>

#input-card {
  position: absolute;
  top: calc(50% - 80px);
  left: calc(50% - 130px);
  height: 160px;
  width: 260px;
  text-align: center;
  border-radius: 10px;
}

.input-form {
  width: 90%;
  margin: 5px 0;
}

</style>