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
public final class PackingListGestampView extends PackingListView {

	private EtichetteImballi etichettaImballoGes001, etichettaImballoGes002;
	
	private Label lableQtaPzGes001, lableQtaPzGes002;
	private Label lableEtichettaGes001, lableEtichettaGes002;
	
	public PackingListGestampView() { }

	@Override
	void initializeGrid() {
		gridGes1 = new Grid<VistaPackingList>();
		gridGes2 = new Grid<VistaPackingList>();
	}

	@Override
    void buildDatamatrixForm() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName(StyleUtils.viewHeaderStyle);
        Responsive.makeResponsive(header);
        
    	this.gridGes1 = new Grid<VistaPackingList>();
    	this.gridGes2 = new Grid<VistaPackingList>();
        
        Label title = new Label(ViewUtils.titoloFaseProcessoPackingListGestamp);
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
    	VerticalLayout layoutPezziScatolaGes2 = new VerticalLayout();
//    	layoutPezziScatolaGes2.setWidth("400px");

    	Label titleGes001 = new Label("GES001");
        setPezziScatolaGridTitleStyle(titleGes001);        
        Label titleGes002 = new Label("GES002");
        setPezziScatolaGridTitleStyle(titleGes002);
        
    	layoutPezziScatolaGes1.addComponent(titleGes001);
    	layoutPezziScatolaGes2.addComponent(titleGes002);
    	
    	lableEtichettaGes001 = new Label("Cod.etic.:");
        setInfoScatolaStyle(lableEtichettaGes001);        
        lableEtichettaGes002 = new Label("Cod.etic.:");
        setInfoScatolaStyle(lableEtichettaGes002);
    	layoutPezziScatolaGes1.addComponent(lableEtichettaGes001);
    	layoutPezziScatolaGes2.addComponent(lableEtichettaGes002);

    	addColumnToGrid(gridGes1);
    	addColumnToGrid(gridGes2);
    	layoutPezziScatolaGes1.addComponent(gridGes1);
    	layoutPezziScatolaGes2.addComponent(gridGes2);

        lableQtaPzGes001 = new Label("Qtà pz in scatola: 0");
        setInfoScatolaStyle(lableQtaPzGes001);        
        lableQtaPzGes002 = new Label("Qtà pz in scatola: 0");
        setInfoScatolaStyle(lableQtaPzGes002);
        
    	layoutPezziScatolaGes1.addComponent(lableQtaPzGes001);
    	layoutPezziScatolaGes2.addComponent(lableQtaPzGes002);

        HorizontalLayout pezziScatolaHorizontalLayout = new HorizontalLayout();
        pezziScatolaHorizontalLayout.addComponent(layoutPezziScatolaGes1);
        pezziScatolaHorizontalLayout.addComponent(layoutPezziScatolaGes2);
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

    	if(this.etichettaImballoGes001 != null) {
    		gridGes1.setItems(new ArrayList<VistaPackingList>());
    		gridGes1.setItems(this.repositoryImballi.getVistaPackingListFromEtichettaScatola(null, this.etichettaImballoGes001.getIdEtichettaImballo()));

    		lableEtichettaGes001.setValue("Cod.etic.:"+ this.etichettaImballoGes001.getCodiceEtichettaImballoSmeup());

        	ListDataProvider<VistaPackingList> dataProvider = (ListDataProvider<VistaPackingList>) gridGes1.getDataProvider();
    		lableQtaPzGes001.setValue("Qtà pz in scatola:  " + String.valueOf(dataProvider.getItems().size()));
    	}

    	if(this.etichettaImballoGes002 != null) {
    		gridGes2.setItems(new ArrayList<VistaPackingList>());
    		gridGes2.setItems(this.repositoryImballi.getVistaPackingListFromEtichettaScatola(null, this.etichettaImballoGes002.getIdEtichettaImballo()));

    		lableEtichettaGes002.setValue("Cod.etic.:"+ this.etichettaImballoGes002.getCodiceEtichetta().substring(0, 10));

        	ListDataProvider<VistaPackingList> dataProvider = (ListDataProvider<VistaPackingList>) gridGes2.getDataProvider();
    		lableQtaPzGes002.setValue("Qtà pz in scatola:  " + String.valueOf(dataProvider.getItems().size()));
    	}
    }

	@Override
    void aggiornaVariabileEtichettaImballo(EtichetteImballi etichettaImballo) {
		if(etichettaImballo.getTipoImballo().getProdotto().getCodiceProdotto().equals(CodiciProdottiLista.GES0001.toString())) {
			this.etichettaImballoGes001 = etichettaImballo;
		} else if(etichettaImballo.getTipoImballo().getProdotto().getCodiceProdotto().equals(CodiciProdottiLista.GES0002.toString())) {
			this.etichettaImballoGes002 = etichettaImballo;
		} 
	}

	@Override
    EtichetteImballi getVariabileEtichettaImballo(String codiceProdotto) {
		if(codiceProdotto.equals(CodiciProdottiLista.GES0001.toString())) {
			return this.etichettaImballoGes001;
		} else if(codiceProdotto.equals(CodiciProdottiLista.GES0002.toString())) {
			return this.etichettaImballoGes002;
		} else {
			return null;
		}
		
	}

	@Override
	String getStringPermessoPackingList() {
		return PermessiUtentiLista.FASE_PROCESSO_PACKINGLISTGESTAMP.toString();
	}

	@Override
	int getPzMancantiInScatolaAttualeRispettoQuellaNuova(Prodotti prodottoEtichettaDaVerificare) {
        int pzMancantiInScatola = 0 ;
		if(prodottoEtichettaDaVerificare.getCodiceProdotto().equals(CodiciProdottiLista.GES0001.toString())) {
			if(this.etichettaImballoGes001 == null) return -1;
			int totPzInScatola = ((ListDataProvider<VistaPackingList>) gridGes1.getDataProvider()).getItems().size();
			pzMancantiInScatola = this.etichettaImballoGes001.getTipoImballo().getQtaPezziPerScatola() - totPzInScatola;
		} else if(prodottoEtichettaDaVerificare.getCodiceProdotto().equals(CodiciProdottiLista.GES0002.toString())) {
			if(this.etichettaImballoGes002 == null) return -1;
			int totPzInScatola = ((ListDataProvider<VistaPackingList>) gridGes2.getDataProvider()).getItems().size();
			pzMancantiInScatola = this.etichettaImballoGes002.getTipoImballo().getQtaPezziPerScatola() - totPzInScatola;
		}
		return pzMancantiInScatola;
	}
}
