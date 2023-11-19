package com.vaadin.demo.dashboard.data.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnection;
import com.vaadin.demo.dashboard.data.model.UtentiPermessi;
import com.vaadin.demo.dashboard.data.repository.campi.CampiUtenti;

@SuppressWarnings("unchecked")
public class RepositoryUtentiPermessi {
	
	public RepositoryUtentiPermessi() { }

	public List<UtentiPermessi> getUtentiPermessi(Integer idUtente) {
        System.out.println("Reading utenti permessi");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();

        List<UtentiPermessi> utentiPermessi = new ArrayList<UtentiPermessi>();
		Criteria criteria = session.createCriteria(UtentiPermessi.class);

		if (idUtente != null && idUtente > 0) {
			criteria.add(Restrictions.eq(CampiUtenti.idUtente, idUtente));
		}
		
        utentiPermessi = criteria.list();

        session.close();
        return utentiPermessi;
	}
	
	public List<String> getPermessiUtenteCorrente(Integer idUtente) {
		List<String> listaPermessi = new ArrayList<String>();
		
		List<UtentiPermessi> utentiPermessi = getUtentiPermessi(idUtente);
		if(utentiPermessi != null && !utentiPermessi.isEmpty()) {
			utentiPermessi.forEach(p -> {
				listaPermessi.add(p.getIdPermesso());
			});
		}
		
		return listaPermessi;
	}
}
