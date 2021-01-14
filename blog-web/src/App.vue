<template>
  <div id="app">
    <el-container direction="horizontal">
      <transition name="fade">
        <el-aside id="aside" v-show="isShow" width="220px">
          <el-button @click="this.$router.push({path: '/'})" class="menu" icon="el-icon-home">首页</el-button><br/>
          <el-button @click="this.$router.push({path: '/archives'})" class="menu" icon="el-icon-archive">归档</el-button><br/>
          <el-button @click="this.$router.push({path: '/categories'})" class="menu" icon="el-icon-category">分类</el-button><br/>
          <el-button @click="this.$router.push({path: '/about'})" class="menu" icon="el-icon-about">关于</el-button><br/>
          <el-button @click="this.$router.push({path: '/links'})" class="menu" icon="el-icon-link">友链</el-button><br/>
        </el-aside>
      </transition>

      <el-container id="right">
        <el-main id="main">
          <transition name="btn">
            <el-button @click="collapseStatus" id="btn-hide"
                       icon="custom-icon el-icon-s-fold" circle
                       v-show="isShow"></el-button>
          </transition>
          <el-button @click="collapseStatus" id="btn-show"
                     icon="custom-icon el-icon-s-unfold" circle
                     v-show="!isShow"></el-button>
          <router-view/>
        </el-main>
        <el-footer id="footer">Footer</el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<script>

export default {
  name: 'App',
  data() {
    return {
      isShow: true
    }
  },
  methods: {
    collapseStatus() {
      this.isShow = !this.isShow
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  /*color: #2c3e50;*/
}

@keyframes show {
  0% {
    left: -220px;
  }

  100% {
    opacity: 1;
  }
}

@keyframes btn_enter {
  0% {
    opacity: 0;
    left: 5px;
  }

  1% {
    opacity: 1;
    left: 5px;
  }

  100% {
    left: 225px;
  }
}

.fade-enter-active {
  animation: show 200ms;
}

.fade-leave-active {
  animation: show 200ms reverse;
}

.btn-enter-active {
  animation: btn_enter 200ms;
}

.btn-leave-active {
  animation: btn_enter 200ms reverse;
}

#right {
  margin: 0;
  padding: 0;
  min-height: 100vh;
}

aside {
  background-color: #3C8CE7; /* 浏览器不支持时显示 */
  background-image: linear-gradient(to bottom right, #C2FFD8, #465EFB);
  left: 0;
  top: 0;
  position: fixed;
  height: 100vh;
  box-shadow: 5px 3px 15px #4A4A4A;
  z-index: 2;
}

#main {
  height: 200vh;
  padding: 0;
  margin: 0;
  z-index: 0;
}

#footer {
  padding: 0;
  margin: 0;
}

.custom-icon {
  font-size: 18px;
}

#btn-hide {
  position: fixed;
  top: 5px;
  left: 225px;
}

#btn-show {
  position: fixed;
  top: 5px;
  left: 5px;
  z-index: 1;
}

#aside .el-button {
  margin: 5px;
  padding: 5px 20px;
  background: none;
  color: black;
  border: none;
}

</style>
