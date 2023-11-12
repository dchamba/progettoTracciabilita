package com.vaadin.demo.dashboard.view.fasiprocesso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.demo.dashboard.component.model.FilterDatamatrix;
import com.vaadin.demo.dashboard.component.style.StyleUtils;
import com.vaadin.demo.dashboard.component.utils.CommonUtils;
import com.vaadin.demo.dashboard.component.utils.FasiProcessoUtils;
import com.vaadin.demo.dashboard.component.utils.PermessiUtils;
import com.vaadin.demo.dashboard.component.utils.ViewUtils;
import com.vaadin.demo.dashboard.component.view.MyCustomView;
import com.vaadin.demo.dashboard.data.model.Datamatrix;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiProcesso;
import com.vaadin.demo.dashboard.data.model.FasiProcesso;
import com.vaadin.demo.dashboard.data.model.FasiProcessoProdotto;
import com.vaadin.demo.dashboard.data.model.Prodotti;
import com.vaadin.demo.dashboard.data.model.Utenti;
import com.vaadin.demo.dashboard.data.repository.RepositoryDatamatrix;
import com.vaadin.demo.dashboard.data.repository.RepositoryDatamatrixFasiProcesso;
import com.vaadin.demo.dashboard.data.repository.RepositoryFasiProcesso;
import com.vaadin.demo.dashboard.data.repository.RepositoryFasiProcessoProdotti;
import com.vaadin.demo.dashboard.data.repository.RepositoryProdotti;
import com.vaadin.demo.dashboard.data.repository.RepositoryProvider;
import com.vaadin.demo.dashboard.event.DashboardEventBus;
import com.vaadin.demo.dashboard.view.DashboardMenu;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public abstract class FasiProcessoView extends MyCustomView {

	private RepositoryFasiProcesso repositoryFasiProcesso = null;
	private RepositoryDatamatrix repositoryDatamatrix = null;
	private RepositoryProdotti repositoryProdotti = null;
	private RepositoryDatamatrixFasiProcesso repositoryDatamatrixFasiProcesso = null;
	
	private TextField textDatamatrix;
	private TextArea textAreaLetturePrecedenti;
	
	private FasiProcesso faseProcessoCorrente = null;
	
	public FasiProcessoView() {
        setSizeFull();
        addStyleName("transactions");
        setMargin(false);
        setSpacing(false);
        
        this.repositoryFasiProcesso = RepositoryProvider.repositoryFasiProcesso();
        this.repositoryDatamatrix = RepositoryProvider.getRepositoryDatamatrix();
        this.repositoryProdotti = RepositoryProvider.getRepositoryProdotti();
        this.repositoryDatamatrixFasiProcesso = RepositoryProvider.getRepositoryDatamatrixTrattamenti();

        String codiceFase = DashboardMenu.MENU_SELECTED.replace(PermessiUtils.ricerca_permessoFaseProcessoSingolo, "");
        this.faseProcessoCorrente = this.repositoryFasiProcesso.getFaseProcessoPerCodice(codiceFase);
        
        DashboardEventBus.register(this);

        buildToolbar();
        buildDatamatrixForm();
        //buildLetturePrecedenti();
	}

	private void buildToolbar() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName(StyleUtils.viewHeaderStyle);
        Responsive.makeResponsive(header);

        Label title = new Label(getViewTitle().toUpperCase());
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);
        
        //header.setHeight("100px");

        addComponent(header);
    	setExpandRatio(header, 2);
    }

	public abstract String getViewTitle();

	private void buildLetturePrecedenti() {
		textAreaLetturePrecedenti = new TextArea();
		textAreaLetturePrecedenti.setWordWrap(false);
        textAreaLetturePrecedenti.setValue("");
        textAreaLetturePrecedenti.setRows(10);
    	textAreaLetturePrecedenti.setEnabled(false);
    	textAreaLetturePrecedenti.setSizeFull();
    	
		Panel panelLetturePrecedenti = new Panel("Working history");
    	panelLetturePrecedenti.setContent(textAreaLetturePrecedenti);
    	
		addComponent(panelLetturePrecedenti);
    	setExpandRatio(panelLetturePrecedenti, 2);
	}

    private void buildDatamatrixForm() {
//    	layoutCampiDatamatrix = new VerticalLayout();
//    	layoutCampiDatamatrix.setSizeFull();
    	
	    ThemeResource resource = new ThemeResource("img/dpmreading.png");
	    Image logo = new Image("", resource);
	    logo.setWidth("350px");
//    	MarginInfo marginInfo = new MarginInfo(false, false, true, false);
//    	marginInfo.do(10, 10, 20, 10);
//    	fields.setMargin(marginInfo);
	    
    	textDatamatrix = new TextField("Datamatrix");  
    	textDatamatrix.setPlaceholder("Datamatrix");
    	//textDatamatrix.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
    	textDatamatrix.setMaxLength(30);
    	textDatamatrix.addValueChangeListener(new ValueChangeListener<String>() {
			
			@Override
			public void valueChange(ValueChangeEvent<String> event) {
				String codiceDataMatrixInserito = textDatamatrix.getValue().trim();

		    	textDatamatrix.setComponentError(null);
				if (codiceDataMatrixInserito.isEmpty()) {
					return;
				}

				checkAndSaveDatamatrixFaseProcesso(codiceDataMatrixInserito);
				
//				if (faseProcessoCorrente.getInserisciFaseProcesso()) {
//					checkAndSaveDatamatrixFaseProcesso(codiceDataMatrixInserito);
//				} else if (faseProcessoCorrente.getInserisciFaseProcesso()) {
//					checkAndSaveDatamatrix(codiceDataMatrixInserito);
//				}

		    	textDatamatrix.setValue("");
		    	textDatamatrix.setComponentError(null);
			}
		});
    	textDatamatrix.setHeight("65px");
    	textDatamatrix.setWidth("500px");

//        addComponent(logo);
//        setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
//    	setExpandRatio(logo, (float) 0.3);
//    	addSpacigComponent(1);
////    	layoutCampiDatamatrix.addComponents(logo);
////    	layoutCampiDatamatrix.setComponentAlignment(logo, Alignment.TOP_CENTER);
//    	layoutCampiDatamatrix.addComponents(textDatamatrix);
//    	layoutCampiDatamatrix.setComponentAlignment(textDatamatrix, Alignment.MIDDLE_CENTER);
//
//    	addSpacigComponent(1);
//    	
//        addComponent(this.layoutCampiDatamatrix);
//    	setExpandRatio(layoutCampiDatamatrix, 2);
//    	
//    	addSpacigComponent(1);
    	
    	VerticalLayout layoutDatamatrix = new VerticalLayout();
    	layoutDatamatrix.setSizeFull();
    	layoutDatamatrix.addComponents(textDatamatrix);
    	layoutDatamatrix.setComponentAlignment(textDatamatrix, Alignment.MIDDLE_CENTER);
    	layoutDatamatrix.addComponent(logo);
    	layoutDatamatrix.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
//    	addSpacigComponent(1);
//    	layoutCampiDatamatrix.addComponents(textDatamatrix);
//    	layoutCampiDatamatrix.setComponentAlignment(textDatamatrix, Alignment.MIDDLE_CENTER);

//    	addSpacigComponent(1);
    	
//    	layoutDatamatrix.addComponent(this.layoutCampiDatamatrix);
//    	setExpandRatio(layoutCampiDatamatrix, 2);
//    	
//    	addSpacigComponent(1);
    	
    	VerticalLayout layoutHistory = new VerticalLayout();
    	layoutHistory.setSizeFull();

    	textAreaLetturePrecedenti = new TextArea();
		textAreaLetturePrecedenti.setWordWrap(false);
        textAreaLetturePrecedenti.setValue("");
        textAreaLetturePrecedenti.setRows(20);
    	textAreaLetturePrecedenti.setEnabled(false);
    	textAreaLetturePrecedenti.setSizeFull();
    	
		Panel panelLetturePrecedenti = new Panel("Working history");
    	panelLetturePrecedenti.setContent(textAreaLetturePrecedenti);
    	layoutHistory.addComponent(panelLetturePrecedenti);
    	//layoutHistory.setExpandRatio(panelLetturePrecedenti, 1);
    	
        HorizontalLayout fields = new HorizontalLayout();
        fields.setSizeFull();
        fields.addComponents(layoutDatamatrix);
        fields.addComponents(layoutHistory);
        
        addComponent(fields);
    	setExpandRatio(fields, 8);
	}
    
	private void addSpacigComponent(Integer expandRatio) {
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSizeFull();
    	addComponents(verticalLayout);
    	setExpandRatio(verticalLayout, expandRatio);
	}
    
	private void checkAndSaveDatamatrixFaseProcesso(String codiceDataMatrixInserito) {		
		try {
			//Verifico il formato e carico il relativo prodotto
			Prodotti prodottoCorrente = this.repositoryProdotti.getProdottoFromDatamatrixFormat(codiceDataMatrixInserito, true);			
			
	    	List<Datamatrix> listaDatamatrix = this.repositoryDatamatrix.getDataMatrix(new FilterDatamatrix(codiceDataMatrixInserito), null).getResult();
	    	
	    	Datamatrix datamatrixCorrente = null;
	    	if(listaDatamatrix != null && listaDatamatrix.size() > 0) {
	    		datamatrixCorrente = listaDatamatrix.get(0);
	    	} else {
				if (this.faseProcessoCorrente.getCreaDatamatrixSeNonEsiste()) {
		    		//Creo il datamatrix
		        	Datamatrix datamatrixNuovo = new Datamatrix();
		        	datamatrixNuovo.setEliminato(false);
		        	datamatrixNuovo.setDataMatrix(codiceDataMatrixInserito);
		        	datamatrixNuovo.setProddoto(prodottoCorrente);
		        	datamatrixNuovo.setUtenteCreatore(getCurrentUser());
		        	datamatrixNuovo.setDataOraUltimaModifica(new Date());
		        	datamatrixNuovo.setUtenteUltimaModifica(getCurrentUser());
		        	datamatrixNuovo.setDataOraCreazione(new Date());
		        	datamatrixNuovo.setPartNumber("");
		        	datamatrixNuovo.setCodiceInternoProdotto("");
		        	datamatrixNuovo.setStato("I");
		        	datamatrixNuovo.setDataOraCreazione(new Date());
		        	this.repositoryDatamatrix.salva(datamatrixNuovo);
		        	datamatrixCorrente = this.repositoryDatamatrix.getDataMatrix(new FilterDatamatrix(codiceDataMatrixInserito), null).getResult().get(0);
				} else {
	        		throw new Exception("Datamatrix non presente in archivio");
				}
	    	}
	    	
	    	//Verifico duplicato fase
	    	DatamatrixFasiProcesso datamatrixFaseProcessoEsistente = this.repositoryDatamatrixFasiProcesso.getDatamatrixFasiProcesso(datamatrixCorrente.getIdDataMatrix(), this.faseProcessoCorrente.getIdFaseProcesso());
	    	if(datamatrixFaseProcessoEsistente != null) {
	    		String utenteOperatore = datamatrixFaseProcessoEsistente.getUtenteOperatore().getNomeCognome();
	    		String dataOra = CommonUtils.DATETIMEFORMAT.format(datamatrixFaseProcessoEsistente.getDataOra());
        		throw new Exception("Pezzo già processato per questa fase il " + dataOra + " da utente : " + utenteOperatore);
	    	}
	    	
	    	FasiProcessoUtils.controlloFasiEseguiteCorrettamente(prodottoCorrente, datamatrixCorrente, this.faseProcessoCorrente.getCodiceFase());
	    	
//        	//Verifico il salto processo
//        	/*
//        	 * 1) FILTRO PER PRODOTTO, LA LISTA DELLE FASI(ORDINATA PER ORDINE)
//        	 * 2) A PARTIRE DALLA FASE ATTUALE VERIFICO SE QUELLE PRECEDENTI CI SONO
//        	 * */
//	    	if(faseProcessoCorrente.getVerificaSaltoFaseProcesso()) {
//	    		List<DatamatrixFasiProcesso> listaFasiProcessoEseguiteSuDatamatrix = this.repositoryDatamatrixFasiProcesso.getListaDatamatrixFasiProcesso(datamatrixCorrente.getIdDataMatrix());
//	    		List<String> codiciFasiEseguitiSuDatamatrix = new ArrayList<String>();
//	    		if(listaFasiProcessoEseguiteSuDatamatrix != null && listaFasiProcessoEseguiteSuDatamatrix.size() > 0) {
//		    		for (DatamatrixFasiProcesso datamatrixFaseProcesso : listaFasiProcessoEseguiteSuDatamatrix) {
//		    			codiciFasiEseguitiSuDatamatrix.add(datamatrixFaseProcesso.getFaseProcesso().getCodiceFase());
//					} 
//	    		}
//	    		
//	    		Integer currentFaseOrdine = null;
//	    		FasiProcessoProdotto currentFaseProcessoProdotto = this.repositoryFasiProcessoProdotto.getFaseProcessoProdotto(prodottoCorrente.getIdProdotto(), faseProcessoCorrente.getIdFaseProcesso());
//	    		if(currentFaseProcessoProdotto != null && currentFaseProcessoProdotto.getOrdine() != null) {
//	    			currentFaseOrdine = currentFaseProcessoProdotto.getOrdine();
//	    			List<FasiProcessoProdotto> listaFasiProcessoProdottoPrecedentiFaseAttuale = this.repositoryFasiProcessoProdotto.getFasiProcessoProdottoFinoAOrdine(currentFaseOrdine, prodottoCorrente.getIdProdotto());
//	    			if(listaFasiProcessoProdottoPrecedentiFaseAttuale != null && listaFasiProcessoProdottoPrecedentiFaseAttuale.size() > 0) {
//		    			for (FasiProcessoProdotto fasiProcessoProdotto : listaFasiProcessoProdottoPrecedentiFaseAttuale) {
//		    				if(!codiciFasiEseguitiSuDatamatrix.contains(fasiProcessoProdotto.getFaseProcesso().getCodiceFase())) {
//				        		throw new Exception("DATAMATRIX NON E' STATO PROCESSATO PER LA FASE: " + fasiProcessoProdotto.getFaseProcesso().getDescrizione());
////		    					for (DatamatrixFasiProcesso faseProcesso : listaFasiProcessoDatamatrix) {
////		    		    			if(faseProcesso.getFaseProcesso().getCodiceFase().equals(codiciFasiEseguitiSuDatamatrix)) {
////						        		throw new Exception("DATAMATRIX NON E' STATO PROCESSATO PER LA FASE: " + faseProcesso.getFaseProcesso().getDescrizione());
////		    		    			}
////		    					} 
//		    				}
//						}
//		    		}
//	    		}
//	    	}

	    	//Inserisco Fase processo per Datamatrix
        	DatamatrixFasiProcesso datamatrixFaseProcesso = new DatamatrixFasiProcesso();
        	datamatrixFaseProcesso.setDataMatrix(datamatrixCorrente);
        	datamatrixFaseProcesso.setDataOra(new Date());
        	datamatrixFaseProcesso.setAzienda(getCurrentUser().getAzienda());
        	datamatrixFaseProcesso.setUtenteOperatore(getCurrentUser());
        	datamatrixFaseProcesso.setEliminato(false);
        	datamatrixFaseProcesso.setFaseProcesso(this.faseProcessoCorrente);
        	this.repositoryDatamatrixFasiProcesso.salvaFaseProcesso(datamatrixFaseProcesso);

	    	ViewUtils.showSuccessfullNotification("Datamatrix " + codiceDataMatrixInserito + " salvato correttamente");
	    	inserisciLetturaCorrenteInTextBox(codiceDataMatrixInserito, prodottoCorrente == null ? "" : prodottoCorrente.getCodiceProdotto());
	    	
		} catch (Exception e) {
        	ViewUtils.showErrorNotificationWithDelay(e.getMessage(), 2500);
        	//logging
		}
	}

//	private void checkAndSaveDatamatrix(String codiceDataMatrixInserito) {
//		try {
//			//Verifico il formato e carico il relativo prodotto
//			Prodotti prodottoCorrente = this.repositoryProdotti.getProdottoFromDatamatrixFormat(codiceDataMatrixInserito);
//
//	    	List<Datamatrix> listaDatamatrix = this.repositoryDatamatrix.getDataMatrix(new FilterDatamatrix(codiceDataMatrixInserito), null).getResult();
//	    	
//	    	if(listaDatamatrix != null && listaDatamatrix.size() > 0) {
//	    		if(this.faseProcessoCorrente.getSegnalaErroreSeDMEsiste()) {
//		            throw new Exception("DATAMATRIX GIA' PRESENTE");
//	    		}
//	    	}
//	    	
//	    	if (this.faseProcessoCorrente.getInserisciDatamatrix()) {
//	    		//Creo il datamatrix
//	        	Datamatrix datamatrixNuovo = new Datamatrix();
//	        	datamatrixNuovo.setEliminato(false);
//	        	datamatrixNuovo.setDataMatrix(codiceDataMatrixInserito);
//	        	datamatrixNuovo.setProddoto(prodottoCorrente);
//	        	datamatrixNuovo.setUtenteCreatore(getCurrentUser());
//	        	datamatrixNuovo.setDataOraUltimaModifica(new Date());
//	        	datamatrixNuovo.setUtenteUltimaModifica(getCurrentUser());
//	        	datamatrixNuovo.setDataOraCreazione(new Date());
//	        	datamatrixNuovo.setPartNumber("");
//	        	datamatrixNuovo.setCodiceInternoProdotto("");
//	        	datamatrixNuovo.setStato("I");
//	        	datamatrixNuovo.setDataOraCreazione(new Date());
//	        	this.repositoryDatamatrix.salva(datamatrixNuovo);
//			}
//
//	    	ViewUtils.showSuccessfullNotification("DATAMATRIX " + codiceDataMatrixInserito + " SALVATO CORRETTAMENTE");
//	    	inserisciLetturaCorrenteInTextBox(codiceDataMatrixInserito, prodottoCorrente == null ? "" : prodottoCorrente.getCodiceProdotto());
//	    	
//		} catch (Exception e) {
//        	ViewUtils.showErrorNotification(e.getMessage());
//        	//logging
//		}
//	}
    
    private void inserisciLetturaCorrenteInTextBox(String datamatrix, String prodotto) {
    	String testoCorrente = "\r\n Datamatrix: " + datamatrix + "\t\t Codice: " + prodotto;
    	testoCorrente += "\r\n" + this.textAreaLetturePrecedenti.getValue();
    	this.textAreaLetturePrecedenti.setValue(testoCorrente);
	}
    
    private Utenti getCurrentUser() {
        return (Utenti) VaadinSession.getCurrent().getAttribute(Utenti.class.getName());
    }

}
