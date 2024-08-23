package com.vaadin.demo.dashboard.data.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;

import com.vaadin.demo.dashboard.component.utils.CommonUtils;
import com.vaadin.demo.dashboard.component.utils.ConfigsUtils;
import com.vaadin.demo.dashboard.component.utils.FasiProcessoUtils;
import com.vaadin.demo.dashboard.data.hibernate.DatabaseHibernateConnectionDatamatrix_002;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiEseguite;
import com.vaadin.demo.dashboard.data.model.Pan5;

@SuppressWarnings("unchecked")
public class RepositoryPin5 {
	
	public RepositoryPin5() { }
	
	public Pan5 caricaAssemblaggioPinPan5(String dataMatrixs) {
        System.out.println("Reading tipo Pan5");

		Session session = DatabaseHibernateConnectionDatamatrix_002.getSessionFactory().openSession();
		session.beginTransaction();

        List<Pan5> Pam5PinCaricati = new ArrayList<Pan5>();
		Criteria criteria = session.createCriteria(Pan5.class);
//		criteria.add(Restrictions.sqlRestriction("QR-Code like ?", "%" + dataMatrixs + "%", StandardBasicTypes.STRING));
		
		if (dataMatrixs != null) {
			criteria.add(Restrictions.ilike("QRCode", dataMatrixs, MatchMode.ANYWHERE));
		} else {
	        session.close();
			return null;
		}
		
        Pam5PinCaricati = criteria.list();

        session.close();
        return (Pam5PinCaricati != null && Pam5PinCaricati.size() > 0) ? Pam5PinCaricati.get(0) : null;
	}
	
	public List<DatamatrixFasiEseguite> getDatamatrixAssemblaggioPinEseguito(String codiceDatamatrix) {
		List<DatamatrixFasiEseguite> listaFasiEseguite = new ArrayList<DatamatrixFasiEseguite>();
		
		Pan5 pan5Caricato = caricaAssemblaggioPinPan5(codiceDatamatrix);
		if(pan5Caricato != null) {
			DatamatrixFasiEseguite faseEseguita = new DatamatrixFasiEseguite();
			faseEseguita.setDataMatrix(pan5Caricato.getQRCode());
			faseEseguita.setFaseProcesso("Assembly PIN");
			
			String esito = pan5Caricato.isEsito() ? "OK" : "KO";
			faseEseguita.setEsito(esito);
			
			String note = new String();
			note += "PIN01 (Pres. " + pan5Caricato.getPressionePin1().toString() + " e Pos. " + pan5Caricato.getPosizionePin1().toString() + ")";
			note += " e ";
			note += "PIN02 (Pres. " + pan5Caricato.getPressionePin2().toString() + " e Pos. " + pan5Caricato.getPosizionePin2().toString() + ")";
			faseEseguita.setNote(note);
			
			faseEseguita.setDataOra(CommonUtils.toDate(pan5Caricato.getDataOra()));

			listaFasiEseguite.add(faseEseguita);
		}
		return listaFasiEseguite;
	}
	
	public String verificaAssemblaggioPinOK(String codiceDatamatrix) {
		Pan5 pan5Caricato = caricaAssemblaggioPinPan5(codiceDatamatrix);
		
		if(pan5Caricato == null) {
			return FasiProcessoUtils.ASSEMBLAGGIO_PIN_NON_ESEGUITO;
		} else {
			try {
				String assemblyPAN10_Pin1_posizioneMax = RepositoryProvider.getRepositoryConfig().getConfigByChiave(ConfigsUtils.assemblyPAN10_Pin1_posizioneMax);
				String assemblyPAN10_Pin1_posizioneMin = RepositoryProvider.getRepositoryConfig().getConfigByChiave(ConfigsUtils.assemblyPAN10_Pin1_posizioneMin);
				String assemblyPAN10_Pin1_pressioneMax = RepositoryProvider.getRepositoryConfig().getConfigByChiave(ConfigsUtils.assemblyPAN10_Pin1_pressioneMax);
				String assemblyPAN10_Pin1_pressioneMin = RepositoryProvider.getRepositoryConfig().getConfigByChiave(ConfigsUtils.assemblyPAN10_Pin1_pressioneMin);

				String assemblyPAN10_Pin2_posizioneMax = RepositoryProvider.getRepositoryConfig().getConfigByChiave(ConfigsUtils.assemblyPAN10_Pin2_posizioneMax);
				String assemblyPAN10_Pin2_posizioneMin = RepositoryProvider.getRepositoryConfig().getConfigByChiave(ConfigsUtils.assemblyPAN10_Pin2_posizioneMin);
				String assemblyPAN10_Pin2_pressioneMax = RepositoryProvider.getRepositoryConfig().getConfigByChiave(ConfigsUtils.assemblyPAN10_Pin2_pressioneMax);
				String assemblyPAN10_Pin2_pressioneMin = RepositoryProvider.getRepositoryConfig().getConfigByChiave(ConfigsUtils.assemblyPAN10_Pin2_pressioneMin);
				
				Double Pin1_posizioneMax = Double.parseDouble(assemblyPAN10_Pin1_posizioneMax);
				Double Pin1_posizioneMin = Double.parseDouble(assemblyPAN10_Pin1_posizioneMin);
				Double Pin1_pressioneMax = Double.parseDouble(assemblyPAN10_Pin1_pressioneMax);
				Double Pin1_pressioneMin = Double.parseDouble(assemblyPAN10_Pin1_pressioneMin);

				Double Pin2_posizioneMax = Double.parseDouble(assemblyPAN10_Pin2_posizioneMax);
				Double Pin2_posizioneMin = Double.parseDouble(assemblyPAN10_Pin2_posizioneMin);
				Double Pin2_pressioneMax = Double.parseDouble(assemblyPAN10_Pin2_pressioneMax);
				Double Pin2_pressioneMin = Double.parseDouble(assemblyPAN10_Pin2_pressioneMin);

				boolean esitoPin1_posizione = true;
				boolean esitoPin1_pressione = true;

				boolean esitoPin2_posizione = true;
				boolean esitoPin2_pressione = true;
				
				//pin1
				if(Pin1_posizioneMin > -1) {
					if(pan5Caricato.getPosizionePin1() >= Pin1_posizioneMin ) { }
					else { esitoPin1_posizione = false; }
				}

				if(Pin1_posizioneMax > -1) {
					if(pan5Caricato.getPosizionePin1() <= Pin1_posizioneMax) { }
					else { esitoPin1_posizione = false; }
				}

				if(Pin1_pressioneMin > -1) {
					if(pan5Caricato.getPressionePin1() >= Pin1_pressioneMin ) { }
					else { esitoPin1_pressione = false; }
				}

				if(Pin1_pressioneMax > -1) {
					if(pan5Caricato.getPressionePin1() <= Pin1_pressioneMax) { }
					else { esitoPin1_pressione = false; }
				}

				//pin2
				if(Pin2_posizioneMin > -1) {
					if(pan5Caricato.getPosizionePin2() >= Pin2_posizioneMin ) { }
					else { esitoPin2_posizione = false; }
				}

				if(Pin2_posizioneMax > -1) {
					if(pan5Caricato.getPosizionePin2() <= Pin2_posizioneMax) { }
					else { esitoPin2_posizione = false; }
				}

				if(Pin2_pressioneMin > -1) {
					if(pan5Caricato.getPressionePin2() >= Pin2_pressioneMin ) { }
					else { esitoPin2_pressione = false; }
				}

				if(Pin2_pressioneMax > -1) {
					if(pan5Caricato.getPressionePin2() <= Pin2_pressioneMax) { }
					else { esitoPin2_pressione = false; }
				}

				String messaggioErrore = "";
				if(!esitoPin1_posizione) {
					messaggioErrore += "Pos. Pin1";
				}
				if(!esitoPin1_pressione) {
					if(messaggioErrore.length() > 0) { messaggioErrore += ","; }
					messaggioErrore += "Press. Pin1";
				}
				if(!esitoPin2_posizione) {
					if(messaggioErrore.length() > 0) { messaggioErrore += ","; }
					messaggioErrore += "Pos. Pin2";
				}
				if(!esitoPin2_pressione) {
					if(messaggioErrore.length() > 0) { messaggioErrore += ","; }
					messaggioErrore += "Press. Pin2";
				}
				
				if(messaggioErrore.length() == 0) {
					return FasiProcessoUtils.ASSEMBLAGGIO_PIN_OK;
				} else {
					messaggioErrore = "Assemblaggio pin KO per parametri: " + messaggioErrore;
					return messaggioErrore;
				}
			} catch (Exception ex) {
				return "Non è stato possibile eseguire il controllo di assemblaggio pin. Errore : " + ex.getMessage();
			}
		}
	}
}
