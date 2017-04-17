package com.cairone.hzsb.cfg;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.microsoft.sqlserver.jdbc.SQLServerXADataSource;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {
	
	@Autowired private DatabaseProps databaseProps = null;
	
	@Bean
	public DataSource databaseDataSource() {
		
		SQLServerXADataSource xaDataSource = new SQLServerXADataSource();
		
		xaDataSource.setServerName(databaseProps.getServerName());
		xaDataSource.setDatabaseName(databaseProps.getDatabaseName());
		xaDataSource.setUser(databaseProps.getUser());
		xaDataSource.setPassword(databaseProps.getPassword());
		
		AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
		
		ds.setXaDataSource(xaDataSource);
		ds.setUniqueResourceName("hzsb");
		ds.setPoolSize(5);
		
		return ds;
	}
}
