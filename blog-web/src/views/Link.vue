<template>
  <div id="link">
    <div v-for="link in links">
      <div class="link-tab">
        <div class="top">
          <div class="avater"> dd</div>
          <div class="description">dddw</div>
        </div>
	      <div class="bottom">
          <div class="urlLink">dddd</div>
          <div class="myDes">dd</div>
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
      links: ['a', 'b', 'c','a', 'b', 'c']
    }
  }
}
</script>

<style scoped>
#link{
  margin: 50px 10px;
  width: 100%;
  height: 100%;
}

*{
  margin: 0;
  padding: 0;
}
.link-tab{
  width: 500px;
  height: 100px;
  margin: auto;
  perspective: 2000px;
}
.top{
  width: 100%;
  height: 100%;
  box-shadow: 0 0 20px #ddd;
  transition: all linear 0.5s;
  backface-visibility: hidden;
  text-align: center;
}
.bottom{
  width: 100%;
  height: 100%;
  box-shadow: 0 0 20px #ddd;
  transition: all linear 0.5s;
  backface-visibility: hidden;
  text-align: center;
  transform: rotateY(180deg);
  position: relative;
  top:-100%;
  left: 0px;
}
.link-tab:hover .top{
  transform: rotateY(-180deg);
}
.link-tab:hover .bottom{
  transform: rotateY(0deg);
}
</style>