<template>
  <div>
    <el-dialog
      class="my-dialog my-dialog-add"
      :fullscreen="true"
      :modal="false"
      :title="!dataForm.id ? '新增' : '修改'"
      :close-on-click-modal="false"
      :visible.sync="visible"
    >
      <el-form
        :model="dataForm"
        size="small"
        :rules="dataRule"
        ref="dataForm"
        @keyup.enter.native="dataFormSubmit()"
        label-width="120px"
      >
        <el-form-item label="专题标题" prop="title">
          <el-input v-model="dataForm.title" placeholder="专题标题"></el-input>
        </el-form-item>
        <el-form-item label="相关推荐图" prop="itemPicUrl">
          <el-upload
            class="avatar-uploader"
            :action="upload_url"
            :show-file-list="false"
            :on-success="handleItemPicSuccess"
            :before-upload="beforeThumbnailUpload"
          >
            <img v-if="dataForm.itemPicUrl" :src="dataForm.itemPicUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            <div slot="tip" class="el-upload__tip">
              <span class="text-danger">建议上传宽高为 642x278 的图片</span>
              <br>请注意您只能上传 jpg,png,gif 格式的图片！
            </div>
          </el-upload>
        </el-form-item>
        <el-form-item label="子标题" prop="subtitle">
          <el-input v-model="dataForm.subtitle" placeholder="子标题"></el-input>
        </el-form-item>
        <el-form-item label="阅读量" prop="readCount">
          <el-input-number :min="1" v-model="dataForm.readCount" placeholder="阅读量"></el-input-number>
        </el-form-item>
        <el-form-item label="相关商品" prop="productList">
          <el-table
            style="margin-bottom:10px"
            size="mini"
            :data="shGoodsList"
            border
            @selection-change="selectionChangeHandle"
          >
            <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
            <el-table-column prop="name" header-align="center" align="center" label="名字"></el-table-column>
            <el-table-column
              prop="shGoodsCategoryName"
              header-align="center"
              align="center"
              label="商品所属分类"
            ></el-table-column>
            <el-table-column prop="unitPrice" header-align="center" align="center" label="商品价格"></el-table-column>
            <el-table-column
              fixed="right"
              header-align="center"
              align="center"
              width="150"
              label="操作"
            >
              <template slot-scope="scope">
                <el-button
                  type="text"
                  size="small"
                  @click="deleteGood(scope.$index,scope.row.id)"
                >删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-button type="primary" @click="openAddGoods" size="mini">新增商品</el-button>
          <el-button type="danger" @click="deleleMultiGoods" size="mini">批量删除</el-button>
        </el-form-item>
        <el-form-item label="场景图片" prop="scenePicUrl">
          <el-upload
            class="avatar-uploader"
            :action="upload_url"
            :show-file-list="false"
            :on-success="handleScenePicSuccess"
            :before-upload="beforeThumbnailUpload"
          >
            <img v-if="dataForm.scenePicUrl" :src="dataForm.scenePicUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            <div slot="tip" class="el-upload__tip">
              <span class="text-danger">建议上传宽高为 750x416 的图片</span>
              <br>请注意您只能上传 jpg,png,gif 格式的图片！
            </div>
          </el-upload>
        </el-form-item>
        <el-form-item label="专题内容" prop="content">
          <div class="editor-container">
            <vue-neditor-wrap v-model="dataForm.content" :config="config" :destroy="false"></vue-neditor-wrap>
          </div>
        </el-form-item>
        <el-form-item label="最低价格" prop="priceInfo">
          <el-input v-model.number="dataForm.priceInfo" placeholder="最低价格"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="visible = false">取消</el-button>
        <el-button size="small" type="primary" @click="dataFormSubmit()">确定</el-button>
      </span>
      <get-goods-list @slcedGoods="addGoods" ref="getGoodsDia"/>
    </el-dialog>
  </div>
</template>

<script>
import API from "@/api";
import GetGoodsList from "../sh-tools/getGoodsList.vue";
import VueNeditorWrap from "vue-neditor-wrap";
export default {
  components: {
    GetGoodsList,
    VueNeditorWrap
  },
  data() {
    return {
      upload_url: API.common.uploadurl() + "?media=image&type=shtopic",
      config: {
        // 如果需要上传功能,找后端小伙伴要服务器接口地址
        serverUrl: API.common.ueditor(),
        // 你的UEditor资源存放的路径,相对于打包后的index.html
        UEDITOR_HOME_URL: "/NEditor/",
        // 编辑器不自动被内容撑高
        autoHeightEnabled: false,
        // 初始容器高度
        initialFrameHeight: 240,
        // 初始容器宽度
        initialFrameWidth: "100%"
      },
      dataListSelections: [],
      visible: false,
      getGoodVisible: false,
      dataForm: {
        id: "",
        title: "",
        itemPicUrl: "",
        subtitle: "",
        readCount: 0,
        scenePicUrl: "",
        content: "",
        priceInfo: ""
      },
      shGoodsList: [],
      shGoodsEntities: {},
      dataRule: {
        title: [
          { required: true, message: "专题标题不能为空", trigger: "blur" }
        ],
        itemPicUrl: [
          { required: true, message: "专题条例图片不能为空", trigger: "blur" }
        ],
        subtitle: [
          { required: true, message: "子标题不能为空", trigger: "blur" }
        ],
        readCount: [
          { required: true, message: "阅读量不能为空", trigger: "blur" }
        ],
        scenePicUrl: [
          { required: true, message: "场景图片链接不能为空", trigger: "blur" }
        ],
        content: [
          { required: true, message: "专题内容不能为空", trigger: "blur" }
        ],
        priceInfo: [
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
        ]
      }
    };
  },
  methods: {
    init(id) {
      this.dataForm.id = id || 0
      this.visible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        console.log(`${this.dataForm.id}`)
        if (this.dataForm.id !== 0) {
          this.$http({
            url: this.$http.adornUrl("/miniapp/shtopic/info" + `/${this.dataForm.id}`),
            method: "get",
            params: ""
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.title = data.shTopic.title;
              this.dataForm.id = data.shTopic.id;
              this.dataForm.itemPicUrl = data.shTopic.itemPicUrl;
              this.dataForm.subtitle = data.shTopic.subtitle;
              this.dataForm.readCount = data.shTopic.readCount;
              this.dataForm.scenePicUrl = data.shTopic.scenePicUrl;
              this.dataForm.priceInfo = data.shTopic.priceInfo;
              this.shGoodsList = data.shTopic.shGoodsEntities;
              this.dataForm.content = data.shTopic.content;
              for (let i = 0; i < data.shTopic.shGoodsEntities.length; i++) {
                let good = data.shTopic.shGoodsEntities;
                this.shGoodsEntities[good.id] = {
                  id: good.id
                };
              }
            }
          });
        }
      });
    },
    selectionChangeHandle(val) {
      this.dataListSelections = val;
    },
    openAddGoods() {
      this.getGoodVisible = true;
      this.$nextTick(() => {
        this.$refs.getGoodsDia.init();
      });
    },
    addGoods(data) {
      console.log(data);
      for(let i=0;i<data.length;i++){
        if(!this.shGoodsEntities[data[i].id]){
          this.shGoodsEntities[data[i].id]=data[i].id;
          this.shGoodsList.push({
            id: data[i].id,
            name: data[i].name,
            shGoodsCategoryName: data[i].shGoodsCategoryName,
            retailPrice: data[i].retailPrice || ''
          })
        }
      }
    },
    deleteGood(index, id) {
      this.shGoodsList.splice(index, 1);
      delete this.shGoodsEntities[id];
    },
    deleleMultiGoods() {
      if (this.dataListSelections.length <= 0) {
        return false;
      }
      // console.log(this.dataListSelections);
    },
    handleItemPicSuccess(res, file) {
      this.dataForm.itemPicUrl = res.file_url;
    },
    handleScenePicSuccess(res, file) {
      this.dataForm.scenePicUrl = res.file_url;
    },
    beforeThumbnailUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isPNG = file.type === "image/png";
      const isGIF = file.type === "image/gif";
      const isLt1M = file.size / 1024 / 1024 < 1;
      if (!isJPG && !isPNG && !isGIF) {
        this.$message.error("上传头像图片只能是 jpg,png,gif 格式!");
      }
      if (!isLt1M) {
        this.$message.error("上传头像图片大小不能超过 500kb!");
      }
      return (isJPG || isPNG || isGIF) && isLt1M;
    },
    handlePicsSuccess(res, file, fileList) {
      let ids = "";
      this.dataForm.listPicUrl = fileList.map(file => {
        ids += file.response.id + ",";
      });
      ids = ids.slice(0, ids.length - 1);
      this.dataForm.listPicUrl = ids;
    },
    // 表单提交
    dataFormSubmit() {
      var shGoodsEntities = [];
      for (let id in this.shGoodsEntities) {
        shGoodsEntities.push({
          id: id
        });
      }
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(
              `/miniapp/shtopic/${!this.dataForm.id ? "save" : "update"}`
            ),
            method: "post",
            data: this.$http.adornData({
              id: this.dataForm.id || undefined,
              title: this.dataForm.title || undefined,
              itemPicUrl: this.dataForm.itemPicUrl || undefined,
              subtitle: this.dataForm.subtitle || undefined,
              readCount: this.dataForm.readCount || undefined,
              scenePicUrl: this.dataForm.scenePicUrl || undefined,
              content: this.dataForm.content || undefined,
              priceInfo: this.dataForm.priceInfo || undefined,
              shGoodsEntities: shGoodsEntities
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
<style>
.my-dialog-add {
  z-index: 200 !important;
}
</style>
