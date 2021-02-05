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
    <div style="margin: 0 24% 30px; text-align: left" v-show="isShow">
      <el-tooltip content="请评论吧！" placement="right-start" effect="light" style="width: 160px; margin: 11px 0"><div >コメントしてください</div></el-tooltip>
      <div id="comment-editor" >
        <div style="display: flex">
          <div :class="isWriteComment ? 'chooseStyle' : 'unChooseStyle'" @click="isWriteComment = true">
            评论
          </div>
          <div :class="!isWriteComment ? 'chooseStyle' : 'unChooseStyle'" @click="isWriteComment = false">
            预览
          </div>
          <div class="editor-bars" style="width: 80%; text-align: right">
          </div>
        </div>
        <div style="display: block; min-height: 200px">
          <div class="write" v-show="isWriteComment">
            dddaawd
          </div>

          <div class="preview" v-show="!isWriteComment">

          </div>
        </div>
      </div>
      <div style="display: flex; padding: 5px">
        <div style="width: 70%; color: grey">
          Markdown is supported!
        </div>
        <div style="width: 30%; text-align: right">
          <el-button>发布评论</el-button>
        </div>
      </div>
    </div>

    <div id="comment">
      <div class="comment-card" v-for="comment in comments">
        <div style="padding: 0 10px; text-align: left; color: #999999;display: flex">
          <div style="width: 92%"><span type="text" style="margin: 0 5px">{{ comment.username }}</span>{{ comment.createTime }}</div>
          <div style="width: 8%; text-align: center" class="replay"><el-button type="text" @click="addComment">replay</el-button></div>
        </div>
<!--        <div style='background-color:#dddddd;height:1px;border:none; margin: 5px 0'/>-->
        <div v-show="comment.replyId !== 0" style="display: flex">
          <blockquote style="padding: 0 10px; color: #6a737d; border-left: .20em solid #97CAFF; margin: 10px 30px 5px">
            <p style="text-align: left; margin: 1px; color: #888888">{{ comment.replyContent }}</p>
          </blockquote>
        </div>
        <div  style="text-align: left; margin:3px 20px; color: gray">
          {{ comment.comment }}
        </div>
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
      isWriteComment: true
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
    getLastArticle() {
      if (this.article.hasLast){
        this.tocData = {
          children: []
        }
        this.index = 0
        this.isShow = false
        this.tocShow = false
        window.scrollTo(0,0);
        this.$router.push({name: 'Article', params: {id: this.article.lastId}})
        this.hideAside()
        setTimeout(this.getArticleDetails, 200)
      }
    },
    getNextArticle() {
      if (this.article.hasNext) {
        this.tocData = {
          children: []
        }
        this.index = 0
        this.isShow = false
        this.tocShow = false
        window.scrollTo(0,0);
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

    }

  },
  created() {
    this.isPhone = document.documentElement.clientWidth < 1200;
    console.log(this.isPhone)
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

#comment {
  margin: 0 24% 30px;
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
  width: 100%;
  height: auto;
  border-top: 1px solid #dddddd;
}

.preview {
  width: 100%;
  height: auto;
  border-top: 1px solid #dddddd;
}

.chooseStyle {
  border-bottom: 2px solid #97CAFF;
  padding: 5px 10px;
}

.unChooseStyle {
  padding: 5px 10px;

}

</style>