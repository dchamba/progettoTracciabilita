package com.vaadin.demo.dashboard.data.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnection;
import com.vaadin.demo.dashboard.data.model.Utenti;
import com.vaadin.demo.dashboard.data.repository.campi.CampiUtenti;

@SuppressWarnings("unchecked")
public class RepositoryUtenti {
	
	public RepositoryUtenti() { }

	public List<Utenti> getUtenti() {
        System.out.println("Reading utenti");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();

        List<Utenti> utenti = new ArrayList<Utenti>();
		Criteria criteria = session.createCriteria(Utenti.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
				
        utenti = criteria.list();

        session.close();
        return utenti;
	}
	
	public Utenti getUtenteByEmailAndPassword(String email, String password) {
        System.out.println("Reading utenti");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();

        List<Utenti> utenti = new ArrayList<Utenti>();
		Criteria criteria = session.createCriteria(Utenti.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
		if (email != null && !email.isEmpty()) {
			criteria.add(Restrictions.like(CampiUtenti.email, email, MatchMode.EXACT));
		} else {
	        session.close();
			return null;
		}
		
		if (password != null && !password.isEmpty()) {
			criteria.add(Restrictions.like(CampiUtenti.password, password, MatchMode.EXACT));
		} else {
	        session.close();
			return null;
		}

        utenti = criteria.list();
        session.close();

        return (utenti != null && utenti.size() > 0) ? utenti.get(0) : null;
	}
	
	public Utenti getUtenteById(Integer idUtente) {
        System.out.println("Reading utenti");

        List<Utenti> utenti = new ArrayList<Utenti>();
        
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Utenti.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
		if (idUtente != null && idUtente > 0) {
			criteria.add(Restrictions.eq(CampiUtenti.idUtente, idUtente));
		}
		
		utenti = criteria.list();
        session.close();
		
        System.out.printf("%-30.30s  %-30.30s%n", "Model", "Price");
        for (Utenti utente : utenti) {
            System.out.printf(utente.getNomeCognome() + " " + utente.getEmail());
        }
        return (utenti != null && utenti.size() > 0) ? utenti.get(0) : null;
	}
}
