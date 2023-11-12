package com.vaadin.demo.dashboard.view.packinglist;

import com.vaadin.demo.dashboard.component.utils.PermessiUtils.PermessiUtentiLista;
import com.vaadin.demo.dashboard.component.utils.ViewUtils;
import com.vaadin.demo.dashboard.data.model.VistaPackingList;
import com.vaadin.ui.Grid;

@SuppressWarnings("serial")
public class PackingListFia0505 extends PackingListFia05 {

	@Override
	void initializeGrid() {
		gridFia05 = new Grid<VistaPackingList>();
	}

	@Override
	String getStringPermessoPackingList() {
		return PermessiUtentiLista.FASE_PROCESSO_PACKINGLISTFIA0505.toString();
	}

	@Override 
	String getTitoloFasePackingListFia05() {
		return ViewUtils.titoloFaseProcessoPackingListFia0505;
	}
	
	@Override
	Integer getIdEtichettaImballoVuota() {
		return -1;
	}
	
	@Override
	String getTitoloGriglia() {
		return "FIA00505";
	}
}


