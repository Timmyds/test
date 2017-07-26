package com.fxb.world.util.security;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class UserResMenu implements Serializable {
	/**
	 * 菜单资源
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String menuName;

	private String menuPath;
	
	private String menulink;

	private String funCode;

	private String aliases;

	private Integer level;

	private Long prjId;

	private Long parentId;
	
	private Integer	isDisplay;//是否显示0：默认显示，1不显示

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuPath() {
		return menuPath;
	}

	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}

	public String getFunCode() {
		return funCode;
	}

	public void setFunCode(String funCode) {
		this.funCode = funCode;
	}

	public String getAliases() {
		return aliases;
	}

	public void setAliases(String aliases) {
		this.aliases = aliases;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getPrjId() {
		return prjId;
	}

	public void setPrjId(Long prjId) {
		this.prjId = prjId;
	}

	public String getMenulink() {
		return menulink;
	}

	public void setMenulink(String menulink) {
		this.menulink = menulink;
	}

	public Integer getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
