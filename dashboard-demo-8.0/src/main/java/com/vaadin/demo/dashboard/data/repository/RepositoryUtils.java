package com.vaadin.demo.dashboard.data.repository;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import com.vaadin.demo.dashboard.data.repository.campi.CampiDatamatrix;
import com.vaadin.demo.dashboard.data.repository.campi.CampiFasiProcesso;

public class RepositoryUtils {
		
	public static String verificaDoppioneDatamatrixPackingList = "verificaDoppioneDatamatrixPackingList";
	public static String verificaFasiProcessoPrecedentiDatamatrix = "verificaFasiProcessoPrecedentiDatamatrix";
	
	public static SimpleExpression getCriteraEliminato() {
		return Restrictions.eq(CampiDatamatrix.eliminato, false);
	}
	
	public static SimpleExpression getCriteraAttivo() {
		return Restrictions.eq(CampiFasiProcesso.attivo, true);
	}

}
