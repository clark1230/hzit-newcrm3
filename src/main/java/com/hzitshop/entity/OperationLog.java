package com.hzitshop.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-12
 */
@TableName("operation_log")
public class OperationLog extends Model<OperationLog> {

    private static final long serialVersionUID = 1L;

	private Integer id;
	private String logname;
	@TableField(value="user_id")
	private Integer userId;
	@TableField("user_name")
	private String userName;
	@TableField("class_name")
	private String className;
	private String method;
    /**
     * 0:一般，1:严重，2:非常严重
     */
	private String level;
	@TableField("create_time")
	private Date createTime;
	private Integer succeed;
	private String message;
	@TableField("ip_address")
	private String ipAddress;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogname() {
		return logname;
	}

	public void setLogname(String logname) {
		this.logname = logname;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getSucceed() {
		return succeed;
	}

	public void setSucceed(Integer succeed) {
		this.succeed = succeed;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
