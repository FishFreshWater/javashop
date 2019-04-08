package com.plusesb.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.plusesb.config.cloud.OSSFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
/**
 * 会员信息
 *
 * @author linyuchi
 * @email xunli-03@163.com
 * @date 2018-09-14 09:46:46
 */
@RestController
@Slf4j
public class UploadController  {

	@RequestMapping(value = "/upload_url",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String uploadUrl(@RequestParam("file") MultipartFile file, HttpServletResponse response, @RequestParam String type){
		JSONObject result = new JSONObject();
		try {
			//文件名:时间戳+_+随机数+原后缀
			String saveFileName = System.currentTimeMillis()+"_"+ RandomStringUtils.randomNumeric(4)
					+ "." + FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
			//支持IE8增加报文头
			response.setHeader("X-Frame-Options","SAMEORIGIN");
			//路径 upload+type+filename
			String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
			result.put("result","success");
			result.put("file_url", url);
		}catch (Exception e){
			log.error("上传出错",e);
			result.put("result","error");
			result.put("msg",e.getMessage());
		}
		return result.toJSONString();
	}
}
