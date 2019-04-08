package com.plusesb.controller;

import com.plusesb.entity.SysDeptEntity;
import com.plusesb.service.SysDeptService;
import com.plusesb.utils.BaseUtils;
import com.plusesb.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

;



/**
 * 部门表
 *
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2018-12-24 14:45:44
 */
@Api(tags = "部门接口")
@RestController
@RequestMapping("sys/dept")
public class SysDeptController extends AbstractController{
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 列表
     */
    @ApiOperation(value = "获取部门列表接口", response = List.class)
    @RequiresPermissions("sys:dept:list")
    @GetMapping("/list")
    public R list(){
        List<SysDeptEntity> deptList = sysDeptService.findAll();
        for(SysDeptEntity sysDeptEntity : deptList){
            SysDeptEntity parentDeptEntity = sysDeptService.getById(sysDeptEntity.getParentId());
            if(BaseUtils.isNotEmpty(parentDeptEntity)){
                sysDeptEntity.setParentName(parentDeptEntity.getName());
            }
        }

        return R.ok().put("list", deptList);
    }


    /**
     * 信息
     */
    @ApiOperation(value = "获取部门信息接口", response = R.class)
    @RequiresPermissions("sys:dept:info")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){

        SysDeptEntity sysDept = sysDeptService.getById(id);
        return R.ok().put("sysDept", sysDept);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "修改部门信息接口", response = R.class)
    @RequiresPermissions("sys:dept:save")
    @PostMapping("/save")
    public R save(@RequestBody SysDeptEntity sysDept){

        sysDeptService.save(sysDept);
        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改部门信息接口", response = R.class)
    @RequiresPermissions("sys:dept:update")
    @PostMapping("/update")
    public R update(@RequestBody SysDeptEntity sysDept){

        sysDeptService.updateById(sysDept);
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除部门信息接口", response = R.class)
    @RequiresPermissions("sys:dept:delete")
    @PostMapping("/delete/{deptId}")
    public R delete(@PathVariable Long deptId){

        List<SysDeptEntity> sysDeptEntities = sysDeptService.findByParentId(deptId);
        if (BaseUtils.isNotEmpty(sysDeptEntities)&&sysDeptEntities.size()>0){
            return R.error("请先删除子部门");
        }
        sysDeptService.removeById(deptId);
        return R.ok();
    }

}
