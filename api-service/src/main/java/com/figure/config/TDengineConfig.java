package com.figure.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.figure.core.tdrepository"},sqlSessionTemplateRef = "TDengineSqlSessionTemplate")
public class TDengineConfig {
    @Bean(name = "dataSource03")
    @Qualifier("dataSource03")
    @ConfigurationProperties(prefix = "spring.datasource03")
    public DataSource dataSource03() {
        try {
            Class.forName("com.taosdata.jdbc.TSDBDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new HikariDataSource();
    }

    @Bean(name = "TDengineSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource03") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:tdmapper/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "TDengineTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource03") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "TDengineSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("TDengineSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
