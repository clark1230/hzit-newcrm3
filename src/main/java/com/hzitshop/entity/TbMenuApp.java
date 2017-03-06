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
 * @since 2017-03-01
 */
@TableName("tb_menu_app")
public class TbMenuApp extends Model<TbMenuApp> {

    private static final long serialVersionUID = 1L;

	private Integer id;
	private String appid;
    /**
     * 图标
     */
	private String icon;
	private String iconbg;
    /**
     * 桌面图表名称
     */
	private String name;
    /**
     * 超链接地址
     */
	private String url;
	private Integer pId;
	private String permission;
    /**
     * 该资源是否可用:0:不可用，1：可用,默认为1
     */
	private Integer available;
	private Boolean checked;
	private Boolean open;
	private String iconSkin;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconbg() {
		return iconbg;
	}

	public void setIconbg(String iconbg) {
		this.iconbg = iconbg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}

	public Boolean isChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean isOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
