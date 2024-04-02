package com.figure.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.figure.core.aop.TimeMetaObjectHandler;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;

@EnableTransactionManagement
@MapperScan(basePackages = {"com.figure.core.repository.*","com.figure.op.**.mapper"},sqlSessionTemplateRef  = "sqlSessionTemplate")
@Configuration
public class MybatisPlusConfig {

    @Value("${mybatis-plus.configuration.log-impl}")
    private String logImpl;

    @Value("${mybatis-plus.configuration.map-underscore-to-camel-case}")
    private boolean mapUnderscoreToCamelCase;

    @Value("${mybatis-plus.mapper-locations}")
    private String mapperLocations;

    @Resource
    TimeMetaObjectHandler timeMetaObjectHandler;


    @Bean(name = "dataSource01")
    @Qualifier("dataSource01")
    @ConfigurationProperties(prefix = "spring.datasource01")
    public DataSource dataSource01() {
        return new HikariDataSource();
    }

    @Bean(name = "dataSource02")
    @Qualifier("dataSource02")
    @ConfigurationProperties(prefix = "spring.datasource02")
    public DataSource dataSource02() {
        return new HikariDataSource();
    }


    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate mysqlSqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "sqlSessionFactory")
    public MybatisSqlSessionFactoryBean sqlSessionFactory(@Qualifier("dataSource01") DataSource dataSource,MybatisPlusInterceptor mybatisPlusInterceptor) throws IOException, ClassNotFoundException {
        MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource); // 设置使用的数据源

        // 创建自定义的 MybatisConfiguration 对象
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        // 进行 MyBatis-Plus 配置的相关设置，例如开启驼峰命名等
        mybatisConfiguration.setLogImpl((Class<? extends Log>) resolveLogImpl());
        mybatisConfiguration.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);

        mybatisConfiguration.setLocalCacheScope(LocalCacheScope.STATEMENT);
        mybatisConfiguration.setCacheEnabled(false);


        // 设置 Mapper XML 的位置
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources(mapperLocations));
        // 将自定义的 MybatisConfiguration 对象设置到 MybatisSqlSessionFactoryBean 中
        sessionFactory.setConfiguration(mybatisConfiguration);

        sessionFactory.setPlugins(mybatisPlusInterceptor);

        //设置自动填充
        GlobalConfig globalConfig=new GlobalConfig();
        globalConfig.setMetaObjectHandler(timeMetaObjectHandler);
        globalConfig.setBanner(false);
        sessionFactory.setGlobalConfig(globalConfig);

        return sessionFactory;
    }

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 自定义拦截器，先添加先执行。
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    private Class<?> resolveLogImpl() throws ClassNotFoundException {
        return Class.forName(logImpl);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("dataSource01") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource01") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}