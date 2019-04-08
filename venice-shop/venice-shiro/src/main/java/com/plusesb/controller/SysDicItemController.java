package com.plusesb.controller;

import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.SysDicItemEntity;
import com.plusesb.service.SysDicItemService;
import com.plusesb.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

;
;



/**
 * 字典表
 *
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2018-12-24 15:03:55
 */
@Api(tags = "字典接口")
@RestController
@RequestMapping("sys/dicitem")
public class SysDicItemController extends AbstractController{
    @Autowired
    private SysDicItemService sysDicItemService;

    /**
     * 列表
     */
    @ApiOperation(value = "获取字典列表接口", response = List.class)
    @RequiresPermissions("sys:dictype:list")
    @GetMapping("/list")
    public R list(){
        SearchDTO simpleSearchDTO = getSearchDtoFromJqGrid();
        PageDTO<SysDicItemEntity> page = sysDicItemService.findPageBySimpleSearch(simpleSearchDTO);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @ApiOperation(value = "获取字典信息接口", response = R.class)
    @RequiresPermissions("sys:dictype:info")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){

        SysDicItemEntity sysDicItem = sysDicItemService.getById(id);
        return R.ok().put("sysDicItem", sysDicItem);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存字典信息接口", response = R.class)
    @RequiresPermissions("sys:dictype:save")
    @PostMapping("/save")
    public R save(@RequestBody SysDicItemEntity sysDicItem){

        sysDicItemService.save(sysDicItem);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改字典信息接口", response = R.class)
    @RequiresPermissions("sys:dictype:update")
    @PostMapping("/update")
    public R update(@RequestBody SysDicItemEntity sysDicItem){

        sysDicItemService.updateById(sysDicItem);
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除字典信息接口", response = R.class)
    @RequiresPermissions("sys:dictype:delete")
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){

        sysDicItemService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
