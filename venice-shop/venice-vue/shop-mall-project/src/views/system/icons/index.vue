<template>
  <div class="content" style="position: relative;">
    <div class="content-header"><span class="content-header__cap">图标集合</span></div>
    <div class="iconWrp clearfix">
      <div @click.stop="hitIcon(item)" v-for="item in iconList" v-bind:key="item" class="item">
        <icon-svg :name="item"></icon-svg>
        <p>{{item}}</p>
      </div>
    </div>
    <div @click.stop="clickStop" v-show="showCopy" class="copyDiv">
      <el-input @focus="copyIcons" ref="copyInp" style="width:100%;" placeholder="点击按钮复制" v-model="iconHtml" class="input-with-select">
        <div slot="append" @click.stop="copyIcons" style="cursor: pointer;width: 100%;height: 100%;">
          <icon-svg name="copy" style="height: 24px;width: 24px;"></icon-svg>
        </div>
      </el-input>
    </div>
  </div>
</template>
<script>
import Icon from "@/icons";

export default {
  data() {
    return {
      iconList: [],
      showCopy: false,
      iconHtml: ""
    };
  },
  created() {
    this.iconList = Icon.getNameList();
  },
  methods: {
    clickStop() {
      console.log("阻止事件冒泡！");
    },
    hitIcon(name) {
      var that = this;
      this.showCopy = true;
      this.iconHtml = `<icon-svg name="${name}"></icon-svg>`;
      function hideCopDiv(event) {
        event.stopPropagation();
        that.showCopy = false;
        that.$root.$el.removeEventListener("click", hideCopDiv);
      }
      this.$root.$el.addEventListener("click", hideCopDiv);
    },
    copyIcons() {
      this.$refs["copyInp"].select();
      if (!document.execCommand("copy")) {
        this.$message({
          message: "复制失败，请手动复制！",
          type: "warning"
        });
      } else {
        this.$message({
          message: "复制成功！",
          type: "success"
        });
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.copyDiv {
  box-shadow: 0 0 3px 0px rgba(0, 0, 0, 0.2);
  width: 500px;
  position: fixed;
  top: 100px;
  left: 50%;
  margin-left: -250px;
}
.iconWrp {
  padding: 30px;
  width: 100%;
}
.item {
  height: 86px;
  width: 86px;
  padding-top: 15px;
  float: left;
  text-align: center;
  cursor: pointer;
  &:hover {
    background: #f5f5f5;
  }
  .icon-svg {
    height: 36px;
    width: 36px;
  }
  p {
    line-height: 14px;
    padding: 0 6px;
  }
}
</style>
