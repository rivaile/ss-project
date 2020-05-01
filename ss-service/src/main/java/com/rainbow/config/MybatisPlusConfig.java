package com.rainbow.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author: lin.deng
 * @version: v1.0
 * @Description:
 * @date: 2019-09-19 17:34
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.rainbow.business.system.dao")
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // paginationInterceptor.setLimit(你的最大单页限制数量，默认 500 条，小于 0 如 -1 不受限制);
//        paginationInterceptor.setLimit(10);
        return paginationInterceptor;
    }
}
