package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.ShSearchHistoryEntity;

import java.util.Map;

/**
 * 搜索历史
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:46
 */
public interface ShSearchHistoryService extends BaseService<ShSearchHistoryEntity>,IService<ShSearchHistoryEntity> {

    /**
     * 通过用户ID删除搜索记录
     * @param userid 用户ID
     */
    void deleteByUserId(Long userid);
}

