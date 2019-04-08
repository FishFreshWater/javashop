
package com.plusesb.controller;

import com.google.gson.Gson;
import com.plusesb.config.cloud.CloudStorageConfig;
import com.plusesb.config.cloud.OSSFactory;
import com.plusesb.constant.SysConstant;
import com.plusesb.constant.status.CloudService;
import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.SysOssEntity;
import com.plusesb.entity.SysUserEntity;
import com.plusesb.exception.RRException;
import com.plusesb.service.SysConfigService;
import com.plusesb.service.SysOssService;
import com.plusesb.utils.R;
import com.plusesb.validator.ValidatorUtils;
import com.plusesb.validator.group.AliyunGroup;
import com.plusesb.validator.group.QcloudGroup;
import com.plusesb.validator.group.QiniuGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

/**
 * 文件上传
 * 
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2017-03-25 12:13:26
 */
@RestController
@RequestMapping("sys/oss")
public class SysOssController extends  AbstractController{
	@Autowired
	private SysOssService sysOssService;
    @Autowired
    private SysConfigService sysConfigService;


	/**
	 * 列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:oss:all")
	public R list(){

		SearchDTO simpleSearchDTO = getSearchDtoFromJqGrid();

		PageDTO<SysUserEntity> page = sysOssService.findPageBySimpleSearch(simpleSearchDTO);
		return R.ok().put("page", page);
	}


    /**
     * 云存储配置信息
     */
    @GetMapping("/config")
    @RequiresPermissions("sys:oss:all")
    public R config(){
        CloudStorageConfig config = sysConfigService.getConfigObject(SysConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);

        return R.ok().put("config", config);
    }


	/**
	 * 保存云存储配置信息
	 */
	@PostMapping("/saveConfig")
	@RequiresPermissions("sys:oss:all")
	public R saveConfig(@RequestBody CloudStorageConfig config){
		//校验类型
		ValidatorUtils.validateEntity(config);

		if(config.getType().equals(CloudService.QINIU.getKey())){
			//校验七牛数据
			ValidatorUtils.validateEntity(config, QiniuGroup.class);
		}else if(config.getType().equals(CloudService.ALIYUN.getKey())){
			//校验阿里云数据
			ValidatorUtils.validateEntity(config, AliyunGroup.class);
		}else if(config.getType().equals(CloudService.QCLOUD.getKey())){
			//校验腾讯云数据
			ValidatorUtils.validateEntity(config, QcloudGroup.class);
		}

        sysConfigService.updateValueByKey(SysConstant.CLOUD_STORAGE_CONFIG_KEY, new Gson().toJson(config));

		return R.ok();
	}
	

	/**
	 * 上传文件
	 */
	@PostMapping("/upload")
	@RequiresPermissions("sys:oss:all")
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

		return R.ok().put("url", url);
	}


	/**
	 * 删除
	 */
	@PostMapping("/delete")
	@RequiresPermissions("sys:oss:all")
	public R delete(@RequestBody Long[] ids){

		sysOssService.removeByIds(Arrays.asList(ids));
		return R.ok();
	}

}
