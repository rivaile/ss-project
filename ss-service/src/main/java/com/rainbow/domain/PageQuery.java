package com.rainbow.domain;

import javax.validation.constraints.Min;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-09-27 17:39
 */
public class PageQuery {

    @Min(value = 1, message = "当前页码不合法")
    private int pageNo = 1;

    @Min(value = 1, message = "每页展示数量不合法")
    private int pageSize = 10;
    // column1 ase,column2 desc
    private String orderby;

    private int offset;

    public int getOffset() {
        return (pageNo - 1) * pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
