<template>
  <div id="login" v-title data-title="Login - For Fun">
    <!--<video preload="auto" class="me-video-player" autoplay="autoplay" loop="loop">
          <source src="../../static/vedio/sea.mp4" type="video/mp4">
      </video>-->

    <div class="me-login-box me-login-box-radius">
      <h1>Login</h1>

      <el-form ref="userForm" :model="userForm" :rules="rules">
        <el-form-item prop="account">
          <el-input placeholder="Username" v-model="userForm.account"></el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input placeholder="Password" type="password" v-model="userForm.password"></el-input>
        </el-form-item>

        <div style="margin-top: -5px; margin-bottom: 5px">
          <el-link style="margin-left: 30%" @click.native="goToForgetPassword">Forget Password</el-link>
<!--          <a>Forget Password</a>-->
        </div>

        <el-form-item size="small" class="me-login-button">
          <el-button type="primary" @click.native.prevent="login('userForm')">Login</el-button>
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
  export default {
    name: 'Login',
    data() {
      return {
        userForm: {
          account: '',
          password: ''
        },
        rules: {
          account: [
            {required: true, message: 'please enter username', trigger: 'blur'},
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
      login(formName) {
        let that = this

        this.$refs[formName].validate((valid) => {
          if (valid) {

            that.$store.dispatch('login', that.userForm).then(() => {
              that.$router.go(-1)
            }).catch((error) => {
              if (error !== 'error') {
                that.$message({message: error, type: 'error', showClose: true});
              }
            })
          } else {
            return false;
          }
        });
      },
      goToForgetPassword(){
        this.$router.push("/forget");
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
    height: 280px;
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
