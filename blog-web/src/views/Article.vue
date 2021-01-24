<template>
  <div id="article" v-loading="loading">
    <div id="article-card" v-show="isShow" name="fade">
      <div id="article-title">{{ article.title }}</div>
      <div v-html="blog" id="article-content" class="markdown-body"></div>
      <div style="display: flex; margin-top: 20px">
        <div id="last" style="text-align: left; width: 50%">
          <el-button type="text" icon="el-icon-d-arrow-left" @click="getLastArticle">
            {{ article.hasLast ? article.lastName : '已经是第一篇啦~' }}
          </el-button>
        </div>
        <div id="next" style="text-align: right; width: 50%">
          <el-button type="text" @click="getNextArticle">
            {{ article.hasNext ? article.nextName : '已经是最后一篇了~' }}<i class="el-icon-d-arrow-right el-icon--right" ></i>
          </el-button>
        </div>
      </div>
    </div>
    <transition name="el-zoom-in-top">
      <el-tree v-show="tocShow" :data="tocData.children" :props="defaultProps" @node-click="handleNodeClick" id="article-toc">
      </el-tree>
    </transition>
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
      tocData: {
        children: []
      }
    }
  },
  methods: {
    getArticleDetails() {
      console.log(this.$route.params.id)
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
      let h = document.querySelectorAll("h1, h2, h3, h4, h5, h6")
      if (h.length === 0){
        return
      }

      let base_level = Number(h.item(0).nodeName.substring(1,2));
      this.buildToc(h, 0, base_level)
      this.tocShowChange()
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
        window.scrollTo(0,0);
        this.$router.push({name: 'Article', params: {id: this.article.lastId}})
        this.hideAside()
        setTimeout(this.getArticleDetails, 200)
      }
    },
    getNextArticle() {
      if (this.article.hasNext) {
        window.scrollTo(0,0);
        this.$router.push({name: 'Article', params: {id: this.article.nextId}})
        this.hideAside()
        setTimeout(this.getArticleDetails, 200)
      }
    }

  },
  created() {
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

#article-title {
  font-size: 34px;
  font-weight: 700;
  padding: 40px;
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


</style>