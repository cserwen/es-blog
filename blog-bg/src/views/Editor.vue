<template>
  <div class="markdown">
    <Markdown
        v-model="content"
        theme="oneDark"
        height="865"
        @on-save="onSave"
        @on-upload-image="onUpladImage"
        @on-ready="onReady"
    />
  </div>
</template>

<script>
import Markdown from '../components/markdown/pro';

export default {
  name: "Editor",
  components: {
    Markdown
  },
  data() {
    return {
      content: '',
      insert: function () {

      }
    }
  },
  methods: {
    onUpladImage(file) {
      let formdata = new FormData()
      formdata.append('picture', file)
      this.axios.post('/article/uploadPicture', formdata, {
        headers: {
          'Content-Type':'multipart/form-data'
        }
      }).then(res => {
        this.insert("![](" + res.data.data + ")")
        console.log(res)
      }).catch(error => {
        console.log(error);
      })
    },
    onReady(data) {
      this.insert = data.insertContent
      this.insert("服务器获取数据")
    },
    onSave(data) {
      //save服务器
      console.log(data.value)
    }
  },
}
</script>

<style lang="scss">
</style>