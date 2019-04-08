package com.plusesb.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.ShGoodsEntity;
import com.plusesb.entity.ShTopicEntity;
import com.plusesb.entity.ShTopicGoodsEntity;
import com.plusesb.service.ShTopicGoodsService;
import com.plusesb.service.ShTopicService;
import com.plusesb.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

;
;



/**
 * 活动
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:46
 */
@RestController
@RequestMapping("miniapp/shtopic")
public class AdminShTopicController extends AdminShAbstractController{
    @Autowired
    private ShTopicService shTopicService;
    @Autowired
    private ShTopicGoodsService shTopicGoodsService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(){
        SearchDTO simpleSearchDTO = getSearchDtoFromJqGrid();
        simpleSearchDTO.addFiled("appid","eq",this.getAppid());
        PageDTO<ShTopicEntity> page = shTopicService.findPageBySimpleSearch(simpleSearchDTO);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){

        ShTopicEntity shTopic = shTopicService.getById(id);
        List<ShGoodsEntity> goodsEntities = shTopicService.findRelatedGoodsByTopicId(shTopic.getId());
        shTopic.setShGoodsEntities(goodsEntities);
        return R.ok().put("shTopic", shTopic);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ShTopicEntity shTopic){

        shTopic.setAppid(this.getAppid());
        shTopicService.save(shTopic);
        shTopicGoodsService.saveList(shTopic.getShGoodsEntities(),shTopic.getId(),this.getAppid());
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody ShTopicEntity shTopic){

        shTopicService.updateById(shTopic);
        shTopicGoodsService.saveList(shTopic.getShGoodsEntities(),shTopic.getId(), this.getAppid());

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){

		shTopicService.removeByIds(Arrays.asList(ids));
        shTopicGoodsService.remove(new QueryWrapper<ShTopicGoodsEntity>().in("topic_id",ids));
        return R.ok();
    }

}
