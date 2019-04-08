package com.plusesb.controller.api;

import com.google.common.collect.Maps;
import com.plusesb.annotation.IgnoreAuth;
import com.plusesb.annotation.LoginUser;
import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.ShFootprintEntity;
import com.plusesb.entity.ShSearchHistoryEntity;
import com.plusesb.entity.ShUserEntity;
import com.plusesb.service.ShFootprintService;
import com.plusesb.service.ShSearchHistoryService;
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

import javax.management.Query;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 搜索
 *
 * @author linyuchi
 * @email xunli-03@163.com
 *
 * @date 2018-09-09 19:43:02
 */
@Api(tags = "搜索")
@RestController
@RequestMapping("api/sh_search")
public class ApiShSearchController extends ApiShAbstractController{


    @Autowired
    private ShSearchHistoryService shSearchHistoryService;
    /**
     * 　　index
     */
    @ApiOperation(value = "搜索商品列表")
    @PostMapping("index")
    public R index(@LoginUser ShUserEntity loginUser) {
        Map<String, Object> resultObj = new HashMap();
//        Map param = new HashMap();
//        param.put("is_default", 1);
//        param.put("page", 1);
//        param.put("limit", 1);
//        param.put("sidx", "id");
//        param.put("order", "asc");
//        List<KeywordsVo> keywordsEntityList = keywordsService.queryList(param);
//        //取出输入框默认的关键词
//        KeywordsVo defaultKeyword = null != keywordsEntityList && keywordsEntityList.size() > 0 ? keywordsEntityList.get(0) : null;
//        //取出热闹关键词
//        param = new HashMap();
//        param.put("fields", "distinct keyword,is_hot");
//        param.put("page", 1);
//        param.put("limit", 10);
//        param.put("sidx", "id");
//        param.put("order", "asc");
//        Query query = new Query(param);
//        List<Map> hotKeywordList = keywordsService.hotKeywordList(query);
        //
        SearchDTO historyParam = new SearchDTO();
        historyParam.addFiled("user_id","eq",loginUser.getId());
        historyParam.addSort("create_time",false);
        historyParam.setSqlSelect(new String[]{"keyword"});
        List<ShSearchHistoryEntity> historyVoList = shSearchHistoryService.findAllBySimpleSearch(historyParam);
        String[] historyKeywordList = historyVoList.stream().map(ShSearchHistoryEntity::getKeyword).toArray(String[]::new);
        //
//        resultObj.put("defaultKeyword", defaultKeyword);
        resultObj.put("historyKeywordList", historyKeywordList);
//        resultObj.put("hotKeywordList", hotKeywordList);
        return R.ok(resultObj);
    }

    /**
     * 　　helper
     */
    @ApiOperation(value = "搜索商品")
    @ApiImplicitParams({@ApiImplicitParam(name = "keyword", value = "关键字", paramType = "path", required = true)})
    @IgnoreAuth
    @PostMapping("helper")
    public R helper(@LoginUser ShUserEntity loginUser, String keyword) {
//        Map param = new HashMap();
//        param.put("fields", "distinct keyword");
//        param.put("keyword", keyword);
//        param.put("limit", 10);
//        param.put("offset", 0);
//        List<KeywordsVo> keywords = keywordsService.queryList(param);
//        String[] keys = new String[keywords.size()];
//        if (null != keywords) {
//            int i = 0;
//            for (KeywordsVo keywordsVo : keywords) {
//                keys[i] = keywordsVo.getKeyword();
//                i++;
//            }
//        }
        //
        return R.ok();
    }
    /**
     * 　　clearhistory
     */
    @PostMapping("clearhistory")
    public R clearhistory(@LoginUser ShUserEntity loginUser) {

        shSearchHistoryService.deleteByUserId(loginUser.getId());
        //
        return R.ok();
    }
}
