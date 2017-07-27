/**
 * Project Name:km-ms
 * File Name:SerieTreeVo.java
 * Package Name:com.kingmed.ms.sales.vo
 * Date:2015年5月7日下午4:10:25
 * Copyright (c) 2015, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.oukingtim.web.vm;

import com.oukingtim.domain.SysMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单数据返回对象
 * Created by oukingtim
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuVM implements Serializable {

    private String id;
    private String parentId;
    private String name;
    private String stateRef;
    private String type;
    private String icon;
    private String title;
    private Integer level;
    private Integer order;
    private List<MenuVM> subMenu;
	public MenuVM() {
	}
	public MenuVM(SysMenu sm) {
        BeanUtils.copyProperties(sm, this);
        this.stateRef = sm.getName();
    }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStateRef() {
		return stateRef;
	}
	public void setStateRef(String stateRef) {
		this.stateRef = stateRef;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public List<MenuVM> getSubMenu() {
		return subMenu;
	}
	public void setSubMenu(List<MenuVM> subMenu) {
		this.subMenu = subMenu;
	}
	
}

