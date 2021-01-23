<template>
  {{  }}
</template>

<script>
export default {
  name: "Article",
  data() {
    return {
      article: {}
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
        }else {
          alert(res.data.data)
        }
        this.loading = false
      }).catch(error => {
        console.log(error);
        alert('服务器异常');
        this.loading = false
      })
    }
  },
  mounted() {
    this.getArticleDetails()
  }
}
</script>

<style scoped>

</style>