<template>
  <div class="content pad10">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input size="small" v-model="dataForm.username" placeholder="用户名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" :loading="isLoading" @click="getDataList()">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
      v-loading="dataListLoading"
      :data="dataList"
      border
      style="width: 100%;"
    >
      <el-table-column prop="ip" header-align="center" align="center" label="ip地址" width="150"></el-table-column>
      <el-table-column prop="username" header-align="center" align="center" label="用户名"></el-table-column>
      <el-table-column prop="type" header-align="center" align="center" label="登陆状态" width="100" ></el-table-column>
      <el-table-column prop="status" header-align="center" align="center" label="状态" width="100" sortable>
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == 'success'" size="small">成功</el-tag>
          <el-tag v-else size="small" type="danger">失败</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="exception" 
      header-align="center" align="center" 
      label="异常信息" :show-overflow-tooltip="true">
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
  </div>
</template>

<script>
export default {
  data() {
    return {
      dataForm: {
        username: ""
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
        url: this.$http.adornUrl("/sys/loginlog/list"),
        method: "get",
        params: this.$http.adornParams({
          page: this.pageIndex,
          limit: this.pageSize,
          'username_like': this.dataForm.username
        })
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.dataList = data.page.list;
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
    }
  }
};
</script>
