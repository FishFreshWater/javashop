<template>
  <div class="content pad10">
    <el-row :gutter="10">
      <el-col :span="8">
        <div style="border: 1px solid; padding:15px;">
          <el-button @click="addParentCate" size="small" type="primary">新建一级分类商品</el-button>
          <el-tree style="margin-top: 10px;" :expand-on-click-node="false" :data="cateTree" :props="cateProps" @node-click="handleNodeClick"></el-tree>
        </div>
      </el-col>
      <el-col :span="16">
        <div v-if="addOrUpdate" style="border: 1px solid;padding:15px;">
          <div>
            <el-button @click="deleteCate" v-show="!isAddParent && !isAddSub" size="small" type="primary">删除当前分类</el-button>
            <el-button @click="addSubCate" v-show="!cateForm.parentId && (!isAddParent || !isAddSub)" size="small" type="primary">新建子分类</el-button>
            <br/>
            <p style="margin: 5px;" class="text-danger" v-show="isAddParent">当前正在新建一级分类</p>
            <p style="margin: 5px;" class="text-danger" v-show="isAddSub">当前正在{{cateForm.parentName}}的子分类</p>
          </div>
          <el-form style="margin-top: 15px;" size="small" :model="cateForm" :rules="cateRule" ref="cateForm" @keyup.enter.native="cateFormSubmit()" label-width="120px">
            <el-form-item label="上级分类" prop="name">
              <el-input disabled="disabled" v-model="cateForm.parentName" placeholder="无"></el-input>
            </el-form-item>
            <el-form-item label="分类名称" prop="name">
              <el-input v-model="cateForm.name" placeholder="分类名称"></el-input>
              
            </el-form-item>
            <el-form-item label="分类显示名称" prop="frontName">
              <el-input v-model="cateForm.frontName" placeholder="分类显示名称"></el-input>
              <p class="help-text">分类查询中分类显示名称</p>
            </el-form-item>
            <el-form-item label="分类显示名称名称描述" prop="frontDesc">
              <el-input v-model="cateForm.frontDesc" placeholder="分类显示名称名称描述"></el-input>
              <p class="help-text">分类查询中分类显示名称描述</p>
            </el-form-item>
            
            <el-form-item label="是否显示" prop="isShow">
              <el-radio-group v-model="cateForm.isShow">
                  <el-radio :label="1">显示</el-radio>
                  <el-radio :label="2">隐藏</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="排序" prop="sortOrder">
              <el-input-number v-model="cateForm.sortOrder" :min="1"></el-input-number>
            </el-form-item>
            <el-form-item label="是否显示在首页频道" prop="isShowChannel" v-show="!cateForm.parentId && (!isAddParent || !isAddSub)">
              <el-radio-group v-model="cateForm.isShowChannel">
                  <el-radio :label="1">显示</el-radio>
                  <el-radio :label="2">隐藏</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="首页展示排序" prop="showIndex" v-show="!cateForm.parentId && (!isAddParent || !isAddSub) && cateForm.isShowChannel == 1">
              <el-input-number v-model="cateForm.showIndex" :min="1"></el-input-number>
            </el-form-item>
            <el-form-item label="分类页面头部图片" prop="bannerUrl" v-show="!cateForm.parentId && (!isAddParent || !isAddSub)">
              <el-upload
                v-model="cateForm.bannerUrl" 
                class="avatar-uploader"
                :action="upload_url"
                :show-file-list="false"
                :on-success="handleBannerSuccess"
                :before-upload="beforeAvatarUpload">
                <img v-if="cateForm.bannerUrl" :src="cateForm.bannerUrl" class="avatar"><i v-else class="el-icon-plus avatar-uploader-icon" ></i>
              </el-upload>
            </el-form-item>
            <el-form-item label="显示在首页频道图标" prop="iconUrl" v-show="!cateForm.parentId && (!isAddParent || !isAddSub) && cateForm.isShowChannel == 1">
              <el-upload
                v-model="cateForm.iconUrl" 
                class="avatar-uploader"
                :action="upload_url"
                :show-file-list="false"
                :on-success="handleIconSuccess"
                :before-upload="beforeAvatarUpload">
                <img v-if="cateForm.iconUrl" :src="cateForm.iconUrl" class="avatar"><i v-else class="el-icon-plus avatar-uploader-icon" ></i>
              </el-upload>
            </el-form-item>
            <el-form-item label="分类图标" prop="imgUrl" v-show="!(!cateForm.parentId && (!isAddParent || !isAddSub))">
              <el-upload
                v-model="cateForm.imgUrl" 
                class="avatar-uploader"
                :action="upload_url"
                :show-file-list="false"
                :on-success="handleImgSuccess"
                :before-upload="beforeAvatarUpload">
                <img v-if="cateForm.imgUrl" :src="cateForm.imgUrl" class="avatar"><i v-else class="el-icon-plus avatar-uploader-icon" ></i>
              </el-upload>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="cateFormSubmit">保存</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import API from "@/api";
import { treeDataTranslate } from "@/utils";
export default {
  data() {
    return {
      cateTree: [],
      upload_url: API.common.uploadurl() + "?media=image&type=goodsCate",
      isAddParent: false,
      isAddSub: false,
      addOrUpdate: false,
      cateForm: {
        id: undefined,
        name: "",
        frontName: "",
        frontDesc: "",
        showIndex: 0,
        isShow: 1,
        isShowChannel: 1,
        sortOrder: 1,
        parentId: undefined,
        parentName: undefined,
        bannerUrl: "",
        iconUrl: "",
        imgUrl: ""
      },
      cateRule: {
        name: [{ required: true, message: "分类名称不能为空", trigger: "blur" }],
        frontName: [
          { required: true, message: "分类显示名称不能为空", trigger: "blur" }
        ],
        frontDesc: [
          { required: true, message: "分类显示名称描述不能为空", trigger: "blur" }
        ],
        showIndex: [
          { required: true, message: "首页展示排序不能为空", trigger: "blur" }
        ],
        isShowChannel: [
          {
            required: true,
            message: "是否显示在首页频道不能为空",
            trigger: "blur"
          }
        ],
        isShow: [
          { required: true, message: "是否显示不能为空", trigger: "blur" }
        ],
        sortOrder: [
          { required: true, message: "排序不能为空", trigger: "blur" }
        ]
        // bannerUrl: [
        //   { required: true, message: "banner图片不能为空", trigger: "blur" }
        // ],
        // iconUrl: [
        //   { required: true, message: "icon图片不能为空", trigger: "blur" }
        // ],
        // imgUrl: [
        //   { required: true, message: "img图片不能为空", trigger: "blur" }
        // ]
      },
      dataListLoading: false,
      cateProps: {
        children: "children",
        label: "name"
      }
    };
  },
  created() {
    this.getDataList();
  },
  methods: {
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl("/miniapp/shgoodscategory/list"),
        method: "get",
        params: this.$http.adornParams({})
      }).then(({ data }) => {
        if (data && data.code === 0) {
          // 转换树形数据
          this.cateTree = treeDataTranslate(data.items);
        } else {
          this.cateTree = [];
        }
        this.dataListLoading = false;
      });
    },
    // 点击树形菜单
    handleNodeClick(data) {
      this.addOrUpdate = true;
      this.isAddParent = false;
      this.isAddSub = false;
      this.cateForm = data;
      console.log(data);
    },
    // 新增分类
    addParentCate() {
      this.isAddParent = true;
      this.addOrUpdate = true;
      this.cateForm = {
        id: undefined,
        name: "",
        frontName: "",
        frontDesc: "",
        showIndex: 0,
        isShow: 1,
        isShowChannel: 1,
        sortOrder: 1,
        parentId: undefined,
        parentName: undefined,
        bannerUrl: "",
        iconUrl: "",
        imgUrl: ""
      };
    },
    // 新增子分类
    addSubCate() {
      let id = this.cateForm.id;
      let name = this.cateForm.name;
      this.isAddSub = true;
      this.cateForm = {
        id: undefined,
        name: "",
        frontName: "",
        frontDesc: "",
        showIndex: 0,
        isShow: 1,
        isShowChannel: 1,
        sortOrder: 1,
        parentId: undefined,
        parentName: undefined,
        bannerUrl: "",
        iconUrl: "",
        imgUrl: ""
      };
      this.$refs["cateForm"].resetFields();
      this.cateForm.parentName = name;
      this.cateForm.parentId = id;
    },
    // 提交保存分类
    cateFormSubmit() {
      this.$refs["cateForm"].validate(valid => {
        if (valid) {
          var params = {
            id: this.cateForm.id || undefined,
            name: this.cateForm.name || undefined,
            frontName: this.cateForm.frontName || undefined,
            frontDesc: this.cateForm.frontDesc || undefined,
            showIndex: this.cateForm.showIndex || undefined,
            isShow: this.cateForm.isShow || undefined,
            isShowChannel: this.cateForm.isShowChannel || undefined,
            sortOrder: this.cateForm.sortOrder || undefined,
            parentId: this.cateForm.parentId || undefined,
            parentName: this.cateForm.parentName || undefined,
            bannerUrl: this.cateForm.bannerUrl || undefined,
            iconUrl: this.cateForm.iconUrl || undefined,
            imgUrl: this.cateForm.imgUrl || undefined
          };
          console.log(params);
          this.$http({
            url: this.$http.adornUrl(
              `/miniapp/shgoodscategory/${
                !this.cateForm.id ? "save" : "update"
              }`
            ),
            method: "post",
            data: this.$http.adornData(params)
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "操作成功",
                type: "success",
                duration: 1500,
                onClose: () => {
                  this.getDataList();
                  this.cateForm = {};
                  this.isAddParent = false;
                  this.isAddSub = false;
                  this.addOrUpdate = false;
                }
              });
            } else {
              this.$message.error(data.msg);
            }
          });
        }
      });
    },
    // 删除分类
    deleteCate() {
      // console.log("删除分类！！");
      this.$confirm(
        `确定删除该商品分类?`,
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      ).then(() => {
        this.$http({
          url: this.$http.adornUrl("/miniapp/shgoodscategory/delete"),
          method: "post",
          data: [this.cateForm.id]
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message({
              message: "操作成功",
              type: "success",
              duration: 1500,
              onClose: () => {
                this.getDataList();
                this.cateForm = {};
                this.isAddParent = false;
                this.isAddSub = false;
                this.addOrUpdate = false;
              }
            });
          } else {
            this.$message.error(data.msg);
          }
        });
      });
    },
    // 图片上传
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isPNG = file.type === "image/png";
      const isGIF = file.type === "image/gif";
      const isLt1M = file.size / 1024 / 1024 < 0.5;
      if (!isJPG && !isPNG && !isGIF) {
        this.$message.error("上传头像图片只能是 JPG,png,gif 格式!");
      }
      if (!isLt1M) {
        this.$message.error("上传头像图片大小不能超过 500kb!");
      }
      return (isJPG || isPNG || isGIF) && isLt1M;
    },
    handleIconSuccess(res, file) {
      this.cateForm.iconUrl = res.file_url;
    },
    handleImgSuccess(res, file) {
      this.cateForm.imgUrl = res.file_url;
    },
    handleBannerSuccess(res, file) {
      console.log(res.file_url);
      this.cateForm.bannerUrl = res.file_url;
    }
  }
};
</script>
