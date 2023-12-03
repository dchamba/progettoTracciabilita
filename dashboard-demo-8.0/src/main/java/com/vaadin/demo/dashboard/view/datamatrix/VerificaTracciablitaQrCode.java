package com.vaadin.demo.dashboard.view.datamatrix;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.demo.dashboard.component.model.StatoBancale;
import com.vaadin.demo.dashboard.component.style.StyleUtils;
import com.vaadin.demo.dashboard.component.utils.CommonUtils;
import com.vaadin.demo.dashboard.component.utils.ViewUtils;
import com.vaadin.demo.dashboard.component.view.MyCustomView;
import com.vaadin.demo.dashboard.data.model.AnimeImballi;
import com.vaadin.demo.dashboard.data.model.Datamatrix;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiEseguite;
import com.vaadin.demo.dashboard.data.model.EtichetteImballi;
import com.vaadin.demo.dashboard.data.model.Prodotti;
import com.vaadin.demo.dashboard.data.model.Utenti;
import com.vaadin.demo.dashboard.data.model.VistaPackingList;
import com.vaadin.demo.dashboard.data.repository.RepositoryAnimeImballi;
import com.vaadin.demo.dashboard.data.repository.RepositoryDatamatrix;
import com.vaadin.demo.dashboard.data.repository.RepositoryDatamatrixFasiProcesso;
import com.vaadin.demo.dashboard.data.repository.RepositoryFasiProcesso;
import com.vaadin.demo.dashboard.data.repository.RepositoryFasiProcessoProdotti;
import com.vaadin.demo.dashboard.data.repository.RepositoryImballi;
import com.vaadin.demo.dashboard.data.repository.RepositoryProdotti;
import com.vaadin.demo.dashboard.data.repository.RepositoryProveTenuta;
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
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.TextRenderer;
import com.vaadin.ui.themes.ValoTheme;

public class VerificaTracciablitaQrCode extends MyCustomView {

	private RepositoryDatamatrix repositoryDatamatrix = null;
	private RepositoryProdotti repositoryProdotti = null;
	private RepositoryImballi repositoryImballi = null;
	private RepositoryProveTenuta repositoryProveTenuta = null;

	private RepositoryFasiProcesso repositoryFasiProcesso = null;
	private RepositoryDatamatrixFasiProcesso repositoryDatamatrixFasiProcesso = null;
	//private RepositoryFasiProcessoProdotti repositoryFasiProcessoProdotto = null;

	private RepositoryVerificaQrCode repositoryVerificaQrCode = new RepositoryVerificaQrCode();
	
	private TextField textDatamatrix;

	private TabSheet tabsheet = new TabSheet();
	
	public VerificaTracciablitaQrCode() {
        setSizeFull();
        addStyleName("transactions");
        setMargin(false);
        setSpacing(false);

        this.repositoryDatamatrix = RepositoryProvider.getRepositoryDatamatrix();
        this.repositoryProdotti = RepositoryProvider.getRepositoryProdotti();
        this.repositoryImballi = RepositoryProvider.repositoryImballi();
        this.repositoryProveTenuta = RepositoryProvider.getRepositoryProveTenuta();
        
        this.repositoryFasiProcesso = RepositoryProvider.repositoryFasiProcesso();
        this.repositoryDatamatrixFasiProcesso = RepositoryProvider.getRepositoryDatamatrixTrattamenti();
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
			
			//Verifico che il formato del valore sia compatibile con uno dei DM
			Prodotti prodottoCorrente  = this.repositoryProdotti.getProdottoFromDatamatrixFormat(codiceDataMatrixInserito, false);
			EtichetteImballi etichettaImballo = this.repositoryImballi.getEtichettaImballo(codiceDataMatrixInserito);
			
	        if(prodottoCorrente != null) {
	        	Map<String, List<DatamatrixFasiEseguite>> mappaFasiEseguite = repositoryVerificaQrCode.getDatamatrixFasiEseguite(codiceDataMatrixInserito);
	        	//si tratta di qrcode inserito
	        	//componento con 3 griglie
	        	
	        } else if(etichettaImballo != null){
	        	this.aggiungiTabIballo(etichettaImballo);
	        } else {
	        	//verifica numero ddt
        		throw new Exception("Datamatrix/qrcode inserito non trovato in sistema");
	        }
		} catch (Exception e) {
          	ViewUtils.showErrorNotification(e.getMessage());
		}
	}

	private void aggiungiTabIballo(EtichetteImballi etichettaImballo) {
		List<VistaPackingList> vistaPackingList = this.repositoryImballi.getVistaPackingListFromEtichettaScatola(null, etichettaImballo.getIdEtichettaImballo());
		VistaPackingList primoVistaPacking = vistaPackingList.size() > 0 ? vistaPackingList.get(0) : null;
		StatoBancale statoBancale = this.repositoryImballi.getStatoBancale(etichettaImballo);
		
		VerticalLayout layoutGridDatiImballo = new VerticalLayout();
		
		Label titleCodiceProdotto = new Label(primoVistaPacking == null ? "" : primoVistaPacking.getCodiceProdotto());
		setPezziScatolaGridTitleStyle(titleCodiceProdotto);
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
		
		Label labelQtaScatoleMancanti = null;
		if(!statoBancale.isBoxesQtyPerPalletComplete()) {
			String testoMaxQtaImballo = "Scatola non completa! Necessari tot : " + etichettaImballo.getTipoImballo().getQtaPezziPerScatola() + " pz";
			testoMaxQtaImballo = "<b style='color:red;'>" + testoMaxQtaImballo + "</b>";
			Label lableQtaMaxImballo = new Label(testoMaxQtaImballo);
			setInfoScatolaStyle(lableQtaMaxImballo);
			lableQtaMaxImballo.setContentMode(com.vaadin.shared.ui.ContentMode.HTML);
			layoutGridDatiImballo.addComponent(lableQtaMaxImballo);
			
			int qtaPzMancanti = etichettaImballo.getTipoImballo().getQtaPezziPerScatola() - vistaPackingList.size();
			String qtaScatoleMantantiStringa = "Qtà. pz mancanti in pallet: " + qtaPzMancanti;
			qtaScatoleMantantiStringa = "<b style='color:red;'>" + qtaScatoleMantantiStringa;
			
			for(Map.Entry<String, Integer> bancaleNonCompleto : statoBancale.getBoxesWithMissingPcs().entrySet()) {
				qtaScatoleMantantiStringa += "<br>" + bancaleNonCompleto.getKey() + " " + bancaleNonCompleto.getValue() + " pz";
			}
			
			qtaScatoleMantantiStringa += "</b>";
			
			labelQtaScatoleMancanti = new Label(qtaScatoleMantantiStringa);
			setInfoScatolaStyle(labelQtaScatoleMancanti);
			labelQtaScatoleMancanti.setContentMode(com.vaadin.shared.ui.ContentMode.HTML);
		}
		
		
		//layoutGridDatiImballo.setId(etichettaImballo.getCodiceEtichettaImballoSmeup());
		

		VerticalLayout layoutStatoBancale = new VerticalLayout();
		
		Label labelNumeroBancale = new Label("Num. bancale : " + etichettaImballo.getCodiceEtichettaBancaleSmeup());
		setPezziScatolaGridTitleStyle(labelNumeroBancale);
		layoutStatoBancale.addComponent(labelNumeroBancale);
		
		Label labelQtaScatole = new Label("Qtà. scatole necessarie: " + etichettaImballo.getTipoImballo().getQtaScatolePerBancale());
		setInfoScatolaStyle(labelQtaScatole);
		layoutStatoBancale.addComponent(labelQtaScatole);

		if(labelQtaScatoleMancanti != null) {
			layoutStatoBancale.addComponent(labelQtaScatoleMancanti);
		}
		if(labelQtaScatoleMancanti != null) {
			layoutStatoBancale.addComponent(labelQtaScatoleMancanti);
		}
		
		layoutStatoBancale.addComponent(labelQtaScatole);
		

        HorizontalLayout pezziScatolaHorizontalLayout = new HorizontalLayout();
        pezziScatolaHorizontalLayout.setSizeFull();
        
        pezziScatolaHorizontalLayout.addComponent(layoutGridDatiImballo);
        pezziScatolaHorizontalLayout.addComponent(layoutStatoBancale);

        pezziScatolaHorizontalLayout.setExpandRatio(layoutGridDatiImballo, 0.7f);
        pezziScatolaHorizontalLayout.setExpandRatio(layoutStatoBancale, 0.3f);

        pezziScatolaHorizontalLayout.setId(etichettaImballo.getCodiceEtichettaImballoSmeup());
		this.tabsheet.addTab(pezziScatolaHorizontalLayout, etichettaImballo.getCodiceEtichettaImballoSmeup()).setClosable(true);
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
