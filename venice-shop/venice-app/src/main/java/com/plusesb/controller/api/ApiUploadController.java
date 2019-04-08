package com.plusesb.controller.api;

import com.plusesb.annotation.IgnoreAuth;
import com.plusesb.config.cloud.OSSFactory;
import com.plusesb.entity.SysOssEntity;
import com.plusesb.exception.RRException;
import com.plusesb.service.SysOssService;
import com.plusesb.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 作者: @author Harmon <br>
 * 时间: 2017-09-08 13:20<br>
 * 描述: ApiUploadController <br>
 */
@Api(tags = "上传")
@RestController
@RequestMapping("/api/upload")
public class ApiUploadController extends ApiShAbstractController {

    @Autowired
    private SysOssService sysOssService;
    /**
     * 上传文件
     */
	@ApiOperation(value = "上传文件")
    @IgnoreAuth
    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file) throws Exception {

        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }
        //上传文件
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);

        //保存文件信息
        SysOssEntity ossEntity = new SysOssEntity();
        ossEntity.setUrl(url);
        sysOssService.save(ossEntity);

        return R.ok().put("data",url);
    }
}