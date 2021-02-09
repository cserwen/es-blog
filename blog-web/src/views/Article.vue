<template>
  <div id="article" v-loading="loading">
    <div :id="isPhone ? 'p_article-card' : 'article-card'" v-show="isShow" name="fade">
      <div id="article-title">{{ article.title }}</div>
      <div v-html="blog" id="article-content" class="markdown-body"></div>
      <div style="display: flex; margin-top: 20px">
        <div style="text-align: left; width: 50%">
          <el-button v-show="isPhone" type="text" icon="el-icon-d-arrow-left" @click="getNextArticle">
            {{ article.hasNext ? '下一篇' : '后面没有了' }}
          </el-button>
          <el-button v-show="!isPhone" type="text" icon="el-icon-d-arrow-left" @click="getNextArticle">
            {{ article.hasNext ? article.nextName : '已经是最后一篇了~' }}
          </el-button>
        </div>
        <div style="text-align: right; width: 50%">
          <el-button type="text" @click="getLastArticle">
            <div v-show="isPhone">{{ article.hasLast ? "上一篇" : '前面没有了' }}<i class="el-icon-d-arrow-right el-icon--right" ></i></div>
            <div v-show="!isPhone">{{ article.hasLast ? article.lastName : '已经是第一篇啦~' }}<i class="el-icon-d-arrow-right el-icon--right" ></i></div>
          </el-button>
        </div>
      </div>
    </div>
    <transition name="el-zoom-in-top">
      <el-tree v-show="tocShow" :data="tocData.children" :props="defaultProps" @node-click="handleNodeClick" id="article-toc">
      </el-tree>
    </transition>
    <div style="" v-show="isShow" :class="isPhone ? 'p_writeDiv' : 'writeDiv'">
      <el-tooltip content="请评论吧！" placement="right-start" effect="light" style="width: 160px; margin: 11px 0"><div >コメントしてください</div></el-tooltip>
      <div style="display: flex; margin: 0">
        <el-input v-model="username" placeholder="请输入昵称"></el-input>
        <el-input v-model="site" placeholder="请输入网站"></el-input>
        <el-input v-model="email" placeholder="请输入邮箱"></el-input>

      </div>
      <div id="comment-editor" >
        <div style="display: flex">
          <div :class="isWriteComment ? 'chooseStyle' : 'unChooseStyle'" @click="isWriteComment = true">
            评论
          </div>
          <div :class="!isWriteComment ? 'chooseStyle' : 'unChooseStyle'" @click="previewComment">
            预览
          </div>
          <div class="editor-bars" style="width: 70%; text-align: right">
          </div>
        </div>
        <div style="display: block; min-height: 200px">
          <textarea class="write" id="write" v-show="isWriteComment" placeholder="请输入内容" rows="8" v-model="commentContext"></textarea>
          <div class="preview markdown-body" v-show="!isWriteComment" v-html="commentHtml">

          </div>
        </div>
      </div>
      <div style="display: flex; padding: 5px">
        <div style="width: 70%; color: grey">
          <el-button type="text">Markdown is supported!</el-button>
        </div>
        <div style="width: 30%; text-align: right">
          <el-button @click="addComment" size="small">发布评论</el-button>
        </div>
      </div>
    </div>

    <div :class="isPhone ? 'p_comment' : 'comment'">
      <div class="comment-card" v-for="comment in comments">
        <div style="padding: 0 10px; text-align: left; color: #999999;display: flex">
          <div style="width: 92%"><el-link style="margin: 5px 5px; font-size: 1em" :href="comment.site" :disabled="comment.site === ''">{{ comment.username }}</el-link>
            <span style="font-size: 0.9em; padding-left: 5px">{{ new Date(comment.createTime).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'') }}</span></div>
          <div style="width: 8%; text-align: center" class="replay"><el-button type="text" @click="replayComment(comment)">replay</el-button></div>
        </div>
<!--        <div style='background-color:#dddddd;height:1px;border:none; margin: 5px 0'/>-->
        <div v-show="comment.replyId !== 0" style="display: flex">
          <blockquote style="padding: 0 10px; color: #6a737d; border-left: .20em solid #97CAFF; margin: 10px 30px 5px">
<!--            <p style="text-align: left; margin: 1px; color: #888888">{{ comment.replyContent }}</p>-->
            <div v-html="comment.replyContent" style="text-align: left"/>
          </blockquote>
        </div>
        <div  style="text-align: left; margin:3px 20px; color: gray" v-html="comment.comment" class="markdown-body"></div>
      </div>
    </div>
  </div>
</template>

<script>
import marked from 'marked'
import {ElMessage} from "element-plus";
import hljs from "highlight.js"
import 'highlight.js/styles/atom-one-dark.css';

export default {
  name: "Article",
  data() {
    return {
      article: {},
      loading: false,
      blog: "",
      isShow: false,
      index: 0,
      tocShow: false,
      isPhone: false,
      tocData: {
        children: []
      },
      comments: [],
      isWriteComment: true,
      commentContext: '',
      commentHtml: '',
      username: '',
      site: '',
      email: '',
      replayId: 0,
    }
  },
  methods: {
    getArticleDetails() {
      this.loading = true
      this.axios.get('/article/getArticleById', {
        params: {
          id: this.$route.params.id
        }
      }).then(res => {
        if (res.data.code === 0){
          this.article = res.data.data
          this.blog = marked(this.article.content, {
            breaks: true,
            gfm: true,
            tables: true,
            smartLists: true,
            highlight: function(code) {
              return hljs.highlightAuto(code).value;
            }
          }).replace(/<pre>/g, "<pre class='hljs'>")
          this.isShow = true
          setTimeout(this.findHeadTag, 1);
        }else {
          ElMessage.warning({
            showClose: true,
            message: res.data.data
          })
        }
        this.loading = false
      }).catch(error => {
        ElMessage.error({
          showClose: true,
          message: "服务器异常"
        })
        this.loading = false
      })
    },
    hideAside() {
      this.$emit("handleHide")
    },
    handleNodeClick(node) {
      window.location.href = '#' + node.label
    },
    findHeadTag(){
      if (this.isPhone){
        this.getComments()
        return;
      }
      let h = document.querySelectorAll("h1, h2, h3, h4, h5, h6")
      if (h.length === 0){
        this.getComments()
        return
      }

      let base_level = Number(h.item(0).nodeName.substring(1,2));
      this.buildToc(h, 0, base_level)
      this.tocShowChange()
      this.getComments()
    },
    tocShowChange(){
      this.tocShow = true
    },
    buildToc(nodes, currentLevel, baseLevel) {
      let local = this.tocData
      for (let j = 0; j < currentLevel-baseLevel; j++) {
        local = this.tocData.children[this.tocData.children.length-1]
      }
      for (; this.index < nodes.length; this.index++) {
        let node = nodes.item(this.index)
        let level = Number(node.nodeName.substring(1,2))
        let toc = {
          label: node.id,
          children: []
        }
        if (currentLevel === level ){
          local.children.push(toc)
        } else if (currentLevel < level ) {
          this.buildToc(nodes,currentLevel+1, baseLevel)
        }else {
          this.buildToc(nodes, currentLevel-1, baseLevel)
        }
      }
    },
    initParams() {
      this.commentContext = ''
      this.commentHtml = ''
      this.isWriteComment = true
      this.site = ''
      this.email = ''
      this.username = ''
      this.replayId = 0
      this.tocData = {
        children: []
      }
      this.index = 0
      this.isShow = false
      this.tocShow = false
      window.scrollTo(0,0);
    },
    getLastArticle() {
      if (this.article.hasLast){
        this.initParams()

        this.$router.push({name: 'Article', params: {id: this.article.lastId}})
        this.hideAside()
        setTimeout(this.getArticleDetails, 200)
      }
    },
    getNextArticle() {
      if (this.article.hasNext) {
        this.initParams()
        this.$router.push({name: 'Article', params: {id: this.article.nextId}})
        this.hideAside()
        setTimeout(this.getArticleDetails, 200)
      }
    },
    getComments() {
      this.axios.get('/comment/getCommentsByArticleId', {
        params: {
          articleId: this.$route.params.id
        }
      }).then(res => {
        if (res.data.code === 0){
          this.comments = res.data.data

        }else {
          ElMessage.warning({
            showClose: true,
            message: res.data.data
          })
        }
      }).catch(error => {
        ElMessage.error({
          showClose: true,
          message: "服务器异常"
        })
      })
    },
    addComment() {
      if (this.commentContext === '') {
        ElMessage.warning({
          showClose: true,
          message: '内容不能为空'
        })
        return
      }
      this.commentHtml = marked(this.commentContext, {
        breaks: true,
        gfm: true,
        tables: true,
        smartLists: true,
        highlight: function(code) {
          return hljs.highlightAuto(code).value;
        }
      }).replace(/<pre>/g, "<pre class='hljs'>")
      this.axios.post("/comment/add", {
        comment: this.commentHtml,
        articleId: this.$route.params.id,
        replyId: this.replayId,
        username: this.username === '' ? "陌生人" : this.username,
        site: this.site,
        email: this.email
      }).then(res => {
        if (res.data.code === 0) {
          ElMessage.success({
            showClose: true,
            message: '发送成功'
          })
          this.getComments()
          this.commentContext = ''
          this.commentHtml = ''
          this.replayId = 0
        }else {
          ElMessage.warning({
            showClose: true,
            message: '发送失败'
          })
        }
      }).catch(err => {
        ElMessage.error({
          showClose: true,
          message: '服务器异常'
        })
      })
    },
    previewComment(){
      this.isWriteComment = false
      this.commentHtml = marked(this.commentContext, {
        breaks: true,
        gfm: true,
        tables: true,
        smartLists: true,
        highlight: function(code) {
          return hljs.highlightAuto(code).value;
        }
      }).replace(/<pre>/g, "<pre class='hljs'>")
    },
    replayComment(comment) {
      //TODO
      let textarea = document.getElementById('write')
      this.isWriteComment = true
      this.replayId = comment.id;
      this.commentContext = '@' + comment.username + ": "
      textarea.focus()

    }
  },
  created() {
    this.isPhone = document.documentElement.clientWidth < 1200;
    this.hideAside()
    setTimeout(this.getArticleDetails, 200)
  },
}
</script>

<style >
@import "../css/markdown.css";

#article {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  min-height: 80px;
}

#article-card {
  margin: 50px 24%;
  box-shadow:0 0 50px #cccccc;
  padding: 20px;
  border-radius: 15px;
  border: 1px;
  animation: show-article 500ms;
}

#p_article-card {
  margin: 40px 10px;
  box-shadow:0 0 50px #cccccc;
  padding: 10px;
  border-radius: 15px;
  border: 1px;
  animation: show-article 500ms;
}

#article-title {
  font-size: 34px;
  font-weight: 700;
  padding: 40px 0;
}

#article-content {
  text-align: left;
  min-height: 60vh;
}

@keyframes show-article {
  0% {
    opacity: 0;
  }

  100% {
    opacity: 1;
  }
}

#article-toc{
  margin-top: 100px;
  margin-right: 50px;
  margin-left: 100px;
  padding: 10px;
  top: 0;
  left:76%;
  right:0;
  position: fixed;
  min-height: 100px;
  box-shadow: 0px 0px 10px #888888;
  border-radius: 7px;
}

.el-tree-node {
  padding: 3px;
}

.comment-card {
  margin: 20px 0;
  padding: 10px 0;
  border: 1px solid #ffffff;
}

.comment-card:hover {
  margin: 20px 0;
  padding: 10px 0;
  background-color: #e1e4e8;
  border: 1px solid #e1e4e8;
  box-shadow: 0 0 20px #c6cbd1;
  border-radius: 10px;
}

.comment {
  margin: 0 24% 30px;
}

.p_comment {
  margin: 0 10px 30px;
}

.commentUser {
  border-radius: 5px;
  border: 1px solid #999999;
  padding: 2px 5px;
}

.replay {
}

.comment-card .el-button {
  padding: 0;
}

#comment-editor {
  border-radius: 5px;
  border: 1px solid #dddddd;
  max-height: 200px;
}

.write {
  padding: 10px;
  width: calc(100% - 20px);
  resize: none;
  font-size: 1em;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
  border-top: 1px solid #dddddd;
  outline: none;
  border-left: none;
  border-bottom: none;
  border-right: none;

}

.preview {
  width: calc(100% - 20px);
  min-height: 50px;
  max-height: 140px;
  overflow: auto;
  padding: 10px 10px;
  border-top: 1px solid #dddddd;

}

.chooseStyle {
  border-bottom: 2px solid #97CAFF;
  padding: 5px 10px;
}

.unChooseStyle {
  padding: 5px 10px;

}

.writeDiv {
  margin: 0 24% 30px;
  text-align: left;
}

.p_writeDiv {
  margin: 0 10px 30px;
  text-align: left;
}

</style>