package com.oukingtim.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.oukingtim.web.vm.JsTreeVM;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by oukingtim
 */
@TableName("sys_role")
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRole extends BaseModel<SysRole> {

    private String roleName;
    private String remark;
    @TableField(exist=false)
    private Boolean checked;
    @TableField(exist=false)
    private List<JsTreeVM> menuTree;

    public SysRole() {
    }

    public SysRole(String roleName) {
        this.roleName = roleName;
    }

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public List<JsTreeVM> getMenuTree() {
		return menuTree;
	}

	public void setMenuTree(List<JsTreeVM> menuTree) {
		this.menuTree = menuTree;
	}
    
}
