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
        <span class="iconfont icon-download" @click="exportFile('untitled')"></span>
      </li>
      <li v-if="split" name="关闭预览">
        <span @click="split = false" class="iconfont icon-md"></span>
      </li>
      <li v-if="!split" name="开启预览">
        <span @click="split = true" class="iconfont icon-group"></span>
      </li>
<!--      <li v-if="openPreview" name="预览">-->
<!--                <span-->
<!--                    @click="preview = true"-->
<!--                    class="iconfont icon-preview"-->
<!--                ></span>-->
<!--      </li>-->
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
      <li class="empty"></li>

    </ul>

    <el-button  @click="dialogVisible=true" type="text" class="publish-blog">发布</el-button>
    <el-dialog title="发布" v-model="dialogVisible" width="30%" center :close-on-click-modal="closeDialog" :close-on-press-escape="closeDialog" :show-close="closeDialog" :model-value="dialogVisible">
      <el-input v-model="blogTitle" placeholder="请输入标题" clearable size="small" style="margin: 5px"></el-input>
      <el-input v-model="description" placeholder="请输入文章描述" clearable size="small" style="margin: 5px"> </el-input>
      <el-select v-model="chooseKeys" multiple filterable allow-create default-first-option placeholder="请选择文章标签" size="mini" clearable style="margin: 5px; width: 100%">
        <el-option
            v-for="item in keys"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-switch v-model="isDiscuss" active-text="开启评论" style="margin: 15px 10px 0"></el-switch>
      <el-switch v-model="isOpen" active-text="是否公开" style="margin: 15px 10px 0"></el-switch>
      <el-switch v-model="isReward" active-text="开启打赏" style="margin: 15px 10px 0"></el-switch><br/>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="publishBlog">发 布</el-button>
        </span>
      </template>
    </el-dialog>
    <!-- 编辑器 -->
    <div class="markdown-content" :style="{ background: preview ? '' : '#fff' }" v-loading="loading">
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
import 'codemirror/theme/material.css'
import 'codemirror/addon/selection/active-line'
import 'codemirror/mode/markdown/markdown';
import 'highlight.js/styles/atom-one-dark.css';
import {ElMessage} from "element-plus";

export default {
  name: "Editor",
  data: function () {
    return {
      width: 'auto',
      height: 855,
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
      editorScrollHeight: 855,
      keys: [{ value: '', label: ''}],

      dialogVisible: false,
      token: ''     ,
      loading: false,
      editor: null,
      blogTitle: '',
      description: '',
      isDiscuss: true,
      isOpen: true,
      isReward: true,
      chooseKeys: [],
      closeDialog: false,

      cmOptions: {
        tabSize: 4,
        mode: 'markdown',
        styleActiveLine: true,
        lineNumbers: true,
        lineWrapping: true,
        line: true,
        theme: 'default',
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
      const input = document.createElement('input');
      input.type = 'file';
      input.accept = 'image/*';
      input.onchange = ()=>{
        const files = input.files;
        if(files[0]){
          this.toUploadImage(files[0])
          input.value = '';
        }
      }
      input.click();
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
    exportFile(fileName) {
      let pom = document.createElement('a');
      pom.setAttribute('href', 'data:text/plain;charset=UTF-8,' + encodeURIComponent(this.editor.getValue()));
      pom.setAttribute('download', fileName + '.md');
      pom.style.display = 'none';
      if (document.createEvent) {
        const event = document.createEvent('MouseEvents');
        event.initEvent('click', true, true);
        pom.dispatchEvent(event);
      } else {
        pom.click();
      }
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
    toUploadImage(file) {
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
          this.$router.push({name: 'Login'})
        }else {
          this.loading = false
          this.editor.replaceSelection("![](" + res.data.data + ")")
          this.editor.focus()
        }
      }).catch(error => {
        this.loading = false
        ElMessage.error({
          showClose: true,
          message: "服务器异常"
        })
      })
    },
    publishBlog() {
      if (this.blogTitle === ''){
        ElMessage.warning({
          showClose: true,
          message: "请输入标题"
        })
        return
      }

      if (this.description === ''){
        ElMessage.warning({
          showClose: true,
          message: "请输入标文章描述"
        })
        return
      }

      if (this.editor.getValue() === ''){
        ElMessage.warning({
          showClose: true,
          message: "文章内容为空"
        })
        return
      }

      if (this.chooseKeys.length === 0){
        ElMessage.warning({
          showClose: true,
          message: "请设置标签"
        })
        return
      }

      this.axios.post("/article/create", {
        "title": this.blogTitle,
        "description": this.description,
        "content": this.editor.getValue(),
        "isDiscuss": this.isDiscuss,
        "isOpen": this.isOpen,
        "isReward": this.isReward
        //TODO 添加标签
      }, {headers: {
          'token': this.token
        }}).then(res => {
        let code = res.data.code
        if (code === 1){
          ElMessage.error({
            showClose: true,
            message: res.data.data})
          this.$router.push({name: 'Login'})
        } else {
          ElMessage.success({
            showClose: true,
            message: "发布成功"
          })
        }
      }).catch(error => {
        ElMessage.error({
          showClose: true,
          message: "服务器异常"})
      })

      this.dialogVisible = false

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
@import "../css/theme";
@import "../css/markdown.css";
@import "../assets/font/iconfont.css";
</style>