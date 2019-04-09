<template>
  <div class="content pad10">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input size="small" v-model="dataForm.name" placeholder="用户名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" :loading="isLoading" @click="getDataList()">查询</el-button>
        <el-button
          size="small"
          v-if="isAuth('sys:dept:save')"
          type="primary"
          @click="addOrUpdateHandle()"
        >新增</el-button>
      </el-form-item>
    </el-form>
    <el-table
      v-loading="dataListLoading"
      :data="dataList"
      border
      style="width: 100%;"
    >
      <table-tree-column
        prop="name"
        header-align="center"
        align="center"
        width="150"
        label="名称">
      </table-tree-column>
      <el-table-column prop="parentId" header-align="center" align="center" label="父类ID,一级为0"></el-table-column>
      <el-table-column prop="orderNum" header-align="center" align="center" label="排序"></el-table-column>
      <el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
        <template slot-scope="scope">
          <el-button
            type="text"
            v-if="isAuth('sys:dept:update')"
            size="small"
            @click="addOrUpdateHandle(scope.row.id)"
          >修改</el-button>
          <el-button
            type="text"
            v-if="isAuth('sys:dept:delete')"
            size="small"
            @click="deleteHandle(scope.row.id)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
import TableTreeColumn from '@/components/table-tree-column';
import AddOrUpdate from "./add-or-update";
import { treeDataTranslate } from '@/utils';
import { compare } from '@/utils' //排序
export default {
  data() {
    return {
      dataForm: {
        key: ""
      },
      dataList: [],
      dataListSelections: [],
      addOrUpdateVisible: false,
      dataListLoading: false,
      isLoading: false
    };
  },
  components: {
    AddOrUpdate,
    TableTreeColumn
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
        url: this.$http.adornUrl("/sys/dept/list"),
        method: "get",
        params: this.$http.adornParams({
          'name_like': this.dataForm.name
        })
      }).then(({ data }) => {
        this.dataListLoading = false;
        this.isLoading = false;
        if (data && data.code === 0) {
          // console.log(data)
          let _data = data.list;
          let _dataList = treeDataTranslate(_data, 'id')
          this.dataList =  _dataList.sort(compare('orderNum')) //排序
        } else {
          this.dataList = [];
        }
      });
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
            url: this.$http.adornUrl(`/sys/dept/delete/${id}`),
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
    }
  }
};
</script>
