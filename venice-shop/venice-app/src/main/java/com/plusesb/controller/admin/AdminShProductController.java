package com.plusesb.controller.admin;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.plusesb.entity.ShProductEntity;
import com.plusesb.controller.AbstractController;
import com.plusesb.service.ShProductService;
import com.plusesb.dto.PageDTO;;
import com.plusesb.dto.SearchDTO;;
import com.plusesb.utils.R;



/**
 * 产品表
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:43:50
 */
@RestController
@RequestMapping("miniapp/shproduct")
public class AdminShProductController extends AdminShAbstractController{
    @Autowired
    private ShProductService shProductService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(){
        SearchDTO simpleSearchDTO = getSearchDtoFromJqGrid();
        simpleSearchDTO.addFiled("appid","eq",this.getAppid());
        PageDTO<ShProductEntity> page = shProductService.findPageBySimpleSearch(simpleSearchDTO);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){

		ShProductEntity shProduct = shProductService.getById(id);

        return R.ok().put("shProduct", shProduct);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ShProductEntity shProduct){

        shProduct.setAppid(this.getAppid());
		shProductService.save(shProduct);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody ShProductEntity shProduct){
			shProductService.updateById(shProduct);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
			shProductService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
