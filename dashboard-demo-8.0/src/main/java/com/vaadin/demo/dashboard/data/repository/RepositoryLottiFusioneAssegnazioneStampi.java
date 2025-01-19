package com.vaadin.demo.dashboard.data.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnection;
import com.vaadin.demo.dashboard.data.model.VistaLottiFusioneAssegnazioneStampi;

@SuppressWarnings("unchecked")
public class RepositoryLottiFusioneAssegnazioneStampi {
	
	public RepositoryLottiFusioneAssegnazioneStampi() { }
	

	public List<VistaLottiFusioneAssegnazioneStampi> getLottiFusioneAssegnazioneStampi(Integer idProdotto, Date daData, Date aData, 
			Integer daProgresivo, Integer aProgresivo) {
        System.out.println("Reading LottiFusioneAssegnazioneStampi");

        List<VistaLottiFusioneAssegnazioneStampi> vistaLottiFusioneAssegnazioneStampi = new ArrayList<VistaLottiFusioneAssegnazioneStampi>();
        
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
//        if (daProgresivo != null) {
//            criteria = cb.and(criteria, cb.greaterThanOrEqualTo(root.get("daProgressivo"), daProgresivo));
//        }
//        if (aProgresivo != null) {
//            criteria = cb.and(criteria, cb.lessThanOrEqualTo(root.get("aProgressivo"), aProgresivo));
//        }
		
		List<VistaLottiFusioneAssegnazioneStampi> lista = criteria.list();
		
        session.close();
        return lista;
	}
}
