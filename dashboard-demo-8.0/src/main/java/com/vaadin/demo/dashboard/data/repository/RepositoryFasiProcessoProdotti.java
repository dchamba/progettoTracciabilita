package com.vaadin.demo.dashboard.data.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnection;
import com.vaadin.demo.dashboard.data.model.FasiProcessoProdotto;
import com.vaadin.demo.dashboard.data.model.Prodotti;
import com.vaadin.demo.dashboard.data.model.Utenti;
import com.vaadin.demo.dashboard.data.repository.campi.CampiFasiProcessoProdotto;
import com.vaadin.demo.dashboard.data.repository.campi.CampiProdotti;

@SuppressWarnings("unchecked")
public class RepositoryFasiProcessoProdotti {
	
	public RepositoryFasiProcessoProdotti() { }
	
	public List<FasiProcessoProdotto> getFasiProcessoProdotto(Integer codiceProdotto) {
        System.out.println("Reading fasiProcessoProdotto");
        
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(FasiProcessoProdotto.class);
		criteria.add(Restrictions.eq(CampiFasiProcessoProdotto.id_idProdotto, codiceProdotto));
		criteria.addOrder(Order.asc(CampiFasiProcessoProdotto.ordine));
		
        List<FasiProcessoProdotto> fasiProcessoProdotto = criteria.list();
        session.close();
        return fasiProcessoProdotto;
	}

	public FasiProcessoProdotto getFaseProcessoProdotto(int idProdotto, Integer idFaseProcesso) {
        System.out.println("Reading fasiProcessoProdotto");
        
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(FasiProcessoProdotto.class);
		criteria.add(Restrictions.eq(CampiFasiProcessoProdotto.id_idProdotto, idProdotto));
		criteria.add(Restrictions.eq(CampiFasiProcessoProdotto.id_idFaseProcesso, idFaseProcesso));
		criteria.setMaxResults(1);
		
		FasiProcessoProdotto result = null;
        List<FasiProcessoProdotto> fasiProcessoProdotto = criteria.list();
        if(fasiProcessoProdotto != null && fasiProcessoProdotto.size() > 0) {
        	result = fasiProcessoProdotto.get(0);
        }
        session.close();
        return result;
	}

	public List<FasiProcessoProdotto> getFasiProcessoProdottoFinoAOrdine(Integer ordine, Integer idProdotto) {
		 System.out.println("Reading fasiProcessoProdotto");
	        
			Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria criteria = session.createCriteria(FasiProcessoProdotto.class);
			criteria.add(Restrictions.lt(CampiFasiProcessoProdotto.ordine, ordine));
			criteria.add(Restrictions.eq(CampiFasiProcessoProdotto.id_idProdotto, idProdotto));
			criteria.addOrder(Order.desc(CampiFasiProcessoProdotto.ordine));
			
	        List<FasiProcessoProdotto> fasiProcessoProdotto = criteria.list();
	        session.close();
	        return fasiProcessoProdotto;
	}   
}
