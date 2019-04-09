<template>
  <div :class="{'is-collapse':sideMenuCollapse}" class="aside">
    <div class="aside-header">
      <img :class="{'is-collapse':sideMenuCollapse}" class="logo--aside" src="~@/assets/images/logo--white.png"/>
    </div>
    <div class="aside-main">
      <div class="aside-menu__wrap">
        <el-menu
          :class="{'is-collapse':sideMenuCollapse}" 
          :collapse="sideMenuCollapse" 
          class="aside-menu" 
          :collapse-transition="false"
          :default-active="menuActiveName || 'system/home/home.html'" 
          active-text-color="#108cee" 
          text-color="#e8ecf0" 
          background-color="#19233c"
          :unique-opened="unique_opened"
          :mode="mode"
          >
          <sub-menu
            v-for="(menu, index) in sideMenu"
            :key="index"
            :menu="menu"
            :dynamicMenuRoutes="dynamicMenuRoutes"
            >
          </sub-menu>
        </el-menu>
      </div>
    </div>
    <div class="aside-footer">
      <span class="aside-tgicn" @click="toggleMenuCollapse">
        <icon-svg v-show="sideMenuCollapse" name="fold-right"></icon-svg>
        <icon-svg v-show="!sideMenuCollapse" name="fold-left"></icon-svg>
      </span>
    </div>
  </div>
</template>
<script>
import SubMenu from "./main-aside-sub";
import { version } from 'element-ui';
export default {
  data() {
    return {
      dynamicMenuRoutes:[],
      unique_opened: true
    };
  },
  components: {
    SubMenu
  },
  computed: {
    sideMenu: {
      get() {
        return this.$store.state.common.sideMenu;
      },
      set(val) {
        this.$store.commit("common/updateSideMenu", val);
      }
    },
    sideMenuCollapse: {
      get() {
        return this.$store.state.common.sideMenuCollapse;
      },
      set(val) {
        this.$store.commit("common/updateSideMenuCollapse", val);
      }
    },
    menuActiveName: {
      get () { return this.$store.state.control.mainTabsActiveName },
      set (val) { this.$store.commit('control/SET_mainTabsActiveName', val) }
    },
    defaultActive() {
      // console.log(this.$route)
      return this.$route.name;
    },
    mode: {
      get() {
        return this.$store.state.control.mode;
      },
      set(val) {
        this.$store.commit("control/SET_mode", val);
      }
    }
  },
  created () {
    this.sideMenu = JSON.parse(sessionStorage.getItem('menuList') || '[]');
    this.dynamicMenuRoutes = JSON.parse(sessionStorage.getItem('dynamicMenuRoutes') || '[]');
  },
  methods: {
    toggleMenuCollapse() {
      this.sideMenuCollapse = !this.sideMenuCollapse;
    },
  }
};
</script>
