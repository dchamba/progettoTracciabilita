package com.vaadin.demo.dashboard.data.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnection;
import com.vaadin.demo.dashboard.data.model.LottiFusioneAssegnazioneStampi;
import com.vaadin.demo.dashboard.data.model.VistaLottiFusioneAssegnazioneStampi;

@SuppressWarnings("unchecked")
public class RepositoryLottiFusioneAssegnazioneStampi {
	
	public RepositoryLottiFusioneAssegnazioneStampi() { }
	

	public List<VistaLottiFusioneAssegnazioneStampi> getLottiFusioneAssegnazioneStampi(Integer idProdotto, Date daData, Date aData, 
			Integer daProgresivo, Integer aProgresivo) {
        System.out.println("Reading LottiFusioneAssegnazioneStampi");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		
		Criteria criteria = session.createCriteria(VistaLottiFusioneAssegnazioneStampi.class, "vista");
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
		criteria.addOrder(Order.desc("aData"));

		if (idProdotto != null) {
			criteria.add(Restrictions.eq("idProdotto", idProdotto));
		}
        //(daData <= FINE AND aData >= INIZIO)
        if (daData != null) {
        	criteria.add(Restrictions.ge("aData", daData));
        }
        if (aData != null) {
        	criteria.add(Restrictions.le("daData", aData));
		}
        if (daProgresivo != null) {
        	criteria.add(Restrictions.ge("aProgresivo", daProgresivo));
        }
        if (aProgresivo != null) {
        	criteria.add(Restrictions.le("daProgresivo", aProgresivo));
		}
		
		List<VistaLottiFusioneAssegnazioneStampi> lista = criteria.list();
		
        session.close();
        return lista;
	}
	
	public boolean verificaCorrettezzaDateEProgressivo(LottiFusioneAssegnazioneStampi lottiFusioneAssegnazioneStampi) {
        System.out.println("VerificaCorrettezzaDateEProgressivo LottiFusioneAssegnazioneStampi");
//        
//		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
//		Transaction saveTransaction = session.beginTransaction();

		List<VistaLottiFusioneAssegnazioneStampi> lista = getLottiFusioneAssegnazioneStampi(lottiFusioneAssegnazioneStampi.getProdotto().getIdProdotto(),
																lottiFusioneAssegnazioneStampi.getDaData(),
																lottiFusioneAssegnazioneStampi.getaData(),
																lottiFusioneAssegnazioneStampi.getDaProgressivo(),
																lottiFusioneAssegnazioneStampi.getaProgressivo());
		
//		Integer idLottiFusioneAssegnazioneStampi = (Integer) session.save(lottiFusioneAssegnazioneStampi); 
//      saveTransaction.commit();
//      session.close();
  
		if(lista != null & lista.size() > 0) 
			return false;
		else 
			return true;
	}

	public LottiFusioneAssegnazioneStampi salvaLottiFusioneAssegnazioneStampi(LottiFusioneAssegnazioneStampi lottiFusioneAssegnazioneStampi) {
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		Transaction transaction =  session.beginTransaction();
		
		session.saveOrUpdate(lottiFusioneAssegnazioneStampi);
		
		transaction.commit();
		
        session.close();
        
        return lottiFusioneAssegnazioneStampi;
	}
	
	public Integer save(LottiFusioneAssegnazioneStampi lottiFusioneAssegnazioneStampi) {
        System.out.println("Ssalvataggio LottiFusioneAssegnazioneStampi");
        
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		Transaction saveTransaction = session.beginTransaction();

		Integer idLottiFusioneAssegnazioneStampi = (Integer) session.save(lottiFusioneAssegnazioneStampi); 
		
        saveTransaction.commit();
        session.close();
        
        return idLottiFusioneAssegnazioneStampi;
	}
}
