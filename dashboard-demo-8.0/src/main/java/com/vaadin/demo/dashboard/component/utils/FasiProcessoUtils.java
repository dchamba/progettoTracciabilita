package com.vaadin.demo.dashboard.component.utils;

import com.vaadin.demo.dashboard.data.model.Datamatrix;
import com.vaadin.demo.dashboard.data.model.FasiProcesso;
import com.vaadin.demo.dashboard.data.model.FasiProcessoProdotto;
import com.vaadin.demo.dashboard.data.model.Pan5;
import com.vaadin.demo.dashboard.data.model.Prodotti;
import com.vaadin.demo.dashboard.data.repository.RepositoryProvider;

public class FasiProcessoUtils {

	public static String NON_PROVATO_PT = "NON_PROVATO_PT";
	public static String PROVATO_KO = "PROVATO_KO";
	public static String PROVATO_OK = "PROVATO_OK";

	public static String ASSEMBLAGGIO_PIN_NON_ESEGUITO = "ASSEMBLAGGIO_PIN_NON_ESEGUITO";
	public static String ASSEMBLAGGIO_PIN_KO = "ASSEMBLAGGIO_PIN_KO";
	public static String ASSEMBLAGGIO_PIN_OK = "ASSEMBLAGGIO_PIN_OK";
	
	public static void verificaEsitoTenutaELanciaErrore(String esitoProvaTenuta) throws Exception {
		if(esitoProvaTenuta.equals(NON_PROVATO_PT)) {
            throw new Exception("ATTENZIONE! Questo pezzo NON è stato provato a tenuta");
		} else if(esitoProvaTenuta.equals(PROVATO_KO)) {
            throw new Exception("ATTENZIONE! Questo pezzo risulta KO a provato tenuta");
		} else if(esitoProvaTenuta.equals(ASSEMBLAGGIO_PIN_NON_ESEGUITO)) {
            throw new Exception("ATTENZIONE! Assemblaggio Pin NON è stato eseguito");
		} else if(esitoProvaTenuta.equals(PROVATO_OK)) { }
		else {
			throw new Exception(esitoProvaTenuta);
		}
	}
	
	public static void controlloFasiEseguiteCorrettamente(Prodotti prodottoCorrente, Datamatrix datamatrix, String faseCorrentePagina) throws Exception {
    	if(prodottoCorrente.getFasiProcesso() == null || prodottoCorrente.getFasiProcesso().size() == 0) {
    		return;
    	}
    	
    	FasiProcesso faseProcessoPaginaCorrente = RepositoryProvider.repositoryFasiProcesso().getFaseProcessoPerCodice(faseCorrentePagina);
    	FasiProcessoProdotto faseProcessoProdotto = RepositoryProvider.repositoryFasiProcessoProdotti().getFaseProcessoProdotto(prodottoCorrente.getIdProdotto(), faseProcessoPaginaCorrente.getIdFaseProcesso());

    	if(!faseProcessoProdotto.getVerificaSaltoProcessoFasiPrecedenti().booleanValue()) 
    		return;
    	
		String fasiNonEseguite = "";
		//Le fasi processo sono caricate in 
    	for(FasiProcessoProdotto faseProcessoProdottoDaVerificareSeEseguita : prodottoCorrente.getFasiProcesso()) {
    		String codiceFaseDaVerificareSeEseguita = faseProcessoProdottoDaVerificareSeEseguita.getFaseProcesso().getCodiceFase();
    		
    		if(!faseProcessoProdottoDaVerificareSeEseguita.getLetturaQrCodeObbligatoriaInSwTracciabilita())
    			continue;
    		
    		if(faseCorrentePagina.equals(codiceFaseDaVerificareSeEseguita)) { break; }
			
			if(codiceFaseDaVerificareSeEseguita.equals(FasiProcessoLista.TEN.toString())) {
				if(!datamatrix.getProddoto().isProvatenutNecessaria()) continue;
				
				String esitoProvaTenutaAria = "";
				if(datamatrix.getProddoto().isProvatenutaaria()) {
					esitoProvaTenutaAria = RepositoryProvider.getRepositoryProveTenuta().verificaEsitoProvaTenutaAria(datamatrix.getDataMatrix());
					FasiProcessoUtils.verificaEsitoTenutaELanciaErrore(esitoProvaTenutaAria);
				}

				String esitoProvaTenutaElio = "";
				if(datamatrix.getProddoto().isProvatenutaelio()) {
					esitoProvaTenutaElio = RepositoryProvider.getRepositoryProveTenuta().verificaEsitoProvaTenutaElio(datamatrix.getDataMatrix());
					FasiProcessoUtils.verificaEsitoTenutaELanciaErrore(esitoProvaTenutaElio);
				}
    		} else if (codiceFaseDaVerificareSeEseguita.equals(FasiProcessoLista.ASS.toString())) { 
        		//verifico se assemblaggio pin è stato eseguito
    			String result = RepositoryProvider.getRepositoryPin5().verificaAssemblaggioPinOK(datamatrix.getDataMatrix());
    			if(result.equals(FasiProcessoUtils.ASSEMBLAGGIO_PIN_NON_ESEGUITO)) {
    				throw new Exception("ATTENZIONE! Per questo pezzo NON è stato eseguito ASSEMBLAGGIO PIN");
    			} 
    			else if(result.equals(FasiProcessoUtils.ASSEMBLAGGIO_PIN_OK)) { }
    			else { throw new Exception(result); }
    		} else {
        		//verifico se questa fase è stata eseguita
    			boolean esitoVerificaFasePricessoEseguita = RepositoryProvider.repositoryFasiProcesso().getFaseProcessoPerDatamtarix(datamatrix.getIdDataMatrix(), faseProcessoProdottoDaVerificareSeEseguita.getFaseProcesso().getIdFaseProcesso());

    			if(!esitoVerificaFasePricessoEseguita) {
    				if(fasiNonEseguite.length() > 0) { fasiNonEseguite += ", "; }
    				
    				fasiNonEseguite += faseProcessoProdottoDaVerificareSeEseguita.getFaseProcesso().getDescrizione();
    			}
    		}
    	}
		
		if(!fasiNonEseguite.isEmpty()) {
            throw new Exception("ATTENZIONE! Questo pezzo NON è stato processato per le fasi: " + fasiNonEseguite);
		}
    }
	
	public enum FasiProcessoLista {
		DASHBOARD, 
		GESTIONE_DATAMATRIX,
		IMBALLI_ANIME,
		CONSULTAZIONE_DATAMATRIX,
		GESTIONE_TRATTAMENTO,
		HB,
		TD,
		FUS,
		STE,
		SAB,
		SBA,
		LAV,
		TEN,
		FIN,
		LIQ,
		RX,
		ASS,
		PACKINGLISTCCU,
		PACKINGLISTGESTAMP,
		PACKINGLISTFIA0505,
		PACKINGLISTFIA0504,
		PACKINGLISTFIA10,
		PACKINGLISTACFSHIELD;
	}
}
