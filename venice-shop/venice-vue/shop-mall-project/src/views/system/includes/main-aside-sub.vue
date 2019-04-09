<template>
    <el-submenu
      v-if="menu.list && menu.list.length >= 1"
      :index="menu.id + ''"
      :key="menu.menuId + ''"
    >
      <template slot="title">
        <icon-svg :name="menu.icon || ''" class="aside__menuicn"></icon-svg>
        <span>{{ menu.name }}</span>
      </template>
      <sub-menu
        v-for="(item,index) in menu.list"
        :key="index"
        :menu="item"
        :dynamicMenuRoutes="dynamicMenuRoutes"
      >></sub-menu>
    </el-submenu>
    <el-menu-item v-else :index="menu.url + ''" @click="gotoRouteHandle(menu)">
      <icon-svg :name="menu.icon || ''" class="aside__menuicn"></icon-svg>
      <span>{{ menu.name }}</span>
    </el-menu-item>
</template>
<script>
import SubMenu from "./main-aside-sub";
export default {
  name: "sub-menu",
  data() {
    return {
    };
  },
  props: {
    menu: {
      type: Object,
      required: true
    },
    dynamicMenuRoutes: {
      type: Array,
      required: true
    }
  },
  components: {
    SubMenu
  },
  computed: {
    mainTabs: {
      get() {
        return this.$store.state.control.mainTabs;
      },
      set(val) {
        this.$store.commit("control/SET_mainTabs", val);
      }
    },
    mainTabsActiveName: {
      get() {
        return this.$store.state.control.mainTabsActiveName;
      },
      set(val) {
        this.$store.commit("control/SET_mainTabsActiveName", val);
      }
    }
  },
  watch: {
    $route: "routeHandle"
  },
  created() {
    this.routeHandle(this.$route);
  },
  methods: {
    // 通过menuId与动态(菜单)路由进行匹配跳转至指定路由
    gotoRouteHandle(menu) {
      var route = this.dynamicMenuRoutes.filter(
        item => item.meta.menuId === menu.id
      );
      if (route.length >= 1) {
        this.$router.push({ name: route[0].name });
      }
    },
    routeHandle(route) {
      let tab = this.mainTabs.filter(item => item.name === route.name)[0];
      if (!tab) {
        tab = {
          menuId: route.menuId || route.name,
          name: route.name,
          routeName: route.meta.title
        };
        this.mainTabs = this.mainTabs.concat(tab);
      }
      this.mainTabsActiveName = tab.name;
    }
  }
};
</script>
