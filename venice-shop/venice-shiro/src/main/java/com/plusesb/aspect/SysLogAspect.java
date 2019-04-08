package com.plusesb.aspect;

import com.google.gson.Gson;
import com.plusesb.annotation.SysLog;
import com.plusesb.entity.SysLogEntity;
import com.plusesb.entity.SysUserEntity;
import com.plusesb.service.AsyncService;
import com.plusesb.service.SysLogService;
import com.plusesb.utils.BaseUtils;
import com.plusesb.utils.HttpContextUtils;
import com.plusesb.utils.RequestHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * 系统日志，切面处理类
 * 
 * @author linyuchi
 * @email linyuchi@heyit.cn
 * @date 2017年3月8日 上午11:07:35
 */
@Aspect
@Component
@Slf4j
public class SysLogAspect {

	private static final ThreadLocal<SysLogEntity> logThreadLocal =  new NamedThreadLocal<SysLogEntity>("ThreadLocal log");
    private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<Date>("ThreadLocal beginTime");
	@Autowired
	private AsyncService asyncService;
	
	@Pointcut("@annotation(com.plusesb.annotation.SysLog)")
	public void logPointCut() {
		
	}

    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint){

	    log.info("进入日志切面前置通知！！");
        Date beginTime = new Date();
        beginTimeThreadLocal.set(beginTime);
        if (log.isDebugEnabled()){
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = signature.getName();
            log.debug("开始计时：{} URI:{}",BaseUtils.getDateString(beginTime),className + "." + methodName + "()");
        }

    }
    @After("logPointCut()")
    public void doAfter(JoinPoint joinPoint){

        log.info("进入日志切面后置通知！！");
        saveSysLog(joinPoint);
    }
	@AfterReturning(pointcut="logPointCut()",returning = "rst")
	public void afterRunning(Response rst){
		System.out.println("方法执行完执行...afterRunning");
		log.info("返回数据：{}", rst);
		log.info("==========================================>");
	}
	@AfterThrowing(pointcut = "logPointCut()",throwing = "e")
	public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {

		log.info("进入异常切片通知");
		SysLogEntity sysLog = logThreadLocal.get();
		if (BaseUtils.isNotEmpty(sysLog)){
			sysLog.setType("error");
			sysLog.setException(e.getMessage());
			asyncService.asyncUpdateLog(sysLog);
		}

	}

	private void saveSysLog(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		SysLogEntity sysLog = new SysLogEntity();
		SysLog syslog = method.getAnnotation(SysLog.class);
		if(syslog != null){
			//注解上的描述
			sysLog.setOperation(syslog.value());
		}

		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");
        sysLog.setException("无异常");
        sysLog.setType("success");
		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = new Gson().toJson(args[0]);
			sysLog.setParams(params);
		}catch (Exception e){

		}

		//获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		//设置IP地址
		sysLog.setIp(RequestHelper.getRemoteHost(request));

		//用户名
        String username = "";
        try{
		    username = ((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername();
        }catch (Exception e){

        }
		sysLog.setUsername(username);

        long beginTime = beginTimeThreadLocal.get().getTime();//得到线程绑定的局部变量（开始时间）
        long endTime = System.currentTimeMillis();  //2、结束时间
		sysLog.setTime(endTime-beginTime);
		//保存系统日志
		asyncService.asyncSaveLog(sysLog);
//		sysLogService.save(sysLog);
		logThreadLocal.set(sysLog);
	}
}
