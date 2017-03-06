package com.hzitshop.web.controllers;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hzitshop.entity.CustomerTraceRecord;
import com.hzitshop.service.ICustomerTraceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-12
 */
@Controller
public class CustomerTraceRecordController {
	@Autowired
    private ICustomerTraceRecordService iCustomerTraceRecordService;

    /**
     * 获取最新跟进记录!!
     * @return
     */
	@RequestMapping("/customerTraceRecord/traceRecord")
    @ResponseBody
    protected List<CustomerTraceRecord> traceRecord(CustomerTraceRecord customerTraceRecord){
	    List<CustomerTraceRecord> list=  iCustomerTraceRecordService.selectList(
	            new EntityWrapper<CustomerTraceRecord>().
                        where("user_id="+customerTraceRecord.getUserId()+" and customer_id="+customerTraceRecord.getCustomerId() ).
                        orderBy("record_id  desc"));
	    return list;
    }
    
}
