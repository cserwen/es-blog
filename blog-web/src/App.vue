<template>
  <div id="app">
    <el-container direction="horizontal">
      <transition name="fade">
        <el-aside id="aside" v-show="isShow">
          这是一行字！
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
  color: #2c3e50;
}

@keyframes show {
  0% {
    left: -300px;
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
    left: 305px;
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

#aside {
  background-color: #0396FF;
  left: 0;
  top: 0;
  position: fixed;
  height: 100vh;
  box-shadow: 1px 0 0 0 gray;
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
  left: 305px;
}

#btn-show {
  position: fixed;
  top: 5px;
  left: 5px;
  z-index: 1;
}
</style>
