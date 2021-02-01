<template>
  <div :class="`markdown ${fullscreen ? 'fullscreen' : ''} ${bordered ? 'border' : ''}`" ref="markdown" :style="{ width: width + 'px', height: height + 'px' }">
    <!-- 头部工具栏 -->
    <ul class="markdown-toolbars" v-show="!preview">
      <li>
        <slot name="title"/>
      </li>
      <li v-if="uploadImage" name="本地图片">
        <span @click="chooseImage" class="iconfont icon-upload-img"></span>
      </li>
      <li name="导入文件" class="import-file" v-show="importMd">
        <span class="iconfont icon-daoru" @click="importFile"></span>
        <input
            type="file"
            @change="importFile($event)"
            accept="text/markdown"
        />
      </li>
      <li name="保存到本地" v-show="exportMd">
        <span class="iconfont icon-download" @click="exportFile"></span>
      </li>
      <li v-if="split" name="全屏编辑">
        <span @click="split = false" class="iconfont icon-md"></span>
      </li>
      <li v-if="!split" name="分屏显示">
        <span @click="split = true" class="iconfont icon-group"></span>
      </li>
<!--      <li v-if="openPreview" name="预览">-->
<!--                <span-->
<!--                    @click="preview = true"-->
<!--                    class="iconfont icon-preview"-->
<!--                ></span>-->
<!--      </li>-->
      <li class="empty"></li>
      <li v-if="!fullscreen" name="全屏">
                <span
                    @click="fullscreen = true"
                    class="iconfont icon-fullscreen"
                ></span>
      </li>
      <li v-if="fullscreen" name="退出全屏">
                <span
                    @click="fullscreen = false"
                    class="iconfont icon-quite"
                ></span>
      </li>
    </ul>
    <!-- 关闭预览按钮 -->
    <div
        class="close-preview"
        v-show="preview"
        @click="preview = false"
    >
      <span class="iconfont icon-close"></span>
    </div>

    <!-- 编辑器 -->
    <div class="markdown-content" :style="{ background: preview ? '' : '#fff' }">
      <div class="codemirror" v-show="!preview" @mouseenter="mousescrollSide('left')" >
        <textarea id="editor" ></textarea>
      </div>
      <div v-show="split && !preview" class="markdown-preview" ref="preview"
          @scroll="previewScroll"
          @mouseenter="mousescrollSide('right')">
        <div v-html="html" ref="previewInner" class="markdown-body"></div>
      </div>
    </div>
<!--    &lt;!&ndash;    预览图片&ndash;&gt;-->
<!--    <div :class="['preview-img', previewImgModal ? 'active' : '']">-->
<!--            <span-->
<!--                class="close icon-close iconfont"-->
<!--                @click="previewImgModal = false"-->
<!--            ></span>-->
<!--      <img :src="previewImgSrc" :class="[previewImgMode]" alt=""/>-->
<!--    </div>-->
  </div>
</template>

<script>
import marked from '../config/marked';
import Codemirror from 'codemirror'
import '../css/codemirror.css'
import 'codemirror/addon/selection/active-line'
import 'codemirror/mode/markdown/markdown';
// import 'highlight.js/styles/atom-one-dark.css';
// import 'codemirror/addon/edit/matchbrackets'
import {ElMessage} from "element-plus";

export default {
  name: "Editor",
  data: function () {
    return {
      width: 'auto',
      height: 865,
      bordered: true,
      fullscreen: false,
      uploadImage:true,
      importMd: true,
      exportMd: true,
      split: true,
      openPreview: true,
      preview: false,
      previewImgSrc: '',
      previewImgMode: '',
      previewImgModal: false,
      html: '',
      value: '',
      scrollSide:'left',
      editorScrollHeight: 865,

      token: ''     ,

      editor: null,
      cmOptions: {
        tabSize: 4,
        mode: 'markdown',
        styleActiveLine: true,
        lineNumbers: true,
        lineWrapping: true,
        line: true,
        theme: 'material',
        cursorHeight:0.8,
        lineWiseCopyCut:true,
        showCursorWhenSelecting: true,
        extraKeys: {
          "Ctrl-S": function (editor) {
            //TODO 保存
          },
        }
      }
    }
  },
  methods: {
    init(){
      let editor = this.editor;
      this.editor.on('change', data => {
        this.value = editor.getValue();
        this.editorScrollHeight = data.doc.height;
      })
      this.editor.on('scroll', this.markdownScroll);
    },
    chooseImage() {

    },
    importFile(e) {
      const file = e.target.files[0];
      if (!file) {
        return;
      }
      const {type} = file;
      if (!['text/markdown', 'text/src'].includes(type)) {
        return;
      }
      const reader = new FileReader();
      reader.readAsText(file, {
        encoding: 'utf-8'
      });
      reader.onload = () => {
        this.value = reader.result;
        e.target.value = '';
        this.editor.setValue(this.value);
      };
      reader.onerror = err => {
        console.error(err);
      }
    },
    exportFile() {

    },
    mousescrollSide(side) {
      this.scrollSide = side;
    },
    markdownScroll(data = {}) {//编辑器区域滚动
      if (this.scrollSide === 'left') {
        let height = data.doc.height
        let scrollTop = data.doc.scrollTop
        let preview = this.$refs.preview;
        let contentHeight = preview.offsetHeight;
        let previewScrollHeight = preview.scrollHeight;
        preview.scrollTop = (scrollTop * (previewScrollHeight - contentHeight)) / (height - contentHeight)
      }
    },
    previewScroll() {
      if (this.scrollSide === 'right') {
        let preview = this.$refs.preview;
        let contentHeight = preview.offsetHeight;
        let previewScrollHeight = preview.scrollHeight;
        let previewScrollTop = preview.scrollTop;
        let scrollTop = (previewScrollTop * (this.editorScrollHeight - contentHeight)) / (previewScrollHeight - contentHeight)
        this.editor.scrollTo(0, scrollTop);
      }
    },
    onUploadImage(file) {
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
  },
  mounted() {
    this.editor = Codemirror.fromTextArea(document.getElementById('editor'), this.cmOptions)
    this.token = localStorage.getItem("token")
    this.init()
  },
  watch: {
    value() {
      let value = this.value
      this.html = marked(value, {}).replace(/<pre>/g, "<pre class='hljs'>");
    }
  }
}
</script>

<style lang="less">
@import "../css/editor";
@import "../css/theme2";
@import "../css/markdown.css";
@import "../assets/font/iconfont.css";
</style>