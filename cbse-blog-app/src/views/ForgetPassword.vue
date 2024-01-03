<template>
  <div id="forget-password" v-title data-title="Forget Password - LOLOBLOG">
    <div class="me-login-box me-login-box-radius">
      <h1>Forget Password</h1>

      <el-form ref="forgetForm" :model="forgetForm" :rules="forgetRules">
        <el-form-item prop="email">
          <el-input placeholder="Registered email" v-model="forgetForm.email"></el-input>
        </el-form-item>


        <el-form-item prop="password">
          <el-input placeholder="New password" v-model="forgetForm.password"></el-input>
        </el-form-item>

        <el-form-item prop="verificationCode">
          <el-input placeholder="Verification code" v-model="forgetForm.verificationCode"></el-input>
          <el-button type="text" @click="sendVerificationCode" :disabled="countdown > 0">
             {{ countdown > 0 ? `After ${countdown}s get again` : 'Get verification code' }}
          </el-button>
        </el-form-item>

        <el-form-item size="small" class="me-login-button">
          <el-button type="primary" @click.native.prevent="submitForgetForm">Reset password</el-button>
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
import {sendEmail, resetPassword} from '@/api/login';
export default {
  name: 'ForgetPassword',
  data() {
    return {
      backendCode: '',
      forgetForm: {
        email: '',
        password: '',
        verificationCode: '',
      },
      forgetRules: {
        email: [
          { required: true, message: 'please enter registered email', trigger: 'blur' },
          { type: 'email', message: 'please enter valid email', trigger: ['blur', 'change'] },
        ],
        password: [
          { required: true, message: 'please enter new password', trigger: 'blur' },
        ],
        verificationCode: [
          { required: true, message: 'please enter verification code', trigger: 'blur' },
        ],
      },
      countdown: 0,
    };
  },
  methods: {
    sendVerificationCode() {
      // Add logic to send verification code to the user's email
      // simulate a countdown here
      if (this.countdown === 0) {
        this.countdown = 60; // Set countdown time (in seconds)
        const timer = setInterval(() => {
          if (this.countdown > 0) {
            this.countdown--;
          } else {
            clearInterval(timer);
          }
        }, 1000);
      }
      // check email and send code

      sendEmail(this.forgetForm.email).then(res => {
        this.backendCode = res.data.code
        console.log('res.data: ' + res.data)
        console.log('backendCode: ' + this.backendCode)
        this.$message({type: 'success', message: 'Send verification code successfully, please check email!', showClose: true})
      }).catch(error => {
        console.log(error)
        if (error !== 'error') {
          this.$message({type: 'error', message: error, showClose: true})
        }
      })
    },
    submitForgetForm() {
      if(this.backendCode !== this.forgetForm.verificationCode){
        this.$message({type: 'error', message: 'Verification Failed', showClose: true})
      }else{
        this.$refs.forgetForm.validate((valid) => {
          if (valid) {
            // Call your API or dispatch an action to handle password recovery
            // You can show a success message or navigate to a success page
            resetPassword(this.forgetForm.email, this.forgetForm.password).then(res => {
              this.$message({type: 'success', message: 'Password reset successfully, please login again!', showClose: true})
              this.$router.push({path: '/'})
            }).catch(error => {
              console.log(error)
              if (error !== 'error') {
                this.$message({type: 'error', message: error, showClose: true})
              }
            })
          } else {
            return false;
          }
        });
      }
    },
  },
};
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
  height: 350px;
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
