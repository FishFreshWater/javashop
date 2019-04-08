package com.plusesb.controller.api;

import com.plusesb.annotation.IgnoreAuth;
import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.ShBrandEntity;
import com.plusesb.service.ShBrandService;
import com.plusesb.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 商城品牌
 *
 * @author linyuchi
 * @email xunli-03@163.com
 *
 * @date 2018-09-09 19:43:02
 */
@Api(tags = "品牌")
@RestController
@RequestMapping("api/sh_brand")
public class
ApiShBrandController extends ApiShAbstractController{

    @Autowired
    private ShBrandService shBrandService;

    /**
     * 分页获取品牌
     */
    @ApiOperation(value = "分页获取品牌")
    @IgnoreAuth
    @GetMapping("list")
    public R list(@RequestParam(value = "page", defaultValue = "1") Long page,
                       @RequestParam(value = "size", defaultValue = "10") Long size) {

        SearchDTO simpleSearchDTO = new SearchDTO();
        simpleSearchDTO.setPageSize(size);
        simpleSearchDTO.setPageIndex(page);
        simpleSearchDTO.addFiled("appid","eq",this.getAppid());
        simpleSearchDTO.setSqlSelect(new String[]{ "id, name, floor_price, thumbnail_url"});


        PageDTO<ShBrandEntity> brandEntityList = shBrandService.findPageBySimpleSearch(simpleSearchDTO);
        return R.ok().put("items",brandEntityList);
    }

    /**
     * 品牌详情
     */
    @ApiOperation(value = "品牌详情")
    @IgnoreAuth
    @GetMapping("detail")
    public R detail(@RequestParam Long id) {

        ShBrandEntity entity = shBrandService.getById(id);
        return  R.ok().put("brand", entity);

    }
}
