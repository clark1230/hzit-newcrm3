package com.hzitshop.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author 冼耀基
 * @since 2017-03-02
 */
public class Classinfo extends Model<Classinfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 班级ID
     */
	private Integer classId;
	private String classname;
    /**
     * 班级类型
     */
	private String classLx;
    /**
     * 班级位置
     */
	private String classWz;
	private Date classTime;
    /**
     * 班级状态
     */
	private String classZt;
	private Integer classTeacher;
	private Integer classSchool;


	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getClassLx() {
		return classLx;
	}

	public void setClassLx(String classLx) {
		this.classLx = classLx;
	}

	public String getClassWz() {
		return classWz;
	}

	public void setClassWz(String classWz) {
		this.classWz = classWz;
	}

	public Date getClassTime() {
		return classTime;
	}

	public void setClassTime(Date classTime) {
		this.classTime = classTime;
	}

	public String getClassZt() {
		return classZt;
	}

	public void setClassZt(String classZt) {
		this.classZt = classZt;
	}

	public Integer getClassTeacher() {
		return classTeacher;
	}

	public void setClassTeacher(Integer classTeacher) {
		this.classTeacher = classTeacher;
	}

	public Integer getClassSchool() {
		return classSchool;
	}

	public void setClassSchool(Integer classSchool) {
		this.classSchool = classSchool;
	}

	@Override
	protected Serializable pkVal() {
		return this.classId;
	}

}
