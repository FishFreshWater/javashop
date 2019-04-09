<template>
  <el-dialog
    :modal="false"
    title="选择品牌"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <p class="text-danger">点击按钮选择品牌</p>
    <el-form size="small" :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="请输入关键字" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getDataList()">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      size="small"
      v-loading="dataListLoading"
      style="width: 100%;">
      <el-table-column
        prop="name"
        header-align="center"
        align="center"
        label="名字">
      </el-table-column>
      <el-table-column
        prop="thumbnailUrl"
        header-align="center"
        align="center"
        label="缩略图">
        <template slot-scope="scope">
          <img style="max-width: 50px;max-height:50px;" :src="scope.row.thumbnailUrl">
        </template>
      </el-table-column>
      <el-table-column
        prop="floorPrice"
        header-align="center"
        align="center"
        label="最低价格">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addBrand(scope.$index)">选择</el-button>
        </template>
      </el-table-column>
    </el-table>
      <el-pagination
        style="margin-top: 10px;"
        @size-change="sizeChangeHandle"
        @current-change="currentChangeHandle"
        :current-page="dataForm.pageIndex"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="dataForm.pageSize"
        :total="totalSize"
        layout="total, sizes, prev, pager, next, jumper">
      </el-pagination>
    <div style="margin: 10px 0;">
      已选中品牌：{{slcedObj.name}}
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" @click="setSlced" type="primary">提交</el-button>
      <el-button size="small" @click.native="visible = false">关闭</el-button>
    </span>
  </el-dialog>
</template>
<style scoped lang="scss">
</style>
<script>
export default {
  data () {
    return {
      visible: false,
      dataForm:{
        key: "",
        pageIndex: 1,
        pageSize: 10
      },
      totalSize: 0,
      dataList:[],
      slcedObj:{},
      dataListLoading: false
    }
  },
  methods: {
    init(){
      this.visible = true
      this.getDataList();
    },
    addBrand(index){
      this.slcedObj=this.dataList[index];
    },
    setSlced(){
      this.$emit("slcedBrand",this.slcedObj);
      this.visible=false;
    },
    getDataList(){
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl("/miniapp/shbrand/list"),
        method: "get",
        params: this.$http.adornParams(this.dataForm)
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.dataList = data.page.list;
          this.totalSize = data.page.totalSize;
        } else {
          this.dataList = [];
          this.totalSize = 0;
        }
        this.dataListLoading = false;
      });
    },
    // 每页数
    sizeChangeHandle(val) {
      this.dataForm.pageSize = val;
      this.dataForm.pageIndex = 1;
      this.getDataList();
    },
    // 当前页
    currentChangeHandle(val) {
      this.dataForm.pageIndex = val;
      this.getDataList();
    }
  }
}
</script>