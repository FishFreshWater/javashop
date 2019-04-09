<template>
  <div class="content pad10">
    <el-form size="small" :inline="true" :model="searchForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="searchForm.orderNumber" placeholder="订单编码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
      </el-form-item>
      <br>
      <el-form-item>
        <el-radio-group v-model="searchForm.singleOrderStatus" @change="getDataList()">
          <el-radio-button
            v-for="item in options"
            :key="item.value"
            :label="item.value"
          >{{item.label}}</el-radio-button>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <el-table
      size="small"
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;"
    >
      <el-table-column prop="orderNumber" header-align="center" align="center" label="订单编码"></el-table-column>
      <el-table-column prop="singleStatusName" header-align="center" align="center" label="订单状态"></el-table-column>
      <el-table-column prop="consignee" header-align="center" align="center" label="收货人"></el-table-column>
      <el-table-column prop="fullRegion" header-align="center" align="center" label="收货地区"></el-table-column>
      <el-table-column prop="address" header-align="center" align="center" label="详细地址"></el-table-column>
      <el-table-column prop="mobile" header-align="center" align="center" label="联系电话"></el-table-column>
      <el-table-column prop="actualPrice" header-align="center" align="center" label="支付金额"></el-table-column>
      <el-table-column prop="orderPrice" header-align="center" align="center" label="订单总价"></el-table-column>

      <el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">详情</el-button>
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
      searchForm: {
        orderNumber: "",
        singleOrderStatus: ""
      },
      options: [
        {
          value: "",
          label: "全部"
        },
        {
          value: "1",
          label: "待付款"
        },
        {
          value: "3",
          label: "待发货"
        },
        {
          value: "6",
          label: "待收货"
        },
        {
          value: "7",
          label: "已退货"
        },
        {
          value: "8",
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
        url: this.$http.adornUrl("/miniapp/shorder/list"),
        method: "get",
        params: this.$http.adornParams({
          pageIndex: this.pageIndex,
          pageSize: this.pageSize,
          number_eq: this.searchForm.orderNumber,
          status_eq: this.searchForm.singleOrderStatus
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
    }
  }
};
</script>