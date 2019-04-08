package com.plusesb.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.plusesb.dto.PageDTO;
import com.plusesb.entity.ShGoodsEntity;
import com.plusesb.utils.BaseUtils;
import org.springframework.stereotype.Service;
import com.plusesb.entity.ShFootprintEntity;
import com.plusesb.service.ShFootprintService;
import com.plusesb.mapper.ShFootprintMapper;

import java.util.Map;


@Service("shFootprintService")
public class ShFootprintServiceImpl extends BaseServiceImpl<ShFootprintMapper, ShFootprintEntity> implements ShFootprintService {


    @Override
    public PageDTO<ShFootprintEntity> findBySql(Map<String, Object> params) {

        Long pageIndex = BaseUtils.isNotEmpty(params.get("pageIndex")) ? 1L : Long.getLong(params.get("pageIndex").toString());
        Long pageSize = BaseUtils.isNotEmpty(params.get("pageSize")) ? 10L : Long.getLong(params.get("pageSize").toString()); ;
        Page<ShFootprintEntity> page = new Page<>(pageIndex, pageSize);
        page.setRecords(baseMapper.findPageBySql(page,params));
        return new PageDTO<>(page);
    }
}
