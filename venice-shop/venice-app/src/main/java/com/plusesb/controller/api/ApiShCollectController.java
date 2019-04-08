package com.plusesb.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.plusesb.annotation.LoginUser;
import com.plusesb.dto.SearchFieldDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.ShCollectEntity;
import com.plusesb.entity.ShUserEntity;
import com.plusesb.service.ShCollectService;
import com.plusesb.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 用户收藏
 *
 * @author linyuchi
 * @email xunli-03@163.com
 *
 * @date 2018-09-09 19:43:02
 */
@Api(tags = "用户收藏")
@RestController
@RequestMapping("api/sh_collect")
public class ApiShCollectController extends ApiShAbstractController{

    @Autowired
    private ShCollectService shCollectService;

    /**
     * 获取用户收藏
     */
    @ApiOperation(value = "获取用户收藏")
    @GetMapping("list")
    public Object list(@LoginUser ShUserEntity loginUser) {

        SearchDTO param = new SearchDTO();
        param.addFiled("user_id","eq",loginUser.getId());
        param.addFiled("appid", "eq",getAppid());

        Map<String,Object> map = Maps.newHashMap();
        List<SearchFieldDTO> searchFieldDTOList = param.getFields();
        for(SearchFieldDTO searchFieldDTO : searchFieldDTOList){
            map.put(searchFieldDTO.getFiled(),searchFieldDTO.getData());
        }

        List<ShCollectEntity> collectEntities = shCollectService.findAllByMap(map);

        return R.ok().put("data",collectEntities);
    }

    /**
     * 获取用户收藏
     */
    @ApiOperation(value = "添加取消收藏")
    @ApiImplicitParams({ @ApiImplicitParam(name = "goodsId", value = "goodsId", paramType = "query", required = true)})
    @PostMapping("addordelete")
    public Object addordelete(@LoginUser ShUserEntity loginUser) {
        JSONObject jsonParam = getJsonRequest();
        Long goodsId = jsonParam.getLongValue("goodsId");

        SearchDTO param = new SearchDTO();
        param.addFiled("user_id","eq",loginUser.getId());
        param.addFiled("goods_id","eq",goodsId);

        List<ShCollectEntity> collectEntities = shCollectService.findAllBySimpleSearch(param);

        String handleType = "add";
        if (null == collectEntities || collectEntities.size() < 1) {
            ShCollectEntity collectEntity = new ShCollectEntity();
            collectEntity.setAddTime(new Date());
            collectEntity.setAppid(this.getAppid());
            collectEntity.setGoodsId(goodsId);
            collectEntity.setUserId(loginUser.getId());
            //添加收藏
            shCollectService.save(collectEntity);
        } else {
            //取消收藏
           shCollectService.removeById(collectEntities.get(0).getId());
            handleType = "delete";
        }
        return R.ok().put("data",handleType);
    }
}
