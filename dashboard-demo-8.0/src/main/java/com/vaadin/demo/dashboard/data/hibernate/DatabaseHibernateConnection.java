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
import com.vaadin.demo.dashboard.data.model.CriteriBloccoDatamatrix;
import com.vaadin.demo.dashboard.data.model.Datamatrix;
import com.vaadin.demo.dashboard.data.model.FasiProcesso;
import com.vaadin.demo.dashboard.data.model.FasiProcessoProdotto;
import com.vaadin.demo.dashboard.data.model.VistaLottiFusioneAssegnazioneStampi;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiProcesso;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiProcessoTT;
import com.vaadin.demo.dashboard.data.model.DatiProvaTenutaElio;
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

        		Properties properties = DatabaseHibernateConnectionUtils.getDatabaseHibernateConnectionProperties("datamatrixnewolef");
        		configuration.setProperties(properties);
                
                configuration.addAnnotatedClass(Accessi.class);
                configuration.addAnnotatedClass(Aziende.class);
                configuration.addAnnotatedClass(Config.class);
                configuration.addAnnotatedClass(CriteriBloccoDatamatrix.class);
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
                configuration.addAnnotatedClass(VistaLottiFusioneAssegnazioneStampi.class);
                configuration.addAnnotatedClass(VistaPackingList.class);
                configuration.addAnnotatedClass(VistaDatamatrixFasiProcesso.class);
                configuration.addAnnotatedClass(VistaDatamatrixFasiProcessoTT.class);
                configuration.addAnnotatedClass(VistaLottiFusioneAssegnazioneStampi.class);

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