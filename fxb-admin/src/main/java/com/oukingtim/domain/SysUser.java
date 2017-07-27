package com.oukingtim.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by oukingtim
 */
@TableName("sys_user")
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser extends BaseModel<SysUser> {

    private String username;
    private String password;
    private String email;
    private String mobile;
    private String status;

    @TableField(exist=false)
    private List<SysRole> rolelist;


    public SysUser(String username) {
        this.username = username;
    }

    public SysUser() {
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<SysRole> getRolelist() {
		return rolelist;
	}

	public void setRolelist(List<SysRole> rolelist) {
		this.rolelist = rolelist;
	}
    
}
