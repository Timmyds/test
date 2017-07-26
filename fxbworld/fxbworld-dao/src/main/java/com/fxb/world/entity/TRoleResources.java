package com.fxb.world.entity;

import java.io.Serializable;

import javax.persistence.*;

@Table(name = "t_role_resources")
public class TRoleResources implements  Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "resources_id")
    private Long resourcesId;

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
     * @return role_id
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * @return resources_id
     */
    public Long getResourcesId() {
        return resourcesId;
    }

    /**
     * @param resourcesId
     */
    public void setResourcesId(Long resourcesId) {
        this.resourcesId = resourcesId;
    }
}