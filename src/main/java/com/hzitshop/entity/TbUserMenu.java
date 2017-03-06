package com.hzitshop.entity;

import com.baomidou.mybatisplus.activerecord.Model;
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
@TableName("tb_user_menu")
public class TbUserMenu extends Model<TbUserMenu> {

    private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer userid;
	private String menuid;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
