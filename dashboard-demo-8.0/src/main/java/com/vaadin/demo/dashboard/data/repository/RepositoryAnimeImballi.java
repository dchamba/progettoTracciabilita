package com.vaadin.demo.dashboard.data.repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnection;
import com.vaadin.demo.dashboard.data.model.AnimeImballi;
import com.vaadin.demo.dashboard.data.model.Datamatrix;
import com.vaadin.demo.dashboard.data.repository.campi.CampiAnimeImballi;
import com.vaadin.demo.dashboard.data.repository.campi.CampiFasiProcessoProdotto;

@SuppressWarnings("unchecked")
public class RepositoryAnimeImballi {
	
	public RepositoryAnimeImballi() { }

	public List<AnimeImballi> getAnimeImballi() {
        System.out.println("Reading tipo imballi");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();

        List<AnimeImballi> AnimeImballi = new ArrayList<AnimeImballi>();
		Criteria criteria = session.createCriteria(AnimeImballi.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
		criteria.addOrder(Order.asc("dataScadenza"));
				
        AnimeImballi = criteria.list();

        session.close();
        return AnimeImballi;
	}
	
	public List<AnimeImballi> getAnimeImballiNonScadute(Date dataScadenzaRicercata) {
        System.out.println("Reading tipo imballi");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();

        List<AnimeImballi> AnimeImballi = new ArrayList<AnimeImballi>();
		Criteria criteria = session.createCriteria(AnimeImballi.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
			
		//Calendar c = Calendar.getInstance();
		//c.getTime()
		criteria.add(Restrictions.gt("dataScadenza", new Date()));
		if(dataScadenzaRicercata != null ) {
			criteria.add(Restrictions.lt("dataScadenza", dataScadenzaRicercata));
		}
        AnimeImballi = criteria.list();

        session.close();
        return AnimeImballi;
	}
	
	public List<AnimeImballi> getAnimeImballiByIdProdotto(Integer idProdotto) {
        System.out.println("Reading tipo animeImballi");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();

        List<AnimeImballi> AnimeImballi = new ArrayList<AnimeImballi>();
		Criteria criteria = session.createCriteria(AnimeImballi.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
		if (idProdotto != null) {
			criteria.add(Restrictions.eq(CampiAnimeImballi.idProdotto, idProdotto));
		} else {
	        session.close();
			return null;
		}
		
        AnimeImballi = criteria.list();

        session.close();
        return AnimeImballi;
	}
	
	public AnimeImballi getAnimeImballiByIdAnimaImballo(Integer idAnimaImballo) {
        System.out.println("Reading tipo animeImballi");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();

        List<AnimeImballi> animeImballiCaricati = new ArrayList<AnimeImballi>();
		Criteria criteria = session.createCriteria(AnimeImballi.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
		if (idAnimaImballo != null) {
			criteria.add(Restrictions.eq(CampiAnimeImballi.idImballoAnime, idAnimaImballo));
		} else {
	        session.close();
			return null;
		}
		
        animeImballiCaricati = criteria.list();

        session.close();
        return (animeImballiCaricati != null && animeImballiCaricati.size() > 0) ? animeImballiCaricati.get(0) : null;
	}
	
	public AnimeImballi getAnimeImballiByQrCode(String qrCode) {
        System.out.println("Reading tipo animeImballi");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();

        List<AnimeImballi> animeImballiCaricati = new ArrayList<AnimeImballi>();
		Criteria criteria = session.createCriteria(AnimeImballi.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
		if (qrCode != null) {
			criteria.add(Restrictions.eq(CampiAnimeImballi.qrcode, qrCode));
		} else {
	        session.close();
			return null;
		}
		
        animeImballiCaricati = criteria.list();

        session.close();
        return (animeImballiCaricati != null && animeImballiCaricati.size() > 0) ? animeImballiCaricati.get(0) : null;
	}

	public AnimeImballi salva(AnimeImballi imballoAnime) {
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		Transaction transaction =  session.beginTransaction();
		
		session.saveOrUpdate(imballoAnime);
		
		transaction.commit();
		
        session.close();
        
        return imballoAnime;
	}
}
