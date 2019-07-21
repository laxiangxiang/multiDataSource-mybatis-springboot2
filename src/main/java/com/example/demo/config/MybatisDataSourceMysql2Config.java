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
@MapperScan(basePackages = "com.example.demo.mapper.mysql2",sqlSessionTemplateRef = "mysql2SqlSessionTemplate")
public class MybatisDataSourceMysql2Config {

    @Bean(name = "mysql2DataSource")
    @ConfigurationProperties(prefix = "datasource.mysql2")
    public DataSource mysql2DataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mysql2SqlSessionFactory")
    public SqlSessionFactory mysql2SqlSessionFactory(@Qualifier("mysql2DataSource")DataSource dataSource) throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/mysql2/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "mysql2TransactionManager")
    public DataSourceTransactionManager mysql2TransactionManager(@Qualifier("mysql2DataSource")DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "mysql2SqlSessionTemplate")
    public SqlSessionTemplate mysql2SqlSessionTemplate(@Qualifier("mysql2SqlSessionFactory")SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
