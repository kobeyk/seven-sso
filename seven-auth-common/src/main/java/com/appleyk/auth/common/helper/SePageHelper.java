package com.appleyk.auth.common.helper;

import com.appleyk.auth.common.core.SePage;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>查询结果分页助手</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/18-10:17
 */
public class SePageHelper {
    public SePageHelper() {
    }
    /**
     *
     * @param items 对象列表
     * @param entityPages （mybatis）PageHelper（拦截器）的Page对象
     * @param <T>  业务（service）模型类型
     * @param <K>  数据（dao）实体类型
     * @return
     */
    public static <T, K> SePage<T> buildPage(List<T> items, Page<K> entityPages) {
        SePage page = new SePage();
        try {
            page.setItems(items);
            page.setPageItems((long)entityPages.getResult().size());
            page.setPageNo((long)entityPages.getPageNum());
            page.setPageSize((long)entityPages.getPageSize());
            page.setTotalPages((long)entityPages.getPages());
            page.setTotalItems(entityPages.getTotal());
        } catch (Exception var4) {
            SeLoggerHelper.error("构建分页对象失败", var4);
        }
        return page;
    }
    public static <T, K> SePage<T> buildPage(List<T> items, PageInfo<K> pageInfo) {
        SePage<T> page = new SePage();
        if (items != null && items.size() == 0) {
            items = null;
        }
        try {
            page.setItems(items);
            page.setPageItems((long)pageInfo.getSize());
            page.setPageNo((long)pageInfo.getPageNum());
            page.setPageSize((long)pageInfo.getPageSize());
            page.setTotalPages((long)pageInfo.getPages());
            page.setTotalItems(pageInfo.getTotal());
        } catch (Exception var4) {
            SeLoggerHelper.error("构建分页对象失败", var4);
        }
        return page;
    }
}
