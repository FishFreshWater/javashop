package com.plusesb.config.cloud;


import com.plusesb.constant.SysConstant;
import com.plusesb.constant.status.CloudService;
import com.plusesb.service.SysConfigService;
import com.plusesb.utils.SpringContextUtils;

/**
 * 文件上传Factory
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2017-03-26 10:18
 */
public final class OSSFactory {
    private static SysConfigService sysConfigService;

    static {
        OSSFactory.sysConfigService = (SysConfigService) SpringContextUtils.getBean("sysConfigService");
    }

    public static CloudStorageService build(){
        //获取云存储配置信息
        CloudStorageConfig config = sysConfigService.getConfigObject(SysConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);

        if(config.getType() == CloudService.QINIU.getKey()){
            return new QiniuCloudStorageService(config);
        }else if(config.getType() == CloudService.ALIYUN.getKey()){
            return new AliyunCloudStorageService(config);
        }else if(config.getType() == CloudService.QCLOUD.getKey()){
            return new QcloudCloudStorageService(config);
        }

        return null;
    }

}
