<template>
  <el-dialog
    class="my-dialog"
    :fullscreen="true"
    :modal="false"
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="200px"
    >
      <el-form-item label="优惠券名" prop="couponName">
        <el-input v-model="dataForm.couponName" placeholder="优惠券名"></el-input>
      </el-form-item>
      <el-form-item label="领取后X天内有效" prop="days">
        <el-input v-model="dataForm.days" placeholder="领取后X天内有效" type="number"></el-input>
      </el-form-item>
      <el-form-item label="满多少使用" prop="limitBalance">
        <el-input v-model="dataForm.limitBalance" placeholder="满多少使用"></el-input>
      </el-form-item>
      <el-form-item label="优惠金额/抵扣金额" prop="couponBalance">
        <el-input v-model="dataForm.couponBalance" placeholder="优惠金额/抵扣金额"></el-input>
      </el-form-item>
      <el-form-item label="剩余数量" prop="couponLeaveCount">
        <el-input v-model="dataForm.couponLeaveCount" placeholder="剩余数量" type="number"></el-input>
      </el-form-item>
      <el-form-item label="发放数量" prop="couponCount">
        <el-input v-model="dataForm.couponCount" placeholder="数量" type="number"></el-input>
      </el-form-item>
      <el-form-item label="是否上架" prop="status">
        <el-radio-group v-model="dataForm.status">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="2">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="开始领取时间" prop="startDay">
        <el-date-picker
          v-model="dataForm.startDay"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择日期时间"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="领取结束时间" prop="endDay">
        <el-date-picker
          v-model="dataForm.endDay"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择日期时间"
        ></el-date-picker>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      visible: false,
      dataForm: {
        couponName: "",
        couponBalance: "",
        days: "",
        couponLeaveCount: "",
        couponCount: "",
        status: 1,
        startDay: "",
        endDay: "",
        limitBalance: ""
      },
      dataRule: {
        couponName: [
          { required: true, message: "优惠券名不能为空", trigger: "blur" }
        ],
        couponBalance: [
          {
            required: true,
            message: "优惠金额/抵扣金额不能为空",
            trigger: "blur"
          }
        ],
        limitBalance: [
          { required: true, message: "购买满金额不能为空", trigger: "blur" }
        ],
        days: [
          {
            required: true,
            message: "领取后X天内有效不能为空",
            trigger: "blur"
          }
        ],
        couponLeaveCount: [
          { required: true, message: "剩余数量不能为空", trigger: "blur" }
        ],
        couponCount: [
          { required: true, message: "数量不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "是否上架不能为空", trigger: "blur" }
        ],
        startDay: [
          { required: true, message: "开始领取时间不能为空", trigger: "blur" }
        ],
        endDay: [
          { required: true, message: "领取结束时间不能为空", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    init(id) {
      this.dataForm.id = id || 0;
      this.visible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(
              `/miniapp/shcoupon/info/${this.dataForm.id}`
            ),
            method: "get",
            params: this.$http.adornParams()
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.couponName = data.shCoupon.couponName;
              this.dataForm.couponBalance = data.shCoupon.couponBalance;
              this.dataForm.days = data.shCoupon.days;
              this.dataForm.couponLeaveCount = data.shCoupon.couponLeaveCount;
              this.dataForm.couponCount = data.shCoupon.couponCount;
              this.dataForm.status = data.shCoupon.status;
              this.dataForm.startDay = data.shCoupon.startDay;
              this.dataForm.endDay = data.shCoupon.endDay;
              this.dataForm.limitBalance = data.shCoupon.limitBalance;
            }
          });
        }
      });
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(
              `/miniapp/shcoupon/${!this.dataForm.id ? "save" : "update"}`
            ),
            method: "post",
            data: this.$http.adornData({
              id: this.dataForm.id,
              couponName: this.dataForm.couponName,
              couponBalance: this.dataForm.couponBalance,
              days: this.dataForm.days,
              couponLeaveCount: this.dataForm.couponLeaveCount,
              couponCount: this.dataForm.couponCount,
              status: this.dataForm.status,
              startDay: this.dataForm.startDay,
              endDay: this.dataForm.endDay,
              limitBalance: this.dataForm.limitBalance
            })
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "操作成功",
                type: "success",
                duration: 1500,
                onClose: () => {
                  this.visible = false;
                  this.$emit("refreshDataList");
                }
              });
            } else {
              this.$message.error(data.msg);
            }
          });
        }
      });
    }
  }
};
</script>
