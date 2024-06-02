  package com.vaadin.demo.dashboard.view.packinglist;

import com.vaadin.demo.dashboard.component.utils.PermessiUtils.PermessiUtentiLista;
import com.vaadin.demo.dashboard.component.utils.ProdottiUtils.CodiciProdottiLista;
import com.vaadin.demo.dashboard.data.model.EtichetteImballi;
import com.vaadin.demo.dashboard.data.model.VistaPackingList;
import com.vaadin.ui.Grid;

@SuppressWarnings("serial")
public final class PackingListOMR0050View extends PackingListViewSingleCode {
	
	public PackingListOMR0050View() { }

	@Override
	void initializeGrid() {
		gridSingleCode = new Grid<VistaPackingList>();
	}

	@Override
    EtichetteImballi getVariabileEtichettaImballo(String codiceProdotto) {
		if(codiceProdotto.equals(CodiciProdottiLista.OMR0050.toString())) {
			return this.etichettaImballoSingleCode;
		} 
		return null;
	}

	@Override
	String getStringPermessoPackingList() {
		return PermessiUtentiLista.FASE_PROCESSO_PACKINGLISTOMR50.toString();
	}
}
