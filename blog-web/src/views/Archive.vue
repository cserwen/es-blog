<template>
  <div :id="isPhone ? 'p_archive' : 'archive'" v-loading="loading">
    <el-timeline>
      <el-timeline-item v-for="archive in archives"
                        :timestamp="new Date(archive.createTime).toISOString().replace(/T/g,' ')
                        .replace(/\.[\d]{3}Z/,'') " placement="top" >
        <el-card >
          <el-button v-show="!isPhone" id="title" @click="$router.push({name: 'Article', params: {id: archive.id}})" type="text">{{ archive.title }}</el-button>
          <div v-show="isPhone" id="p_title" @click="$router.push({name: 'Article', params: {id: archive.id}})" type="text">{{ archive.title }}</div>
          <p id="description">{{ archive.description }}</p>
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>
  <el-pagination v-show="isShow" @current-change="getArchiveList" background layout="prev, pager, next" :page-count="pages" id = "page">
  </el-pagination>
</template>

<script>

import {ElMessage} from "element-plus";

export default {
  name: "Archive",
  data() {
    return {
      pages: 0,
      archives: [],
      loading: false,
      isShow: false,
      isPhone: false
    }
  },
  methods: {
    getArchiveList(current) {
      window.scrollTo(0,0);
      this.loading = true
      this.axios.get('/archive/list', {
        params: {
          next: current,
          size: 5
        }
      }).then(res => {
        console.log(res)
        if (res.data.code === 0){
          this.archives = res.data.data.archiveViews
          this.pages = res.data.data.pages;
          console.log(res.data.data.pages)
          this.loading = false
          this.isShow = true
        }else {
          ElMessage.warning({
            showClose: true,
            message: res.data.data
          })
        }
        this.loading = false
      }).catch(error => {
        ElMessage.error({
          showClose: true,
          message: "服务器异常"
        })
        this.loading = false
        this.isShow = false
      })
    }
  },
  created() {
    this.isPhone = document.documentElement.clientWidth < 1200;
    this.getArchiveList(1)
    document.title = "cserwen - 归档"
  }
}
</script>

<style scoped>
#archive {
  margin: 50px 28% 0;
  text-align: left;
  min-height: 80vh;
}

#p_archive {
  text-align: left;
  padding-right: 40px;
  padding-top: 50px;
  padding-bottom: 10px;
  min-height: 100vh;
}


#page {
  margin-bottom: 20px;
}

#title {
  color: #666666;
  font-size: 18px;
  padding-top: 5px;
  padding-bottom: 20px;
}

#p_title {
  color: #666666;
  font-size: 15px;
  padding-top: 0;
  padding-bottom: 15px;
}

#description {
  padding: 0;
  margin: 0;
  color: #999999;
}
</style>