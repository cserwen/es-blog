<template>
  <div class="markdown" v-loading="loading">
    <Markdown
        theme="oneDark"
        height="865"
        @on-save="onSave"
        @on-upload-image="onUpladImage"
        @on-ready="onReady"
        @on-input="getValue"
    />
  </div>
</template>

<script>
import Markdown from '../components/markdown/pro';
import {ElMessage} from "element-plus";

export default {
  name: "Editor",
  components: {
    Markdown
  },
  data: function () {
    return {
      insert: function () {

      },
      loading: false,
      token: '',
      value: ''
    }
  },
  methods: {
    onUpladImage(file) {
      console.log(this.value + 'dd')

      this.loading = true
      let formdata = new FormData()
      formdata.append('picture', file)
      this.axios.post('/article/uploadPicture', formdata, {
        headers: {
          'token': this.token,
          'Content-Type':'multipart/form-data'
        }
      }).then(res => {
        if (res.data.code === 1){
          ElMessage.error({
            showClose: true,
            message: "登录失效"})
          localStorage.setItem("stash", this.value)
          this.$router.push({name: 'Login'})
        }else {
          this.loading = false
          this.insert("![](" + res.data.data + ")")
        }
      }).catch(error => {
        this.loading = false
        ElMessage.error({
          showClose: true,
          message: "服务器异常"
        })
      })
    },
    onReady(data) {
      this.insert = data.insertContent
      this.insert(localStorage.getItem('stash'))
    },
    onSave(data) {
      localStorage.setItem("stash", data.value)
    },
    getValue(value){
      this.value = value
      //传直，发送保存
    }
  },
  mounted() {
    this.token = localStorage.getItem("token")
  }
}
</script>

<style lang="scss">
</style>