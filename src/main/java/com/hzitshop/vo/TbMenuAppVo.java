package com.hzitshop.vo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.hzitshop.entity.TbMenuAppDesc;

import java.io.Serializable;
import java.util.List;


/**
 * <p>
 * 
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-17
 */
public class TbMenuAppVo {



	private Integer id;
	private String appid;
    /**
     * 图标
     */
	private String icon;
	private String iconbg;
    /**
     * 桌面图标名称
     */
	private String name;
    /**
     * 超链接地址
     */
	private String url;
    /**
     * 窗口的高度
     */
	private Double height;
    /**
     * 窗口的宽度
     */
	private Double width;
    /**
     * 超链接target属性
     */
	private String target;
    /**
     * 资源类型:app(桌面图标),button(按钮)
     */
	private String type;
	private Integer parentId;
	private String parentIds;
	private String permission;
    /**
     * 该资源是否可用:0:不可用，1：可用,默认为1
     */
	private Integer available;
	/**
	 * 按钮描述
	 */
	private List<TbMenuAppDesc> tbMenuAppDescList;
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

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
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

	public List<TbMenuAppDesc> getTbMenuAppDescList() {
		return tbMenuAppDescList;
	}

	public void setTbMenuAppDescList(List<TbMenuAppDesc> tbMenuAppDescList) {
		this.tbMenuAppDescList = tbMenuAppDescList;
	}
}
