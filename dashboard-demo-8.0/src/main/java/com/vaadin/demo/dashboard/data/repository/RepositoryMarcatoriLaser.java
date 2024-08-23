package com.vaadin.demo.dashboard.data.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnectionDatamatrix_Syncro;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiEseguite;
import com.vaadin.demo.dashboard.data.model.LaserIsolaFisep_Log;
import com.vaadin.demo.dashboard.data.model.LaserMaicoFisep_Log;

@SuppressWarnings("unchecked")
public class RepositoryMarcatoriLaser {
	
	public RepositoryMarcatoriLaser() { }
	
	public LaserIsolaFisep_Log caricaDatiLaserIsolaFisep_Log(String dataMatrixs) {
        System.out.println("Reading tipo LaserIsolaFisep_Log");

		Session session = DatabaseHibernateConnectionDatamatrix_Syncro.getSessionFactory().openSession();
		session.beginTransaction();

        List<LaserIsolaFisep_Log> dati = new ArrayList<LaserIsolaFisep_Log>();
		Criteria criteria = session.createCriteria(LaserIsolaFisep_Log.class);
		criteria.addOrder(Order.desc("Id"));
		
		if (dataMatrixs != null) {
			criteria.add(Restrictions.ilike("MarkingContent", dataMatrixs, MatchMode.ANYWHERE));
		} else {
	        session.close();
			return null;
		}
		
		dati = criteria.list();

        session.close();
        return (dati != null && dati.size() > 0) ? dati.get(0) : null;
	}

	public LaserMaicoFisep_Log caricaDatiLaserMaicoFisep_Log(String dataMatrixs) {
        System.out.println("Reading tipo LaserMaicoFisep_Log");

		Session session = DatabaseHibernateConnectionDatamatrix_Syncro.getSessionFactory().openSession();
		session.beginTransaction();

        List<LaserMaicoFisep_Log> dati = new ArrayList<LaserMaicoFisep_Log>();
		Criteria criteria = session.createCriteria(LaserMaicoFisep_Log.class);
		criteria.addOrder(Order.desc("Id"));
		
		if (dataMatrixs != null) {
			criteria.add(Restrictions.ilike("MarkingContent", dataMatrixs, MatchMode.ANYWHERE));
		} else {
	        session.close();
			return null;
		}
		
		dati = criteria.list();

        session.close();
        return (dati != null && dati.size() > 0) ? dati.get(0) : null;
	}
	
	public List<DatamatrixFasiEseguite> getDatamatrixFasiEseguiteMarcatureLaser(String codiceDatamatrix) {
		List<DatamatrixFasiEseguite> listaFasiEseguite = new ArrayList<DatamatrixFasiEseguite>();
		
		LaserIsolaFisep_Log marcaturaItalpresse = caricaDatiLaserIsolaFisep_Log(codiceDatamatrix);
		LaserMaicoFisep_Log marcaturaMaico = caricaDatiLaserMaicoFisep_Log(codiceDatamatrix);
		
		if(marcaturaItalpresse != null || marcaturaMaico != null) {
			DatamatrixFasiEseguite faseEseguita = new DatamatrixFasiEseguite();
			faseEseguita.setDataMatrix(codiceDatamatrix);
			faseEseguita.setFaseProcesso("Laser");
			
			if(marcaturaItalpresse != null) {
				faseEseguita.setDataOra(marcaturaItalpresse.getDateHours());
				
				faseEseguita.setEsito(marcaturaItalpresse.getMarkingResult());
				faseEseguita.setEsitoValore("Grado: " + marcaturaItalpresse.getCodGrade());
				
				faseEseguita.setImpianto("Italpresse 1450t");
				faseEseguita.setNote("Tempo ciclo:" + marcaturaItalpresse.getCycleTime().toString() + "s");
			} else if(marcaturaMaico != null) {
				faseEseguita.setDataOra(marcaturaMaico.getDateHours());
				
				faseEseguita.setEsito(marcaturaMaico.getMarkingResult());
				faseEseguita.setEsitoValore("Grado: " + marcaturaMaico.getCodGrade());
				
				faseEseguita.setImpianto("Marico 1100t");
				faseEseguita.setNote("Tempo ciclo:" + marcaturaMaico.getCycleTime().toString() + "s");
			}
			
			listaFasiEseguite.add(faseEseguita);
		}
		return listaFasiEseguite;
	}
}
