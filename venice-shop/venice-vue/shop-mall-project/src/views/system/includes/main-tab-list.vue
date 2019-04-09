<template>
  <div class="l-tab-list">
    <el-tabs 
      v-model="mainTabsActiveName" 
      type="card" 
      closable 
      @tab-remove="removeTab"
      @tab-click="tab_click"
    >
    <el-tab-pane
      v-for="(item) in mainTabs"
      :key="item.menuId"
      :label="item.routeName"
      :name="item.name"
      :routeName="item.routeName"
    ></el-tab-pane>
    </el-tabs>
    <el-dropdown class="site-tabs__tools" size="mini">
      <el-button type="primary" size="mini">
        <i class="el-icon-arrow-down el-icon--right"></i>
      </el-button>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="tabsCloseCurrentHandle">关闭当前标签页</el-dropdown-item>
        <el-dropdown-item @click.native="tabsCloseOtherHandle">关闭其它标签页</el-dropdown-item>
        <el-dropdown-item @click.native="tabsCloseAllHandle">关闭全部标签页</el-dropdown-item>
        <el-dropdown-item @click.native="tabsRefreshCurrentHandle">刷新当前标签页</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  data() {
    return {
    }
  },
  computed:{
    defaultActive: {
      get() {
        return this.$route.name
      },
      set() {
        return this.$route.name
      }
    },
    mainTabsActiveName: {
      get () { 
        return this.$store.state.control.mainTabsActiveName;
      },
      set(val) {
        this.$store.commit('control/SET_mainTabsActiveName',val);
      }
    },
    mainTabs:{
      get() {
        return this.$store.state.control.mainTabs;
      },
      set(val) {
        this.$store.commit('control/SET_mainTabs',val);
      }
    }
  },
  methods: {
    removeTab(targetName) {
      console.log(targetName)
      let tabs = this.mainTabs;
      let activeName = this.mainTabsActiveName;
      if (activeName === targetName) {
        this.removeTabHandle(this.mainTabsActiveName)
      }
      this.mainTabsActiveName = activeName;
      this.mainTabs = tabs.filter(tab => tab.name !== targetName);
    },
    tab_click(route) {
      route.name && this.$router.push({ name: route.name });
    },
    // tabs, 删除tab
    removeTabHandle (tabName) {
      this.mainTabs = this.mainTabs.filter(item => item.name !== tabName)
      if (this.mainTabs.length >= 1) {
        // 当前选中tab被删除
        if (tabName === this.mainTabsActiveName) {
          var tab = this.mainTabs[this.mainTabs.length - 1];
          this.$router.push({ name: tab.name }, () => {
            this.mainTabsActiveName = this.$route.name
          })
        }
      } else {
        this.menuActiveName = ''
        this.$router.push({ name: 'system/home/home.html' })
      }
    },
     // tabs, 关闭当前
    tabsCloseCurrentHandle () {
      this.removeTabHandle(this.mainTabsActiveName)
    },
    // tabs, 关闭其它
    tabsCloseOtherHandle () {
      this.mainTabs = this.mainTabs.filter(item => item.name === this.mainTabsActiveName)
    },
    // tabs, 关闭全部
    tabsCloseAllHandle () {
      this.mainTabs = []
      this.menuActiveName = ''
      this.$router.push({ name: 'system/home/home.html' })
    },
    // tabs, 刷新当前  原理：先删除当前，然后在重新加载，缺点 删除当前后会加载一次 相邻页面路由
    tabsRefreshCurrentHandle () {
      var tab = this.$route;
      console.log(tab)
      this.removeTabHandle(tab.name)
      this.$nextTick(() => {
        this.$router.push({ name: tab.name })
      })
    }
  }
};
</script>

<style>
.l-tab-list{
  position: relative;
  background-color: #fff;
  border-radius: 3px;
  box-shadow: 0 0 1px 1px rgba(0, 0, 0, 0.01);
  -webkit-box-shadow:0 0 1px 1px rgba(0, 0, 0, 0.01);
}
.l-tab-list .el-tabs--card>.el-tabs__header .el-tabs__item{
  border: none;
  height: 36px;
  line-height: 36px;
}
.l-tab-list .el-tabs--card>.el-tabs__header{
  border: none !important;
}
.l-tab-list .el-tabs--card>.el-tabs__header .el-tabs__nav{
  border: none !important;
}
.l-tab-list .el-tabs--card>.el-tabs__header .el-tabs__item{
  border: none !important;

}
.l-tab-list .el-tabs__nav-next, .el-tabs__nav-prev{
  line-height: 38px;
}
.l-tab-list .el-tabs--card{
  padding-right: 60px;
}
.l-tab-list .site-tabs__tools.el-dropdown{
  position: absolute;
  top: 2px;
  right: 8px;
  z-index: 9;
  width: 42px;
  text-align: center;
  cursor: pointer;
  -webkit-transition: right .3s;
  transition: right .3s;
}
</style>