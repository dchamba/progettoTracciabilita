package com.vaadin.demo.dashboard.data.hibernate;

import java.sql.Connection;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.vaadin.demo.dashboard.data.model.DatiProvaTenutaElio;

public class DatabaseHibernateConnectionDatamatrix_Syncro {
	static Session sessione = null;	
	private static SessionFactory sessionFactory = null;
	private static ServiceRegistry serviceRegistry = null;
	private static Configuration configuration = null;
	
    public static SessionFactory getSessionFactory() {
    	try {
        	if (configuration == null) {
        		configuration = new Configuration();
                
        		Properties properties = DatabaseHibernateConnectionUtils.getDatabaseHibernateConnectionProperties("Datamatrix_Syncro");
        		configuration.setProperties(properties);
        		
                configuration.addAnnotatedClass(DatiProvaTenutaElio.class);

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