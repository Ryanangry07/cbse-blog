<template>
  <div v-title data-title="ForFun Find Yourself">



<!--    <div>
      <el-input v-model="name" placeholder="Please enter to search" suffix-icon="el-icon-search"
                style="width: 100%; margin-bottom: 30px" ></el-input>
      &lt;!&ndash;      @keyup.enter.native="loadList" &ndash;&gt;
    </div>-->
    <el-container>


      <el-main class="me-articles">


        <article-scroll-page></article-scroll-page>

      </el-main>

      <el-aside>

        <card-me class="me-area"></card-me>
        <card-tag :tags="hotTags"></card-tag>

        <card-article cardHeader="The Hottest Articles" :articles="hotArticles"></card-article>

        <card-archive cardHeader="Article Retrieve" :archives="archives"></card-archive>

        <card-article cardHeader="The Latest Articles" :articles="newArticles"></card-article>

      </el-aside>

    </el-container>
  </div>
</template>

<script>
  import CardMe from '@/components/card/CardMe'
  import CardArticle from '@/components/card/CardArticle'
  import CardArchive from '@/components/card/CardArchive'
  import CardTag from '@/components/card/CardTag'
  import ArticleScrollPage from '@/views/common/ArticleScrollPage'

  import {getArticles, getHotArtices, getNewArtices} from '@/api/article'
  import {getHotTags} from '@/api/tag'
  import {listArchives} from '@/api/article'

  export default {
    name: 'Index',
    created() {
      this.getHotArtices()
      this.getNewArtices()
      this.getHotTags()
      this.listArchives()
    },
    data() {
      return {
        hotTags: [],
        hotArticles: [],
        newArticles: [],
        archives: []
      }
    },
    methods: {
      getHotArtices() {
        let that = this
        getHotArtices().then(data => {
          that.hotArticles = data.data
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: 'The hottest articles load failed!', showClose: true})
          }

        })

      },
      getNewArtices() {
        let that = this
        getNewArtices().then(data => {
          that.newArticles = data.data
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: 'The latest articles load failed!', showClose: true})
          }

        })

      },
      getHotTags() {
        let that = this
        getHotTags().then(data => {
          that.hotTags = data.data
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: 'The hottest tags load failed!', showClose: true})
          }

        })
      },
      listArchives() {
        listArchives().then((data => {
          this.archives = data.data
        })).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: 'The article retrieve load failed!', showClose: true})
          }
        })
      }

    },
    components: {
      'card-me': CardMe,
      'card-article': CardArticle,
      'card-tag': CardTag,
      ArticleScrollPage,
      CardArchive
    }
  }
</script>

<style scoped>

  .el-container {
    width: 960px;
  }

  .el-aside {
    margin-left: 20px;
    width: 260px;
  }

  .el-main {
    padding: 0px;
    line-height: 16px;
  }

  .el-card {
    border-radius: 0;
  }

  .el-card:not(:first-child) {
    margin-top: 20px;
  }
</style>
