
package com.plusesb.entity;


import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;


/**
 * 文件上传
 * 
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2017-03-25 12:13:26
 */
@TableName("sys_oss")
public class SysOssEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//URL地址
	private String url;

	/**
	 * 设置：URL地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：URL地址
	 */
	public String getUrl() {
		return url;
	}
}
