package com.vaadin.demo.dashboard.view.packinglist;

import java.util.ArrayList;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.demo.dashboard.component.style.StyleUtils;
import com.vaadin.demo.dashboard.component.utils.ViewUtils;
import com.vaadin.demo.dashboard.component.utils.PermessiUtils.PermessiUtentiLista;
import com.vaadin.demo.dashboard.data.model.EtichetteImballi;
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
public class PackingListFia10 extends PackingListView {

	private EtichetteImballi etichettaImballoFia10;
	
	private Label lableQtaPzFia10;
	private Label lableEtichettaFia10;


	public PackingListFia10() { }

	@Override
	void initializeGrid() {
		gridFia05 = new Grid<VistaPackingList>();
	}

	@Override
    void buildDatamatrixForm() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName(StyleUtils.viewHeaderStyle);
        Responsive.makeResponsive(header);
        
        //this.gridFia10 = new Grid<VistaPackingList>();

        Label title = new Label(ViewUtils.titoloFaseProcessoPackingListFia10);
        //title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);
        
        header.setHeight("90px");
        //addComponent(header);
        //setExpandRatio(header, 0);

//    	layoutCampiDatamatrix = new VerticalLayout();
//    	layoutCampiDatamatrix.setSizeFull();
    	
//    	MarginInfo marginInfo = new MarginInfo(false, false, true, false);
//    	marginInfo.do(10, 10, 20, 10);
//    	fields.setMargin(marginInfo);
	    
    	textDatamatrix = new TextField("QRCode");  
    	textDatamatrix.setPlaceholder("QRCode");
    	//textDatamatrix.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
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

    	VerticalLayout layoutPezziScatolaFia10 = new VerticalLayout();
    	//layoutPezziScatolaFia10.setWidth("800px");
    	//layoutPezziScatolaFia10.setSizeFull();

    	Label titleFia10 = new Label("FIA010");
        setPezziScatolaGridTitleStyle(titleFia10);    
        
    	layoutPezziScatolaFia10.addComponent(titleFia10);
    	
    	lableEtichettaFia10 = new Label("Cod.etic.:");
        setInfoScatolaStyle(lableEtichettaFia10);
        
    	layoutPezziScatolaFia10.addComponent(lableEtichettaFia10);

    	addColumnToGrid(gridFia05);
    	
    	layoutPezziScatolaFia10.addComponent(gridFia05);

        lableQtaPzFia10 = new Label("Qtà pz in scatola: 0");
        setInfoScatolaStyle(lableQtaPzFia10);
        
    	layoutPezziScatolaFia10.addComponent(lableQtaPzFia10);

        HorizontalLayout pezziScatolaHorizontalLayout = new HorizontalLayout();
        pezziScatolaHorizontalLayout.addComponent(layoutPezziScatolaFia10);
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
    	if(this.etichettaImballoFia10 != null) {
    		gridFia05.setItems(new ArrayList<VistaPackingList>());
    		gridFia05.setItems(this.repositoryImballi.getVistaPackingListFromEtichettaScatola(null, this.etichettaImballoFia10.getIdEtichettaImballo()));

    		lableEtichettaFia10.setValue("Cod.etic.:"+ this.etichettaImballoFia10.getCodiceEtichetta().substring(0, 10));

        	ListDataProvider<VistaPackingList> dataProvider = (ListDataProvider<VistaPackingList>) this.gridFia05.getDataProvider();
    		lableQtaPzFia10.setValue("Qtà pz in scatola:  " + String.valueOf(dataProvider.getItems().size()));
    	}
    }

	@Override
    void aggiornaVariabileEtichettaImballo(EtichetteImballi etichettaImballo) {
		this.etichettaImballoFia10 = etichettaImballo;
	}

	@Override
    EtichetteImballi getVariabileEtichettaImballo(String codiceProdotto) {
		return this.etichettaImballoFia10;
	}

	@Override
	String getStringPermessoPackingList() {
		return PermessiUtentiLista.FASE_PROCESSO_PACKINGLISTFIA10.toString();
	}
}
