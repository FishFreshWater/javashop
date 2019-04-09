<template>
  <div class="header">
    <div class="header-guide">
      <div class="header-srh" title="搜索" :class="{'is-collapse':srhCollapse}">
        <transition
          :duration="400"
          v-on:enter="srhActive = true;"
          v-on:leave="srhActive = true;"
          v-on:after-enter="srhActive = false;"
          v-on:after-leave="srhActive = false;"
          >
          <div v-if="!srhCollapse" style="width:0;height:0;"></div>
        </transition>
        <div class="header-srh__bg"></div>
        <input :disabled="srhActive" v-model="srhKeyWrd" @keyup.enter="srhSubmit" class="header-srh__inp" type="text"/>
        <button :disabled="srhActive" type="button" @click="srhSubmit" class="header-srh__icn">
          <icon-svg name="search"></icon-svg>
        </button>
      </div>
      <span class="header-fn" title="列表">
        <icon-svg name="list"></icon-svg>
      </span>
      <span @click.stop="showMessage" :class="{'is-hasMsg':newMessage}" class="header-fn" title="消息">
        <icon-svg name="chatboxes"></icon-svg>
      </span>
      <span @click.stop="showControl" class="header-fn" title="设置">
        <icon-svg name="setting"></icon-svg>
      </span>
      <router-link :to="{name: 'help'}" class="header-fn" title="帮助">
        <icon-svg name="help-circle"></icon-svg>
      </router-link>
      <span class="header-fn" title="组织机构">
        <icon-svg name="apps"></icon-svg>
      </span>
      <el-dropdown class="header-userlnk" :show-timeout="0" placement="bottom">
        <span class="el-dropdown-link header-avatar">
          <img src="~@/assets/images/avatar.png" :alt="userName">{{userName}}
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="updatePasswordHandle()">修改密码</el-dropdown-item>
          <el-dropdown-item @click.native="logoutHandle()">退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>
<script>
import { clearLoginInfo } from '@/utils'
export default {
  data() {
    return {
      srhKeyWrd: "",
      srhActive: false,
      srhCollapse: true
    };
  },
  computed: {
    userId: {
      get() {
        return this.$store.state.user.id;
      },
      set(val) {
        this.$store.commit("user/updateId", val);
      }
    },
    userName: {
      get() {
        return this.$store.state.user.name;
      },
      set(val) {
        this.$store.commit("user/updateName", val);
      }
    },
    sideMessageShow: {
      get() {
        return this.$store.state.user.sideMessageShow;
      },
      set(val) {
        this.$store.commit("user/updateSideMessageShow", val);
      }
    },
    sideControlShow: {
      get() {
        return this.$store.state.user.sideControlShow;
      },
      set(val) {
        this.$store.commit("user/updateSideControlShow", val);
      }
    },
    newMessage: {
      get() {
        return this.$store.state.user.newMessage;
      }
    }
  },
  methods: {
    showMessage() {
      var that = this;
      if (this.sideControlShow) {
        this.sideControlShow = false;
      }
      this.sideMessageShow = true;
      function hideMessage(event) {
        event.stopPropagation();
        that.sideMessageShow = false;
        that.$root.$el.removeEventListener("click", hideMessage);
      }
      this.$root.$el.addEventListener("click", hideMessage);
      
    },
    showControl() {
      var that = this;
      if (this.sideMessageShow) {
        this.sideMessageShow = false
      }
      this.sideControlShow = true;
      function hideControl(event) {
        event.stopPropagation();
        that.sideControlShow = false;
        that.$root.$el.removeEventListener("click", hideControl);
      }
      this.$root.$el.addEventListener("click", hideControl);
      
    },
    srhSubmit() {
      if (this.srhKeyWrd === "") {
        this.srhCollapse = !this.srhCollapse;
      } else {
        this.srhCollapse = false;
        console.log("search!!!");
      }
    },
    logoutHandle() {
      // 退出
      this.$confirm(`确定进行[退出]操作?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http({
            url: this.$http.adornUrl('/sys/logout'),
            method: 'post',
            params: this.$http.adornParams({})
          }).then(({data}) =>{
            // this.$cookie.delete("token");
              // this.$router.push({ name: "login" });
              clearLoginInfo()
              this.$router.push({ name: 'login' })
          })
        })
        .catch(() => {});
    }
  }
};
</script>
