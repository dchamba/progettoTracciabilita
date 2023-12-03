package com.vaadin.demo.dashboard.data.hibernate;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.vaadin.demo.dashboard.data.model.Accessi;
import com.vaadin.demo.dashboard.data.model.AnimeImballi;
import com.vaadin.demo.dashboard.data.model.Aziende;
import com.vaadin.demo.dashboard.data.model.Config;
import com.vaadin.demo.dashboard.data.model.Datamatrix;
import com.vaadin.demo.dashboard.data.model.FasiProcesso;
import com.vaadin.demo.dashboard.data.model.FasiProcessoProdotto;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiProcesso;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiProcessoTT;
import com.vaadin.demo.dashboard.data.model.EtichetteBancali;
import com.vaadin.demo.dashboard.data.model.EtichetteImballi;
import com.vaadin.demo.dashboard.data.model.EtichettePezzi;
import com.vaadin.demo.dashboard.data.model.Permessi;
import com.vaadin.demo.dashboard.data.model.Prodotti;
import com.vaadin.demo.dashboard.data.model.TipoImballi;
import com.vaadin.demo.dashboard.data.model.Utenti;
import com.vaadin.demo.dashboard.data.model.UtentiPermessi;
import com.vaadin.demo.dashboard.data.model.VistaPackingList;
import com.vaadin.demo.dashboard.data.model.VistaDatamatrixFasiProcesso;
import com.vaadin.demo.dashboard.data.model.VistaDatamatrixFasiProcessoTT;

public class DatabaseHibernateConnection {
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
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/datamatrixnewolef?useSSL=false");
                settings.put(Environment.USER, "dchamba");
                settings.put(Environment.PASS, "Ch@Dha881");

//               settings.put(Environment.URL, "jdbc:mysql://52.236.142.113:3306/datamatrixnewolef?useSSL=false");
//                settings.put(Environment.USER, "UtExt01");
//                settings.put(Environment.PASS, "P@sDB0001");
                
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
                
                configuration.addAnnotatedClass(Accessi.class);
                configuration.addAnnotatedClass(Aziende.class);
                configuration.addAnnotatedClass(Config.class);
                configuration.addAnnotatedClass(Datamatrix.class);
                configuration.addAnnotatedClass(DatamatrixFasiProcesso.class);
                configuration.addAnnotatedClass(DatamatrixFasiProcessoTT.class);
                configuration.addAnnotatedClass(FasiProcesso.class);
                configuration.addAnnotatedClass(FasiProcessoProdotto.class);
                configuration.addAnnotatedClass(Permessi.class);
                configuration.addAnnotatedClass(Prodotti.class);
                configuration.addAnnotatedClass(Utenti.class);
                configuration.addAnnotatedClass(UtentiPermessi.class);
                configuration.addAnnotatedClass(TipoImballi.class);
                configuration.addAnnotatedClass(EtichetteImballi.class);
                configuration.addAnnotatedClass(EtichettePezzi.class);
                configuration.addAnnotatedClass(EtichetteBancali.class);
                configuration.addAnnotatedClass(AnimeImballi.class);
                configuration.addAnnotatedClass(VistaPackingList.class);
                configuration.addAnnotatedClass(VistaDatamatrixFasiProcesso.class);
                configuration.addAnnotatedClass(VistaDatamatrixFasiProcessoTT.class);

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