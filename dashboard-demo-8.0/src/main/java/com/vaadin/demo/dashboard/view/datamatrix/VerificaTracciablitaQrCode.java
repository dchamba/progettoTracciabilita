package com.vaadin.demo.dashboard.view.datamatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.demo.dashboard.component.model.StatoBancale;
import com.vaadin.demo.dashboard.component.style.StyleUtils;
import com.vaadin.demo.dashboard.component.utils.CommonUtils;
import com.vaadin.demo.dashboard.component.utils.ViewUtils;
import com.vaadin.demo.dashboard.component.view.MyCustomView;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiEseguite;
import com.vaadin.demo.dashboard.data.model.EtichetteImballi;
import com.vaadin.demo.dashboard.data.model.Prodotti;
import com.vaadin.demo.dashboard.data.model.Utenti;
import com.vaadin.demo.dashboard.data.model.VistaPackingList;
import com.vaadin.demo.dashboard.data.repository.RepositoryImballi;
import com.vaadin.demo.dashboard.data.repository.RepositoryProdotti;
import com.vaadin.demo.dashboard.data.repository.RepositoryProvider;
import com.vaadin.demo.dashboard.data.repository.RepositoryVerificaQrCode;
import com.vaadin.demo.dashboard.event.DashboardEventBus;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.TextRenderer;
import com.vaadin.ui.themes.ValoTheme;

public class VerificaTracciablitaQrCode extends MyCustomView {

	private RepositoryProdotti repositoryProdotti = null;
	private RepositoryImballi repositoryImballi = null;
//	private RepositoryProveTenuta repositoryProveTenuta = null;
//	private RepositoryDatamatrix repositoryDatamatrix = null;
//
//	private RepositoryFasiProcesso repositoryFasiProcesso = null;
//	private RepositoryDatamatrixFasiProcesso repositoryDatamatrixFasiProcesso = null;
	//private RepositoryFasiProcessoProdotti repositoryFasiProcessoProdotto = null;

	private RepositoryVerificaQrCode repositoryVerificaQrCode = new RepositoryVerificaQrCode();
	
	private TextField textDatamatrix;

	private TabSheet tabsheet = new TabSheet();
	
	public VerificaTracciablitaQrCode() {
        setSizeFull();
        addStyleName("transactions");
        setMargin(false);
        setSpacing(false);

        this.repositoryProdotti = RepositoryProvider.getRepositoryProdotti();
        this.repositoryImballi = RepositoryProvider.repositoryImballi();
        
//        this.repositoryProveTenuta = RepositoryProvider.getRepositoryProveTenuta();
//        this.repositoryDatamatrix = RepositoryProvider.getRepositoryDatamatrix();
//        
//        this.repositoryFasiProcesso = RepositoryProvider.repositoryFasiProcesso();
//        this.repositoryDatamatrixFasiProcesso = RepositoryProvider.getRepositoryDatamatrixTrattamenti();
        //this.repositoryFasiProcessoProdotto = RepositoryProvider.repositoryFasiProcessoProdotti();
        
        DashboardEventBus.register(this);
        buildDatamatrixForm();
	}
	
    void buildDatamatrixForm() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName(StyleUtils.viewHeaderStyle);
        Responsive.makeResponsive(header);
        
        Label title = new Label(ViewUtils.titoloVistaVerificaQrCode);
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
	    
    	textDatamatrix = new TextField("Inserisci QrCode");  
    	textDatamatrix.setPlaceholder("Qrcode pezzo/imballo (ricerca multipla con valori separati da \";\" e max 10 qrcode)");
    	//textDatamatrix.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
    	textDatamatrix.addValueChangeListener(new ValueChangeListener<String>() {
			
			@Override
			public void valueChange(ValueChangeEvent<String> event) {
				String codiceDataMatrixInserito = textDatamatrix.getValue().trim();

		    	textDatamatrix.setComponentError(null);
				if (codiceDataMatrixInserito.isEmpty()) {
					return;
				}
				checkQrCodeInserted(codiceDataMatrixInserito);
						
		    	textDatamatrix.setValue("");
		    	textDatamatrix.setComponentError(null);
			}
		});
    	textDatamatrix.setHeight("65px");
    	textDatamatrix.setWidth("800px");

    	
    	VerticalLayout layoutDatamatrix = new VerticalLayout();
    	layoutDatamatrix.setSizeFull();
    	layoutDatamatrix.addComponent(textDatamatrix);
    	layoutDatamatrix.setComponentAlignment(textDatamatrix, Alignment.MIDDLE_CENTER);

    	VerticalLayout layoutPezziScatolaFia10 = new VerticalLayout();
    	//layoutPezziScatolaFia10.setWidth("800px");
    	//layoutPezziScatolaFia10.setSizeFull();

    	Label titleFia10 = new Label("Anime GESTAMP");
        setPezziScatolaGridTitleStyle(titleFia10);    
        
    	layoutPezziScatolaFia10.addComponent(titleFia10);

    	//layoutPezziScatolaFia10.addComponent(gridAnimeImballi);
        
//        HorizontalLayout pezziScatolaHorizontalLayout = new HorizontalLayout();
//        pezziScatolaHorizontalLayout.addComponent(layoutPezziScatolaFia10);
//        pezziScatolaHorizontalLayout.setSizeFull();
    	
    	tabsheet.setSizeFull();

    	VerticalLayout vTab1 = new VerticalLayout();
    	VerticalLayout vTab2 = new VerticalLayout();

        VerticalLayout fields = new VerticalLayout();
        fields.setSizeFull();
        fields.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        addComponent(header);
        addComponents(layoutDatamatrix);
        addComponents(tabsheet);
        
        setExpandRatio(header, 0.1f);
        setExpandRatio(layoutDatamatrix, 0.2f);
        setExpandRatio(tabsheet, 0.7f);
        
        
        addComponent(fields);
    	//setExpandRatio(fields, 8);
	}    

	protected void checkQrCodeInserted(String codiceDataMatrixInserito) {
		try {
			//se codiceDatamatrix già cercato apro il relativo Tab
			if(this.tabsheet.getComponentCount() > 0) {
				Iterator<Component> i = this.tabsheet.getComponentIterator();
				while (i.hasNext()) {
					Component component = (Component) i.next();
				    Tab tab = this.tabsheet.getTab(component);
				    if (codiceDataMatrixInserito.equals(tab.getCaption()) || codiceDataMatrixInserito.startsWith(tab.getCaption())) {
				         this.tabsheet.setSelectedTab(component);
				         return;
				    }
				}
			}
			
			List<String> listaCodiceDataMatrixInseritoSplittato = new ArrayList<>();
			listaCodiceDataMatrixInseritoSplittato.add(codiceDataMatrixInserito);
			if(codiceDataMatrixInserito.contains(";")) {
				listaCodiceDataMatrixInseritoSplittato.addAll(Arrays.asList(codiceDataMatrixInserito.split(";")));
			}
			
			Integer tabCreate = 0;
			
			for (String codiceDataMatrixInseritoSplittato : listaCodiceDataMatrixInseritoSplittato) {
				//Verifico che il formato del valore sia compatibile con uno dei DM
				Prodotti prodottoCorrente  = this.repositoryProdotti.getProdottoFromDatamatrixFormat(codiceDataMatrixInseritoSplittato, false);
				EtichetteImballi etichettaImballo = this.repositoryImballi.getEtichettaImballoByCodiceEtichettaOrStartWith(codiceDataMatrixInseritoSplittato);
				
		        if(prodottoCorrente != null) {
		        	Map<String, List<DatamatrixFasiEseguite>> mappaFasiEseguite = repositoryVerificaQrCode.getDatamatrixFasiEseguite(codiceDataMatrixInseritoSplittato);
		        	//si tratta di qrcode inserito, componento con 3 griglie
		        	this.aggiungiTabDatiDataMatrix(mappaFasiEseguite, codiceDataMatrixInseritoSplittato);	  
		        	++tabCreate;      	
		        } else if(etichettaImballo != null){
		        	this.aggiungiTabIballo(etichettaImballo);
		        	++tabCreate;
		        } else {
		        	//verifica numero ddt
		        }
			}
			
			if(tabCreate == 0) {
	    		throw new Exception("Datamatrix/qrcode inserito non trovato in sistema");
			}
			
		} catch (Exception e) {
          	ViewUtils.showErrorNotification("Errore durante caricamento dati: " + e.getMessage());
		}
	}

	private void aggiungiTabIballo(EtichetteImballi etichettaImballo) {
		List<VistaPackingList> vistaPackingList = this.repositoryImballi.getVistaPackingListFromEtichettaScatola(null, etichettaImballo.getIdEtichettaImballo());
		StatoBancale statoBancale = this.repositoryImballi.getStatoBancale(etichettaImballo);
		
		VerticalLayout layoutGridDatiImballo = new VerticalLayout();
		
//		Label titleCodiceProdotto = new Label(primoVistaPacking == null ? "" : primoVistaPacking.getCodiceProdotto());
		Label titleCodiceProdotto = new Label("Dati scatola");
		setPezziScatolaGridTitleStyle(titleCodiceProdotto);
		titleCodiceProdotto.addStyleName(ValoTheme.LABEL_BOLD);
		layoutGridDatiImballo.addComponent(titleCodiceProdotto);
		
		Label lableEtichetta = new Label("Cod.etic.: " + etichettaImballo.getCodiceEtichettaImballoSmeup());
		setInfoScatolaStyle(lableEtichetta);
		layoutGridDatiImballo.addComponent(lableEtichetta);

		Label lableQtaPzImballo = new Label("Qtà pz in scatola: " + vistaPackingList.size());
		setInfoScatolaStyle(lableQtaPzImballo);
		layoutGridDatiImballo.addComponent(lableQtaPzImballo);
		
		//fare griglia e colonne
		Grid<VistaPackingList> gridDatiImballo = new Grid<VistaPackingList>();
		gridDatiImballo.setSizeFull();   
		gridDatiImballo.addColumn(v -> v.getDataMatrix()).setCaption("QRCode pz");
		gridDatiImballo.addColumn(v -> CommonUtils.DATETIMEFORMAT.format(v.getDataOraCreazioneEtichettaPezzo()), new TextRenderer("")).setCaption("Data/ora imballo pz");
		gridDatiImballo.addColumn(v -> v.getCodiceProdotto()).setCaption("Cod. prodotto");
		gridDatiImballo.addColumn(v -> v.getNomeCognome()).setCaption("Operatore");
		gridDatiImballo.setColumnReorderingAllowed(true);
		gridDatiImballo.setItems(vistaPackingList);
		
		layoutGridDatiImballo.addComponent(gridDatiImballo); 
		
		Label labelQtaScatoleMancanti = null, labelQtaScatolePzSonoCorrette = null,labelQtaPzMancanti = null;
		
		if(vistaPackingList.size() < etichettaImballo.getTipoImballo().getQtaPezziPerScatola()) {
			String testoMaxQtaImballo = "Scatola non completa! Necessari tot : " + etichettaImballo.getTipoImballo().getQtaPezziPerScatola() + " pz";
			testoMaxQtaImballo = "<b style='color:red;'>" + testoMaxQtaImballo + "</b>";
			testoMaxQtaImballo += "<br>";
			Label lableQtaMaxImballo = new Label(testoMaxQtaImballo);
			setInfoScatolaStyle(lableQtaMaxImballo);
			lableQtaMaxImballo.setContentMode(com.vaadin.shared.ui.ContentMode.HTML);
			layoutGridDatiImballo.addComponent(lableQtaMaxImballo);
		}
		
		if(statoBancale.getQtyOfMissingPcsInThePallet() > 0) {
			String qtaPzMantantiStringa = "<b style='color:red;'>Qtà. pz mancanti in pallet: " + statoBancale.getQtyOfMissingPcsInThePallet() + "</b>";
			qtaPzMantantiStringa += "<br>";
			
			labelQtaPzMancanti = new Label(qtaPzMantantiStringa);
			setInfoScatolaStyle(labelQtaPzMancanti);
			labelQtaPzMancanti.setContentMode(com.vaadin.shared.ui.ContentMode.HTML);
		}
		
		if(statoBancale.getBoxesWithMissingPcs().size() > 0) {
			String qtaScatoleMantantiStringa = "<br><b style='color:red;'>Scatole incomplete:</b>";
			
			for(Map.Entry<String, Integer> bancaleNonCompleto : statoBancale.getBoxesWithMissingPcs().entrySet()) {
				qtaScatoleMantantiStringa += "<br><b style='color:red;'>" + bancaleNonCompleto.getKey() + "   -" + bancaleNonCompleto.getValue() + " pz</b>";
			}
			
			labelQtaScatoleMancanti = new Label(qtaScatoleMantantiStringa);
			setInfoScatolaStyle(labelQtaScatoleMancanti);
			labelQtaScatoleMancanti.setContentMode(com.vaadin.shared.ui.ContentMode.HTML);
		}

		if(statoBancale.isPcsQtyForPalletComplete()) {
			labelQtaScatolePzSonoCorrette = new Label("Pallet completo!");
			setPezziScatolaGridTitleStyle(labelQtaScatolePzSonoCorrette);
			labelQtaScatolePzSonoCorrette.addStyleName(ValoTheme.LABEL_SUCCESS);
		}

		VerticalLayout layoutStatoBancale = new VerticalLayout();
		layoutStatoBancale.setSizeFull();
		
		Label labelTitoloDatiPallet = new Label("Dati pallet");
		setPezziScatolaGridTitleStyle(labelTitoloDatiPallet);
		labelTitoloDatiPallet.addStyleName(ValoTheme.LABEL_BOLD);
		layoutStatoBancale.addComponent(labelTitoloDatiPallet);

//		layoutStatoBancale.addComponent(new Label(" "));
		
		Label labelNumeroBancale = new Label("Num. pallet : " + etichettaImballo.getCodiceEtichettaBancaleSmeup());
		setPezziScatolaGridTitleStyle(labelNumeroBancale);
		layoutStatoBancale.addComponent(labelNumeroBancale);
		
//		layoutStatoBancale.addComponent(new Label(" "));
		
//		Label labelQtaScatoleNecessarie = new Label("Qtà. scatole necessarie: " + etichettaImballo.getTipoImballo().getQtaScatolePerBancale());
//		setInfoScatolaStyle(labelQtaScatoleNecessarie);
//		layoutStatoBancale.addComponent(labelQtaScatoleNecessarie);
		
		String labelHtmlNumeroBancaleQtaScatoleNecessarie = "<b style='font-size:1.2em;color:blue;'>Qtà. scatole necessarie: " + etichettaImballo.getTipoImballo().getQtaScatolePerBancale() + "</b>";
		labelHtmlNumeroBancaleQtaScatoleNecessarie += "</br>";
		labelHtmlNumeroBancaleQtaScatoleNecessarie += "<b style='font-size:1.2em;color:blue;'>Qtà. pz necessari: " + statoBancale.getStandardPcsQtyPerPallet() + "</b>";
		Label labelNumeroBancaleQtaScatoleNecessarie = new Label(labelHtmlNumeroBancaleQtaScatoleNecessarie);
		labelNumeroBancaleQtaScatoleNecessarie.setContentMode(com.vaadin.shared.ui.ContentMode.HTML);
		layoutStatoBancale.addComponent(labelNumeroBancaleQtaScatoleNecessarie);
		
//		Label labelQtaPzNecessari = new Label("Qtà. pz necessari: " + statoBancale.getStandardPcsQtyPerPallet());
//		setInfoScatolaStyle(labelQtaPzNecessari);
//		layoutStatoBancale.addComponent(labelQtaPzNecessari);
		
		layoutStatoBancale.addComponent(new Label(" "));

		Label labelQtaScatoleCaricateInBancale = new Label("<b>Qtà. scatole caricate: " + statoBancale.getQtyOfBoxesInThePallet() + "</b>");
		setInfoScatolaStyle(labelQtaScatoleCaricateInBancale);
		labelQtaScatoleCaricateInBancale.setContentMode(com.vaadin.shared.ui.ContentMode.HTML);
		layoutStatoBancale.addComponent(labelQtaScatoleCaricateInBancale);

		Label labelQtaPzCaricatiInBancale = new Label("<b>Qtà. tot. pz caricati: " + statoBancale.getQtyOfPcsInThePallet() + "</b>");
		setInfoScatolaStyle(labelQtaPzCaricatiInBancale);
		labelQtaPzCaricatiInBancale.setContentMode(com.vaadin.shared.ui.ContentMode.HTML);
		layoutStatoBancale.addComponent(labelQtaPzCaricatiInBancale);
		
		if(labelQtaScatoleMancanti != null) {
			layoutStatoBancale.addComponent(labelQtaScatoleMancanti);
		}

		if(labelQtaPzMancanti != null) {
			layoutStatoBancale.addComponent(labelQtaPzMancanti);
		}

		if(labelQtaScatolePzSonoCorrette != null) {
			layoutStatoBancale.addComponent(labelQtaScatolePzSonoCorrette);
		}
		
//		HorizontalSplitPanel pezziScatolaHorizontalLayout = new HorizontalSplitPanel();
		HorizontalLayout pezziScatolaHorizontalLayout = new HorizontalLayout();
        pezziScatolaHorizontalLayout.setSizeFull();
        
//        layoutGridDatiImballo.setWidth(100, Unit.PERCENTAGE);
//        layoutStatoBancale.setWidth(100, Unit.PERCENTAGE);
//        pezziScatolaHorizontalLayout.setFirstComponent(layoutGridDatiImballo);
//        pezziScatolaHorizontalLayout.setSecondComponent(layoutStatoBancale);
        
        pezziScatolaHorizontalLayout.addComponent(layoutGridDatiImballo);
        pezziScatolaHorizontalLayout.addComponent(layoutStatoBancale);
        
        pezziScatolaHorizontalLayout.setExpandRatio(layoutGridDatiImballo, 0.7f);
        pezziScatolaHorizontalLayout.setExpandRatio(layoutStatoBancale, 0.3f);
//        pezziScatolaHorizontalLayout.setSplitPosition(900, Unit.PIXELS);

        pezziScatolaHorizontalLayout.setId(etichettaImballo.getCodiceEtichettaImballoSmeup());
		this.tabsheet.addTab(pezziScatolaHorizontalLayout, etichettaImballo.getCodiceEtichettaImballoSmeup()).setClosable(true);
		this.tabsheet.setSelectedTab((this.tabsheet.getComponentCount() - 1));
	}

	private void aggiungiTabDatiDataMatrix(Map<String, List<DatamatrixFasiEseguite>> mappaFasiEseguite, String datamatrix) {
		VerticalLayout layoutGridDatiImballo = new VerticalLayout();
		
//		Label titleCodiceProdotto = new Label(primoVistaPacking == null ? "" : primoVistaPacking.getCodiceProdotto());
		Label titleCodiceProdotto = new Label("Dati QrCode");
		setPezziScatolaGridTitleStyle(titleCodiceProdotto);
		titleCodiceProdotto.addStyleName(ValoTheme.LABEL_BOLD);
		layoutGridDatiImballo.addComponent(titleCodiceProdotto);
		
		Label lableEtichetta = new Label("Datamatrix: " + datamatrix);
		setInfoScatolaStyle(lableEtichetta);
		layoutGridDatiImballo.addComponent(lableEtichetta);
		
		//fare griglia e colonne
		Grid<DatamatrixFasiEseguite> gridDatiImballo = new Grid<DatamatrixFasiEseguite>();
		gridDatiImballo.setSizeFull();   
		gridDatiImballo.addColumn(v -> v.getDataMatrix()).setCaption("QRCode pz");
		gridDatiImballo.addColumn(v -> CommonUtils.DATETIMEFORMAT.format(v.getDataOra()), new TextRenderer("")).setCaption("Data/ora");
		gridDatiImballo.addColumn(v -> v.getFaseProcesso()).setCaption("Fase processo");
		gridDatiImballo.addColumn(v -> v.getImpianto()).setCaption("Operatore/Macchina");
		gridDatiImballo.addColumn(v -> v.getEsito()).setCaption("Esito");
		gridDatiImballo.addColumn(v -> v.getEsitoValore()).setCaption("Esito valore");
		gridDatiImballo.addColumn(v -> v.getImpianto()).setCaption("Impianto");
		gridDatiImballo.addColumn(v -> v.getDurezza()).setCaption("Durezza");
		gridDatiImballo.addColumn(v -> v.getNumeroFornata()).setCaption("N. fornata");
		gridDatiImballo.addColumn(v -> v.getFornitore()).setCaption("Fornitore");
		gridDatiImballo.addColumn(v -> v.getUtente()).setCaption("Operatore");
		gridDatiImballo.addColumn(v -> v.getNote()).setCaption("Note");
		gridDatiImballo.setColumnReorderingAllowed(true);

		List<DatamatrixFasiEseguite> listaDataSource = new ArrayList<DatamatrixFasiEseguite>();		
		//listaDataSource.sort(Comparator.comparing(dato -> dato.));

		for(List<DatamatrixFasiEseguite> listaVista : mappaFasiEseguite.values()) {
			listaDataSource.addAll(listaVista);
		} 
		
		//Ordinare dati per data/ora
		listaDataSource.sort(Comparator.comparing(DatamatrixFasiEseguite::getDataOra));
		
		gridDatiImballo.setItems(listaDataSource);
		
		layoutGridDatiImballo.addComponent(gridDatiImballo); 
		layoutGridDatiImballo.setId(datamatrix);
		this.tabsheet.addTab(layoutGridDatiImballo, datamatrix).setClosable(true);
		this.tabsheet.setSelectedTab((this.tabsheet.getComponentCount() - 1));
	}

	void setPezziScatolaGridTitleStyle(Label title) {
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H2);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
	}
    
    Utenti getCurrentUser() {
        return (Utenti) VaadinSession.getCurrent().getAttribute(Utenti.class.getName());
    }

	void addSpacigComponent(Integer expandRatio) {
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSizeFull();
    	addComponents(verticalLayout);
    	setExpandRatio(verticalLayout, expandRatio);
	}

	void setInfoScatolaStyle(Label title) {
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
	}
}
