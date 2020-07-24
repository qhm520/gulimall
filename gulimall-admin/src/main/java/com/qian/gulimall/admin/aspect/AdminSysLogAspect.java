package com.qian.gulimall.admin.aspect;

import com.google.gson.Gson;
import com.qian.gulimall.common.annotation.SysLog;
import com.qian.gulimall.admin.entity.SysLogEntity;
import com.qian.gulimall.admin.service.SysLogService;
import com.qian.gulimall.common.entity.vo.UserDetailsVo;
import com.qian.gulimall.common.utils.HttpContextUtils;
import com.qian.gulimall.common.utils.IPUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * AdminSysLogAspect is 系统日志，切面处理类
 *
 * @author QIAN
 * Date 2020/04/19
 * Time 09:50
 */
@Aspect
@Component
public class AdminSysLogAspect {

    @Autowired
    private SysLogService sysLogService;

    @Pointcut("@annotation(com.qian.gulimall.common.annotation.SysLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        saveSysLog(point, time);

        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLogEntity sysLog = new SysLogEntity();
        SysLog syslog = method.getAnnotation(SysLog.class);
        if (syslog != null) {
            //注解上的描述
            sysLog.setOperation(syslog.value());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            if (args.length > 0) {
                String params = new Gson().toJson(args[0]);
                sysLog.setParams(params);
            }
        } catch (Exception e) {

        }

        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();

        //设置IP地址
        sysLog.setIp(IPUtils.getIPAddress(request));

        //用户名
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsVo userDetailsVo = (UserDetailsVo)authentication.getPrincipal();
        String username = userDetailsVo.getUsername();
        sysLog.setUsername(username);

        sysLog.setTime(time);
        sysLog.setCreateDate(new Date());
        //保存系统日志
        sysLogService.save(sysLog);
    }
}
