package com.appleyk.auth.core.configure;

import com.appleyk.auth.core.config.SeDynamicTableConfig;
import com.appleyk.auth.core.redis.SeRedisPoolConfig;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * <p>
 *     配置选择器，集中选中多个配置类，然后交给ioc去批量处理成bean
 *     处理的配置类的解析器是：{@link org.springframework.context.annotation.ConfigurationClassParser}
 * </p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午10:05 2022/3/28
 */
public class ConfigSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{SeDynamicTableConfig.class.getName(),
                SeRedisPoolConfig.class.getName()};
    }
}
