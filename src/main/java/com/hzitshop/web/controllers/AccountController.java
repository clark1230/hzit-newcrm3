package com.hzitshop.web.controllers;

import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.service.IEmployeeInfoService;
import com.hzitshop.util.Captcha;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xianyaoji on 2017/2/10.
 */
@Controller
public class AccountController {
    private Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private IEmployeeInfoService iEmployeeInfoService;
    @RequestMapping(value="/account/login",method = RequestMethod.GET)
    protected String login(){
        System.out.println("跳转到登录页!!");
        return "login";
    }
   /* @RequestMapping(value ="/account/login",method=RequestMethod.POST)
    @ResponseBody
    protected Map<String,Object> login(String str){
        Map<String,Object> resultMap= new HashMap<>();
        return resultMap;
    }*/
    /**
     *
     * 获取登录验证码!
     * @param response
     */
    @RequestMapping("/account/captcha")
    protected  void captcha(HttpServletResponse response, HttpServletRequest request){
        if(request != null && response != null){
            Captcha.init(response,request).render();
        }
    }

    /**
     * 处理登录请求!//57dd03ed397eabaeaa395eb740b770fd
     * @return
     */
    @RequestMapping(value ="/account/login",method = RequestMethod.POST)
    @ResponseBody
    protected Map<String,Object> login(EmployeeInfo employeeInfo, String rememberMe, HttpServletRequest request) throws Exception {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        Subject subject = SecurityUtils.getSubject();//从SecurityUtils中获取主体对象
        //创建账号和密码令牌
        /*boolean remember = false;
        if("true".equals(rememberMe)){
            remember = true;
        }*/
        UsernamePasswordToken token = new UsernamePasswordToken(employeeInfo.getName(), employeeInfo.getPassword());
        try{
            subject.login(token);
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("name",employeeInfo.getName());
           /* List<EmployeeInfoVo> employeeInfoVoList = employeeInfoService.searchEmployeeInfoByParams(paramMap);
           EmployeeInfoVo employeeInfoVo = null;
            if(ObjectUtil.isNotNull(employeeInfoVoList)){
                employeeInfoVo = employeeInfoVoList.get(0);
                employeeInfoVo.setPassword("");
            }*/
            //httpSession.setAttribute("employeeInfo",employeeInfoVo);
            //httpSession.setMaxInactiveInterval(4*60*60);
            List<EmployeeInfo> employeeInfoList = iEmployeeInfoService.selectByMap(paramMap);
            HttpSession httpSession = request.getSession();
            if(employeeInfoList!=null && employeeInfoList.size() >0){
                employeeInfo = employeeInfoList.get(0);
                employeeInfo.setPassword("");
            }
            httpSession.setAttribute("employeeInfo",employeeInfo);
            resultMap.put("status",200);
            resultMap.put("message", "登录成功!");
        }catch (AuthenticationException e){
            logger.error("------------用户登录出错----------------"+e.getMessage());
            resultMap.put("status",300);
            resultMap.put("message", "登录失败!");
        }
        return resultMap;
    }
    /**
     * 系统注销!
     * @return
     */
    @RequestMapping(value = "/account/logout")
    protected String logout(HttpServletRequest request){
        SecurityUtils.getSubject().logout();//系统注销!!
        return "redirect:/account/login";//跳转到登录页
    }
    /**
     * 非法操作
     * @return
     */
    @RequestMapping("/account/unauthor")
    protected  String unauthor(){
        return "unauthor";
    }

}
