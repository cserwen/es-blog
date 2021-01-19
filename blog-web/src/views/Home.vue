<template>
  <div id="home">
    <ul class="article-list" v-infinite-scroll="load" infinite-scroll-disabled="disabled">
      <li v-for="i in count" class="list-item">
        <div class="article-card">
          <el-button type="text" id="title">npm全局安装失败解决办法</el-button>
          <div id="time">2020.12.31</div>
          <div id="description">本文介绍如何解决npm全局安装失败的问题</div>
          <el-button icon="el-icon-position">开始阅读</el-button>
          <hr id="line"/>
          <el-button v-for="key in keys" class="article-key" size="mini">{{ key }}</el-button>
        </div>
      </li>
    </ul>
    <p v-if="loading">加载中...</p>
    <p v-if="noMore">没有更多了</p>
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      count: 10,
      loading: false,
      keys: [
          "Linux",
          "npm"
      ]
    }
  },
  computed: {
    noMore() {
      return this.count >= 20;
    },
    disabled() {
      return this.loading || this.noMore
    }
  },
  methods: {
    load () {
      this.loading = true
      setTimeout(() => {
        this.count += 2
        this.loading = false
      }, 2000)
    }
  }
}
</script>

<style>
#home {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  width: 100%;
  height: 100%;
  text-align: center;

}

.article-list{
  list-style-type: none;
  margin: 0;
  padding: 0;
  width: 45%;
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
  margin-bottom: 12px;
  padding: 0;
  background-color: #dddddd;
  height: 1px;
  border: 0;
}

</style>