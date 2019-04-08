package com.plusesb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.plusesb.entity.ShCollectEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户收藏
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-29 02:59:11
 */
public interface ShCollectService extends BaseService<ShCollectEntity>,IService<ShCollectEntity> {


    List<ShCollectEntity> findAllByMap(Map<String,Object> map);
}

