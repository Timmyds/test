//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.oukingtim.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通用实体（通用字段）
 * Created by oukingtim
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseModel<T extends BaseModel<?>> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;

    private String createUserId;

    private Date createTime;

    private String updateUserId;

    private Date updateTime;

    protected Serializable pkVal() {
        // TODO Auto-generated method stub
        return id;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
    
}
