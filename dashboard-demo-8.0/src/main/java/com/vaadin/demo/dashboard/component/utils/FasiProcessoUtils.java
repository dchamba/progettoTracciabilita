package com.vaadin.demo.dashboard.component.utils;

import com.vaadin.demo.dashboard.data.model.Datamatrix;
import com.vaadin.demo.dashboard.data.model.FasiProcesso;
import com.vaadin.demo.dashboard.data.model.FasiProcessoProdotto;
import com.vaadin.demo.dashboard.data.model.Prodotti;
import com.vaadin.demo.dashboard.data.repository.RepositoryProvider;

public class FasiProcessoUtils {
	
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
    		//if(codiceFase.equals(FasiProcessoLista.FIN.toString())) { continue; }
    		//if(codiceFaseDaVerificareSeEseguita.equals(FasiProcessoLista.HB.toString())) { continue; }
    		
//    		//verifico se lavorazione eseguita
//			if(codiceFaseDaVerificareSeEseguita.equals(FasiProcessoLista.LAV.toString()) || 
//				codiceFaseDaVerificareSeEseguita.equals(FasiProcessoLista.LIQ.toString()) ||
//				codiceFaseDaVerificareSeEseguita.equals(FasiProcessoLista.RX.toString()) ||
//				codiceFaseDaVerificareSeEseguita.equals(FasiProcessoLista.STE.toString())) {
//			
//			boolean esitoVerificaFasePricessoEseguita = RepositoryProvider.repositoryFasiProcesso().getFaseProcessoPerDatamtarix(datamatrix.getIdDataMatrix(), faseProcessoProdottoDaVerificareSeEseguita.getFaseProcesso().getIdFaseProcesso());
//
//			if(!esitoVerificaFasePricessoEseguita) {
//				if(fasiNonEseguite.length() > 0) { fasiNonEseguite += ", "; }
//				
//				fasiNonEseguite += faseProcessoProdottoDaVerificareSeEseguita.getFaseProcesso().getDescrizione();
//			}
//		} 
			
			if(codiceFaseDaVerificareSeEseguita.equals(FasiProcessoLista.TEN.toString())) {
				if(!datamatrix.getProddoto().isProvatenutNecessaria()) continue;
				
				String esitoProvaTenutaAria = "";
				if(datamatrix.getProddoto().isProvatenutaaria()) {
					esitoProvaTenutaAria = RepositoryProvider.getRepositoryProveTenuta().verificaEsitoProvaTenutaAria(datamatrix.getDataMatrix());
					ProveTenutaUtils.verificaEsitoTenutaELanciaErrore(esitoProvaTenutaAria);
				}

				String esitoProvaTenutaElio = "";
				if(datamatrix.getProddoto().isProvatenutaelio()) {
					esitoProvaTenutaElio = RepositoryProvider.getRepositoryProveTenuta().verificaEsitoProvaTenutaElio(datamatrix.getDataMatrix());
					ProveTenutaUtils.verificaEsitoTenutaELanciaErrore(esitoProvaTenutaElio);
				}
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
		ANU,
		PACKINGLISTCCU,
		PACKINGLISTGESTAMP,
		PACKINGLISTFIA0505,
		PACKINGLISTFIA0504,
		PACKINGLISTFIA10,
		PACKINGLISTACFSHIELD;
	}
}
