package com.vaadin.demo.dashboard.view.packinglist;

import java.util.ArrayList;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.demo.dashboard.component.style.StyleUtils;
import com.vaadin.demo.dashboard.component.utils.ProdottiUtils.CodiciProdottiLista;
import com.vaadin.demo.dashboard.component.utils.ViewUtils;
import com.vaadin.demo.dashboard.data.model.EtichetteImballi;
import com.vaadin.demo.dashboard.data.model.Prodotti;
import com.vaadin.demo.dashboard.data.model.VistaPackingList;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class PackingListViewSingleCode extends PackingListView {
	
	Label lableEtichettaImballoSingleCode = null, lableQtaPzSingleCode = null;
	EtichetteImballi etichettaImballoSingleCode;

	Grid<VistaPackingList> gridSingleCode = new Grid<VistaPackingList>();
	
	@Override
	void aggiornaDatiImballi() {
    	if(this.etichettaImballoSingleCode != null) {
    		gridSingleCode.setItems(new ArrayList<VistaPackingList>());
    		gridSingleCode.setItems(this.repositoryImballi.getVistaPackingListFromEtichettaScatola(null, this.etichettaImballoSingleCode.getIdEtichettaImballo()));

    		lableEtichettaImballoSingleCode.setValue("Cod.etic.:"+ this.etichettaImballoSingleCode.getCodiceEtichettaImballoSmeup());

        	ListDataProvider<VistaPackingList> dataProvider = (ListDataProvider<VistaPackingList>) gridSingleCode.getDataProvider();
    		lableQtaPzSingleCode.setValue("Qtà pz in scatola:  " + String.valueOf(dataProvider.getItems().size()));
    	}
	}

	@Override
	int getPzMancantiInScatolaAttualeRispettoQuellaNuova(Prodotti prodottoEtichettaDaVerificare) {
		if(this.etichettaImballoSingleCode == null) return -1;
		int totPzInScatola = ((ListDataProvider<VistaPackingList>) gridSingleCode.getDataProvider()).getItems().size();
		int pzMancantiInScatola = this.etichettaImballoSingleCode.getTipoImballo().getQtaPezziPerScatola() - totPzInScatola;
		return pzMancantiInScatola;
	}
	
	@Override
    void aggiornaVariabileEtichettaImballo(EtichetteImballi etichettaImballo) {
		if(etichettaImballo.getTipoImballo().getProdotto().getCodiceProdotto().equals(getCodiceProdotto())) {
			this.etichettaImballoSingleCode = etichettaImballo;
		} 
	}
	
	public String getCodiceProdotto() { return "";}
	public String getTitoloPaginaPackigList() { return "";}

	@Override
	void buildDatamatrixForm() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName(StyleUtils.viewHeaderStyle);
        Responsive.makeResponsive(header);
        
    	this.gridSingleCode = new Grid<VistaPackingList>();
        
        Label title = new Label(getTitoloPaginaPackigList());
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);
        
        header.setHeight("90px");
	    
    	textDatamatrix = new TextField("Datamatrix");  
    	textDatamatrix.setPlaceholder("Datamatrix");
    	textDatamatrix.addValueChangeListener(new ValueChangeListener<String>() {
			
			@Override
			public void valueChange(ValueChangeEvent<String> event) {
				String codiceDataMatrixInserito = textDatamatrix.getValue().trim();

		    	textDatamatrix.setComponentError(null);
				if (codiceDataMatrixInserito.isEmpty()) {
					return;
				}
				checkAndSaveDatamatrix(codiceDataMatrixInserito);
						
		    	textDatamatrix.setValue("");
		    	textDatamatrix.setComponentError(null);
			}
		});
    	textDatamatrix.setHeight("65px");
    	textDatamatrix.setWidth("700px");

    	
    	VerticalLayout layoutDatamatrix = new VerticalLayout();
    	layoutDatamatrix.setSizeFull();
    	layoutDatamatrix.addComponents(textDatamatrix);
    	layoutDatamatrix.setComponentAlignment(textDatamatrix, Alignment.MIDDLE_CENTER);

    	VerticalLayout layoutPezziScatolaGes1 = new VerticalLayout();
//    	layoutPezziScatolaGes1.setWidth("400px");

    	Label titleGes001 = new Label(getCodiceProdotto());
        setPezziScatolaGridTitleStyle(titleGes001);    
        
    	layoutPezziScatolaGes1.addComponent(titleGes001);
    	
    	lableEtichettaImballoSingleCode = new Label("Cod.etic.:");
        setInfoScatolaStyle(lableEtichettaImballoSingleCode);        
    	layoutPezziScatolaGes1.addComponent(lableEtichettaImballoSingleCode);

    	addColumnToGrid(gridSingleCode);
    	layoutPezziScatolaGes1.addComponent(gridSingleCode);

    	lableQtaPzSingleCode = new Label("Qtà pz in scatola: 0");
        setInfoScatolaStyle(lableQtaPzSingleCode);
    	layoutPezziScatolaGes1.addComponent(lableQtaPzSingleCode);

        HorizontalLayout pezziScatolaHorizontalLayout = new HorizontalLayout();
        pezziScatolaHorizontalLayout.addComponent(layoutPezziScatolaGes1);
        pezziScatolaHorizontalLayout.setSizeFull();

        VerticalLayout fields = new VerticalLayout();
        fields.setSizeFull();
        fields.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        addComponent(header);
        addComponents(layoutDatamatrix);
        addComponents(pezziScatolaHorizontalLayout);
        
        setExpandRatio(header, 0.1f);
        setExpandRatio(layoutDatamatrix, 0.2f);
        setExpandRatio(pezziScatolaHorizontalLayout, 0.7f);
        
        
        addComponent(fields);
    	//setExpandRatio(fields, 8);
	}
}
