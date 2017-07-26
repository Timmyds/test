package com.fxb.world.config;

import java.util.Properties;

import javax.sql.DataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.github.pagehelper.PageHelper;

@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = ClusterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "clusterSqlSessionFactory")
public class ClusterDataSourceConfig {

	// 精确到 cluster 目录，以便跟其他数据源隔离
	static final String PACKAGE = "com.fxb.world.dao.cluster";
	static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

/*	@Value("${cluster.datasource.url}")
	private String url;

	@Value("${cluster.datasource.username}")
	private String user;

	@Value("${cluster.datasource.password}")
	private String password;

	@Value("${cluster.datasource.driverClassName}")
	private String driverClass;

	@Bean(name = "clusterDataSource")
	public DataSource clusterDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		return dataSource;
	}*/
	@Autowired
	@Qualifier("clusterDataSource")
	private DataSource clusterDataSource;
	
	@Bean(name = "clusterTransactionManager")
	public DataSourceTransactionManager clusterTransactionManager() {
		return new DataSourceTransactionManager(clusterDataSource);
	}

	@Bean(name = "clusterSqlSessionFactory")
	public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("clusterDataSource") DataSource clusterDataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(clusterDataSource);
		 // 分页插件
        PageHelper pageHelper = new PageHelper();
        Properties props = new Properties();
        props.setProperty("reasonable", "true");
        props.setProperty("supportMethodsArguments", "true");
        props.setProperty("returnPageInfo", "check");
        props.setProperty("params", "count=countSql");
        pageHelper.setProperties(props);
        // 添加插件
        sessionFactory.setPlugins(new Interceptor[] { pageHelper });
		sessionFactory.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources(ClusterDataSourceConfig.MAPPER_LOCATION));

		return sessionFactory.getObject();
	}
	 
}
