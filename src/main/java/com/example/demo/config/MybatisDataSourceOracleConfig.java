package com.example.demo.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by LXX on 2018/11/28.
 */
@Configuration
@MapperScan(basePackages = "com.example.demo.mapper.oracle",sqlSessionTemplateRef = "oracleSqlSessionTemplate")
public class MybatisDataSourceOracleConfig {

    @Bean(name = "oracleDataSource")
    @ConfigurationProperties(prefix = "datasource.oracle")
    @Primary
    public DataSource oracleDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "oracleSqlSessionFactory")
    @Primary
    public SqlSessionFactory oracleSqlSessionFactory(DataSource dataSource) throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/oracle/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "oracleTransactionManager")
    @Primary
    public DataSourceTransactionManager oracleTransactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "oracleSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate oracleSqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
