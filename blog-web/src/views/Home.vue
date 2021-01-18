<template>
  <div id="home">
    <ul class="article-list" v-infinite-scroll="load" infinite-scroll-disabled="disabled">
      <li v-for="i in count" class="list-item">
        <div class="article-card">
          <div style="padding: 14px;">
            <span>好吃的汉堡</span>
            <div class="bottom clearfix">
              <el-button type="text" class="button">操作按钮</el-button>
            </div>
          </div>
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
      loading: false
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
  width: 40%;
  display: inline-block;
}

.el-card {

}

.article-card {
  margin: 30px;
  height: 200px;
  border-radius: 15px;
  border-color: #969896;
  box-shadow:0 0 20px #ddd;
}

.article-card:hover {
  animation: hover-card 0.2s;
  transform: translate(0,-6px);
  transition-delay: 0.2s;
  box-shadow: 0 2px 20px #97CAFF;
}

@keyframes hover-card{
  0% {
    transform:translate(0,0);
    box-shadow:0 0 20px #ddd
  }

  100% {
    transform:translate(0,-6px);
    box-shadow: 0 2px 20px #97CAFF;
  }
}

</style>