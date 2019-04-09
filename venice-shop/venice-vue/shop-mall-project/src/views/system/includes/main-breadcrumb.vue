<template>
  <div class="crumbs clearfix">
    <template v-for="(item,index) of breadCrumbsTitles">
      <div class="crumb-item" v-bind:key="item.menuId">
        <span class="crumb-item__title" @click="linkHandle(item.routeName||'',item.routeParams||'')">{{item.name}}</span>
        <span class="crumb-item__sep" v-if="index<breadCrumbsTitles.length-1">/</span>
      </div>
    </template>
  </div>
</template>
<script>
export default {
  data() {
    return {};
  },
  computed: {
    breadCrumbsTitles: {
      get() {
        return this.$store.state.common.breadCrumbsTitles;
      },
      set(val) {
        this.$store.commit("common/updateBreadCrumbsTitles", val);
      }
    }
  },
  methods: {
    linkHandle(routeName, params) {
      // if (routeName) {
      //   this.$router.push({
      //     name: routeName,
      //     params: params || {}
      //   });
      // }
    }
  }
};
</script>
