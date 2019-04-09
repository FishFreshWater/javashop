<template>
  <el-dialog
    class="my-dialog"
    :fullscreen="true"
    :modal="false"
    :close-on-click-modal="false"
    :visible.sync="visible"
  >
    <el-row :gutter="0">
      <el-col :span="10" style="padding: 10px 15px 20px;background: #f3f3f3;margin: 10px 10px;">
        <h3>退款申请处理</h3>
        <div style="line-height: 24px;">
          <p>
            <el-button @click="goOrder" type="text">所属订单</el-button>
          </p>
          <p>
            <span style="width: 100px;">状态：</span>
            {{dataForm.statusName}}
          </p>
          <p>操作人：{{dataForm.operator}}</p>
          <p>审核时间：{{dataForm.checkTime}}</p>
          <p>
            审核状态：
            <span class="text-danger">{{dataForm.checkStatusName}}</span>
          </p>
          <p>处理结果：{{dataForm.checkComment}}</p>
          <p style="margin-top: 10px;" v-if="dataForm.status == 0  && dataForm.returnType ==0">
            <el-button type="primary" size="small" @click="refundCheck">同意退款</el-button>
            <el-button type="primary" size="small" @click="refundCancel" plain>拒绝退款</el-button>
          </p>
          <p
            style="margin-top: 10px;"
            v-if="dataForm.status == 0  && dataForm.returnType ==1 && dataForm.checkStatus == 0"
          >
            <el-button type="primary" size="small" @click="returnCheck">同意退货</el-button>
            <el-button type="primary" size="small" @click="returnCancel" plain>拒绝退货</el-button>
          </p>
          <p
            style="margin-top: 10px;"
            v-if="dataForm.status == 0  && dataForm.returnType ==1  && dataForm.checkStatus == 1"
          >
            <el-button type="primary" size="small" @click="returnDo">确认收到退货</el-button>
          </p>
          <div style="margin-top: 10px;">
            <p>操作日志：</p>
            <el-table :data="shOrderReturnLogList" stripe style="width: 100%">
              <el-table-column prop="createTime" label="操作时间"></el-table-column>
              <el-table-column prop="operator" label="操作人"></el-table-column>
              <el-table-column prop="content" label="描述"></el-table-column>
            </el-table>
          </div>
        </div>
      </el-col>
      <el-col :span="10" style="padding: 10px 15px 20px;background: #f3f3f3;margin: 10px 10px;">
        <h3>退款申请信息</h3>
        <table class="order-lst">
          <tr>
            <td>
              <span style="width: 100px;">退款类型：</span>
              <span class="text-danger">{{dataForm.typeName}}</span>
            </td>
          </tr>
          <tr>
            <td>
              <p>
                <span style="width: 100px;">状态：</span>
                {{dataForm.statusName}}
              </p>
            </td>
          </tr>
          <tr>
            <td>
              <p>
                <span style="width: 100px;">退货退款单号：</span>
                {{dataForm.number}}
              </p>
            </td>
          </tr>
          <tr>
            <td>
              <p>
                <span style="width: 100px;">退款金额：</span>
                {{dataForm.amount}}
              </p>
            </td>
          </tr>
          <tr>
            <td>
              <p>
                <span style="width: 100px;">归属订单：</span>
                {{dataForm.orderId}}
              </p>
            </td>
          </tr>
          <tr>
            <td>
              <p>
                <span style="width: 100px;">所属用户：</span>
                {{dataForm.userId}}
              </p>
            </td>
          </tr>
          <tr>
            <td>
              <p>
                <span style="width: 100px;">原因：</span>
                {{dataForm.reason}}
              </p>
            </td>
          </tr>
          <tr>
            <td>
              <p>
                <span style="width: 100px;">快递单号：</span>
                {{dataForm.tracking}}
              </p>
            </td>
          </tr>
          <tr>
            <td>
              <p>
                <span style="width: 100px;">物流公司：</span>
                {{dataForm.expressCrop}}
              </p>
            </td>
          </tr>
          <tr>
            <td>
              <p>
                <span style="width: 100px;">物流发货备注：</span>
                {{dataForm.expressDesc}}
              </p>
            </td>
          </tr>
        </table>
      </el-col>
    </el-row>
  </el-dialog>
</template>
<style lang="scss" scoped>
.order-lst {
  width: 100%;
  border: 1px solid #666;
  border-collapse: collapse;
  border-spacing: 0;
  td {
    border: 1px solid #666;
    padding: 5px 8px;
    line-height: 28px;
  }
}
</style>
<script>
export default {
  data() {
    return {
      visible: false,
      id: "",
      dataForm: {},
      shOrderReturnLogList: []
    };
  },
  methods: {
    init(id) {
      this.id = id || 0;
      this.visible = true;
      this.$nextTick(() => {
        if (this.id) {
          this.$http({
            url: this.$http.adornUrl(`/miniapp/shorderreturn/info/${this.id}`),
            method: "get",
            params: this.$http.adornParams()
          }).then(({ data }) => {
            console.log(data);
            if (data && data.code === 0) {
              this.dataForm = data.shOrderReturn;
              this.shOrderReturnLogList = data.shOrderReturnLogList;
            }
          });
        }
      });
    },
    goOrder() {
      this.$router.push({
        path: `/shop-mall/sh-order-detail/${this.dataForm.orderId}`
      });
    },
    refundCheck() {
      if (this.id !== -1) {
        this.$prompt("退款金额", "同意退款", {
          confirmButtonText: "确定",
          cancelButtonText: "取消"
        })
          .then(({ value }) => {
            this.$http({
              url: this.$http.adornUrl(`/miniapp/shorderreturn/refund_check`),
              method: "post",
              data: this.$http.adornData({
                id: this.id,
                comment: value,
                checkStatus: 1
              })
            }).then(({ data }) => {
              if (data && data.code === 0) {
                this.$message({
                  type: "success",
                  message: "退款成功"
                });
                this.getData();
              }
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "取消操作"
            });
          });
      }
    },
    returnCheck() {
      if (this.id !== -1) {
        this.$prompt("审核意见", "同意退货", {
          confirmButtonText: "确定",
          cancelButtonText: "取消"
        })
          .then(({ value }) => {
            this.$http({
              url: this.$http.adornUrl(`/miniapp/shorderreturn/return_check`),
              method: "post",
              data: this.$http.adornData({
                id: this.id,
                comment: value,
                checkStatus: 1
              })
            }).then(({ data }) => {
              if (data && data.code === 0) {
                this.$message({
                  type: "success",
                  message: "退货审核成功"
                });
                this.getData();
              }
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "取消操作"
            });
          });
      }
    },
    returnDo() {
      if (this.id !== -1) {
        this.$prompt("确认收到退款金额", "退款金额", {
          confirmButtonText: "确定",
          cancelButtonText: "取消"
        })
          .then(({ value }) => {
            this.$http({
              url: this.$http.adornUrl(
                `/miniapp/shorderreturn/check/completed`
              ),
              method: "post",
              data: this.$http.adornData({
                id: this.id,
                comment: value
              })
            }).then(({ data }) => {
              if (data && data.code === 0) {
                this.$message({
                  type: "success",
                  message: "退款成功"
                });
                this.getData();
              }
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "取消操作"
            });
          });
      }
    },
    refundCancel() {
      if (this.id !== -1) {
        this.$prompt("审核意见", "取消退款", {
          confirmButtonText: "确定",
          cancelButtonText: "取消"
        })
          .then(({ value }) => {
            this.$http({
              url: this.$http.adornUrl(`/miniapp/shorderreturn/refund_check`),
              method: "post",
              data: this.$http.adornData({
                id: this.id,
                comment: value,
                checkStatus: 2
              })
            }).then(({ data }) => {
              if (data && data.code === 0) {
                this.$message({
                  type: "success",
                  message: "操作成功"
                });
                this.getData();
              }
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "取消操作"
            });
          });
      }
    },
    returnCancel() {
      if (this.id !== -1) {
        this.$prompt("审核意见", "取消退货", {
          confirmButtonText: "确定",
          cancelButtonText: "取消"
        })
          .then(({ value }) => {
            this.$http({
              url: this.$http.adornUrl(`/miniapp/shorderreturn/return_check`),
              method: "post",
              data: this.$http.adornData({
                id: this.id,
                comment: value,
                checkStatus: 2
              })
            }).then(({ data }) => {
              if (data && data.code === 0) {
                this.$message({
                  type: "success",
                  message: "操作成功"
                });
                this.getData();
              }
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "取消操作"
            });
          });
      }
    }
  }
};
</script>