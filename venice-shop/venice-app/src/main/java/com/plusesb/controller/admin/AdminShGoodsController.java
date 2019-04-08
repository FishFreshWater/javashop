package com.plusesb.controller.admin;


import com.baomidou.mybatisplus.annotation.TableField;
import com.google.common.collect.Maps;
import com.plusesb.controller.AbstractController;
import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchFieldDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.*;
import com.plusesb.service.*;
import com.plusesb.utils.BaseUtils;
import com.plusesb.utils.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 商品信息
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:47
 */
@RestController
@RequestMapping("miniapp/shgoods")
public class AdminShGoodsController extends AdminShAbstractController{
    @Autowired
    private ShGoodsService shGoodsService;
    @Autowired
    private ShGoodsCategoryService shGoodsCategoryService;
    @Autowired
    private ShProductService shProductService;
    @Autowired
    private ShGoodsSpecificationService shGoodsSpecificationService;
    @Autowired
    private ShSpecificationService shSpecificationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(){

        SearchDTO simpleSearchDTO = getSearchDtoFromJqGrid();
        Map<String,Object> params = Maps.newHashMap();
        params.put("pageIndex",simpleSearchDTO.getPageIndex());
        params.put("pageSize",simpleSearchDTO.getPageSize());
        simpleSearchDTO.addFiled("appid","eq",this.getAppid());

        List<SearchFieldDTO> fieldDTOList = simpleSearchDTO.getFields();
        for(SearchFieldDTO searchFieldDTO : fieldDTOList){
            params.put(searchFieldDTO.getFiled(),searchFieldDTO.getData());
        }
        PageDTO<ShGoodsEntity> page = shGoodsService.findBySql(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){

        ShGoodsEntity shGoods = shGoodsService.getById(id);

        if(BaseUtils.isNotEmpty(shGoods.getCategoryId())){
            ShGoodsCategoryEntity shGoodsCategoryEntity = shGoodsCategoryService.getById(shGoods.getCategoryId());
            if(BaseUtils.isNotEmpty(shGoodsCategoryEntity)){
                shGoods.setShGoodsCategoryName(shGoodsCategoryEntity.getName());
            }
        }
        //拼接规格

        if(shGoods.getIsOnSpec() == 1){
            List<Map> productCategory = new ArrayList<>();
            Map idsMap = new HashMap();
            List<ShSpecificationEntity> shSpecificationEntities = shSpecificationService.findByGoodsId(shGoods.getId());
            for (ShSpecificationEntity entity : shSpecificationEntities){
                List<ShGoodsSpecificationEntity> specificationEntities = shGoodsSpecificationService.findByGoodsIdAndSpecId(shGoods.getId(),entity.getId());
                List<String> nameList = specificationEntities.stream().map(ShGoodsSpecificationEntity::getValue).collect(Collectors.toList());
                List<Long> idList = specificationEntities.stream().map(ShGoodsSpecificationEntity::getId).collect(Collectors.toList());
                Map specMap = new HashMap();
                specMap.put("key",entity.getName());
                specMap.put("value",nameList);
                productCategory.add(specMap);
                for (int i=0;i<idList.size();i++){
                    idsMap.put(idList.get(i),i);
                }
            }

            shGoods.setProductCategory(productCategory);
            Map<String, Map> productMap = new HashMap<>();

            List<ShProductEntity> shProductEntities = shProductService.findByGoodsIdAnIsDefault(shGoods.getId(),0);

            Map<String,ShProductEntity> spMap = shProductEntities.stream().collect(Collectors.toMap(ShProductEntity::getSpecKey,shProductEntity-> shProductEntity));
            Iterator<Map.Entry<String, ShProductEntity>> it = spMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, ShProductEntity> entry = it.next();
                String key = entry.getKey();
                logger.info("key= " + entry.getKey() + " and value= " + entry.getValue());
                String [] keys = key.split("_");
                List<Long> ids = new ArrayList<>();
                for (int j=0;j<keys.length;j++) {
                    System.out.println(idsMap.get(Long.parseLong(keys[j])));
                    ids.add(Long.parseLong(idsMap.get(Long.parseLong(keys[j])).toString()));
                }
                String specKey = StringUtils.join(ids,"_");

                Map entityMap = new HashMap();
                ShProductEntity entity = entry.getValue();
                entityMap.put("marketPrice",entity.getMarketPrice());
                entityMap.put("retailPrice",entity.getRetailPrice());
                entityMap.put("stockNumber",entity.getStockNumber());
                entityMap.put("thumbnailUrl",entity.getThumbnailUrl());
                productMap.put(specKey,entityMap);
            }
            shGoods.setProductMap(productMap);

        }


        List<Map<String,String>> fileList = new ArrayList<>();

        if(BaseUtils.isNotEmpty(shGoods.getListPicUrl())){
            String[] urls = shGoods.getListPicUrl().split(",");
            for(String url : urls){
               Map<String,String> map = Maps.newHashMap();
               map.put("url",url);
               fileList.add(map);
            }
        }
        shGoods.setFileList(fileList);
        return R.ok().put("shGoods", shGoods);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ShGoodsEntity shGoods){
        shGoods.setGoodsSn(BaseUtils.get32UUID());
        shGoods.setAppid(this.getAppid());
        shGoodsService.saveAndProduct(shGoods);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ShGoodsEntity shGoods){

        shGoodsService.saveAndProduct(shGoods);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
//			shGoodsService.removeByIds(Arrays.asList(ids));

        for(Long id:ids){
            shGoodsService.updateIsDeleteByIds(Arrays.asList(ids));
        }

        return R.ok();
    }

    /**
     * 修改是否上架
     */
    @RequestMapping("changeisonsale")
    public R changeisonsale(@RequestBody ShGoodsEntity shGoodsEntity){
        shGoodsService.updateIsOnSaleById(shGoodsEntity);
        return R.ok();
    }

}
