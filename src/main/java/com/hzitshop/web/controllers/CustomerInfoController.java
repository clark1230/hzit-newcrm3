package com.hzitshop.web.controllers;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.hzitshop.entity.*;
import com.hzitshop.service.*;
import com.hzitshop.vo.BootstrapTable;
import com.hzitshop.vo.BootstrapEntity;
import com.hzitshop.vo.CustomerInfoVo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.*;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-12
 */
@Controller
//@RequiresAuthentication //表示当前类需要进行授权
public class CustomerInfoController {
    @Autowired
    private ICustomerInfoService iCustomerInfoService;
    @Autowired
    private IEmployeeInfoService iEmployeeInfoService;
    @Autowired
    private ITbDictService iTbDictService;
    @Autowired
    private ICustomerTraceRecordService iCustomerTraceRecordService;

    @Autowired
    private IClassinfoService iClassinfoService;
    @Autowired
    private  IStudentinfoService iStudentinfoService;

    @Autowired
    private  ITbRoleService iTbRoleService;

    @Autowired
    private  ITbMenuAppService iTbMenuAppService;


    private Logger logger = LoggerFactory.getLogger(CustomerInfoController.class);

    /**
     * 跳转到学员列表页
     *
     * @return
     */
   /* @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)*/
    @RequiresPermissions(value="customerInfo:list")
    @RequestMapping("/customerInfo/list")
    protected String list() {
        return "/customerinfo/list";
    }

    /**
     * 获取学员数据
     *
     * @return
     */
    @RequiresPermissions(value = {"customerInfo:listData"})
    @RequestMapping("/customerInfo/listData")
    @ResponseBody
    protected BootstrapTable<CustomerInfoVo> listData(BootstrapEntity bt,CustomerInfo customerInfo,EmployeeInfo ei) {
        if (bt.getOffset() == null || bt.getLimit() == null) {
            bt.setOffset(1);
            bt.setLimit(20);
        } else {
            bt.setOffset(bt.getOffset() / bt.getLimit());
        }
        if(customerInfo.getIsDelete() == null){
            customerInfo.setIsDelete(0);   //默认显示非逻辑删除数据
        }

        Page<CustomerInfo> searchPage = new Page<CustomerInfo>(bt.getOffset(), bt.getLimit());
        Wrapper<CustomerInfo> ew = null;
        if("-1".equals(bt.getCondition()) ){
            bt.setCondition("");
        }

        if(ei.getUserId()!=null){ //咨询师
            if(StringUtils.isNotEmpty(bt.getCondition())&& StringUtils.isNotEmpty(bt.getValue())){
                ew= new EntityWrapper<CustomerInfo>().
                        where("isDelete="+customerInfo.getIsDelete())
                        .and("user_id="+ei.getUserId())
                        .like(bt.getCondition(),bt.getValue()).
                        orderBy(" customer_id desc");
            }else{
                ew = new EntityWrapper<CustomerInfo>()
                        .where("isDelete="+customerInfo.getIsDelete())
                        .and("user_id="+ei.getUserId())
                        .orderBy("customer_id desc");
            }
        }else{ //非咨询师
            if(StringUtils.isNotEmpty(bt.getCondition())&& StringUtils.isNotEmpty(bt.getValue())){
                ew= new EntityWrapper<CustomerInfo>().
                        where("isDelete="+customerInfo.getIsDelete())
                        .like(bt.getCondition(),bt.getValue()).
                        orderBy(" customer_id desc");
            }else{
                ew = new EntityWrapper<CustomerInfo>()
                        .where("isDelete="+customerInfo.getIsDelete())
                        .orderBy("customer_id desc");
            }
        }
        BootstrapTable<CustomerInfoVo> bootstrapTable = iCustomerInfoService.ajaxData(searchPage,ew);
        return bootstrapTable;
    }

    /**
     * 跳转到添加页面
     *
     * @return
     */
    @RequiresPermissions(value = {"customerInfo:add"})
    @RequestMapping(value = "/customerInfo/add", method = RequestMethod.GET)
    protected String add(ModelMap modelMap, EmployeeInfo ei, HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        EmployeeInfo employeeInfo = (EmployeeInfo)httpSession.getAttribute("employeeInfo");
        if(ei.getCompanyId()==null){
            this.modelMap(modelMap,employeeInfo.getCompanyId());
        }  else{
            this.modelMap(modelMap,ei.getCompanyId());
        }
        return "/customerinfo/add";
    }

    /**
     * 保存数据
     *
     * @param customerInfo
     * @return
     * @throws InterruptedException
     */
    @RequiresPermissions(value = {"customerInfo:add"})
    @RequestMapping(value = "/customerInfo/add", method = RequestMethod.POST)
    @ResponseBody
    protected Map<String, Object> add(CustomerInfo customerInfo) throws InterruptedException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            if(customerInfo.getCustomerState()==null){
                customerInfo.setCustomerState(28);//待面试
            }
            customerInfo.setCreateTime(new Date());
            customerInfo.setLastTime(new Date());
            iCustomerInfoService.insert(customerInfo);
            resultMap.put("code", 200);
            resultMap.put("msg", "添加成功!");
        } catch (Exception e) {
            logger.error("-----------咨询师添加学员信息 ------------------------"+e.getMessage());
            e.printStackTrace();
            resultMap.put("code", 300);
            resultMap.put("msg", "添加失败!");
        }
        //Thread.sleep(4000);模拟网络阻塞
        return resultMap;
    }

    /**
     * 根据父编号获取数据字典信息
     *
     * @param pid
     * @return
     */
    protected List<TbDict> getTbgDict(String pid) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pid", pid);
        return iTbDictService.selectByMap(paramMap);
    }

    /**
     * 根据部门编号获取用户
     *
     * @param id
     * @return
     */
    protected List<EmployeeInfo> getEmployeeInfo(String id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("dept_id", id);
        return iEmployeeInfoService.selectByMap(paramMap);
    }

    /**
     * 修改学员信息页面
     *
     * @return
     */
    @RequiresPermissions(value={"customerInfo:edit"})
    @RequestMapping(value = "/customerInfo/edit", method = RequestMethod.GET)
    protected String edit(ModelMap modelMap, CustomerInfo customerInfo) {
        //根据customerId获取学员数据
        customerInfo = iCustomerInfoService.selectOne(new EntityWrapper<CustomerInfo>().where("customer_id=" + customerInfo.getCustomerId()));
        this.modelMap(modelMap,customerInfo.getCompanyId());
        modelMap.addAttribute("customerInfo", customerInfo);
        System.out.println(customerInfo);
        return "/customerinfo/edit";
    }

    public void modelMap(ModelMap modelMap,Integer companyId) {
        modelMap.addAttribute("customerStateList", this.getTbgDict("27"));//学员状态
        modelMap.addAttribute("targetSkillList", this.getTbgDict("2"));//目标技能
        modelMap.addAttribute("recruitChannelList", this.getTbgDict("21"));//应聘渠道
        modelMap.addAttribute("customerLevelList", this.getTbgDict("16"));//学员级别
        modelMap.addAttribute("educationBgList", this.getTbgDict("7")); //获取学历信息
        modelMap.addAttribute("guandanList", this.getEmployeeInfo(new EntityWrapper<EmployeeInfo>()
                .where("isConsultant=1").
                        and("company_id="+companyId)));//获取关单人
        modelMap.addAttribute("introducerList", this.getEmployeeInfo("42"));//获取邀约人
    }

    public List<EmployeeInfo> getEmployeeInfo(Wrapper<EmployeeInfo> wrapper){
        return iEmployeeInfoService.selectList(wrapper);
    }

    /**
     * 保存修改数据
     *
     * @param customerInfo
     * @return
     */
    @RequiresPermissions(value={"customerInfo:edit"})
    @RequestMapping(value = "/customerInfo/edit", method = RequestMethod.POST)
    @ResponseBody
    protected Map<String, Object> edit(CustomerInfo customerInfo) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            iCustomerInfoService.update(customerInfo,
                    new EntityWrapper<CustomerInfo>().where("customer_id=" + customerInfo.getCustomerId()));
            resultMap.put("code", "200");
            resultMap.put("msg", "保存成功!");
        } catch (Exception e) {
            logger.error("------------咨询师编辑学员信息---------------"+e.getMessage());
            e.printStackTrace();
            resultMap.put("code", "300");
            resultMap.put("msg", "保存失败!");
        }
        return resultMap;
    }

    /**
     * 删除学员(丢到回收站)
     *
     * @return
     */
    /*@RequiresPermissions(value = {"customerInfo:remove"})
    @RequestMapping(value = "/customerInfo/remove", method = RequestMethod.GET)
    @ResponseBody
    protected Map<String, Object> remove(String customerIdArr) {
        Map<String, Object> resultMap = new HashMap<>();
        return resultMap;
    }*/

    /**
     * 彻底删除学员信息
     *到service层中线先删除该学员的跟进记录，后再删除customre_info中相关的数据
     * @return
     */
    @RequiresPermissions(value = {"customerInfo:delete"})
    @RequestMapping(value = "/customerInfo/delete", method = RequestMethod.POST)
    @ResponseBody
    protected Map<String, Object> delete(String customerIdArr) {
        Map<String, Object> resultMap = new HashMap<>();
        try{
            iCustomerInfoService.deleteCustomerInfo(customerIdArr);
            resultMap.put("code",200);
            resultMap.put("msg","删除成功!");
        } catch (Exception e){
            logger.error("----------------------彻底删除学员数据---------------"+e.getMessage());
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put("code",300);
            resultMap.put("msg","删除失败!");
        }
        return resultMap;
    }

    /**
     * 批量面试学员
     *
     * @return
     */
    @RequiresPermissions(value = "customerInfo:interview")
    @RequestMapping(value = "/customerInfo/interview", method = RequestMethod.POST)
    @ResponseBody
    protected Map<String, Object> interview(String customerIdArr) {
        String condition = "customer_sate";
        return getStringObjectMap(customerIdArr, condition);
    }

    /**
     * 逻辑删除学员数据
     *
     * @return
     */
    @RequiresPermissions(value="customerInfo:remove")
    @RequestMapping(value = "/customerInfo/remove")
    @ResponseBody
    protected Map<String, Object> remove(String customerIdArr) {
        String condition = "isDelete";
        return getStringObjectMap(customerIdArr, condition);
    }
    @RequiresPermissions(value="customerInfo:recover")
    @RequestMapping(value = "/customerInfo/recover",method = RequestMethod.POST)
    @ResponseBody
    protected  Map<String,Object> recover(String customerIdArr){
        return  getStringObjectMap(customerIdArr,"isDelete=0");

    }
    private Map<String, Object> getStringObjectMap(String customerIdArr, String condition) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            CustomerInfo customerInfo = null;
            if (StringUtils.isNotEmpty(customerIdArr)) {
                String[] idArr = customerIdArr.split(",");
                for (String id : idArr) {
                    customerInfo = new CustomerInfo();
                    if ("isDelete".equals(condition)) {
                        customerInfo.setIsDelete(1);//逻辑删除数据
                    }else if("isDelete=0".equals(condition)){
                        customerInfo.setIsDelete(0);//恢复逻辑删除的数据
                    } else if ("customer_sate".equals(condition)) {
                        customerInfo.setCustomerState(29); //修改面试状态为已面试
                    }
                    customerInfo.setCustomerId(Integer.parseInt(id));
                    iCustomerInfoService.update(customerInfo,
                            new EntityWrapper<CustomerInfo>().where("customer_id=" + customerInfo.getCustomerId()));
                }
            }
            resultMap.put("code", 200);
            resultMap.put("msg", "操作成功!");
        } catch (Exception e) {
            logger.error("--------------------面试学员-------------------"+e.getMessage());
            e.printStackTrace();
            resultMap.put("code", 300);
            resultMap.put("msg", "操作失败!");
        }
        return resultMap;
    }

    /**
     * 学员信息跟进
     *
     * @param customerInfo
     * @return
     */
    @RequiresPermissions(value="customerInfo:follow")
    @RequestMapping(value = "/customerInfo/follow", method = RequestMethod.GET)
    protected String follow(CustomerInfo customerInfo, ModelMap modelMap) {
        //根据customerId获取学员数据
        customerInfo = iCustomerInfoService.selectOne(new EntityWrapper<CustomerInfo>().where("customer_id=" + customerInfo.getCustomerId()));
        this.modelMap(modelMap,customerInfo.getCompanyId());
        modelMap.addAttribute("customerInfo", customerInfo);
        return "/customerinfo/follow";
    }

    /**
     * 保存跟进记录
     * 需要咨询师编号,学员编号，跟进内容g
     *
     * @param ctr
     * @return
     */
    @RequiresPermissions(value="customerInfo:follow")
    @RequestMapping(value = "/customerInfo/follow", method = RequestMethod.POST)
    @ResponseBody
    protected Map<String, Object> follow(CustomerTraceRecord ctr) {
        Map<String, Object> resultMap = new HashMap<>();
        ctr.setRecordDate(new Date());
        try {
            //到customer_info中修改最新跟进时间
            CustomerInfo customerInfo = new CustomerInfo() ;
            customerInfo.setCustomerId(ctr.getCustomerId());
            customerInfo.setLastTime(new Date());
            iCustomerInfoService.updateById(customerInfo);
            iCustomerTraceRecordService.insert(ctr);   //保存跟进记录!!
            resultMap.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code",300);
        }
        return resultMap;
    }

    /**
     * 跳转到高级搜索页面
     * @return
     */
    @RequiresPermissions(value="customerInfo:multiSearch")
    @RequestMapping(value="/customerInfo/multiSearch",method = RequestMethod.GET)
    protected String multiSearch(){
        return "/customerinfo/multiSearch";
    }

    /**
     * 跳转到报表导出页面
     * @return
     */
    @RequiresPermissions(value="customerInfo:export")
    @RequestMapping(value="/customerInfo/export",method=RequestMethod.GET)
    protected String export(){
        return "/customerinfo/export";
    }

    /**
     * 回收站
     * @return
     */
    @RequiresPermissions(value="customerInfo:recyleBin")
    @RequestMapping(value="/customerInfo/recyleBin",method = RequestMethod.GET)
    public String recyleBin(){
        return "/customerinfo/recyleBin";
    }

    @RequestMapping(value="/customerInfo/recycleBinAjaxData",method = RequestMethod.GET)
    @ResponseBody
    protected  BootstrapTable<CustomerInfo> recycleBinAjaxData(BootstrapEntity bt,CustomerInfo customerInfo,EmployeeInfo ei){
        if (bt.getOffset() == null || bt.getLimit() == null) {
            bt.setOffset(1);
            bt.setLimit(20);
        } else {
            bt.setOffset(bt.getOffset() / bt.getLimit());
        }
        Wrapper<CustomerInfo> ew =null;
        if("-1".equals(bt.getCondition()) ){
            bt.setCondition("");
        }
        if("1".equals(ei.getIsConsultant())){ //咨询师
            if(StringUtils.isNotEmpty(bt.getCondition())&& StringUtils.isNotEmpty(bt.getValue())){
                ew= new EntityWrapper<CustomerInfo>().
                        where("isDelete=1").and("user_id="+ei.getUserId()).
                        like(bt.getCondition(),bt.getValue()).
                        orderBy(" customer_id desc");
            }else{
                ew = new EntityWrapper<CustomerInfo>()
                        .where("isDelete=1").and("user_id="+ei.getUserId())
                        .orderBy("customer_id desc");
            }
        }else{ //非咨询师
            if(StringUtils.isNotEmpty(bt.getCondition())&& StringUtils.isNotEmpty(bt.getValue())){
                ew= new EntityWrapper<CustomerInfo>().
                        where("isDelete=1").
                        like(bt.getCondition(),bt.getValue()).
                        orderBy(" customer_id desc");
            }else{
                ew = new EntityWrapper<CustomerInfo>()
                        .where("isDelete=1")
                        .orderBy("customer_id desc");
            }
        }

        Page<CustomerInfo> page = iCustomerInfoService.selectPage(new Page<CustomerInfo>(
                bt.getOffset(),bt.getLimit()),
                ew);
        BootstrapTable<CustomerInfo> bootstrapTable = new BootstrapTable<>();
        bootstrapTable.setRows(page.getRecords());
        bootstrapTable.setTotal(page.getTotal());
        return bootstrapTable;
    }

    /**
     * 跳转到搜索页
     * @return
     */
    @RequiresPermissions(value="customerInfo:searchPage")
    @RequestMapping("/customerInfo/searchPage")
    protected  String searchPage(ModelMap modelMap,EmployeeInfo ei){
        //获取学员状态
        //获取学员级别
        //获取目标技能
        //获取应聘渠道
        //获取邀约人
        //获取咨询师
        //获取关单人
        modelMap(modelMap,ei.getCompanyId());
         return "/customerinfo/searchPage";
    }

    /**
     * 跳转到学员进班页
     * @return
     */
    @RequestMapping(value="/customerInfo/enterClass",method = RequestMethod.GET)
    protected String endterClass(EmployeeInfo employeeInfo,Model model){
        //获取班级信息
        List<Classinfo> classinfoList = iClassinfoService.selectClassInfo();
        model.addAttribute("classinfoList",classinfoList);
        //获取贷款信息
        List<TbDict> tbDictList = iTbDictService.selectList(new EntityWrapper<TbDict>().where("pid=46"));
        model.addAttribute("tbDictList",tbDictList);
        return "/customerinfo/enterClass";
    }

    /**
     * 获取学员进班数据
     * @param 
     * @return
     */
   /* @RequestMapping(value="/customerInfo/enterClass2")
    @ResponseBody
    public Map<String,Object> enterClass(Studentinfo studentinfo,Integer customerId){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            //iStudentinfoService.insertStudentinfo(studentinfo,customerId);
            resultMap.put("code",200);
            resultMap.put("msg","操作成功!");
        }  catch (Exception e){

           resultMap.put("code",300);
           resultMap.put("msg","操作失败!");
        }
        return resultMap;
    }*/
    @RequiresPermissions("customerInfo:enterClass")
    @RequestMapping("/customerInfo/enterClass")
    @ResponseBody
    public Map<String,Object> hello(Integer customerId,Integer stedentClass,
                                    String studentStatus,String studentintime,String studentdes) throws ParseException {
        Studentinfo studentinfo = new Studentinfo();

        studentinfo.setStedentClass(stedentClass);
        studentinfo.setStudentStatus(studentStatus);
        studentinfo.getStudentintime();
        studentinfo.setStudentdes(studentdes);
        Map<String,Object> resultMap = new HashMap<>();
        try{
            iStudentinfoService.insertStudentinfo(studentinfo,customerId);
            resultMap.put("code",200);
            resultMap.put("msg","操作成功");
        }   catch ( Exception e){
            e.printStackTrace();
            resultMap.put("code",300);
            resultMap.put("msg","操作失败!");
        }

        return resultMap;
    }

    /**
     * 跳转到进班学员列表
     * @return
     */
    @RequestMapping("/customerInfo/enterClassList")
    public String enterClassList(){
        return "/customerinfo/enterClassList";
   }


    /**************数据统计开始***********************/
    /*@RequestMapping(value="/customerInfo/")
    protected  String index(){
        return null;
    }*/


    /**************数据统计结束***********************/



}
