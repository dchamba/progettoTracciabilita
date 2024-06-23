package com.vaadin.demo.dashboard.data.hibernate;

import java.sql.Connection;
import java.util.Properties;

import org.hibernate.cfg.Environment;

public class DatabaseHibernateConnectionUtils {

	public static Properties getDatabaseHibernateConnectionProperties(String databaseName) {

        Properties settings = new Properties();
        settings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
        
        setLocalhostDatabaseProperties(settings, databaseName, true);
        
        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        settings.put(Environment.HBM2DDL_AUTO, "none");
        settings.put(Environment.USE_QUERY_CACHE, "false");
        settings.put(Environment.USE_SECOND_LEVEL_CACHE, "false");
        settings.put(Environment.C3P0_MAX_STATEMENTS, "0");
        settings.put(Environment.ISOLATION, String.valueOf(Connection.TRANSACTION_READ_COMMITTED));
        return settings;
	}
	
	public static void setLocalhostDatabaseProperties(Properties settings, String databaseName, boolean setLocalHost) {
		if(setLocalHost) {
			settings.put(Environment.URL, "jdbc:mysql://localhost:3306/" + databaseName + "?useSSL=false");
			settings.put(Environment.USER, "dchamba");
			settings.put(Environment.PASS, "Ch@Dha881");
		} else {
			settings.put(Environment.URL, "jdbc:mysql://52.236.142.113:3306/" + databaseName + "?useSSL=false");
			settings.put(Environment.USER, "UtExt01");
		    settings.put(Environment.PASS, "P@sDB0001");	
		}      
	}
}
