<template>
  <div class="me-allct-body" v-title :data-title="categoryTagTitle">
    <el-container class="me-allct-container">
      <el-main>
        <el-tabs v-model="activeName">
          <el-tab-pane label="Category" name="category">
            <el-input
              v-model="categoryKeyword"
              placeholder="Please enter to search"
              suffix-icon="el-icon-search"
              style="margin-right: 7px; width: 60%; margin: 10px 10px 50px 5px"
              @keyup.enter.native="getCategorys"
            ></el-input>
            <!--      @keyup.enter.native="loadList" -->

            <el-button size="medium" type="primary" @click="getCategorys">Search</el-button>
            <el-button v-if="1 == this.$store.state.admin" size="medium" type="primary" icon="el-icon-plus" @click="createCategory" circle>New</el-button>
            <el-button v-if="1 == this.$store.state.admin" size="medium" icon="el-icon-edit" type="primary" @click="openMergeCategory">Merge</el-button>

            <ul class="me-allct-items">
              <li
                v-for="c in categorys"
                @click="view(c.id)"
                :key="c.id"
                class="me-allct-item"
              >
                <div class="me-allct-content">
                  <a class="me-allct-info">
                    <img
                      class="me-allct-img"
                      :src="c.avatar ? c.avatar : defaultAvatar"
                    />
                    <h4 class="me-allct-name">{{ c.categoryname }}</h4>
                    <p class="me-allct-description">{{ c.description }}</p>
                  </a>

                  <div class="me-allct-meta">
                    <span>{{ c.articles }} Articles</span>
                  </div>
                </div>
              </li>
            </ul>
          </el-tab-pane>

          <el-tab-pane label="Tag" name="tag">
            <el-input
              v-model="tagKeyword"
              placeholder="Please enter to search"
              suffix-icon="el-icon-search"
              style="margin-right: 7px; width: 60%; margin: 10px 10px 50px 5px"
              @keyup.enter.native="getTags"
            ></el-input>
            <!--      @keyup.enter.native="loadList" -->
            <el-button size="medium" type="primary" @click="getTags">Search</el-button>
            <el-button size="medium" type="primary" icon="el-icon-plus" @click="createTag" circle>New</el-button>
            <el-button v-if="1 == this.$store.state.admin" size="medium" icon="el-icon-edit" type="primary" @click="openMergeTag">Merge</el-button>

            <el-divider></el-divider>

            <ul class="me-allct-items">
              <li
                v-for="t in tags"
                @click="view(t.id)"
                :key="t.id"
                class="me-allct-item"
              >
                <div class="me-allct-content">
                  <a class="me-allct-info">
                    <img
                      class="me-allct-img"
                      :src="t.avatar ? t.avatar : defaultAvatar"
                    />
                    <h4 class="me-allct-name">{{ t.tagname }}</h4>
                  </a>

                  <div class="me-allct-meta">
                    <span>{{ t.articles }} Articles</span>
                  </div>
                </div>
              </li>
            </ul>
          </el-tab-pane>
        </el-tabs>
      </el-main>

      <el-dialog
        title="Create New Category"
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
            <el-col :span="20">
              <el-input v-model="categoryForm.categoryname"></el-input>
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
          <el-button @click="categoryDialogVisible = false">Cancel</el-button>
          <el-button type="primary" @click="saveCategoryForm"
            >Confirm</el-button
          >
        </span>
      </el-dialog>

      <el-dialog
        title="Create New Tag"
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
          <el-button type="primary" @click="saveTagForm">Confirm</el-button>
        </span>
      </el-dialog>

      <!--  MERGE CATEGORY  -->
      <el-dialog
        title="Merge Categories to One"
        :visible.sync="mergeCategoryDialogVisible"
        width="30%"
        center
      >
        <el-form
          ref="mergeCategoryForm"
          :rules="mergeCategoryRules"
          :model="mergeCategoryForm"
          label-width="120px"
          label-position="top"
          style="padding-right: 20px"
        >
        
          <el-form-item label="Select Category to Merge:" prop="selectedcategories">
            <el-checkbox-group v-model="mergeCategoryForm.selectedcategories" size="mini">
              <el-checkbox v-for="c in categorys" :key="c.id" :label="c.id" border>{{c.categoryname}}</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label="New Category Name" prop="mergedcategoryname">
              <el-input v-model="mergeCategoryForm.mergedcategoryname"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="mergeCategoryDialogVisible = false">Cancel</el-button>
          <el-button type="primary" @click="saveMergeCategoryForm">Confirm</el-button>
        </span>
      </el-dialog>

      <!--  MERGE TAGS -->
      <el-dialog
        title="Merge Tags to One"
        :visible.sync="mergeTagDialogVisible"
        width="30%"
        center
      >
        <el-form
          ref="mergeTagForm"
          :rules="mergeTagRules"
          :model="mergeTagForm"
          label-width="120px"
          label-position="top"
          style="padding-right: 20px"
        >
        
          <el-form-item label="Select Tag to Merge:" prop="selectedtags">
            <el-checkbox-group v-model="mergeTagForm.selectedtags" size="mini">
              <el-checkbox v-for="t in tags" :key="t.id" :label="t.id" border>{{t.tagname}}</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label="New Tag Name" prop="mergedtagname">
              <el-input v-model="mergeTagForm.mergedtagname"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="mergeTagDialogVisible = false">Cancel</el-button>
          <el-button type="primary" @click="saveMergeTagForm">Confirm</el-button>
        </span>
      </el-dialog>

    </el-container>
  </div>
</template>

<script>
import defaultAvatar from "@/assets/img/logo.png";
import {
  getAllCategorysDetail,
  saveCategory,
  mergeCategory,
  searchCategorys,
} from "@/api/category";
import { getAllTagsDetail, saveTag, mergeTag, searchTags } from "@/api/tag";
// import {saveCategory, searchCategorys} from "../../api/category";

export default {
  name: "BlogAllCategoryTag",
  data() {
    return {
      imageUrl: "",
      defaultAvatar: defaultAvatar,
      categorys: [],
      tags: [],
      categoryKeyword: "",
      tagKeyword: "",
      categoryDialogVisible: false,
      tagDialogVisible: false,
      mergeCategoryDialogVisible: false,
      mergeTagDialogVisible: false,
      mergeCategoryForm: {
        id: "",
        selectedcategories: [], 
        mergedcategoryname: "", 
      },
      mergeTagForm: {
        id: "",
        selectedtags: [], 
        mergedtagname: "", 
      },
      categoryForm: {
        id: "",
        categoryname: "",
        avatar: "",
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
      mergeCategoryRules: {
        selectedcategories: [
          { required: true, message: "Please select categories to merge", trigger: "blur" },
        ],
        mergedcategoryname: [
          { required: true, message: "Please enter a new category name to assign to", trigger: "blur" },
        ],
      },
      mergeTagRules: {
        selectedtags: [
          { required: true, message: "Please select tags to merge", trigger: "blur" },
        ],
        mergedtagname: [
          { required: true, message: "Please enter a new tag name to assign to", trigger: "blur" },
        ],
      },
      currentActiveName: "category",
    };
  },
  created() {
    this.getCategorys();
    this.getTags();
  },
  computed: {
    activeName: {
      get() {
        return (this.currentActiveName = this.$route.params.type);
      },
      set(newValue) {
        this.currentActiveName = newValue;
      },
    },
    categoryTagTitle() {
      if (this.currentActiveName == "category") {
        return "文章分类 - For Fun";
      }
      console.info("dddd");
      return "标签 - For Fun";
    },
  },
  methods: {
    view(id) {
      this.$router.push({ path: `/${this.currentActiveName}/${id}` });
    },
    getCategorys() {
      let that = this;
      getAllCategorysDetail(that.categoryKeyword)
        .then((data) => {
          that.categorys = data.data;
        })
        .catch((error) => {
          if (error !== "error") {
            that.$message({
              type: "error",
              message: "文章分类加载失败",
              showClose: true,
            });
          }
        });
    },
    getTags() {
      let that = this;
      getAllTagsDetail(that.tagKeyword)
        .then((data) => {
          that.tags = data.data;
        })
        .catch((error) => {
          if (error !== "error") {
            that.$message({
              type: "error",
              message: "标签加载失败",
              showClose: true,
            });
          }
        });
    },
    saveCategoryForm() {
      // check form input by using 'rules[]'
      this.$refs.categoryForm.validate((valid) => {
        // if valid, then send request
        if (valid) {
          this.saveCategory();
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    saveTagForm() {
      // check form input by using 'rules[]'
      this.$refs.tagForm.validate((valid) => {
        // if valid, then send request
        if (valid) {
          this.saveTag();
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    saveMergeCategoryForm(){
      // check form input by using 'rules[]'
      this.$refs.mergeCategoryForm.validate((valid) => {
        // if valid, then send request
        if (valid) {
          this.mergeCategory();
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    saveMergeTagForm(){
      // check form input by using 'rules[]'
      this.$refs.mergeTagForm.validate((valid) => {
        // if valid, then send request
        if (valid) {
          this.mergeTag();
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    saveCategory() {
      let that = this;
      if (that.imageUrl == null) {
        that.imageUrl = "/category/lift.jpg"; //default
      }
      saveCategory(that.imageUrl, that.categoryForm.categoryname)
        .then((res) => {
          console.log(res);
          this.categoryDialogVisible = false;
          this.$message({
            showClose: true,
            message: "Congrats, create category successfully.",
            type: "success",
          });
          this.getCategorys();
        })
        .catch((error) => {
          if (error !== "error") {
            that.$message({
              type: "error",
              message: "create category failed",
              showClose: true,
            });
          }
        });
    },
    saveTag() {
      let that = this;
      if (that.imageUrl == null) {
        that.imageUrl = "/category/lift.jpg"; //default
      }
      saveTag(that.imageUrl, that.tagForm.tagname)
        .then((res) => {
          console.log(res);
          this.tagDialogVisible = false;
          this.$message({
            showClose: true,
            message: "Congrats, create tag successfully.",
            type: "success",
          });
          this.getTags();
        })
        .catch((error) => {
          if (error !== "error") {
            that.$message({
              type: "error",
              message: "create tag failed",
              showClose: true,
            });
          }
        });
    },
    mergeCategory() {
      let that = this;
      mergeCategory(that.mergeCategoryForm.selectedcategories, that.mergeCategoryForm.mergedcategoryname)//from api
        .then((res) => {
          console.log(res);
          this.mergeCategoryDialogVisible = false;
          this.$message({
            showClose: true,
            message: "Congrats, merged category successfully.",
            type: "success",
          });
          this.getCategorys();
        })
        .catch((error) => {
          if (error !== "error") {
            that.$message({
              type: "error",
              message: "merge category failed",
              showClose: true,
            });
          }
        });
    },
    mergeTag() {
      let that = this;
      mergeTag(that.mergeTagForm.selectedtags, that.mergeTagForm.mergedtagname) //from api
        .then((res) => {
          console.log(res);
          this.mergeTagDialogVisible = false;
          this.$message({
            showClose: true,
            message: "Congrats, merged tags successfully.",
            type: "success",
          });
          this.getTags();
        })
        .catch((error) => {
          if (error !== "error") {
            that.$message({
              type: "error",
              message: "merge tag failed",
              showClose: true,
            });
          }
        });
    },
    /*searchCategorys(){
        let that = this
        searchCategorys().then(res => {
          console.log(res)
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: 'star failed', showClose: true})
          }
        })
      },
      searchTags(){
        let that = this
        searchTags().then(res => {
          console.log(res)
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: 'star failed', showClose: true})
          }
        })
      },*/
    createCategory() {
      this.categoryDialogVisible = true;
      //this.resetForm();
      this.$nextTick(() => {
        this.resetCategoryForm();
      });
      this.categoryForm.id = "";
    },
    createTag() {
      this.tagDialogVisible = true;
      //this.resetForm();
      this.$nextTick(() => {
        this.resetTagForm();
      });
      this.tagForm.id = "";
    },
    openMergeCategory() {
      this.mergeCategoryDialogVisible = true;
      //this.resetForm();
      this.$nextTick(() => {
        this.resetMergeCatfegoryForm();
      });
      this.mergeCategoryForm.id = "";
    },
    openMergeTag() {
      this.mergeTagDialogVisible = true;
      //this.resetForm();
      this.$nextTick(() => {
        this.resetMergeTagForm();
      });
      this.mergeTagForm.id = "";
    },
    resetCategoryForm() {
      this.categoryForm.avatar = "";
      this.$refs.categoryForm.resetFields();
    },
    resetMergeCategoryForm() {
      this.$refs.mergeCategoryForm.resetFields();
    },
    resetTagForm() {
      this.tagForm.avatar = "";
      this.$refs.tagForm.resetFields();
    },
    resetMergeTagForm() {
      this.$refs.mergeTagForm.resetFields();
    },
    // upload
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
  //组件内的守卫 调整body的背景色
  beforeRouteEnter(to, from, next) {
    window.document.body.style.backgroundColor = "#fff";
    next();
  },
  beforeRouteLeave(to, from, next) {
    window.document.body.style.backgroundColor = "#f5f5f5";
    next();
  },
};
</script>

<style>
.me-allct-body {
  margin: 60px auto 140px;
}

.me-allct-container {
  width: 1000px;
}

.me-allct-items {
  padding-top: 2rem;
}

.me-allct-item {
  width: 25%;
  display: inline-block;
  margin-bottom: 2.4rem;
  padding: 0 0.7rem;
  box-sizing: border-box;
}

.me-allct-content {
  display: inline-block;
  width: 100%;
  background-color: #fff;
  border: 1px solid #f1f1f1;
  transition: border-color 0.3s;
  text-align: center;
  padding: 1.5rem 0;
}

.me-allct-info {
  cursor: pointer;
}

.me-allct-img {
  width: 60px;
  height: 60px;
  vertical-align: middle;
}

.me-allct-name {
  font-size: 21px;
  font-weight: 150;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-top: 4px;
}

.me-allct-description {
  min-height: 50px;
  font-size: 13px;
  line-height: 25px;
}

.me-allct-meta {
  font-size: 12px;
  color: #969696;
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
