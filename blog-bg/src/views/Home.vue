<template>
  <el-tabs v-model="activeName" type="border-card" @tab-click="handleClick" class="tab">
    <el-tab-pane label="博客管理" name="first"></el-tab-pane>
    <el-tab-pane label="评论管理" name="second"></el-tab-pane>
    <el-tab-pane label="写文章" name="third" ><div id="editor"></div></el-tab-pane>
  </el-tabs>
</template>

<script>
import {ElMessage} from "element-plus";

export default {
  name: "Home",
  data() {
    return {
      token: '',
      activeName: 'first'
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
        if (code === 1000){
          ElMessage.error({
            showClose: true,
            message: res.data.data})
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
    handleClick(tab, event) {
      console.log(tab, event);
    }
  },
  mounted() {
    // this.isLogin();
  }
}
</script>

<style >
.tab {
  width: 94%;
  margin: 1% 3%;
}

#editor {
  min-height: 88vh;
}
</style>