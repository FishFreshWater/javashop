<template>
  <el-dialog
    :visible.sync="visible"
    class="my-dialog my-dialog-add"
    :fullscreen="true"
    :modal="false"
    :title="!dataForm.id ? '新增' : '修改'"
  >
    <el-form
      size="small"
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="120px"
    >
      <el-tabs type="border-card">
        <el-tab-pane label="商品信息">
          <el-form-item label="商品名称" prop="name">
            <el-input v-model="dataForm.name" placeholder="名字"></el-input>
          </el-form-item>
          <el-form-item label="所属分类" prop="categoryId">
            <el-input type="hidden" style="width: auto;" v-model="dataForm.categoryId"></el-input>
            <el-input
              style="width: auto;margin-right:10px;"
              v-model="dataForm.shGoodsCategoryName"
              disabled="disabled"
              placeholder="无"
            ></el-input>
            <el-button type="primary" size="small" @click="choseGoodsCate">选择商品分类</el-button>
          </el-form-item>
          <el-form-item label="所属品牌" prop="brandId">
            <el-input type="hidden" style="width: auto;" v-model="dataForm.brandId"></el-input>
            <el-input
              style="width: auto;margin-right:10px;"
              v-model="brandName"
              disabled="disabled"
              placeholder="无"
            ></el-input>
            <el-button type="primary" size="small" @click="choseBrand">选择商品品牌</el-button>
          </el-form-item>
          <el-form-item label="商品缩略图" prop="primaryPicUrl">
            <el-upload
              v-model="dataForm.primaryPicUrl"
              class="avatar-uploader"
              :action="upload_url"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
            >
              <img v-if="dataForm.primaryPicUrl" :src="dataForm.primaryPicUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="库存" prop="stockNumber">
            <el-input-number v-model="dataForm.stockNumber" :precision="0" :min="0"></el-input-number>
          </el-form-item>
          <el-form-item label="销量" prop="sellVolume">
            <el-input-number v-model="dataForm.sellVolume" :precision="2" :min="0"></el-input-number>
          </el-form-item>
          <el-form-item label="零售价格" prop="retailPrice">
            <el-input-number v-model="dataForm.retailPrice" :precision="2" :min="0.00"></el-input-number>
          </el-form-item>
          <el-form-item label="市场价格" prop="marketPrice">
            <el-input-number v-model="dataForm.marketPrice" :precision="2" :min="0.00"></el-input-number>
          </el-form-item>
          <el-form-item label="排序" prop="sortOrder">
            <el-input-number v-model="dataForm.sortOrder" :min="1"></el-input-number>
          </el-form-item>

          <el-form-item label="商品列表图" prop="listPicUrl">
            <el-upload
              :action="upload_url"
              list-type="picture-card"
              :on-success="handlePicsSuccess"
              :on-preview="handlePictureCardPreview"
              :on-remove="handleRemove"
              :file-list="fileList"
            >
              <i class="el-icon-plus"></i>
            </el-upload>
            <el-dialog :visible.sync="dialogVisible">
              <img width="100%" :src="dialogImageUrl" alt>
            </el-dialog>
          </el-form-item>
        </el-tab-pane>

        <el-tab-pane label="规格设置">
          <el-form-item label="启用商品规格" prop="isOnSpec">
            <el-radio-group v-model="dataForm.isOnSpec">
              <el-radio :label="2">否</el-radio>
              <el-radio :label="1">是</el-radio>
            </el-radio-group>
          </el-form-item>
          <p class="text-danger" style="margin-bottom: 20px;">启用商品规格后，商品的价格及库存以商品规格为准</p>
          <div v-if="dataForm.isOnSpec==1">
            <div
              style="margin-bottom: 10px;"
              v-for="(item,index) in dataForm.productCategory"
              :key="item.index"
            >
              <el-form-item label="规格">
                <el-input v-model="item.key" placeholder="（比如：颜色）">
                  <el-button
                    slot="prepend"
                    @click="delProCategory(index)"
                    size="small"
                    type="danger"
                  >删除当前规格</el-button>
                </el-input>
              </el-form-item>
              <div class="clearfix" style="padding-left: 120px;">
                <div
                  style="width:50%;float: left;padding: 5px 5px 5px 0;"
                  v-for="(valitem,iindex) in item.value"
                  :key="iindex"
                >
                  <el-input
                    size="mini"
                    @change="updateProCateVal($event,index,iindex)"
                    placeholder="请规格项内容"
                    :value="valitem"
                  >
                    <el-button slot="prepend" @click="delProCateVal(index,iindex)" type="danger">删除</el-button>
                  </el-input>
                </div>
              </div>
              <div style="margin-top: 10px;padding-left: 120px;">
                <el-button @click="addProCateVal(index)" size="small" type="primary">新增规格项</el-button>
              </div>
            </div>
            <hr>
            <div style="margin-bottom: 10px;">
              <el-button @click="addProCategory" size="small" type="primary">新增规格</el-button>
              <el-button @click="setProductTable" size="small" type="primary">刷新生成规格产品</el-button>
            </div>
            <div>
              <table class="productTable" style="width: 100%;">
                <thead>
                  <th v-for="item in dataForm.productCategory" :key="item.key">{{item.key}}</th>
                  <th>库存</th>
                  <th>零售价格</th>
                  <th>市场价格</th>
                  <th>商品图片</th>
                </thead>
                <tbody>
                  <tr v-for="(item,index) in productTable" :key="index">
                    <td v-for="iitem in item" :key="iitem.text">{{iitem.text}}</td>
                    <td>
                      <el-input-number
                        size="small"
                        v-model="productForm[index].stockNumber"
                        :precision="0"
                        :min="0"
                      ></el-input-number>
                    </td>
                    <td>
                      <el-input-number
                        size="small"
                        v-model="productForm[index].retailPrice"
                        :precision="0.00"
                        :min="0"
                      ></el-input-number>
                    </td>
                    <td>
                      <el-input-number
                        size="small"
                        v-model="productForm[index].marketPrice"
                        :precision="0.00"
                        :min="0.00"
                      ></el-input-number>
                    </td>
                    <td>
                      <list-upload
                        v-model="productForm[index].thumbnailUrl"
                        :action="upload_url"
                        :uploadId="index"
                        size="mini"
                        :onSuccess="handleProImgSuccess"
                      >
                        <img
                          v-if="productForm[index].thumbnailUrl"
                          :src="productForm[index].thumbnailUrl"
                          class="avatar"
                        >
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                      </list-upload>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </el-tab-pane>

        <el-tab-pane label="详细配置">
          <el-form-item label="商品描述" prop="goodsDesc">
            <div class="editor-container">
              <vue-neditor-wrap v-model="dataForm.goodsDesc" :config="config" :destroy="false"></vue-neditor-wrap>
            </div>
          </el-form-item>
          <el-form-item label="商品简述" prop="goodsBrief">
            <el-input v-model="dataForm.goodsBrief" type="textarea" :rows="2" placeholder="商品简述"></el-input>
          </el-form-item>
        </el-tab-pane>

        <el-tab-pane label="其他">
          <el-form-item label="是否上架" prop="isOnSale">
            <el-radio-group v-model="dataForm.isOnSale">
              <el-radio :label="2">否</el-radio>
              <el-radio :label="1">是</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="新品" prop="isNew">
            <el-radio-group v-model="dataForm.isNew">
              <el-radio :label="2">否</el-radio>
              <el-radio :label="1">是</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="限购" prop="isLimited">
            <el-radio-group v-model="dataForm.isLimited">
              <el-radio :label="2">否</el-radio>
              <el-radio :label="1">是</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="是否热卖" prop="isHot">
            <el-radio-group v-model="dataForm.isHot">
              <el-radio :label="2">否</el-radio>
              <el-radio :label="1">是</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="显示库存" prop="isShowStock">
            <el-radio-group v-model="dataForm.isShowStock">
              <el-radio :label="2">否</el-radio>
              <el-radio :label="1">是</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="显示销量" prop="isShowSellVolume">
            <el-radio-group v-model="dataForm.isShowSellVolume">
              <el-radio :label="2">否</el-radio>
              <el-radio :label="1">是</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-tab-pane>
      </el-tabs>

      <el-form-item style="margin-top:20px;">
        <el-button size="small" type="primary" @click="dataFormSubmit()">提交</el-button>
        <el-button size="small" @click="resetForm('dataForm')">重置</el-button>
        <el-button size="small" @click="cancel()">取消</el-button>
      </el-form-item>
    </el-form>
    <get-goods-cate
      v-if="getGoodsCateVisible"
      ref="getGoodsCateDia"
      @slcedGoodsCate="slcedTreeNode"
    />
    <get-brand v-if="getBrandVisible" ref="getBrandDia" @slcedBrand="slcedBrand"/>
  </el-dialog>
</template>
<style lang="scss" scoped>
.el-tab-pane {
  padding: 20px 30px 10px 15px;
}
.productTable {
  border: 1px solid #eee;
  border-spacing: 0;
  border-collapse: collapse;
  text-align: center;
  th,
  td {
    padding: 5px;
    line-height: 26px;
    border: 1px solid #ececec;
  }
}
</style>

<script>
import API from "@/api";
import GetGoodsCate from "../sh-tools/getGoodsCate";
import ListUpload from "./list-upload";
import VueNeditorWrap from "vue-neditor-wrap";
import GetBrand from "../sh-tools/getBrand.vue";
export default {
  data() {
    return {
      visible: false,
      getGoodsCateVisible: false,
      getBrandVisible: false,
      isAdd: true,
      // 商品规格
      productTable: [],
      updateSpec: "0",
      productForm: [],
      // 所属类别
      upload_url: API.common.uploadurl() + "?media=image&type=shgoods",
      dialogVisible: false,
      dialogImageUrl: "",
      brandName: "",
      // 文件列表
      fileList: [],
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
      dataForm: {
        id: "",
        name: "",
        categoryId: "",
        brandId: "",
        isOnSale: 1,
        isOnSpec: 2,
        productMap: {},
        shGoodsCategoryName: "",
        productCategory: [],
        isNew: 1,
        isLimited: 2,
        isHot: 1,
        isShowStock: 1,
        isShowSellVolume: 1,
        unitPrice: 0,
        retailPrice: 0.0,
        marketPrice: 0.0,
        primaryPicUrl: "",
        sortOrder: "",
        sellVolume: "",
        typePay: 2,
        typeReduceStock: 3,
        goodsDesc: "",
        stockNumber: "",
        goodsBrief: "",
        listPicUrl: "",
        promotionDesc: "",
        promotionTag: ""
      },
      dataRule: {
        name: [{ required: true, message: "名字不能为空", trigger: "blur" }],
        categoryId: [
          { required: true, message: "商品所属分类不能为空", trigger: "blur" }
        ],
        brandId: [
          { required: true, message: "品牌id不能为空", trigger: "blur" }
        ],
        isOnSale: [
          { required: true, message: "上架不能为空", trigger: "blur" }
        ],
        isOnSpec: [
          { required: true, message: "启用商品规格不能为空", trigger: "blur" }
        ],
        isNew: [
          { required: true, message: "是否新品不能为空", trigger: "blur" }
        ],
        isLimited: [
          { required: true, message: "是否限购不能为空", trigger: "blur" }
        ],
        isHot: [
          { required: true, message: "是否热卖不能为空", trigger: "blur" }
        ],
        isShowStock: [
          { required: true, message: "显示库存不能为空", trigger: "blur" }
        ],
        isShowSellVolume: [
          { required: true, message: "显示销量不能为空", trigger: "blur" }
        ],
        retailPrice: [
          { required: true, message: "零售价格不能为空", trigger: "blur" }
        ],
        // unitPrice: [
        //   { required: true, message: "原价不能为空", trigger: "blur" }
        // ],
        marketPrice: [
          { required: true, message: "市场不能为空", trigger: "blur" }
        ],
        primaryPicUrl: [
          { required: true, message: "商品图片不能为空", trigger: "blur" }
        ],
        sortOrder: [
          { required: true, message: "排序不能为空", trigger: "blur" }
        ],
        sellVolume: [
          { required: true, message: "销量不能为空", trigger: "blur" }
        ],
        // typePay: [
        //   { required: true, message: "支付类型不能为空", trigger: "blur" }
        // ],
        // typeReduceStock: [
        //   { required: true, message: "减库存方式不能为空", trigger: "blur" }
        // ],
        goodsDesc: [
          { required: true, message: "商品描述不能为空", trigger: "blur" }
        ],
        stockNumber: [
          { required: true, message: "库存不能为空", trigger: "blur" }
        ],
        goodsBrief: [
          { required: true, message: "商品简述不能为空", trigger: "blur" }
        ],
        listPicUrl: [
          { required: true, message: "商品列表图不能为空", trigger: "blur" }
        ]
      }
    };
  },
  components: {
    GetBrand,
    GetGoodsCate,
    ListUpload,
    VueNeditorWrap
  },
  methods: {
    init(id) {
      console.log("zx");
      this.visible = true;
      this.dataForm.id = id == -1 ? "" : id;
      this.isAdd = !Boolean(this.dataForm.id);
      //
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (this.dataForm.id) {
          // 获取商品信息。
          // /miniapp/shgoods/list
          this.$http({
            url: this.$http.adornUrl("/miniapp/shgoods/info" + `/${id}`),
            method: "get",
            params: ""
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.name = data.shGoods.name;
              this.dataForm.categoryId = data.shGoods.categoryId;
              this.dataForm.brandId = data.shGoods.brandId;
              this.dataForm.isOnSale = data.shGoods.isOnSale;
              this.dataForm.isOnSpec = data.shGoods.isOnSpec;
              this.dataForm.isNew = data.shGoods.isNew;
              this.dataForm.isLimited = data.shGoods.isLimited;
              this.dataForm.isHot = data.shGoods.isHot;
              this.dataForm.shGoodsCategoryName =
                data.shGoods.shGoodsCategoryName;
              this.dataForm.isShowStock = data.shGoods.isShowStock;
              this.dataForm.isShowSellVolume = data.shGoods.isShowSellVolume;
              this.dataForm.retailPrice = data.shGoods.retailPrice;
              this.dataForm.productCategory = data.shGoods.productCategory;
              this.dataForm.productMap = data.shGoods.productMap;
              this.dataForm.unitPrice = data.shGoods.unitPrice;
              this.dataForm.marketPrice = data.shGoods.marketPrice;
              this.dataForm.primaryPicUrl = data.shGoods.primaryPicUrl;
              this.dataForm.sortOrder = data.shGoods.sortOrder;
              this.dataForm.sellVolume = data.shGoods.sellVolume;
              this.dataForm.typePay = data.shGoods.typePay;
              this.dataForm.typeReduceStock = data.shGoods.typeReduceStock;
              this.dataForm.goodsDesc = data.shGoods.goodsDesc;
              this.dataForm.stockNumber = data.shGoods.stockNumber;
              this.dataForm.goodsBrief = data.shGoods.goodsBrief;
              this.dataForm.listPicUrl = data.shGoods.listPicUrl;
              this.dataForm.promotionDesc = data.shGoods.promotionDesc;
              this.dataForm.promotionTag = data.shGoods.promotionTag;
              this.dataForm.productMap = data.shGoods.productMap;
              this.fileList = data.shGoods.fileList;
              this.$refs.ue.setUEContent(data.shGoods.goodsDesc);
            }
            if (this.dataForm.isOnSpec == 1) {
              this.setProductTable();
            }
          });
        }
      });
    },
    setProductTable() {
      var result = [];
      var data = this.dataForm.productCategory;
      if (data.length <= 0 || this.dataForm.isOnSpec == 2) return false;
      var total = 1;
      function cartesianProductOf(arrA, arrB) {
        var ret = [];
        arrA.forEach(function(a, i) {
          arrB.forEach(function(b, j) {
            let arr = [];
            if (a.constructor == Array) {
              arr = a.concat({
                text: b,
                index: j
              });
              ret.push(arr);
            } else {
              arr.push({ text: a, index: i });
              arr.push({ text: b, index: j });
              ret.push(arr);
            }
          });
        });
        return ret;
      }
      for (let i = 0; i < data[0].value.length; i++) {
        result.push([
          {
            text: data[0].value[i],
            index: i
          }
        ]);
      }
      for (let i = 1; i < data.length; i++) {
        result = cartesianProductOf(result, data[i].value);
      }

      this.productTable = result;
      this.productForm = [];
      for (let i = 0; i < result.length; i++) {
        let key = "";
        for (let j = 0, len = result[i].length; j < len; j++) {
          key += result[i][j].index + (j < len - 1 && "_");
        }
        if (this.dataForm.productMap[key] && this.updateSpec == "0") {
          this.productForm.push(this.dataForm.productMap[key]);
        } else {
          this.productForm.push({
            key: key,
            stockNumber: 0,
            retailPrice: 0,
            marketPrice: 0,
            thumbnailUrl: ""
          });
        }
      }
    },
    addProCategory() {
      this.updateSpec = "1";
      this.dataForm.productCategory.push({
        key: "",
        value: []
      });
    },
    delProCategory(index) {
      this.updateSpec = "1";
      this.dataForm.productCategory.splice(index, 1);
    },
    updateProCateVal(event, cateInx, valInx) {
      this.updateSpec = "1";
      this.dataForm.productCategory[cateInx].value[valInx] = event;
    },
    delProCateVal(cateInx, valInx) {
      this.updateSpec = "1";
      this.dataForm.productCategory[cateInx].value.splice(valInx, 1);
    },
    addProCateVal(index) {
      this.updateSpec = "1";
      this.dataForm.productCategory[index].value.push("");
    },
    choseBrand() {
      this.getBrandVisible = true;
      this.$nextTick(() => {
        this.$refs.getBrandDia.init();
      });
    },
    slcedBrand(data) {
      this.dataForm.brandId = data.id;
      this.brandName = data.name;
    },
    choseGoodsCate() {
      this.getGoodsCateVisible = true;
      this.$nextTick(() => {
        this.$refs.getGoodsCateDia.init();
      });
    },
    // 选中商品分类回掉方法
    slcedTreeNode(data) {
      this.dataForm.categoryId = data.id;
      this.dataForm.shGoodsCategoryName = data.name;
    },
    handleProImgSuccess(res, file, fileList, index) {
      this.productForm[index].thumbnailUrl = res.file_url;
    },
    handleAvatarSuccess(res, file) {
      this.dataForm.primaryPicUrl = res.file_url;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isPNG = file.type === "image/png";
      const isGIF = file.type === "image/gif";
      const isLt1M = file.size / 1024 / 1024 < 1;
      if (!isJPG && !isPNG && !isGIF) {
        this.$message.error("上传头像图片只能是 JPG,png,gif 格式!");
      }
      if (!isLt1M) {
        this.$message.error("上传头像图片大小不能超过 1MB!");
      }
      return (isJPG || isPNG || isGIF) && isLt1M;
    },
    handlePicsSuccess(res, file, fileList) {
      let urls = "";
      this.dataForm.listPicUrl = fileList.map(file => {
        urls += res.file_url + ",";
      });
      urls = urls.slice(0, urls.length - 1);
      this.dataForm.listPicUrl = urls;
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleRemove(file, fileList) {
      let urls = "";
      this.dataForm.listPicUrl = fileList.map(file => {
        urls += file.url + ",";
      });
      urls = urls.slice(0, urls.length - 1);
      this.dataForm.listPicUrl = urls;
    },
    // 验证
    valiteProduct() {
      var total = this.dataForm.productCategory.length == 0 ? 0 : 1;
      if (this.dataForm.isOnSpec == 2) {
        return true;
      }
      for (let i = 0; i < this.dataForm.productCategory.length; i++) {
        var data = this.dataForm.productCategory[i];
        if (data.key == "") {
          this.$message({
            message: "规格描述为空!",
            type: "error",
            duration: 1500
          });
          return false;
        }
        if (data.value.length <= 0) {
          this.$message({
            message: "没有规格项!",
            type: "error",
            duration: 1500
          });
          return false;
        }
        for (let j = 0; j < data.value.length; j++) {
          if (data.value[j] == "") {
            this.$message({
              message: "规格项内容为空!",
              type: "error",
              duration: 1500
            });
            return false;
          }
        }
        total = total * data.value.length;
      }
      if (total !== this.productForm.length) {
        this.$message({
          message: "请刷新，配置完整规格产品!",
          type: "error",
          duration: 1500
        });
        return false;
      }
      for (let i = 0; i < this.productForm.length; i++) {
        var data = this.productForm[i];
        if (data.retailPrice <= 0 || data.marketPrice <= 0) {
          this.$message({
            message: "规格商品价格小于等于0!",
            type: "error",
            duration: 1500
          });
          return false;
        }
        if (data.thumbnailUrl == "") {
          this.$message({
            message: "规格商品图片为空!",
            type: "error",
            duration: 1500
          });
          return false;
        }
      }

      if (this.updateSpec == "1") {
        console.log("如果添加或修改了规则，则重新赋值给productList");
        this.dataForm.productMap = {};
        for (let i = 0; i < this.productForm.length; i++) {
          var data = this.productForm[i];
          this.dataForm.productMap[data.key] = {
            stockNumber: data.stockNumber,
            retailPrice: data.retailPrice,
            marketPrice: data.marketPrice,
            thumbnailUrl: data.thumbnailUrl
          };
        }
      }
      return true;
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate(valid => {
        if (!this.valiteProduct()) return false;
        if (valid) {
          var params = {
            id: this.dataForm.id,
            name: this.dataForm.name || undefined,
            updateSpec: this.updateSpec,
            categoryId: this.dataForm.categoryId || undefined,
            brandId: this.dataForm.brandId || undefined,
            isOnSale: this.dataForm.isOnSale || undefined,
            isOnSpec: this.dataForm.isOnSpec || undefined,
            isNew: this.dataForm.isNew || undefined,
            isLimited: this.dataForm.isLimited || undefined,
            isHot: this.dataForm.isHot || undefined,
            isShowStock: this.dataForm.isShowStock || undefined,
            isShowSellVolume: this.dataForm.isShowSellVolume || undefined,
            retailPrice: this.dataForm.retailPrice || undefined,
            unitPrice: this.dataForm.unitPrice || undefined,
            marketPrice: this.dataForm.marketPrice || undefined,
            primaryPicUrl: this.dataForm.primaryPicUrl || undefined,
            sortOrder: this.dataForm.sortOrder || undefined,
            sellVolume: this.dataForm.sellVolume || undefined,
            typePay: this.dataForm.typePay || undefined,
            typeReduceStock: this.dataForm.typeReduceStock || undefined,
            goodsDesc: this.dataForm.goodsDesc || undefined,
            stockNumber: this.dataForm.stockNumber || undefined,
            goodsBrief: this.dataForm.goodsBrief || undefined,
            listPicUrl: this.dataForm.listPicUrl || undefined,
            promotionDesc: this.dataForm.promotionDesc || undefined,
            promotionTag: this.dataForm.promotionTag || undefined,
            productCategory: this.dataForm.productCategory || undefined,
            productMap: this.dataForm.productMap || undefined
          };
          this.$http({
            url: this.$http.adornUrl(
              `/miniapp/shgoods/${!this.dataForm.id ? "save" : "update"}`
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
                  this.visible = false;
                  this.$emit("refreshDataList");
                }
              });
            } else {
              this.$message.error(data.msg);
            }
          });
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
    },
    // 取消
    cancel() {
      this.$emit("cancel");
    }
  }
};
</script>