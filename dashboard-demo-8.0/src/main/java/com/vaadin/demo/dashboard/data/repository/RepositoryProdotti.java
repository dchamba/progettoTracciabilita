package com.vaadin.demo.dashboard.data.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnection;
import com.vaadin.demo.dashboard.data.model.Prodotti;
import com.vaadin.demo.dashboard.data.repository.campi.CampiProdotti;

@SuppressWarnings("unchecked")
public class RepositoryProdotti {
	
	public RepositoryProdotti() { }
	
	public Prodotti getProdotto(String codiceProdotto) {
        System.out.println("Reading prodotti");
        
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Prodotti.class);
		criteria.add(Restrictions.ilike(CampiProdotti.codiceDatamatrix, codiceProdotto, MatchMode.EXACT));
		
        List<Prodotti> prodotti = criteria.list();
        Prodotti prodotto = null;
        if (prodotti != null && !prodotti.isEmpty()) {
            prodotto = prodotti.get(0);
        }
		
        session.close();
        return prodotto;
	}
	
	public List<Prodotti> getProdotti() {
        System.out.println("Reading prodotti");
        
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Prodotti.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());

        List<Prodotti> prodotti = criteria.list();
        session.close();
        return prodotti;
	}
	
	public List<Prodotti> getProdottoByPackingListPermesso(String packingListPermesso) {
        System.out.println("Reading prodotti");
        
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Prodotti.class);
		criteria.add(Restrictions.ilike(CampiProdotti.packingListPermesso, packingListPermesso, MatchMode.EXACT));
		
        List<Prodotti> prodotti = criteria.list();
        session.close();
        return prodotti;
	}
	
	public Prodotti getProdottoByNumeroDisegno(String numeroDisegno) {
        System.out.println("Reading prodotti");
        
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Prodotti.class);
		criteria.add(Restrictions.ilike(CampiProdotti.numeroDisegno, numeroDisegno, MatchMode.EXACT));

        List<Prodotti> prodotti = criteria.list();
        Prodotti prodotto = null;
        if (prodotti != null && !prodotti.isEmpty()) {
            prodotto = prodotti.get(0);
        }
        
        session.close();
        return prodotto;
	}
	
	public Prodotti getProdottoFromDatamatrixFormat(String codiceDataMatrix, boolean throwExceptionSeFormatoNonEsiste) throws Exception {
		System.out.println("Reading prodotti");
        
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();
		
        List<Prodotti> prodotti = new ArrayList<Prodotti>();
		Criteria criteria = session.createCriteria(Prodotti.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
				
		prodotti = criteria.list();
        session.close();
        
        for (Prodotti prodottoToCheck : prodotti) {
        	if(prodottoToCheck.getFormatoDatamatrix() != null && !prodottoToCheck.getFormatoDatamatrix().isEmpty()) {
            	boolean isFormatoCorretto = Pattern.matches(prodottoToCheck.getFormatoDatamatrix(), codiceDataMatrix);
            	if(isFormatoCorretto) {
            		return prodottoToCheck;
            	}
        	}
		}
        if(throwExceptionSeFormatoNonEsiste) { throw new Exception("Formato datamatrix NON corretto"); }
        return null;
	}
}
