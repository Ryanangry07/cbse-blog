<template>

  <div class="me-message" v-title :data-title="title">
    <el-container class="">
      <el-main class="me-main">
        <el-alert
          title="Message board function is not opening for now。。。。。。。。。。。"
          type="warning"
          center
          show-icon>
        </el-alert>

      </el-main>
    </el-container>
  </div>
</template>

<script>
  export default {
    name: 'MessageBoard',
    data() {
      return {
      }
    },
    computed: {
      title (){
        return '留言板 - For Fun'
      }
    }
  }
</script>

<style scoped>
  .me-message {
  }

  .el-container {
    width: 700px;
  }

  .me-main {
    overflow: hidden;
  }

  .me-log-box {
    margin-left: 30%;
    margin-top: 20px;

  }

</style>
