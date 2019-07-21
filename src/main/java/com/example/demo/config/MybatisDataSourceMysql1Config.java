package com.example.demo.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by LXX on 2018/11/28.
 */
@Configuration
@MapperScan(basePackages = "com.example.demo.mapper.mysql1",sqlSessionTemplateRef = "mysql1SqlSessionTemplate")
public class MybatisDataSourceMysql1Config {

    @Bean(name = "mysql1DataSource")
    @ConfigurationProperties(prefix = "datasource.mysql1")
    public DataSource mysql1DataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mysql1SqlSessionFactory")
    public SqlSessionFactory mysql1SqlSessionFactory(@Qualifier(value = "mysql1DataSource") DataSource dataSource) throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/mysql1/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "mysql1TransactionManager")
    public DataSourceTransactionManager mysql1TransactionManager(@Qualifier(value = "mysql1DataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "mysql1SqlSessionTemplate")
    public SqlSessionTemplate mysql1SqlSessionTemplate(@Qualifier(value = "mysql1SqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
