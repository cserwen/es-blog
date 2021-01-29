<template>
  <el-row v-show="!isPhone">
    <el-col :span="12">
      <div id="left">
        <label for="editor"></label><textarea id="editor" v-model="content" @input="buildMDText"/>
      </div>
    </el-col>
    <el-col :span="12">
      <div id="right">
        <div v-html="mdText" id="view" class="markdown-body" ref="rightScroll"/>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import hljs from "highlight.js";
import marked from 'marked'
import 'highlight.js/styles/atom-one-dark.css';

export default {
  name: "Editor",
  data() {
    return {
      content: '',
      isPhone: false,
      mdText: '',
    }
  },
  mounted() {
    this.isPhone = document.documentElement.clientWidth < 1200;
    console.log(this.isPhone)
    this.content = localStorage.getItem('article')
    this.buildMDText()
  },
  methods: {
    buildMDText() {
      this.mdText = marked(this.content, {
        breaks: true,
        gfm: true,
        tables: true,
        smartLists: true,
        highlight: function(code) {
          return hljs.highlightAuto(code).value;
        }
      }).replace(/<pre>/g, "<pre class='hljs'>")
      localStorage.setItem("article", this.content)
    },
  }
}
</script>

<style scoped>
@import "../css/markdown.css";

#editor {
  border: none;
  border-right: 1px solid #dddddd;
  resize: none;
  padding: 0 10px;
  width: 96%;
  outline: none;
  color: #222222;
  font-size: small;
  font-family: Arial, "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", sans-serif;
}

#right {
  overflow: auto;
  padding-right: 20px;
  padding-top: 20px;
  max-height: 86vh
}

#p_editor {
  width: 99%;
  height: 100%;
  resize: none;
  border: none;
  border-bottom: 1px solid #dddddd;
  outline: none;
  color: #222222;
  font-size: small;
  font-family: Arial, "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", sans-serif;
}
</style>