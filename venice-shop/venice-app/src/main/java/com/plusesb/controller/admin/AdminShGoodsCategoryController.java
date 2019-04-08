package com.plusesb.controller.admin;

import com.plusesb.controller.AbstractController;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.ShGoodsCategoryEntity;
import com.plusesb.entity.ShGoodsEntity;
import com.plusesb.service.ShGoodsCategoryService;
import com.plusesb.service.ShGoodsService;
import com.plusesb.utils.BaseUtils;
import com.plusesb.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

;
;



/**
 * 导航
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:43:50
 */
@RestController
@RequestMapping("miniapp/shgoodscategory")
public class AdminShGoodsCategoryController extends AdminShAbstractController{
    @Autowired
    private ShGoodsCategoryService shGoodsCategoryService;

    @Autowired
    private ShGoodsService shGoodsService;
    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(){
        SearchDTO simpleSearchDTO = getSearchDtoFromJqGrid();
        simpleSearchDTO.addFiled("appid","eq",this.getAppid());
        List<ShGoodsCategoryEntity> list = shGoodsCategoryService.findAllBySimpleSearch(simpleSearchDTO);
        for (ShGoodsCategoryEntity shGoodsCategory:list){
            if (BaseUtils.isNotEmpty(shGoodsCategory.getParentId())){
                shGoodsCategory.setParentCategory(shGoodsCategoryService.getById(shGoodsCategory.getParentId()));
            }
        }
        return R.ok().put("items", list);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){

        ShGoodsCategoryEntity shGoodsCategory = shGoodsCategoryService.getById(id);
        if (BaseUtils.isNotEmpty(shGoodsCategory.getParentId())){
            shGoodsCategory.setParentCategory(shGoodsCategoryService.getById(shGoodsCategory.getParentId()));
        }
        return R.ok().put("shGoodsCategory", shGoodsCategory);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ShGoodsCategoryEntity shGoodsCategory){

        shGoodsCategory.setAppid(this.getAppid());
        shGoodsCategoryService.save(shGoodsCategory);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody ShGoodsCategoryEntity shGoodsCategory){
        shGoodsCategoryService.updateById(shGoodsCategory);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){

        for (Long id:ids){
            SearchDTO simpleSearchDTO = new SearchDTO();
            simpleSearchDTO.addFiled("category_id","eq",id);
            List<ShGoodsEntity> shGoodsEntities = shGoodsService.findAllBySimpleSearch(simpleSearchDTO);
            ShGoodsCategoryEntity shGoodsCategoryEntity = shGoodsCategoryService.getById(id);
            if (shGoodsEntities.size()>0){
                return R.error("类别"+shGoodsCategoryEntity.getName()+"存在商品无法删除！");
            }
        }
        shGoodsCategoryService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
