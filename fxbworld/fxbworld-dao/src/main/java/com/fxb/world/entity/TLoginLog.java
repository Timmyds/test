package com.fxb.world.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_login_log")
public class TLoginLog implements  Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "login_ip")
    private String loginIp;

    @Column(name = "login_address")
    private String loginAddress;

    /**
     * 登录方式
     */
    @Column(name = "login_mode")
    private String loginMode;

    @Column(name = "login_name")
    private String loginName;

    @Column(name = "login_date")
    private Date loginDate;

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
     * @return user_id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
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
     * @return login_address
     */
    public String getLoginAddress() {
        return loginAddress;
    }

    /**
     * @param loginAddress
     */
    public void setLoginAddress(String loginAddress) {
        this.loginAddress = loginAddress;
    }

    /**
     * 获取登录方式
     *
     * @return login_mode - 登录方式
     */
    public String getLoginMode() {
        return loginMode;
    }

    /**
     * 设置登录方式
     *
     * @param loginMode 登录方式
     */
    public void setLoginMode(String loginMode) {
        this.loginMode = loginMode;
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
     * @return login_date
     */
    public Date getLoginDate() {
        return loginDate;
    }

    /**
     * @param loginDate
     */
    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }
}