package com.plusesb.controller.api;

import com.plusesb.annotation.IgnoreAuth;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.*;
import com.plusesb.service.*;
import com.plusesb.utils.BaseUtils;
import com.plusesb.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


/**
 * 商城首页
 *
 * @author linyuchi
 * @email xunli-03@163.com
 *
 * @date 2018-09-09 19:43:02
 */
@Api(tags = "商城首页接口")
@RestController
@RequestMapping("api/sh_index")
public class ApiShIndexController extends ApiShAbstractController{

    @Autowired
    private ShAdvertService shAdvertService;
    @Autowired
    private ShGoodsService shGoodsService;
    @Autowired
    private ShCartService shCartService;
    @Autowired
    private ShGoodsCategoryService shGoodsCategoryService;
    @Autowired
    private ShBrandService shBrandService;
    @Autowired
    private ShTopicService shTopicService;

    @ApiOperation(value = "banner列表")
    @IgnoreAuth
    @GetMapping(value = "banner")
    public R banner() {

        SearchDTO simpleSearchDTO = new SearchDTO();
        simpleSearchDTO.setLimit(4);
        simpleSearchDTO.addFiled("appid","eq",this.getAppid());
        simpleSearchDTO.addFiled("end_time","gt", BaseUtils.getDatetimeString(new Date()));
        simpleSearchDTO.addFiled("display","eq", 1);
        simpleSearchDTO.addSort("create_time",false);
        simpleSearchDTO.addSort("sort",false);
        List<ShAdvertEntity> banner = shAdvertService.findAllBySimpleSearch(simpleSearchDTO);
        return R.ok().put("banner",banner);
    }


    /**
     * app首页
     */
    @ApiOperation(value = "新商品信息")
    @IgnoreAuth
    @GetMapping(value = "newGoods")
    public R newGoods() {

        SearchDTO simpleSearchDTO = new SearchDTO();
        simpleSearchDTO.setSqlSelect(new String[]{"id", "name", "list_pic_url as listPicUrl","sell_volume as sellVolume","primary_pic_url primaryPicUrl", "retail_price retailPrice", "market_price marketPrice"});
        simpleSearchDTO.addFiled("is_new","eq","1");
        simpleSearchDTO.addFiled("appid","eq",this.getAppid());
        simpleSearchDTO.addFiled("is_delete","eq","0");
        simpleSearchDTO.addSort("sort_order",false);

        simpleSearchDTO.setLimit(4);
        List<ShGoodsEntity> newGoods = shGoodsService.findAllBySimpleSearch(simpleSearchDTO);

        return R.ok().put("newGoodsList",newGoods);
    }

    @ApiOperation(value = "新热门商品信息")
    @IgnoreAuth
    @GetMapping(value = "hotGoods")
    public R hotGoods() {
        SearchDTO simpleSearchDTO = new SearchDTO();
        simpleSearchDTO.setSqlSelect(new String[]{"id", "name", "list_pic_url as listPicUrl","sell_volume as sellVolume","primary_pic_url primaryPicUrl","goods_brief goodsBrief", "retail_price retailPrice", "market_price marketPrice"});
        simpleSearchDTO.addFiled("is_hot","eq","1");
        simpleSearchDTO.addFiled("appid","eq",this.getAppid());
        simpleSearchDTO.addFiled("is_delete","eq","0");
        simpleSearchDTO.addSort("sort_order",false);

        simpleSearchDTO.setLimit(4);
        List<ShGoodsEntity> hotGoods = shGoodsService.findAllBySimpleSearch(simpleSearchDTO);
        return R.ok().put("hotGoodsList", hotGoods);
    }

    @ApiOperation(value = "专题列表")
    @IgnoreAuth
    @GetMapping(value = "topic")
    public R topic() {

        SearchDTO simpleSearchDTO = new SearchDTO();
        simpleSearchDTO.setLimit(4);
        simpleSearchDTO.addFiled("appid","eq",this.getAppid());
        simpleSearchDTO.addSort("create_time",false);
        List<ShTopicEntity> topicList = shTopicService.findAllBySimpleSearch(simpleSearchDTO);

        return R.ok().put("topicList",topicList);
    }

    @ApiOperation(value = "分类信息")
    @IgnoreAuth
    @GetMapping(value = "category")
    public R  category() {

        SearchDTO simpleSearchDTO = new SearchDTO();
        simpleSearchDTO.setLimit(4);
        simpleSearchDTO.addFiled("appid","eq",this.getAppid());
        simpleSearchDTO.addFiled("is_show","eq","1");
        simpleSearchDTO.addFiled("parent_id","isNull","isNull");
        simpleSearchDTO.addSort("sort_order",false);

        List<ShGoodsCategoryEntity> categoryList = shGoodsCategoryService.findAllBySimpleSearch(simpleSearchDTO);
        List<Map<String, Object>> newCategoryList = new ArrayList<>();

        for (ShGoodsCategoryEntity categoryItem : categoryList) {
            List<ShGoodsCategoryEntity> categoryEntityList = shGoodsCategoryService.findByAppidAndParentIdAndShowIndex(getAppid(),categoryItem.getId(),1);
            List<Long> childCategoryIds = new ArrayList<>();
            for (ShGoodsCategoryEntity categoryEntity : categoryEntityList) {
                childCategoryIds.add(categoryEntity.getId());
            }
            if (childCategoryIds.size()>0){
                List<ShGoodsEntity> categoryGoods =  shGoodsService.findByCategoryIdsAndAppid(childCategoryIds,getAppid());
                if (categoryGoods.size()>0){
                    Map<String, Object> newCategory = new HashMap<String, Object>();
                    newCategory.put("id", categoryItem.getId());
                    newCategory.put("name", categoryItem.getName());
                    newCategory.put("goodsList", categoryGoods);
                    newCategoryList.add(newCategory);
                }
            }

        }
        return R.ok().put("categoryList", newCategoryList);
    }

    @ApiOperation(value = "推荐品牌")
    @IgnoreAuth
    @GetMapping(value = "brand")
    public R brand() {

        SearchDTO simpleSearchDTO = new SearchDTO();
        simpleSearchDTO.setLimit(4);
        simpleSearchDTO.addFiled("appid","eq",this.getAppid());
        simpleSearchDTO.addFiled("is_new","eq","1");
        simpleSearchDTO.addSort("sort_order",false);
        List<ShBrandEntity> brandList = shBrandService.findAllBySimpleSearch(simpleSearchDTO);

        return R.ok().put("brandList", brandList);
    }

    @ApiOperation(value = "推荐频道")
    @IgnoreAuth
    @GetMapping(value = "channel")
    public R channel() {

        SearchDTO simpleSearchDTO = new SearchDTO();
        simpleSearchDTO.setLimit(4);
        simpleSearchDTO.addFiled("appid","eq",this.getAppid());
        simpleSearchDTO.addFiled("is_show_channel","eq","1");
        simpleSearchDTO.addFiled("parent_id","isNull","isNull");
        simpleSearchDTO.addSort("sort_order",false);

        List<ShGoodsCategoryEntity> channel = shGoodsCategoryService.findAllBySimpleSearch(simpleSearchDTO);

        return R.ok().put("channel", channel);
    }

}
