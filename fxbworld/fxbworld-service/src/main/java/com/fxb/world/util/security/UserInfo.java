package com.fxb.world.util.security;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

public class UserInfo implements Serializable {

	/**
	 * 用户基本信息
	 */
	private static final long serialVersionUID = 1L;

	private Long userId;
	private String loginName;
	private String userName;//用户名称、县域名称网点名称
	private Date gmtLast;// 上次登录时间
	private String loginIp;//登录ip
	private String lastIp;//上次登录时间
	private Date loginTime;// 登录时间
	private Long loginCount;
	private String token;
	private String sessionId;
	private Integer roleType; // roleType; // 角色类型 2:平台管理 3:县域 4:网点，5：供应商9会员
	private Long roleId; // 角色id
	// 主账户id
	private Long masteraccId;//平台账户id/供应商账户id/中心账户id/网点账户id
	
	private Long errorCount;
	private boolean isManager;//是否为管理员账号
	
	private List<UserResMenu> resourcesMenu;//菜单

	
	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getLoginName() {
		return loginName;
	}


	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public Date getGmtLast() {
		return gmtLast;
	}


	public void setGmtLast(Date gmtLast) {
		this.gmtLast = gmtLast;
	}


	public String getLoginIp() {
		return loginIp;
	}


	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}


	public String getLastIp() {
		return lastIp;
	}


	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}


	public Date getLoginTime() {
		return loginTime;
	}


	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}


	public Long getLoginCount() {
		return loginCount;
	}


	public void setLoginCount(Long loginCount) {
		this.loginCount = loginCount;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public String getSessionId() {
		return sessionId;
	}


	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}


	public Integer getRoleType() {
		return roleType;
	}


	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}


	public Long getRoleId() {
		return roleId;
	}


	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}


	public Long getMasteraccId() {
		return masteraccId;
	}


	public void setMasteraccId(Long masteraccId) {
		this.masteraccId = masteraccId;
	}


	public Long getErrorCount() {
		return errorCount;
	}


	public void setErrorCount(Long errorCount) {
		this.errorCount = errorCount;
	}


	public boolean isManager() {
		return isManager;
	}


	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}


	public List<UserResMenu> getResourcesMenu() {
		return resourcesMenu;
	}


	public void setResourcesMenu(List<UserResMenu> resourcesMenu) {
		this.resourcesMenu = resourcesMenu;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
