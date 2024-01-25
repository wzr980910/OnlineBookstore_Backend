package com.bookStore.log;

import com.bookStore.mapper.LogsMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.logging.LogManager;

/**
 * ClassName: MethosLogAspect
 * Package: com.bookStore.log
 * Description:
 *
 * @Author: 邓桂材
 * @Create: 2024/1/25 -15:33
 * @Version: v1.0
 */
@Component
@Aspect
@Slf4j
public class MethosLogAspect {
    @Autowired
    private LogsMapper logsMapper;
    //定义切点
    @Pointcut("@annotation(com.bookStore.log.MethodLog)")
    public void methodLog() {

    }

    @Around("methodLog()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        StartRecord(joinPoint);
        //执行方法
        Object result = joinPoint.proceed();
        //执行时长
        Long time = System.currentTimeMillis() - beginTime;
        //保存日志
        EndRecord(time);
        return result;
    }

    private void EndRecord(Long time) {
        log.info("excute time:{}ms", time);
        log.info("====================log end====================");
    }

    private static void StartRecord(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        log.info("====================log start====================");
        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("request method:{}", className + "." + methodName + "()");
        //请求的参数
        Object[] args = joinPoint.getArgs();
        Gson gson = new Gson();
        String params = gson.toJson(args);
        String logInfo="log start>>>\n"
                +"request method:"+ className + "." + methodName + "()\n"
                +"params:"+params+"\n";
    }

}
