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
 * @since 2017-02-18
 */
@TableName("customer_info")
public class CustomerInfo extends Model<CustomerInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 客户id
     */
	@TableId(value="customer_id",type= IdType.AUTO)
	private Integer customerId;
    /**
     * 客户姓名
     */
	@TableField("real_name")
	private String realName;
    /**
     * 性别[1-男  2-女]
     */
	private Integer sex;
    /**
     * 年龄
     */
	private Integer age;
    /**
     * 籍贯
     */
	@TableField("native_place")
	private String nativePlace;
    /**
     * 联系电话
     */
	private String tel;
    /**
     * 微信账号
     */
	@TableField("wechat_no")
	private String wechatNo;
    /**
     * 扣扣号
     */
	private String qq;
    /**
     * 学历[1-小学 2-初中 3-高中 4-大专 5-本科 6-研究生 0-其他]
     */
	@TableField("education_bg")
	private Integer educationBg;
    /**
     * 毕业时间
     */
	@TableField("graduate_time")
	private String graduateTime;
    /**
     * 毕业院校
     */
	@TableField("graduate_from")
	private String graduateFrom;
    /**
     * 专业
     */
	@TableField("major_in")
	private String majorIn;
    /**
     * 工作年限
     */
	@TableField("work_age")
	private Integer workAge;
    /**
     * 工作经历
     */
	@TableField("work_experience")
	private String workExperience;
    /**
     * 从事职业
     */
	private String job;
    /**
     * 教育培训经历
     */
	@TableField("educate_experience")
	private String educateExperience;
    /**
     * 应聘渠道[1-智联 2-前程无忧 3-58同城 4-转介绍 5-中华英才 6-其他待定]
     */
	@TableField("recruit_channel")
	private Integer recruitChannel;
    /**
     * 客户状态[待定]已报名，已就业，意向[],无效
     */
	@TableField("customer_state")
	private Integer customerState;
    /**
     * 客户级别[待定]（ABCD）
     */
	@TableField("customer_level")
	private String customerLevel;
    /**
     * 咨询师id
     */
	@TableField("user_id")
	private Integer userId;
    /**
     * 客户感兴趣的目标技能
     */
	@TableField("target_skill")
	private String targetSkill;
    /**
     * 推荐人（创量部电话邀约）
     */
	private String introducer;
    /**
     * 备注
     */
	private String memo;
    /**
     * 最后跟进时间 格式2015-10-09
     */
	@TableField("last_time")
	private Date lastTime;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
	@TableField("company_id")
	private Integer companyId;
	private Integer isDelete;
    /**
     * 关单人
     */
	private Integer guandan;
    /**
     * 是否是会销(0:是,1:不是)
     */
	private String isMarket;


	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getWechatNo() {
		return wechatNo;
	}

	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Integer getEducationBg() {
		return educationBg;
	}

	public void setEducationBg(Integer educationBg) {
		this.educationBg = educationBg;
	}

	public String getGraduateTime() {
		return graduateTime;
	}

	public void setGraduateTime(String graduateTime) {
		this.graduateTime = graduateTime;
	}

	public String getGraduateFrom() {
		return graduateFrom;
	}

	public void setGraduateFrom(String graduateFrom) {
		this.graduateFrom = graduateFrom;
	}

	public String getMajorIn() {
		return majorIn;
	}

	public void setMajorIn(String majorIn) {
		this.majorIn = majorIn;
	}

	public Integer getWorkAge() {
		return workAge;
	}

	public void setWorkAge(Integer workAge) {
		this.workAge = workAge;
	}

	public String getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(String workExperience) {
		this.workExperience = workExperience;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getEducateExperience() {
		return educateExperience;
	}

	public void setEducateExperience(String educateExperience) {
		this.educateExperience = educateExperience;
	}

	public Integer getRecruitChannel() {
		return recruitChannel;
	}

	public void setRecruitChannel(Integer recruitChannel) {
		this.recruitChannel = recruitChannel;
	}

	public Integer getCustomerState() {
		return customerState;
	}

	public void setCustomerState(Integer customerState) {
		this.customerState = customerState;
	}

	public String getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTargetSkill() {
		return targetSkill;
	}

	public void setTargetSkill(String targetSkill) {
		this.targetSkill = targetSkill;
	}

	public String getIntroducer() {
		return introducer;
	}

	public void setIntroducer(String introducer) {
		this.introducer = introducer;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getGuandan() {
		return guandan;
	}

	public void setGuandan(Integer guandan) {
		this.guandan = guandan;
	}

	public String getIsMarket() {
		return isMarket;
	}

	public void setIsMarket(String isMarket) {
		this.isMarket = isMarket;
	}

	@Override
	protected Serializable pkVal() {
		return this.customerId;
	}

	@Override
	public String toString() {
		return "CustomerInfoVo{" +
				"customerId=" + customerId +
				", realName='" + realName + '\'' +
				", sex=" + sex +
				", age=" + age +
				", nativePlace='" + nativePlace + '\'' +
				", tel='" + tel + '\'' +
				", wechatNo='" + wechatNo + '\'' +
				", qq='" + qq + '\'' +
				", educationBg=" + educationBg +
				", graduateTime='" + graduateTime + '\'' +
				", graduateFrom='" + graduateFrom + '\'' +
				", majorIn='" + majorIn + '\'' +
				", workAge=" + workAge +
				", workExperience='" + workExperience + '\'' +
				", job='" + job + '\'' +
				", educateExperience='" + educateExperience + '\'' +
				", recruitChannel=" + recruitChannel +
				", customerState=" + customerState +
				", customerLevel='" + customerLevel + '\'' +
				", userId=" + userId +
				", targetSkill='" + targetSkill + '\'' +
				", introducer='" + introducer + '\'' +
				", memo='" + memo + '\'' +
				", lastTime=" + lastTime +
				", createTime=" + createTime +
				", companyId=" + companyId +
				", isDelete=" + isDelete +
				", guandan=" + guandan +
				", isMarket='" + isMarket + '\'' +
				'}';
	}
}
