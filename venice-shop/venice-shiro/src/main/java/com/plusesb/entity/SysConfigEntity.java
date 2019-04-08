
package com.plusesb.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 系统配置信息
 * 
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2016年12月4日 下午6:43:36
 */
@TableName("sys_config")
public class SysConfigEntity extends BaseEntity{

	@NotBlank(message="参数名不能为空")
	private String paramKey;
	@NotBlank(message="参数值不能为空")
	private String paramValue;
	private String remark;

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
