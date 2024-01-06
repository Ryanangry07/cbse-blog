
<template>
  <div>
    <el-card style="min-width: 900px">
      <div slot="header" class="clearfix header-center">
        <span>User Profile</span>
      </div>
      <el-form label-position="right" :model="user" label-width="170px">
        <el-form-item label="Account">
          <el-input v-model="user.account" disabled></el-input>
        </el-form-item>
        <el-form-item label="Nickname">
          <el-input v-model="user.nickname"></el-input>
        </el-form-item>

<!--
        <el-form-item label="Avatar">
          <el-upload
            action="/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
          >
            <el-button size="small" type="primary">Upload</el-button>
          </el-upload>
          <img v-if="user.avatar" :src="user.avatar" class="avatar-preview" />
        </el-form-item>-->

        <el-form-item label="Avatar" prop="avatar">
          <el-upload
            class="avatar-uploader"
            action="http://localhost:8080/upload"
            name="uploadAvatar"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="user.avatar" :src="user.avatar" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="Email">
          <el-input v-model="user.email"></el-input>
        </el-form-item>
        <el-form-item label="Mobile Number">
          <el-input v-model="user.mobilePhoneNumber"></el-input>
        </el-form-item>
        <el-form-item label="About Me Visible">
<!--          <el-switch v-model="user.aboutMeVisible"></el-switch>-->
          <el-radio-group v-model="user.aboutMeVisible">
            <el-radio :label="true">Public</el-radio>
            <el-radio :label="false">Private</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="About Me">
          <el-input type="textarea" v-model="user.aboutMe"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveProfile">Save</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>

</template>

<script>
  import {getUserProfile, saveUserProfile} from '@/api/login'
  export default {
    name: "Profile",
    created() {
      this.getUserProfile()
    },
    data() {
      return {
        user: {
          id: '',
          account: '',
          nickname: '',
          avatar: '', // Set the default avatar URL if available
          email: '',
          mobilePhoneNumber: '',
          aboutMeVisible: '',
          aboutMe: ''
        },
      };
    },
    methods: {
      getUserProfile(){
        let that = this
        let userId = this.$store.state.id
        getUserProfile(userId).then(res => {
          that.user.id = res.data.id
          that.user.account = res.data.account
          that.user.nickname = res.data.nickname
          that.user.avatar = res.data.avatar
          that.user.email = res.data.email
          that.user.mobilePhoneNumber = res.data.mobilePhoneNumber
          that.user.aboutMe = res.data.aboutMe
          that.user.aboutMeVisible = res.data.aboutMeVisible
          /*console.log(that.user)
          console.log('store avatar: ' + that.$store.state.avatar)
          console.log('get avatar: ' + that.user.avatar)*/
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: 'User Profile Load Failed', showClose: true})
          }
        })
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isPNG = file.type === 'image/png';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG && !isPNG) {
          this.$message.error('You can only upload JPG or PNG image!');
        }
        if (!isLt2M) {
          this.$message.error('The size of image cannot exceed 2MB!');
        }
        //if less than 2MB and correct image format, return true
        return isLt2M && (isJPG || isPNG);
      },
      handleAvatarSuccess(res, file, fileList) {
        // Handle the avatar upload success, update user.avatar with the new URL
        let that = this
        if(res.code == 0){
          //this.imageUrl = URL.createObjectURL(file.raw);
          that.user.avatar = res.data.url
        }else{
          this.$message.error(`user avatar upload failed:${res.message}`)
        }
        console.log(that.user.avatar)
      },
      saveProfile() {
        // Implement your logic to save the user profile changes
        console.log('Profile saved:', this.user);
        let that = this
        saveUserProfile(that.user).then(res => {
          that.$store.state.avatar = that.user.avatar
          this.$message({
            showClose: true,
            message: 'Congrats, update profile successfully.',
            type: 'success'
          });
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: 'User Profile Load Failed', showClose: true})
          }
        })
      }
    }
  }
</script>

<style scoped>
  .header-center {
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
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
    background-color: #5fb878;
  }
</style>
