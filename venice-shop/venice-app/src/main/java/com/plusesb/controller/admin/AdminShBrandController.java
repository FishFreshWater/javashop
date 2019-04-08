package com.plusesb.controller.admin;

import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.ShBrandEntity;
import com.plusesb.service.ShBrandService;
import com.plusesb.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;




/**
 * 商品品牌
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-19 19:09:24
 */
@RestController
@RequestMapping("miniapp/shbrand")
public class AdminShBrandController extends AdminShAbstractController{
    @Autowired
    private ShBrandService shBrandService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(){
        SearchDTO simpleSearchDTO = getSearchDtoFromJqGrid();
        simpleSearchDTO.addFiled("appid","eq",this.getAppid());
        PageDTO<ShBrandEntity> page = shBrandService.findPageBySimpleSearch(simpleSearchDTO);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){

        ShBrandEntity shBrand = shBrandService.getById(id);
        return R.ok().put("shBrand", shBrand);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ShBrandEntity shBrand){

        shBrand.setAppid(this.getAppid());
		shBrandService.save(shBrand);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody ShBrandEntity shBrand){
			shBrandService.updateById(shBrand);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
			shBrandService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
