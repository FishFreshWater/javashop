package com.plusesb.controller.admin;

import com.plusesb.controller.AbstractController;
import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.dto.form.ShOrderReturnDTO;
import com.plusesb.entity.ShOrderReturnEntity;
import com.plusesb.entity.ShOrderReturnLogEntity;
import com.plusesb.entity.enums.OrderReturnCheckStatus;
import com.plusesb.service.ShOrderReturnLogService;
import com.plusesb.service.ShOrderReturnService;
import com.plusesb.utils.R;
import com.plusesb.utils.RequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 订单退款
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-10-08 19:48:28
 */
@RestController
@RequestMapping("miniapp/shorderreturn")
public class AdminShOrderReturnController extends AdminShAbstractController{
    @Autowired
    private ShOrderReturnService shOrderReturnService;
    @Autowired
    private ShOrderReturnLogService shOrderReturnLogService;
    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(){
        SearchDTO simpleSearchDTO = getSearchDtoFromJqGrid();
        simpleSearchDTO.addFiled("appid","eq",this.getAppid());
        PageDTO<ShOrderReturnEntity> page = shOrderReturnService.findPageBySimpleSearch(simpleSearchDTO);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		ShOrderReturnEntity shOrderReturn = shOrderReturnService.getById(id);
        List<ShOrderReturnLogEntity> shOrderReturnLogEntities = shOrderReturnLogService.findByOrderReturnId(id);

        return R.ok().put("shOrderReturn", shOrderReturn).put("shOrderReturnLogList",shOrderReturnLogEntities);
    }

    /**
     * 订单退款审核
     * @return
     */
    @PostMapping(value={"/refund_check"})
    public R refundCheck(@RequestBody ShOrderReturnDTO shOrderReturnDTO, HttpServletRequest request) {
        if (shOrderReturnDTO.getCheckStatus() == OrderReturnCheckStatus.CHECKED.getValue()){
            shOrderReturnService.checkRefundCompelted("退款审核通过:"+shOrderReturnDTO.getComment(),shOrderReturnDTO.getId(),
                    this.getUser().getUsername(), RequestHelper.getRemoteHost(request));
        }else
        {
            shOrderReturnService.checkCancel("退款审核不通过:"+shOrderReturnDTO.getComment(),shOrderReturnDTO.getId(),this.getUser().getUsername());
        }
        return R.ok("操作成功！");
    }

    /**
     * 退货审核
     * @return
     */
    @PostMapping(value={"/return_check"})
    public R returnCheck(@RequestBody  ShOrderReturnDTO shOrderReturnDTO) {
        if (shOrderReturnDTO.getCheckStatus() == OrderReturnCheckStatus.CHECKED.getValue()){
            shOrderReturnService.checkReturnInit("退货审核通过:"+shOrderReturnDTO.getComment(),shOrderReturnDTO.getId(),this.getUser().getUsername());
        }else
        {
            shOrderReturnService.checkCancel("退货审核不通过:"+shOrderReturnDTO.getComment(),shOrderReturnDTO.getId(),this.getUser().getUsername());
        }
        return R.ok("操作成功！");
    }

    /**
     * 订单退货完成
     * @return
     */
    @PostMapping(value={"/check/completed"})
    public R submitCompleted(@RequestBody  ShOrderReturnDTO shOrderReturnDTO,HttpServletRequest request) {
        shOrderReturnService.checkReturnCompelted("退款退货完成:"+shOrderReturnDTO.getComment(),shOrderReturnDTO.getId(),this.getUser().getUsername()
                ,shOrderReturnDTO.getAmount(),RequestHelper.getRemoteHost(request));
        return R.ok("操作成功！");
    }

}
