<template>
  <el-dialog title="选择链接" :close-on-click-modal="false" :visible.sync="visible">
    <el-tabs :value="tabValue" type="card" @tab-click="handleClick">
      <el-tab-pane label="商品分类">
        <el-tree style="margin-top: 10px;" :data="cateTree" v-loading="cateLoading" :props="cateProps">
          <span class="custom-tree-node" slot-scope="{ node, data }">
            <span class="label">{{ node.label }}</span>
            <span class="opera" v-if="node.isLeaf">
              <el-button type="text" size="mini" @click="() => slcTreeNode(data)">
                选择
              </el-button>
            </span>
          </span>
        </el-tree>
      </el-tab-pane>
      <el-tab-pane label="商品品牌">
        <el-input size="mini" placeholder="品牌关键字" v-model="brandForm.key">
          <el-button @click.native="getBrandData" slot="append">搜索</el-button>
        </el-input>
        <el-table :data="brandList" border size="small" v-loading="brandLoading" style="margin-top: 10px;width: 100%;">
          <el-table-column prop="name" header-align="center" align="center" label="名字">
          </el-table-column>
          <el-table-column prop="floorPrice" header-align="center" align="center" label="最低价格">
          </el-table-column>
          <el-table-column header-align="center" align="center" label="新品">
            <template slot-scope="scope">
              {{scope.row.isNew==0?"是":"否"}}
            </template>
          </el-table-column>
          <el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="slcBrand(scope.row.id)">选择</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="商品专题">
        <el-input size="mini" placeholder="专题关键字" v-model="topicForm.key">
          <el-button @click.native="getTopicData" slot="append">搜索</el-button>
        </el-input>
        <el-table size="small" :data="topicList" border v-loading="topicLoading" style="margin-top: 10px;width: 100%;">
          <el-table-column prop="title" header-align="center" align="center" label="活动主题">
          </el-table-column>
          <el-table-column prop="priceInfo" header-align="center" align="center" label="活动价格">
          </el-table-column>
          <el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="slcTopic(scope.row.id)">选择</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="单个商品">
        <el-input size="mini" placeholder="商品关键字" v-model="goodsForm.key">
          <el-button @click.native="getGoodsData" slot="append">搜索</el-button>
        </el-input>
        <el-table
        :data="goodsList"
        border
        size="small"
        v-loading="goodsLoading"
        style="margin-top: 10px;width: 100%;">
        <el-table-column
          type="selection"
          header-align="center"
          align="center"
          width="50">
        </el-table-column>
        <el-table-column
          prop="name"
          header-align="center"
          align="center"
          label="名字">
        </el-table-column>
        <el-table-column
          prop="shGoodsCategoryName"
          header-align="center"
          align="center"
          label="商品所属分类">
        </el-table-column>
        <el-table-column
          fixed="right"
          header-align="center"
          align="center"
          width="150"
          label="操作">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="slcGood(scope.row.id)">选择</el-button>
          </template>
        </el-table-column>
      </el-table>
      </el-tab-pane>
    </el-tabs>
    <span slot="footer" class="dialog-footer">
      <el-button @click.native="visible = false">关闭</el-button>
    </span>
  </el-dialog>
</template>
<style scoped lang="scss">
.custom-tree-node {
  .opera {
    padding: 0 40px;
  }
}
</style>
<script>
import { treeDataTranslate } from "@/utils";
export default {
  data() {
    return {
      tabValue: 0,

      visible: false,

      cateLoading: false,
      cateProps: {
        children: "children",
        label: "name"
      },
      cateTree: [],
      cateLd: false,

      brandLoading: false,
      brandList: [],
      brandForm: {
        key: ""
      },

      topicLoading: false,
      topicList: [],
      topicForm: {
        key: ""
      },

      goodsLoading: false,
      goodsList: [],
      goodsForm: {
        key: ""
      }
    };
  },
  methods: {
    init() {
      this.visible = true;
      this.getCateList();
    },
    handleClick(tab, event) {
      if(tab.index==0){
        if(this.cateTree.length==0){
          this.getCateList();
        }
      }
      if(tab.index==1){
        if(this.brandList.length==0){
          this.getBrandData();
        }
      }
      if(tab.index==2){
        if(this.topicList.length==0){
          this.getTopicData();
        }
      }
      if(tab.index==3){
        if(this.goodsList.length==0){
          this.getGoodsData();
        }
      }
    },
    getCateList() {
      this.cateLoading = true;
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
        this.cateLoading = false;
      });
    },
    slcTreeNode(data) {
      this.$emit("slclink", "pages/category/category?id=" + data.id);
      this.visible = false;
    },
    // 品牌
    getBrandData() {
      this.brandLoading = true;
      this.$http({
        url: this.$http.adornUrl("/miniapp/shbrand/list"),
        method: "get",
        params: this.$http.adornParams({
          page: 1,
          limit: 10,
          key: this.brandForm.key
        })
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.brandList = data.page.content;
        } else {
          this.dataList = [];
        }
        this.brandLoading = false;
      });
    },
    slcBrand(id) {
      this.$emit("slclink", "pages/brandDetail/brandDetail?id=" + id);
      this.visible = false;
    },
    // 专题
    getTopicData() {
      this.topicLoading = true;
      this.$http({
        url: this.$http.adornUrl("/miniapp/shtopic/list"),
        method: "get",
        params: this.$http.adornParams({
          page: 1,
          limit: 1,
          key: this.topicForm.key
        })
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.topicList = data.page.content;
        } else {
          this.topicList = [];
        }
        this.topicLoading = false;
      });
    },
    slcTopic(id) {
      this.$emit("slclink", "pages/topicDetail/topicDetail?id=" + id);
      this.visible = false;
    },
    // 商品
    getGoodsData() {
      this.goodsLoading = true;
      var params = {
        pageIndex: 0,
        pageSize: 10,
        name_like: this.goodsForm.key
      };
      this.$http({
        url: this.$http.adornUrl("/miniapp/shgoods/list"),
        method: "get",
        params: this.$http.adornParams(params)
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.goodsList = data.page.content;
        } else {
          this.goodsList = [];
        }
        this.goodsLoading = false;
      });
    },
    slcGood(id) {
      this.$emit("slclink", "pages/goods/goods?id=" + id);
      this.visible = false;
    }
  }
};
</script>