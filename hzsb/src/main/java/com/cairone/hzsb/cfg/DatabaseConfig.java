package com.cairone.hzsb.cfg;

import javax.sql.DataSource;

import net.sourceforge.jtds.jdbcx.JtdsDataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

	@Bean
	public DataSource databaseDataSource() {
		
		JtdsDataSource ds = new JtdsDataSource();
		
		ds.setServerName("localhost");
		ds.setDatabaseName("EJERCICIO_JPA");
		ds.setUser("sa");
		ds.setPassword("rv760");
		
		return ds;
	}
}
