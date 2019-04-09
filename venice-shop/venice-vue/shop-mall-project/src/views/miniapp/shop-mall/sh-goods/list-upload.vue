<template>
  <el-upload 
    class="list-upload"
    list-type="picture-card"
    :action="action"
    :show-file-list="false"
    :on-success="handleAvatarSuccess"
    :before-upload="beforeAvatarUpload">
    <slot></slot>
  </el-upload>
</template>
<style lang="scss" scoped>
.list-upload{
  height: 86px;
  width: 86px;
  margin: 0 auto;
  /deep/ img{
    max-width: 86px;
    max-height: 86px;
    vertical-align: middle;
  }
  /deep/ .el-upload--picture-card{
    width: 86px;
    height: 86px;
    line-height: 86px;
  }
  /deep/ .avatar-uploader-icon{
    height: 80px;
    width: 80px;
    line-height: 80px;
  }
}
</style>

<script>
  export default {
    data() {
      return {};
    },
    props:{
      uploadId: null,
      onSuccess: Function,
      action: ''
    },
    methods: {
      beforeAvatarUpload(file) {
        const isJPG = file.type === "image/jpeg";
        const isPNG = file.type === "image/png";
        const isGIF = file.type === "image/gif";
        const isLt1M = file.size / 1024 / 1024 < 1;
        if (!isJPG && !isPNG && !isGIF) {
          this.$message.error("上传头像图片只能是 jpg,png,gif 格式!");
        }
        if (!isLt1M) {
          this.$message.error("上传头像图片大小不能超过 1MB!");
        }
        return (isJPG || isPNG || isGIF) && isLt1M;
      },
      handleAvatarSuccess(){
        var arg=[].slice.call(arguments);
        // console.log(this.uploadId,arg)
        arg.push(this.uploadId);
        this.onSuccess.apply(this,arg);
      }
    }
  };
</script>