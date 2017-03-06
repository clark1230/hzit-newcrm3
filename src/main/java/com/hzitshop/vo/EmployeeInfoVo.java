package com.hzitshop.vo;

import java.util.Date;


/**
 * <p>
 * 
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-26
@TableName("employee_info")
*/
public class EmployeeInfoVo {

    /**
     * 用户id
     */
	private Integer userId;
    /**
     * 用户名
     */
	private String name;
	private String password;
	private String salt;

    /**
     * 微信号
     */
	private String wechatNo;
    /**
     * 手机号
     */
	private String tel;
    /**
     * 用户姓名
     */
	private String realName;
	private Integer deptId;
	private String dept;
	private Integer companyId;
	private String company;
	private String createBy;
	private Date createTime;
	private String updateBy;
	private Date updateTime;
	private Integer isDelete;

    /**
     * 是否禁用用户:0:不禁用.1:禁用 默认为:0
     */
	private String isLocked;
	private String gender;
    /**
     * 0：不是咨询师,1:是咨询师 默认为:0
     */
	private String isConsultant;
	private String email;
    /**
     * 邮件是否发送成功:0:不成功，1：成功,默认:0
     */
	private String isEmailActive;
	private String resourceIds;
	/**
	 * 是否授权:0:没有授权,1:权限授权，2:角色授权
	 */
	private String isPermission;
	public EmployeeInfoVo(){

	}

	public EmployeeInfoVo(Integer userId, String isLocked) {
		this.userId = userId;
		this.isLocked = isLocked;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}



	public String getWechatNo() {
		return wechatNo;
	}

	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIsConsultant() {
		return isConsultant;
	}

	public void setIsConsultant(String isConsultant) {
		this.isConsultant = isConsultant;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIsEmailActive() {
		return isEmailActive;
	}

	public void setIsEmailActive(String isEmailActive) {
		this.isEmailActive = isEmailActive;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getIsPermission() {
		return isPermission;
	}

	public void setIsPermission(String isPermission) {
		this.isPermission = isPermission;
	}
}
