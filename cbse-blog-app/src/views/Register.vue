<template>
  <div id="register" v-title data-title="Register - For Fun">
    <!--<video preload="auto" class="me-video-player" autoplay="autoplay" loop="loop">
          <source src="../../static/vedio/sea.mp4" type="video/mp4">
      </video>-->

    <div class="me-login-box me-login-box-radius">
      <h1>Register</h1>

      <el-form ref="userForm" :model="userForm" :rules="rules">
        <el-form-item prop="account">
          <el-input placeholder="Username" v-model="userForm.account"></el-input>
        </el-form-item>

        <el-form-item prop="nickname">
          <el-input placeholder="Nickname" v-model="userForm.nickname"></el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input placeholder="Password" v-model="userForm.password"></el-input>
        </el-form-item>

        <el-form-item size="small" class="me-login-button">
          <el-button type="primary" @click.native.prevent="register('userForm')">Register</el-button>
        </el-form-item>
      </el-form>

      <div class="me-login-design">
        <p>Designed by
          <strong>
            <router-link to="/" class="me-login-design-color">LOLOBLOG</router-link>
          </strong>
        </p>
      </div>

    </div>
  </div>
</template>

<script>
  import {register} from '@/api/login'

  export default {
    name: 'Register',
    data() {
      return {
        userForm: {
          account: '',
          nickname: '',
          password: ''
        },
        rules: {
          account: [
            {required: true, message: 'please enter username', trigger: 'blur'},
            {max: 10, message: 'no more than 10 characters', trigger: 'blur'}
          ],
          nickname: [
            {required: true, message: 'please enter nickname', trigger: 'blur'},
            {max: 10, message: 'no more than 10 characters', trigger: 'blur'}
          ],
          password: [
            {required: true, message: 'please enter password', trigger: 'blur'},
            {max: 10, message: 'no more than 10 characters', trigger: 'blur'}
          ]
        }

      }
    },
    methods: {
      register(formName) {
        let that = this
        this.$refs[formName].validate((valid) => {
          if (valid) {
            that.$store.dispatch('register', that.userForm).then(() => {
              that.$message({message: 'Register successful, enjoy our website', type: 'success', showClose: true});
              that.$router.push({path: '/'})
            }).catch((error) => {
              if (error !== 'error') {
                that.$message({message: error, type: 'error', showClose: true});
              }
            })
          } else {
            return false;
          }
        });
      }

    }
  }
</script>

<style scoped>
  #login {
    min-width: 100%;
    min-height: 100%;
  }

  .me-video-player {
    background-color: transparent;
    width: 100%;
    height: 100%;
    object-fit: fill;
    display: block;
    position: absolute;
    left: 0;
    z-index: 0;
    top: 0;
  }

  .me-login-box {
    position: absolute;
    width: 300px;
    height: 320px;
    background-color: white;
    margin-top: 150px;
    margin-left: -180px;
    left: 50%;
    padding: 30px;
  }

  .me-login-box-radius {
    border-radius: 10px;
    box-shadow: 0px 0px 1px 1px rgba(161, 159, 159, 0.1);
  }

  .me-login-box h1 {
    text-align: center;
    font-size: 24px;
    margin-bottom: 20px;
    vertical-align: middle;
  }

  .me-login-design {
    text-align: center;
    font-family: 'Open Sans', sans-serif;
    font-size: 18px;
  }

  .me-login-design-color {
    color: #5FB878 !important;
  }

  .me-login-button {
    text-align: center;
  }

  .me-login-button button {
    width: 100%;
  }

</style>
