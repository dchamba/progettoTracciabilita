package com.vaadin.demo.dashboard.data.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.vaadin.demo.dashboard.component.model.StatoBancale;
import com.vaadin.demo.dashboard.component.utils.CommonUtils;
import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnection;
import com.vaadin.demo.dashboard.data.model.EtichetteImballi;
import com.vaadin.demo.dashboard.data.model.EtichettePezzi;
import com.vaadin.demo.dashboard.data.model.Prodotti;
import com.vaadin.demo.dashboard.data.model.TipoImballi;
import com.vaadin.demo.dashboard.data.model.VistaPackingList;
import com.vaadin.demo.dashboard.data.repository.campi.CampiTipoImballi;
import com.vaadin.shared.ui.dnd.criteria.Criterion.Match;

@SuppressWarnings("unchecked")
public class RepositoryImballi {
	
	public RepositoryImballi() { }

	public List<TipoImballi> getTipoImballi() {
        System.out.println("Reading tipo imballi");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();

        List<TipoImballi> tipoImballi = new ArrayList<TipoImballi>();
		Criteria criteria = session.createCriteria(TipoImballi.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
				
        tipoImballi = criteria.list();

        session.close();
        return tipoImballi;
	}
	
	public List<TipoImballi> getTipoImballiByIdProdotto(Integer idProdotto) {
        System.out.println("Reading tipo imballi");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();

        List<TipoImballi> tipoImballi = new ArrayList<TipoImballi>();
		Criteria criteria = session.createCriteria(TipoImballi.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
		if (idProdotto != null) {
			criteria.add(Restrictions.eq(CampiTipoImballi.idProdotto, idProdotto));
		} else {
	        session.close();
			return null;
		}
		
        tipoImballi = criteria.list();

        session.close();
        return tipoImballi;
	}
	
	public TipoImballi getTipoImballoByFormatoQrCodeMatchCodiceImballo(String codiceImballo) {
        System.out.println("Reading tipo imballi by formato qrcode match");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();

        List<TipoImballi> tipoImballi = new ArrayList<TipoImballi>();
		Criteria criteria = session.createCriteria(TipoImballi.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
        tipoImballi = criteria.list();
        session.close();
        
		Optional<TipoImballi> tipoImballoCaricoPerFormatoDmc = tipoImballi.stream().filter(p -> Pattern.matches(p.getFormatoDatamatrixImballo(), codiceImballo)).findFirst();
		TipoImballi result = tipoImballoCaricoPerFormatoDmc.isPresent() ? tipoImballoCaricoPerFormatoDmc.get() : null;
		
        return result;
	}
		
	public EtichettePezzi getEtichettaPezzo(Integer idEtichettaPezzo, Integer idEtichettaImballo) {
        System.out.println("Reading etichetta pezzo");

        List<EtichettePezzi> etichettePezzi = new ArrayList<EtichettePezzi>();
        
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(EtichettePezzi.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
		if (idEtichettaPezzo != null && idEtichettaPezzo > 0) {
			criteria.add(Restrictions.eq("idEtichettaPezzo", idEtichettaPezzo));
		}
		
		if (idEtichettaImballo != null) {
			criteria.add(Restrictions.eq("idEtichettaImballo", idEtichettaImballo));
		}

		etichettePezzi = criteria.list();
        session.close();
		
        return (etichettePezzi != null && etichettePezzi.size() > 0) ? etichettePezzi.get(0) : null;
	}
	
	public List<EtichettePezzi> getEtichettaPezzoByEtichettaImballo(Integer idEtichettaImballo) {
        System.out.println("Reading etichetta pezzo");

        List<EtichettePezzi> etichettePezzi = new ArrayList<EtichettePezzi>();
        
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(EtichettePezzi.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
				
		if (idEtichettaImballo != null) {
			criteria.add(Restrictions.eq("idEtichettaImballo", idEtichettaImballo));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		etichettePezzi = criteria.list();
        session.close();
		
        return etichettePezzi;
	}
	
	public EtichetteImballi getEtichettaImballo(String codiceEtichettaImballo) {
	    System.out.println("Reading etichetta imballo");
	
	    List<EtichetteImballi> etichetteImballi = new ArrayList<EtichetteImballi>();
	    
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(EtichetteImballi.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
		if (codiceEtichettaImballo != null && !codiceEtichettaImballo.isEmpty()) {
			criteria.add(Restrictions.like("codiceEtichetta", codiceEtichettaImballo));
		}
	
		etichetteImballi = criteria.list();
	    session.close();
		
	    return (etichetteImballi != null && etichetteImballi.size() > 0) ? etichetteImballi.get(0) : null;
	}
	
	public EtichetteImballi getEtichettaImballoByCodiceEtichettaOrStartWith(String codiceEtichettaImballo) {
		EtichetteImballi result = getEtichettaImballo(codiceEtichettaImballo);
		EtichetteImballi result1 = getEtichettaImballoByCodiceEtichetta(codiceEtichettaImballo);
		return result == null ? result1 : result;
	}
	
	public EtichetteImballi getEtichettaImballoByCodiceEtichetta(String codiceEtichettaImballo) {
	    System.out.println("Reading etichetta imballo");
	
	    List<EtichetteImballi> etichetteImballi = new ArrayList<EtichetteImballi>();
	    
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(EtichetteImballi.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
		if (codiceEtichettaImballo != null && !codiceEtichettaImballo.isEmpty()) {
			criteria.add(Restrictions.like("codiceEtichetta", codiceEtichettaImballo, MatchMode.START));
		}
	
		etichetteImballi = criteria.list();
	    session.close();
		
	    return (etichetteImballi != null && etichetteImballi.size() > 0) ? etichetteImballi.get(0) : null;
	}
	
	public EtichetteImballi getEtichettaImballo(Integer idEtichettaImballo) {
	    System.out.println("Reading etichetta imballo");
	
	    List<EtichetteImballi> etichetteImballi = new ArrayList<EtichetteImballi>();
	    
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(EtichetteImballi.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
		if (idEtichettaImballo != null) {
			criteria.add(Restrictions.like("idEtichettaImballo", idEtichettaImballo));
		}
	
		etichetteImballi = criteria.list();
	    session.close();
		
	    return (etichetteImballi != null && etichetteImballi.size() > 0) ? etichetteImballi.get(0) : null;
	}
	
	public List<VistaPackingList> getVistaPackingListByCodiceBancale(String codiceBancale) {
		System.out.println("Reading VistaPackingList");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();

        List<VistaPackingList> vistaPackingList = new ArrayList<VistaPackingList>();
		Criteria criteria = session.createCriteria(VistaPackingList.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
		if (codiceBancale != null && !codiceBancale.isEmpty()) {
			criteria.add(Restrictions.like("codiceEtichetta", codiceBancale, MatchMode.ANYWHERE));
		} else {
	        session.close();
			return null;
		}
		
        vistaPackingList = criteria.list();
        session.close();

        return vistaPackingList;
	}

	public List<VistaPackingList> getVistaPackingListByDatamatrix(String dataMatrix) {
		System.out.println("Reading VistaPackingList");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();

        List<VistaPackingList> vistaPackingList = new ArrayList<VistaPackingList>();
		Criteria criteria = session.createCriteria(VistaPackingList.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
		if (dataMatrix != null && !dataMatrix.isEmpty()) {
			criteria.add(Restrictions.like("dataMatrix", dataMatrix, MatchMode.EXACT));
		} else {
	        session.close();
			return null;
		}
		
        vistaPackingList = criteria.list();
        session.close();

        return vistaPackingList;
	}
	
	public List<VistaPackingList> getVistaPackingListFromEtichettaScatola(String etichettaScatola, Integer idEtichettaImballo) {
		System.out.println("Reading VistaPackingList");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();

        List<VistaPackingList> vistaPackingList = new ArrayList<VistaPackingList>();
		Criteria criteria = session.createCriteria(VistaPackingList.class);
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
		if (etichettaScatola != null && !etichettaScatola.isEmpty()) {
			criteria.add(Restrictions.like("codiceEtichetta", etichettaScatola, MatchMode.EXACT));
		}

		if (idEtichettaImballo != null) {
			criteria.add(Restrictions.eq("idEtichettaImballo", idEtichettaImballo));
		}
		
        vistaPackingList = criteria.list();
        session.close();

        return vistaPackingList == null ? new ArrayList<VistaPackingList>() : vistaPackingList;
	}
	
	public EtichettePezzi salva(EtichettePezzi etichettePezzi) {
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		Transaction transaction =  session.beginTransaction();
		
		session.saveOrUpdate(etichettePezzi);
		transaction.commit();
		
        session.close();
        return etichettePezzi;
	}
	
	public EtichettePezzi elimina(EtichettePezzi etichettePezzi) {
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		Transaction transaction =  session.beginTransaction();
		
		session.delete(etichettePezzi);
		transaction.commit();
		
        session.close();
        return etichettePezzi;
	}

	public EtichetteImballi salva(EtichetteImballi etichetteImballi) {
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		Transaction transaction =  session.beginTransaction();
		
		session.saveOrUpdate(etichetteImballi);
		transaction.commit();
		
        session.close();
        return etichetteImballi;
	}
	
	public void assegnaIdEtichettaImballo(Integer vecchioIdEtichettaImballo, EtichetteImballi nuovaEtichettaImballo) {
		
		List<EtichettePezzi> listaEtichettaPezzi = getEtichettaPezzoByEtichettaImballo(vecchioIdEtichettaImballo);
		for (EtichettePezzi etichettePezzoVecchio : listaEtichettaPezzi) {
			EtichettePezzi etichettaPezzoNuovo = new EtichettePezzi();
			etichettaPezzoNuovo.setIdEtichettaImballo(nuovaEtichettaImballo.getIdEtichettaImballo());
			etichettaPezzoNuovo.setEtichettaImballo(nuovaEtichettaImballo);
			etichettaPezzoNuovo.setDatamatrix(etichettePezzoVecchio.getDatamatrix());
			etichettaPezzoNuovo.setDataOraCreazione(etichettePezzoVecchio.getDataOraCreazione());
			etichettaPezzoNuovo.setDataOraUltimaModifca(etichettePezzoVecchio.getDataOraUltimaModifca());
			etichettaPezzoNuovo.setUtenteCreatore(etichettePezzoVecchio.getUtenteCreatore());
			etichettaPezzoNuovo.setUtenteUltimaModifica(etichettePezzoVecchio.getUtenteUltimaModifica());
			etichettaPezzoNuovo.setEliminato(false);
			salva(etichettaPezzoNuovo);
		}

		for (EtichettePezzi etichettePezzoVecchio : listaEtichettaPezzi) {
			elimina(etichettePezzoVecchio);
		}
	}
	
	public StatoBancale getStatoBancale(EtichetteImballi etichettaImballo) {
		StatoBancale result = new StatoBancale();
		
		TipoImballi tipoImballoCorrente = etichettaImballo.getTipoImballo();
		//int totalePezziPerBancaleRichiesti = tipoImballoCorrente.getQtaPezziPerScatola() * tipoImballoCorrente.getQtaScatolePerBancale();
		
		List<VistaPackingList> pzInBancale = getVistaPackingListByCodiceBancale(etichettaImballo.getCodiceEtichettaBancaleSmeup());
		List<VistaPackingList> listaEtichette = pzInBancale.stream().filter(CommonUtils.distinctByKey(p -> p.getCodiceEtichetta())).collect(Collectors.toList());
		
		result.setQtyOfPcsInThePallet(pzInBancale.size());
		result.setQtyOfBoxesInThePallet(listaEtichette.size());
		result.setBoxesQtyPerPalletComplete(listaEtichette.size() == tipoImballoCorrente.getQtaScatolePerBancale());
		
		result.setStandardBoxesQtyPerPallet(tipoImballoCorrente.getQtaScatolePerBancale());
		result.setStandardPcsQtyPerBoxes(tipoImballoCorrente.getQtaPezziPerScatola());
		result.setStandardPcsQtyPerPallet(result.getStandardPcsQtyPerBoxes() * result.getStandardBoxesQtyPerPallet());
		
		for (VistaPackingList etichetta : listaEtichette) {
			int totPzImballo = this.getVistaPackingListFromEtichettaScatola(etichetta.getCodiceEtichetta(), null).size();
			int pzMancanti = (result.getStandardPcsQtyPerBoxes() - totPzImballo);
			if(pzMancanti > 0) {
				result.getBoxesWithMissingPcs().put(etichetta.getCodiceEtichettaImballoSmeup(), pzMancanti);
			}
		}
		return result;
	}
	
	
}
