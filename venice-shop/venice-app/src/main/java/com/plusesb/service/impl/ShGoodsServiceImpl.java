package com.plusesb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.plusesb.dto.PageDTO;
import com.plusesb.entity.*;
import com.plusesb.mapper.ShGoodsMapper;
import com.plusesb.service.*;
import com.plusesb.utils.BaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service("shGoodsService")
public class ShGoodsServiceImpl extends BaseServiceImpl<ShGoodsMapper, ShGoodsEntity> implements ShGoodsService {


    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ShSpecificationService shSpecificationService;
    @Autowired
    private ShGoodsSpecificationService shGoodsSpecificationService;
    @Autowired
    private ShProductService shProductService;
    @Autowired
    private ShOrderGoodsService shOrderGoodsService;

    @Override
    public ShGoodsEntity updateIsOnSaleById(ShGoodsEntity shGoodsEntity) {

        baseMapper.updateIsOnSaleById(shGoodsEntity.getId(),shGoodsEntity.getIsOnSale());
        return shGoodsEntity;
    }

    @Override
    public PageDTO<ShGoodsEntity> findBySql(Map<String,Object> map) {

        Long pageIndex = BaseUtils.isNotEmpty(map.get("pageIndex")) ?  Long.parseLong(map.get("pageIndex").toString() ): 1L ;
        Long pageSize = BaseUtils.isNotEmpty(map.get("pageSize")) ? Long.parseLong(map.get("pageSize").toString() ): 10L;
        Page<ShGoodsEntity> page = new Page<>(pageIndex, pageSize);

        page.setRecords(baseMapper.findPageBySql(page,map));

        return new PageDTO<>(page);
    }




    @Override
    public List<ShGoodsEntity> findByCategoryIdsAndAppid(List<Long> childCategoryIds, String appId) {
        return baseMapper.selectList(new QueryWrapper<ShGoodsEntity>()
                .select("id", "name", "primary_pic_url as primaryPicUrl", "retail_price retailPrice")
                .in("category_id",childCategoryIds).eq("appid",appId).orderByDesc("sort_order").last(" limit 6"));
    }

    @Override
    public void addSellVolumeByOrderId(Long orderId) {

        List<ShOrderGoodsEntity> shOrderGoodsEntities = shOrderGoodsService.findByOrderId(orderId);
        shOrderGoodsEntities.forEach(shOrderGoodsEntity -> {
            ShGoodsEntity goodsEntity = baseMapper.selectById(shOrderGoodsEntity.getGoodsId());
            int number = goodsEntity.getSellVolume() + shOrderGoodsEntity.getNumber();
            goodsEntity.setSellVolume(number);
            goodsEntity.setStockNumber(goodsEntity.getSellVolume()-shOrderGoodsEntity.getNumber());
            baseMapper.updateById(goodsEntity);
            ShProductEntity shProductEntity = shProductService.getById(shOrderGoodsEntity.getProductId());
            shProductEntity.setStockNumber(shProductEntity.getStockNumber()-shOrderGoodsEntity.getNumber());
            shProductService.updateById(shProductEntity);
        });

    }

    @Override
    public void updateIsDeleteByIds(List<Long> ids) {
        baseMapper.updateIsDeleteByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAndProduct(ShGoodsEntity dto) {

        //如果是修改先清空修改数据
        if(BaseUtils.isNotEmpty(dto.getId())){
            //
            baseMapper.updateById(dto);
        }else {
            baseMapper.insert(dto);
        }
        //是否开启规格
        if(dto.getIsOnSpec() == 1) {
           if (dto.getUpdateSpec() == 1){
               //规格参数
               List<Map> maps = dto.getProductCategory();
               Map  specValueMap = new HashMap();
               Map  specKeyMap = new HashMap();
               shSpecificationService.deleteByGoodsId(dto.getId());
               shGoodsSpecificationService.deleteByGoodsId(dto.getId());
               shProductService.deleteByGoodsId(dto.getId());
               for (int i = 0;i<maps.size();i++){
                   String spec = maps.get(i).get("key").toString();
                   ShSpecificationEntity specEntity = new ShSpecificationEntity();
                   specEntity.setName(spec);
                   specEntity.setAppid(dto.getAppid());
                   specEntity.setGoodsId(dto.getId());
                   shSpecificationService.save(specEntity);
                   List<String> specValue = (List<String>) maps.get(i).get("value");
                   for (int j=0;j<specValue.size();j++){
                       ShGoodsSpecificationEntity shGoodsSpecEntity = new ShGoodsSpecificationEntity();
                       shGoodsSpecEntity.setValue(specValue.get(j));
                       shGoodsSpecEntity.setGoodsId(dto.getId());
                       shGoodsSpecEntity.setSpecificationId(specEntity.getId());
                       shGoodsSpecEntity.setAppid(dto.getAppid());
                       shGoodsSpecificationService.save(shGoodsSpecEntity);
                       specKeyMap.put(i+"_"+j,shGoodsSpecEntity.getId());
                       specValueMap.put(i+"_"+j,shGoodsSpecEntity.getValue());
                   }
               }
               Map productMap = dto.getProductMap();
               Iterator<Map.Entry<String, Map>> it = productMap.entrySet().iterator();
               while (it.hasNext()) {
                   Map.Entry<String, Map> entry = it.next();
                   logger.info("key= " + entry.getKey() + " and value= " + entry.getValue());
                   String key = entry.getKey();
                   String [] keys = key.split("_");
                   List<Long> ids = new ArrayList<>();
                   List<String> vals = new ArrayList<>();
                   for (int j=0;j<keys.length;j++) {
                       Long id = Long.parseLong(specKeyMap.get(j+"_"+keys[j]).toString());
                       String val = specValueMap.get(j+"_"+keys[j]).toString();
                       logger.info("取到拼接的id为"+id);
                       ids.add(id);
                       vals.add(val);
                   }
                   String specKey = org.apache.commons.lang.StringUtils.join(ids,"_");
                   String specValue = org.apache.commons.lang.StringUtils.join(vals,"_");
                   Map value = entry.getValue();
                   ShProductEntity entity = new ShProductEntity();
                   if (BaseUtils.isNotEmpty(value.get("thumbnailUrl"))){
                       entity.setThumbnailUrl(value.get("thumbnailUrl").toString());
                   }
                   if (BaseUtils.isNotEmpty(value.get("stockNumber"))){
                       entity.setStockNumber(Integer.parseInt(value.get("stockNumber").toString()));
                   }
                   if (BaseUtils.isNotEmpty(value.get("marketPrice"))){
                       entity.setMarketPrice(new BigDecimal(value.get("marketPrice").toString()));
                   }
                   if (BaseUtils.isNotEmpty(value.get("retailPrice"))){
                       entity.setRetailPrice(new BigDecimal(value.get("retailPrice").toString()));
                   }
                   entity.setIsDefault(0);
                   entity.setAppid(dto.getAppid());
                   entity.setSpecKey(specKey);
                   entity.setSpecValue(specValue);
                   entity.setGoodsId(dto.getId());
                   shProductService.save(entity);
               }
           }
        }else {
            //更新默认规
            List<ShProductEntity> entities = shProductService.findByGoodsIdAnIsDefault(dto.getId(),1);
            ShProductEntity shProductEntity ;
            if (entities.size()>0){
                shProductEntity = entities.get(0);
            }else {
                shProductEntity = new ShProductEntity();
            }
            shProductEntity.setGoodsId(dto.getId());
            shProductEntity.setIsDefault(1);
            shProductEntity.setAppid(dto.getAppid());
            shProductEntity.setMarketPrice(dto.getMarketPrice());
            shProductEntity.setRetailPrice(dto.getRetailPrice());
            shProductEntity.setStockNumber(dto.getStockNumber());
            shProductEntity.setThumbnailUrl(dto.getPrimaryPicUrl());
            shProductService.saveOrUpdate(shProductEntity);
        }


    }
}


