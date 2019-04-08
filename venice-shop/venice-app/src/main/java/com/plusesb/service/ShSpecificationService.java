package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.ShSpecificationEntity;

import java.util.List;
import java.util.Map;

/**
 * 规格
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-10-11 22:10:48
 */
public interface ShSpecificationService extends BaseService<ShSpecificationEntity>,IService<ShSpecificationEntity> {

    /**
     * 删除数据
     * @param id
     */
    void deleteByGoodsId(Long id);

    /**
     * 通过商品ID查询规格
     * @param id 商品ID
     * @return
     */
    List<ShSpecificationEntity> findByGoodsId(Long id);
}

