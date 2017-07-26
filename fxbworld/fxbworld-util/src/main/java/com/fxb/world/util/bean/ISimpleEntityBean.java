package com.fxb.world.util.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 简单实体接口（不包含Name）
 * @author Administrator
 *
 */
public interface ISimpleEntityBean extends Serializable {
	/**
	 * 获取ID
	 * 
	 * @return
	 */
	Integer getId();

	/**
	 * 设置ID
	 * 
	 * @param id
	 */
	void setId(Integer id);
	
	/**
	 * 获取最后修改的用户
	 * 
	 * @return
	 */
	Serializable getLastUpdateUserId();

	/**
	 * 设置最后修改的用户
	 * 
	 * @param lastUpdateUserId
	 */
	void setLastUpdateUserId(Serializable lastUpdateUserId);

	/**
	 * 获取最后修改的时间
	 * 
	 * @return
	 */
	Date getLastUpdateTime();

	/**
	 * 设置最后修改的时间
	 * 
	 * @param lastUpdateTime
	 */
	void setLastUpdateTime(Date lastUpdateTime);

	/**
	 * 获取创建的用户
	 * 
	 * @return
	 */
	Serializable getCreationUserId();

	/**
	 * 设置创建的用户
	 * 
	 * @param creationUserId
	 */
	void setCreationUserId(Serializable creationUserId);

	/**
	 * 获取创建的时间
	 * 
	 * @return
	 */
	Date getCreationTime();

	/**
	 * 设置创建的时间
	 * 
	 * @param creationTime
	 */
	void setCreationTime(Date creationTime);

	/**
	 * 获取创建的APP
	 * @return
	 */
	Integer getCreationApp();

	/**
	 * 设置创建的APP
	 * @param creationApp
	 */
	void setCreationApp(Integer creationApp);

	/**
	 * 获取创建的API版本
	 * @return
	 */
	Integer getCreationApi();

	/**
	 * 设置创建的API版本
	 * @param creationApi
	 */
	void setCreationApi(Integer creationApi);

	/**
	 * 获取最后更新的APP
	 * @return
	 */
	Integer getLastUpdateApp();
	
	/**
	 * 设置最后更新的APP
	 * @param lastUpdateApp
	 */
	void setLastUpdateApp(Integer lastUpdateApp);

	/**
	 * 获取最后更新的API版本
	 * @return
	 */
	Integer getLastUpdateApi();

	/**
	 * 设置最后更新的API版本
	 * @param lastUpdateApi
	 */
	void setLastUpdateApi(Integer lastUpdateApi);

	/**
	 * 是否启用
	 * 
	 * @return
	 */
	Boolean isEnable();

	/**
	 * 是否启用
	 * 
	 * @param enable
	 */
	void setEnable(boolean enable);
}
