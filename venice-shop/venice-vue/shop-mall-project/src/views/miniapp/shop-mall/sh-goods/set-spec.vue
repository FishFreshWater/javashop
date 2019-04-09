<template>
  <el-dialog
    :title="isAdd ? '新增规格配置' : '修改规格配置'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form size="small" :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
      <el-form-item label="规格描述" prop="productCategory">
        <el-input v-model="dataForm.productCategory" placeholder="规格描述"></el-input>
      </el-form-item>
      <el-form-item label="库存" prop="stockNumber">
        <el-input-number v-model="dataForm.stockNumber" :precision="0" :min="0"></el-input-number>
      </el-form-item>
      <el-form-item label="现价" prop="retailPrice">
        <el-input-number v-model="dataForm.retailPrice" :precision="2" :min="0.00"></el-input-number>
      </el-form-item>
      <el-form-item label="原价" prop="marketPrice">
        <el-input-number v-model="dataForm.marketPrice" :precision="2" :min="0.00"></el-input-number>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" type="primary" @click="dataFormSubmit()">提交</el-button>
      <el-button size="small" @click="resetForm('dataForm')">重置</el-button>
      <el-button size="small" @click="visible = false">取消</el-button>
    </span>
  </el-dialog>
</template>
<style lang="scss" scoped>
</style>

<script>
  export default {
    data() {
      return {
        visible: false,
        isAdd: true,
        dataForm: {
          index: -1,
          productCategory: "",
          retailPrice: 0,
          marketPrice: 0,
          stockNumber: 0
        },
        dataRule: {
          productCategory: [{ required: true, message: "规格描述不能为空", trigger: "blur" }],
          retailPrice: [
            { required: true, message: "现价不能为空", trigger: "blur" }
          ],
          marketPrice: [
            { required: true, message: "原价不能为空", trigger: "blur" }
          ],
          stockNumber: [
            { required: true, message: "规格库存不能为空", trigger: "blur" }
          ]
        }
      };
    },
    methods: {
      init(data) {
        this.visible = true;
        this.$nextTick(() => {
          console.log(data)
          this.$refs['dataForm'].resetFields();
          this.dataForm.index = -1;
          if(Boolean(data)){
            this.isAdd = false;
            this.dataForm=data;
          }else{
            this.isAdd = true;
          }
        })
      },
      // 表单提交
      dataFormSubmit() {
        this.$refs["dataForm"].validate(valid => {
          if (valid) {
            this.$emit("setedSpec",this.dataForm);
          } else {
            this.$message({
              message: "有必填选项未填,请确认!",
              type: "error",
              duration: 1500
            });
          }
        });
      },
      // 表单重置
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  };
</script>