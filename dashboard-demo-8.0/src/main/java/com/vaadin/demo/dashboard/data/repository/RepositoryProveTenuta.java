package com.vaadin.demo.dashboard.data.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.vaadin.demo.dashboard.component.utils.CommonUtils;
import com.vaadin.demo.dashboard.component.utils.ProveTenutaUtils;
import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnectionDatamatrix_Syncro;
import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnectionl7002619;
import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnectionl7003820;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiEseguite;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiProcesso;
import com.vaadin.demo.dashboard.data.model.DatiProvaTenutaElio;

@SuppressWarnings("unchecked")
public class RepositoryProveTenuta {
	
	public RepositoryProveTenuta() { }
	  
	public String verificaEsitoProvaTenutaAria(String dataMatrix) {
		String esitoProvaTenutaAria7002619 = verificaEsitoProvaTenutaAria(caricaEsitoProvaTenutaAria7002619(dataMatrix));
		String esitoProvaTenutaAria7003820 = verificaEsitoProvaTenutaAria(caricaEsitoProvaTenutaAria7003820(dataMatrix));
		
		if(esitoProvaTenutaAria7002619.equals(ProveTenutaUtils.PROVATO_OK) || esitoProvaTenutaAria7003820.equals(ProveTenutaUtils.PROVATO_OK)) {
			return ProveTenutaUtils.PROVATO_OK;
		} else if(esitoProvaTenutaAria7002619.equals(ProveTenutaUtils.NON_PROVATO_PT) && esitoProvaTenutaAria7003820.equals(ProveTenutaUtils.NON_PROVATO_PT)) {
			return ProveTenutaUtils.NON_PROVATO_PT;
		} else {
			return ProveTenutaUtils.PROVATO_KO;
		}
	}
	
	public String verificaEsitoProvaTenutaAria(List<Object[]> esitiTenuta) {
		if(esitiTenuta == null || esitiTenuta.size() == 0) return ProveTenutaUtils.NON_PROVATO_PT;
		else {
			for(Object[] oggettoEsitoTenuta : esitiTenuta) {
				if(oggettoEsitoTenuta[4].equals("GOOD")) {
					return ProveTenutaUtils.PROVATO_OK;
				}
			}
			return ProveTenutaUtils.PROVATO_KO;
		}
	}
	
	public List<Object[]> caricaEsitoProvaTenutaAria7002619(String dataMatrix) {
		System.out.println("Reading prove tenuta 7002619");

		Session sessionl7002619 = DatabaseHibernateConnectionl7002619.getSessionFactory().openSession();
		sessionl7002619.beginTransaction();
		
		List<Object[]> esitiTenuta = null;
		List<Object> list = sessionl7002619.createSQLQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = 'l7002619'").list(); 

		if(list != null && list.size() > 0) {
			String queryProvaTenuta = "";
			
			for(Object nomeTabella : list) {
				if(!queryProvaTenuta.isEmpty()) { queryProvaTenuta += " UNION "; }
				
				queryProvaTenuta += " SELECT Id, Data, Ora, DMC, Operatore, EsitoTOT, PerdPT1 "
						+ " FROM " + nomeTabella + " "
						+ " WHERE DMC LIKE '" + dataMatrix + "' ";	
				//+ " WHERE EsitoTOT LIKE 'GOOD' AND DMC LIKE '" + dataMatrix + "' ";	
			}
			queryProvaTenuta += " ORDER BY Id ASC ";
			
			SQLQuery query = sessionl7002619.createSQLQuery(queryProvaTenuta);
			esitiTenuta = query.list();
		}
		
		
		sessionl7002619.close();
		
        return esitiTenuta;
	}
  
	public List<Object[]> caricaEsitoProvaTenutaAria7003820(String dataMatrix) {
		System.out.println("Reading prove tenuta 7003820");

		Session sessionl7003820 = DatabaseHibernateConnectionl7003820.getSessionFactory().openSession();
		sessionl7003820.beginTransaction();
		
		List<Object[]> esitiTenuta = null;
		List<Object> list = sessionl7003820.createSQLQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = 'l7003820'").list(); 

		if(list != null && list.size() > 0) {
			String queryProvaTenuta = "";
			
			for(Object nomeTabella : list) {
				if(!queryProvaTenuta.isEmpty()) { queryProvaTenuta += " UNION "; }
				
				queryProvaTenuta += " SELECT Id, Data, Ora, DMC, Operatore, EsitoTOT, PerdPT1 "
						+ " FROM " + nomeTabella + " "
						+ " WHERE DMC LIKE '" + dataMatrix + "' ";
					//+ " WHERE EsitoTOT LIKE 'GOOD' AND DMC LIKE '" + dataMatrix + "' ";
			}
			queryProvaTenuta += " ORDER BY Id ASC ";
			
			SQLQuery query = sessionl7003820.createSQLQuery(queryProvaTenuta);
			esitiTenuta = query.list();
		}
		
		
		sessionl7003820.close();
		
        return esitiTenuta;
	}
	  
	public String verificaEsitoProvaTenutaElio(String dataMatrix) {
        List<DatiProvaTenutaElio> listaProvetenuta = caricaEsitoProvaTenutaElio(dataMatrix);
        String esitoTenutaElio = "";
        
        if(listaProvetenuta == null || listaProvetenuta.size() == 0) {
        	esitoTenutaElio = ProveTenutaUtils.NON_PROVATO_PT;
        } else {

			for(DatiProvaTenutaElio rigaEsitoTenutaElio : listaProvetenuta) {
				if(rigaEsitoTenutaElio.getEsito().equals("BUONO")) {
					esitoTenutaElio = ProveTenutaUtils.PROVATO_OK;
				}
			}
			if(esitoTenutaElio.isEmpty()) {
				esitoTenutaElio = ProveTenutaUtils.PROVATO_KO;
			}
        }
        return esitoTenutaElio;
	}
	
	public List<DatiProvaTenutaElio> caricaEsitoProvaTenutaElio(String dataMatrix) {
		System.out.println("Reading prove tenuta");

		Session session = DatabaseHibernateConnectionDatamatrix_Syncro.getSessionFactory().openSession();
		session.beginTransaction();

        List<DatiProvaTenutaElio> listaProvetenuta = new ArrayList<DatiProvaTenutaElio>();
		Criteria criteria = session.createCriteria(DatiProvaTenutaElio.class);
		criteria.add(Restrictions.like("Datamatrix", dataMatrix));
		//criteria.add(Restrictions.like("Esito", "BUONO")); 
		criteria.addOrder(Order.desc("Id"));
		
		listaProvetenuta = criteria.list();
        session.close();
        
        return listaProvetenuta;
	}

	public List<DatamatrixFasiEseguite> getDatamatrixProveTenutaEseguite(String datamatrix) throws ParseException {
		List<DatamatrixFasiEseguite> listaFasiEseguite = new ArrayList<DatamatrixFasiEseguite>();
		
		List<Object[]> listaEsitiTenuta7002619 = this.caricaEsitoProvaTenutaAria7002619(datamatrix);
		List<Object[]> listaEsitiTenuta7003820 = this.caricaEsitoProvaTenutaAria7003820(datamatrix);
        List<DatiProvaTenutaElio> listaEsitiTenutaElio = caricaEsitoProvaTenutaElio(datamatrix);
		
        Map<String, List<Object[]>> esitiProvaTenutaAria = new HashMap<String, List<Object[]>>();
        esitiProvaTenutaAria.put("Leonardo 7002619", listaEsitiTenuta7002619);
        esitiProvaTenutaAria.put("Leonardo 7003820", listaEsitiTenuta7003820);
        
		for (Map.Entry<String, List<Object[]>> entry : esitiProvaTenutaAria.entrySet()) {
		    String nomeImpianto = entry.getKey();
		    List<Object[]> listaEsitiTenuta700xxxx = entry.getValue();

			for (Object[] esutoTenuta : listaEsitiTenuta700xxxx) {
				DatamatrixFasiEseguite dme = new DatamatrixFasiEseguite();
				dme.setDataMatrix(datamatrix);

				// SELECT Id, Data, Ora, DMC, Operatore, EsitoTOT, PerdPT1 
				
	    	    SimpleDateFormat formatter = new SimpleDateFormat(CommonUtils.DATETIMEFORMAT.toString());
				dme.setDataOra(formatter.parse(esutoTenuta[1].toString() + " " + esutoTenuta[2].toString()));
				
				dme.setUtente(esutoTenuta[4].toString());
				dme.setEsito(esutoTenuta[5].toString());
				
				String doubleEsito = CommonUtils.toDouble(esutoTenuta[6].toString().replace(".", ",")).toString();
				dme.setEsitoValore(doubleEsito.toString());
				
				dme.setImpianto(nomeImpianto);
				dme.setFaseProcesso("Prova tenuta aria");
				
				listaFasiEseguite.add(dme);
			}
		}
        
		for(DatiProvaTenutaElio esitoTenutaElio : listaEsitiTenutaElio) {
			DatamatrixFasiEseguite dme = new DatamatrixFasiEseguite();
			dme.setDataMatrix(datamatrix);

			// SELECT , , , , , EsitoTOT, PerdPT1 
			
    	    SimpleDateFormat formatter = new SimpleDateFormat(CommonUtils.DATETIMEFORMAT.toString());
			dme.setDataOra(formatter.parse(esitoTenutaElio.getData() + " " + esitoTenutaElio.getOra()));
			
			dme.setUtente(esitoTenutaElio.getOperatore());
			dme.setEsito(esitoTenutaElio.getEsito());
			
			String doubleEsito = CommonUtils.toDouble(esitoTenutaElio.getPerdita().replace(".", ",")).toString();
			dme.setEsitoValore(doubleEsito.toString());
			
			dme.setImpianto("Leonardo 7003920");
			dme.setFaseProcesso("Prova tenuta elio");
			
			listaFasiEseguite.add(dme);
		}
		
		return listaFasiEseguite;
	}
}
