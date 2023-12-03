package com.vaadin.demo.dashboard.data.repository;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.demo.dashboard.component.model.FilterDatamatrix;
import com.vaadin.demo.dashboard.component.utils.FasiProcessoUtils.FasiProcessoLista;
import com.vaadin.demo.dashboard.data.model.Datamatrix;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiEseguite;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiProcesso;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiProcessoTT;

@SuppressWarnings("unchecked")
public class RepositoryVerificaQrCode {
	
	public RepositoryVerificaQrCode() { }
	
	public Map<String, List<DatamatrixFasiEseguite>> getDatamatrixFasiEseguite(String codiceDatamatrix) throws ParseException {
		
		Datamatrix datamatriCercato = RepositoryProvider.getRepositoryDatamatrix().getDataMatrix(new FilterDatamatrix(codiceDatamatrix), null).getResult().get(0);
		List<DatamatrixFasiProcesso> fasiProcessoProcessoEseguite = RepositoryProvider.getRepositoryDatamatrixTrattamenti().getListaDatamatrixFasiProcesso(datamatriCercato.getIdDataMatrix());
		
		//Fasi processo
		List<DatamatrixFasiEseguite> listaFasiEseguite = new ArrayList<DatamatrixFasiEseguite>();
		for(DatamatrixFasiProcesso dfp : fasiProcessoProcessoEseguite) {
			if(dfp.getFaseProcesso().getCodiceFase().equals(FasiProcessoLista.HB.toString())) {
				continue;
			}
			DatamatrixFasiEseguite datamatrixFaseEseuita = new DatamatrixFasiEseguite();
			
			datamatrixFaseEseuita.setDataMatrix(dfp.getDataMatrix().getDataMatrix());
			datamatrixFaseEseuita.setIdDataMatrix(dfp.getDataMatrix().getIdDataMatrix());
			datamatrixFaseEseuita.setDataOra(dfp.getDataOra());
			datamatrixFaseEseuita.setFaseProcesso(dfp.getFaseProcesso().getDescrizione());
			datamatrixFaseEseuita.setCodiceFaseProcesso(dfp.getFaseProcesso().getCodiceFase());
			datamatrixFaseEseuita.setImpianto(dfp.getUtenteOperatore().getUsername());
			listaFasiEseguite.add(datamatrixFaseEseuita);
		}

		List<DatamatrixFasiEseguite> listaFasiTrattamentoEseguite = new ArrayList<DatamatrixFasiEseguite>();
		
		for(DatamatrixFasiProcesso dfp : fasiProcessoProcessoEseguite) {
			if(dfp.getFaseProcesso().getCodiceFase().equals(FasiProcessoLista.HB.toString())) {

				DatamatrixFasiEseguite datamatrixTrattamentoFaseEseguita = new DatamatrixFasiEseguite();
				
				datamatrixTrattamentoFaseEseguita.setDataMatrix(dfp.getDataMatrix().getDataMatrix());
				datamatrixTrattamentoFaseEseguita.setIdDataMatrix(dfp.getDataMatrix().getIdDataMatrix());
				datamatrixTrattamentoFaseEseguita.setDataOra(dfp.getDataOra());
				datamatrixTrattamentoFaseEseguita.setFaseProcesso(dfp.getFaseProcesso().getDescrizione());
				datamatrixTrattamentoFaseEseguita.setCodiceFaseProcesso(dfp.getFaseProcesso().getCodiceFase());
				
				DatamatrixFasiProcessoTT dfpTT = RepositoryProvider.getRepositoryDatamatrixTrattamentiTT().getDatamatrixFasiProcessoTTByIdFaseProcesso(dfp.getIdDatamatrixFaseProcesso());
				
				datamatrixTrattamentoFaseEseguita.setUtente(dfpTT.getOperatore());
				datamatrixTrattamentoFaseEseguita.setDurezza(dfpTT.getValoreDurezza().toString()); 
				datamatrixTrattamentoFaseEseguita.setNumeroFornata(dfpTT.getNumeroFornata());
				datamatrixTrattamentoFaseEseguita.setFornitore(dfp.getUtenteOperatore().getAzienda().getRagioneSociale());
				
				listaFasiTrattamentoEseguite.add(datamatrixTrattamentoFaseEseguita);
			}
		}
		
		List<DatamatrixFasiEseguite> fasiProcessoProveTenutaEseguite = RepositoryProvider.getRepositoryProveTenuta().getDatamatrixProveTenutaEseguite(codiceDatamatrix);
		
		Map<String, List<DatamatrixFasiEseguite>> mappaListaFasiEseguite = new HashMap<>();
		mappaListaFasiEseguite.put(TipoDatamatrixFasiEseguite.FASI_PROCESSO.toString(), listaFasiEseguite);
		mappaListaFasiEseguite.put(TipoDatamatrixFasiEseguite.TRATTAMENTO_TERMICO.toString(), listaFasiTrattamentoEseguite);
		mappaListaFasiEseguite.put(TipoDatamatrixFasiEseguite.PROVE_TENUTA.toString(), fasiProcessoProveTenutaEseguite);
		
		return mappaListaFasiEseguite;
	}
		
	public enum TipoDatamatrixFasiEseguite {
		FASI_PROCESSO, 
		TRATTAMENTO_TERMICO,
		PROVE_TENUTA;
	}
}
