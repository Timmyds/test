package com.fxb.world.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_resources")
public class TResources implements  Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 菜单名称
     */
    @Column(name = "menu_name")
    private String menuName;

    /**
     * 菜单路径
     */
    @Column(name = "menu_path")
    private String menuPath;

    /**
     * 菜单级别1:一级，2：二级，3：三级（功能菜单）
     */
    private Integer level;

    private String domain;

    /**
     * 菜单编码
     */
    private String funcode;

    /**
     * 父级菜单id
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 状态1：正常 0：关闭
     */
    private Integer status;

    /**
     * 菜单别名
     */
    private String aliases;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private Long createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_by")
    private Long updateBy;

    @Column(name = "update_date")
    private Date updateDate;

    private Integer sort;

    @Column(name = "is_display")
    private Integer isDisplay;

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
     * 获取菜单名称
     *
     * @return menu_name - 菜单名称
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * 设置菜单名称
     *
     * @param menuName 菜单名称
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * 获取菜单路径
     *
     * @return menu_path - 菜单路径
     */
    public String getMenuPath() {
        return menuPath;
    }

    /**
     * 设置菜单路径
     *
     * @param menuPath 菜单路径
     */
    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath;
    }

    /**
     * 获取菜单级别1:一级，2：二级，3：三级（功能菜单）
     *
     * @return level - 菜单级别1:一级，2：二级，3：三级（功能菜单）
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 设置菜单级别1:一级，2：二级，3：三级（功能菜单）
     *
     * @param level 菜单级别1:一级，2：二级，3：三级（功能菜单）
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * @return domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * @param domain
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * 获取菜单编码
     *
     * @return funcode - 菜单编码
     */
    public String getFuncode() {
        return funcode;
    }

    /**
     * 设置菜单编码
     *
     * @param funcode 菜单编码
     */
    public void setFuncode(String funcode) {
        this.funcode = funcode;
    }

    /**
     * 获取父级菜单id
     *
     * @return parent_id - 父级菜单id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父级菜单id
     *
     * @param parentId 父级菜单id
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取状态1：正常 0：关闭
     *
     * @return status - 状态1：正常 0：关闭
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态1：正常 0：关闭
     *
     * @param status 状态1：正常 0：关闭
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取菜单别名
     *
     * @return aliases - 菜单别名
     */
    public String getAliases() {
        return aliases;
    }

    /**
     * 设置菜单别名
     *
     * @param aliases 菜单别名
     */
    public void setAliases(String aliases) {
        this.aliases = aliases;
    }

    /**
     * 获取创建人
     *
     * @return create_by - 创建人
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人
     *
     * @param createBy 创建人
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
     * @return sort
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * @param sort
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * @return is_display
     */
    public Integer getIsDisplay() {
        return isDisplay;
    }

    /**
     * @param isDisplay
     */
    public void setIsDisplay(Integer isDisplay) {
        this.isDisplay = isDisplay;
    }

	@Override
	public String toString() {
		return "TResources [id=" + id + ", menuName=" + menuName + ", menuPath=" + menuPath + ", level=" + level
				+ ", domain=" + domain + ", funcode=" + funcode + ", parentId=" + parentId + ", status=" + status
				+ ", aliases=" + aliases + ", createBy=" + createBy + ", createDate=" + createDate + ", updateBy="
				+ updateBy + ", updateDate=" + updateDate + ", sort=" + sort + ", isDisplay=" + isDisplay + "]";
	}
    
}