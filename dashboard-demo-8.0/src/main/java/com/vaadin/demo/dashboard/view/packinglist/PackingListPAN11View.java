 package com.vaadin.demo.dashboard.view.packinglist;

import com.vaadin.demo.dashboard.component.utils.ViewUtils;
import com.vaadin.demo.dashboard.component.utils.PermessiUtils.PermessiUtentiLista;
import com.vaadin.demo.dashboard.component.utils.ProdottiUtils.CodiciProdottiLista;
import com.vaadin.demo.dashboard.data.model.EtichetteImballi;
import com.vaadin.demo.dashboard.data.model.VistaPackingList;
import com.vaadin.ui.Grid;

@SuppressWarnings("serial")
public final class PackingListPAN11View extends PackingListViewSingleCode {
	
	public PackingListPAN11View() { }

	@Override
	void initializeGrid() {
		gridPan11 = new Grid<VistaPackingList>();
	}

	@Override
    EtichetteImballi getVariabileEtichettaImballo(String codiceProdotto) {
		if(codiceProdotto.equals(CodiciProdottiLista.PAN0011.toString())) {
			return this.etichettaImballoSingleCode;
		} 
		return null;
	}

	@Override
	String getStringPermessoPackingList() {
		return PermessiUtentiLista.FASE_PROCESSO_PACKINGLISTACFSHIELD.toString();
	}
	
	@Override
	public String getCodiceProdotto() {
		return CodiciProdottiLista.PAN0011.toString();
	}
	
	@Override
	public String getTitoloPaginaPackigList() {
		return ViewUtils.titoloFaseProcessoPackingListPan11;
	}
}
