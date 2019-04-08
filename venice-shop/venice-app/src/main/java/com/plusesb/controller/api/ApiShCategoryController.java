package com.plusesb.controller.api;

import com.plusesb.annotation.IgnoreAuth;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.ShGoodsCategoryEntity;
import com.plusesb.service.ShGoodsCategoryService;
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
 * 分类接口
 *
 * @author linyuchi
 * @email xunli-03@163.com
 *
 * @date 2018-09-09 19:43:02
 */
@Api(tags = "分类接口")
@RestController
@RequestMapping("api/sh_category")
public class ApiShCategoryController extends ApiShAbstractController{

    @Autowired
    private ShGoodsCategoryService shGoodsCategoryService;

    /**
     * 获取分类栏目数据
     */
    @ApiOperation(value = "获取分类栏目数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id", paramType = "query", required = false),
            @ApiImplicitParam(name = "page", value = "page", paramType = "query", required = false),
            @ApiImplicitParam(name = "size", value = "size", paramType = "query", required = false)})
    @IgnoreAuth
    @GetMapping(value = "index")
    public R index(Long id,
                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "size", defaultValue = "10") Integer size) {

        SearchDTO params = new SearchDTO();
        params.addSort("sort_order",false);
        params.addFiled("appid","eq",this.getAppid());
        params.addFiled("parent_id","isNull","isNull");
        //查询列表数据
        List<ShGoodsCategoryEntity> data = shGoodsCategoryService.findAllBySimpleSearch(params);
        //
        ShGoodsCategoryEntity currentCategory = null;
        if (null != id) {
            currentCategory = shGoodsCategoryService.getById(id);
        }
        if (null == currentCategory && null != data && data.size() != 0) {
            currentCategory = data.get(0);
        } else {
            currentCategory = new ShGoodsCategoryEntity();
        }

        //获取子分类数据
        if (null != currentCategory && null != currentCategory.getId()) {
            SearchDTO subParams = new SearchDTO();
            subParams.addSort("sort_order",true);
            subParams.addFiled("appid","eq",this.getAppid());
            subParams.addFiled("parent_id", "eq",currentCategory.getId());
            currentCategory.setSubCategoryList(shGoodsCategoryService.findAllBySimpleSearch(subParams));
        }
        Map resultObj = new HashMap();
        resultObj.put("categoryList", data);
        resultObj.put("currentCategory", currentCategory);

        return R.ok(resultObj);
    }

    /**
     *分类目录当前分类数据接口
     */
    @ApiOperation(value = "分类目录当前分类数据接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id", paramType = "query", required = false)})
    @IgnoreAuth
    @GetMapping(value = "current")
    public R current(Long id) {

        SearchDTO params = new SearchDTO();
        ShGoodsCategoryEntity currentCategory = null;
        if (null != id) {
            currentCategory = shGoodsCategoryService.getById(id);
        }
        //获取子分类数据
        if (null != currentCategory && null != currentCategory.getId()) {
            params.addFiled("parent_id", "eq",currentCategory.getId());
            currentCategory.setSubCategoryList(shGoodsCategoryService.findAllBySimpleSearch(params));
        }

        Map resultObj = new HashMap();
        resultObj.put("currentCategory", currentCategory);

        return R.ok(resultObj);
    }
}
