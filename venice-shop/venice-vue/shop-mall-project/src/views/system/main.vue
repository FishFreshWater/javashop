<template>
  <div 
    v-loading.fullscreen.lock="loading"
    element-loading-text="拼命加载中"
    class="l-container"
    :class="[
      {
        'aui-wrapper--center': wrapperCenter,
        'aui-wrapper--headerSkin': headerSkin,
        'aui-aside--top': asideTop,
        'aui-aside--skin': asideSkin,
        'aui-sideMenuCollapse':sideMenuCollapse
      }
    ]"
  >
    <div class="l-aside" :style="{width: asideWidth+'px'}" v-if="!showAsideTop">
      <Aside/>
    </div>
    <div class="l-main" :style="{marginLeft: asideWidth+'px'}">
      <div class="l-header">
        <Header/>
      </div>
      <!-- 面包屑 -->
      <div class="l-breadcrumb" v-if="!mainType">
        <Breadcrumb />
      </div>
      <div class="l-aside-top" v-if="showAsideTop">
        <Aside />
      </div>
      <!-- tabs -->
      <div class="l-tablist" v-if="mainType">
        <Tablist />
      </div>  
      <div class="l-mainbody">
        <div class="l-content">
          <!-- <keep-alive><router-view></router-view></keep-alive> -->
          <router-view/>
        </div>
      </div>
      <div class="l-message" :class="{'is-show':sideMessageShow}">
        <Message />
      </div>
      <!-- 控制 -->
      <div class="l-control" :class="{'is-show-control': sideControlShow}">
        <Control  
          v-on:wrapperCenterMethod="wrapperCenterMethod" 
          v-on:headerSkinMethod="headerSkinMethod"
          v-on:asideTopMethod="asideTopMethod"
          v-on:asideSkinMethod="asideSkinMethod"
          v-on:mainTypeMethod="mainTypeMethod"
        />
      </div>
    </div>
    <!-- 通用遮照 -->
    <!-- <div class="l-mask" v-show="mainLoadNum">
      <div class="main-loading">正在读取数据，请稍候...</div>
    </div> -->
  </div>
</template>
<script>
import Vue from "vue";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import "@/style/main.scss";
import Aside from "./includes/main-aside";
import Header from "./includes/main-header";
import Breadcrumb from "./includes/main-breadcrumb";
import Tablist from "./includes/main-tab-list";
import Message from "./includes/main-message";
import Control from "./includes/main-control";
import "@/icons";
Vue.use(ElementUI);

export default {
  data() {
    return {
      showTabs: false, //main-tabs 标签页
      showAsideTop: false, //top至头部
      loading: true
    };
  },
  created() {
    this.getUserInfo();
    this.getSideMenu();
  },
  beforeRouteUpdate(to, from, next) {
    //面包屑
    this.setBreadcrumb(to);
    next();
  },
  watch: {
    breadCrumbsMap: function() {
      this.setBreadcrumb(this.$router.currentRoute);
    }
  },
  components: {
    Aside,
    Header,
    Breadcrumb,
    Message,
    Control,
    Tablist
  },
  computed: {
    asideWidth: function() {
      return this.sideMenuCollapse ? 64 : 200;
    },
    mainLoadNum: {
      get() {
        return this.$store.state.common.mainLoadNum;
      },
      set(val) {
        this.$store.commit("common/updateMainLoadNum", val);
      }
    },
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
    sideMenuCollapse: {
      get() {
        return this.$store.state.common.sideMenuCollapse;
      }
    },
    sideMessageShow: {
      get() {
        return this.$store.state.user.sideMessageShow;
      }
    },
    sideControlShow: {
      get () {
        return this.$store.state.user.sideControlShow;
      }
    },
    breadCrumbsMap: {
      get() {
        return this.$store.state.common.breadCrumbsMap;
      },
      set(val) {
        this.$store.commit("common/updateBreadCrumbsMap", val);
      }
    },
    breadCrumbsTitles: {
      get() {
        return this.$store.state.common.breadCrumbsTitles;
      },
      set(val) {
        this.$store.commit("common/updateBreadCrumbsTitles", val);
      }
    },
    wrapperCenter:{
      get() {
        return this.$store.state.control.wrapperCenter;
      },
      set(val) {
        this.$store.commit('control/SET_wrapperCenter',val);
      }
    },
    headerSkin:{
      get() {
        return this.$store.state.control.headerSkin;
      },
      set(val) {
        this.$store.commit('control/SET_headerSkin',val);
      }
    },
    asideTop:{
      get() {
        return this.$store.state.control.asideTop;
      },
      set(val) {
        this.$store.commit('control/SET_asideTop',val);
        if(val) {
          this.$store.commit('control/SET_mode','horizontal');
        }else{
          this.$store.commit('control/SET_mode','vertical');
        }
      }
    },
    asideSkin: {
      get() {
        return this.$store.state.control.asideSkin;
      },
      set(val) {
        this.$store.commit('control/SET_asideSkin',val);
      }
    },
    mainType: {
      get() {
        return this.$store.state.control.mainType;
      },
      set(val) {
        this.$store.commit('control/SET_mainType',val);
      }
    }
  },
  methods: {
    setBreadcrumb(route) {
      let arr = [];
      let maps = this.breadCrumbsMap;
      if (!maps[route.meta.menuId]) return; //不存在 return
      var obj = maps[route.meta.menuId];
      for (let i = 0; i < 6; i++) {
        arr.push(obj);
        if (obj["parentId"] == undefined) break;
        else {
          //此处便利maps 找到相同的id==parentID 的类 并且改变obj
          obj = maps[obj["parentId"]];
        }
      }
      this.breadCrumbsTitles = arr.reverse();
    },
    getUserInfo() {
      // 获取用户信息
      this.$http({
        url: this.$http.adornUrl('/sys/user/info'),
        method: 'get',
        params: this.$http.adornParams()
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.loading = false
          this.userId = data.user.userId
          this.userName = data.user.username
        }
      })
    },
    getSideMenu() {
      // 获取侧边菜单栏
      this.$http({
        url: this.$http.adornUrl('/sys/menu/nav'),
        method: 'get',
        data: this.$http.adornParams()
      }).then(({data})=> {
        if (data && data.code === 0) {
          this.createBreadCrumbsMap(data.menuList);
        }
      })
    },
    createBreadCrumbsMap(menu) {
      let obj = {};
      function walkList(menu, parentId) {
        for (let i = 0; i < menu.length; i++) {
          if (menu[i].id) {
            obj[menu[i].id + ""] = {
              flag: menu[i].name ? "name" : "id",
              id: menu[i].id,
              name: menu[i].name,
              icon: menu[i].icon,
              parentId: parentId,
              url:menu[i].url
            };
          }
          if (menu[i].list && menu[i].list.length > 0) {
            walkList(menu[i].list, menu[i].id);
          }
        }
      }
      walkList(menu, undefined);
      //console.log("面包屑Maps", obj);
      this.breadCrumbsMap = obj;
    },
    //子组件传值↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    wrapperCenterMethod(val) {
       this.$store.commit('control/SET_wrapperCenter',val);
    },
    headerSkinMethod(val) {
      this.$store.commit('control/SET_headerSkin',val);
    },
    asideTopMethod(val) {
      this.$store.commit("common/updateSideMenuCollapse", false);
      this.$store.commit('control/SET_asideTop',val);
      this.showAsideTop = val;
      location.reload()
    },
    asideSkinMethod(val) {
      this.$store.commit('control/SET_asideSkin',val);
    },
    mainTypeMethod(val) {
      this.$store.commit('control/SET_mainType',val);
    }
    //子组件传值↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
  }
};
</script>
