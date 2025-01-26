package com.vaadin.demo.dashboard.data.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnection;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiProcessoTT;
import com.vaadin.demo.dashboard.data.model.LottiFusioneAssegnazioneStampi;
import com.vaadin.demo.dashboard.data.model.Prodotti;
import com.vaadin.demo.dashboard.data.model.StampiProdotti;
import com.vaadin.demo.dashboard.data.model.VistaLottiFusioneAssegnazioneStampi;
import com.vaadin.demo.dashboard.data.repository.campi.CampiProdotti;

@SuppressWarnings("unchecked")
public class RepositoryLottiFusioneAssegnazioneStampi {
	
	public RepositoryLottiFusioneAssegnazioneStampi() { }

	public LottiFusioneAssegnazioneStampi getLottiFusioneAssegnazioneStampiById(Integer idLottoFusione) {
		System.out.println("Reading getLottiFusioneAssegnazioneStampiById");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(LottiFusioneAssegnazioneStampi.class, "vista");
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
		if (idLottoFusione != null) {
			criteria.add(Restrictions.eq("idLottoFusioneAssegnazioneStampo", idLottoFusione));
		}
		
		List<LottiFusioneAssegnazioneStampi> lista = criteria.list();
		
        session.close();
        return (lista != null && lista.size() > 0) ? lista.get(0) : null;
	}

	public List<VistaLottiFusioneAssegnazioneStampi> getVistaLottiFusioneAssegnazioneStampiById(Integer idLottoFusione) {
		System.out.println("Reading getVistaLottiFusioneAssegnazioneStampiById");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		
		Criteria criteria = session.createCriteria(VistaLottiFusioneAssegnazioneStampi.class, "vista");
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
		if (idLottoFusione != null) {
			criteria.add(Restrictions.eq("idLottoFusioneAssegnazioneStampo", idLottoFusione));
		}
		
		List<VistaLottiFusioneAssegnazioneStampi> lista = criteria.list();
		
        session.close();
        return lista;
	}
	
	public List<VistaLottiFusioneAssegnazioneStampi> getVistaLottiFusioneAssegnazioneStampi(Prodotti prodotto, StampiProdotti stampoProdotto, Optional<LocalDate[]> intervallo) {
		System.out.println("Reading getVistaLottiFusioneAssegnazioneStampi");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		
		Criteria criteria = session.createCriteria(VistaLottiFusioneAssegnazioneStampi.class, "vista");
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
		criteria.addOrder(Order.desc("aData"));

		if (prodotto != null) {
			criteria.add(Restrictions.eq("idProdotto", prodotto.getIdProdotto()));
		}

		if (stampoProdotto != null) {
			criteria.add(Restrictions.eq("idStampoProdotto", stampoProdotto.getIdStampoProdotto()));
		}
		
//		if(intervallo != null && intervallo.get()[0] != null && intervallo.get()[1] != null) {
//        	criteria.add(Restrictions.between("daData", intervallo.get()[0], intervallo.get()[1]));
//        	criteria.add(Restrictions.between("aData", intervallo.get()[0], intervallo.get()[1]));
//		}
	    if (intervallo != null && intervallo.isPresent() && intervallo.get()[0] != null && intervallo.get()[1] != null) {
	    	java.sql.Date dataInizio = java.sql.Date.valueOf(intervallo.get()[0]); // Conversione della data iniziale
	    	java.sql.Date dataFine = java.sql.Date.valueOf(intervallo.get()[1]);   // Conversione della data finale

	    	
	        // Creazione del filtro OR per daData e aData
	        criteria.add(Restrictions.disjunction()
	                .add(Restrictions.between("daData", dataInizio, dataFine))
	                .add(Restrictions.between("aData", dataInizio, dataFine)));
	    }
		
		List<VistaLottiFusioneAssegnazioneStampi> lista = criteria.list();
		
        session.close();
        return lista;
	}

	public List<VistaLottiFusioneAssegnazioneStampi> getLottiFusioneAssegnazioneStampi(Integer idProdotto, Integer idStampoProdotto, Date daData, Date aData, 
			Integer daProgresivo, Integer aProgresivo) {
        System.out.println("Reading LottiFusioneAssegnazioneStampi");

		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		
		Criteria criteria = session.createCriteria(VistaLottiFusioneAssegnazioneStampi.class, "vista");
		criteria.add(RepositoryUtils.getCriteraEliminato());
		
		criteria.addOrder(Order.desc("aData"));

		if (idProdotto != null) {
			criteria.add(Restrictions.eq("idProdotto", idProdotto));
		}

		if (idStampoProdotto != null) {
			criteria.add(Restrictions.eq("idStampoProdotto", idStampoProdotto));
		}
		
        //(daData <= FINE AND aData >= INIZIO)
        if (daData != null) {
        	criteria.add(Restrictions.ge("aData", daData));
        }
        if (aData != null) {
        	criteria.add(Restrictions.le("daData", aData));
		}
        if (daProgresivo != null) {
        	criteria.add(Restrictions.ge("aProgresivo", daProgresivo));
        }
        if (aProgresivo != null) {
        	criteria.add(Restrictions.le("daProgresivo", aProgresivo));
		}
		
		List<VistaLottiFusioneAssegnazioneStampi> lista = criteria.list();
		
        session.close();
        return lista;
	}
	
	public List<VistaLottiFusioneAssegnazioneStampi> verificaCorrettezzaDateEProgressivo(LottiFusioneAssegnazioneStampi lottoFusione) {
        System.out.println("VerificaCorrettezzaDateEProgressivo LottiFusioneAssegnazioneStampi");

	    Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(VistaLottiFusioneAssegnazioneStampi.class, "vista");

        criteria.add(Restrictions.eq("eliminato", false));

        if (lottoFusione.getStampiProdotto() != null) {
            criteria.add(Restrictions.eq("stampiProdotto.idStampoProdotto", lottoFusione.getStampiProdotto().getIdStampoProdotto()));
        }

        // Crea una congiunzione per collegare le condizioni con AND
        Conjunction conjunction = Restrictions.conjunction();

        // Aggiungi il criterio di intersezione per daData e aData
        if (lottoFusione.getDaData() != null && lottoFusione.getaData() != null) {
            conjunction.add(Restrictions.and(
                Restrictions.le("daData", lottoFusione.getaData()),
                Restrictions.ge("aData", lottoFusione.getDaData())
            ));
        }

        // Aggiungi il criterio di intersezione per daProgressivo e aProgressivo
        if (lottoFusione.getDaProgressivo() != null && lottoFusione.getaProgressivo() != null) {
            conjunction.add(Restrictions.and(
                Restrictions.le("daProgressivo", lottoFusione.getaProgressivo()),
                Restrictions.ge("aProgressivo", lottoFusione.getDaProgressivo())
            ));
        }

        // Aggiungi la congiunzione ai criteri principali
        criteria.add(conjunction);

        // Esegui la query
        List<VistaLottiFusioneAssegnazioneStampi> lista = criteria.list();

        session.close();
        
        return lista;
	}

	public LottiFusioneAssegnazioneStampi salvaLottiFusioneAssegnazioneStampi(LottiFusioneAssegnazioneStampi lottiFusioneAssegnazioneStampi) {
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		Transaction transaction =  session.beginTransaction();
		
		session.saveOrUpdate(lottiFusioneAssegnazioneStampi);
		
		transaction.commit();
		
        session.close();
        
        return lottiFusioneAssegnazioneStampi;
	}
	
	public Integer save(LottiFusioneAssegnazioneStampi lottiFusioneAssegnazioneStampi) {
        System.out.println("Ssalvataggio LottiFusioneAssegnazioneStampi");
        
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		Transaction saveTransaction = session.beginTransaction();

		Integer idLottiFusioneAssegnazioneStampi = (Integer) session.save(lottiFusioneAssegnazioneStampi); 
		
        saveTransaction.commit();
        session.close();
        
        return idLottiFusioneAssegnazioneStampi;
	}

	public LottiFusioneAssegnazioneStampi saveOrUpdate(LottiFusioneAssegnazioneStampi lottiFusioneAssegnazioneStampi) {
		Session session = DatabaseHibernateConnection.getSessionFactory().openSession();
		Transaction transaction =  session.beginTransaction();
		
		session.saveOrUpdate(lottiFusioneAssegnazioneStampi);
		
		transaction.commit();
		
        session.close();
        
        return lottiFusioneAssegnazioneStampi;
	}

}
