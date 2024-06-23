package com.vaadin.demo.dashboard.data.repository;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.demo.dashboard.component.model.FilterDatamatrix;
import com.vaadin.demo.dashboard.component.utils.FasiProcessoUtils;
import com.vaadin.demo.dashboard.component.utils.FasiProcessoUtils.FasiProcessoLista;
import com.vaadin.demo.dashboard.data.model.Datamatrix;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiEseguite;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiProcesso;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiProcessoTT;
import com.vaadin.demo.dashboard.data.model.VistaPackingList;

@SuppressWarnings("unchecked")
public class RepositoryVerificaQrCode {
	
	public RepositoryVerificaQrCode() { }
	
	public Map<String, List<DatamatrixFasiEseguite>> getDatamatrixFasiEseguite(String codiceDatamatrix) throws ParseException {
		
		Datamatrix datamatriCercato = RepositoryProvider.getRepositoryDatamatrix().getDataMatrix(new FilterDatamatrix(codiceDatamatrix), null).getResult().get(0);
		List<DatamatrixFasiProcesso> fasiProcessoProcessoEseguite = RepositoryProvider.getRepositoryDatamatrixTrattamenti().getListaDatamatrixFasiProcesso(datamatriCercato.getIdDataMatrix());
		List<VistaPackingList> vistaPackingList = RepositoryProvider.repositoryImballi().getVistaPackingListByDatamatrix(codiceDatamatrix);
		
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
			
			if(dfp.getImpianto() != null && !dfp.getImpianto().isEmpty()) {
				datamatrixFaseEseuita.setImpianto(dfp.getImpianto());
				
				String note = "";
				if(!dfp.getPallet().isEmpty() && dfp.getPallet() != null) {
					note += "Pallet: " + dfp.getPallet();
				}
				if(!dfp.getPosizione().isEmpty() && dfp.getPosizione() != null) {
					note += " Pos.: " + dfp.getPosizione();
				}
				datamatrixFaseEseuita.setNote(note);
			} else {
				datamatrixFaseEseuita.setImpianto(dfp.getUtenteOperatore().getUsername());
			}
				
			listaFasiEseguite.add(datamatrixFaseEseuita);
		}

		//Trattamento termico
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
				if(dfpTT != null) {
					datamatrixTrattamentoFaseEseguita.setUtente(dfpTT.getOperatore());
					datamatrixTrattamentoFaseEseguita.setDurezza(dfpTT.getValoreDurezza().toString()); 
					datamatrixTrattamentoFaseEseguita.setNumeroFornata(dfpTT.getNumeroFornata());
					datamatrixTrattamentoFaseEseguita.setFornitore(dfp.getUtenteOperatore().getAzienda().getRagioneSociale());
				}
				
				listaFasiTrattamentoEseguite.add(datamatrixTrattamentoFaseEseguita);
			}
		}
		
		//Packing List
		List<DatamatrixFasiEseguite> listaPackingListEseguite = new ArrayList<DatamatrixFasiEseguite>();
		if(vistaPackingList != null) {
			for(VistaPackingList dfp : vistaPackingList) {
				DatamatrixFasiEseguite datamatrixPackingListaFaseEseguita = new DatamatrixFasiEseguite();;
				
				datamatrixPackingListaFaseEseguita.setDataMatrix(dfp.getDataMatrix());
				datamatrixPackingListaFaseEseguita.setIdDataMatrix(dfp.getIdDataMatrix());
				datamatrixPackingListaFaseEseguita.setDataOra(dfp.getDataOraCreazioneEtichettaPezzo());
				datamatrixPackingListaFaseEseguita.setFaseProcesso("Packing list");
				datamatrixPackingListaFaseEseguita.setCodiceFaseProcesso(FasiProcessoUtils.FasiProcessoLista.PACKINGLISTCCU.toString());
				datamatrixPackingListaFaseEseguita.setImpianto(dfp.getNomeCognome());
				datamatrixPackingListaFaseEseguita.setNote(dfp.getCodiceEtichetta());
				
				listaPackingListEseguite.add(datamatrixPackingListaFaseEseguita);
				
			}
		}
		
		//Prova tenuta
		List<DatamatrixFasiEseguite> fasiProcessoProveTenutaEseguite = RepositoryProvider.getRepositoryProveTenuta().getDatamatrixProveTenutaEseguite(codiceDatamatrix);
		
		Map<String, List<DatamatrixFasiEseguite>> mappaListaFasiEseguite = new HashMap<>();
		mappaListaFasiEseguite.put(TipoDatamatrixFasiEseguite.FASI_PROCESSO.toString(), listaFasiEseguite);
		mappaListaFasiEseguite.put(TipoDatamatrixFasiEseguite.TRATTAMENTO_TERMICO.toString(), listaFasiTrattamentoEseguite);
		mappaListaFasiEseguite.put(TipoDatamatrixFasiEseguite.PROVE_TENUTA.toString(), fasiProcessoProveTenutaEseguite);
		mappaListaFasiEseguite.put(TipoDatamatrixFasiEseguite.PACKING_LIST.toString(), listaPackingListEseguite);
		
		//Fusione/Laser
		
		return mappaListaFasiEseguite;
	}
		
	public enum TipoDatamatrixFasiEseguite {
		FASI_PROCESSO, 
		TRATTAMENTO_TERMICO,
		PACKING_LIST,
		FUSIONE_LASER,
		PROVE_TENUTA;
	}
}
