package com.appleyk.auth.common.helper;

import com.appleyk.auth.common.core.filter.SeFilter;
import com.appleyk.auth.common.util.SeGeneralUtils;
import com.github.pagehelper.PageHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>过滤器（查询）助手</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/18-10:07
 */
public class SeFilterHelper {
    private List<String> ids = new ArrayList();
    private Long id;
    private List<String> names = new ArrayList();
    public SeFilterHelper() {
    }
    /**
     * 通过filter配置mybatis分页插件的属性配置
     * @param filter 基础过滤器类型（多态，这里传子类对象也OK）
     */
    public static void pageIntercept(SeFilter filter) {
        Integer pageNum = filter.getPageNum();
        Integer pageSize = filter.getPageSize();
        if (!SeGeneralUtils.isNotEmpty(pageNum)) {
            pageNum = 1;
        }
        if (SeGeneralUtils.isEmpty(pageSize)) {
            pageSize = 100;
            if (pageSize > 2000) {
                pageSize = 2000;
            }
            PageHelper.startPage(pageNum, pageSize);
        }
    }
}