<template>
  <div id="app">
    <el-container direction="vertical">
      <transition name="fade">
        <el-aside id="aside" v-show="isShow" width="220px">
          <img id="avatar" src="https://portrait.gitee.com/uploads/avatars/user/1886/5660923_litterboys_1597989320.png!avatar200"/><br/>
          <el-button @click="changeView('/')" class="menu" icon="el-icon-my-home">首页</el-button><br/>
          <el-button @click="changeView('/archives')" class="menu" icon="el-icon-my-archive">归档</el-button><br/>
          <el-button @click="changeView('/categories')" class="menu" icon="el-icon-my-category">分类</el-button><br/>
          <el-button @click="changeView('/about')" class="menu" icon="el-icon-my-about">关于</el-button><br/>
          <el-button @click="changeView('/about')" class="menu" icon="el-icon-my-link">友链</el-button><br/>
        </el-aside>
      </transition>

      <div id="button-show">
        <transition name="btn">
          <el-button @click="collapseStatus" id="btn-hide"
                     icon="custom-icon el-icon-s-fold" circle
                     v-show="isShow"></el-button>
        </transition>
        <el-button @click="collapseStatus" id="btn-show"
                   icon="custom-icon el-icon-s-unfold" circle
                   v-show="!isShow"></el-button>
      </div>

      <div id="view">
        <router-view v-on:handleHide="hideAside"/>
      </div>
      <el-footer id="footer">
        <el-link type="info" class="foot-info" :underline="false">{{ this.copyRight }}</el-link>
        <el-link href="https://beian.miit.gov.cn/" type="info" class="foot-info" :underline="false">冀ICP备20003509号</el-link>
      </el-footer>
    </el-container>
  </div>
</template>

<script>

export default {
  name: 'App',
  data() {
    return {
      isShow: true,
      copyRight: ''
    }
  },
  methods: {
    collapseStatus() {
      this.isShow = !this.isShow
    },
    hideAside() {
      this.isShow = false
    },
    changeView(path) {
      this.$router.push({path: path})
      window.scrollTo(0,0);
    }
  },
  mounted() {
    let year = new Date().getFullYear();
    this.copyRight = 'Copyrights © 2019-' + year + ' litterboys'
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
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
    left: 230px;
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

aside {
  background-color: #3C8CE7;
  background-image: linear-gradient(to bottom right, #C2FFD8, #465EFB);
  left: 0;
  top: 0;
  position: fixed;
  height: 100%;
  box-shadow: 5px 3px 10px #888888;
  z-index: 2;
  border-radius: 7px;
}

#button-show .el-button {
  font-size: 18px;
  padding: 6px;
  margin: 0;
}

#btn-hide {
  position: fixed;
  top: 5px;
  left: 230px;
  box-shadow:0 0 7px #4A4A4A;
  z-index: 999;
}

#btn-show {
  position: fixed;
  top: 5px;
  left: 5px;
  z-index: 1;
  box-shadow:0 0 7px #4A4A4A;
}

#aside .el-button {
  margin: 3px;
  padding: 10px;
  background: none;
  border: none;
  color: black;
  width: 100px;
  font-size: 15px;
  border-radius: 8px;
  transition: box-shadow 300ms;
}

.menu:hover {
  box-shadow: 5px 5px 20px #999999;
}

#view {
  width: 100%;
  min-height: calc(100vh - 60px); /*减去footer的高度*/;
}

#avatar {
  border-radius: 50%;
  width: 70px;
  height: 70px;
  margin-top: 20px;
  margin-left: 10px;
}

.foot-info {
  margin: 0 5px;
}
</style>
