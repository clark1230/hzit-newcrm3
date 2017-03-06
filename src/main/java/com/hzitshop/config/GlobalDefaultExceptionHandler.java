package com.hzitshop.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xianyaoji on 2016/12/15.
 */
/*
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
        private Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);
        @ExceptionHandler(value = Exception.class)
        public void defaultErrorHandler(HttpServletRequest req, Exception e)  {
                //System.out.println("全局异常处理!!");
                //打印异常信息：
                //e.printStackTrace();
                //System.out.println("GlobalDefaultExceptionHandler.defaultErrorHandler()");
               */
/*
                * 返回json数据或者String数据：
                * 那么需要在方法上加上注解：@ResponseBody
                * 添加return即可。
                *//*


               */
/*
                * 返回视图：
                * 定义一个ModelAndView即可，
                * 然后return;
                * 定义视图文件(比如：error.html,error.ftl,error.jsp);
                *
                *//*

               logger.debug(e.getMessage());
        }

}*/
