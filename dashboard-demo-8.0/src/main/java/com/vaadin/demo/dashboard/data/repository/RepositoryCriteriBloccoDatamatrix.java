package com.vaadin.demo.dashboard.data.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnection;
import com.vaadin.demo.dashboard.data.model.Config;
import com.vaadin.demo.dashboard.data.model.CriteriBloccoDatamatrix;
import com.vaadin.demo.dashboard.data.model.Datamatrix;
import com.vaadin.demo.dashboard.data.repository.campi.CampiDatamatrix;

@SuppressWarnings("unchecked")
public class RepositoryCriteriBloccoDatamatrix {
	
	public RepositoryCriteriBloccoDatamatrix() { }
	
	public CriteriBloccoDatamatrix getCriteriBloccoDatamatrix(Datamatrix datamatrix) {
        System.out.println("Reading Config");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();

        List<CriteriBloccoDatamatrix> result = new ArrayList<CriteriBloccoDatamatrix>();
		Criteria criteria = session.createCriteria(Config.class);
		
		criteria.add(Restrictions.eq(CampiDatamatrix.idProdotto, datamatrix.getProddoto().getIdProdotto()));
		criteria.add(Restrictions.eq("attivo", true));
		
		result = criteria.list();
        session.close();

        if(result == null || result.size() == 0) { return null; } 
        else {
        	//controllo data
        	//controllo progressivo
        	
        	String stringaDatamtrix = datamatrix.getDataMatrix();
        	String data = stringaDatamtrix.substring(5 ,6);
        	String progressivo = stringaDatamtrix.substring(stringaDatamtrix.length() - 4);
        			
        			
        	for (CriteriBloccoDatamatrix criterioBloccoDatamatrix : result) {
				
			}
        	
        	return criterio;
        }
	}
}
