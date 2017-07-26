package com.fxb.world.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_user")
public class TUser implements  Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_name")
    private String loginName;

    /**
     * 用户昵称
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 登录密码
     */
    @Column(name = "login_pwd")
    private String loginPwd;

    /**
     * 状态
     */
    private Integer status;

    private String phone;

    @Column(name = "login_time")
    private Date loginTime;

    @Column(name = "login_ip")
    private String loginIp;

    @Column(name = "gmt_last")
    private Date gmtLast;

    /**
     * 是否是管理员
     */
    @Column(name = "is_manager")
    private Boolean isManager;


    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @Column(name = "login_count")
    private Long loginCount;

    @Column(name = "error_count")
    private Long errorCount;

    @Column(name = "account_type")
    private Integer accountType;

    @Column(name = "is_protection")
    private Integer isProtection;

    private String mail;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return login_name
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * @param loginName
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 获取用户昵称
     *
     * @return user_name - 用户昵称
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户昵称
     *
     * @param userName 用户昵称
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取登录密码
     *
     * @return login_pwd - 登录密码
     */
    public String getLoginPwd() {
        return loginPwd;
    }

    /**
     * 设置登录密码
     *
     * @param loginPwd 登录密码
     */
    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return login_time
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * @param loginTime
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * @return login_ip
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * @param loginIp
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    /**
     * @return gmt_last
     */
    public Date getGmtLast() {
        return gmtLast;
    }

    /**
     * @param gmtLast
     */
    public void setGmtLast(Date gmtLast) {
        this.gmtLast = gmtLast;
    }

    /**
     * 获取是否是管理员
     *
     * @return is_manager - 是否是管理员
     */
    public Boolean getIsManager() {
        return isManager;
    }

    /**
     * 设置是否是管理员
     *
     * @param isManager 是否是管理员
     */
    public void setIsManager(Boolean isManager) {
        this.isManager = isManager;
    }


    /**
     * @return create_date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return create_by
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * @return update_date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * @return update_by
     */
    public Long getUpdateBy() {
        return updateBy;
    }

    /**
     * @param updateBy
     */
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * @return login_count
     */
    public Long getLoginCount() {
        return loginCount;
    }

    /**
     * @param loginCount
     */
    public void setLoginCount(Long loginCount) {
        this.loginCount = loginCount;
    }

    /**
     * @return error_count
     */
    public Long getErrorCount() {
        return errorCount;
    }

    /**
     * @param errorCount
     */
    public void setErrorCount(Long errorCount) {
        this.errorCount = errorCount;
    }

    /**
     * @return account_type
     */
    public Integer getAccountType() {
        return accountType;
    }

    /**
     * @param accountType
     */
    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    /**
     * @return is_protection
     */
    public Integer getIsProtection() {
        return isProtection;
    }

    /**
     * @param isProtection
     */
    public void setIsProtection(Integer isProtection) {
        this.isProtection = isProtection;
    }

    /**
     * @return mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

	@Override
	public String toString() {
		return "TUser [id=" + id + ", loginName=" + loginName + ", userName=" + userName + ", loginPwd=" + loginPwd
				+ ", status=" + status + ", phone=" + phone + ", loginTime=" + loginTime + ", loginIp=" + loginIp
				+ ", gmtLast=" + gmtLast + ", isManager=" + isManager 
				+ ", createDate=" + createDate + ", createBy=" + createBy + ", updateDate=" + updateDate + ", updateBy="
				+ updateBy + ", loginCount=" + loginCount + ", errorCount=" + errorCount + ", accountType="
				+ accountType + ", isProtection=" + isProtection + ", mail=" + mail + "]";
	}
    
}