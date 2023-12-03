package com.vaadin.demo.dashboard.view.packinglist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.demo.dashboard.component.model.FilterDatamatrix;
import com.vaadin.demo.dashboard.component.style.StyleUtils;
import com.vaadin.demo.dashboard.component.utils.CommonUtils;
import com.vaadin.demo.dashboard.component.utils.PermessiUtils.PermessiUtentiLista;
import com.vaadin.demo.dashboard.component.utils.ViewUtils;
import com.vaadin.demo.dashboard.data.model.Datamatrix;
import com.vaadin.demo.dashboard.data.model.EtichetteImballi;
import com.vaadin.demo.dashboard.data.model.EtichettePezzi;
import com.vaadin.demo.dashboard.data.model.Prodotti;
import com.vaadin.demo.dashboard.data.model.TipoImballi;
import com.vaadin.demo.dashboard.data.model.VistaPackingList;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class PackingListFia05 extends PackingListView {

	private EtichetteImballi etichettaImballoFia05;
	
	private Label lableQtaPzFia05;
	private Label lableEtichettaFia05;

	public PackingListFia05() {	}

	@Override
	void initializeGrid() {
		gridFia05 = new Grid<VistaPackingList>();
	}
	
	@Override
    void buildDatamatrixForm() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName(StyleUtils.viewHeaderStyle);
        Responsive.makeResponsive(header);
        
        Label title = new Label(getTitoloFasePackingListFia05());
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

    	HorizontalLayout layoutPulsantiEtichetta = new HorizontalLayout();
    	
    	Button buttonStampaEtichetta = new Button("Stampa etichetta");
    	buttonStampaEtichetta.setHeight("65px");
    	buttonStampaEtichetta.setWidth("300px");    	
    	buttonStampaEtichetta.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(etichettaImballoFia05 != null && etichettaImballoFia05.getIdEtichettaImballo() != getIdEtichettaImballoVuota()) {
					getUrlStampaEtichettaImballo();
					return;
				}
				
				if(etichettaImballoFia05 != null && etichettaImballoFia05.getIdEtichettaImballo() == getIdEtichettaImballoVuota()) {
					
					Integer totPezziPerScatola = etichettaImballoFia05.getTipoImballo().getQtaPezziPerScatola();
				   	
					ListDataProvider<VistaPackingList> dataProvider = (ListDataProvider<VistaPackingList>) gridFia05.getDataProvider();
					if (dataProvider.getItems().size() == totPezziPerScatola) {
						String codiceScatola = "0";
						codiceScatola += listaProdottiPackingList.get(0).getNumeroDisegno(); 
						codiceScatola += "0";
						codiceScatola += ".";
						codiceScatola += CommonUtils.formatDate(new Date(), CommonUtils.YEARTWODIGITFORMAT);
						codiceScatola += ".";
						codiceScatola += CommonUtils.formatDate(new Date(), CommonUtils.JULIANDAYFORMAT);
						codiceScatola += CommonUtils.formatDate(new Date(), CommonUtils.HOUMMINUTEFIVEDIGITFORMAT);
						
						EtichetteImballi etichettaImballoNuova =  new EtichetteImballi();
		        		etichettaImballoNuova.setCodiceEtichetta(codiceScatola);
		        		etichettaImballoNuova.setEliminato(false);
		        		
		        		Prodotti prodottoEtichettaNuova = null;
		        		for (Prodotti prodottoEtichetta : listaProdottiPackingList) {
		        			if(prodottoEtichetta.getNumeroDisegno() != null && codiceScatola.contains(prodottoEtichetta.getNumeroDisegno())) {
		        				prodottoEtichettaNuova = prodottoEtichetta;
		        			}
						}
		        		
		        		for (TipoImballi tipoImballoInCiclo : listaTipoImballi) {
		        			if(tipoImballoInCiclo.getProdotto().getIdProdotto() == prodottoEtichettaNuova.getIdProdotto()) {
		        				etichettaImballoNuova.setTipoImballo(tipoImballoInCiclo);
		        				break;
		        			}
		        		}
		        		
		        		
		        		repositoryImballi.salva(etichettaImballoNuova);
						aggiornaVariabileEtichettaImballo(etichettaImballoNuova);
						repositoryImballi.assegnaIdEtichettaImballo(getIdEtichettaImballoVuota(), etichettaImballoNuova);
						aggiornaDatiImballi();
						getUrlStampaEtichettaImballo();
					} else {
				    	ViewUtils.showErrorNotification("E' necessario raggiungere qtà pz in scatola : " + totPezziPerScatola);
				    	return;
					}
				}
			}
		});

    	VerticalLayout layoutDatamatrix = new VerticalLayout();
    	layoutDatamatrix.setSizeFull();
    	layoutDatamatrix.addComponents(textDatamatrix);
    	layoutDatamatrix.setComponentAlignment(textDatamatrix, Alignment.MIDDLE_CENTER);
//    	layoutDatamatrix.addComponents(buttonStampaEtichetta);
//    	layoutDatamatrix.setComponentAlignment(buttonStampaEtichetta, Alignment);

    	VerticalLayout layoutPezziScatolaFia05 = new VerticalLayout();
    	//layoutPezziScatolaFia05.setWidth("800px");
    	//layoutPezziScatolaFia05.setSizeFull();

    	Label titleFia05 = new Label(getTitoloGriglia());
        setPezziScatolaGridTitleStyle(titleFia05);    
        
    	layoutPezziScatolaFia05.addComponent(titleFia05);
    	
    	lableEtichettaFia05 = new Label("Cod.etic.:");
        setInfoScatolaStyle(lableEtichettaFia05);
        
    	layoutPezziScatolaFia05.addComponent(lableEtichettaFia05);

    	addColumnToGrid(gridFia05);
    	
    	layoutPezziScatolaFia05.addComponent(gridFia05);

        lableQtaPzFia05 = new Label("Qtà pz in scatola: 0");
        setInfoScatolaStyle(lableQtaPzFia05);
        
    	layoutPezziScatolaFia05.addComponent(lableQtaPzFia05);

    	layoutPulsantiEtichetta.addComponent(buttonStampaEtichetta);
    	layoutPulsantiEtichetta.addComponent(buttonStampaEtichetta);
    	
    	layoutPezziScatolaFia05.addComponent(layoutPulsantiEtichetta);
    	
        HorizontalLayout pezziScatolaHorizontalLayout = new HorizontalLayout();
        pezziScatolaHorizontalLayout.addComponent(layoutPezziScatolaFia05);
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

        this.etichettaImballoFia05 = this.repositoryImballi.getEtichettaImballo(getIdEtichettaImballoVuota());
		this.aggiornaDatiImballi();
	}

	@Override
    void aggiornaDatiImballi() {
    	Integer idEtichettaImballoDaCaricare = getIdEtichettaImballoVuota();
		if(this.etichettaImballoFia05 != null) {
			idEtichettaImballoDaCaricare = this.etichettaImballoFia05.getIdEtichettaImballo();
    	}

		gridFia05.setItems(new ArrayList<VistaPackingList>());
		gridFia05.setItems(this.repositoryImballi.getVistaPackingListFromEtichettaScatola(null, idEtichettaImballoDaCaricare));

		lableEtichettaFia05.setValue("Cod.etic.:" + this.etichettaImballoFia05.getCodiceEtichetta());

    	ListDataProvider<VistaPackingList> dataProvider = (ListDataProvider<VistaPackingList>) this.gridFia05.getDataProvider();
		lableQtaPzFia05.setValue("Qtà pz in scatola:  " + String.valueOf(dataProvider.getItems().size()));
    }
	
	Integer getIdEtichettaImballoVuota() { return null; }

	@Override
    void aggiornaVariabileEtichettaImballo(EtichetteImballi etichettaImballo) {
		this.etichettaImballoFia05 = etichettaImballo;
	}

	@Override
    EtichetteImballi getVariabileEtichettaImballo(String codiceProdotto) {
		return this.etichettaImballoFia05;
	}

	@Override
	String getStringPermessoPackingList() {
		return PermessiUtentiLista.FASE_PROCESSO_PACKINGLISTFIA0504.toString();
	}

	@Override
	void checkAndSaveDatamatrix(String codiceDataMatrixInserito) {
		try {
			//Verifico che il formato del valore sia compatibile con uno dei DM
			Optional<Prodotti> prodottoCaricoPerFormatoDmc = this.listaProdottiPackingList.stream().filter(p -> Pattern.matches(p.getFormatoDatamatrix(), codiceDataMatrixInserito)).findFirst();
			Prodotti prodottoCorrente = prodottoCaricoPerFormatoDmc.isPresent() ? prodottoCaricoPerFormatoDmc.get() : null;
	        
	        if(prodottoCorrente == null) {
		        //verifico che non sia una scatola letta
		        if(codiceDataMatrixInserito.length() == 23 && codiceDataMatrixInserito.contains(this.etichettaImballoFia05.getTipoImballo().getProdotto().getNumeroDisegno())) {
		        	EtichetteImballi etichettaImballo = this.repositoryImballi.getEtichettaImballo(codiceDataMatrixInserito);
		         	if (etichettaImballo == null) {
		        		etichettaImballo = new EtichetteImballi();
		        		etichettaImballo.setCodiceEtichetta(codiceDataMatrixInserito);
		        		etichettaImballo.setEliminato(false);
		        		
		        		Prodotti prodottoEtichettaNuova = null;
		        		for (Prodotti prodottoEtichetta : this.listaProdottiPackingList) {
		        			if(prodottoEtichetta.getNumeroDisegno() != null && codiceDataMatrixInserito.contains(prodottoEtichetta.getNumeroDisegno())) {
		        				prodottoEtichettaNuova = prodottoEtichetta;
		        			}
		        			
						}
		        		for (TipoImballi tipoImballoInCiclo : this.listaTipoImballi) {
		        			if(tipoImballoInCiclo.getProdotto().getIdProdotto() == prodottoEtichettaNuova.getIdProdotto()) {
		        				etichettaImballo.setTipoImballo(tipoImballoInCiclo);
		        				break;
		        			}
		        		}
		        		
		        		this.repositoryImballi.salva(etichettaImballo);						
		        	} 
					this.aggiornaVariabileEtichettaImballo(etichettaImballo);
					this.aggiornaDatiImballi();
			    	ViewUtils.showSuccessfullNotification("Etichetta scatola inserita");
		        } else {
		            throw new Exception("Formato QrCode non riconosciuto");
		        }
	        } else {
	        	//verifico e inserico prodotto in scatola
		    	List<Datamatrix> listaDatamatrix = this.repositoryDatamatrix.getDataMatrix(new FilterDatamatrix(codiceDataMatrixInserito), null).getResult();
		    	Datamatrix datamatrix = null;
		    	
		    	if(listaDatamatrix == null || listaDatamatrix.size() == 0) {
		    		datamatrix = new Datamatrix();
		    		datamatrix.setEliminato(false);
		    		datamatrix.setDataMatrix(codiceDataMatrixInserito);
		    		datamatrix.setProddoto(prodottoCorrente);
		    		datamatrix.setUtenteCreatore(getCurrentUser());
		    		datamatrix.setDataOraUltimaModifica(new Date());
		    		datamatrix.setUtenteUltimaModifica(getCurrentUser());
		    		datamatrix.setDataOraCreazione(new Date());
		    		datamatrix.setPartNumber("");
		    		datamatrix.setCodiceInternoProdotto("");
		    		datamatrix.setStato("I");
		        	
		        	this.repositoryDatamatrix.salva(datamatrix);
		        	listaDatamatrix = this.repositoryDatamatrix.getDataMatrix(new FilterDatamatrix(codiceDataMatrixInserito), null).getResult();
		    	}
	    		datamatrix = listaDatamatrix.get(0);
	    		
	    		//Etichetta inserita per questo codice prodotto ??
	    		EtichetteImballi etichettaImballo = this.getVariabileEtichettaImballo(datamatrix.getProddoto().getCodiceProdotto());
	    		if(etichettaImballo == null) {
		            throw new Exception("Inserire codice etichetta per questo tipo di prodotto");
	    		}

	    		if (this.verificaDoppioneDatamatrixPackingList) {
		    		//Verifica doppione/pezzo già in packing list 
		    		List<VistaPackingList> vistaPackingList = this.repositoryImballi.getVistaPackingListByDatamatrix(datamatrix.getDataMatrix());
		    		if(vistaPackingList != null && vistaPackingList.size() > 0) {
			            throw new Exception("Qrcode doppio o pezzo già inserito in packing list");
		    		}
	    		}
	    		
	    		if (this.verificaFasiProcessoPrecedentiDatamatrix) {
		    		controlloFasiEseguiteCorrettamente(prodottoCorrente, datamatrix);
	    		}
	    		
	    		//scatola piena ??
	    		List<EtichettePezzi> etichettePezzi = this.repositoryImballi.getEtichettaPezzoByEtichettaImballo(etichettaImballo.getIdEtichettaImballo());
	    		if(etichettePezzi.size() >= etichettaImballo.getTipoImballo().getQtaPezziPerScatola()) {
		            throw new Exception("Scatola già piena, utilizzare una nuova etichetta");
	    		}
	    		
	    		EtichettePezzi nuovaEtichettaPezzo = new EtichettePezzi();
	    		nuovaEtichettaPezzo.setDatamatrix(datamatrix);
	    		nuovaEtichettaPezzo.setEliminato(false);
	    		nuovaEtichettaPezzo.setEtichettaImballo(etichettaImballo);
	    		nuovaEtichettaPezzo.setUtenteCreatore(getCurrentUser());
	    		nuovaEtichettaPezzo.setDataOraCreazione(new Date());
	    		nuovaEtichettaPezzo.setUtenteUltimaModifica(getCurrentUser());
	    		nuovaEtichettaPezzo.setDataOraUltimaModifca(new Date());
	    		this.repositoryImballi.salva(nuovaEtichettaPezzo);
	    		
	    		this.aggiornaDatiImballi();
		    	ViewUtils.showSuccessfullNotification("Pezzo inserito in scatola");
	        }	    	
		} catch (Exception e) {
          	ViewUtils.showErrorNotification(e.getMessage());
        	//logging
		}
	}
	
	private void getUrlStampaEtichettaImballo() {
		String url = Page.getCurrent().getLocation().getSchemeSpecificPart();
		url += ViewUtils.nomeStampaEtichettaImballoView;
		if(this.etichettaImballoFia05 != null && this.etichettaImballoFia05.getIdEtichettaImballo() != getIdEtichettaImballoVuota()) {
			url += this.etichettaImballoFia05.getIdEtichettaImballo();
		}

		getUI().getPage().open(url, "_blank");
	}

	String getTitoloFasePackingListFia05() {
		return null;
	}

	String getTitoloGriglia() {
		return null;
	}
}


