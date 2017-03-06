package com.hzitshop.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-12
 */
@TableName("customer_trace_record")
public class CustomerTraceRecord extends Model<CustomerTraceRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 客户id
     */
	@TableId(value="record_id",type= IdType.AUTO)
	private Integer recordId;
    /**
     * 客户ID
     */
	@TableField("customer_id")
	private Integer customerId;
    /**
     * 咨询师id
     */
	@TableField("user_id")
	private Integer userId;
    /**
     * 访问渠道
     */
	private Integer channel;
    /**
     * 咨询日期
     */
	@TableField("record_date")
	private Date recordDate;
    /**
     * 时间戳
     */
	private String times;
    /**
     * 内容
     */
	private String content;


	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	protected Serializable pkVal() {
		return this.recordId;
	}

}
