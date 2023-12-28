<template>
  <el-card>
    <h1 class="me-author-name" style="margin-bottom: 10px">CBSE G5</h1>
    <div class="me-author-description">
      <span><i class="el-icon-location-outline"></i> &nbsp;UM &nbsp;&nbsp;</span>
      <span><i class="me-icon-job"></i> &nbsp;Vue + SpringBoot</span>
    </div>
    <div class="me-author-tool">
      <i @click="showTool(um)" :title="um.title" class="el-icon-location"></i>
      <i @click="showTool(cbse)" :title="cbse.title" class="el-icon-info"></i>
      <i @click="showTool(github)" :title="github.title" class="me-icon-github"></i>
    </div>
  </el-card>

</template>

<script>
  export default {
    name: 'CardMe',
    data() {
      return {
        um: {title: 'UM', message: 'University Malaya'},
        cbse: {title: 'CBSE', message: 'Component-Based Software Engineering'},
        github: {
          title: 'github',
          message: '<a target="_blank" href="https://github.com/Ryanangry07/cbse-blog">https://github.com/Ryanangry07/cbse-blog</a>'
        }
      }
    },
    methods: {
      showTool(tool) {
        this.$message({
          duration: 3000,
          showClose: true,
          dangerouslyUseHTMLString: true,
          message: '<strong>' + tool.message + '</strong>'
        });
      }
    }
  }
</script>

<style scoped>
  .me-author-name {
    text-align: center;
    font-size: 30px;
    border-bottom: 1px solid #5FB878;
  }

  .me-author-description {
    padding: 8px 0;
  }

  .me-icon-job {
    padding-left: 16px;
  }

  .me-author-tool {
    text-align: center;
    padding-top: 10px;
  }

  .me-author-tool i {
    cursor: pointer;
    padding: 4px 10px;
    font-size: 30px;
  }
</style>
