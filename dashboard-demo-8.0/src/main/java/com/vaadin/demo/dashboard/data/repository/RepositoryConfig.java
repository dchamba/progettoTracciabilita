package com.vaadin.demo.dashboard.data.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnection;
import com.vaadin.demo.dashboard.data.model.Config;

@SuppressWarnings("unchecked")
public class RepositoryConfig {
	
	public RepositoryConfig() { }
	
	public String getConfigByChiave(String chiave) {
        System.out.println("Reading Config");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();

        List<Config> config = new ArrayList<Config>();
		Criteria criteria = session.createCriteria(Config.class);
		
		if (chiave != null && !chiave.isEmpty()) {
			criteria.add(Restrictions.like("chiave", chiave, MatchMode.EXACT));
		} else {
	        session.close();
			return null;
		}

        config = criteria.list();
        session.close();

        return (config != null && config.size() > 0) ? config.get(0).getValore() : null;
	}
	
}
