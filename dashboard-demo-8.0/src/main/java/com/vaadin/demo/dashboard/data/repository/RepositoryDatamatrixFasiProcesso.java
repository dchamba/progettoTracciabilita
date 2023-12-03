package com.vaadin.demo.dashboard.data.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnection;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiEseguite;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiProcesso;
import com.vaadin.demo.dashboard.data.repository.campi.CampiDatamatrixFasiProcesso;

@SuppressWarnings("unchecked")
public class RepositoryDatamatrixFasiProcesso {
	
	public RepositoryDatamatrixFasiProcesso() { }

	public DatamatrixFasiProcesso getUltimoFaseProcesso(Integer idUtente) {
        DatamatrixFasiProcesso dataMatrixFaseProcesso = null;
        
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(DatamatrixFasiProcesso.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
		criteria.setMaxResults(1);
	    criteria.addOrder(Order.desc(CampiDatamatrixFasiProcesso.idDatamatrixFaseProcesso));
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
		if (idUtente != null && idUtente > 0) {
			criteria.add(Restrictions.eq(CampiDatamatrixFasiProcesso.idUtente, idUtente));
		}
		
		if (criteria.list() != null && criteria.list().size() > 0) {
			dataMatrixFaseProcesso = (DatamatrixFasiProcesso)criteria.list().get(0);
		}
		
        session.close();
        return dataMatrixFaseProcesso;
	}

	public List<DatamatrixFasiProcesso> getListaDatamatrixFasiProcesso(Integer idDataMatrix) {
		List<DatamatrixFasiProcesso> dataMatrixFaseProcesso = null;
        
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(DatamatrixFasiProcesso.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
	    criteria.addOrder(Order.desc(CampiDatamatrixFasiProcesso.idDatamatrixFaseProcesso));
		criteria.add(Restrictions.eq(CampiDatamatrixFasiProcesso.dataMatrix_idDataMatrix, idDataMatrix));
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
		if (criteria.list() != null && criteria.list().size() > 0) {
			dataMatrixFaseProcesso = criteria.list();
		}
		
        session.close();
        return dataMatrixFaseProcesso;
	}
	
	public DatamatrixFasiProcesso getDatamatrixFasiProcesso(Integer idDatamatrix, Integer idFaseProcesso) {
		DatamatrixFasiProcesso result = null;
        
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(DatamatrixFasiProcesso.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
		criteria.add(Restrictions.eq(CampiDatamatrixFasiProcesso.dataMatrix_idDataMatrix, idDatamatrix));
		criteria.add(Restrictions.eq(CampiDatamatrixFasiProcesso.faseProcesso_idFaseProcesso, idFaseProcesso));
		
		List<DatamatrixFasiProcesso> datamatrixFasiProcesso = criteria.list();
		if (datamatrixFasiProcesso != null && datamatrixFasiProcesso.size() > 0) {
			result = datamatrixFasiProcesso.get(0);
		}
		
        session.close();
        return result;
	}

	public DatamatrixFasiProcesso getByIdDatamatrixFasiProcesso(Integer idDatamatrixFaseProcesso) {
		DatamatrixFasiProcesso result = null;
        
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(DatamatrixFasiProcesso.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
		criteria.add(Restrictions.eq(CampiDatamatrixFasiProcesso.idDatamatrixFaseProcesso, idDatamatrixFaseProcesso));
		
		List<DatamatrixFasiProcesso> datamatrixFasiProcesso = criteria.list();
		if (datamatrixFasiProcesso != null && datamatrixFasiProcesso.size() > 0) {
			result = datamatrixFasiProcesso.get(0);
		}
		
        session.close();
        return result;
	}
	
	public DatamatrixFasiProcesso salvaFaseProcesso(DatamatrixFasiProcesso trattamento) {
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		Transaction transaction =  session.beginTransaction();
		
		session.saveOrUpdate(trattamento);
		
		transaction.commit();
		
        session.close();
        
        return trattamento;
	}
	
	public Integer save(DatamatrixFasiProcesso trattamento) {
        System.out.println("salvataggio trattamento");
        
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		Transaction saveTransaction = session.beginTransaction();

		Integer idTrattamento = (Integer) session.save(trattamento); 
		
        saveTransaction.commit();
        session.close();
        
        return idTrattamento;
	}
}
