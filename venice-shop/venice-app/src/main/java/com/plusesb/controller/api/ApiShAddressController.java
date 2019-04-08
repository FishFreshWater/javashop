package com.plusesb.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.plusesb.annotation.IgnoreAuth;
import com.plusesb.annotation.LoginUser;
import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.ShAddressEntity;
import com.plusesb.entity.ShBrandEntity;
import com.plusesb.entity.ShUserEntity;
import com.plusesb.service.ShAddressService;
import com.plusesb.service.ShBrandService;
import com.plusesb.utils.BaseUtils;
import com.plusesb.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 收货地址
 *
 * @author linyuchi
 * @email xunli-03@163.com
 *
 * @date 2018-09-09 19:43:02
 */
@Api(tags = "收货地址")
@RestController
@RequestMapping("api/sh_address")
public class ApiShAddressController extends ApiShAbstractController{

    @Autowired
    private ShAddressService shAddressService;

    /**
     * 获取用户的收货地址
     */
    @ApiOperation(value = "获取用户的收货地址接口", response = Map.class)
    @GetMapping("list")
    public R list(@LoginUser ShUserEntity loginUser) {

        SearchDTO simpleSearchDTO = new SearchDTO();
        simpleSearchDTO.addFiled("user_id","eq",loginUser.getId());
        List<ShAddressEntity> addressEntities = shAddressService.findAllBySimpleSearch(simpleSearchDTO);
        return R.ok().put("data",addressEntities);
    }

    /**
     * 获取收货地址的详情
     */
    @ApiOperation(value = "获取收货地址的详情", response = Map.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "收获地址ID", required = true, dataType = "Long")})
    @GetMapping("detail")
    public R detail(Long id, @LoginUser ShUserEntity loginUser) {

        ShAddressEntity entity = shAddressService.getById(id);
        //判断越权行为
        if (!entity.getUserId().equals(loginUser.getId())) {
            return R.error(403, "您无权查看");
        }
        return R.ok().put("data",entity);
    }

    /**
     * 添加或更新收货地址
     */
    @ApiOperation(value = "添加或更新收货地址", response = Map.class)
    @PostMapping("save")
    public R save(@LoginUser ShUserEntity loginUser) {

        JSONObject addressJson = this.getJsonRequest();
        ShAddressEntity entity = new ShAddressEntity();
        if (null != addressJson) {
            entity.setId(addressJson.getLong("id"));
            entity.setUserId(loginUser.getId());
            entity.setUserName(addressJson.getString("userName"));
            entity.setPostalCode(addressJson.getString("postalCode"));
            entity.setProvinceName(addressJson.getString("provinceName"));
            entity.setCityName(addressJson.getString("cityName"));
            entity.setCountyName(addressJson.getString("countyName"));
            entity.setDetailInfo(addressJson.getString("detailInfo"));
            entity.setNationalCode(addressJson.getString("nationalCode"));
            entity.setTelNumber(addressJson.getString("telNumber"));
            entity.setIsDefault(addressJson.getInteger("isDefault"));
        }
        if (BaseUtils.isEmpty(entity.getId())||entity.getId() == 0) {
            entity.setId(null);
            shAddressService.save(entity);
        } else {
            shAddressService.updateById(entity);
        }
        //将其余设置为非默认
        if (entity.getIsDefault() == 1){
            shAddressService.updateNotDefault(entity.getId(),loginUser.getId());
        }
        return R.ok().put("data",entity);
    }

    /**
     * 删除指定的收货地址
     */
    @ApiOperation(value = "删除指定的收货地址", response = Map.class)
    @PostMapping("delete")
    public R delete(@LoginUser ShUserEntity loginUser) {

        JSONObject jsonParam = this.getJsonRequest();
        Long id = jsonParam.getLongValue("id");

        ShAddressEntity entity = shAddressService.getById(id);
        //判断越权行为
        if (!entity.getUserId().equals(loginUser.getId())) {
            return R.error(403, "您无权删除");
        }
        shAddressService.removeById(id);
        return R.ok("删除成功");
    }
}
