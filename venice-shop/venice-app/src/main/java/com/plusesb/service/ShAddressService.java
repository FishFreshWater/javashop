package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.ShAddressEntity;

/**
 * 用户地址信息
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:46
 */
public interface ShAddressService extends BaseService<ShAddressEntity>,IService<ShAddressEntity> {

    /**
     * 更新除ID外为非默认
     * @param id
     * @param loginUserId
     */
    void updateNotDefault(Long id, Long loginUserId);
}

