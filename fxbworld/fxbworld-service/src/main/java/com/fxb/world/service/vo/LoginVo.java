package com.fxb.world.service.vo;

import java.io.Serializable;

public class LoginVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1728274914515264468L;

	private Long id;
	private String loginName;
	private String password;
	private Integer errorCount;
	private String reqSource;

	private String verifiedCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}

	public String getVerifiedCode() {
		return verifiedCode;
	}

	public void setVerifiedCode(String verifiedCode) {
		this.verifiedCode = verifiedCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getReqSource() {
		return reqSource;
	}

	public void setReqSource(String reqSource) {
		this.reqSource = reqSource;
	}

	@Override
	public String toString() {
		return "LoginUserWebVo [id=" + id + ", loginName=" + loginName + ", password=" + password + ", errorCount=" + errorCount + ", reqSource=" + reqSource + ", verifiedCode=" + verifiedCode + "]";
	}

}
