package com.fxb.world.entity;

import java.io.Serializable;

import javax.persistence.*;

@Table(name = "t_user_resources")
public class TUserResources implements  Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 菜单id
     */
    @Column(name = "resources_id")
    private Long resourcesId;

    /**
     * 状态1：权限限制，0：权限授权
     */
    private Byte status;

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
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取菜单id
     *
     * @return resources_id - 菜单id
     */
    public Long getResourcesId() {
        return resourcesId;
    }

    /**
     * 设置菜单id
     *
     * @param resourcesId 菜单id
     */
    public void setResourcesId(Long resourcesId) {
        this.resourcesId = resourcesId;
    }

    /**
     * 获取状态1：权限限制，0：权限授权
     *
     * @return status - 状态1：权限限制，0：权限授权
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态1：权限限制，0：权限授权
     *
     * @param status 状态1：权限限制，0：权限授权
     */
    public void setStatus(Byte status) {
        this.status = status;
    }
}