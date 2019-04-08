package com.plusesb.controller;

import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.SysDicTypeEntity;
import com.plusesb.service.SysDicTypeService;
import com.plusesb.utils.BaseUtils;
import com.plusesb.utils.R;
import com.plusesb.validator.ValidatorUtils;
import com.plusesb.validator.group.AddGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 字典类型表
 *
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2018-12-24 15:03:54
 */
@Api(tags = "字典类型接口")
@RestController
@RequestMapping("sys/dictype")
public class SysDicTypeController extends AbstractController{
    @Autowired
    private SysDicTypeService sysDicTypeService;

    /**
     * 列表
     */
    @ApiOperation(value = "获取字典类型列表接口", response = List.class)
    @GetMapping("/list")
    @RequiresPermissions("sys:dictype:list")
    public R list(){
        SearchDTO simpleSearchDTO = getSearchDtoFromJqGrid();
        PageDTO<SysDicTypeEntity> page = sysDicTypeService.findPageBySimpleSearch(simpleSearchDTO);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @ApiOperation(value = "获取字典类型信息接口", response = R.class)
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:dictype:info")
    public R info(@PathVariable("id") Long id){

        SysDicTypeEntity sysDicType = sysDicTypeService.getById(id);
        return R.ok().put("sysDicType", sysDicType);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "获取字典类型信息接口", response = R.class)
    @PostMapping("/save")
    @RequiresPermissions("sys:dictype:save")
    public R save(@RequestBody SysDicTypeEntity sysDicType){

        ValidatorUtils.validateEntity(sysDicType, AddGroup.class);
        //唯一值校验
        List<SysDicTypeEntity> dicTypeEntities = sysDicTypeService.findByCode(sysDicType.getCode());
        if (BaseUtils.isNotEmpty(dicTypeEntities)&&dicTypeEntities.size()>0){
            return R.error("该字典编码已经存在");
        }
        sysDicTypeService.save(sysDicType);
        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改字典类型信息接口", response = R.class)
    @PostMapping("/update")
    @RequiresPermissions("sys:dictype:update")
    public R update(@RequestBody SysDicTypeEntity sysDicType){

        sysDicTypeService.updateById(sysDicType);
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除字典类型信息接口", response = R.class)
    @RequiresPermissions("sys:dictype:delete")
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){

        return sysDicTypeService.removeByIdsNoItem(ids);
    }

}
