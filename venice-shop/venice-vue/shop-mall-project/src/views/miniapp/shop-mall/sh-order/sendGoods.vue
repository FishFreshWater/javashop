<template>
  <el-dialog title="填写发货信息" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="发货单号" prop="shippingNo">
        <el-input v-model="dataForm.shippingNo" placeholder="请填写发货单号"></el-input>
      </el-form-item>
       <el-form-item label="快递公司" prop="shippingName">
        <el-input v-model="dataForm.shippingName" placeholder="请填写快递公司"></el-input>
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
        orderId: "",
        shippingNo: "",
        shippingName: ""
      },
      dataRule: {
        shippingNo: [
          { required: true, message: "发货单号不能为空", trigger: "blur" }
        ],
         shippingName: [
          { required: true, message: "快递公司不能为空", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    init(id) {
      this.dataForm.orderId = id;
      this.visible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
      });
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate(valid => {
        if (valid && this.dataForm.orderId) {
          this.$http({
            url: this.$http.adornUrl("/miniapp/shorder/send"),
            method: "post",
            data: this.$http.adornData(this.dataForm)
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "操作成功",
                type: "success",
                duration: 1500,
                onClose: () => {
                  this.$emit("success");
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