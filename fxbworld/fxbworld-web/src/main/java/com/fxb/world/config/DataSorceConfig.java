package com.fxb.world.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class DataSorceConfig {
	private Logger logger = LoggerFactory.getLogger(DataSorceConfig.class);

	/**
	 * fxb数据源
	 */
	@Value("${spring.datasource.fxb.url}")
	private String fxbUrl;

	@Value("${spring.datasource.fxb.username}")
	private String fxbUsername;

	@Value("${spring.datasource.fxb.password}")
	private String fxbPassword;

	@Value("${spring.datasource.fxb.driver-class-name}")
	private String fxbDriverClassName;

/**
	 * cluster数据源
	 */
	@Value("${spring.datasource.cluster.url}")
	private String clusterUrl;

	@Value("${spring.datasource.cluster.username}")
	private String clusterUsername;

	@Value("${spring.datasource.cluster.password}")
	private String clusterPassword;

	@Value("${spring.datasource.cluster.driver-class-name}")
	private String clusterDriverClassName;


	/**
	 * 连接池的配置信息  
	 */
	@Value("${spring.datasource.initialSize}")
	private int initialSize;

	@Value("${spring.datasource.minIdle}")
	private int minIdle;

	@Value("${spring.datasource.maxActive}")
	private int maxActive;

	@Value("${spring.datasource.maxWait}")
	private int maxWait;

	@Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;

	@Value("${spring.datasource.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;

	@Value("${spring.datasource.validationQuery}")
	private String validationQuery;

	@Value("${spring.datasource.testWhileIdle}")
	private boolean testWhileIdle;

	@Value("${spring.datasource.testOnBorrow}")
	private boolean testOnBorrow;

	@Value("${spring.datasource.testOnReturn}")
	private boolean testOnReturn;

	@Value("${spring.datasource.poolPreparedStatements}")
	private boolean poolPreparedStatements;

	@Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;

	@Value("${spring.datasource.filters}")
	private String filters;

	@Value("{spring.datasource.connectionProperties}")
	private String connectionProperties;

	@Value("${spring.datasource.useGlobalDataSourceStat}")
	private boolean useGlobalDataSourceStat;

	@Bean(name = "fxbDataSource")
	@Qualifier("fxbDataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.fxb")
	public DataSource fxbDataSource() {
		DruidDataSource datasource = new DruidDataSource();

		datasource.setUrl(fxbUrl);
		datasource.setUsername(fxbUsername);
		datasource.setPassword(fxbPassword);
		datasource.setDriverClassName(fxbDriverClassName);

		// configuration
		datasource.setInitialSize(initialSize);
		datasource.setMinIdle(minIdle);
		datasource.setMaxActive(maxActive);
		datasource.setMaxWait(maxWait);
		datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		datasource.setValidationQuery(validationQuery);
		datasource.setTestWhileIdle(testWhileIdle);
		datasource.setTestOnBorrow(testOnBorrow);
		datasource.setTestOnReturn(testOnReturn);
		datasource.setPoolPreparedStatements(poolPreparedStatements);
		datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		try {
			datasource.setFilters(filters);
		} catch (SQLException e) {
			logger.error("druid configuration initialization filter", e);
		}
		datasource.setConnectionProperties(connectionProperties);
		datasource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
		return datasource;
	}

	@Bean(name = "clusterDataSource")
	@Qualifier("clusterDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.cluster")
	public DataSource clusterDataSource() {
		DruidDataSource datasource = new DruidDataSource();

		datasource.setUrl(clusterUrl);
		datasource.setUsername(clusterUsername);
		datasource.setPassword(clusterPassword);
		datasource.setDriverClassName(clusterDriverClassName);

		// configuration
		datasource.setInitialSize(initialSize);
		datasource.setMinIdle(minIdle);
		datasource.setMaxActive(maxActive);
		datasource.setMaxWait(maxWait);
		datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		datasource.setValidationQuery(validationQuery);
		datasource.setTestWhileIdle(testWhileIdle);
		datasource.setTestOnBorrow(testOnBorrow);
		datasource.setTestOnReturn(testOnReturn);
		datasource.setPoolPreparedStatements(poolPreparedStatements);
		datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		try {
			datasource.setFilters(filters);
		} catch (SQLException e) {
			logger.error("druid configuration initialization filter", e);
		}
		datasource.setConnectionProperties(connectionProperties);
		datasource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
		return datasource;
	}
	

}
