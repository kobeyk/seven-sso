package com.appleyk.auth.core.model.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>所有业务模型对象的基（父）类</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/18-13:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeObject implements Serializable {
    private Long id;
    private String name;
    private Integer checkStatus = SeCheckStatus.PENDING;
}
