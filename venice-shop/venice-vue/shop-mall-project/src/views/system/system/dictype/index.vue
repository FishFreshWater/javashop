<template>
  <div class="content pad10">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input size="small" v-model="dataForm.name" placeholder="字典类型名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" :loading="isLoading" @click="getDataList()">查询</el-button>
        <el-button
          size="small"
          v-if="isAuth('sys:dictype:save')"
          type="primary"
          @click="addOrUpdateHandle()"
        >新增</el-button>
        <el-button
          size="small"
          v-if="isAuth('sys:dictype:delete')"
          type="danger"
          @click="deleteHandle()"
          :disabled="dataListSelections.length <= 0"
        >批量删除</el-button>
      </el-form-item>
    </el-form>
    <el-table
      v-loading="dataListLoading"
      :data="dataList"
      border
      @selection-change="selectionChangeHandle"
      style="width: 100%;"
    >
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="code" header-align="center" align="center" label="编码">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="lookDictiem(scope.row.code)">{{scope.row.code}}</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="name" header-align="center" align="center" label="字典类型名称"></el-table-column>
      <el-table-column prop="orderNum" header-align="center" align="center" label="排序" width="50"></el-table-column>
      <el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
        <template slot-scope="scope">
          <el-button
            type="text"
            v-if="isAuth('sys:dictype:update')"
            size="small"
            @click="addOrUpdateHandle(scope.row.id)"
          >修改</el-button>
          <el-button
            type="text"
            v-if="isAuth('sys:dictype:delete')"
            size="small"
            @click="deleteHandle(scope.row.id)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      class="mar-t10"
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper"
    ></el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
import AddOrUpdate from "./add-or-update";
import { compare } from '@/utils' //排序
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
      dataListSelections: [],
      addOrUpdateVisible: false,
      dataListLoading: false,
      isLoading: false
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
      this.isLoading = true;
      this.$http({
        url: this.$http.adornUrl("/sys/dictype/list"),
        method: "get",
        params: this.$http.adornParams({
          'page': this.pageIndex,
          'limit': this.pageSize,
          'name_like': this.dataForm.name
        })
      }).then(({ data }) => {
        if (data && data.code === 0) {
          let _list = data.page.list.sort(compare('orderNum'));
          this.dataList = _list;
          this.totalPage = data.page.totalCount;
        } else {
          this.dataList = [];
          this.totalPage = 0;
        }
        this.dataListLoading = false;
        this.isLoading = false;
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
      )
        .then(() => {
          this.$http({
            url: this.$http.adornUrl("/sys/dictype/delete"),
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
        })
        .catch(() => {});
    },
    //点击字典编码
    lookDictiem (val) {
      localStorage.setItem('dicitemCode',val)
      this.$router.replace({ name: "dicitem" });
    }
  }
};
</script>
