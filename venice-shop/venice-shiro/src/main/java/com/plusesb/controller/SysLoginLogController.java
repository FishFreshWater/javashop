package com.plusesb.controller;

import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.SysLoginLogEntity;
import com.plusesb.service.SysLoginLogService;
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
 * 登陆日志
 *
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2018-12-25 00:13:15
 */
@Api(tags = "登陆日志")
@RestController
@RequestMapping("sys/loginlog")
public class SysLoginLogController extends AbstractController{
    @Autowired
    private SysLoginLogService sysLoginLogService;

    /**
     * 列表
     */
    @ApiOperation(value = "获取登陆日志列表接口", response = List.class)
    @RequiresPermissions("sys:loginlog:list")
    @GetMapping("/list")
    public R list(){
        SearchDTO simpleSearchDTO = getSearchDtoFromJqGrid();
        PageDTO<SysLoginLogEntity> page = sysLoginLogService.findPageBySimpleSearch(simpleSearchDTO);
        return R.ok().put("page", page);
    }
}
