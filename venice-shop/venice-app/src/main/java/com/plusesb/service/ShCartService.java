package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.ShCartEntity;

import java.util.List;
import java.util.Map;

/**
 * 购物车
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:47
 */
public interface ShCartService extends BaseService<ShCartEntity>,IService<ShCartEntity> {



    /**
     * 更新勾选状态
     * @param productIdArray id列表
     * @param isChecked 勾选状态
     * @param id 用户id
     */
    void updateCheck(String[] productIdArray, Integer isChecked, Long id);

    /**
     * 删除购物车中产品
     * @param userId
     * @param productIdsArray
     */
    void deleteByUserAndProductIds(Long userId, String[] productIdsArray);

    /**
     * 查询用户已经勾选的购物车列表
     * @param id 用户ID
     * @param checked 是否勾选
     * @return
     */
    List<ShCartEntity> findByUserIdAndChecked(Long id, int checked);

    /**
     * 通过用户和appid查询数据
     * @param userId
     * @return
     */
    List<ShCartEntity> findByUserId(Long userId);
}

