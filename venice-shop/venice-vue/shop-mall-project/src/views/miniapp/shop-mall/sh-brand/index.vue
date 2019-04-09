<template>
  <div class="mod-config content pad10">
    <el-form size="small" :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input size="small" v-model="dataForm.name" placeholder="名字" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button size="small" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button size="small" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
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
        prop="name"
        header-align="center"
        align="center"
        label="名字">
      </el-table-column>
      <el-table-column
        header-align="center"
        align="center"
        label="首页品牌图片">
        <template slot-scope="scope">
          <img style="max-width: 50px;max-height:50px;" :src="scope.row.thumbnailUrl">
        </template>
        <template slot-scope="scope">
          <img  :src="scope.row.thumbnailUrl" alt="" style="width: 50px;height: 50px">
        </template>
      </el-table-column>
      <el-table-column
        prop="simpleDesc"
        header-align="center"
        align="center"
        label="内容">
      </el-table-column>
      <el-table-column
        header-align="center"
        align="center"
        label="品牌详情页头图">
        <template slot-scope="scope">
          <img style="max-width: 50px;max-height:50px;" :src="scope.row.picUrl">
        </template>
        <template slot-scope="scope">
          <img  :src="scope.row.picUrl" alt="" style="width: 50px;height: 50px">
        </template>
      </el-table-column>
      <el-table-column
        prop="sortOrder"
        header-align="center"
        align="center"
        label="排序">
      </el-table-column>
      <el-table-column
        header-align="center"
        align="center"
        label="是否启用">
        <template slot-scope="scope">
          {{scope.row.isShow==1?"启用":"不启用"}}
        </template>
      </el-table-column>
      <el-table-column
        prop="floorPrice"
        header-align="center"
        align="center"
        label="最低价格">
      </el-table-column>
      <el-table-column
        header-align="center"
        align="center"
        label="新品">
        <template slot-scope="scope">
          {{scope.row.isNew==1?"是":"否"}}
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      style="margin-top: 10px;"
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
import AddOrUpdate from "./add-or-update";
export default {
  data() {
    return {
      dataForm: {
        name: ""
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false
    };
  },
  components: {
    AddOrUpdate
  },
  created() {
    this.getDataList();
  },
  methods: {
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl("/miniapp/shbrand/list"),
        method: "get",
        params: this.$http.adornParams({
          pageIndex: this.pageIndex,
          pageSize: this.pageSize,
          name_like: this.dataForm.name
        })
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.dataList = data.page.list;
          this.totalPage = data.page.totalSize;
        } else {
          this.dataList = [];
          this.totalPage = 0;
        }
        this.dataListLoading = false;
      });
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDataList();
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getDataList();
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val;
    },
    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true;
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id);
      });
    },
    // 删除
    deleteHandle(id) {
      var ids = id
        ? [id]
        : this.dataListSelections.map(item => {
            return item.id;
          });
      this.$confirm(
        `确定对[id=${ids.join(",")}]进行[${id ? "删除" : "批量删除"}]操作?`,
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      ).then(() => {
        this.$http({
          url: this.$http.adornUrl("/miniapp/shbrand/delete"),
          method: "post",
          data: this.$http.adornData(ids, false)
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message({
              message: "操作成功",
              type: "success",
              duration: 1500,
              onClose: () => {
                this.getDataList();
              }
            });
          } else {
            this.$message.error(data.msg);
          }
        });
      });
    }
  }
};
</script>
