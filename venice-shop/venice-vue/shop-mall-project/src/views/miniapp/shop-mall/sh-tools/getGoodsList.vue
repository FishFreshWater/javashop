<template>
  <el-dialog
    title="选择商品"
    :modal="false"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <p class="text-danger">点击按钮添加商品</p>
    <el-form style="margin-top:10px;" size="small" :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.name_like" placeholder="请输入关键字" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getDataList()">查询</el-button>
        <el-button type="primary" @click="addGoods">批量添加</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      size="small"
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="id"
        header-align="center"
        align="center"
        label="ID">
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
        prop="retailPrice"
        header-align="center"
        align="center"
        label="商品价格">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addGood(scope.$index)">添加</el-button>
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
      已选中商品：
      <span v-for="obj in slcedObj" :key="obj.id">
        {{obj.name}}
        &nbsp;&nbsp;
        <em style="color:#f00;font-size:13px;cursor:pointer;" @click="deleteGood(obj.id)">删除</em>
        &nbsp;&nbsp;
      </span>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" @click="setSlced" type="primary">提交</el-button>
      <el-button size="small" @click="clearAll" type="primary">清除</el-button>
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
        name_like: "",
        pageIndex: 1,
        pageSize: 10
      },
      dataListSelections: [],
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
    //点击添加
    addGood(index){
      var good=this.dataList[index];
      if(!this.slcedObj[good.id]){
        this.$set(this.slcedObj,good.id,{
          id: good.id,
          name: good.name,
          shGoodsCategoryName: good.shGoodsCategoryName,
          unitPrice: good.unitPrice
        })
      }
    },
    deleteGood(id){
      if(this.slcedObj[id]) this.$delete(this.slcedObj,id);
    },
    clearAll(){
      this.slcedObj={};
    },
    //批量添加
    addGoods(){
      for(var i=0,data=this.dataListSelections;i<data.length;i++){
        var good = data[i]
        if(!this.slcedObj[good.id]){
          this.$set(this.slcedObj,good.id,{
            id: good.id,
            name: good.name,
            shGoodsCategoryName: good.shGoodsCategoryName,
            unitPrice: good.unitPrice
          })
        }
      }
    },
    //提交
    setSlced(){
      var data=[];
      for(var id in this.slcedObj){
        var good=this.slcedObj[id];
        data.push({
          id: good.id,
          name: good.name,
          shGoodsCategoryName: good.shGoodsCategoryName,
          unitPrice: good.unitPrice
        })
      }
      this.$emit("slcedGoods",data);
      this.visible=false;
    },
    getDataList(){
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl("/miniapp/shgoods/list"),
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
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val;
    }
  }
}
</script>