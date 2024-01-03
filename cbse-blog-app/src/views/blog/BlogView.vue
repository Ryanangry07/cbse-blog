<template>
  <div class="me-view-body" v-title :data-title="title">
    <el-container class="me-view-container">
      <!--<el-aside class="me-area">-->
        <!--<ul class="me-operation-list">-->
          <!--<li class="me-operation-item">-->
            <!--<el-button type="primary" icon="el-icon-edit"></el-button>-->
          <!--</li>-->
        <!--</ul>-->
      <!--</el-aside>-->
      <el-main>

        <div class="me-view-card">
          <h1 class="me-view-title">{{article.title}}</h1>

          <div class="me-view-author">
            <a>
              <img v-popover:popoverA class="me-view-picture" :src="article.author.avatar"></img>
            </a>
            <el-popover
              ref="popoverA"
              placement="bottom"
              title="About Me"
              width="200"
              trigger="click">
                <template v-if="article.author.aboutMeVisible">
                  <!-- If aboutMeVisible is true, show user's aboutMe content -->
                  <div>{{ article.author.aboutMe }}</div>
                </template>
                <template v-else>
                  <!-- If aboutMeVisible is false, show default content -->
                  <div>This user didn't show anything.</div>
                </template>
            </el-popover>


            <div class="me-view-info">
              <span>{{article.author.nickname}} </span>
              <div class="me-view-meta">
                <span>{{article.createDate | format}}</span>
                <span> | views {{article.viewCounts}} </span>
                <span> | stars {{article.starCounts}} </span>
                <span> | comments {{article.commentCounts}} </span>
              </div>
            </div>
            <el-button
              v-if="this.article.author.id == this.$store.state.id"
              @click="editArticle()"
              style="position: absolute;left: 55%;"
              size="mini"
              round
              icon="el-icon-edit">Edit</el-button>
            <!--              v-if="this.article.author.id == this.$store.state.id"-->
            <el-button
              v-if="this.article.author.id == this.$store.state.id"
              @click="deleteArticle()"
              style="position: absolute;left: 60%;"
              size="mini"
              round
              icon="el-icon-delete">Delete</el-button>


            <span class="me-pull-right me-article-count" style="margin-right: 30px">
                  <i :class="article.starStatus" style="cursor: pointer" @click="starArticle"></i>&nbsp;{{article.starCounts}}
            </span>
          </div>
          <div class="me-view-content">
            <markdown-editor style="box-shadow: none" :editor=article.editor></markdown-editor>
          </div>

          <div class="me-view-end">
            <el-alert
              title="Article End..."
              type="success"
              center
              :closable="false">
            </el-alert>
          </div>

          <div class="me-view-tag">
            Tag:
            <!--<el-tag v-for="t in article.tags" :key="t.id" class="me-view-tag-item" size="mini" type="success">{{t.tagname}}</el-tag>-->
            <el-button @click="tagOrCategory('tag', t.id)" size="mini" type="primary" v-for="t in article.tags" :key="t.id" round plain>{{t.tagname}}</el-button>
          </div>

          <div class="me-view-tag">
            Category:
            <!--<span style="font-weight: 600">{{article.category.categoryname}}</span>-->
            <el-button @click="tagOrCategory('category', article.category.id)" size="mini" type="primary" round plain>{{article.category.categoryname}}</el-button>
          </div>

          <div class="me-view-comment">
            <div class="me-view-comment-write">
              <el-row :gutter="20">
                <el-col :span="2">
                  <a class="">
                    <img v-popover:popoverC class="me-view-picture" :src="avatar"></img>
                  </a>
                  <el-popover
                    ref="popoverC"
                    placement="bottom"
                    title="About Me"
                    width="200"
                    trigger="click">
                    <template v-if="article.author.aboutMeVisible">
                      <!-- If aboutMeVisible is true, show user's aboutMe content -->
                      <div>{{ article.author.aboutMe }}</div>
                    </template>
                    <template v-else>
                      <!-- If aboutMeVisible is false, show default content -->
                      <div>This user didn't show anything.</div>
                    </template>
                  </el-popover>
                </el-col>
                <el-col :span="22">
                  <el-input
                    type="textarea"
                    :autosize="{ minRows: 2}"
                    placeholder="Your comment..."
                    class="me-view-comment-text"
                    v-model="comment.content"
                    resize="none">
                  </el-input>
                </el-col>
              </el-row>

              <el-row :gutter="20">
                <el-col :span="2" :offset="22">
                  <el-button type="text" @click="publishComment()">Send</el-button>
                </el-col>
              </el-row>
            </div>

            <div class="me-view-comment-title">
              <span>{{article.commentCounts}} comments</span>
            </div>

            <commment-item
              v-for="(c,index) in comments"
              :comment="c"
              :articleId="article.id"
              :index="index"
              :rootCommentCounts="comments.length"
              @commentCountsIncrement="commentCountsIncrement"
              :key="c.id">
            </commment-item>

          </div>

        </div>
      </el-main>

    </el-container>
  </div>
</template>

<script>
  import MarkdownEditor from '@/components/markdown/MarkdownEditor'
  import CommmentItem from '@/components/comment/CommentItem'
  import {viewArticle, starArticle, loadStar, deleteArticleById} from '@/api/article'
  import {getCommentsByArticle, publishComment} from '@/api/comment'

  import default_avatar from '@/assets/img/default_avatar.png'
  import eventBus from "@/utils/eventBus";

  export default {
    name: 'BlogView',
    created() {
      this.getArticle()
      this.loadStar()
    },
    watch: {
      '$route': 'getArticle'
    },
    data() {
      return {
        article: {
          id: '',
          title: '',
          commentCounts: 0,
          viewCounts: 0,
          starCounts: 0,
          starStatus: 'el-icon-star-off',
          summary: '',
          author: {},
          tags: [],
          category:{},
          createDate: '',
          editor: {
            value: '',
            toolbarsFlag: false,
            subfield: false,
            defaultOpen: 'preview'
          }
        },
        comments: [],
        comment: {
          article: {},
          content: ''
        },
        author: {
          aboutMeVisible: '',
          aboutMe: ''
        }
      }
    },
    computed: {
      avatar() {
        let avatar = this.$store.state.avatar

        if (avatar.length > 0) {
          return avatar
        }
        return default_avatar
      },
      title() {
        return `${this.article.title} - 文章 - For Fun`
      }
    },
    methods: {
      tagOrCategory(type, id) {
        this.$router.push({path: `/${type}/${id}`})
      },
      editArticle() {
        this.$router.push({path: `/write/${this.article.id}`})
      },
      deleteArticle() {
        this.$confirm('System will delete this article, continue?', 'Tips', {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }).then(() => {
          let that = this
          deleteArticleById(that.article.id)
          that.$message({
            type: 'success',
            message: 'Delete successfully!'
          });
          that.$router.push({ path: '/' })
            .then(() => {
              // After the navigation is complete, refresh the browser
              window.location.reload();
            })
            .catch(error => {
              console.error('Navigation failed:', error);
            });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: 'Delete canceled'
          });
        });
      },
      getArticle() {
        let that = this
        viewArticle(that.$route.params.id).then(data => {
          Object.assign(that.article, data.data)
          that.article.editor.value = data.data.body.content

          that.getCommentsByArticle()
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '文章加载失败', showClose: true})
          }
        })
      },
      publishComment() {
        let that = this
        if (!that.comment.content) {
          return;
        }
        that.comment.article.id = that.article.id

        publishComment(that.comment).then(data => {
          that.$message({type: 'success', message: '评论成功', showClose: true})
          that.comments.unshift(data.data)
          that.commentCountsIncrement()
          that.comment.content = ''
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '评论失败', showClose: true})
          }
        })
      },
      getCommentsByArticle() {
        let that = this
        getCommentsByArticle(that.article.id).then(data => {
          that.comments = data.data
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '评论加载失败', showClose: true})
          }
        })
      },
      commentCountsIncrement() {
        this.article.commentCounts += 1
      },
      loadStar(){
        console.log('load current star')
        //load current star
        let that = this
        let userId = this.$store.state.id
        loadStar(that.$route.params.id, userId).then(data => {
          that.article.starStatus = data.data.starStatus
          that.article.starCounts = data.data.starCounts
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: 'star failed', showClose: true})
          }
        })
      },
      starArticle(){
        //star()
        //this.starStatus =
        let that = this
        let userId = this.$store.state.id
        starArticle(that.article.id, userId).then(data => {
          //that.starStatus = data.data.starStatus
          console.log(data.data.starStatus)
          that.article.starStatus = (that.article.starStatus === 'el-icon-star-off') ? 'el-icon-star-on' : 'el-icon-star-off';
          that.article.starCounts = data.data.starCounts
          // eventBus.$emit("unreadCountsIncrement")
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: 'star failed', showClose: true})
          }
        })
      },
      setStar(starStatus){
        this.starStatus = starStatus
      }
    },
    components: {
      'markdown-editor': MarkdownEditor,
      CommmentItem
    },
    //组件内的守卫 调整body的背景色
    beforeRouteEnter(to, from, next) {
      window.document.body.style.backgroundColor = '#fff';
      next();
    },
    beforeRouteLeave(to, from, next) {
      window.document.body.style.backgroundColor = '#f5f5f5';
      next();
    }
  }
</script>

<style>
  .me-view-body {
    margin: 100px auto 140px;
  }

  .me-view-container {
    width: 700px;
  }

  .el-main {
    overflow: hidden;
  }

  .me-view-title {
    font-size: 34px;
    font-weight: 700;
    line-height: 1.3;
  }

  .me-view-author {
    /*margin: 30px 0;*/
    margin-top: 30px;
    vertical-align: middle;
  }

  .me-view-picture {
    width: 40px;
    height: 40px;
    border: 1px solid #ddd;
    border-radius: 50%;
    vertical-align: middle;
    background-color: #5fb878;
  }

  .me-view-info {
    display: inline-block;
    vertical-align: middle;
    margin-left: 8px;
  }

  .me-view-meta {
    font-size: 12px;
    color: #969696;
  }

  .me-view-end {
    margin-top: 20px;
  }

  .me-view-tag {
    margin-top: 20px;
    padding-left: 6px;
    border-left: 4px solid #c5cac3;
  }

  .me-view-tag-item {
    margin: 0 4px;
  }

  .me-view-comment {
    margin-top: 60px;
  }

  .me-view-comment-title {
    font-weight: 600;
    border-bottom: 1px solid #f0f0f0;
    padding-bottom: 20px;
  }

  .me-view-comment-write {
    margin-top: 20px;
  }

  .me-view-comment-text {
    font-size: 16px;
  }

  .v-show-content {
    padding: 8px 25px 15px 0px !important;
  }

  .v-note-wrapper .v-note-panel {
    box-shadow: none !important;
  }

  .v-note-wrapper .v-note-panel .v-note-show .v-show-content, .v-note-wrapper .v-note-panel .v-note-show .v-show-content-html {
    background: #fff !important;
  }


</style>
