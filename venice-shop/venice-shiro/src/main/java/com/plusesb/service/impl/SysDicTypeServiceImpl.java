package com.plusesb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.plusesb.entity.SysDicItemEntity;
import com.plusesb.exception.RRException;
import com.plusesb.service.SysDicItemService;
import com.plusesb.utils.BaseUtils;
import com.plusesb.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.plusesb.entity.SysDicTypeEntity;
import com.plusesb.service.SysDicTypeService;
import com.plusesb.mapper.SysDicTypeMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("sysDicTypeService")
public class SysDicTypeServiceImpl extends BaseServiceImpl<SysDicTypeMapper, SysDicTypeEntity> implements SysDicTypeService {

    @Autowired
    private SysDicItemService sysDicItemService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R removeByIdsNoItem(Long[] ids) {

        try {
            for (Long id :ids ){
                List<SysDicItemEntity> sysDicTypeList = sysDicItemService.findByTypeId(id);
                if (BaseUtils.isNotEmpty(sysDicTypeList)&&sysDicTypeList.size()>0){
                    throw new  RRException(id+":存在字典，请先删除！");
                }
                baseMapper.deleteById(id);
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error(e.getMessage());
        }

        return R.ok();
    }

    @Override
    public List<SysDicTypeEntity> findByCode(String code) {
        return baseMapper.selectList(new QueryWrapper<SysDicTypeEntity>().eq("code",code));
    }
}
