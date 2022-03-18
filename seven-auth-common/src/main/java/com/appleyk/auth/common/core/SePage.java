package com.appleyk.auth.common.core;

import lombok.Data;

import java.util.List;

/**
 * <p>分页对象</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/18-10:04
 */
@Data
public class SePage<T> {
    private Long totalItems;
    private Long totalPages;
    private Long pageNo;
    private Long pageSize;
    private Long pageItems;
    private List<T> items;
    public SePage() {
    }
    public SePage(List<T> items) {
        this.items = items;
    }
    public SePage(List<T> items, Long totalItems, Long pageSize, Long pageNo) {
        this(items);
        this.totalItems = totalItems;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        if (pageSize != null && pageSize > 0L) {
            this.totalPages = totalItems / pageSize;
            if (totalItems % pageSize != 0L) {
                this.totalPages = this.totalPages + 1L;
            }
            this.pageItems = items == null ? 0L : (long)items.size();
        } else {
            this.pageNo = 0L;
            this.totalPages = 0L;
            this.pageItems = 0L;
        }
    }
}
