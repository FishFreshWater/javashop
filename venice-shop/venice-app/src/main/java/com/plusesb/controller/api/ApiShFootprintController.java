package com.plusesb.controller.api;

import com.google.common.collect.Maps;
import com.plusesb.annotation.LoginUser;
import com.plusesb.dto.PageDTO;
import com.plusesb.entity.ShFootprintEntity;
import com.plusesb.entity.ShUserEntity;
import com.plusesb.service.ShFootprintService;
import com.plusesb.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 用户足迹
 *
 * @author linyuchi
 * @email xunli-03@163.com
 *
 * @date 2018-09-09 19:43:02
 */
@Api(tags = "用户足迹")
@RestController
@RequestMapping("api/sh_footprint")
public class ApiShFootprintController extends ApiShAbstractController{


    @Autowired
    private ShFootprintService shFootprintService;

    /**
     */
    @ApiOperation(value = "删除足迹")
    @ApiImplicitParams({@ApiImplicitParam(name = "footprintId", value = "足迹id", paramType = "path", required = true)})
    @PostMapping("delete")
    public R delete(@LoginUser ShUserEntity loginUser, Long footprintId) {
        if (footprintId == null) {
            return R.error("删除出错");
        }
        //删除当天的同一个商品的足迹
        shFootprintService.removeById(footprintId);
        return R.ok("删除成功");
    }

    /**
     */
    @ApiOperation(value = "获取足迹列表")
    @PostMapping("list")
    public Object list(@LoginUser ShUserEntity loginUser,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Map<String, Object> resultObj = new HashMap<String, Object>();

        Map<String,Object> params = Maps.newHashMap();
        params.put("appid",getAppid());
        params.put("user_id",loginUser.getId());
        params.put("pageIndex",page);
        params.put("pageSize",size);
        PageDTO<ShFootprintEntity> pageDTO = shFootprintService.findBySql(params);

        Map<Date,List<ShFootprintEntity>> map = pageDTO.getList().stream().collect(Collectors.groupingBy(ShFootprintEntity::getCreateTime));

        List<List<ShFootprintEntity>> footprintVoList = new ArrayList<List<ShFootprintEntity>>();
        for (Map.Entry<Date, List<ShFootprintEntity>> entry : map.entrySet()) {
            footprintVoList.add(entry.getValue());
        }
        resultObj.put("totalSize", pageDTO.getTotalCount());
        resultObj.put("totalPage", pageDTO.getTotalPage());
        resultObj.put("pageSize", pageDTO.getPageSize());
        resultObj.put("pageIndex", pageDTO.getCurrPage());
        resultObj.put("data", footprintVoList);

        return R.ok(resultObj);
    }

}
