/*
 * @(#)PageRequest.java 0.1 Aug 11, 2008
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.util;

/**
 * Class <code>PageRequest</code> is ...
 * 
 * @author 
 * @version 0.1, Aug 11, 2008
 */
public class PageRequest {

    public static final int DEFAULT_PAGE_SIZE = 10;

    private int pageSize = DEFAULT_PAGE_SIZE;

    private int pageNo = 1;

    public PageRequest(int pageNo) {
	this(pageNo, 0);
    }

    public PageRequest(int pageNo, int pageSize) {
	this.pageNo = pageNo > 0 ? pageNo : 1;
	this.pageSize = pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE;
    }
    
    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the pageNo
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * @param pageNo the pageNo to set
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    
    public int getFirstIndex() {
	return pageSize * (pageNo - 1);
    }

}
