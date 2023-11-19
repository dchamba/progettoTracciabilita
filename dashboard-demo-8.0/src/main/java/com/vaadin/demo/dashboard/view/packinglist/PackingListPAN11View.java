  package com.vaadin.demo.dashboard.view.packinglist;

import java.util.ArrayList;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.demo.dashboard.component.style.StyleUtils;
import com.vaadin.demo.dashboard.component.utils.PermessiUtils.PermessiUtentiLista;
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

@SuppressWarnings("serial")
public final class PackingListPAN11View extends PackingListView {

	private EtichetteImballi etichettaImballoPan11;
	
	private Label lableQtaPzPan11;
	private Label lableEtichettaPan11;
	
	public PackingListPAN11View() { }

	@Override
	void initializeGrid() {
		gridPan11 = new Grid<VistaPackingList>();
	}

	@Override
    void buildDatamatrixForm() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName(StyleUtils.viewHeaderStyle);
        Responsive.makeResponsive(header);
        
    	this.gridPan11 = new Grid<VistaPackingList>();
        
        Label title = new Label(ViewUtils.titoloFaseProcessoPackingListPan11);
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

    	Label titleGes001 = new Label("PAN0011");
        setPezziScatolaGridTitleStyle(titleGes001);    
        
    	layoutPezziScatolaGes1.addComponent(titleGes001);
    	
    	lableEtichettaPan11 = new Label("Cod.etic.:");
        setInfoScatolaStyle(lableEtichettaPan11);        
    	layoutPezziScatolaGes1.addComponent(lableEtichettaPan11);

    	addColumnToGrid(gridPan11);
    	layoutPezziScatolaGes1.addComponent(gridPan11);

        lableQtaPzPan11 = new Label("Qtà pz in scatola: 0");
        setInfoScatolaStyle(lableQtaPzPan11);
        
    	layoutPezziScatolaGes1.addComponent(lableQtaPzPan11);

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
	
	@Override
    void aggiornaDatiImballi() {

    	if(this.etichettaImballoPan11 != null) {
    		gridPan11.setItems(new ArrayList<VistaPackingList>());
    		gridPan11.setItems(this.repositoryImballi.getVistaPackingListFromEtichettaScatola(null, this.etichettaImballoPan11.getIdEtichettaImballo()));

    		lableEtichettaPan11.setValue("Cod.etic.:"+ this.etichettaImballoPan11.getCodiceEtichetta().substring(0, 10));

        	ListDataProvider<VistaPackingList> dataProvider = (ListDataProvider<VistaPackingList>) gridPan11.getDataProvider();
    		lableQtaPzPan11.setValue("Qtà pz in scatola:  " + String.valueOf(dataProvider.getItems().size()));
    	}
    }

	@Override
    void aggiornaVariabileEtichettaImballo(EtichetteImballi etichettaImballo) {
		if(etichettaImballo.getTipoImballo().getProdotto().getCodiceProdotto().equals(CodiciProdottiLista.PAN0011.toString())) {
			this.etichettaImballoPan11 = etichettaImballo;
		} 
	}

	@Override
    EtichetteImballi getVariabileEtichettaImballo(String codiceProdotto) {
		if(codiceProdotto.equals(CodiciProdottiLista.PAN0011.toString())) {
			return this.etichettaImballoPan11;
		} 
		return null;
		
	}

	@Override
	String getStringPermessoPackingList() {
		return PermessiUtentiLista.FASE_PROCESSO_PACKINGLISTACFSHIELD.toString();
	}

	@Override
	int getPzMancantiInScatolaAttualeRispettoQuellaNuova(Prodotti prodottoEtichettaDaVerificare) {
		if(this.etichettaImballoPan11 == null) return -1;
		int totPzInScatola = ((ListDataProvider<VistaPackingList>) gridPan11.getDataProvider()).getItems().size();
		int pzMancantiInScatola = this.etichettaImballoPan11.getTipoImballo().getQtaPezziPerScatola() - totPzInScatola;
		return pzMancantiInScatola;
	}
}
