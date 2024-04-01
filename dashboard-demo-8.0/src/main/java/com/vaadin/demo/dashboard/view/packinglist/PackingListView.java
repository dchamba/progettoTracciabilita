package com.vaadin.demo.dashboard.view.packinglist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import com.vaadin.demo.dashboard.component.model.FilterDatamatrix;
import com.vaadin.demo.dashboard.component.utils.CustomPopupWindow;
import com.vaadin.demo.dashboard.component.utils.FasiProcessoUtils;
import com.vaadin.demo.dashboard.component.utils.FasiProcessoUtils.FasiProcessoLista;
import com.vaadin.demo.dashboard.component.utils.PermessiUtils;
import com.vaadin.demo.dashboard.component.utils.ViewUtils;
import com.vaadin.demo.dashboard.component.view.MyCustomView;
import com.vaadin.demo.dashboard.data.model.CriteriBloccoDatamatrix;
import com.vaadin.demo.dashboard.data.model.Datamatrix;
import com.vaadin.demo.dashboard.data.model.EtichetteImballi;
import com.vaadin.demo.dashboard.data.model.EtichettePezzi;
import com.vaadin.demo.dashboard.data.model.FasiProcesso;
import com.vaadin.demo.dashboard.data.model.Prodotti;
import com.vaadin.demo.dashboard.data.model.TipoImballi;
import com.vaadin.demo.dashboard.data.model.Utenti;
import com.vaadin.demo.dashboard.data.model.VistaPackingList;
import com.vaadin.demo.dashboard.data.repository.RepositoryDatamatrix;
import com.vaadin.demo.dashboard.data.repository.RepositoryDatamatrixFasiProcesso;
import com.vaadin.demo.dashboard.data.repository.RepositoryFasiProcesso;
import com.vaadin.demo.dashboard.data.repository.RepositoryFasiProcessoProdotti;
import com.vaadin.demo.dashboard.data.repository.RepositoryImballi;
import com.vaadin.demo.dashboard.data.repository.RepositoryProdotti;
import com.vaadin.demo.dashboard.data.repository.RepositoryProveTenuta;
import com.vaadin.demo.dashboard.data.repository.RepositoryProvider;
import com.vaadin.demo.dashboard.data.repository.RepositoryUtils;
import com.vaadin.demo.dashboard.event.DashboardEventBus;
import com.vaadin.demo.dashboard.view.DashboardMenu;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

public class PackingListView extends MyCustomView {

	RepositoryDatamatrix repositoryDatamatrix = null;
	RepositoryProdotti repositoryProdotti = null;
	RepositoryImballi repositoryImballi = null;
	RepositoryProveTenuta repositoryProveTenuta = null;

	RepositoryFasiProcesso repositoryFasiProcesso = null;
	RepositoryDatamatrixFasiProcesso repositoryDatamatrixFasiProcesso = null;
	RepositoryFasiProcessoProdotti repositoryFasiProcessoProdotto = null;
		
	FasiProcesso faseProcessoCorrente = null;
	
	List<Prodotti> listaProdottiPackingList = new ArrayList<Prodotti>();
	List<TipoImballi> listaTipoImballi = new ArrayList<TipoImballi>();

	Grid<VistaPackingList> gridFia05 = new Grid<VistaPackingList>();
	Grid<VistaPackingList> gridPan3 = new Grid<VistaPackingList>();
	Grid<VistaPackingList> gridPan4 = new Grid<VistaPackingList>();
	//Grid<VistaPackingList> gridPan5 = new Grid<VistaPackingList>();
	Grid<VistaPackingList> gridPan10 = new Grid<VistaPackingList>();
	Grid<VistaPackingList> gridGes1 = new Grid<VistaPackingList>();
	Grid<VistaPackingList> gridGes2 = new Grid<VistaPackingList>();
	Grid<VistaPackingList> gridPan11 = new Grid<VistaPackingList>();
	Grid<VistaPackingList> gridAttco01 = new Grid<VistaPackingList>();
	Grid<VistaPackingList> gridAttco02 = new Grid<VistaPackingList>();

	Grid<VistaPackingList> gridOmr01 = new Grid<VistaPackingList>();
	Grid<VistaPackingList> gridOmr02 = new Grid<VistaPackingList>();
	Grid<VistaPackingList> gridOmr03 = new Grid<VistaPackingList>();
	Grid<VistaPackingList> gridOmr04 = new Grid<VistaPackingList>();
	Grid<VistaPackingList> gridOmr05 = new Grid<VistaPackingList>();
	Grid<VistaPackingList> gridOmr06 = new Grid<VistaPackingList>();
	Grid<VistaPackingList> gridOmr07 = new Grid<VistaPackingList>();
	Grid<VistaPackingList> gridOmr08 = new Grid<VistaPackingList>();
	
	TextField textDatamatrix;
	
	boolean verificaDoppioneDatamatrixPackingList = false;
	boolean verificaFasiProcessoPrecedentiDatamatrix = false;
	
	public PackingListView() {
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
        this.repositoryFasiProcessoProdotto = RepositoryProvider.repositoryFasiProcessoProdotti();
        
        String codiceFase = DashboardMenu.MENU_SELECTED.replace(PermessiUtils.ricerca_permessoFaseProcessoSingolo, "");
        this.faseProcessoCorrente = this.repositoryFasiProcesso.getFaseProcessoPerCodice(codiceFase);
        
        this.listaProdottiPackingList = this.repositoryProdotti.getProdottoByPackingListPermesso(getStringPermessoPackingList());
        this.listaTipoImballi = this.repositoryImballi.getTipoImballi();

        this.verificaDoppioneDatamatrixPackingList = Boolean.valueOf(RepositoryProvider.getRepositoryConfig().getConfigByChiave(RepositoryUtils.verificaDoppioneDatamatrixPackingList));
        this.verificaFasiProcessoPrecedentiDatamatrix = Boolean.valueOf(RepositoryProvider.getRepositoryConfig().getConfigByChiave(RepositoryUtils.verificaFasiProcessoPrecedentiDatamatrix));
        
        DashboardEventBus.register(this);

        //initializeGrid();
        buildDatamatrixForm();
	}
	
	void initializeGrid() { }

	String getStringPermessoPackingList() { return null; }
	
	String getStringFaseProcesso() { return FasiProcessoLista.FIN.toString(); }

	void buildDatamatrixForm() { }

	void checkAndSaveDatamatrix(String codiceDataMatrixInserito) {
		try {
			//Verifico che il formato del valore sia compatibile con uno dei DM
			Optional<Prodotti> prodottoCaricoPerFormatoDmc = this.listaProdottiPackingList.stream().filter(p -> Pattern.matches(p.getFormatoDatamatrix(), codiceDataMatrixInserito)).findFirst();
			Prodotti prodottoCorrente = prodottoCaricoPerFormatoDmc.isPresent() ? prodottoCaricoPerFormatoDmc.get() : null;
	        
	        if(prodottoCorrente == null) {
		        //verifico che non sia una scatola letta
		        if(verificaCodiceImballo(codiceDataMatrixInserito)) {
		        	
		        	//Verifico che la scatola attuale sia completa e chiedo conferma operatre di cambio scatola (in caso attuale scatola è parziale)
		        	Prodotti prodottoPerCodiceImballoInserito = this.listaProdottiPackingList.stream().filter(p -> codiceDataMatrixInserito.contains(p.getNumeroDisegno())).findFirst().get();
		        	int pzMancantiInScatola = getPzMancantiInScatolaAttualeRispettoQuellaNuova(prodottoPerCodiceImballoInserito);
		        	
		    		if(pzMancantiInScatola > 0) {
		    			String messaggio = "Scantola NON completa. Mancano " + pzMancantiInScatola + " pz! \rVuoi proseguire comunque con la nuova scatola ?";
		    			CustomPopupWindow window = new CustomPopupWindow("Conferma", messaggio);
		    			window.addCloseListener(new CloseListener() {
							
							@Override
							public void windowClose(CloseEvent e) {
								if(window.getEsito()) {
									creaOCaricaImballo(codiceDataMatrixInserito, prodottoPerCodiceImballoInserito);
								} else {
									
								}
							}
						});
		    			getUI().addWindow(window);
		    		} else {
						creaOCaricaImballo(codiceDataMatrixInserito, prodottoPerCodiceImballoInserito);
		    		}
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
		    		datamatrix.setDataOraCreazione(new Date());
		        	
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
	    		
	    		//controllo fasi precedenti eseguite correttamente
	    		controlloFasiEseguiteCorrettamente(prodottoCorrente, datamatrix);

	    		//verifico che non ci siano criteri di blocco per questo datamtrix
	    		CriteriBloccoDatamatrix criterioBloccoDmx = RepositoryProvider.getRepositoryCriteriBloccoDatamatrix().getCriteriBloccoDatamatrix(datamatrix, isViewPackingList());
	    		if(criterioBloccoDmx != null) {
	    			throw new Exception(criterioBloccoDmx.getMessaggioUtente());
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

    protected void creaOCaricaImballo(String codiceDataMatrixInserito, Prodotti prodottoPerCodiceImballoInserito) {
    	EtichetteImballi etichettaImballo = repositoryImballi.getEtichettaImballo(codiceDataMatrixInserito);
     	if (etichettaImballo == null) {
    		etichettaImballo = new EtichetteImballi();
    		etichettaImballo.setCodiceEtichetta(codiceDataMatrixInserito);
    		etichettaImballo.setEliminato(false);
    		TipoImballi tipoImballoPerProdotto = listaTipoImballi.stream().filter(t -> t.getProdotto().getIdProdotto() == prodottoPerCodiceImballoInserito.getIdProdotto()).findFirst().get();
    		etichettaImballo.setTipoImballo(tipoImballoPerProdotto);						        		
    		repositoryImballi.salva(etichettaImballo);						
    	} 
		aggiornaVariabileEtichettaImballo(etichettaImballo);
		aggiornaDatiImballi();
    	ViewUtils.showSuccessfullNotification("Etichetta scatola inserita");
	}

    int getPzMancantiInScatolaAttualeRispettoQuellaNuova(Prodotti prodottoEtichettaDaVerificare) { return 0; }

	boolean verificaCodiceImballo(String codiceDataMatrixInserito) {
		return codiceDataMatrixInserito.length() > 70 && codiceDataMatrixInserito.length() < 90;
	}

	void controlloFasiEseguiteCorrettamente(Prodotti prodottoCorrente, Datamatrix datamatrix) throws Exception {
    	FasiProcessoUtils.controlloFasiEseguiteCorrettamente(prodottoCorrente, datamatrix, getStringFaseProcesso());
    }

	void aggiornaVariabileEtichettaImballo(EtichetteImballi etichettaImballo) { }

	EtichetteImballi getVariabileEtichettaImballo(String codiceProdotto) { return null; }

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

	void eliminaPezzoDaScatola(VistaPackingList v) {
		if(v != null) {
			EtichettePezzi etichettaPezzo = this.repositoryImballi.getEtichettaPezzo(v.getIdEtichettaPezzo(), null);
			etichettaPezzo.setEliminato(true);
			this.repositoryImballi.salva(etichettaPezzo);
			
			this.aggiornaDatiImballi();
		}
	}

	void aggiornaDatiImballi() { }

	void setPezziScatolaGridTitleStyle(Label title) {
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H2);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
	}

	void setInfoScatolaStyle(Label title) {
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H3);
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
	
	public boolean isViewPackingList() { return true; }
}
