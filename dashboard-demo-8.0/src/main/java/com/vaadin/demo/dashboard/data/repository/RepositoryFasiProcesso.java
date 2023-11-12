package com.vaadin.demo.dashboard.data.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnection;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiProcesso;
import com.vaadin.demo.dashboard.data.model.FasiProcesso;
import com.vaadin.demo.dashboard.data.model.FasiProcessoProdotto;
import com.vaadin.demo.dashboard.data.model.Prodotti;
import com.vaadin.demo.dashboard.data.model.Utenti;
import com.vaadin.demo.dashboard.data.repository.campi.CampiDatamatrixFasiProcesso;
import com.vaadin.demo.dashboard.data.repository.campi.CampiFasiProcesso;
import com.vaadin.demo.dashboard.data.repository.campi.CampiFasiProcessoProdotto;

@SuppressWarnings("unchecked")
public class RepositoryFasiProcesso {
	
	public RepositoryFasiProcesso() { }
	
	public boolean getFaseProcessoPerDatamtarix(int idDatamatrix, int idFaseProcesso) {
        System.out.println("Reading Fasi processo prodotto");
        
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(DatamatrixFasiProcesso.class);
		criteria.add(Restrictions.eq(CampiDatamatrixFasiProcesso.faseProcesso_idFaseProcesso, idFaseProcesso));
		criteria.add(Restrictions.eq(CampiDatamatrixFasiProcesso.dataMatrix_idDataMatrix, idDatamatrix));
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
        List<FasiProcessoProdotto> fasiProcessoProdotto = criteria.list();		
        session.close();
        return fasiProcessoProdotto != null && !fasiProcessoProdotto.isEmpty();
	}
	
	public FasiProcesso getFaseProcessoPerCodice(String codiceFase) {
        System.out.println("Reading Fasi processo");
        
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(FasiProcesso.class);
		criteria.add(Restrictions.ilike(CampiFasiProcesso.codiceFase, codiceFase, MatchMode.EXACT));
		
		FasiProcesso fase = null;
        List<FasiProcesso> fasiProcesso = criteria.list();
        if (fasiProcesso != null && !fasiProcesso.isEmpty()) {
        	fase = fasiProcesso.get(0);
        }
		
        session.close();
        return fase;
	}
	
	public List<FasiProcesso> getFasiProcesso() {
        System.out.println("Reading utenti");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();

        List<FasiProcesso> fasiProcesso = new ArrayList<FasiProcesso>();
		Criteria criteria = session.createCriteria(FasiProcesso.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
		criteria.add(RepositoryUtils.getCriteraAttivo());
				
        fasiProcesso = criteria.list();

        session.close();
        return fasiProcesso;
	}
}
