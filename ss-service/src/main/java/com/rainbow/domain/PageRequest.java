package com.rainbow.domain;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-09-27 17:39
 */
public class PageRequest implements Serializable {

    @Min(value = 1, message = "当前页码不合法")
    private int current = 1;

    @Min(value = 1, message = "每页展示数量不合法")
    private int pageSize = 10;

    @ApiModelProperty("排序格式:column1 ase,column2 desc")
    private String orderby;

    @ApiModelProperty(hidden = true)
    private int offset;

    public int getOffset() {
        return (current - 1) * pageSize;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
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

    public List<OrderItem> getOrders() {
        List<OrderItem> orderItems = new ArrayList<>();
        String orderby = getOrderby();
        if (StringUtils.isNotEmpty(orderby)) {
            String[] sort = orderby.split(",");
            for (String s : sort) {
                String[] item = s.split(" ");
                OrderItem orderItem = new OrderItem();
                orderItem.setColumn(item[0]);
                orderItem.setAsc(item[1].trim().equalsIgnoreCase("asc"));
                orderItems.add(orderItem);
            }
        }
        return orderItems;
    }
}
