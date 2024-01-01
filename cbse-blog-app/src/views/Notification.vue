<template>
  <div style="width: 70%">
<!--    <el-badge is-dot class="item">数据查询</el-badge>-->
    <el-tabs :tab-position="tabPosition" style="height: 100%">
      <el-tab-pane label="All Notifications">
        <div>
          <span style="margin-left: 10px">All Notifications</span>
          <div style="float: right; margin-top: -15px">
            <el-button type="primary" class="el-icon-info notification-btn" @click="markPageAsRead(pageFullNotificationList)">&nbsp Mark This Page as Read</el-button>
            <el-button type="warning" class="el-icon-question notification-btn" @click="markPageAsUnread(pageFullNotificationList)">&nbsp Mark This Page as Unread</el-button>
            <el-button type="danger" class="el-icon-delete notification-btn" @click="deletePage(pageFullNotificationList)">&nbsp Delete This Page</el-button>
          </div>

          <el-collapse style="margin-top: 13px" v-model="activeName" @change="handleChange" accordion>
            <el-collapse-item v-for="notification in pageFullNotificationList" :key="notification.id" :name="notification.id" style="margin-left: 15px">
              <template slot="title">
                <el-badge :is-dot="!notification.readStatus" class="item">
                  <i class="el-icon-bell right-3 item"></i>
                </el-badge>
<!--                <span v-if="notification.readStatus">{{notification.title}}</span>-->
                <span :style="{ 'font-weight': notification.readStatus ? 'normal' : 'bold' }">{{ notification.title }}</span>
                <div style="float: right">{{notification.createDate}}</div>
              </template>
              <div><b>Datetime</b>: {{notification.createDate}}</div>

              <div v-if="notification.fromUser"><b>From account</b>: {{notification.fromUser}}</div>
              <div v-else><b>From</b>: System</div>

              <div><b>Content</b>: {{notification.content}}</div>
              <el-button type="warning" class="el-icon-question notification-btn" @click="markNotificationAsUnread(notification.id)">&nbsp Mark as Unread</el-button>
              <el-button type="danger" class="el-icon-delete notification-btn" @click="deleteNotification(notification.id)">&nbsp Delete</el-button>
            </el-collapse-item>
          </el-collapse>

          <el-pagination
            @size-change="handleAllSizeChange"
            @current-change="handleAllCurrentChange"
            :current-page="pageNumAll"
            :page-sizes="[5, 10, 20, 30]"
            :page-size="pageSizeAll"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalAll"
            style="margin-left: 25%; margin-top: 10px">
          </el-pagination>
        </div>
      </el-tab-pane>

      <el-tab-pane label="Unread Notifications">
        <div>
          <span style="margin-left: 10px">Unread Notifications</span>
          <div style="float: right; margin-top: -15px">
            <el-button type="primary" class="el-icon-info notification-btn" @click="markPageAsRead(pageUnreadNotificationList)">&nbsp Mark This Page as Read</el-button>
            <el-button type="warning" class="el-icon-question notification-btn" @click="markPageAsUnread(pageUnreadNotificationList)">&nbsp Mark This Page as Unread</el-button>
            <el-button type="danger" class="el-icon-delete notification-btn" @click="deletePage(pageUnreadNotificationList)">&nbsp Delete This Page</el-button>
          </div>

          <el-collapse style="margin-top: 13px" v-model="activeName" @change="handleChange" accordion>
            <el-collapse-item v-for="notification in pageUnreadNotificationList" :key="notification.id" :name="notification.id" style="margin-left: 15px">
              <template slot="title">
                <el-badge :is-dot="!notification.readStatus" class="item">
                  <i class="el-icon-bell right-3 item"></i>
                </el-badge>
                <span :style="{ 'font-weight': notification.readStatus ? 'normal' : 'bold' }">{{ notification.title }}</span>
                <div style="float: right">{{notification.createDate}}</div>
              </template>
              <div><b>Datetime</b>: {{notification.createDate}}</div>

              <div v-if="notification.fromUser"><b>From account</b>: {{notification.fromUser}}</div>
              <div v-else><b>From</b>: System</div>

              <div><b>Content</b>: {{notification.content}}</div>
              <el-button type="warning" class="el-icon-question notification-btn" @click="markNotificationAsUnread(notification.id)">&nbsp Mark as Unread</el-button>
              <el-button type="danger" class="el-icon-delete notification-btn" @click="deleteNotification(notification.id)">&nbsp Delete</el-button>
            </el-collapse-item>
          </el-collapse>

          <el-pagination
            @size-change="handleUnreadSizeChange"
            @current-change="handleUnreadCurrentChange"
            :current-page="pageNumUnread"
            :page-sizes="[5, 10, 20, 30]"
            :page-size="pageSizeUnread"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalUnread"
            style="margin-left: 25%; margin-top: 10px">
          </el-pagination>
        </div>
      </el-tab-pane>

      <el-tab-pane label="@me Notifications">
        <div>
          <span style="margin-left: 10px">@me Notifications</span>
          <div style="float: right; margin-top: -15px">
            <el-button type="primary" class="el-icon-info notification-btn" @click="markPageAsRead(pageAtMeNotificationList)">&nbsp Mark This Page as Read</el-button>
            <el-button type="warning" class="el-icon-question notification-btn" @click="markPageAsUnread(pageAtMeNotificationList)">&nbsp Mark This Page as Unread</el-button>
            <el-button type="danger" class="el-icon-delete notification-btn" @click="deletePage(pageAtMeNotificationList)">&nbsp Delete This Page</el-button>
          </div>

          <el-collapse style="margin-top: 13px" v-model="activeName" @change="handleChange" accordion>
            <el-collapse-item v-for="notification in pageAtMeNotificationList" :key="notification.id" :name="notification.id" style="margin-left: 15px">
              <template slot="title">
                <el-badge :is-dot="!notification.readStatus" class="item">
                  <i class="el-icon-bell right-3 item"></i>
                </el-badge>
                <!--                <span v-if="notification.readStatus">{{notification.title}}</span>-->
                <span :style="{ 'font-weight': notification.readStatus ? 'normal' : 'bold' }">{{ notification.title }}</span>
                <div style="float: right">{{notification.createDate}}</div>
              </template>
              <div><b>Datetime</b>: {{notification.createDate}}</div>

              <div v-if="notification.fromUser"><b>From account</b>: {{notification.fromUser}}</div>
              <div v-else><b>From</b>: System</div>

              <div><b>Content</b>: {{notification.content}}</div>
              <el-button type="warning" class="el-icon-question notification-btn" @click="markNotificationAsUnread(notification.id)">&nbsp Mark as Unread</el-button>
              <el-button type="danger" class="el-icon-delete notification-btn" @click="deleteNotification(notification.id)">&nbsp Delete</el-button>
            </el-collapse-item>
          </el-collapse>

          <el-pagination
            @size-change="handleAtMeSizeChange"
            @current-change="handleAtMeCurrentChange"
            :current-page="pageNumAtMe"
            :page-sizes="[5, 10, 20, 30]"
            :page-size="pageSizeAtMe"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalAtMe"
            style="margin-left: 25%; margin-top: 10px">
          </el-pagination>
        </div>
      </el-tab-pane>


      <el-tab-pane label="System Notifications">
        <div>
          <span style="margin-left: 10px">System Notifications</span>
          <div style="float: right; margin-top: -15px">
            <el-button type="primary" class="el-icon-info notification-btn" @click="markPageAsRead(pageSystemNotificationList)">&nbsp Mark This Page as Read</el-button>
            <el-button type="warning" class="el-icon-question notification-btn" @click="markPageAsUnread(pageSystemNotificationList)">&nbsp Mark This Page as Unread</el-button>
            <el-button type="danger" class="el-icon-delete notification-btn" @click="deletePage(pageSystemNotificationList)">&nbsp Delete This Page</el-button>
          </div>

          <el-collapse style="margin-top: 13px" v-model="activeName" @change="handleChange" accordion>
            <el-collapse-item v-for="notification in pageSystemNotificationList" :key="notification.id" :name="notification.id" style="margin-left: 15px">
              <template slot="title">
                <el-badge :is-dot="!notification.readStatus" class="item">
                  <i class="el-icon-bell right-3 item"></i>
                </el-badge>
                <span :style="{ 'font-weight': notification.readStatus ? 'normal' : 'bold' }">{{ notification.title }}</span>
                <div style="float: right">{{notification.createDate}}</div>
              </template>
              <div><b>Datetime</b>: {{notification.createDate}}</div>

              <div v-if="notification.fromUser"><b>From account</b>: {{notification.fromUser}}</div>
              <div v-else><b>From</b>: System</div>

              <div><b>Content</b>: {{notification.content}}</div>
              <el-button type="warning" class="el-icon-question notification-btn" @click="markNotificationAsUnread(notification.id)">&nbsp Mark as Unread</el-button>
              <el-button type="danger" class="el-icon-delete notification-btn" @click="deleteNotification(notification.id)">&nbsp Delete</el-button>
            </el-collapse-item>
          </el-collapse>

          <el-pagination
            @size-change="handleSystemSizeChange"
            @current-change="handleSystemCurrentChange"
            :current-page="pageNumSystem"
            :page-sizes="[5, 10, 20, 30]"
            :page-size="pageSizeSystem"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalSystem"
            style="margin-left: 25%; margin-top: 10px">
          </el-pagination>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>


</template>

<script>
  import {getAllNotifications, markAsRead, markAsUnread, deleteById,
    markPageAsRead, markPageAsUnread, deletePage} from '@/api/notification';
  import eventBus from '@/utils/eventBus.js';
  export default {
    name: "Notification",
    created() {
      this.getAllNotifications()
    },
    data() {
      return {
        // pagination
        pageSizeAll: 10,
        pageNumAll: 1,
        totalAll: 0,
        pageSizeUnread: 10,
        pageNumUnread: 1,
        totalUnread: 0,
        pageSizeAtMe: 10,
        pageNumAtMe: 1,
        totalAtMe: 0,
        pageSizeSystem: 10,
        pageNumSystem: 1,
        totalSystem: 0,
        // four types of notification lists
        pageFullNotificationList: [],
        fullNotificationList: [],
        //unreadNotificationList: [],
        pageUnreadNotificationList: [],
        //atMeNotificationList: [],
        pageAtMeNotificationList: [],
        //systemNotificationList: [],
        pageSystemNotificationList: [],
        // element ui
        tabPosition: 'left',
        activeName: '0'
      };
    },
    computed: {
      unreadNotificationList() {
        let unread = this.fullNotificationList.filter(notification => !notification.readStatus);
        this.totalUnread = unread.length;
        return unread;
      },
      atMeNotificationList() {
        let atMe = this.fullNotificationList.filter(notification => notification.type === 0);
        this.totalAtMe = atMe.length;
        return atMe;
      },
      systemNotificationList() {
        let system = this.fullNotificationList.filter(notification => notification.type === 1);
        this.totalSystem = system.length;
        return system;
      }
    },
    methods: {
      getAllNotifications(){
        let that = this
        getAllNotifications(that.$store.state.id).then(res => {

          // fullNotificationList changed, so computed (Unread, AtMe, System) update
          that.fullNotificationList = res.data.notificationList
          that.totalAll = res.data.total
          that.loadAllPageData()
          that.loadUnreadPageData()
          that.unreadCounts = that.unreadNotificationList.length
          eventBus.$emit("unreadCountsChanged", this.unreadCounts)
          that.loadAtMePageData()
          that.loadSystemPageData()
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: 'Notification list load failed!', showClose: true})
          }
        })
      },
      handleChange(val) {
        let that = this
        let notificationId = val;
        let notification = that.fullNotificationList.find(item => item.id === notificationId);

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

        // fullNotificationList changed, so computed (Unread, AtMe, System) update
        let notification = this.fullNotificationList.find(item => item.id === notificationId);
        if (notification) {
          notification.readStatus = true;
          markAsRead(notificationId).then(res => {
            this.getAllNotifications()
          }).catch(error => {
            if (error !== 'error') {
              that.$message({type: 'error', message: 'Notification mark as read failed!', showClose: true})
            }
          })
          // eventBus.$emit("unreadCountsChanged", this.totalUnread)
          //console.log(`Marked notification ${notificationId} as read.`);
        }
      },
      markNotificationAsUnread(notificationId) {
        let that = this
        // Implement your logic to send a read request here
        // Once the request is successful, update the readStatus in the pageNotificationList

        // fullNotificationList changed, so computed (Unread, AtMe, System) update
        let notification = this.fullNotificationList.find(item => item.id === notificationId);
        if (notification) {
          notification.readStatus = false;
          markAsUnread(notificationId).then(res => {
            this.getAllNotifications()
          }).catch(error => {
            if (error !== 'error') {
              that.$message({type: 'error', message: 'Notification mark as read failed!', showClose: true})
            }
          })
          // eventBus.$emit("unreadCountsChanged", this.unreadCounts)
          // console.log(`Marked notification ${notificationId} as unread.`);
        }
      },
      deleteNotification(notificationId){
        deleteById(notificationId)
          .then(res => {
            // Handle success if needed
            // Remove the deleted notification from the notificationList
            // fullNotificationList changed, so computed (Unread, AtMe, System) update
            this.fullNotificationList = this.fullNotificationList.filter(item => item.id !== notificationId);
            this.loadAllPageData()
            // eventBus.$emit('unreadCountsChanged', this.totalUnread);
            // console.log(`Deleted notification with ID: ${notificationId}`);
          })
          .catch(error => {
            if (error !== 'error') {
              this.$message({ type: 'error', message: 'Notification deletion failed!', showClose: true });
            }
          });
      },
      markPageAsUnread(pageList){
        let that = this;
        // Implement your logic to send a read request for each page in the pageList
        // Once the requests are successful, update the readStatus in the pageList

        // Iterate through each page in the pageList
        pageList.forEach(notificationId => {
          let notification = this.fullNotificationList.find(item => item.id === notificationId);
          if (notification) {
            notification.readStatus = false;
          }
        });

        let notificationIds = pageList.map(page => page.id);
        markPageAsUnread(notificationIds).then(res => {
          // Handle success if needed
          // fullNotificationList changed, so computed (Unread, Read) update
          this.getAllNotifications();
          // eventBus.$emit("unreadCountsChanged", this.totalUnread);
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: 'Page mark as read failed!', showClose: true});
          }
        });
      },
      markPageAsRead(pageList){
        let that = this;
        // Implement your logic to send a read request for each page in the pageList
        // Once the requests are successful, update the readStatus in the pageList

        // Iterate through each page in the pageList
        pageList.forEach(notificationId => {
          let notification = this.fullNotificationList.find(item => item.id === notificationId);
          if (notification) {
            notification.readStatus = true;
          }
        });

        let notificationIds = pageList.map(page => page.id);
        markPageAsRead(notificationIds).then(res => {
          // Handle success if needed
          // fullNotificationList changed, so computed (Unread, Read) update
          this.getAllNotifications();
          // eventBus.$emit("unreadCountsChanged", this.totalUnread);
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: 'Page mark as unread failed!', showClose: true});
          }
        });
      },
      deletePage(pageList){
        // Implement your logic to delete each page in the pageList
        // Once the requests are successful, remove the deleted pages from the fullNotificationList

        // Iterate through each page in the pageList
        /*pageList.forEach(notificationId => {
          this.fullNotificationList = this.fullNotificationList.filter(item => item.id !== notificationId);
          this.totalAll = this.fullNotificationList.length;
          eventBus.$emit('unreadCountsChanged', this.unreadCounts);
          console.log(`Deleted page with ID: ${notificationId}`);
        });*/
        let notificationIds = pageList.map(page => page.id);
        deletePage(notificationIds).then(res => {
          // Handle success if needed
          // Remove the deleted page from the fullNotificationList
          this.getAllNotifications();
          this.totalAll = this.fullNotificationList.length;
          // eventBus.$emit('unreadCountsChanged', this.totalUnread);
        }).catch(error => {
          if (error !== 'error') {
            this.$message({ type: 'error', message: 'Page deletion failed!', showClose: true });
          }
        });
      },
      handleAllSizeChange(val) {
        this.pageNumAll = 1;
        this.pageSizeAll = val;
        // Load the corresponding data based on the selected page size
        // You may need to modify this logic based on your actual use case
        this.loadAllPageData();
        console.log(`${val} items per page`);
      },
      handleAllCurrentChange(val) {
        this.pageNumAll = val;
        // Load the corresponding data based on the selected page
        // You may need to modify this logic based on your actual use case
        this.loadAllPageData();
        console.log(`current page: ${val}`);
      },
      loadAllPageData() {
        // Implement the logic to load the corresponding data for the current page
        // You may need to adjust this based on your actual use case
        // For example, update pageNotificationList with the data for the current page
        const startIdx = (this.pageNumAll - 1) * this.pageSizeAll;
        const endIdx = startIdx + this.pageSizeAll;
        console.log('start: ' + startIdx + ', end: ' + endIdx)
        this.pageFullNotificationList = this.fullNotificationList.slice(startIdx, endIdx);
      },
      handleUnreadSizeChange(val) {
        this.pageNumUnread = 1;
        this.pageSizeUnread = val;
        this.loadUnreadPageData();
      },
      handleUnreadCurrentChange(val) {
        this.pageNumUnread = val;
        this.loadUnreadPageData();
      },
      loadUnreadPageData() {
        const startIdx = (this.pageNumUnread - 1) * this.pageSizeUnread;
        this.pageUnreadNotificationList = this.unreadNotificationList.slice(startIdx, startIdx + this.pageSizeUnread);
      },
      handleAtMeSizeChange(val) {
        this.pageNumAtMe = 1;
        this.pageSizeAtMe = val;
        this.loadAtMePageData();
      },
      handleAtMeCurrentChange(val) {
        this.pageNumAtMe = val;
        this.loadAtMePageData();
      },
      loadAtMePageData() {
        const startIdx = (this.pageNumAtMe - 1) * this.pageSizeAtMe;
        this.pageAtMeNotificationList = this.atMeNotificationList.slice(startIdx, startIdx + this.pageSizeAtMe);
      },
      handleSystemSizeChange(val) {
        this.pageNumSystem = 1;
        this.pageSizeSystem = val;
        this.loadSystemPageData();
      },
      handleSystemCurrentChange(val) {
        this.pageNumSystem = val;
        this.loadSystemPageData();
      },
      loadSystemPageData() {
        const startIdx = (this.pageNumSystem - 1) * this.pageSizeSystem;
        this.pageSystemNotificationList = this.systemNotificationList.slice(startIdx, startIdx + this.pageSizeSystem);
      },
    }
  };
</script>

<style scoped>

  .notification-btn{
    font-size: 15px;
    padding: 5px 5px;
    margin-right: 10px;
    margin-top: 15px;
    cursor: pointer;
  }
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
