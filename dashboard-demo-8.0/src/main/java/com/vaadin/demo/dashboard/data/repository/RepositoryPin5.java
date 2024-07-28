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
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.vaadin.addon.pagination.PaginationResource;
import com.vaadin.demo.dashboard.component.model.FilterDatamatrix;
import com.vaadin.demo.dashboard.component.model.GridResult;
import com.vaadin.demo.dashboard.component.utils.CommonUtils;
import com.vaadin.demo.dashboard.component.utils.ProveTenutaUtils;
import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnection;
import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnectionDatamatrix_002;
import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnectionDatamatrix_Syncro;
import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnectionl7002619;
import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnectionl7003820;
import com.vaadin.demo.dashboard.data.model.AnimeImballi;
import com.vaadin.demo.dashboard.data.model.Datamatrix;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiEseguite;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiProcesso;
import com.vaadin.demo.dashboard.data.model.DatiProvaTenutaElio;
import com.vaadin.demo.dashboard.data.model.Pan5;
import com.vaadin.demo.dashboard.data.repository.campi.CampiAnimeImballi;
import com.vaadin.demo.dashboard.data.repository.campi.CampiDatamatrix;
import com.vaadin.demo.dashboard.data.repository.campi.CampiDatamatrixFasiProcesso;
import com.vaadin.demo.dashboard.data.repository.campi.CampiProdotti;

@SuppressWarnings("unchecked")
public class RepositoryPin5 {
	
	public RepositoryPin5() { }
	
	public Pan5 caricaAssemblaggioPinPan5(String dataMatrixs) {
        System.out.println("Reading tipo Pan5");

		Session session = DatabaseHibernateConnectionDatamatrix_002.getSessionFactory().openSession();
		session.beginTransaction();

        List<Pan5> Pam5PinCaricati = new ArrayList<Pan5>();
		Criteria criteria = session.createCriteria(Pan5.class);
		
		if (dataMatrixs != null) {
			criteria.add(Restrictions.ilike("QR-Code", dataMatrixs, MatchMode.EXACT));
		} else {
	        session.close();
			return null;
		}
		
        Pam5PinCaricati = criteria.list();

        session.close();
        return (Pam5PinCaricati != null && Pam5PinCaricati.size() > 0) ? Pam5PinCaricati.get(0) : null;
	}

}
