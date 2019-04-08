package com.plusesb.controller.admin;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.plusesb.entity.ShAdvertEntity;
import com.plusesb.service.ShAdvertService;
import com.plusesb.dto.PageDTO;;
import com.plusesb.dto.SearchDTO;;
import com.plusesb.utils.R;



/**
 * 广告
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-16 16:06:24
 */
@RestController
@RequestMapping("miniapp/shadvert")
public class AdminShAdvertController extends AdminShAbstractController{
    @Autowired
    private ShAdvertService shAdvertService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(){
        SearchDTO simpleSearchDTO = getSearchDtoFromJqGrid();
        simpleSearchDTO.addFiled("appid","eq",this.getAppid());
        PageDTO<ShAdvertEntity> page = shAdvertService.findPageBySimpleSearch(simpleSearchDTO);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){

        ShAdvertEntity shAdvert = shAdvertService.getById(id);
        return R.ok().put("shAdvert", shAdvert);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ShAdvertEntity shAdvert){

        shAdvert.setAppid(this.getAppid());
			shAdvertService.save(shAdvert);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody ShAdvertEntity shAdvert){

        shAdvertService.updateById(shAdvert);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
			shAdvertService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
    /**
     * 更新是否显示
     * @param msCaseInfo
     * @return
     */
    @RequestMapping("/updatedisplay")
    public R updateDisplay(@RequestBody ShAdvertEntity msCaseInfo){
        shAdvertService.updateStatus(msCaseInfo);
        return R.ok();
    }

}
