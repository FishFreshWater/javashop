package com.plusesb.controller.api;

import com.plusesb.annotation.IgnoreAuth;
import com.plusesb.annotation.LoginUser;
import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.*;
import com.plusesb.service.*;
import com.plusesb.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.management.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 商城首页
 *
 * @author linyuchi
 * @email xunli-03@163.com
 *
 * @date 2018-09-09 19:43:02
 */
@Api(tags = "商城专题接口")
@RestController
@RequestMapping("api/sh_topic")
public class ApiShTopicController extends ApiShAbstractController{

    @Autowired
    private ShTopicService shTopicService;

    /**
     * 专题分页
     */
    @ApiOperation(value = "专题列表分页")
    @IgnoreAuth
    @GetMapping("list")
    public R list(@RequestParam(value = "page", defaultValue = "1") Long page,
                       @RequestParam(value = "size", defaultValue = "10") Long size) {
        SearchDTO simpleSearchDTO = new SearchDTO();
        simpleSearchDTO.setPageIndex(page);
        simpleSearchDTO.setPageSize(size);
        simpleSearchDTO.addFiled("appid","eq",this.getAppid());
        simpleSearchDTO.setSqlSelect(new String[]{"id, title, price_info, scene_pic_url,subtitle"});
        //查询列表数据
        PageDTO<ShTopicEntity> topicEntities = shTopicService.findPageBySimpleSearch(simpleSearchDTO);
        return R.ok().put("data",topicEntities);
    }

    /**
     * 专题详情
     */
    @ApiOperation(value = "专题详情")
    @IgnoreAuth
    @GetMapping("detail")
    public R detail(@LoginUser ShUserEntity loginUser, Long id) {
        ShTopicEntity topicEntity = shTopicService.getById(id);
        return R.ok().put("data",topicEntity);
    }

    /**
     *相关专题
     */
    @ApiOperation(value = "相关专题")
    @IgnoreAuth
    @GetMapping("related")
    public R related( Long id) {

        List<ShGoodsEntity> goodsEntities = shTopicService.findRelatedGoodsByTopicId(id);
        return R.ok().put("data",goodsEntities);
    }
}
