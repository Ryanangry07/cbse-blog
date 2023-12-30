<template>
  <div style="width: 70%">
<!--    <el-badge is-dot class="item">数据查询</el-badge>-->
    <el-tabs :tab-position="tabPosition" style="height: 100%">
      <el-tab-pane label="All Notifications">
        <div>
          <span style="margin-left: 10px">All Notifications</span>
          <el-collapse v-model="activeName" @change="handleChange" accordion>
            <el-collapse-item v-for="notification in notificationList" :key="notification.id" :name="notification.id" style="margin-left: 15px">
              <template slot="title">
                <el-badge :is-dot="!notification.readStatus" class="item">
                  <i class="el-icon-bell right-3 item"></i>
                </el-badge>
                {{notification.title}}
              </template>
              <div>Datetime: {{notification.createDate}}</div>

              <div v-if="notification.fromUser">From account: {{notification.fromUser}}</div>
              <div v-else>From: System</div>

              <div>Content: {{notification.content}}</div>
            </el-collapse-item>
          </el-collapse>
        </div>
      </el-tab-pane>

      <el-tab-pane label="Unread Notifications">
        <div>
          <span style="margin-left: 10px">Unread Notifications</span>
          <el-collapse v-model="activeName" @change="handleChange" accordion>
            <el-collapse-item v-for="notification in unreadNotifications" :key="notification.id" :name="notification.id" style="margin-left: 15px">
              <template slot="title">
                <el-badge :is-dot="!notification.readStatus" class="item">
                  <i class="el-icon-bell right-3 item"></i>
                </el-badge>
                {{notification.title}}
              </template>
              <div>Datetime: {{notification.createDate}}</div>
              <div v-if="notification.fromUser">From account: {{notification.fromUser}}</div>
              <div v-else>From: System</div>
              <div>Content: {{notification.content}}</div>
            </el-collapse-item>
          </el-collapse>
        </div>
      </el-tab-pane>

      <el-tab-pane label="@me Notifications">
        <div>
          <span style="margin-left: 10px">@me Notifications</span>
          <el-collapse v-model="activeName" @change="handleChange" accordion>
            <el-collapse-item v-for="notification in atMeNotifications" :key="notification.id" :name="notification.id" style="margin-left: 15px">
              <template slot="title">
                <el-badge :is-dot="!notification.readStatus" class="item">
                  <i class="el-icon-bell right-3 item"></i>
                </el-badge>
                {{notification.title}}
              </template>
              <div>Datetime: {{notification.createDate}}</div>
              <div v-if="notification.fromUser">From account: {{notification.fromUser}}</div>
              <div v-else>From: System</div>
              <div>Content: {{notification.content}}</div>
            </el-collapse-item>
          </el-collapse>
        </div>
      </el-tab-pane>


      <el-tab-pane label="System Notifications">
        <div>
          <span style="margin-left: 10px">System Notifications</span>
          <el-collapse v-model="activeName" @change="handleChange" accordion>
            <el-collapse-item v-for="notification in systemNotifications" :key="notification.id" :name="notification.id" style="margin-left: 15px">
              <template slot="title">
                <el-badge :is-dot="!notification.readStatus" class="item">
                  <i class="el-icon-bell right-3 item"></i>
                </el-badge>
                {{notification.title}}
              </template>
              <div>Datetime: {{notification.createDate}}</div>
              <div v-if="notification.fromUser">From account: {{notification.fromUser}}</div>
              <div v-else>From: System</div>
              <div>Content: {{notification.content}}</div>
            </el-collapse-item>
          </el-collapse>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>


</template>

<script>
  import {getNotifications, markAsRead} from '@/api/notification';
  import eventBus from '@/utils/eventBus.js';
  export default {
    name: "Notification",
    created() {
      this.getNotifications()
    },
    data() {
      return {
        // activeNames: ['0'],
        notificationList: [
          /*{
            id: '1',
            readStatus: false,
            title: 'I am a title',
            content: 'I am the content',
            type: '1', // 0: @me(comment,reply,star), 1:system
            createDate: '2023-12-30 5:04',
            //uid
            //fromUid
            fromUser: ''
          },
          {
            id: '2',
            readStatus: false,
            title: 'Hello, this is my read notification',
            content: 'I am the content, and I have read this content before',
            type: '1', // 0: @me(comment,reply,star), 1:system
            createDate: '2023-12-30 5:04',
            //uid
            //fromUid
            fromUser: ''
          },
          {
            id: '3',
            readStatus: false,
            title: 'Hello, someone\'s like you ',
            content: 'User root click like on your article.',
            type: '0', // 0: @me(comment,reply,star), 1:system
            createDate: '2023-12-30 5:04',
            //uid
            //fromUid
            fromUser: 'root'
          },*/
        ],
        tabPosition: 'left',
        activeName: '0'
      };
    },
    computed: {
      unreadNotifications() {
        return this.notificationList.filter(notification => !notification.readStatus);
      },
      unreadCounts() {
        return this.unreadNotifications.length;
      },
      atMeNotifications() {
        return this.notificationList.filter(notification => notification.type === 0);
      },
      systemNotifications() {
        return this.notificationList.filter(notification => notification.type === 1);
      }
    },
    methods: {
      getNotifications(){
        let that = this
        getNotifications(that.$store.state.id).then(res => {
          that.notificationList = res.data
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: 'Notification list load failed!', showClose: true})
          }
        })
      },
      handleChange(val) {
        let that = this
        let notificationId = val;
        let notification = that.notificationList.find(item => item.id === notificationId);

        if (notification && !notification.readStatus) {
          // Notification is unread, send read request and update readStatus
          that.markNotificationAsRead(notificationId);
        } else {
          // Notification is already read, do nothing
          console.log(`Notification ${notificationId} is already read.`);
        }
      },
      markNotificationAsRead(notificationId) {
        let that = this
        // Implement your logic to send a read request here
        // Once the request is successful, update the readStatus in the notificationList
        let notification = this.notificationList.find(item => item.id === notificationId);
        if (notification) {
          notification.readStatus = true;
          markAsRead(notificationId).then(res => {

          }).catch(error => {
            if (error !== 'error') {
              that.$message({type: 'error', message: 'Notification mark as read failed!', showClose: true})
            }
          })
          eventBus.$emit("unreadCountsChanged", this.unreadCounts)
          //console.log(`Marked notification ${notificationId} as read.`);
        }
      },
      markNotificationAsUnread(notificationId) {
        // Implement your logic to send a read request here
        // Once the request is successful, update the readStatus in the notificationList
        let notification = this.notificationList.find(item => item.id === notificationId);
        if (notification) {
          notification.readStatus = true;
          console.log(`Marked notification ${notificationId} as unread.`);
        }
      }
    }
  };
</script>

<style scoped>


  .item {
    /*margin-top: 10px;
    margin-right: 40px;*/
    margin: 0px 20px 0px 0px;
    padding: 0;
    line-height: 1.5;
  }
  .item-back{
    margin-top: -10px;
    margin-right: -40px;
  }
  .right-3 {
    margin-right: 3px;
  }
  .el-collapse >>> .el-collapse-item {
    background-color: #f5f5f5 !important;
  }
  .el-collapse-item >>> .el-collapse-item__header {
    background-color: #f5f5f5 !important;
  }
  .el-collapse-item >>> .el-collapse-item__wrap {
    background-color: #f5f5f5 !important;
    border-bottom: 2px solid #ebeef5 !important;
  }

</style>

<style>
</style>
