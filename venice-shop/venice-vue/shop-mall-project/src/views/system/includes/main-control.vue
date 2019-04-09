<template>
  <div class="control" @click.stop="stopHideControl">
    <el-tabs v-model="activeName" :stretch="true">
      <el-tab-pane label="Layout" name="layout">
        <dl class="aui-control__setting">
          <dt>Wrapper</dt>
          <dd><el-checkbox @change="ClickwrapperCenter" v-model="wrapperCenter">Center 居中</el-checkbox></dd>
        </dl>
        <dl class="aui-control__setting">
          <dt>Header</dt>
          <dd><el-checkbox @change="ClickheaderSkin" v-model="headerSkin">Colorful 鲜艳</el-checkbox></dd>
        </dl>
        <dl class="aui-control__setting">
          <dt>Aside</dt>
          <dd><el-checkbox @change="clickasideSkin" v-model="asideSkin">Dark 鲜艳</el-checkbox></dd>
          <dd><el-checkbox @change="clickasideTop" v-model="asideTop">Top 至头部</el-checkbox></dd>
        </dl>
        <dl class="aui-control__setting">
          <dt>Main</dt>
          <dd><el-checkbox @change="clickmainType" v-model="mainType">Tabs 标签页</el-checkbox></dd>
        </dl>
      </el-tab-pane>
      <el-tab-pane label="Skins" name="skins">
        <dl class="aui-control__setting">
          <dt>Skins</dt>
          <dd v-for="item in skinList" :key="item.name">
            <el-radio v-model="skin" :label="item.name">
              <span class="t-capitalize">{{ item.name }}</span> {{ item.remark }}
            </el-radio>
          </dd>
        </dl>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { toggleClass } from '@/utils/index.js'
export default {
  data () {
    return {
      activeName: 'layout',
      skinList: [
        { name: 'blue'     , color: '#3E8EF7', remark: '蓝色' },
        { name: 'orange'   , color: '#EB6709', remark: '橙色' },
        { name: 'green'    , color: '#11C26D', remark: '绿色' },
        { name: 'brown'    , color: '#997B71', remark: '棕色' },
        { name: 'cyan'     , color: '#0BB2D4', remark: '青色' },
        { name: 'gray'     , color: '#757575', remark: '灰色' },
        { name: 'indigo'   , color: '#667AFA', remark: '靛青色' },
        { name: 'pink'     , color: '#F74584', remark: '粉红色' },
        { name: 'purple'   , color: '#9463F7', remark: '紫色' },
        { name: 'red'      , color: '#FF4C52', remark: '红色' },
        { name: 'turquoise', color: '#17B3A3', remark: '蓝绿色' },
        { name: 'yellow'   , color: '#FCB900', remark: '黄色' }
      ],
    }
  },
  mounted() {
    toggleClass(document.body, 'custom-' + this.skin);
  },
  computed: {
    skin:{
      get() {
        return this.$store.state.control.themecolor;
      },
      set(val) {
        this.$store.commit('control/SET_themecolor',val);
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
    asideTop: {
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
  watch: {
    skin: {
      handler() {
        toggleClass(document.body, 'custom-' + this.skin);
      }
    }
  },
  methods: {
    stopHideControl() {
      console.log("阻止事件！");
    },
    //居中
    ClickwrapperCenter(val) {
      this.$emit('wrapperCenterMethod',val);
    },
    //colorful鲜艳
    ClickheaderSkin(val) {
      this.$emit('headerSkinMethod',val);
    },
    //top至头部
    clickasideTop(val) {
      this.$emit('asideTopMethod',val);
    },
    //dark 鲜艳
    clickasideSkin(val) {
      this.$emit('asideSkinMethod',val);
    },
    // tabs 标签页
    clickmainType(val) {
      this.$emit('mainTypeMethod',val);
    },
  }
};
</script>

<style scoped>
</style>