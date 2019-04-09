<template>
  <el-dialog
    class="my-dialog"
    :fullscreen="true"
    :modal="false"
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" size="small" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="名字" prop="name">
        <el-input v-model="dataForm.name" placeholder="名字"></el-input>
      </el-form-item>
      <el-form-item label="首页品牌图片" prop="thumbnailUrl">
        <el-upload
          class="avatar-uploader"
          :action="upload_url"
          :show-file-list="false"
          :on-success="handleThumbnailSuccess"
          :before-upload="beforeThumbnailUpload">
          <img v-if="dataForm.thumbnailUrl" :src="dataForm.thumbnailUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          <div slot="tip" class="el-upload__tip"><span class="text-danger">建议上传宽高为 750x416 的图片</span><br/>请注意您只能上传 jpg,png,gif 格式的图片！</div>
        </el-upload>
      </el-form-item>
      <el-form-item label="描述" prop="simpleDesc">
        <el-input type="textarea" :rows="4"
          v-model="dataForm.simpleDesc" placeholder="请输入描述"></el-input>
      </el-form-item>
      <el-form-item label="品牌详情页头图" prop="picUrl">
        <el-upload
          class="avatar-uploader"
          :action="upload_url"
          :show-file-list="false"
          :on-success="handleItemPicSuccess"
          :before-upload="beforeThumbnailUpload">
          <img v-if="dataForm.picUrl" :src="dataForm.picUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          <div slot="tip" class="el-upload__tip"><span class="text-danger">建议上传宽高为 750x290 的图片</span><br/>请注意您只能上传 jpg,png,gif 格式的图片！</div>
        </el-upload>
      </el-form-item>
      <el-form-item label="排序" prop="sortOrder">
        <el-input-number type="number" v-model.number="dataForm.sortOrder" placeholder="排序" :min="1"></el-input-number>
      </el-form-item>
      <el-form-item label="启用" prop="isShow">
        <el-radio-group v-model="dataForm.isShow">
          <el-radio :label="1">启用</el-radio>
          <el-radio :label="0">不启用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="最低价格" prop="floorPrice">
        <el-input-number type="number" v-model.number="dataForm.floorPrice" placeholder="最低价格"></el-input-number>
      </el-form-item>
      <el-form-item label="新品牌" prop="isNew">
        <el-radio-group v-model="dataForm.isNew">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">不是</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" @click="visible = false">取消</el-button>
      <el-button size="small" type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import API from "@/api";
export default {
  data() {
    return {
      visible: false,
      upload_url: API.common.uploadurl() + "?media=image&type=shbrand",
      // upload_url: API.common.upload() + "?media=image&type=shbrand",
      dataForm: {
        name: "",
        thumbnailUrl: "",
        simpleDesc: "",
        picUrl: "",
        sortOrder: 1,
        isShow: 1,
        floorPrice: "",
        isNew: 1
      },
      dataRule: {
        name: [{ required: true, message: "名字不能为空", trigger: "blur" }],
        thumbnailUrl: [
          { required: true, message: "图片不能为空", trigger: "blur" }
        ],
        simpleDesc: [
          { required: true, message: "描述不能为空", trigger: "blur" }
        ],
        picUrl: [{ required: true, message: "图片不能为空", trigger: "blur" }],
        sortOrder: [
          {
            type: "number",
            required: true,
            message: "请设置排序",
            trigger: "blur"
          }
        ],
        isShow: [{ required: true, message: "启用不能为空", trigger: "blur" }],
        floorPrice: [
          {
            required: true,
            validator: (rule, value, callback) => {
              if (!value) {
                return callback(new Error("价格不能为空"));
              }
              setTimeout(() => {
                if (!Number.isInteger(value)) {
                  callback(new Error("请输入数字值"));
                } else {
                  if (value < 0) {
                    callback(new Error("价格必须大于0"));
                  } else {
                    callback();
                  }
                }
              }, 1000);
            },
            trigger: "blur"
          }
        ],
        isNew: [{ required: true, message: "启用不能为空", trigger: "blur" }]
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
              `/miniapp/shbrand/info/${this.dataForm.id}`
            ),
            method: "get",
            params: this.$http.adornParams()
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.name = data.shBrand.name;
              this.dataForm.thumbnailUrl = data.shBrand.thumbnailUrl;
              this.dataForm.picUrl = data.shBrand.picUrl;
              this.dataForm.simpleDesc = data.shBrand.simpleDesc;
              this.dataForm.sortOrder = data.shBrand.sortOrder;
              this.dataForm.isShow = data.shBrand.isShow;
              this.dataForm.floorPrice = data.shBrand.floorPrice;
              this.dataForm.isNew = data.shBrand.isNew;
            }
          });
        }
      });
    },
    handleThumbnailSuccess(res, file) {
      this.dataForm.thumbnailUrl = res.file_url;
    },
    handleItemPicSuccess(res, file) {
      this.dataForm.picUrl = res.file_url;
    },
    beforeThumbnailUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isPNG = file.type === "image/png";
      const isGIF = file.type === "image/gif";
      const isLt1M = file.size / 1024 / 1024 < .5;
      if (!isJPG && !isPNG && !isGIF) {
        this.$message.error("上传头像图片只能是 jpg,png,gif 格式!");
      }
      if (!isLt1M) {
        this.$message.error("上传头像图片大小不能超过 500kb!");
      }
      return (isJPG || isPNG || isGIF) && isLt1M;
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(
              `/miniapp/shbrand/${!this.dataForm.id ? "save" : "update"}`
            ),
            method: "post",
            data: this.$http.adornData({
              id: this.dataForm.id || undefined,
              name: this.dataForm.name,
              thumbnailUrl: this.dataForm.thumbnailUrl,
              simpleDesc: this.dataForm.simpleDesc,
              picUrl: this.dataForm.picUrl,
              sortOrder: this.dataForm.sortOrder,
              isShow: this.dataForm.isShow,
              floorPrice: this.dataForm.floorPrice,
              isNew: this.dataForm.isNew
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
