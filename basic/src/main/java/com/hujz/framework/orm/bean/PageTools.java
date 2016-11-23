package com.hujz.framework.orm.bean;

import java.io.Serializable;

/**
 *********************************************** 
 * @Create Date: 2015年9月12日 下午12:57:21
 * @Create Author: jiuzhou.hu
 * @File Name: PageTools
 * @Function: 封装分页参数.
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public class PageTools implements Serializable{

	/**
	 * @Title serialVersionUID
	 * @type long
	 * @date 2015-1-21 下午8:29:00
	 * 含义 TODO
	 */
	private static final long serialVersionUID = -6586839034985170593L;

	/** 传入：一页几行*/
	private int pageSize = 10;
	 
	/** 传入：开始行*/
	private int startRow;
	
	/** 返回：当前总行数*/
	private int recordCount;
	
	/** 返回：总页数*/
	private int recordPage = 0;

	/**当前页*/
    private int pageNo = 1;
    
    /**当前的连接*/
    private String url = "";
    
    /**当前几行*/
    private int recordCountNo = 0;
    
    /**
     * 是否查询总行数的标志
     */
    private boolean queryTotalCount = false;

	public int getStartRow() {
	    startRow = (pageNo - 1) * pageSize;
	    startRow = startRow < 0 ? 0 : startRow;
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
		if(pageSize > 0) {
		    pageNo = startRow/pageSize +1;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if(pageSize > 0) {
			this.pageSize = pageSize;
		}
        if(this.getRecordCountNo()>0){
            setRecordCountNo(this.getRecordCountNo());
        }
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public int getRecordCountNo() {
        return recordCountNo;
    }

    public void setRecordCountNo(int recordCountNo) {
        this.recordCountNo = recordCountNo;
        if(this.getPageSize()>0&&recordCountNo>0){
            setPageNo(recordCountNo/this.getPageSize()+1);
        }
    }

	/**
	 * queryTotalCount的获取.
	 * @return boolean
	 */
	public boolean isQueryTotalCount() {
		return queryTotalCount;
	}

	/**
	 * 设定queryTotalCount的值.
	 * @param boolean
	 */
	public void setQueryTotalCount(boolean queryTotalCount) {
		this.queryTotalCount = queryTotalCount;
	}

	public int getRecordPage() {
		if(this.recordCount > 0){
			
			if(this.recordCount % this.pageSize == 0){
				recordPage = this.recordCount / this.pageSize;
			}
			else{
				recordPage = this.recordCount / this.pageSize + 1;
			}
		}
		return recordPage;
	}
    
}
