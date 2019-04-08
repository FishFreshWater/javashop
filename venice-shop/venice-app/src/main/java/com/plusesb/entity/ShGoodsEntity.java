package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品信息
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:47
 */
@SuppressWarnings("serial")
@TableName("sh_goods")
public class ShGoodsEntity extends AppBaseEntity {

            /**
             * 名字
             */
            private String name;

            /**
             * 商品序列号
             */
            private String goodsSn;

            /**
             * 商品所属分类
             */
            private Long categoryId;
            /**
             * 商品 关键字
             */
//            private String keywords;

            /**
             * 品牌id
             */
            private Integer brandId;

            /**
             * 是否上架
             */
            private Integer isOnSale;

            /**
             * 是否删除
             */
            private Integer isDelete;

            /**
             * 是否启用商品规格
             */
            private Integer isOnSpec;

            /**
             * 是否新品
             */
            private Integer isNew;

            /**
             * 是否限购
             */
            private Integer isLimited;

            /**
             * 是否热卖
             */
            private Integer isHot;

            /**
             * 是否显示库存
             */
            private Integer isShowStock;

            /**
             * 是否显示销量
             */
            private Integer isShowSellVolume;


            /**
             * 零售价格
             */
            private BigDecimal retailPrice;


            /**
             * 市场价
             */
            private BigDecimal marketPrice;


            /**
             * 商品图片
             */
            private String primaryPicUrl;


            /**
             * 排序
             */
            private Integer sortOrder;

            /**
             * 销量
             */
            private Integer sellVolume;

            /**
             * 支付类型(01:货到付款;02：微信支付;03：会员卡支付)
             */
            private Integer typePay;

            /**
             * 减库存方式
             */
            private Integer typeReduceStock;

            /**
             * 商品描述
             */
            private String goodsDesc;

            /**
             * 库存
             */
            private Integer stockNumber;

            /**
             * 商品简述
             */
            private String goodsBrief;


            /**
             *图片列表
             */
            private String listPicUrl;


            //**********************************附加字段*********************************//

            @TableField(exist = false)
            private List<Map> productCategory = new ArrayList<>();

            @TableField(exist = false)
            private Map<String, Map> productMap = new HashMap<>();

            @TableField(exist = false)
            private List<ShProductEntity> productList;

            @TableField(exist = false)
            private List<Map<String,String>> fileList;

            @TableField(exist = false)
            private Integer updateSpec;

            public List<Map> getProductCategory() {
                return productCategory;
            }

            public void setProductCategory(List<Map> productCategory) {
                this.productCategory = productCategory;
            }

            public Map<String, Map> getProductMap() {
                return productMap;
            }

            public void setProductMap(Map<String, Map> productMap) {
                this.productMap = productMap;
            }

            public Integer getUpdateSpec() {
                return updateSpec;
            }

            public void setUpdateSpec(Integer updateSpec) {
                this.updateSpec = updateSpec;
            }

            public List<ShProductEntity> getProductList() {
                return productList;
            }

            public void setProductList(List<ShProductEntity> productList) {
                this.productList = productList;
            }



            public List<Map<String, String>> getFileList() {
                return fileList;
            }

            public void setFileList(List<Map<String, String>> fileList) {
                this.fileList = fileList;
            }

             /**
             * 类别名
             * @return
             */
             @TableField(exist = false)
            private String shGoodsCategoryName;

            public String getShGoodsCategoryName() {
                return shGoodsCategoryName;
            }

            public void setShGoodsCategoryName(String shGoodsCategoryName) {
                this.shGoodsCategoryName = shGoodsCategoryName;
            }
            //**********************************附加字段*********************************//


            public String getListPicUrl() {
                return listPicUrl;
            }

            public void setListPicUrl(String listPicUrl) {
                this.listPicUrl = listPicUrl;
            }

            /**
             * 购物车中商品数量
             */
            @TableField(exist = false)
            private Integer catNum;

            public Integer getCatNum() {
                return catNum;
            }

            public void setCatNum(Integer catNum) {
                this.catNum = catNum;
            }

            /**
             * 设置：名字
             */
            public void setName(String name) {
                this.name = name;
            }
            /**
             * 获取：名字
             */
            public String getName() {
                return name;
            }

            /**
             * 设置：商品序列号
             */
            public void setGoodsSn(String goodsSn) {
                this.goodsSn = goodsSn;
            }
            /**
             * 获取：商品序列号
             */
            public String getGoodsSn() {
                return goodsSn;
            }

            /**
             * 设置：商品所属分类
             */
            public void setCategoryId(Long categoryId) {
                this.categoryId = categoryId;
            }
            /**
             * 获取：商品所属分类
             */
            public Long getCategoryId() {
                return categoryId;
            }


            /**
             * 设置：品牌id
             */
            public void setBrandId(Integer brandId) {
                this.brandId = brandId;
            }
            /**
             * 获取：品牌id
             */
            public Integer getBrandId() {
                return brandId;
            }

            /**
             * 设置：是否上架
             */
            public void setIsOnSale(Integer isOnSale) {
                this.isOnSale = isOnSale;
            }
            /**
             * 获取：是否上架
             */
            public Integer getIsOnSale() {
                return isOnSale;
            }

            /**
             * 设置：是否下架
             */
            public void setIsDelete(Integer isDelete) {
                this.isDelete = isDelete;
            }
            /**
             * 获取：是否下架
             */
            public Integer getIsDelete() {
                return isDelete;
            }

            /**
             * 设置：是否启用商品规格
             */
            public void setIsOnSpec(Integer isOnSpec) {
                this.isOnSpec = isOnSpec;
            }
            /**
             * 获取：是否启用商品规格
             */
            public Integer getIsOnSpec() {
                return isOnSpec;
            }

            /**
             * 设置：是否新品
             */
            public void setIsNew(Integer isNew) {
                this.isNew = isNew;
            }
            /**
             * 获取：是否新品
             */
            public Integer getIsNew() {
                return isNew;
            }

            /**
             * 设置：是否限购
             */
            public void setIsLimited(Integer isLimited) {
                this.isLimited = isLimited;
            }
            /**
             * 获取：是否限购
             */
            public Integer getIsLimited() {
                return isLimited;
            }

            /**
             * 设置：是否热卖
             */
            public void setIsHot(Integer isHot) {
                this.isHot = isHot;
            }
            /**
             * 获取：是否热卖
             */
            public Integer getIsHot() {
                return isHot;
            }

            /**
             * 设置：是否显示库存
             */
            public void setIsShowStock(Integer isShowStock) {
                this.isShowStock = isShowStock;
            }
            /**
             * 获取：是否显示库存
             */
            public Integer getIsShowStock() {
                return isShowStock;
            }

            /**
             * 设置：是否显示销量
             */
            public void setIsShowSellVolume(Integer isShowSellVolume) {
                this.isShowSellVolume = isShowSellVolume;
            }
            /**
             * 获取：是否显示销量
             */
            public Integer getIsShowSellVolume() {
                return isShowSellVolume;
            }


            /**
             * 设置：零售价格
             */
            public void setRetailPrice(BigDecimal retailPrice) {
                this.retailPrice = retailPrice;
            }
            /**
             * 获取：零售价格
             */
            public BigDecimal getRetailPrice() {
                return retailPrice;
            }


            /**
             * 设置：市场价
             */
            public void setMarketPrice(BigDecimal marketPrice) {
                this.marketPrice = marketPrice;
            }
            /**
             * 获取：市场价
             */
            public BigDecimal getMarketPrice() {
                return marketPrice;
            }


            /**
             * 获取：商品图片
             */
            public String getPrimaryPicUrl() {
                return primaryPicUrl;
            }

            /**
             * 设置：商品图片
             */
            public void setPrimaryPicUrl(String primaryPicUrl) {
                this.primaryPicUrl = primaryPicUrl;
            }

            /**
             * 设置：排序
             */
            public void setSortOrder(Integer sortOrder) {
                this.sortOrder = sortOrder;
            }
            /**
             * 获取：排序
             */
            public Integer getSortOrder() {
                return sortOrder;
            }

            /**
             * 设置：销量
             */
            public void setSellVolume(Integer sellVolume) {
                this.sellVolume = sellVolume;
            }
            /**
             * 获取：销量
             */
            public Integer getSellVolume() {
                return sellVolume;
            }

            /**
             * 设置：支付类型(01:货到付款;02：微信支付;03：会员卡支付)
             */
            public void setTypePay(Integer typePay) {
                this.typePay = typePay;
            }
            /**
             * 获取：支付类型(01:货到付款;02：微信支付;03：会员卡支付)
             */
            public Integer getTypePay() {
                return typePay;
            }

            /**
             * 设置：减库存方式
             */
            public void setTypeReduceStock(Integer typeReduceStock) {
                this.typeReduceStock = typeReduceStock;
            }
            /**
             * 获取：减库存方式
             */
            public Integer getTypeReduceStock() {
                return typeReduceStock;
            }

            /**
             * 设置：商品描述
             */
            public void setGoodsDesc(String goodsDesc) {
                this.goodsDesc = goodsDesc;
            }
            /**
             * 获取：商品描述
             */
            public String getGoodsDesc() {
                return goodsDesc;
            }

            /**
             * 设置：库存
             */
            public Integer getStockNumber() {
                return stockNumber;
            }

            public void setStockNumber(Integer stockNumber) {
                this.stockNumber = stockNumber;
            }

            /**
             * 设置：商品简述
             */
            public void setGoodsBrief(String goodsBrief) {
                this.goodsBrief = goodsBrief;
            }
            /**
             * 获取：商品简述
             */
            public String getGoodsBrief() {
                return goodsBrief;
            }




}
