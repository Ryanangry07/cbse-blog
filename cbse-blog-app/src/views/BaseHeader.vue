<template>
  <el-header class="me-area">
    <el-row class="me-header">

      <el-col :span="4" class="me-header-left">
        <router-link to="/" class="me-title">
          <img src="../assets/img/logo.png"/>
        </router-link>
      </el-col>

      <el-col v-if="!simple" :span="16">
        <el-menu :router=true menu-trigger="click" active-text-color="#5FB878" :default-active="activeIndex"
                 mode="horizontal">
          <el-menu-item index="/">Home</el-menu-item>
          <el-menu-item index="/category/all">Category and Tag</el-menu-item>
<!--          <el-menu-item index="/tag/all">标签</el-menu-item>-->
          <el-menu-item index="/archives">Article Archive</el-menu-item>
<!--          <el-menu-item index="/log">日志</el-menu-item>-->
          <el-menu-item index="/messageBoard">Message Board</el-menu-item>

          <el-col :span="4" :offset="4">
            <el-menu-item index="/write"><i class="el-icon-edit"></i>Write Article</el-menu-item>
          </el-col>

        </el-menu>
      </el-col>

      <template v-else>
        <slot></slot>
      </template>

      <el-col :span="4">
        <el-menu :router=true menu-trigger="click" mode="horizontal" active-text-color="#5FB878">

          <template v-if="!user.login">
            <el-menu-item index="/login">
              <el-button type="text">Login</el-button>
            </el-menu-item>
            <el-menu-item index="/register">
              <el-button type="text">Register</el-button>
            </el-menu-item>
          </template>

          <template v-else>
            <el-badge :hidden="hidden" :value="formattedUnreadCounts" class="item">
<!--              <i style="margin-right: 10px" class="el-icon-message"></i>-->
              <el-button @click.native="goToNotification" size="small" style="margin-bottom: 4px" icon="el-icon-message" circle></el-button>
            </el-badge>
            <el-dropdown trigger="click" placement="top" style="line-height: 60px">

              <img class="me-header-picture el-dropdown-link" :src="user.avatar"/>

              <el-dropdown-menu slot="dropdown">
<!--                <el-dropdown-item @click="logout"><i class="el-icon-user"></i>Profile</el-dropdown-item>
                <el-dropdown-item @click="logout"><i class="el-icon-back"></i>Logout</el-dropdown-item>-->
                <el-dropdown-item @click.native="goToMyArticles">My Articles</el-dropdown-item>
                <el-dropdown-item @click.native="goToMyStars">My Stars</el-dropdown-item>
                <el-dropdown-item @click.native="goToProfile">Profile</el-dropdown-item>
                <el-dropdown-item @click.native="logout">Logout</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
<!--            <el-submenu index>
              <template slot="title">
                <img class="me-header-picture" :src="user.avatar"/>
              </template>
              <el-menu-item index @click="logout"><i class="el-icon-user"></i>Profile</el-menu-item>
              <el-menu-item index @click="logout"><i class="el-icon-back"></i>Logout</el-menu-item>
            </el-submenu>-->
            <!--            notification-->
          </template>
        </el-menu>
      </el-col>

    </el-row>
  </el-header>
</template>

<script>
  import eventBus from '@/utils/eventBus.js';
  import {loadUnreadCounts} from '@/api/notification.js';
  export default {
    name: 'BaseHeader',
    props: {
      activeIndex: String,
      simple: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        unreadCounts: 0,
        hidden: false
      }
    },
    created() {
      if(this.$store.state.id){
        this.loadUnreadCounts();
      }
      eventBus.$on('unreadCountsChanged', counts => {
        this.unreadCounts = counts;
      });
      eventBus.$on('unreadCountsIncrement', counts => {
        this.unreadCounts += 1;
      });
    },
    computed: {
      formattedUnreadCounts() {
        // Display null when unreadCounts is 0, otherwise, display the actual value
        if(this.unreadCounts === 0){
          this.hidden = true;
        }else{
          this.hidden = false;
        }
        return this.unreadCounts;
      },
      user() {
        let login = this.$store.state.account.length != 0
        let avatar = this.$store.state.avatar
        return {
          login, avatar
        }
      }
    },
    methods: {
      logout() {
        let that = this
        this.$store.dispatch('logout').then(() => {
          this.$router.push({path: '/'})
        }).catch((error) => {
          if (error !== 'error') {
            that.$message({message: error, type: 'error', showClose: true});
          }
        })
      },
      goToMyArticles(){
        this.$router.push("/blogmy");
      },
      goToMyStars(){
        this.$router.push("/mystars");
      },
      goToProfile() {
        // Use this.$router.push to navigate to the "/profile" route
        console.log('go to profile')
        this.$router.push("/profile");
      },
      goToNotification(){
        console.log('go to profile')
        this.$router.push("/notification");
      },
      loadUnreadCounts(){
        let that = this
        loadUnreadCounts(that.$store.state.id).then(res => {
          that.unreadCounts = res.data.unreadCounts
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: 'Unread notification counts load failed', showClose: true})
          }
        })
      }
    }
  }
</script>

<style>

  .el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
  }
  .el-icon-arrow-down {
    font-size: 12px;
  }
  .demonstration {
    display: block;
    color: #8492a6;
    font-size: 14px;
    margin-bottom: 20px;
  }

  .el-header {
    position: fixed;
    z-index: 1024;
    min-width: 100%;
    box-shadow: 0 2px 3px hsla(0, 0%, 7%, .1), 0 0 0 1px hsla(0, 0%, 7%, .1);
  }

  .me-title {
    margin-top: 10px;
    font-size: 24px;
  }

  .me-header-left {
    margin-top: 10px;
  }

  .me-title img {
    max-height: 2.4rem;
    max-width: 100%;
  }

  .me-header-picture {
    width: 36px;
    height: 36px;
    border: 1px solid #ddd;
    border-radius: 50%;
    vertical-align: middle;
    background-color: #5fb878;
  }

  .item {
    margin-top: 10px;
    margin-right: 40px;
  }
</style>
