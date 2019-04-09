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
      label-width="80px"
    >
      <el-form-item label="名称" prop="name">
        <el-input v-model="dataForm.name" placeholder="名称"></el-input>
      </el-form-item>
      <el-form-item label="值" prop="value">
        <el-input v-model="dataForm.value" placeholder="值"></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="orderNum">
        <el-input v-model="dataForm.orderNum" placeholder="排序"></el-input>
      </el-form-item>
      <el-form-item label="编码" prop="code">
        <p>{{paramsCode}}</p>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="dataForm.remark" placeholder="备注"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" @click="visible = false">取消</el-button>
      <el-button size="small" type="primary" :disabled="isDisabled" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      visible: false,
      isDisabled: false,
      roleList: [],
      dataForm: {
        name: "",
        value: "",
        orderNum: "",
        remark: ""
      },
      dataRule: {
        name: [{ required: true, message: "名称不能为空", trigger: "blur" }],
        value: [{ required: true, message: "值不能为空", trigger: "blur" }],
        orderNum: [
          { required: true, message: "排序不能为空", trigger: "blur" }
        ],
        typeId: [
          { required: true, message: "类型ID不能为空", trigger: "blur" }
        ],
        remark: [{ required: true, message: "备注不能为空", trigger: "blur" }]
      },
      paramsCode:''
    };
  },
  methods: {
    init(id,paramsCode) {
      this.dataForm.id = id || 0;
      this.paramsCode = paramsCode;
      this.visible = true;
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/sys/dicitem/info/${this.dataForm.id}`),
            method: "get",
            params: this.$http.adornParams()
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.name = data.sysDicItem.name;
              this.dataForm.value = data.sysDicItem.value;
              this.dataForm.orderNum = data.sysDicItem.orderNum;
              // this.dataForm.typeId = data.sysDicItem.typeId;
              this.dataForm.remark = data.sysDicItem.remark;
            }
          });
        }
      })
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          this.isDisabled = true;
          this.$http({
            url: this.$http.adornUrl(
              `/sys/dicitem/${!this.dataForm.id ? "save" : "update"}`
            ),
            method: "post",
            data: this.$http.adornData({
              id: this.dataForm.id || undefined,
              name: this.dataForm.name,
              value: this.dataForm.value,
              orderNum: this.dataForm.orderNum,
              code: this.paramsCode,
              remark: this.dataForm.remark
            })
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "操作成功",
                type: "success",
                duration: 1500,
                onClose: () => {
                  this.visible = false;
                  this.isDisabled = false;
                  this.$emit("refreshDataList"); //刷新父组件
                }
              });
            } else {
              this.isDisabled = false;
              this.$message.error(data.msg);
            }
          });
        }
      });
    }
  }
};
</script>
