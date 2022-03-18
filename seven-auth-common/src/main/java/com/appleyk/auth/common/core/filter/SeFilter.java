package com.appleyk.auth.common.core.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * <p>基础过滤器（所有查询过滤器的基类）</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/18-10:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeFilter {
    private Long id;
    private Set<Long> ids;
    private String name;
    private Integer pageNum = 1;
    private Integer pageSize = 20;
}
