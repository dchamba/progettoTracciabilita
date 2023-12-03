package com.vaadin.demo.dashboard.data.repository;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnection;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiProcessoTT;
import com.vaadin.demo.dashboard.data.model.VistaDatamatrixFasiProcessoTT;
import com.vaadin.demo.dashboard.data.repository.campi.CampiDatamatrix;
import com.vaadin.demo.dashboard.data.repository.campi.CampiDatamatrixFasiProcessoTT;
import com.vaadin.demo.dashboard.data.repository.campi.CampiUtenti;

@SuppressWarnings("unchecked")
public class RepositoryDatamatrixFasiProcessoTT {
	
	public RepositoryDatamatrixFasiProcessoTT() { }

	public DatamatrixFasiProcessoTT getDatamatrixFasiProcessoTTById(Integer idDatamatrixFasiProcessoTT) {
        DatamatrixFasiProcessoTT dataMatrixFaseProcesso = null;
        
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(DatamatrixFasiProcessoTT.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
		criteria.setMaxResults(1);
		criteria.add(Restrictions.eq(CampiDatamatrixFasiProcessoTT.idDatamatrixFaseProcessoTT, idDatamatrixFasiProcessoTT));
		
		if (criteria.list() != null && criteria.list().size() > 0) {
			dataMatrixFaseProcesso = (DatamatrixFasiProcessoTT)criteria.list().get(0);
		}
		
        session.close();
        return dataMatrixFaseProcesso;
	}

	public DatamatrixFasiProcessoTT getDatamatrixFasiProcessoTTByIdFaseProcesso(Integer idDatamatrixFasiProcesso) {
        DatamatrixFasiProcessoTT dataMatrixFaseProcesso = null;
        
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(DatamatrixFasiProcessoTT.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
		criteria.setMaxResults(1);
		criteria.add(Restrictions.eq(CampiDatamatrixFasiProcessoTT.idDatamatrixFaseProcesso, idDatamatrixFasiProcesso));
		
		if (criteria.list() != null && criteria.list().size() > 0) {
			dataMatrixFaseProcesso = (DatamatrixFasiProcessoTT)criteria.list().get(0);
		}
		
        session.close();
        return dataMatrixFaseProcesso;
	}

	public List<VistaDatamatrixFasiProcessoTT> getVistaDatamatrixFasiProcessoTT(Integer numeroFornata, Integer idAzienda, String datamatrix) {
		List<VistaDatamatrixFasiProcessoTT> dataMatrixFaseProcessoTT = null;
		
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();

		String sqlQUery = "";
		
		if (numeroFornata != null) {
			sqlQUery += " numeroFornata LIKE '" + numeroFornata.toString() + "'";
		}
		if (idAzienda != null) {
			if(!sqlQUery.isEmpty()) { sqlQUery += " AND ";}
			sqlQUery += " idAzienda = " + idAzienda.toString();
		}
		if (datamatrix != null && !datamatrix.isEmpty()) {
			if(!sqlQUery.isEmpty()) { sqlQUery += " AND ";}
			sqlQUery = " dataMatrix LIKE  '" + datamatrix + "'";
		}
		
		dataMatrixFaseProcessoTT = session.createQuery(" FROM VistaDatamatrixFasiProcessoTT WHERE eliminato = 0 AND " + sqlQUery).list();
		
		session.close();
		return dataMatrixFaseProcessoTT;
	}

	public DatamatrixFasiProcessoTT salvaDatamatrixFasiProcessoTT(DatamatrixFasiProcessoTT trattamento) {
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		Transaction transaction =  session.beginTransaction();
		
		session.saveOrUpdate(trattamento);
		
		transaction.commit();
		
        session.close();
        
        return trattamento;
	}
	
	public Integer save(DatamatrixFasiProcessoTT trattamento) {
        System.out.println("salvataggio trattamento");
        
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		Transaction saveTransaction = session.beginTransaction();

		Integer idTrattamento = (Integer) session.save(trattamento); 
		
        saveTransaction.commit();
        session.close();
        
        return idTrattamento;
	}
}
