package com.hzitshop.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author 冼耀基
 * @since 2017-03-01
 */
@TableName("tb_menu_app_desc")
public class TbMenuAppDesc extends Model<TbMenuAppDesc> {

    private static final long serialVersionUID = 1L;

	private Integer id;
	@TableField("menu_app_id")
	private Integer menuAppId;
	private String permission;
	private String name;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMenuAppId() {
		return menuAppId;
	}

	public void setMenuAppId(Integer menuAppId) {
		this.menuAppId = menuAppId;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
