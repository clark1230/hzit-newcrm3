package com.hzitshop.vo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;


/**
 * <p>
 * 
 * </p>
 *
 * @author 冼耀基
 * @since 2017-03-03
 */

public class TbRoleVo {
	/**
	 * id自动增长
	 */
	private Integer id;
	private String role;
    /**
     * 角色描述
     */
	private String description;
    /**
     * tb_menu_app的资源
     */
	private String resourceIds;
    /**
     * 0:不可用,1:可用:默认不可用
     */
	private Integer available;
    /**
     * 录入人
     */
	private Integer createBy;
	private String createByMsg;
    /**
     * 录入时间
     */
	private Date createTime;
    /**
     * 修改人
     */
	private Integer updateBy;
	private String updateByMsg;
    /**
     * 修改时间
     */
	private Date updateTime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}

	public Integer getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date creaateTime) {
		this.createTime = creaateTime;
	}

	public Integer getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateByMsg() {
		return createByMsg;
	}

	public void setCreateByMsg(String createByMsg) {
		this.createByMsg = createByMsg;
	}

	public String getUpdateByMsg() {
		return updateByMsg;
	}

	public void setUpdateByMsg(String updateByMsg) {
		this.updateByMsg = updateByMsg;
	}
}
