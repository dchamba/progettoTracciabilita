  package com.vaadin.demo.dashboard.view.packinglist;

import java.util.ArrayList;
import java.util.regex.Pattern;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.demo.dashboard.component.model.StatoBancale;
import com.vaadin.demo.dashboard.component.style.StyleUtils;
import com.vaadin.demo.dashboard.component.utils.PermessiUtils.PermessiUtentiLista;
import com.vaadin.demo.dashboard.component.utils.ProdottiUtils.CodiciProdottiLista;
import com.vaadin.demo.dashboard.component.utils.CommonUtils;
import com.vaadin.demo.dashboard.component.utils.CustomPopupWindow;
import com.vaadin.demo.dashboard.component.utils.ViewUtils;
import com.vaadin.demo.dashboard.component.view.DatamatrixScartoComponentFactory;
import com.vaadin.demo.dashboard.component.view.SegmentedProgressBar;
import com.vaadin.demo.dashboard.data.model.Datamatrix;
import com.vaadin.demo.dashboard.data.model.EtichetteImballi;
import com.vaadin.demo.dashboard.data.model.Prodotti;
import com.vaadin.demo.dashboard.data.model.TipoImballi;
import com.vaadin.demo.dashboard.data.model.VistaPackingList;
import com.vaadin.demo.dashboard.view.scarto.ScartoWindow;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.TextRenderer;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;

@SuppressWarnings("serial")
public final class PackingListCCUView extends PackingListView {

	private EtichetteImballi etichettaImballoPan003, etichettaImballoPan004, etichettaImballoPan005, etichettaImballoPan010;
	
	private Label lableQtaPzPan003, lableQtaPzPan004, lableQtaPzPan005, lableQtaPzPan010;
	private Label lableEtichettaPan003, lableEtichettaPan004, lableEtichettaPan005, lableEtichettaPan010;
	private Label lableBancalePan003, lableBancalePan004, lableBancalePan010;
		
	public PackingListCCUView() { }
	
	@Override
	void initializeVariables() {
		this.tipoImballoPan003 = this.listaTipoImballi.stream().filter(v -> v.getProdotto().getCodiceProdotto().equals(CodiciProdottiLista.PAN0003.toString())).findFirst().get();
		this.tipoImballoPan004 = this.listaTipoImballi.stream().filter(v -> v.getProdotto().getCodiceProdotto().equals(CodiciProdottiLista.PAN0004.toString())).findFirst().get();
		this.tipoImballoPan010 = this.listaTipoImballi.stream().filter(v -> v.getProdotto().getCodiceProdotto().equals(CodiciProdottiLista.PAN0010.toString())).findFirst().get();

		this.progressBarScatolaPan003 = new SegmentedProgressBar(tipoImballoPan003.getQtaPezziPerScatola());
		this.progressBarScatolaPan004 = new SegmentedProgressBar(tipoImballoPan004.getQtaPezziPerScatola());
		this.progressBarScatolaPan010 = new SegmentedProgressBar(tipoImballoPan010.getQtaPezziPerScatola());
		
		this.progressBarBancalePan003 = new SegmentedProgressBar(tipoImballoPan003.getQtaScatolePerBancale());
		this.progressBarBancalePan004 = new SegmentedProgressBar(tipoImballoPan004.getQtaScatolePerBancale());
		this.progressBarBancalePan010 = new SegmentedProgressBar(tipoImballoPan010.getQtaScatolePerBancale());
	}

	@Override
    void buildDatamatrixForm() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName(StyleUtils.viewHeaderStyle);
        Responsive.makeResponsive(header);
        
    	this.gridPan3 = new Grid<VistaPackingList>();
    	this.gridPan4 = new Grid<VistaPackingList>();
    	//this.gridPan5 = new Grid<VistaPackingList>();
    	this.gridPan10 = new Grid<VistaPackingList>();
        
        Label title = new Label(ViewUtils.titoloFaseProcessoPackingListCCU);
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
	    
        datamatrixScartoComponent = DatamatrixScartoComponentFactory.create("QRCode", new DatamatrixScartoComponentFactory.ModalitaScartoHandler() {
                    @Override
                    public void onChange(boolean attiva) {
                    	//PackingListCCUView.super.modalitaScarto = attiva;
                    }

                    @Override
                    public void onDatamatrixInserito(String codice) {
                    	datamatrixScartoComponent.textDatamatrix.setComponentError(null);
                        checkAndSaveDatamatrix(codice); // o il metodo equivalente della view
                        datamatrixScartoComponent.textDatamatrix.setComponentError(null);
                    }
                });

        VerticalLayout layoutDatamatrix = new VerticalLayout();
        layoutDatamatrix.setSizeFull();
        layoutDatamatrix.addComponent(datamatrixScartoComponent.layout);
        layoutDatamatrix.setComponentAlignment(datamatrixScartoComponent.layout, Alignment.MIDDLE_CENTER);

    	VerticalLayout layoutPezziScatolaPan3 = new VerticalLayout();
//    	layoutPezziScatolaPan3.setWidth("400px");
    	VerticalLayout layoutPezziScatolaPan4 = new VerticalLayout();
//    	layoutPezziScatolaPan4.setWidth("400px");
//    	VerticalLayout layoutPezziScatolaPan5 = new VerticalLayout();
//    	layoutPezziScatolaPan5.setWidth("400px");
    	VerticalLayout layoutPezziScatolaPan10 = new VerticalLayout();

    	Label titlePan003 = new Label("PAN003");
        setPezziScatolaGridTitleStyle(titlePan003);        
        Label titlePan004 = new Label("PAN004");
        setPezziScatolaGridTitleStyle(titlePan004);
//      Label titlePan005 = new Label("PAN005");
//      setPezziScatolaGridTitleStyle(titlePan005);
        Label titlePan010 = new Label("PAN010");
        setPezziScatolaGridTitleStyle(titlePan010);
        
    	layoutPezziScatolaPan3.addComponent(titlePan003);
    	layoutPezziScatolaPan4.addComponent(titlePan004);
//    	layoutPezziScatolaPan5.addComponent(titlePan005);
    	layoutPezziScatolaPan10.addComponent(titlePan010);

    	//Label codice etichetta scatola
    	lableEtichettaPan003 = new Label("Cod.etic.:");
        setInfoScatolaStyle(lableEtichettaPan003);        
        lableEtichettaPan004 = new Label("Cod.etic.:");
        setInfoScatolaStyle(lableEtichettaPan004);
//      lableEtichettaPan005 = new Label("Cod.etic.:");
//      setInfoScatolaStyle(lableEtichettaPan005);
        lableEtichettaPan010 = new Label("Cod.etic.:");
        setInfoScatolaStyle(lableEtichettaPan010);
    	layoutPezziScatolaPan3.addComponent(lableEtichettaPan003);
    	layoutPezziScatolaPan4.addComponent(lableEtichettaPan004);
//    	layoutPezziScatolaPan5.addComponent(lableEtichettaPan005);
    	layoutPezziScatolaPan10.addComponent(lableEtichettaPan010);


    	//Label qtà pezzi
        lableQtaPzPan003 = new Label("Qtà pz in scatola: 0");
        setInfoScatolaStyle(lableQtaPzPan003);    
        lableQtaPzPan003.addStyleName(ValoTheme.LABEL_H4);    
        lableQtaPzPan004 = new Label("Qtà pz in scatola: 0");
        setInfoScatolaStyle(lableQtaPzPan004);
        lableQtaPzPan004.addStyleName(ValoTheme.LABEL_H4); 
//      lableQtaPzPan005 = new Label("Qtà pz in scatola: 0");
//      setInfoScatolaStyle(lableQtaPzPan005);
        lableQtaPzPan010 = new Label("Qtà pz in scatola: 0");
        setInfoScatolaStyle(lableQtaPzPan010);
        lableQtaPzPan010.addStyleName(ValoTheme.LABEL_H4); 

    	layoutPezziScatolaPan3.addComponent(lableQtaPzPan003);
    	layoutPezziScatolaPan4.addComponent(lableQtaPzPan004);
//    	layoutPezziScatolaPan5.addComponent(lableQtaPzPan005);
    	layoutPezziScatolaPan10.addComponent(lableQtaPzPan010);
    	
    	//Progressbar
    	progressBarScatolaPan003.setAllSegmentsColor(SegmentedProgressBar.COLOR_VERDE);
    	progressBarScatolaPan004.setAllSegmentsColor(SegmentedProgressBar.COLOR_VERDE);
    	progressBarScatolaPan010.setAllSegmentsColor(SegmentedProgressBar.COLOR_VERDE);

    	layoutPezziScatolaPan3.addComponent(progressBarScatolaPan003);
    	layoutPezziScatolaPan4.addComponent(progressBarScatolaPan004);
    	layoutPezziScatolaPan10.addComponent(progressBarScatolaPan010);
    	
    	//Grid lista datamatix
    	addColumnToGrid(gridPan3);
    	addColumnToGrid(gridPan4);
//    	addColumnToGrid(gridPan5);
    	addColumnToGrid(gridPan10);
    	layoutPezziScatolaPan3.addComponent(gridPan3);
    	layoutPezziScatolaPan4.addComponent(gridPan4);
//    	layoutPezziScatolaPan5.addComponent(gridPan5);
    	layoutPezziScatolaPan10.addComponent(gridPan10);

        HorizontalLayout pezziScatolaHorizontalLayout = new HorizontalLayout();
        pezziScatolaHorizontalLayout.addComponent(layoutPezziScatolaPan3);
        pezziScatolaHorizontalLayout.addComponent(layoutPezziScatolaPan4);
//      pezziScatolaHorizontalLayout.addComponent(layoutPezziScatolaPan5);
        pezziScatolaHorizontalLayout.addComponent(layoutPezziScatolaPan10);
        pezziScatolaHorizontalLayout.setSizeFull();

        
        //Layout bancale
        
    	lableBancalePan003 = new Label("Cod. bancale:");
        setInfoScatolaStyle(lableBancalePan003);        
        lableBancalePan004 = new Label("Cod. bancale:");
        setInfoScatolaStyle(lableBancalePan004);
        lableBancalePan010 = new Label("Cod. bancale:");
        setInfoScatolaStyle(lableBancalePan010);
        
    	progressBarBancalePan003.setAllSegmentsColor(SegmentedProgressBar.COLOR_VERDE);
    	progressBarBancalePan004.setAllSegmentsColor(SegmentedProgressBar.COLOR_VERDE);
    	progressBarBancalePan010.setAllSegmentsColor(SegmentedProgressBar.COLOR_VERDE);

    	VerticalLayout layoutBancalePan3 = new VerticalLayout();
    	VerticalLayout layoutBancalePan4 = new VerticalLayout();
    	VerticalLayout layoutBancalePan10 = new VerticalLayout();
    	
    	layoutBancalePan3.addComponent(lableBancalePan003);
    	layoutBancalePan4.addComponent(lableBancalePan004);
    	layoutBancalePan10.addComponent(lableBancalePan010);

    	layoutPezziScatolaPan3.addComponent(progressBarBancalePan003);
    	layoutPezziScatolaPan4.addComponent(progressBarBancalePan004);
    	layoutPezziScatolaPan10.addComponent(progressBarBancalePan010);
        
        HorizontalLayout bancaleHorizontalLayout = new HorizontalLayout();
        bancaleHorizontalLayout.addComponent(layoutBancalePan3);
        bancaleHorizontalLayout.addComponent(layoutBancalePan4);
        bancaleHorizontalLayout.addComponent(layoutBancalePan10);
        bancaleHorizontalLayout.setSizeFull();
        
        //Layout 
        VerticalLayout fields = new VerticalLayout();
        fields.setSizeFull();
        fields.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        addComponent(header);
        addComponents(layoutDatamatrix);
        addComponents(pezziScatolaHorizontalLayout);
        addComponents(bancaleHorizontalLayout);
        
        setExpandRatio(header, 0.05f);
        setExpandRatio(layoutDatamatrix, 0.2f);
        setExpandRatio(pezziScatolaHorizontalLayout, 0.65f);
        setExpandRatio(bancaleHorizontalLayout, 0.1f);
        
        addComponent(fields);
    	//setExpandRatio(fields, 8);
	}
	
	@Override
    void aggiornaDatiImballi() {

    	if(this.etichettaImballoPan003 != null) {
    		gridPan3.setItems(new ArrayList<VistaPackingList>());
    		gridPan3.setItems(this.repositoryImballi.getVistaPackingListFromEtichettaScatola(null, this.etichettaImballoPan003.getIdEtichettaImballo()));

    		lableEtichettaPan003.setValue("Cod.etic.:"+ this.etichettaImballoPan003.getCodiceEtichettaImballoSmeup());
    		
        	ListDataProvider<VistaPackingList> dataProvider = (ListDataProvider<VistaPackingList>) gridPan3.getDataProvider();
        	int qtaPzInScatola = dataProvider.getItems().size();
        	this.lableQtaPzPan003.setValue("Qtà pz in scatola:  " + String.valueOf(qtaPzInScatola));
    		this.progressBarScatolaPan003.setCurrentStep(qtaPzInScatola);

    	 	StatoBancale statoBancale = this.repositoryImballi.getStatoBancale(this.etichettaImballoPan003);
    	 	this.lableBancalePan003.setValue("Cod. bancale: " + statoBancale.getCodiceBancale());
    	 	this.progressBarBancalePan003.setCurrentStep(statoBancale.getQtyOfBoxesInThePallet()); 
    	 	this.progressBarBancalePan003.setLastNStepsColor(statoBancale.getBoxesWithNotCompleteQty(), SegmentedProgressBar.COLOR_GIALLO);
    	}

    	if(this.etichettaImballoPan004 != null) {
    		gridPan4.setItems(new ArrayList<VistaPackingList>());
    		gridPan4.setItems(this.repositoryImballi.getVistaPackingListFromEtichettaScatola(null, this.etichettaImballoPan004.getIdEtichettaImballo()));

    		this.lableEtichettaPan004.setValue("Cod.etic.:"+ this.etichettaImballoPan004.getCodiceEtichettaImballoSmeup());

        	ListDataProvider<VistaPackingList> dataProvider = (ListDataProvider<VistaPackingList>) gridPan4.getDataProvider();
        	int qtaPzInScatola = dataProvider.getItems().size();
        	this.lableQtaPzPan004.setValue("Qtà pz in scatola:  " + String.valueOf(qtaPzInScatola));
    		this.progressBarScatolaPan004.setCurrentStep(qtaPzInScatola);

    	 	StatoBancale statoBancale = this.repositoryImballi.getStatoBancale(this.etichettaImballoPan004);
    	 	this.lableBancalePan004.setValue("Cod. bancale: " + statoBancale.getCodiceBancale());
    	 	this.progressBarBancalePan004.setCurrentStep(statoBancale.getQtyOfBoxesInThePallet()); 
    	 	this.progressBarBancalePan004.setLastNStepsColor(statoBancale.getBoxesWithNotCompleteQty(), SegmentedProgressBar.COLOR_GIALLO);
    	}

//    	if(this.etichettaImballoPan005 != null) {
//    		gridPan5.setItems(new ArrayList<VistaPackingList>());
//    		gridPan5.setItems(this.repositoryImballi.getVistaPackingListFromEtichettaScatola(null, this.etichettaImballoPan005.getIdEtichettaImballo()));
//
//    		lableEtichettaPan005.setValue("Cod.etic.:"+ this.etichettaImballoPan005.getCodiceEtichetta().substring(0, 10));
//    		
//        	ListDataProvider<VistaPackingList> dataProvider = (ListDataProvider<VistaPackingList>) gridPan5.getDataProvider();
//    		lableQtaPzPan005.setValue("Qtà pz in scatola:  " + String.valueOf(dataProvider.getItems().size()));
//    	}

    	if(this.etichettaImballoPan010 != null) {
    		gridPan10.setItems(new ArrayList<VistaPackingList>());
    		gridPan10.setItems(this.repositoryImballi.getVistaPackingListFromEtichettaScatola(null, this.etichettaImballoPan010.getIdEtichettaImballo()));

    		this.lableEtichettaPan010.setValue("Cod.etic.:"+ this.etichettaImballoPan010.getCodiceEtichettaImballoSmeup());
    		
        	ListDataProvider<VistaPackingList> dataProvider = (ListDataProvider<VistaPackingList>) gridPan10.getDataProvider();
        	int qtaPzInScatola = dataProvider.getItems().size();
        	this.lableQtaPzPan010.setValue("Qtà pz in scatola:  " + String.valueOf(qtaPzInScatola));
    		this.progressBarScatolaPan010.setCurrentStep(qtaPzInScatola);

    	 	StatoBancale statoBancale = this.repositoryImballi.getStatoBancale(this.etichettaImballoPan010);
    	 	this.lableBancalePan010.setValue("Cod. bancale: " + statoBancale.getCodiceBancale());
    	 	this.progressBarBancalePan010.setCurrentStep(statoBancale.getQtyOfBoxesInThePallet()); 
    	 	this.progressBarBancalePan010.setLastNStepsColor(statoBancale.getBoxesWithNotCompleteQty(), SegmentedProgressBar.COLOR_GIALLO);
    	}
    }

	@Override
    void aggiornaVariabileEtichettaImballo(EtichetteImballi etichettaImballo) {
		if(etichettaImballo.getTipoImballo().getProdotto().getCodiceProdotto().equals("PAN0003")) {
			this.etichettaImballoPan003 = etichettaImballo;
		} else if(etichettaImballo.getTipoImballo().getProdotto().getCodiceProdotto().equals("PAN0004")) {
			this.etichettaImballoPan004 = etichettaImballo;
//		} else if(etichettaImballo.getTipoImballo().getProdotto().getCodiceProdotto().equals("PAN0005")) {
//			this.etichettaImballoPan005 = etichettaImballo;
		} else if(etichettaImballo.getTipoImballo().getProdotto().getCodiceProdotto().equals("PAN0010")) {
			this.etichettaImballoPan010 = etichettaImballo;
		}
	}

	@Override
    EtichetteImballi getVariabileEtichettaImballo(String codiceProdotto) {
		if(codiceProdotto.equals("PAN0003")) {
			return this.etichettaImballoPan003;
		} else if(codiceProdotto.equals("PAN0004")) {
			return this.etichettaImballoPan004;
//		} else if(codiceProdotto.equals("PAN0005")) {
//			return this.etichettaImballoPan005;
		} else if(codiceProdotto.equals("PAN0010")) {
			return this.etichettaImballoPan010;
		} else {
			return null;
		}
	}

	@Override
	String getStringPermessoPackingList() {
		return PermessiUtentiLista.FASE_PROCESSO_PACKINGLISTCCU.toString();
	}
    
	@Override
	int getPzMancantiInScatolaAttualeRispettoQuellaNuova(Prodotti prodottoEtichettaDaVerificare) {
        int pzMancantiInScatola = 0 ;
		if(prodottoEtichettaDaVerificare.getCodiceProdotto().equals(CodiciProdottiLista.PAN0003.toString())) {
			if(this.etichettaImballoPan003 == null) return -1;
			int totPzInScatola = ((ListDataProvider<VistaPackingList>) gridPan3.getDataProvider()).getItems().size();
			pzMancantiInScatola = this.etichettaImballoPan003.getTipoImballo().getQtaPezziPerScatola() - totPzInScatola;
		} else if(prodottoEtichettaDaVerificare.getCodiceProdotto().equals(CodiciProdottiLista.PAN0004.toString())) {
			if(this.etichettaImballoPan004 == null) return -1;
			int totPzInScatola = ((ListDataProvider<VistaPackingList>) gridPan4.getDataProvider()).getItems().size();
			pzMancantiInScatola = this.etichettaImballoPan004.getTipoImballo().getQtaPezziPerScatola() - totPzInScatola;
		} else if(prodottoEtichettaDaVerificare.getCodiceProdotto().equals(CodiciProdottiLista.PAN0010.toString())) {
			if(this.etichettaImballoPan010 == null) return -1;
			int totPzInScatola = ((ListDataProvider<VistaPackingList>) gridPan10.getDataProvider()).getItems().size();
			pzMancantiInScatola = this.etichettaImballoPan010.getTipoImballo().getQtaPezziPerScatola() - totPzInScatola;
		}
		return pzMancantiInScatola;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	void addColumnToGrid(Grid<VistaPackingList> grid) {

		grid.setSizeFull();
		
		grid.addColumn(v -> v.getDataMatrix()).setCaption("QRCode");
		
		grid.addColumn(v -> "Cancella",
		      new ButtonRenderer(clickEvent -> { 
		    	  VistaPackingList v = (VistaPackingList)clickEvent.getItem();
		    	  eliminaPezzoDaScatola(v); 
	    	  })).setCaption("Elimina").setWidth(120);
		
		grid.setColumnReorderingAllowed(true);
	}
	
	@Override
	protected void creaOCaricaImballo(String codiceDataMatrixInserito, Prodotti prodottoPerCodiceImballoInserito) {
		
		EtichetteImballi etichettaCorrente = this.getVariabileEtichettaImballo(prodottoPerCodiceImballoInserito.getCodiceProdotto());
		
		//se non ho alcuna etichetta selezionata, non controllo cambio e qtà bancale
		if(etichettaCorrente == null) {
			super.creaOCaricaImballo(codiceDataMatrixInserito, prodottoPerCodiceImballoInserito);
			return;
		}
		
		EtichetteImballi etichettaImballoNuova = new EtichetteImballi();
		etichettaImballoNuova.setCodiceEtichetta(codiceDataMatrixInserito);

		//se etichetta nuova fa parte dello stesso bancale dell'etichetta corrente, non controllo cambio e qtà bancale
		if(etichettaCorrente.getCodiceEtichettaBancaleSmeup() == etichettaImballoNuova.getCodiceEtichettaBancaleSmeup()) {
			super.creaOCaricaImballo(codiceDataMatrixInserito, prodottoPerCodiceImballoInserito);
			return;
		}
		
		StatoBancale statoBancaleCorrente = this.repositoryImballi.getStatoBancale(etichettaCorrente);

		//se bancale corrente è completo, non controllo cambio e qtà bancale
		if(statoBancaleCorrente.isPcsQtyForPalletComplete()) {
			super.creaOCaricaImballo(codiceDataMatrixInserito, prodottoPerCodiceImballoInserito);
			return;
		}
		
    	//Verifico bancale diverso e completamento bancale corrente
    	String messaggio = "Il bancale corrente non è completo ed hai letto etichetta scatola di un altro bancale! <br/>Vuoi proseguire comunque con la nuova scatola e bancale ?";
		CustomPopupWindow window = new CustomPopupWindow("Conferma", messaggio, CustomPopupWindow.urlImmagineBancaleScatole1);
		window.addCloseListener(new CloseListener() {
			
			@Override
			public void windowClose(CloseEvent e) {
				if(window.getEsito()) {
					PackingListCCUView.super.creaOCaricaImballo(codiceDataMatrixInserito, prodottoPerCodiceImballoInserito);
				} else {
					
				}
			}
		});
		getUI().addWindow(window);
	}
}
