package com.vaadin.demo.dashboard.data.hibernate;

import java.sql.Connection;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.vaadin.demo.dashboard.data.model.Archive_21;
import com.vaadin.demo.dashboard.data.model.Archive_22;

public class DatabaseHibernateConnectionl7002619 {
	static Session sessione = null;	
	private static SessionFactory sessionFactory = null;
	private static ServiceRegistry serviceRegistry = null;
	private static Configuration configuration = null;
	
    public static SessionFactory getSessionFactory() {
    	try {
        	if (configuration == null) {
        		configuration = new Configuration();
                
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/l7002619?useSSL=false");
                settings.put(Environment.USER, "dchamba");
                settings.put(Environment.PASS, "Ch@Dha881");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "none");
                settings.put(Environment.USE_QUERY_CACHE, "false");
                settings.put(Environment.USE_SECOND_LEVEL_CACHE, "false");
                settings.put(Environment.C3P0_MAX_STATEMENTS, "0");
                settings.put(Environment.ISOLATION, String.valueOf(Connection.TRANSACTION_READ_COMMITTED));
                configuration.setProperties(settings);
                
//                <property name="hibernate.cache.use_second_level_cache">false</property>
//                <property name="hibernate.cache.use_query_cache">false</property>
//                <property name="hibernate.c3p0.max_statements">0</property>
                
                configuration.addAnnotatedClass(Archive_21.class);
                configuration.addAnnotatedClass(Archive_22.class);

                serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        	}
        } catch (Throwable e) {
            e.printStackTrace();
        }
		return getCurrentSessionFactory();
	}
    
    public static SessionFactory getNewSessionFactory() {
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }
    
    public static SessionFactory getCurrentSessionFactory() {
    	if(sessionFactory == null) {
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    	}
        return sessionFactory;
    }
}