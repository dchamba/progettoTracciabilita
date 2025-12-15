package com.vaadin.demo.dashboard.data.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnection;
import com.vaadin.demo.dashboard.data.model.TipiDifetto;

public class RepositoryTipiDifetto {

    private static RepositoryTipiDifetto repositoryTipiDifetto;

    public RepositoryTipiDifetto() { }

    public static RepositoryTipiDifetto getInstance() {
        if (repositoryTipiDifetto == null) {
            repositoryTipiDifetto = new RepositoryTipiDifetto();
        }
        return repositoryTipiDifetto;
    }

	public List<TipiDifetto> getTipiDifetti() {
        System.out.println("Reading tipi difetto");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();

        List<TipiDifetto> listaTipiDifetti = new ArrayList<TipiDifetto>();
		Criteria criteria = session.createCriteria(TipiDifetto.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
		criteria.addOrder(Order.asc("idTipoDifetto"));
				
		listaTipiDifetti = criteria.list();

        session.close();
        return listaTipiDifetti;
	}
}
