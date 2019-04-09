<template>
  <el-dialog
    class="my-dialog"
    :fullscreen="true"
    :modal="false"
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form size="small" :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="名字" prop="name">
        <el-input v-model="dataForm.name" placeholder="名字"></el-input>
      </el-form-item>
      <el-form-item label="链接" prop="link">
        <el-input v-model="dataForm.link" placeholder="链接"></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input v-model="dataForm.sort" placeholder="排序"></el-input>
      </el-form-item>
      <el-form-item label="图片" prop="imageUrl">
        <el-upload
          v-model="dataForm.imageUrl" 
          class="avatar-uploader"
          :action="upload_url"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload">
          <img v-if="dataForm.imageUrl" :src="dataForm.imageUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon" ></i>
          <div slot="tip" class="el-upload__tip"><span class="text-danger">建议上传宽高为 750x416 的图片</span><br/>请注意您只能上传 jpg,png,gif 格式的图片！</div>
        </el-upload>
      </el-form-item>
      <el-form-item label="失效时间" prop="endTime">
        <el-date-picker
          v-model="dataForm.endTime"
          type="datetime"
          value-format="yyyy-MM-dd HH:mm:ss"
          placeholder="失效时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="显示" prop="display">
        <el-radio-group v-model="dataForm.display">
          <el-radio :label="1">显示</el-radio>
          <el-radio :label="0">不显示</el-radio>
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
      dataForm: {
        name: "",
        link: "",
        sort: "",
        imageUrl: "",
        endTime: "",
        display: 1
      },
      upload_url: API.common.uploadurl() + "?type=shadvert",
      dataRule: {
        name: [{ required: true, message: "名字不能为空", trigger: "blur" }],
        link: [{ required: true, message: "链接不能为空", trigger: "blur" }],
        sort: [{ required: true, message: "排序不能为空", trigger: "blur" }],
        imageUrl: [
          { required: true, message: "图片不能为空", trigger: "blur" }
        ],
        endTime: [
          { required: true, message: "失效时间不能为空", trigger: "blur" }
        ],
        display: [{ required: true, message: "启用不能为空", trigger: "blur" }]
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
              `/miniapp/shadvert/info/${this.dataForm.id}`
            ),
            method: "get",
            params: this.$http.adornParams()
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.name = data.shAdvert.name;
              this.dataForm.link = data.shAdvert.link;
              this.dataForm.sort = data.shAdvert.sort;
              this.dataForm.imageUrl = data.shAdvert.imageUrl;
              this.dataForm.endTime = data.shAdvert.endTime;
              this.dataForm.display = data.shAdvert.display;
            }
          });
        }
      });
    },
    handleAvatarSuccess(res, file) {
      this.dataForm.imageUrl = res.file_url;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isPNG = file.type === "image/png";
      const isGIF = file.type === "image/gif";
      const isLt2M = file.size / 1024 / 1024 < 1;
      if (!isJPG && !isPNG && !isGIF) {
        this.$message.error("上传图片 JPG/PGN/GIF 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传图片大小不能超过 1MB!");
      }
      return isJPG || isPNG || (isGIF && isLt2M);
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(
              `/miniapp/shadvert/${!this.dataForm.id ? "save" : "update"}`
            ),
            method: "post",
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              name: this.dataForm.name,
              link: this.dataForm.link,
              sort: this.dataForm.sort,
              imageUrl: this.dataForm.imageUrl,
              endTime: this.dataForm.endTime,
              display: this.dataForm.display
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
