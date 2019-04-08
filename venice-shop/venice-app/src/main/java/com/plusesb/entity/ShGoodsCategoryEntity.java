package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.plusesb.utils.BaseUtils;

import java.util.List;


/**
 * 导航
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:43:50
 */
@SuppressWarnings("serial")
@TableName("sh_goods_category")
public class ShGoodsCategoryEntity extends AppBaseEntity {


    /**
     * 名字
     */
    private String name;

    /**
     * 前端名字
     */
    private String frontName;

    /**
     * 前端名字描述
     */
    private String frontDesc;


    /**
     * 首页展示排序
     */
    private Integer showIndex;

    /**
     * 是否显示
     */
    private Integer isShow;

    /**
     * 是否显示首页频道
     */
    private Integer isShowChannel;

    /**
     * 关键字
     */
    private String keywords;

    /**
     * 上级类型id
     */
    private Long parentId;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * banner图片链接
     */
    private String bannerUrl;

    /**
     * icon图片链接
     */
    private String iconUrl;

    /**
     * img图片链接
     */
    private String imgUrl;

    /**
     * 上级分类名称
     */
    @TableField(exist = false)
    private ShGoodsCategoryEntity parentCategory;

    /**
     *上级节点名称
     */
    @TableField(exist = false)
    private String parentName;

    @TableField(exist = false)
    private Boolean checked;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getParentName() {
        if (BaseUtils.isNotEmpty(parentCategory)){
            return parentCategory.getName();
        }
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public ShGoodsCategoryEntity getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(ShGoodsCategoryEntity parentCategory) {
        this.parentCategory = parentCategory;
    }

    @TableField(exist = false)
    private List<ShGoodsCategoryEntity> subCategoryList;

    public List<ShGoodsCategoryEntity> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<ShGoodsCategoryEntity> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    public Integer getIsShowChannel() {
        return isShowChannel;
    }

    public void setIsShowChannel(Integer isShowChannel) {
        this.isShowChannel = isShowChannel;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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
     * 设置：前端名字
     */
    public void setFrontName(String frontName) {
        this.frontName = frontName;
    }
    /**
     * 获取：前端名字
     */
    public String getFrontName() {
        return frontName;
    }

    /**
     * 设置：前段名字描述
     */
    public void setFrontDesc(String frontDesc) {
        this.frontDesc = frontDesc;
    }
    /**
     * 获取：前段名字描述
     */
    public String getFrontDesc() {
        return frontDesc;
    }

    /**
     * 设置：首页展示排序
     */
    public void setShowIndex(Integer showIndex) {
        this.showIndex = showIndex;
    }
    /**
     * 获取：首页展示排序
     */
    public Integer getShowIndex() {
        return showIndex;
    }

    /**
     * 设置：是否显示
     */
    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }
    /**
     * 获取：是否显示
     */
    public Integer getIsShow() {
        return isShow;
    }

    /**
     * 设置：关键字
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    /**
     * 获取：关键字
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * 设置：上级类型id
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    /**
     * 获取：上级类型id
     */
    public Long getParentId() {
        return parentId;
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




}
