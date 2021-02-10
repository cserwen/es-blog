<template>
  <div id="link">
    <div v-for="link in links">
      <div :class="isPhone ? 'p_link-tab' : 'link-tab'">
        <div class="top" style="display: flex">
          <img class="avatar" :src="link.avatarUrl"/>
          <div class="description">{{ link.description }}</div>
        </div>
	      <div class="bottom" style="display: flex">
          <div style="width: 40%; position: fixed; top: 37%;"><el-link class="urlLink" :href="link.url" :underline="false">{{ link.site }}</el-link></div>
          <div class="myDes">{{ link.motto }}</div>
        </div>
      </div>
      <div style="height: 20px"/>
    </div>
  </div>
</template>

<script>
export default {
  name: "Link",
  data() {
    return {
      links: [],
      isPhone: false
    }
  },
  methods: {
    getLinkList() {
      this.axios.get('/link/list')
          .then(res => {
            this.links = res.data.data;
      }).catch(error => {

      })
    }
  },
  mounted() {
    this.getLinkList()
    this.isPhone = document.documentElement.clientWidth < 1200;
    document.title = "cserwen - 友情链接"
  }
}
</script>

<style scoped>
#link{
  margin: 50px 10px;
  height: 100%;
  text-align: center;
}

*{
  margin: 0;
  padding: 0;
}
.link-tab{
  height: 100px;
  margin: 0 35%;
  perspective: 2000px;
}

.p_link-tab {
  height: 100px;
  margin: 0 10px;
  perspective: 2000px;
}
.top{
  border-radius: 15px;
  padding-left: 30px;
  height: 100%;
  box-shadow: 0 0 20px #ddd;
  transition: all linear 0.5s;
  backface-visibility: hidden;
  text-align: center;
}
.bottom{
  border-radius:15px;
  padding-left: 30px;
  height: 100%;
  box-shadow: 0 0 20px #ddd;
  transition: all linear 0.5s;
  backface-visibility: hidden;
  text-align: center;
  transform: rotateY(180deg);
  position: relative;
  top:-100%;
  left: 0;
}
.link-tab:hover .top{
  transform: rotateY(-180deg);
}
.link-tab:hover .bottom{
  transform: rotateY(0deg);
}

.p_link-tab:hover .top{
  transform: rotateY(-180deg);
}
.p_link-tab:hover .bottom{
  transform: rotateY(0deg);
}

.avatar {
  border-radius: 50%;
  width: 70px;
  height: 70px;
  margin-top: 15px;
}

.description {
  text-align: center;
  position: fixed;
  top: 35%;
  color: #3C8CE7;
  font-size: 20px;
  margin-left: 90px;
}

</style>