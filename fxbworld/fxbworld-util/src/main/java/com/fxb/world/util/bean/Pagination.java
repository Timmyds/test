package com.fxb.world.util.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 分页实体
 * @author WuNan2
 *
 * @version V1.0 2015年1月4日
 */
public class Pagination implements Serializable {
	private static final long serialVersionUID = -8852076508062554407L;
	
	private long total;
	private List<?> rows;

	/**
	 * 创建分页对象
	 * @param total		总记录数
	 * @param rows		当前页数据
	 * @return
	 */
	public static Pagination createPagination(long total, List<?> rows) {
		Pagination page = new Pagination();
		page.setRows(rows);
		page.setTotal(total);
		return page;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}
    /**
     * 计算当前页开始记录
     * @param pageSize 每页记录数
     * @param currentPage 当前第几页
     * @return 当前页开始记录号
     */
    public static int countOffset(final int pageSize,final int currentPage){
    	
    	if(pageSize <= 0)
    		return 0;
    	
        final int offset = pageSize*(currentPage-1);
        return offset;
    }
}
