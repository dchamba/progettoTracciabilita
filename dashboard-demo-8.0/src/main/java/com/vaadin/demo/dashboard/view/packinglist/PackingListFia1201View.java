  package com.vaadin.demo.dashboard.view.packinglist;

import com.vaadin.demo.dashboard.component.utils.ViewUtils;
import com.vaadin.demo.dashboard.component.utils.PermessiUtils.PermessiUtentiLista;
import com.vaadin.demo.dashboard.component.utils.ProdottiUtils.CodiciProdottiLista;
import com.vaadin.demo.dashboard.data.model.EtichetteImballi;
import com.vaadin.demo.dashboard.data.model.VistaPackingList;
import com.vaadin.ui.Grid;

@SuppressWarnings("serial")
public final class PackingListFia1201View extends PackingListViewSingleCode {
	
	public PackingListFia1201View() { }

	@Override
    EtichetteImballi getVariabileEtichettaImballo(String codiceProdotto) {
		if(codiceProdotto.equals(CodiciProdottiLista.FIA001201.toString())) {
			return this.etichettaImballoSingleCode;
		} 
		return null;
	}

	@Override
	public String getNumeroDisegnoDaVericareInImballo(String codiceDataMatrixInserito) {
		return "670053447";
	}
	
	@Override
	String getStringPermessoPackingList() {
		return PermessiUtentiLista.FASE_PROCESSO_PACKINGLISTFIA1201.toString();
	}
	
	@Override
	String getTitoloPagina() {
		return ViewUtils.titoloFaseProcessoPackingListFia1201;
	}
	
	@Override
	String getCodiceProdottoString() {
		return CodiciProdottiLista.FIA001201.toString();
	}
}
