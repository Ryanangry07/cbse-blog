<template>
  <scroll-page :loading="loading" :offset="offset" :no-data="noData" v-on:load="load">
    <div>

      <el-select v-model="categoryId" filterable placeholder="Category" style="width: 15%; float: left">
        <el-option
          v-for="item in categorys"
          :key="item.id"
          :label="item.categoryname"
          :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="tagId" filterable placeholder="Tag" style="margin-left: 3px; width: 15%; float: left">
        <el-option
          v-for="item in tags"
          :key="item.id"
          :label="item.tagname"
          :value="item.id">
        </el-option>
      </el-select>
      <el-input v-model="keyword" placeholder="Please enter to search" suffix-icon="el-icon-search"
                style="margin-left: 3px; width: 40%; margin-bottom: 10px" @keyup.enter.native="searchArticles"></el-input>
      <!--      @keyup.enter.native="loadList" -->

      <el-button size="medium" type="primary" style="" @click="searchArticles">Search</el-button>
      <el-button size="medium" type="primary" @click="resetQuery">Reset</el-button>
    </div>
    <article-item v-for="a in articles" :key="a.id" v-bind="a"></article-item>
  </scroll-page>
</template>

<script>
  import ArticleItem from '@/components/article/ArticleItem'
  import ScrollPage from '@/components/scrollpage'
  import {getArticles} from '@/api/article'
  import {getAllCategorys} from '@/api/category'
  import {getAllTags} from '@/api/tag'

  export default {
    name: "ArticleScrollPage",
    props: {
      offset: {
        type: Number,
        default: 100
      },
      page: {
        type: Object,
        default() {
          return {}
        }
      },
      query: {
        type: Object,
        default() {
          return {}
        }
      }
    },
    watch: {
      'query': {
        handler() {
          this.noData = false
          this.articles = []
          this.innerPage.pageNumber = 1
          this.getArticles()
        },
        deep: true
      },
      'page': {
        handler() {
          this.noData = false
          this.articles = []
          this.innerPage = this.page
          this.getArticles()
        },
        deep: true
      }
    },
    created() {
      this.getArticles()
      this.getCategorysAndTags()
    },
    data() {
      return {
        loading: false,
        noData: false,
        keyword: '',
        categoryId: '',
        tagId: '',
        innerPage: {
          pageSize: 5,
          pageNumber: 1,
          name: 'a.createDate',
          sort: 'desc'
        },
        articles: [],
        categorys: [],
        tags: []
      }
    },
    methods: {
      load() {
        this.getArticles()
      },
      view(id) {
        this.$router.push({path: `/view/${id}`})
      },
      getArticles() {
        let that = this
        that.loading = true

        getArticles(that.query, that.innerPage).then(data => {

          let newArticles = data.data
          if (newArticles && newArticles.length > 0) {
            that.innerPage.pageNumber += 1
            that.articles = that.articles.concat(newArticles)
          } else {
            that.noData = true
          }

        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '文章加载失败!', showClose: true})
          }
        }).finally(() => {
          that.loading = false
        })

      },
      searchArticles() {
        let that = this
        that.loading = true
        that.innerPage.pageNumber = 1
        that.query.keyword = that.keyword
        that.query.categoryId = that.categoryId
        that.query.tagId = that.tagId

        getArticles(that.query, that.innerPage).then(data => {

          let newArticles = data.data
          if (newArticles && newArticles.length > 0) {
            that.innerPage.pageNumber += 1
            // that.articles = that.articles.concat(newArticles)
            that.articles = newArticles
          } else {
            this.articles = []
            that.noData = true
          }

        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: 'Articles load failed!', showClose: true})
          }
        }).finally(() => {
          that.loading = false
        })
      },
      getCategorysAndTags() {
        let that = this
        getAllCategorys().then(data => {
          that.categorys = data.data
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '文章分类加载失败', showClose: true})
          }
        })

        getAllTags().then(data => {
          that.tags = data.data
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '标签加载失败', showClose: true})
          }
        })

      },
      resetQuery(){
        this.categoryId = '';
        this.tagId = '';
        this.keyword = '';
      }
    },
    components: {
      'article-item': ArticleItem,
      'scroll-page': ScrollPage
    }

  }
</script>

<style scoped>
  .el-card {
    border-radius: 0;
  }

  .el-card:not(:first-child) {
    margin-top: 10px;

  }
</style>
