package com.vaadin.demo.dashboard.data.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnection;
import com.vaadin.demo.dashboard.data.model.StampiProdotti;

@SuppressWarnings("unchecked")
public class RepositoryStampiProdotti {
	
	public RepositoryStampiProdotti() { }

	public List<StampiProdotti> getStampiProdotti() {
        System.out.println("Reading Stampi prodotti");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();

        List<StampiProdotti> lista = new ArrayList<StampiProdotti>();
		Criteria criteria = session.createCriteria(StampiProdotti.class);
		
		criteria.add(RepositoryUtils.getCriteraEliminato());
				
        lista = criteria.list();

        Set<StampiProdotti> stampiProdotti = new HashSet<>(lista);
        session.close();
        return new ArrayList<StampiProdotti>(stampiProdotti);
	}
}
