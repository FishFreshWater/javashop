<template>
  <div class="content pad10">
    <el-form size="small" :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.number" placeholder="订单号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.status" placeholder="请选择">
          <el-option
            v-for="item in statusOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
      </el-form-item>
      <br>
      <el-form-item>
        <el-radio-group v-model="dataForm.checkStatus" @change="getDataList()">
          <el-radio-button
            v-for="item in options"
            :key="item.value"
            :label="item.value"
          >{{item.label}}</el-radio-button>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border v-loading="dataListLoading" style="width: 100%;">
      <el-table-column prop="number" header-align="center" align="center" label="退货退款单号"></el-table-column>
      <el-table-column prop="typeName" header-align="center" align="center" label="退货类型"></el-table-column>
      <el-table-column prop="amount" header-align="center" align="center" label="退货金额"></el-table-column>
      <el-table-column prop="reason" header-align="center" align="center" label="原因"></el-table-column>
      <el-table-column prop="checkStatusName" header-align="center" align="center" label="审核状态"></el-table-column>
      <el-table-column prop="statusName" header-align="center" align="center" label="当前状态"></el-table-column>
      <el-table-column prop="createTime" header-align="center" align="center" label="操作时间"></el-table-column>
      <el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">详情</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      style="margin-top:10px"
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
import AddOrUpdate from "./detail";
export default {
  data() {
    return {
      dataForm: {
        number: "",
        checkStatus: "",
        status: ""
      },
      options: [
        {
          value: "",
          label: "全部"
        },
        {
          value: "0",
          label: "未审核"
        },
        {
          value: "1",
          label: "已审核"
        },
        {
          value: "2",
          label: "审核失败"
        }
      ],
      statusOptions: [
        {
          value: "",
          label: "全部"
        },
        {
          value: "0",
          label: "进行中"
        },
        {
          value: "1",
          label: "已完成"
        },
        {
          value: "2",
          label: "已取消"
        }
      ],
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false
    };
  },
  created() {
    this.getDataList();
  },
  components: {
    AddOrUpdate
  },
  methods: {
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl("/miniapp/shorderreturn/list"),
        method: "get",
        params: this.$http.adornParams({
          pageIndex: this.pageIndex,
          pageSize: this.pageSize,
          number_like: this.dataForm.number,
          checkStatus_eq: this.dataForm.checkStatus,
          status_eq: this.dataForm.status
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
    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true;
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id);
      });
    }
  }
};
</script>