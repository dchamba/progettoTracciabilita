package com.vaadin.demo.dashboard.data.repository;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.vaadin.demo.dashboard.component.utils.CommonUtils;
import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnection;
import com.vaadin.demo.dashboard.data.model.Config;
import com.vaadin.demo.dashboard.data.model.CriteriBloccoDatamatrix;
import com.vaadin.demo.dashboard.data.model.Datamatrix;
import com.vaadin.demo.dashboard.data.repository.campi.CampiDatamatrix;

@SuppressWarnings("unchecked")
public class RepositoryCriteriBloccoDatamatrix {
	
	public RepositoryCriteriBloccoDatamatrix() { }
	
	public CriteriBloccoDatamatrix getCriteriBloccoDatamatrix(Datamatrix datamatrix, boolean isCurrentViewPackingList) throws ParseException {
        System.out.println("Reading Config");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();

        List<CriteriBloccoDatamatrix> result = new ArrayList<CriteriBloccoDatamatrix>();
		Criteria criteria = session.createCriteria(CriteriBloccoDatamatrix.class);
		
		criteria.add(Restrictions.eq("prodotto.idProdotto", datamatrix.getProddoto().getIdProdotto()));
		criteria.add(Restrictions.eq("attivo", true));
		
		result = criteria.list();
        session.close();

        if(result != null && result.size() > 0) {
        	//controllo data
        	//controllo progressivo
        	
        	String stringaDatamtrix = datamatrix.getDataMatrix();
        	
        	int indexInizioData = datamatrix.getProddoto().getIndexCarattereInizioData();
        	int indexFineData = indexInizioData + datamatrix.getProddoto().getFormatoData().length();
        	String dataString = stringaDatamtrix.substring(indexInizioData, indexFineData);
        	Date dataInDatamatrix = CommonUtils.toDate(datamatrix.getProddoto().getFormatoData(), dataString);
        	
        	int indexInizioProgressivo = datamatrix.getProddoto().getIndexCarattereInizioProgressivo();
        	int indexFineProgressivo = indexInizioProgressivo + datamatrix.getProddoto().getFormatoProgressivo().length();
        	String progressivoString = stringaDatamtrix.substring(indexInizioProgressivo, indexFineProgressivo);
        	int progressivo = Integer.parseInt(progressivoString);
        	
        	boolean dataDentroRange = false, progressivoDentroRange = false,
        			controlloDataRange = false, controlloProgressivoRange = false;
        	for (CriteriBloccoDatamatrix criterioBloccoDatamatrix : result) {
            	dataDentroRange = false; 
            	progressivoDentroRange = false;
            	controlloDataRange = false;
            	controlloProgressivoRange = false;
            	
        		if(criterioBloccoDatamatrix.getApplicaSoloAPackingList() && !isCurrentViewPackingList) {
        			continue;
        		}
        		    			
    			if(criterioBloccoDatamatrix.getDaData() != null && criterioBloccoDatamatrix.getaData() != null) {
    				dataDentroRange = dataInDatamatrix.after(criterioBloccoDatamatrix.getDaData()) && dataInDatamatrix.before(criterioBloccoDatamatrix.getaData());
    				dataDentroRange |=  dataInDatamatrix.equals(criterioBloccoDatamatrix.getDaData()) || dataInDatamatrix.equals(criterioBloccoDatamatrix.getaData());
    				controlloDataRange = true;
    			}
    			if(criterioBloccoDatamatrix.getDaProgressivo() != null && criterioBloccoDatamatrix.getaProgressivo() != null) {
    				progressivoDentroRange = progressivo >= criterioBloccoDatamatrix.getDaProgressivo() && progressivo <= criterioBloccoDatamatrix.getaProgressivo();
    				controlloProgressivoRange = true;
    			}
    			
    			//se sono compialte sia Range del Progressivo che Date li applico insieme
    			//altrimenti li applico singolarmente
    			if(controlloDataRange && controlloProgressivoRange) {
    				if(dataDentroRange && progressivoDentroRange) return criterioBloccoDatamatrix;
    				else continue;
    			} 
				if(controlloDataRange && dataDentroRange) { return criterioBloccoDatamatrix; }
    			if(controlloProgressivoRange && progressivoDentroRange) { return criterioBloccoDatamatrix; }
			}
        }
    	
    	return null;
	}
}
