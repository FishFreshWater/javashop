<template>
  <el-dialog
    class="my-dialog"
    :fullscreen="true"
    :modal="false"
    :close-on-click-modal="false"
    :visible.sync="visible"
  >
    <el-tabs type="border-card">
      <el-tab-pane label="订单信息">
        <div>
          订单状态：
          {{order.singleStatusName}}
          <el-button
            v-if="order.singleStatus == 1 || order.singleStatus == 3"
            size="small"
            @click="closeOrder"
            type="primary"
            plain
          >关闭订单</el-button>
          <el-button
            v-if="order.singleStatus == 3"
            @click="sendGoods"
            size="small"
            type="primary"
          >立即发货</el-button>
        </div>
        <div class="order-sec">
          <h2 class="order-hd">订单信息</h2>
          <table class="order-lst">
            <tr>
              <td class="order__lab">订单编号：</td>
              <td>{{order.orderNumber}}</td>
              <td class="order__lab">下单时间：</td>
              <td>{{order.addTime}}</td>
            </tr>
            <tr>
              <td class="order__lab">支付金额：</td>
              <td>微信支付 {{order.actualPrice}}</td>
              <td class="order__lab">支付状态：</td>
              <td>{{order.singleStatusName}}</td>
            </tr>
            <tr>
              <td class="order__lab">配送方式：</td>
              <td>快递</td>
              <td class="order__lab">买家账号：</td>
              <td>{{shUser.nickname}}</td>
            </tr>
          </table>
        </div>
        <div class="order-sec">
          <h2 class="order-hd">收货信息</h2>
          <table class="order-lst">
            <tr>
              <td class="order__lab">收件人：</td>
              <td>{{order.consignee}}</td>
              <td class="order__lab">收件地址：</td>
              <td>{{order.fullRegion}}{{order.address}}</td>
            </tr>
            <tr>
              <td class="order__lab">联系电话：</td>
              <td>{{order.mobile}}</td>
              <td class="order__lab">邮政编码：</td>
              <td>{{order.postcode}}</td>
            </tr>
            <tr>
              <td class="order__lab">买家留言：</td>
              <td colspan="3">{{order.postscript}}</td>
            </tr>
          </table>
        </div>
        <div class="order-sec">
          <h2 class="order-hd">商品信息</h2>
          <el-table :data="order.shOrderGoodsEntityList" border style="width: 100%">
            <el-table-column prop="goodsName" label="商品名称"></el-table-column>
            <el-table-column prop="specValue" label="所选规格"></el-table-column>
            <el-table-column prop="number" label="商品数量"></el-table-column>
            <el-table-column prop="retailPrice" label="商品单价"></el-table-column>
          </el-table>
        </div>
      </el-tab-pane>
      <el-tab-pane label="支付信息">
        <el-table :data="orderPayList" stripe style="width: 100%">
          <el-table-column prop="createTime" label="支付时间"></el-table-column>
          <el-table-column prop="statusName" label="状态"></el-table-column>
          <el-table-column prop="payType" label="支付方式"></el-table-column>
          <el-table-column prop="payBalance" label="支付金额"></el-table-column>
          <el-table-column prop="payNumber" label="商户订单号"></el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="订单日志">
        <el-table :data="orderLogList" stripe style="width: 100%">
          <el-table-column prop="createTime" label="操作时间"></el-table-column>
          <el-table-column prop="userName" label="操作人"></el-table-column>
          <el-table-column prop="content" label="描述"></el-table-column>
        </el-table>
      </el-tab-pane>
      <send-goods v-if="sendGoodsVisible" ref="sendGoodsDia" @success="getData"/>
    </el-tabs>
  </el-dialog>
</template>
<style lang="scss" scoped>
.order-sec {
  margin: 20px 10px;
}
.order-hd {
  border: 1px solid #e9e9e9;
  line-height: 38px;
  padding: 0 20px;
  font-size: 16px;
  border-bottom: 2px solid #0b75be;
  margin: 0;
}
.order-lst {
  width: 100%;
  border: 1px solid #eee;
  border-collapse: collapse;
  border-spacing: 0;
  td {
    border: 1px solid #eee;
    padding: 5px 8px;
    line-height: 28px;
  }
}
</style>
<script>
import sendGoods from "./sendGoods";
export default {
  components: {
    sendGoods
  },
  data() {
    return {
      visible: false,
      sendGoodsVisible: false,
      order: {},
      shUser: {},
      orderPayList: [],
      orderLogList: [],
      id: ""
    };
  },
  methods: {
    init(id) {
      this.id = id || 0;
      this.visible = true;
      this.$nextTick(() => {
        if (this.id) {
          this.$http({
            url: this.$http.adornUrl(`/miniapp/shorder/info/${this.id}`),
            method: "get",
            params: this.$http.adornParams()
          }).then(({ data }) => {
            console.log(data);
            if (data && data.code === 0) {
              this.order = data.shOrder;
              this.shUser = data.shOrder.shUser;
              this.orderPayList = data.orderPayList;
              this.orderLogList = data.orderLogList;
            }
          });
        }
      });
    },
    closeOrder() {
      if (this.id !== 0) {
        this.$confirm("确认关闭订单", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl("/miniapp/shorder/cancel/" + this.id),
            method: "post",
            data: this.$http.adornData({ id: this.id })
          }).then(({ data }) => {
            console.log(data);
            if (data && data.code === 0) {
              this.$message({
                message: "操作成功",
                type: "success",
                duration: 1500,
                onClose: () => {
                  onClose: () => {
                    this.visible = false;
                    this.$emit("refreshDataList");
                  };
                }
              });
            }
          });
        });
      }
    },
    sendGoods() {
      if (this.id !== -1) {
        this.sendGoodsVisible = true;
        this.$nextTick(() => {
          this.$refs.sendGoodsDia.init(this.id);
        });
      }
    }
  }
};
</script>