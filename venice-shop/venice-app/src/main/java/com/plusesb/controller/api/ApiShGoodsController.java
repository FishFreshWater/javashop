package com.plusesb.controller.api;


import com.google.common.collect.Maps;
import com.plusesb.annotation.IgnoreAuth;
import com.plusesb.annotation.LoginUser;
import com.plusesb.constant.status.NormalStatus;
import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.*;
import com.plusesb.service.*;
import com.plusesb.utils.BaseUtils;
import com.plusesb.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商城首页
 *
 * @author yehb
 * @email xunli-03@163.com
 *
 * @date 2018-09-09 19:43:02
 */
@Api(tags = "商品管理")
@RestController
@RequestMapping("api/sh_goods")
public class ApiShGoodsController extends ApiShAbstractController{


    @Autowired
    private ShGoodsService shGoodsService;
    @Autowired
    private ShGoodsCategoryService shGoodsCategoryService;
    @Autowired
    private ShProductService shProductService;
    @Autowired
    private ShBrandService shBrandService;
    @Autowired
    private ShCollectService shCollectService;
    @Autowired
    private ShFootprintService shFootprintService;
    @Autowired
    private ShUserService shUserService;
    @Autowired
    private ShCommentService shCommentService;
    @Autowired
    private ShGoodsSpecificationService shGoodsSpecificationService;
    @Autowired
    private ShSpecificationService shSpecificationService;
    @Autowired
    private ShSearchHistoryService shSearchHistoryService;



    /**
     */
    @ApiOperation(value = "商品首页")
    @IgnoreAuth
    @GetMapping(value = "index")
    public Object index() {
        R r = R.ok();

        SearchDTO simpleSearchDTO = new SearchDTO();
        simpleSearchDTO.addFiled("is_on_sale","eq",1);
        simpleSearchDTO.addFiled("is_delete","eq",0);
        simpleSearchDTO.addFiled("appid","eq",getAppid());
        simpleSearchDTO.addSort("sort_order",false);
        List<ShGoodsEntity> goodsList = shGoodsService.findAllBySimpleSearch(simpleSearchDTO) ;

        r.put("goodsList",goodsList);
        //
        return r;
    }


    /**
     * 　　在售的商品总数
     */
    @ApiOperation(value = "在售的商品总数")
    @IgnoreAuth
    @GetMapping(value = "count")
    public R count() {
        SearchDTO simpleSearchDTO = new SearchDTO();
        //1是 2否
        simpleSearchDTO.addFiled("is_on_sale","eq","1");
        simpleSearchDTO.addFiled("is_delete","eq","0");
        simpleSearchDTO.addFiled("appid","eq",getAppid());

        Integer goodsCount = shGoodsService.countBySimpleSearch(simpleSearchDTO);
        Map map = new HashMap();
        map.put("goodsCount", goodsCount);
        return R.ok(map);
    }


    /**
     * 　　获取商品列表
     */
    @ApiOperation(value = "获取商品列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "categoryId", value = "分类id", paramType = "path", required = true),
            @ApiImplicitParam(name = "brandId", value = "品牌Id", paramType = "path", required = true),
            @ApiImplicitParam(name = "isNew", value = "新商品", paramType = "path", required = true),
            @ApiImplicitParam(name = "isHot", value = "热卖商品", paramType = "path", required = true)})
    @IgnoreAuth
    @GetMapping(value = "list")
    public Object list(@LoginUser ShUserEntity loginUser, Long categoryId,
                       Long brandId, String keyword, Integer isNew, Integer isHot,
                       @RequestParam(value = "page", defaultValue = "1") Long page, @RequestParam(value = "size", defaultValue = "10") Long size,
                       String sort, String order) {
        SearchDTO simpleSearchDTO = new SearchDTO();
        simpleSearchDTO.addFiled("appid","eq",getAppid());
        simpleSearchDTO.addFiled("is_on_sale","eq", NormalStatus.YES.getKey());
        simpleSearchDTO.addFiled("brand_id","eq", brandId);
        simpleSearchDTO.addFiled("name","like", keyword);
        simpleSearchDTO.addFiled("is_new","eq", isNew);
        simpleSearchDTO.addFiled("is_hot","eq", isHot);
        simpleSearchDTO.addFiled("is_delete","eq","0");
        simpleSearchDTO.addSort("sort_order",false);

        simpleSearchDTO.setPageIndex(page);
        simpleSearchDTO.setPageSize(size);

        //添加搜索记录
        if (BaseUtils.isNotEmpty(keyword)&&BaseUtils.isNotEmpty(loginUser)) {
            //是否存在当前搜索记录
            SearchDTO historyParam = new SearchDTO();
            historyParam.addFiled("keyword","eq",keyword);
            historyParam.addFiled("user_id","eq",loginUser.getId());
            List<ShSearchHistoryEntity> shSearchHistoryEntities = shSearchHistoryService.findAllBySimpleSearch(historyParam);
            if (shSearchHistoryEntities.size()==0){
                ShSearchHistoryEntity searchHistoryVo = new ShSearchHistoryEntity();
                searchHistoryVo.setKeyword(keyword);
                searchHistoryVo.setUserId(loginUser.getId());
                shSearchHistoryService.save(searchHistoryVo);
            }
        }
        //筛选的分类
        List<ShGoodsCategoryEntity> filterCategory = new ArrayList();
        ShGoodsCategoryEntity rootCategory = new ShGoodsCategoryEntity();
        rootCategory.setId(0L);
        rootCategory.setName("全部");
        rootCategory.setChecked(false);
        filterCategory.add(rootCategory);

        simpleSearchDTO.setSqlSelect(new String[]{"category_id"});
        List<ShGoodsEntity> shGoodsEntities = shGoodsService.findAllBySimpleSearch(simpleSearchDTO);
        simpleSearchDTO.setSqlSelect(null);

        if(BaseUtils.isNotEmpty(shGoodsEntities)&&shGoodsEntities.size()>0){
            String categoryIds = shGoodsEntities.stream().map(ShGoodsEntity -> ShGoodsEntity.getCategoryId().toString()).collect(Collectors.joining(","));
            //查找二级分类的parent_id
            SearchDTO categoryParam = new SearchDTO();
            categoryParam.addFiled("id","in", categoryIds);
            categoryParam.setSqlSelect(new String[]{"parent_id"});
            List<ShGoodsCategoryEntity> parentCategoryList = shGoodsCategoryService.findAllBySimpleSearch(categoryParam);
            List<String> parentIdList= new ArrayList<>();
            for (ShGoodsCategoryEntity parentCategory:parentCategoryList){
                if (BaseUtils.isNotEmpty(parentCategory)){
                    parentIdList.add(parentCategory.getParentId().toString());
                }
            }
            //
            if (BaseUtils.isNotEmpty(parentIdList)&&parentIdList.size()>0) {
                String parentIds = parentIdList.stream().collect(Collectors.joining(","));

                //一级分类
                categoryParam = new SearchDTO();
                categoryParam.setSqlSelect(new String[]{"id", "name"});
                categoryParam.addSort("sort_order", true);
                categoryParam.addFiled("id", "in", parentIds);
                List<ShGoodsCategoryEntity> parentCategory = shGoodsCategoryService.findAllBySimpleSearch(categoryParam);
                if (null != parentCategory) {
                    filterCategory.addAll(parentCategory);
                }
            }
        }


        if (BaseUtils.isNotEmpty(categoryId)&& categoryId !=0 ){
            //分类是否为一级分类
            ShGoodsCategoryEntity goodsCategoryEntity = shGoodsCategoryService.getById(categoryId);
            if (BaseUtils.isEmpty(goodsCategoryEntity.getParentId())){
                List<ShGoodsCategoryEntity> childCate = shGoodsCategoryService.findByAppidAndParentId(this.getAppid(),categoryId);
                String childCateIds = childCate.stream().map(shGoodsCategoryEntity -> shGoodsCategoryEntity.getId().toString()).collect(Collectors.joining(","));
                //是否存在子类别 //如果不存在子类别查询数据应该为空
                if(BaseUtils.isEmpty(childCateIds)){
                    simpleSearchDTO.addFiled("category_id","eq", categoryId);
                }else {
                    simpleSearchDTO.addFiled("category_id","in", childCateIds);
                }
            }else {
                simpleSearchDTO.addFiled("category_id","eq", categoryId);
            }
        }

        //排序
        if (null != sort && sort.equals("price")) {
            simpleSearchDTO.addSort("retail_price",order.equalsIgnoreCase("asc"));
        } else {
            simpleSearchDTO.addSort("id",false);
        }
        simpleSearchDTO.setSqlSelect(new String[]{"id","name","primary_pic_url","market_price","retail_price","goods_brief"});

        PageDTO<ShGoodsEntity> resultPage= shGoodsService.findPageBySimpleSearch(simpleSearchDTO);
        for (ShGoodsCategoryEntity categoryEntity : filterCategory) {
            if (null != categoryId && categoryEntity.getId() == 0 || categoryEntity.getId() == categoryId) {
                categoryEntity.setChecked(true);
            } else {
                categoryEntity.setChecked(false);
            }
        }
        return R.ok().put("page",resultPage).put("filterCategory",filterCategory);

    }

//
    /**
     * 　获取分类下的商品
     */
    @ApiOperation(value = " 获取分类下的商品")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "分类id", paramType = "path", required = true)})
    @IgnoreAuth
    @GetMapping(value = "category")
    public Object category(Long id) {
        SearchDTO simpleSearchDTO = new SearchDTO();
        simpleSearchDTO.addFiled("appid","eq",getAppid());

        Map resultObj = Maps.newHashMap();
        //当前类别
        ShGoodsCategoryEntity currentCategory = shGoodsCategoryService.getById(id);
        //上级类别
        if(BaseUtils.isNotEmpty(currentCategory.getParentId())) {
            ShGoodsCategoryEntity parentCategory = shGoodsCategoryService.getById(currentCategory.getParentId());
            if(BaseUtils.isNotEmpty(parentCategory)){
                resultObj.put("parentCategory", parentCategory);
            }
            simpleSearchDTO.addFiled("parent_id","eq",currentCategory.getParentId());
        }else {
            simpleSearchDTO.addFiled("parent_id","isNull","isNull");
        }

        List<ShGoodsCategoryEntity> brotherCategory = shGoodsCategoryService.findAllBySimpleSearch(simpleSearchDTO);
        //
        resultObj.put("currentCategory", currentCategory);
        resultObj.put("brotherCategory", brotherCategory);
        return R.ok(resultObj);
    }


    /**
     * 商品详情页数据
     */
    @ApiOperation(value = " 商品详情页数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "商品id", paramType = "path", required = true)})
    @GetMapping(value = "detail")
    public Object detail(@LoginUser ShUserEntity loginUser,Long id) {

        Map<String, Object> resultObj = new HashMap();

        //
        ShGoodsEntity shGoodsEntity = shGoodsService.getById(id);

        List<ShProductEntity> productEntityList = new ArrayList<>();
        if (shGoodsEntity.getIsOnSpec() == 1){
            productEntityList = shProductService.findByGoodsIdAnIsDefault(shGoodsEntity.getId(),0);
        }else {
            productEntityList = shProductService.findByGoodsIdAnIsDefault(shGoodsEntity.getId(),1);
        }

        //品牌
        if(BaseUtils.isNotEmpty(shGoodsEntity.getBrandId())){
            ShBrandEntity shBrandEntity = shBrandService.getById(shGoodsEntity.getBrandId());
            resultObj.put("brand",shBrandEntity);
        }
        //当前用户是否收藏
        SearchDTO collectParam = new SearchDTO();
        collectParam.addFiled("user_id","eq",loginUser.getId());
        collectParam.addFiled("goods_id","eq",id);
        Integer userHasCollect = shCollectService.countBySimpleSearch(collectParam);
        if (userHasCollect > 0) {
            userHasCollect = 1;
        }
        //评论
        SearchDTO commentParam  = new SearchDTO();
        commentParam.addFiled("goods_id","eq",id);
        Integer commentCount = shCommentService.countBySimpleSearch(commentParam);
        commentParam.setLimit(1);
        List<ShCommentEntity> hotComment = shCommentService.findAllBySimpleSearch(commentParam);
        Map commentInfo = new HashMap();
        if (null != hotComment && hotComment.size() > 0) {
            ShUserEntity commentUser = shUserService.getById(hotComment.get(0).getUserId());
            commentInfo.put("content", hotComment.get(0).getContent());
            commentInfo.put("add_time", hotComment.get(0).getCreateTime());
            commentInfo.put("nickname", commentUser.getNickname());
            commentInfo.put("avatar", commentUser.getAvatarUrl());
            commentInfo.put("pic_list", hotComment.get(0).getPicUrl());
        }
        Map comment = new HashMap();
        comment.put("count", commentCount);
        comment.put("data", commentInfo);
        //按照规格名称分组
        SearchDTO specParam  = new SearchDTO();
        specParam.addFiled("goods_id","eq",id);
        List<ShGoodsSpecificationEntity> specificationEntities = shGoodsSpecificationService.findAllBySimpleSearch(specParam);
        List<Map> specificationList = new ArrayList();

        if (BaseUtils.isNotEmpty(specificationEntities)&& specificationEntities.size()>0){
            Map<Long,List<ShGoodsSpecificationEntity>> specificationMap = specificationEntities.stream().collect(Collectors.groupingBy(ShGoodsSpecificationEntity::getSpecificationId));
            for (Long in : specificationMap.keySet()) {
                ShSpecificationEntity shSpecificationEntity = shSpecificationService.getById(in);
                Map temp = new HashMap();
                temp.put("name", shSpecificationEntity.getName());
                temp.put("specificationId", in);
                temp.put("valueList", specificationMap.get(in));
                specificationList.add(temp);
            }
        }
        //记录用户的足迹
        ShFootprintEntity footprintEntity = new ShFootprintEntity();
        footprintEntity.setGoodsId(shGoodsEntity.getId());
        footprintEntity.setUserId(loginUser.getId());
        footprintEntity.setAppid(this.getAppid());
        shFootprintService.save(footprintEntity);

        resultObj.put("info", shGoodsEntity);
        String [] gallery = shGoodsEntity.getListPicUrl().split(",");
        resultObj.put("gallery", gallery);
        resultObj.put("userHasCollect", userHasCollect);
        resultObj.put("comment", comment);
        resultObj.put("specificationList", specificationList);
        resultObj.put("productList", productEntityList);



        return R.ok(resultObj);
    }



    /**
     * 　　新品首发
     */
    @ApiOperation(value = "新品首发")
    @IgnoreAuth
    @GetMapping(value = "new")
    public Object newAction() {

        SearchDTO simpleSearchDTO = new SearchDTO();
        simpleSearchDTO.addFiled("is_new","eq","1");
        simpleSearchDTO.addFiled("appid","eq",getAppid());
        simpleSearchDTO.addFiled("is_on_sale","eq","1");
        simpleSearchDTO.addFiled("is_delete","eq","0");
        simpleSearchDTO.addSort("sort_order",false);
        simpleSearchDTO.setSqlSelect(new String[]{"id", "name", "list_pic_url as listPicUrl","primary_pic_url primaryPicUrl","sell_volume as sellVolume", "market_price marketPrice","goods_brief goodsBrief", "retail_price retailPrice"});
        PageDTO<ShGoodsEntity> shGoodsList = shGoodsService.findPageBySimpleSearch(simpleSearchDTO);
        return  R.ok().put("newGoods",shGoodsList);
    }

    /**
     * 　　人气推荐
     */
    @ApiOperation(value = "人气推荐")
    @IgnoreAuth
    @GetMapping(value = "hot")
    public Object hot() {
        SearchDTO simpleSearchDTO = new SearchDTO();
        simpleSearchDTO.addFiled("is_hot","eq","1");
        simpleSearchDTO.addFiled("appid","eq",getAppid());
        simpleSearchDTO.addFiled("is_on_sale","eq","1");
        simpleSearchDTO.addFiled("is_delete","eq","0");
        simpleSearchDTO.addSort("sort_order",false);
        simpleSearchDTO.setSqlSelect(new String[]{"id", "name", "list_pic_url as listPicUrl","primary_pic_url primaryPicUrl","sell_volume as sellVolume", "market_price marketPrice","goods_brief goodsBrief", "retail_price retailPrice"});
        PageDTO<ShGoodsEntity> shGoodsList = shGoodsService.findPageBySimpleSearch(simpleSearchDTO);
        return  R.ok().put("hotGoods",shGoodsList);
    }


    /**
     * 　　商品详情页的大家都在看的商品
     */
    @ApiOperation(value = "商品详情页-同类商品")
    @IgnoreAuth
    @GetMapping(value = "related")
    public R related(Long id) {
        List<ShGoodsEntity> relateShoods = new ArrayList<>();
        ShGoodsEntity shGoodsEntity = shGoodsService.getById(id);
        if(BaseUtils.isNotEmpty(shGoodsEntity)){
            Long cateId = shGoodsEntity.getCategoryId();
            SearchDTO simpleSearchDTO = new SearchDTO();
            simpleSearchDTO.addFiled("appid","eq",getAppid());
            simpleSearchDTO.addFiled("category_id","eq",cateId);
            simpleSearchDTO.addFiled("id","ne",id);
            simpleSearchDTO.addFiled("is_delete","eq","0");
            simpleSearchDTO.addSort("sort_order",false);
            simpleSearchDTO.setLimit(4);
            simpleSearchDTO.setSqlSelect(new String[]{"id", "name", "list_pic_url as listPicUrl","primary_pic_url primaryPicUrl","sell_volume as sellVolume", "market_price marketPrice","goods_brief goodsBrief", "retail_price retailPrice"});
            //同类商品
            relateShoods =  shGoodsService.findAllBySimpleSearch(simpleSearchDTO);
        }
        return R.ok().put("goodsList",relateShoods);

    }











}
