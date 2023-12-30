<template>
  <div class="me-ct-body" v-title :data-title="title">
    <el-container class="me-ct-container">
      <el-main>
        <div class="me-ct-title me-area">
          <template v-if="this.$route.params.type === 'tag'">
            <img class="me-ct-picture" :src="ct.avatar?ct.avatar:defaultAvatar"/>
            <div class="me-one-row">
              <h3 class="me-ct-name">{{ct.tagname}}</h3>
              <el-button v-if="1 == this.$store.state.admin" icon="el-icon-edit" size="mini" @click="editTagDialog" class="edit-button" circle></el-button>
            </div>
          </template>

          <template v-else>
            <img class="me-ct-picture" :src="ct.avatar?ct.avatar:defaultAvatar"/>
            <div class="me-one-row">
              <h3 class="me-ct-name">{{ct.categoryname}}</h3>
              <el-button v-if="1 == this.$store.state.admin" icon="el-icon-edit" size="mini" @click="editCategoryDialog" class="edit-button" circle></el-button>
            </div>
            <p>{{ct.description}}</p>
          </template>

          <span class="me-ct-meta">{{ct.articles}} Articles</span>
        </div>

        <el-dialog
          title="Edit Category"
          :visible.sync="categoryDialogVisible"
          width="30%"
          center
        >
          <el-form
            ref="categoryForm"
            :rules="categoryRules"
            :model="categoryForm"
            label-width="120px"
            style="padding-right: 20px"
          >
            <el-form-item label="Category Name" prop="categoryname">
              <el-input v-model="categoryForm.categoryname"></el-input>
            </el-form-item>
            <el-form-item label="Avatar" prop="avatar">
              <el-upload
                class="avatar-uploader"
                action="http://localhost:8080/upload"
                name="uploadAvatar"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
              >
                <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
            <el-form-item label="Description" prop="description">
                <el-input type="textarea" :rows="5" v-model="categoryForm.description"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="categoryDialogVisible = false">Cancel</el-button>
            <el-button type="primary" @click="editCategoryForm"
              >Confirm</el-button
            >
          </span>
        </el-dialog>

        <el-dialog
          title="Edit Tag"
          :visible.sync="tagDialogVisible"
          width="30%"
          center
        >
          <el-form
            ref="tagForm"
            :rules="tagRules"
            :model="tagForm"
            label-width="120px"
            style="padding-right: 20px"
          >
            <el-form-item label="Tag Name" props="tagname">
              <el-col :span="20">
                <el-input v-model="tagForm.tagname"></el-input>
              </el-col>
            </el-form-item>
            <el-form-item label="Avatar" prop="avatar">
              <el-upload
                class="avatar-uploader"
                action="http://localhost:8080/upload"
                name="uploadAvatar"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
              >
                <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="tagDialogVisible = false">Cancel</el-button>
            <el-button type="primary" @click="editTagForm">Confirm</el-button>
          </span>
        </el-dialog>

        <div class="me-ct-articles">
          <article-scroll-page v-bind="article"></article-scroll-page>
        </div>

      </el-main>
    </el-container>
  </div>
</template>

<script>
  import ArticleScrollPage from '@/views/common/ArticleScrollPage'
  import {getArticlesByCategory, getArticlesByTag} from '@/api/article'
  import {getTagDetail, editTag} from '@/api/tag'
  import {getCategoryDetail, editCategory} from '@/api/category'
  import defaultAvatar from '@/assets/img/logo.png'


  export default {
    name: 'BlogCategoryTag',
    created() {
      this.getCategoryOrTagAndArticles()
    },
    watch: {
      '$route': 'getCategoryOrTagAndArticles'
    },
    data() {
      return {
        defaultAvatar: defaultAvatar,
        ct: {},
        article: {
          query: {
            tagId: '',
            categoryId: ''
          }
        },
        imageUrl: "",
        categoryDialogVisible: false,
        tagDialogVisible: false,
        categoryForm: {
          id: "",
          categoryname: "",
          avatar: "",
          description: "",
        },
        tagForm: {
          id: "",
          tagname: "",
          avatar: "",
        },
        categoryRules: {
          categoryname: [
            {
              required: true,
              message: "Please enter category name",
              trigger: "blur",
            },
          ],
        },
        tagRules: {
          tagname: [
            { required: true, message: "Please enter tag name", trigger: "blur" },
          ],
        },
      }
    },
    computed: {
      title() {
        if(this.$route.params.type === 'tag'){
          return `${this.ct.tagname} - 标签 - For Fun`
        }
        return `${this.ct.categoryname} - 文章分类 - For Fun`
      }
    },
    methods: {
      getCategoryOrTagAndArticles() {
        let id = this.$route.params.id
        let type = this.$route.params.type
        if ('tag' === type) {
          this.getTagDetail(id)
          this.article.query.tagId = id
        } else {
          this.getCategoryDetail(id)
          this.article.query.categoryId = id
        }

      },
      getCategoryDetail(id) {
        let that = this
        getCategoryDetail(id).then(data => {
          that.ct = data.data
          that.categoryForm.categoryname = data.data.categoryname
          that.categoryForm.avatar = data.data.avatar
          that.categoryForm.id = data.data.id
          that.categoryForm.description = data.data.description
          that.imageUrl = data.data.avatar
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '文章分类加载失败', showClose: true})
          }
        })
      },
      getTagDetail(id) {
        let that = this
        getTagDetail(id).then(data => {
          that.ct = data.data
          that.tagForm.tagname = data.data.tagname
          that.tagForm.avatar = data.data.avatar
          that.tagForm.id = data.data.id
          that.imageUrl = data.data.avatar
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '标签加载失败', showClose: true})
          }
        })
      },
      getArticlesByCategory(id) {
        let that = this
        getArticlesByCategory(id).then(data => {
          that.articles = data.data
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '文章加载失败', showClose: true})
          }
        })
      },
      getArticlesByTag(id) {
        let that = this
        getArticlesByTag(id).then(data => {
          that.articles = data.data
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '文章加载失败', showClose: true})
          }
        })
      },
      editCategoryDialog() {
        this.categoryDialogVisible = true;
        //this.resetForm();
        this.$nextTick(() => {
          this.resetCategoryForm();
        });
      },
      editTagDialog() {
        this.tagDialogVisible = true;
        //this.resetForm();
        this.$nextTick(() => {
          this.resetTagForm();
        });
      },
      resetCategoryForm() {
        this.categoryForm.avatar = "";
        this.$refs.categoryForm.resetFields();
      },
      resetTagForm() {
        this.tagForm.avatar = "";
        this.$refs.tagForm.resetFields();
      },
      editCategoryForm() {
        // check form input by using 'rules[]'
        this.$refs.categoryForm.validate((valid) => {
          // if valid, then send request
          if (valid) {
            this.editCategory();
          } else {
            console.log("error submit!!");
            return false;
          }
        });
      },
      editTagForm() {
        // check form input by using 'rules[]'
        this.$refs.tagForm.validate((valid) => {
          // if valid, then send request
          if (valid) {
            this.editTag();
          } else {
            console.log("error submit!!");
            return false;
          }
        });
      },
      editCategory() {
        let that = this;
        if (that.imageUrl == null) {
          that.imageUrl = "/category/lift.jpg"; //default
        }
        editCategory(that.imageUrl, that.categoryForm.categoryname, that.categoryForm.id, that.categoryForm.description)
          .then((res) => {
            console.log(res);
            this.categoryDialogVisible = false;
            this.$message({
              showClose: true,
              message: "Congrats, category updated successfully.",
              type: "success",
            });
            this.getCategoryOrTagAndArticles();
          })
          .catch((error) => {
            if (error !== "error") {
              that.$message({
                type: "error",
                message: "edit category failed",
                showClose: true,
              });
            }
          });
      },
      editTag() {
        let that = this;
        if (that.imageUrl == null) {
          that.imageUrl = "/category/lift.jpg"; //default
        }
        editTag(that.imageUrl, that.tagForm.tagname, that.tagForm.id)
          .then((res) => {
            console.log(res);
            this.tagDialogVisible = false;
            this.$message({
              showClose: true,
              message: "Congrats, tag updated successfully.",
              type: "success",
            });
            this.getCategoryOrTagAndArticles();
          })
          .catch((error) => {
            if (error !== "error") {
              that.$message({
                type: "error",
                message: "edit tag failed",
                showClose: true,
              });
            }
          });
      },
      handleAvatarSuccess(res, file) {
        let that = this;
        if (res.code == 0) {
          //this.imageUrl = URL.createObjectURL(file.raw);
          that.avatar = res.data.url;
          that.imageUrl = res.data.url;
        } else {
          this.$message.error(`image upload failed:${res.message}`);
        }
        console.log(this.imageUrl);
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === "image/jpeg";
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          //this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error("上传头像图片大小不能超过 2MB!");
        }
        //return isJPG && isLt2M;
        return isLt2M;
      },
    },
    components: {
      ArticleScrollPage
    }
  }
</script>

<style>
  .me-ct-body {
    margin: 60px auto 140px;
    min-width: 100%;
  }

  .el-main {
    padding: 0;
  }

  .me-ct-title {
    height: 150px;
    padding-top: 25px; 
    padding-bottom: 40px;
    text-align: center; /* Align text center */
  }

  .me-one-row {
    display: flex;
    justify-content: center;
    margin-bottom: 10px;
  }

  .edit-button {
    border-radius: 50%; /* Make the button circular */
    margin-left: 10px; /* Adjust margin as needed */
  }


  .me-ct-picture {
    width: 60px;
    height: 60px;
  }

  .me-ct-name {
    font-size: 28px;
  }

  .me-ct-meta {
    font-size: 12px;
    color: #969696;
  }

  .me-ct-articles {
    width: 640px;
    margin: 30px auto;
  }

  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409eff;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 90px;
    height: 90px;
    line-height: 90px;
    text-align: center;
  }
  .avatar {
    width: 90px;
    height: 90px;
    display: block;
  }

</style>
