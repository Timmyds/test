package com.fxb.world.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_phone_operate_log")
public class TPhoneOperateLog implements  Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "operate_ip")
    private String operateIp;

    @Column(name = "operate_address")
    private String operateAddress;

    @Column(name = "operate_mode")
    private String operateMode;

    @Column(name = "login_name")
    private String loginName;

    @Column(name = "operate_date")
    private Date operateDate;

    @Column(name = "operate_user_name")
    private String operateUserName;

    @Column(name = "operate_user_id")
    private Long operateUserId;

    private String phone;

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
     * @return operate_ip
     */
    public String getOperateIp() {
        return operateIp;
    }

    /**
     * @param operateIp
     */
    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }

    /**
     * @return operate_address
     */
    public String getOperateAddress() {
        return operateAddress;
    }

    /**
     * @param operateAddress
     */
    public void setOperateAddress(String operateAddress) {
        this.operateAddress = operateAddress;
    }

    /**
     * @return operate_mode
     */
    public String getOperateMode() {
        return operateMode;
    }

    /**
     * @param operateMode
     */
    public void setOperateMode(String operateMode) {
        this.operateMode = operateMode;
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
     * @return operate_date
     */
    public Date getOperateDate() {
        return operateDate;
    }

    /**
     * @param operateDate
     */
    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    /**
     * @return operate_user_name
     */
    public String getOperateUserName() {
        return operateUserName;
    }

    /**
     * @param operateUserName
     */
    public void setOperateUserName(String operateUserName) {
        this.operateUserName = operateUserName;
    }

    /**
     * @return operate_user_id
     */
    public Long getOperateUserId() {
        return operateUserId;
    }

    /**
     * @param operateUserId
     */
    public void setOperateUserId(Long operateUserId) {
        this.operateUserId = operateUserId;
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
}