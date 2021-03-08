<template>
  <div id="home">
    <ul :class="isPhone ? 'p_article-list' : 'article-list'" v-loading="loading">
      <li v-for="article in articles" :class="isPhone ? 'p_list-item' : 'list-item'">
        <div class="article-card" v-show="!isPhone">
          <router-link id="title" target="_blank" :to="{path: '/article', query: {id: article.id}}">{{ article.title }}</router-link>
          <div id="time">{{ new Date(article.createTime).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'') }}</div>
          <div id="description">{{ article.description }}</div>
          <el-button icon="el-icon-position" @click="this.$router.push({name: 'Article', params: {id: article.id}})">开始阅读</el-button>
          <hr id="line"/>
          <el-button v-for="(tag, index) in article.tags" class="article-key"
                     size="mini" :type="buttonColor[index]" :icon="buttonIcon[index]">{{ tag }}</el-button>
        </div>
        <div class="p_article-card" v-show="isPhone">
          <router-link id="p_title" target="_blank" :to="{path: '/article', query: {id: article.id}}">{{ article.title }}</router-link>
          <div id="p_time">{{ new Date(article.createTime).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'') }}</div>
          <div id="p_description">{{ article.description }}</div>
          <el-button icon="el-icon-position" @click="this.$router.push({name: 'Article', params: {id: article.id}})" size="small">开始阅读</el-button>
          <hr id="p_line"/>
          <el-button v-for="(tag, index) in article.tags" class="article-key"
                     size="mini" :type="buttonColor[index]" :icon="buttonIcon[index]">{{ tag }}</el-button>
        </div>
      </li>
    </ul>
    <el-pagination v-show="isShow" @current-change="getArticleList" background layout="prev, pager, next" :page-count="pages" >
    </el-pagination>
  </div>
</template>

<script>
import {ElMessage} from "element-plus";

export default {
  name: "Home",
  data() {
    return {
      pages: -1,
      articles: [],
      loading: false,
      isShow: false,
      buttonColor: [
        'primary', 'success', 'warning', 'danger'
      ],
      buttonIcon: [
        'el-icon-s-opportunity', 'el-icon-s-flag', 'el-icon-info', 'el-icon-star-on'
      ],
      isPhone: false
    }
  },
  methods: {
    getArticleList(current) {
      window.scrollTo(0,0);
      this.loading = true
      this.axios.get('/article/list/', {
        params: {
          next: current,
          size: 5
        }
      }).then(res => {
        if (res.data.code === 0){
          this.articles = res.data.data.articleCards;
          this.pages = res.data.data.pages;
          this.loading = false
          this.isShow = true
        }else {
          ElMessage.warning({
            showClose: true,
            message: res.data.data
          })
        }
      }).catch(error => {
        console.log(error);
        ElMessage.error({
          showClose: true,
          message: "服务器异常"
        })
        this.loading = false
        this.isShow = false
      })
    },
  },
  mounted() {
    this.getArticleList(1);
    this.isPhone = document.documentElement.clientWidth < 1200;
    if (this.isPhone) {
      this.isShow = false
    }
    document.title = "cserwen - 首页"
  }
}
</script>

<style>
#home {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  height: 100%;
  text-align: center;
  margin-bottom: 30px;
}

.article-list{
  list-style-type: none;
  margin: 0;
  padding: 0;
  width: 46%;
  min-height: 91vh;
  display: inline-block;
}

.p_article-list {
  list-style-type: none;
  margin: 0;
  padding: 25px 0 0 0;
  width: 90%;
  min-height: 91vh;
  display: inline-block;
}


.article-card {
  margin: 30px;
  height: 250px;
  border-radius: 10px;
  border-color: #969896;
  box-shadow:0 0 20px #ddd;
  transition: box-shadow 300ms;
}

.p_article-card {
  padding-top: 15px;
  padding-bottom: 8px;
  margin: 15px 0;
  min-height: 160px;
  border-radius: 10px;
  border-color: #969896;
  box-shadow:0 0 20px #ddd;
  transition: box-shadow 300ms;
}

.p_article-card:hover {
  box-shadow: 0 5px 25px #97CAFF;
}


.article-card:hover {
  box-shadow: 0 5px 25px #97CAFF;
}

#title {
  text-decoration: none;
  color: #333333;
  line-height: 60px;
  font-size: 25px;
  transition:  color 300ms;
}

#p_title {
  word-break: break-word;
  text-decoration: none;
  clear: both;
  color: #333333;
  font-size: 18px;
  transition:  color 300ms;
}

#title:hover {
  color: #3C8CE7;
}

#time {
  color: #bbbbbb;
}

#p_time {
  color: #bbbbbb;
  margin-top: 15px;
}

#description {
  color: #666666;
  padding-top: 15px;
  padding-bottom: 15px;
}

#p_description {
  color: #666666;
  font-size: 12px;
  padding-top: 10px;
  padding-bottom: 10px;
}

#line {
  margin-top: 15px;
  margin-bottom: 12px;
  padding: 0;
  background-color: #dddddd;
  height: 1px;
  border: 0;
}

#p_line {
  padding: 0;
  background-color: #dddddd;
  height: 1px;
  border: 0;
}



</style>