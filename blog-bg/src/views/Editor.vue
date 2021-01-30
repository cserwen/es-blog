<template>
  <div class="markdown">
    <Markdown
        v-model="content"
        theme="oneDark"
        height="865"
        auto-save="true"
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
      //上传图片，明天todo
    },
    onReady(data) {
      this.insert = data.insertContent
      this.insert(localStorage.getItem("article"))
    },
    onSave(data) {
      localStorage.setItem("article", data.value)
    }
  },
}
</script>

<style lang="scss">
</style>