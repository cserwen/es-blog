<template >
  <div style="min-height: 100vh" v-loading="loading">
    {{ article.title }}
    <div v-html="blog" id="blog"></div>
    <p></p>
  </div>
</template>

<script>
import marked from 'marked'

export default {
  name: "Article",
  data() {
    return {
      article: {},
      loading: false,
      blog: ""
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
          this.blog = marked(this.article.content)
        }else {
          alert(res.data.data)
        }
        this.loading = false
      }).catch(error => {
        console.log(error);
        alert('服务器异常');
        this.loading = false
      })
    },
    hideAside() {
      this.$emit("handleHide")
    }
  },
  mounted() {
    this.hideAside()
    setTimeout(this.getArticleDetails, 200);
  }
}
</script>

<style scoped>
#blog {
  text-align: left;
}
</style>