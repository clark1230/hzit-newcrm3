package com.hzitshop.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
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
@TableName("tb_dict")
public class TbDict extends Model<TbDict> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="id",type= IdType.AUTO)
	private Integer id;
	private String code;
	private Integer num;
    /**
     * 父级id
     */
	@TableField(value = "pid")
	private Integer pId;
    /**
     * 标签名
     */
	private String name;
    /**
     * 提示
     */
	private String tips;
	private Integer version;
    /**
     * 图标
     */
	private String icon;
    /**
     * 该节点是否打开
     */
	private String open;
	private String checked;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
