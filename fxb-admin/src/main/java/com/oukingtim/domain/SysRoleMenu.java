package com.oukingtim.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by oukingtim
 */
@TableName("sys_role_menu")
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRoleMenu extends BaseModel<SysRoleMenu> {

    private String roleId;
    private String menuId;

    public SysRoleMenu(String menuId) {
        this.menuId = menuId;
    }

    public SysRoleMenu() {
    }

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
    
}
