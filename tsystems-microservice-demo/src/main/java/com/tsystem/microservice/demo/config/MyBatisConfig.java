package com.tsystem.microservice.demo.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.alibaba.druid.pool.DruidDataSource;
import com.tsystems.core.db.DynamicDataSource;


/**
 * 
 * @author Lichunlei
 *
 */
@Configuration
@MapperScan(basePackages = "com.tsystem.microservice.demo.repository.db.mapper")
public class MyBatisConfig {

	@Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource getMasterDataSource() {
        return new DruidDataSource();
    }
	
	@Bean
    @Primary
    public DynamicDataSource getDataSource(
            @Qualifier("masterDataSource") DataSource masterDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(BrandDB.MASTER.class.getSimpleName(), masterDataSource);

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(masterDataSource);
        return dataSource;
    }

	@Bean(name = "sqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory(DynamicDataSource ds)
            throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setVfs(SpringBootVFS.class);
        fb.setDataSource(ds);
   
        fb.setConfigLocation(new DefaultResourceLoader()
               .getResource("classpath:mybatis.xml"));
        fb.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/*.xml"));
        fb.setTypeAliasesPackage("com.tsystem.microservice.demo.domain.model");
        

        return fb.getObject();
    }
}
