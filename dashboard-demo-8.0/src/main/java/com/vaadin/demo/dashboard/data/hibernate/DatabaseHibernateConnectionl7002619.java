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
                
        		Properties properties = DatabaseHibernateConnectionUtils.getDatabaseHibernateConnectionProperties("l7002619");
        		configuration.setProperties(properties);
                
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