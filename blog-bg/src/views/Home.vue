<template>
  <div id="menu">
    <div style="display: flex">
      <div style="width: 100%">
        <el-menu mode="horizontal">
          <el-menu-item index="1" @click="changeView('Blog')">博客管理</el-menu-item>
          <el-menu-item index="2" @click="changeView('Comment')">评论管理</el-menu-item>
          <el-menu-item index="3" @click="changeView('Editor')">写文章</el-menu-item>
        </el-menu></div>
    </div>
    <router-view/>
  </div>
</template>

<script>
import {ElMessage} from "element-plus";

export default {
  name: "Home",
  data() {
    return {
      token: '',
      activeName: 'first',
      blogContent: ''
    }
  },
  methods: {
    isLogin() {
      let token = ''
      token = localStorage.getItem("token")
      this.axios.post("/user/auth", {
        "token": token
      }, ).then(res => {
        let code = res.data.code
        if (code === 1) {
          ElMessage.error({
            showClose: true,
            message: "登录失效"})
          this.$router.push({name: 'Login'})
        }else if (!res.data.data) {
          ElMessage.error({
            showClose: true,
            message: "登录失效"})
          this.$router.push({name: 'Login'})
        }
      }).catch(error => {
        ElMessage.error({
          showClose: true,
          message: "服务器异常"})
      })
    },
    changeView(view) {
      this.$router.push({name: view})
    },
    publishBlog() {

    }
  },
  mounted() {
    this.isLogin();
  }
}
</script>

<style >
#menu {
  width: 96%;
  margin-left: 2%;
  margin-top: 10px;
  height: 95vh;
  box-shadow:0 0 50px #cccccc;
  border: 1px solid #dddddd;
  border-radius: 15px;
  padding-top: 12px;
}

#editor {
  min-height: 88vh;
}

</style>