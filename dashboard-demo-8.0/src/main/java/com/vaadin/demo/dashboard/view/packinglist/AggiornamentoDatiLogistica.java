//  package com.vaadin.demo.dashboard.view.packinglist;
//
//import java.io.OutputStream;
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.regex.Pattern;
//
//import com.vaadin.data.HasValue.ValueChangeEvent;
//import com.vaadin.data.HasValue.ValueChangeListener;
//import com.vaadin.data.provider.ListDataProvider;
//import com.vaadin.demo.dashboard.component.model.FilterDatamatrix;
//import com.vaadin.demo.dashboard.component.style.StyleUtils;
//import com.vaadin.demo.dashboard.component.utils.CommonUtils;
//import com.vaadin.demo.dashboard.component.utils.PermessiUtils;
//import com.vaadin.demo.dashboard.component.utils.PermessiUtils.PermessiUtentiLista;
//import com.vaadin.demo.dashboard.component.utils.ViewUtils;
//import com.vaadin.demo.dashboard.component.view.MyCustomView;
//import com.vaadin.demo.dashboard.data.model.Datamatrix;
//import com.vaadin.demo.dashboard.data.model.DatamatrixFasiProcesso;
//import com.vaadin.demo.dashboard.data.model.EtichetteImballi;
//import com.vaadin.demo.dashboard.data.model.EtichettePezzi;
//import com.vaadin.demo.dashboard.data.model.FasiProcesso;
//import com.vaadin.demo.dashboard.data.model.FasiProcessoProdotto;
//import com.vaadin.demo.dashboard.data.model.Prodotti;
//import com.vaadin.demo.dashboard.data.model.TipoImballi;
//import com.vaadin.demo.dashboard.data.model.Utenti;
//import com.vaadin.demo.dashboard.data.model.VistaPackingList;
//import com.vaadin.demo.dashboard.data.repository.RepositoryDatamatrix;
//import com.vaadin.demo.dashboard.data.repository.RepositoryDatamatrixFasiProcesso;
//import com.vaadin.demo.dashboard.data.repository.RepositoryFasiProcesso;
//import com.vaadin.demo.dashboard.data.repository.RepositoryFasiProcessoProdotti;
//import com.vaadin.demo.dashboard.data.repository.RepositoryImballi;
//import com.vaadin.demo.dashboard.data.repository.RepositoryProdotti;
//import com.vaadin.demo.dashboard.data.repository.RepositoryProvider;
//import com.vaadin.demo.dashboard.event.DashboardEventBus;
//import com.vaadin.demo.dashboard.view.DashboardMenu;
//import com.vaadin.server.Page;
//import com.vaadin.server.Responsive;
//import com.vaadin.server.ThemeResource;
//import com.vaadin.server.VaadinRequest;
//import com.vaadin.server.VaadinService;
//import com.vaadin.server.VaadinSession;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Grid;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Image;
//import com.vaadin.ui.Label;
//import com.vaadin.ui.Panel;
//import com.vaadin.ui.TextArea;
//import com.vaadin.ui.TextField;
//import com.vaadin.ui.UI;
//import com.vaadin.ui.Upload;
//import com.vaadin.ui.Upload.Receiver;
//import com.vaadin.ui.Upload.SucceededEvent;
//import com.vaadin.ui.VerticalLayout;
//import com.vaadin.ui.renderers.ButtonRenderer;
//import com.vaadin.ui.themes.ValoTheme;
//
//@SuppressWarnings("serial")
//public final class AggiornamentoDatiLogistica extends MyCustomView {
//
//	private RepositoryImballi repositoryImballi = null;
//	
//	Grid gridCodiciBancali = new Grid();
//	
//	private TextField textDatamatrix;
//	
//    
//	public AggiornamentoDatiLogistica() {
//        setSizeFull();
//        addStyleName("transactions");
//        setMargin(false);
//        setSpacing(false);
//        
//        this.repositoryImballi = RepositoryProvider.repositoryImballi();
//        
//        DashboardEventBus.register(this);
//
//        buildDatamatrixForm();
//	}
//
//    private void buildDatamatrixForm() {
//        HorizontalLayout header = new HorizontalLayout();
//        header.addStyleName(StyleUtils.viewHeaderStyle);
//        Responsive.makeResponsive(header);
//
//        Label title = new Label(ViewUtils.titoloAgiornamentoDatiLogistica);
//        //title.setSizeUndefined();
//        title.addStyleName(ValoTheme.LABEL_H1);
//        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
//        header.addComponent(title);
//        
//        header.setHeight("90px");
//        //addComponent(header);
//        //setExpandRatio(header, 0);
//
////    	layoutCampiDatamatrix = new VerticalLayout();
////    	layoutCampiDatamatrix.setSizeFull();
//    	
////    	MarginInfo marginInfo = new MarginInfo(false, false, true, false);
////    	marginInfo.do(10, 10, 20, 10);
////    	fields.setMargin(marginInfo);
//
//        
//    	textDatamatrix = new TextField("Aggiornamento codici Bancali ()");  
//    	textDatamatrix.setPlaceholder("File csv");
//    	//textDatamatrix.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
//    	textDatamatrix.addValueChangeListener(new ValueChangeListener<String>() {
//			
//			@Override
//			public void valueChange(ValueChangeEvent<String> event) {
//				String codiceDataMatrixInserito = textDatamatrix.getValue().trim();
//
//		    	textDatamatrix.setComponentError(null);
//				if (codiceDataMatrixInserito.isEmpty()) {
//					return;
//				}
//				checkAndSaveDatamatrix(codiceDataMatrixInserito);
//						
//		    	textDatamatrix.setValue("");
//		    	textDatamatrix.setComponentError(null);
//			}
//		});
//    	textDatamatrix.setHeight("65px");
//    	textDatamatrix.setWidth("700px");
//
//    	
//    	VerticalLayout layoutDatamatrix = new VerticalLayout();
//    	layoutDatamatrix.setSizeFull();
//    	layoutDatamatrix.addComponents(textDatamatrix);
//    	layoutDatamatrix.setComponentAlignment(textDatamatrix, Alignment.MIDDLE_CENTER);
//
//    	VerticalLayout layoutPezziScatolaPan3 = new VerticalLayout();
//    	layoutPezziScatolaPan3.setWidth("400px");
//    	VerticalLayout layoutPezziScatolaPan4 = new VerticalLayout();
//    	layoutPezziScatolaPan4.setWidth("400px");
//    	VerticalLayout layoutPezziScatolaPan5 = new VerticalLayout();
//    	layoutPezziScatolaPan5.setWidth("400px");
//    	VerticalLayout layoutPezziScatolaPan9 = new VerticalLayout();
//    	layoutPezziScatolaPan9.setWidth("400px");
//    	VerticalLayout layoutPezziScatolaPan10 = new VerticalLayout();
//
//    	Label titlePan003 = new Label("PAN003");
//        setPezziScatolaGridTitleStyle(titlePan003);        
//        Label titlePan004 = new Label("PAN004");
//        setPezziScatolaGridTitleStyle(titlePan004);
//        Label titlePan005 = new Label("PAN005");
//        setPezziScatolaGridTitleStyle(titlePan005);
//        Label titlePan009 = new Label("PAN009");
//        setPezziScatolaGridTitleStyle(titlePan009);
//        Label titlePan010 = new Label("PAN010");
//        setPezziScatolaGridTitleStyle(titlePan010);
//        
//    	layoutPezziScatolaPan3.addComponent(titlePan003);
//    	layoutPezziScatolaPan4.addComponent(titlePan004);
//    	layoutPezziScatolaPan5.addComponent(titlePan005);
//    	layoutPezziScatolaPan9.addComponent(titlePan009);
//    	layoutPezziScatolaPan10.addComponent(titlePan010);
//    	
//    	lableEtichettaPan003 = new Label("Cod.etic.:");
//        setInfoScatolaStyle(lableEtichettaPan003);        
//        lableEtichettaPan004 = new Label("Cod.etic.:");
//        setInfoScatolaStyle(lableEtichettaPan004);
//        lableEtichettaPan005 = new Label("Cod.etic.:");
//        setInfoScatolaStyle(lableEtichettaPan005);
//        lableEtichettaPan009 = new Label("Cod.etic.:");
//        setInfoScatolaStyle(lableEtichettaPan009);
//        lableEtichettaPan010 = new Label("Cod.etic.:");
//        setInfoScatolaStyle(lableEtichettaPan010);
//    	layoutPezziScatolaPan3.addComponent(lableEtichettaPan003);
//    	layoutPezziScatolaPan4.addComponent(lableEtichettaPan004);
//    	layoutPezziScatolaPan5.addComponent(lableEtichettaPan005);
//    	layoutPezziScatolaPan9.addComponent(lableEtichettaPan009);
//    	layoutPezziScatolaPan10.addComponent(lableEtichettaPan010);
//
//    	addColumnToGrid(gridPan3);
//    	addColumnToGrid(gridPan4);
//    	addColumnToGrid(gridPan5);
//    	addColumnToGrid(gridPan9);
//    	addColumnToGrid(gridPan10);
//    	layoutPezziScatolaPan3.addComponent(gridPan3);
//    	layoutPezziScatolaPan4.addComponent(gridPan4);
//    	layoutPezziScatolaPan5.addComponent(gridPan5);
//    	layoutPezziScatolaPan9.addComponent(gridPan9);
//    	//layoutPezziScatolaPan10.addComponent(gridPan10);
//
//        lableQtaPzPan003 = new Label("Qtà pz in scatola: 0");
//        setInfoScatolaStyle(lableQtaPzPan003);        
//        lableQtaPzPan004 = new Label("Qtà pz in scatola: 0");
//        setInfoScatolaStyle(lableQtaPzPan004);
//        lableQtaPzPan005 = new Label("Qtà pz in scatola: 0");
//        setInfoScatolaStyle(lableQtaPzPan005);
//        lableQtaPzPan009 = new Label("Qtà pz in scatola: 0");
//        setInfoScatolaStyle(lableQtaPzPan009);
//        lableQtaPzPan010 = new Label("Qtà pz in scatola: 0");
//        setInfoScatolaStyle(lableQtaPzPan010);
//        
//    	layoutPezziScatolaPan3.addComponent(lableQtaPzPan003);
//    	layoutPezziScatolaPan4.addComponent(lableQtaPzPan004);
//    	layoutPezziScatolaPan5.addComponent(lableQtaPzPan005);
//    	layoutPezziScatolaPan9.addComponent(lableQtaPzPan009);
//    	layoutPezziScatolaPan10.addComponent(lableQtaPzPan010);
//
//        HorizontalLayout pezziScatolaHorizontalLayout = new HorizontalLayout();
//        pezziScatolaHorizontalLayout.addComponent(layoutPezziScatolaPan3);
//        pezziScatolaHorizontalLayout.addComponent(layoutPezziScatolaPan4);
//        pezziScatolaHorizontalLayout.addComponent(layoutPezziScatolaPan5);
//        pezziScatolaHorizontalLayout.addComponent(layoutPezziScatolaPan9);
//        //pezziScatolaHorizontalLayout.addComponent(layoutPezziScatolaPan10);
//        pezziScatolaHorizontalLayout.setSizeFull();
//
//        VerticalLayout fields = new VerticalLayout();
//        fields.setSizeFull();
//        fields.setDefaultComponentAlignment(Alignment.TOP_CENTER);
//        addComponent(header);
//        addComponents(layoutDatamatrix);
//        addComponents(pezziScatolaHorizontalLayout);
//        
//        setExpandRatio(header, 0.1f);
//        setExpandRatio(layoutDatamatrix, 0.2f);
//        setExpandRatio(pezziScatolaHorizontalLayout, 0.7f);
//        
//        
//        addComponent(fields);
//    	//setExpandRatio(fields, 8);
//	}
//    
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	private void addColumnToGrid(Grid<VistaPackingList> grid) {
//
//		grid.setSizeFull();
//		
//		grid.addColumn(v -> v.getDataMatrix()).setCaption("QRCode");
//		
//		grid.addColumn(v -> "Cancella",
//		      new ButtonRenderer(clickEvent -> { 
//		    	  VistaPackingList v = (VistaPackingList)clickEvent.getItem();
//		    	  eliminaPezzoDaScatola(v); 
//	    	  })).setCaption("Elimina").setWidth(120);
//		
//		grid.setColumnReorderingAllowed(true);
//	}
//
//	private void eliminaPezzoDaScatola(VistaPackingList v) {
//		if(v != null) {
//			EtichettePezzi etichettaPezzo = this.repositoryImballi.getEtichettaPezzo(v.getIdEtichettaPezzo(), null);
//			etichettaPezzo.setEliminato(true);
//			this.repositoryImballi.salva(etichettaPezzo);
//			
//			this.aggiornaDatiImballi();
//		}
//	}
//
//	private void setPezziScatolaGridTitleStyle(Label title) {
//        title.setSizeUndefined();
//        title.addStyleName(ValoTheme.LABEL_H2);
//        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
//	}
//
//	private void setInfoScatolaStyle(Label title) {
//        title.setSizeUndefined();
//        title.addStyleName(ValoTheme.LABEL_H3);
//        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
//	}
//
//	private void checkAndSaveDatamatrix(String codiceDataMatrixInserito) {
//		try {
//			//Verifico che il formato del valore sia compatibile con uno dei DM
//			Prodotti prodottoCorrente = null;
//	        for (Prodotti prodottoToCheck : this.listaProdottiPackingList) {
//	        	if(prodottoToCheck.getFormatoDatamatrix() != null && !prodottoToCheck.getFormatoDatamatrix().isEmpty()) {
//	            	boolean isFormatoCorretto = Pattern.matches(prodottoToCheck.getFormatoDatamatrix(), codiceDataMatrixInserito);
//	            	if(isFormatoCorretto) {
//	            		prodottoCorrente = prodottoToCheck;
//	            		break;
//	            	}
//	        	}
//			}
//	        
//	        if(prodottoCorrente == null) {
//		        //verifico che non sia una scatola letta
//		        if(codiceDataMatrixInserito.length() > 70 && codiceDataMatrixInserito.length() < 90) {
//		        	EtichetteImballi etichettaImballo = this.repositoryImballi.getEtichettaImballo(codiceDataMatrixInserito);
//		         	if (etichettaImballo == null) {
//		        		etichettaImballo = new EtichetteImballi();
//		        		etichettaImballo.setCodiceEtichetta(codiceDataMatrixInserito);
//		        		etichettaImballo.setEliminato(false);
//		        		String numeroDisegnoEtichetta = codiceDataMatrixInserito.substring(codiceDataMatrixInserito.indexOf("YEF"), codiceDataMatrixInserito.indexOf("YEF")+12);
//		        		
//		        		Prodotti prodottoEtichettaNuova = null;
//		        		for (Prodotti prodottoEtichetta : this.listaProdottiPackingList) {
//							if(prodottoEtichetta.getNumeroDisegno().equals(numeroDisegnoEtichetta)) {
//								prodottoEtichettaNuova = prodottoEtichetta;
//		        				break;
//							}
//						}
//		        		
//		        		for (TipoImballi tipoImballoInCiclo : this.listaTipoImballi) {
//		        			if(tipoImballoInCiclo.getProdotto().getIdProdotto() == prodottoEtichettaNuova.getIdProdotto()) {
//		        				etichettaImballo.setTipoImballo(tipoImballoInCiclo);
//		        				break;
//		        			}
//		        		}
//		        		
//		        		//List<TipoImballi> listaTipoImablli = this.repositoryImballi.getTipoImballiByIdProdotto(prodottoEtichettaNuova.getIdProdotto());
//		        		
//		        		this.repositoryImballi.salva(etichettaImballo);						
//		        	} 
//					this.aggiornaVariabileEtichettaImballo(etichettaImballo);
//					this.aggiornaDatiImballi();
//			    	ViewUtils.showSuccessfullNotification("Etichetta scatola inserita");
//		        } else {
//		            throw new Exception("Formato QrCode non riconosciuto");
//		        }
//	        } else {
//	        	//verifico e inserico prodotto in scatola
//		    	List<Datamatrix> listaDatamatrix = this.repositoryDatamatrix.getDataMatrix(new FilterDatamatrix(codiceDataMatrixInserito), null).getResult();
//		    	Datamatrix datamatrix = null;
//		    	
//		    	if(listaDatamatrix == null || listaDatamatrix.size() == 0) {
//		    		datamatrix = new Datamatrix();
//		    		datamatrix.setEliminato(false);
//		    		datamatrix.setDataMatrix(codiceDataMatrixInserito);
//		    		datamatrix.setProddoto(prodottoCorrente);
//		    		datamatrix.setUtenteCreatore(getCurrentUser());
//		    		datamatrix.setDataOraUltimaModifica(new Date());
//		    		datamatrix.setUtenteUltimaModifica(getCurrentUser());
//		    		datamatrix.setDataOraCreazione(new Date());
//		    		datamatrix.setPartNumber("");
//		    		datamatrix.setCodiceInternoProdotto("");
//		    		datamatrix.setStato("I");
//		    		datamatrix.setDataOraCreazione(new Date());
//		        	
//		        	this.repositoryDatamatrix.salva(datamatrix);
//		        	listaDatamatrix = this.repositoryDatamatrix.getDataMatrix(new FilterDatamatrix(codiceDataMatrixInserito), null).getResult();
//		    	}
//	    		datamatrix = listaDatamatrix.get(0);
//	    		
//	    		//Etichetta inserita per questo codice prodotto ??
//	    		EtichetteImballi etichettaImballo = this.getVariabileEtichettaImballo(datamatrix.getProddoto().getCodiceProdotto());
//	    		if(etichettaImballo == null) {
//		            throw new Exception("Inserire codice etichetta per questo tipo di prodotto");
//	    		}
//
//	    		//Doppione / pezzo già in packing list ??
//	    		List<VistaPackingList> vistaPackingList = this.repositoryImballi.getVistaPackingListByDatamatrix(datamatrix.getDataMatrix());
//	    		if(vistaPackingList != null && vistaPackingList.size() > 0) {
//		            throw new Exception("Qrcode doppio o pezzo già inserito in packing list");
//	    		}
//	    		
//	    		//scatola piena ??
//	    		List<EtichettePezzi> etichettePezzi = this.repositoryImballi.getEtichettaPezzoByEtichettaImballo(etichettaImballo.getIdEtichettaImballo());
//	    		if(etichettePezzi.size() >= etichettaImballo.getTipoImballo().getQtaPezziPerScatola()) {
//		            throw new Exception("Scatola già piena, utilizzare una nuova etichetta");
//	    		}
//	    		
//	    		EtichettePezzi nuovaEtichettaPezzo = new EtichettePezzi();
//	    		nuovaEtichettaPezzo.setDatamatrix(datamatrix);
//	    		nuovaEtichettaPezzo.setEliminato(false);
//	    		nuovaEtichettaPezzo.setEtichettaImballo(etichettaImballo);
//	    		nuovaEtichettaPezzo.setUtenteCreatore(getCurrentUser());
//	    		nuovaEtichettaPezzo.setDataOraCreazione(new Date());
//	    		nuovaEtichettaPezzo.setUtenteUltimaModifica(getCurrentUser());
//	    		nuovaEtichettaPezzo.setDataOraUltimaModifca(new Date());
//	    		this.repositoryImballi.salva(nuovaEtichettaPezzo);
//	    		
//	    		this.aggiornaDatiImballi();
//		    	ViewUtils.showSuccessfullNotification("Pezzo inserito in scatola");
//	        }	    	
//		} catch (Exception e) {
//        	ViewUtils.showErrorNotification(e.getMessage());
//        	//logging
//		}
//	}
//
//    private void aggiornaVariabileEtichettaImballo(EtichetteImballi etichettaImballo) {
//		if(etichettaImballo.getTipoImballo().getProdotto().getCodiceProdotto().equals("PAN0003")) {
//			this.etichettaImballoPan003 = etichettaImballo;
//		} else if(etichettaImballo.getTipoImballo().getProdotto().getCodiceProdotto().equals("PAN0004")) {
//			this.etichettaImballoPan004 = etichettaImballo;
//		} else if(etichettaImballo.getTipoImballo().getProdotto().getCodiceProdotto().equals("PAN0005")) {
//			this.etichettaImballoPan005 = etichettaImballo;
//		} else if(etichettaImballo.getTipoImballo().getProdotto().getCodiceProdotto().equals("PAN0009")) {
//			this.etichettaImballoPan009 = etichettaImballo;
//		} else if(etichettaImballo.getTipoImballo().getProdotto().getCodiceProdotto().equals("PAN00010")) {
//			this.etichettaImballoPan010 = etichettaImballo;
//		}
//	}
//
//    private EtichetteImballi getVariabileEtichettaImballo(String codiceProdotto) {
//		if(codiceProdotto.equals("PAN0003")) {
//			return this.etichettaImballoPan003;
//		} else if(codiceProdotto.equals("PAN0004")) {
//			return this.etichettaImballoPan004;
//		} else if(codiceProdotto.equals("PAN0005")) {
//			return this.etichettaImballoPan005;
//		} else if(codiceProdotto.equals("PAN0009")) {
//			return this.etichettaImballoPan009;
//		} else if(codiceProdotto.equals("PAN00010")) {
//			return this.etichettaImballoPan010;
//		} else {
//			return null;
//		}
//	}
//    
//    private void aggiornaDatiImballi() {
//
//    	if(this.etichettaImballoPan003 != null) {
//    		gridPan3.setItems(new ArrayList<VistaPackingList>());
//    		gridPan3.setItems(this.repositoryImballi.getVistaPackingListFromEtichettaScatola(null, this.etichettaImballoPan003.getIdEtichettaImballo()));
//
//    		lableEtichettaPan003.setValue("Cod.etic.:"+ this.etichettaImballoPan003.getCodiceEtichetta().substring(0, 10));
//
//        	ListDataProvider<VistaPackingList> dataProvider = (ListDataProvider<VistaPackingList>) gridPan3.getDataProvider();
//    		lableQtaPzPan003.setValue("Qtà pz in scatola:  " + String.valueOf(dataProvider.getItems().size()));
//    	}
//
//    	if(this.etichettaImballoPan004 != null) {
//    		gridPan4.setItems(new ArrayList<VistaPackingList>());
//    		gridPan4.setItems(this.repositoryImballi.getVistaPackingListFromEtichettaScatola(null, this.etichettaImballoPan004.getIdEtichettaImballo()));
//
//    		lableEtichettaPan004.setValue("Cod.etic.:"+ this.etichettaImballoPan004.getCodiceEtichetta().substring(0, 10));
//
//        	ListDataProvider<VistaPackingList> dataProvider = (ListDataProvider<VistaPackingList>) gridPan4.getDataProvider();
//    		lableQtaPzPan004.setValue("Qtà pz in scatola:  " + String.valueOf(dataProvider.getItems().size()));
//    	}
//
//    	if(this.etichettaImballoPan005 != null) {
//    		gridPan5.setItems(new ArrayList<VistaPackingList>());
//    		gridPan5.setItems(this.repositoryImballi.getVistaPackingListFromEtichettaScatola(null, this.etichettaImballoPan005.getIdEtichettaImballo()));
//
//    		lableEtichettaPan005.setValue("Cod.etic.:"+ this.etichettaImballoPan005.getCodiceEtichetta().substring(0, 10));
//    		
//        	ListDataProvider<VistaPackingList> dataProvider = (ListDataProvider<VistaPackingList>) gridPan5.getDataProvider();
//    		lableQtaPzPan005.setValue("Qtà pz in scatola:  " + String.valueOf(dataProvider.getItems().size()));
//    	}
//
//    	if(this.etichettaImballoPan009 != null) {
//    		gridPan9.setItems(new ArrayList<VistaPackingList>());
//    		gridPan9.setItems(this.repositoryImballi.getVistaPackingListFromEtichettaScatola(null, this.etichettaImballoPan009.getIdEtichettaImballo()));
//
//    		lableEtichettaPan009.setValue("Cod.etic.:"+ this.etichettaImballoPan009.getCodiceEtichetta().substring(0, 10));
//    		
//        	ListDataProvider<VistaPackingList> dataProvider = (ListDataProvider<VistaPackingList>) gridPan9.getDataProvider();
//    		lableQtaPzPan009.setValue("Qtà pz in scatola:  " + String.valueOf(dataProvider.getItems().size()));
//    	}
//
//    	if(this.etichettaImballoPan010 != null) {
//    		gridPan10.setItems(new ArrayList<VistaPackingList>());
//    		gridPan10.setItems(this.repositoryImballi.getVistaPackingListFromEtichettaScatola(null, this.etichettaImballoPan010.getIdEtichettaImballo()));
//
//    		lableEtichettaPan010.setValue("Cod.etic.:"+ this.etichettaImballoPan010.getCodiceEtichetta().substring(0, 10));
//    		
//        	ListDataProvider<VistaPackingList> dataProvider = (ListDataProvider<VistaPackingList>) gridPan10.getDataProvider();
//    		lableQtaPzPan010.setValue("Qtà pz in scatola:  " + String.valueOf(dataProvider.getItems().size()));
//    	}
//	}
//    
//    private Utenti getCurrentUser() {
//        return (Utenti) VaadinSession.getCurrent().getAttribute(Utenti.class.getName());
//    }
//
//	private void addSpacigComponent(Integer expandRatio) {
//		VerticalLayout verticalLayout = new VerticalLayout();
//		verticalLayout.setSizeFull();
//    	addComponents(verticalLayout);
//    	setExpandRatio(verticalLayout, expandRatio);
//	}
//
//}
