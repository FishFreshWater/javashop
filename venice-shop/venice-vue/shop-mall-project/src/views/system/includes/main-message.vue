<template>
  <div class="message" @click.stop="stopHideMsg">
    <div class="message__header">
      <span>未读消息</span>
      <span @click="hideMessage" class="message__close"><icon-svg name="close-circle"></icon-svg></span>
    </div>
    <div class="message__content">
      <div v-show="loading" class="message__loading">加载中...</div>
      <div v-if="!loading&&msgList.length===0" class="message__nores">
        暂无消息
      </div>
      <ul v-if="!loading&&msgList.length>0" class="message-list">
        <li v-for="message of msgList" v-bind:key="message.id">
          <div class="message-item">
            <p>
              <span class="message-item__cate">[{{message.state}}]</span>
              <span class="message-item__time">{{message.time}}</span>
            </p>
            <p class="message-item__desc">
              <span v-if="message.rank===1" class="message-item__mod is-rank1">重要</span>
              <span v-if="message.rank===2" class="message-item__mod is-rank2">提醒</span>
              {{message.title}}
            </p>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>
<script>
// import api from "@/api";
export default {
  data() {
    return {
      msgList: [],
      loading: false
    };
  },
  created() {
    // this.getUserMessage();
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
    newMessage: {
      get() {
        return this.$store.state.user.newMessage;
      },
      set(val) {
        this.$store.commit("user/updateNewMessage", val);
      }
    }
  },
  methods: {
    stopHideMsg() {
      console.log("阻止事件！");
    },
    hideMessage() {
      this.sideMessageShow = false;
    },
    // getUserMessage() {
    //   api.user.getUserMessage().then(({ data }) => {
    //     if (data && data.code === 0) {
    //       this.msgList = data.list;
    //       this.loading = false;
    //       this.newMessage = true;
    //     } else {
    //       this.newMessage = false;
    //     }
    //   });
    // }
  }
};
</script>
