<template>
  <div class="mod-log content pad10">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input size="small" v-model="dataForm.username" placeholder="用户名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" @click="getDataList()">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @sort-change="sort_change"
      style="width: 100%"
    >
      <el-table-column prop="id" header-align="center" align="center" width="80" label="ID"></el-table-column>
      <el-table-column prop="username" header-align="center" align="center" label="用户名"></el-table-column>
      <el-table-column prop="operation" header-align="center" align="center" label="用户操作"></el-table-column>
      <el-table-column
        prop="method"
        header-align="center"
        align="center"
        width="150"
        :show-overflow-tooltip="true"
        label="请求方法"
      ></el-table-column>
      <el-table-column
        prop="params"
        header-align="center"
        align="center"
        width="150"
        :show-overflow-tooltip="true"
        label="请求参数"
      ></el-table-column>
      <el-table-column
        prop="time"
        header-align="center"
        align="center"
        sortable="custom"
        label="执行时长(毫秒)"
      ></el-table-column>
      <el-table-column 
        prop="type" 
        header-align="center" 
        align="center" 
        sortable="custom" 
        label="状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type == 'error'" size="small" type="danger">失败</el-tag>
          <el-tag v-else size="small">成功</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="ip" header-align="center" align="center" width="150" label="IP地址"></el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        width="180"
        sortable="custom"
        label="创建时间"
      ></el-table-column>
      <el-table-column fixed="right" header-align="center" align="center" width="100" label="操作">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.type == 'error'"
            type="text"
            size="small"
            @click="errorMessage(scope.row.id)"
          >查看异常信息</el-button>
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
    <!-- 异常信息弹窗 -->
    <el-dialog title="异常信息" :visible.sync="errorMessageDialog" width="70%">
      <span>{{errorMessageData}}</span>
    </el-dialog>
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
      dataListLoading: false,
      selectionDataList: [],
      errorMessageDialog: false,
      errorMessageData: ""
    };
  },
  created() {
    this.getDataList();
  },
  methods: {
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl("/sys/log/list"),
        method: "get",
        params: this.$http.adornParams({
          page: this.pageIndex,
          limit: this.pageSize,
          username_like: this.dataForm.username
        })
      }).then(({ data }) => {
        console.log('/sys/log/list---返回的data',data)
        if (data && data.code === 0) {
          this.dataList = data.page.list;
          this.totalPage = data.page.totalCount;
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
    //异常信息
    errorMessage(val) {
      this.errorMessageDialog = true;
      let exception_item = this.dataList.filter(item => {
        return item.id == val;
      });
      this.errorMessageData = exception_item[0].exception;
    },
    //排序
    sort_change(column, prop, order) {
      // console.log(column)
      let _prop = column.prop;
      let _order = column.order
      if (_order == 'ascending') {
        //上升
        this.$http({
          url: this.$http.adornUrl("/sys/log/list"),
          method: "get",
          params: this.$http.adornParams({
            page: this.pageIndex,
            limit: this.pageSize,
            username_like: this.dataForm.username,
            "orderBy":`${_prop} asc`
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
        });
      }else {
        //下降
        this.$http({
          url: this.$http.adornUrl("/sys/log/list"),
          method: "get",
          params: this.$http.adornParams({
            page: this.pageIndex,
            limit: this.pageSize,
            username_like: this.dataForm.username,
            "orderBy":`${_prop} desc`
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
        });
      }
    }
  } 
};
</script>
