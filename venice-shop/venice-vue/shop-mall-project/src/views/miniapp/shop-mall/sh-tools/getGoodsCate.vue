<template>
  <el-dialog
    :modal="false"
    title="选择商品分类"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <p class="text-danger">点击树形分类菜单选择商品，注意只能选择二级分类</p>
    <el-tree 
      style="margin-top: 10px;" 
      :data="cateTree"
      v-loading="dataListLoading"
      :props="cateProps">
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <span class="label">{{ node.label }}</span>
        <span class="opera" v-if="node.isLeaf">
          <el-button
            type="text"
            size="mini"
            @click="() => slcTreeNode(data)">
            选择
          </el-button>
        </span>
      </span>
    </el-tree>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" @click.native="visible = false">关闭</el-button>
    </span>
  </el-dialog>
</template>
<style scoped lang="scss">
.custom-tree-node{
  .opera{
    padding: 0 40px;
  }
}
</style>
<script>
import { treeDataTranslate } from "@/utils";
export default {
  data () {
    return {
      visible: false,
      cateTree: [],
      cateProps: {
        children: "children",
        label: "name"
      },
      dataListLoading: false
    }
  },
  methods: {
    init(){
      this.visible = true
      this.getCateList()
    },
    getCateList(){
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl("/miniapp/shgoodscategory/list"),
        method: "get",
        params: this.$http.adornParams({})
      }).then(({ data }) => {
        if (data && data.code === 0) {
          // 转换树形数据
          this.cateTree = treeDataTranslate(data.items);
        } else {
          this.cateTree = [];
        }
        this.dataListLoading = false;
      });
    },
    slcTreeNode(data){
      this.$emit('slcedGoodsCate',data)
      this.visible = false
    }
  }
}
</script>