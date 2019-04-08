package com.plusesb.service;

import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.BaseEntity;

import java.util.List;

/**
 * 服务基类
 */
public interface BaseService<T extends BaseEntity> {


    /**
     * 通用类型的查询，一般是管理后台会用到
     * @param simpleSearchDTO 查询封装的dto
     * @param <T> 实体类型
     * @return 实体对象类别
     */
    <T> PageDTO<T> findPageBySimpleSearch(SearchDTO simpleSearchDTO);

    /**
     * 通用类型的查询，一般是管理后台会用到
     * @param simpleSearchDTO 查询封装的dto
     * @param <T> 实体类型
     * @return 实体对象类别
     */
    <T> List<T> findAllBySimpleSearch(SearchDTO simpleSearchDTO);

    /**
     * 查询所有列表
     */
    List<T> findAll();

    List<T> findByIds(List<Long> longs);

    /**
     * 通用统计查询
     * @param simpleSearchDTO 查询封装的dto
     * @return
     */
    Integer countBySimpleSearch(SearchDTO simpleSearchDTO);

}
