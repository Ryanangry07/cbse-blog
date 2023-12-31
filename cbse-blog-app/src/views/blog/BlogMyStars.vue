<template>
  <div v-title data-title="My Stars - Article Archives - For Fun">
    <el-container>
      <el-main class="me-articles">
        <div class="me-month-title">My Stars</div>
        <article-scroll-page v-bind="article"></article-scroll-page>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  import ArticleScrollPage from '@/views/common/ArticleScrollPage'
  export default {
    name: "BlogMyStars",
    components: {
      ArticleScrollPage
    },
    data() {
      return {
        article: {
          query: {
            starUid: this.$store.state.id
          }
        }
      }
    }
  }
</script>

<style scoped>


  .me-month-title {
    margin-left: 4px;
    margin-bottom: 12px;
  }
  .me-articles{
    max-width: 854px;
  }
</style>
