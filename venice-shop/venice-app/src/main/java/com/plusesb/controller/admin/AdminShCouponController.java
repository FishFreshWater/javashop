package com.plusesb.controller.admin;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.plusesb.entity.ShCouponEntity;
import com.plusesb.controller.AbstractController;
import com.plusesb.service.ShCouponService;
import com.plusesb.dto.PageDTO;;
import com.plusesb.dto.SearchDTO;;
import com.plusesb.utils.R;


/**
 * 优惠券信息
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:43:50
 */
@RestController
@RequestMapping("miniapp/shcoupon")
public class AdminShCouponController extends AdminShAbstractController {
    @Autowired
    private ShCouponService shCouponService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list() {
        SearchDTO simpleSearchDTO = getSearchDtoFromJqGrid();
        simpleSearchDTO.addFiled("appid","eq",this.getAppid());
        PageDTO <ShCouponEntity> page = shCouponService.findPageBySimpleSearch(simpleSearchDTO);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        ShCouponEntity shCoupon = shCouponService.getById(id);

        return R.ok().put("shCoupon", shCoupon);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ShCouponEntity shCoupon) {

        shCoupon.setAppid(this.getAppid());
        shCouponService.save(shCoupon);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody ShCouponEntity shCoupon) {
        shCouponService.updateById(shCoupon);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {

        shCouponService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 上下架
     */
    @PostMapping("/display")
    public R display(@RequestBody ShCouponEntity dto) {

        shCouponService.updateStatusById(dto.getId(),dto.getStatus());
        return R.ok();
    }
}
