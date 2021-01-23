<template>
  <div id="home">
    <ul class="article-list" v-loading="loading">
      <li v-for="article in articles" class="list-item">
        <div class="article-card">
          <el-button type="text" id="title" @click="this.$router.push({name: 'Article', params: {id: article.id}})">{{ article.title }}</el-button>
          <div id="time">{{ new Date(article.createTime).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'') }}</div>
          <div id="description">{{ article.description }}</div>
          <el-button icon="el-icon-position" @click="this.$router.push({name: 'Article', params: {id: article.id}})">开始阅读</el-button>
          <hr id="line"/>
          <el-button v-for="(tag, index) in article.tags" class="article-key"
                     size="mini" :type="getButtonColor(index)" :icon="getButtonIcon(index)">{{ tag }}</el-button>
        </div>
      </li>
    </ul>
    <el-pagination v-show="isShow" @current-change="getArticleList" background layout="prev, pager, next" :page-count="this.pages" >
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
      isShow: true
    }
  },
  methods: {
    getArticleList(current) {
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
    getButtonColor(index){
      switch (index){
        case 0:
          return 'primary'
        case 1:
          return 'success'
        case 2:
          return 'warning'
        case 3:
          return 'danger'
      }
    },
    getButtonIcon(index){
      switch (index){
        case 0:
          return 'el-icon-s-opportunity'
        case 1:
          return 'el-icon-s-flag'
        case 2:
          return 'el-icon-info'
        case 3:
          return 'el-icon-star-on'
      }
    }
  },
  beforeMount() {
    this.getArticleList(1);
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

.article-card {
  margin: 30px;
  height: 250px;
  border-radius: 10px;
  border-color: #969896;
  box-shadow:0 0 20px #ddd;
  transition: box-shadow 300ms;
}

.article-card:hover {
  box-shadow: 0 5px 25px #97CAFF;
}

#title {
  color: #333333;
  font-size: 25px;
  transition:  color 300ms;
  padding-top: 20px;
}

#title:hover {
  color: #3C8CE7;
}

#time {
  color: #bbbbbb;
  padding-top: 10px;
}

#description {
  color: #666666;
  padding-top: 15px;
  padding-bottom: 15px;
}

#line {
  margin-top: 15px;
  margin-bottom: 15px;
  padding: 0;
  background-color: #dddddd;
  height: 1px;
  border: 0;
}

.article-key {
  background-color: #3C8CE7;
}

</style>