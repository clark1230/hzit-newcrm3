package com.hzitshop.web.controllers;

import com.hzitshop.email.EmailUtil;
import com.hzitshop.vo.Email;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *   发送邮件控制器
 * </p>
 * Created by xianyaoji on 2017/2/24.
 */
@Controller
public class EmailController {
    /**
     *  跳转到发送邮件主页面
     * @return
     */
    @RequiresPermissions(value={"email:index"})
    @RequestMapping("/email/index")
    protected String index(){
        return "/email/index";
    }

    /**
     * 发送邮件
     * @return
     */
    @RequiresPermissions(value={"email:send"})
    @RequestMapping(value="/email/send",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> send(Email email){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            //发送邮件
            EmailUtil.sendEmail(email.getFrom(),email.getFromPwd(),email.getTo(),email.getEmailContent());
            resultMap.put("code",200);
            resultMap.put("msg","发送成功!");
        } catch (Exception e){
            resultMap.put("code",300);
            resultMap.put("msg","发送失败!(可能是网络原因或者邮件地址，密码等错误!)");
        }
        return resultMap;
    }
    @RequiresPermissions(value={"email:fileUpload"})
    @RequestMapping("/email/fileUpload")
    @ResponseBody
    protected  Map<String,Object> fileUpload(){
        Map<String,Object> resultMap = new HashMap<>();
        return resultMap;
    }

}
