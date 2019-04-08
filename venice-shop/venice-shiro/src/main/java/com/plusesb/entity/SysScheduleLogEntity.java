
package com.plusesb.entity;


import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 定时任务日志
 *
 * @author linyuchi@heyit.cn
 * @since 2018-12-10
 */
@TableName("sys_schedule_log")
public class SysScheduleLogEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 任务id
	 */
	private Long jobId;
	
	/**
	 * spring bean名称
	 */
	private String beanName;
	
	/**
	 * 方法名
	 */
	private String methodName;
	
	/**
	 * 参数
	 */
	private String params;
	
	/**
	 * 任务状态    0：成功    1：失败
	 */
	private Integer status;
	
	/**
	 * 失败信息
	 */
	private String error;
	
	/**
	 * 耗时(单位：毫秒)
	 */
	private Integer times;

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}


}
