<template>
  Home
</template>

<script>
import {ElMessage} from "element-plus";

export default {
  name: "Home",
  data() {
    return {
      token: ''
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
    }
  },
  mounted() {
    this.isLogin();
  }
}
</script>

<style scoped>

</style>