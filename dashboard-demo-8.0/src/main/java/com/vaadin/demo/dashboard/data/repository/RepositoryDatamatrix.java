package com.vaadin.demo.dashboard.data.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.vaadin.addon.pagination.PaginationResource;
import com.vaadin.demo.dashboard.component.model.FilterDatamatrix;
import com.vaadin.demo.dashboard.component.model.GridResult;
import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnection;
import com.vaadin.demo.dashboard.data.model.Datamatrix;
import com.vaadin.demo.dashboard.data.repository.campi.CampiDatamatrix;
import com.vaadin.demo.dashboard.data.repository.campi.CampiDatamatrixFasiProcesso;
import com.vaadin.demo.dashboard.data.repository.campi.CampiProdotti;

@SuppressWarnings("unchecked")
public class RepositoryDatamatrix {
	
	public RepositoryDatamatrix() { }
	
	public GridResult<Datamatrix> getDataMatrix(FilterDatamatrix filterDatamatrix, PaginationResource pagination) {
        System.out.println("Reading datamatrix");

        List<Datamatrix> dataMatrix = new ArrayList<Datamatrix>();
        
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		
		Criteria criteria = session.createCriteria(Datamatrix.class, "dm");
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
		criteria.addOrder(Order.desc(CampiDatamatrix.idDataMatrix));
		
		if(filterDatamatrix != null) {
			if (filterDatamatrix.getDatamatrix() != null && !filterDatamatrix.getDatamatrix().isEmpty()) {
				criteria.add(Restrictions.ilike(CampiDatamatrix.dataMatrix, filterDatamatrix.getDatamatrix(), MatchMode.ANYWHERE));
			}
			if (filterDatamatrix.getDataInizio() != null) {
				criteria.add(Restrictions.ge(CampiDatamatrix.dataOraCreazione, filterDatamatrix.getDataInizio()));
			}
			if (filterDatamatrix.getDataFine() != null) {
				criteria.add(Restrictions.le(CampiDatamatrix.dataOraCreazione, filterDatamatrix.getDataFine()));
			}
			if (filterDatamatrix.getIdAziendaUtente() != null) {
				criteria.createAlias("dm." +  CampiDatamatrix.trattamenti, "dmt");
				criteria.add(Restrictions.eq("dmt." + CampiDatamatrixFasiProcesso.azienda + "." + CampiDatamatrixFasiProcesso.idAzienda, filterDatamatrix.getIdAziendaUtente()));
			}
			if (filterDatamatrix.getCodiceProdotto() != null && !filterDatamatrix.getCodiceProdotto().isEmpty()) {
				criteria.createAlias("dm." +  CampiDatamatrix.prodotto, "p");
				criteria.add(Restrictions.ilike("p." + CampiProdotti.codiceProdotto, filterDatamatrix.getCodiceProdotto(), MatchMode.ANYWHERE));
			}
		}
		
		if (pagination != null) {
			criteria.setFirstResult(pagination.offset());
			criteria.setMaxResults(pagination.limit());
		}

		dataMatrix = criteria.list();
		
		criteria.setProjection(Projections.rowCount());
		Long rowCount = (Long)criteria.uniqueResult();
		
        session.close();
        return new GridResult<Datamatrix>(dataMatrix, rowCount);
	}

	public Datamatrix salva(Datamatrix dataMatrix) {
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		Transaction transaction =  session.beginTransaction();
		
		session.saveOrUpdate(dataMatrix);
		
		transaction.commit();
		
        session.close();
        
        return dataMatrix;
	}
}
