package com.plusesb.controller.admin;

import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.ShUserEntity;
import com.plusesb.service.ShUserService;
import com.plusesb.utils.BaseUtils;
import com.plusesb.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;




/**
 * 会员信息
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:46
 */
@RestController
@RequestMapping("miniapp/shuser")
public class AdminShUserController extends AdminShAbstractController{
    @Autowired
    private ShUserService shUserService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(){
        SearchDTO simpleSearchDTO = getSearchDtoFromJqGrid();
        simpleSearchDTO.addFiled("appid","eq",this.getAppid());
        PageDTO<ShUserEntity> page = shUserService.findPageBySimpleSearch(simpleSearchDTO);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){

			ShUserEntity shUser = shUserService.getById(id);

        return R.ok().put("shUser", shUser);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ShUserEntity shUser){
        shUser.setAppid(this.getAppid());
        shUserService.save(shUser);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody ShUserEntity shUser){

        if(BaseUtils.isNotEmpty(shUser)  && BaseUtils.isNotEmpty(shUser.getId())){
            ShUserEntity targetUser = shUserService.getById(shUser.getId());
            if(BaseUtils.isNotEmpty(targetUser)){
                if(BaseUtils.isNotEmpty(shUser.getNickname())){
                    targetUser.setNickname(shUser.getNickname());
                }
                if(BaseUtils.isNotEmpty(shUser.getMobile())){
                    targetUser.setMobile(shUser.getMobile());
                }
                if(BaseUtils.isNotEmpty(shUser.getEmail())){
                    targetUser.setEmail(shUser.getEmail());
                }
                if(BaseUtils.isNotEmpty(shUser.getCity())){
                    targetUser.setCity(shUser.getCity());
                }
                if(BaseUtils.isNotEmpty(shUser.getGender())){
                    targetUser.setGender(shUser.getGender());
                }
                shUserService.updateById(targetUser);
                return R.ok();
            }
            return R.error("不存在编号为[" + shUser.getId() +"]的用户");
        }
        return R.error("请确认用户信息是否存在!");
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
			shUserService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
