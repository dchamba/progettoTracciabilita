package com.vaadin.demo.dashboard.view.trattamenti;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.demo.dashboard.component.NumericField;
import com.vaadin.demo.dashboard.component.model.FilterDatamatrix;
import com.vaadin.demo.dashboard.component.style.StyleUtils;
import com.vaadin.demo.dashboard.component.utils.CommonUtils;
import com.vaadin.demo.dashboard.component.utils.FasiProcessoUtils;
import com.vaadin.demo.dashboard.component.utils.UtentiUtils;
import com.vaadin.demo.dashboard.component.utils.ViewUtils;
import com.vaadin.demo.dashboard.component.view.MyCustomView;
import com.vaadin.demo.dashboard.data.model.Datamatrix;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiProcesso;
import com.vaadin.demo.dashboard.data.model.DatamatrixFasiProcessoTT;
import com.vaadin.demo.dashboard.data.model.FasiProcesso;
import com.vaadin.demo.dashboard.data.model.Prodotti;
import com.vaadin.demo.dashboard.data.model.VistaDatamatrixFasiProcessoTT;
import com.vaadin.demo.dashboard.data.repository.RepositoryDatamatrix;
import com.vaadin.demo.dashboard.data.repository.RepositoryDatamatrixFasiProcesso;
import com.vaadin.demo.dashboard.data.repository.RepositoryDatamatrixFasiProcessoTT;
import com.vaadin.demo.dashboard.data.repository.RepositoryProdotti;
import com.vaadin.demo.dashboard.data.repository.RepositoryProvider;
import com.vaadin.demo.dashboard.event.DashboardEventBus;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class TrattamentoView extends MyCustomView {
	private RepositoryDatamatrix repositoryDatamatrix = null;
	private RepositoryProdotti repositoryProdotti = null;
	private RepositoryDatamatrixFasiProcesso repositoryDatamatrixTrattamenti = null;
	private RepositoryDatamatrixFasiProcessoTT repositoryDatamatrixTrattamentiTT = null;
	private NumericField textNumeroFornata, textValoreDurezza;
	private TextField textDatamatrix, textOperatore;
	private Grid<VistaDatamatrixFasiProcessoTT> gridDatiCestaTrattamento = new Grid<VistaDatamatrixFasiProcessoTT>();
	private Label lableQtaPzFornataTrattamento, lableNumeroTrattamento;

	private Integer numeroFornata;

	public TrattamentoView() {
		setSizeFull();
		addStyleName("transactions");
		setMargin(false);
		setSpacing(false);

		this.repositoryDatamatrix = RepositoryProvider.getRepositoryDatamatrix();
		this.repositoryDatamatrixTrattamenti = RepositoryProvider.getRepositoryDatamatrixTrattamenti();
		this.repositoryDatamatrixTrattamentiTT = RepositoryProvider.getRepositoryDatamatrixTrattamentiTT();
		this.repositoryProdotti = RepositoryProvider.getRepositoryProdotti();

		DashboardEventBus.register(this);

		buildToolbar();
		// addSpacigComponent(1);
		buildDatamatrixFormVerticalLayoutContainer();
		// addSpacigComponent(1);
		builGridPezziFornata();
		this.setTrattamentoFieldsEnabled(false, true);
	}

	private void buildToolbar() {
		HorizontalLayout header = new HorizontalLayout();
		header.addStyleName(StyleUtils.viewHeaderStyle);
		Responsive.makeResponsive(header);

		Label title = new Label(ViewUtils.titoloTrattamentoHtt);
		title.setSizeUndefined();
		title.addStyleName(ValoTheme.LABEL_H1);
		title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		header.addComponent(title);

		addComponent(header);
		//setExpandRatio(header, 0.1f);
	}

	private void buildDatamatrixFormVerticalLayoutContainer() {

		textNumeroFornata = new NumericField("Numero trattamento (da 1 a 99999 e senza lettere)");
		textNumeroFornata.setPlaceholder("Numero trattamento");
		textNumeroFornata.addValueChangeListener(event -> checkNumeroFornataInserito());
		textNumeroFornata.setHeightUndefined();
		textNumeroFornata.setWidth("400px");
		// textNumeroFornata.setHeight("65px");
		textNumeroFornata.setMaxLength(5);
		// textNumeroFornata.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		textValoreDurezza = new NumericField("Durezza") {
			public com.vaadin.shared.Registration addValueChangeListener(ValueChangeListener<String> listener) {
				return null;
				
			};
		};
		textValoreDurezza.setPlaceholder("Durezza");
		textValoreDurezza.setHeightUndefined();
		textValoreDurezza.setWidth("400px");
		// textValoreDurezza.setHeight("65px");
		// textValoreDurezza.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		textDatamatrix = new TextField("Datamatrix");
		textDatamatrix.setPlaceholder("Datamatrix");
		textDatamatrix.setHeightUndefined();
		textDatamatrix.setWidth("400px");
		// textDatamatrix.setHeight("65px");
		// textDatamatrix.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		// textDatamatrix.setMaxLength(12);
    	textDatamatrix.addValueChangeListener(new ValueChangeListener<String>() {
			
			@Override
			public void valueChange(ValueChangeEvent<String> event) {
				checkDatamatrixInserito();	

		    	textDatamatrix.clear();
		    	textDatamatrix.setComponentError(null);
			}
		});

		textOperatore = new TextField("Operatore");
		textOperatore.setPlaceholder("Operatore");
		textOperatore.setHeightUndefined();
		textOperatore.setWidth("400px");
		// textOperatore.setHeight("65px");
		// textOperatore.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		// textOperatore.setMaxLength(12);

		VerticalLayout layoutCampiTrattamento = new VerticalLayout();
		layoutCampiTrattamento.setSizeFull();
		layoutCampiTrattamento.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		layoutCampiTrattamento.addComponent(textNumeroFornata);
		layoutCampiTrattamento.setExpandRatio(textNumeroFornata, 1);
		layoutCampiTrattamento.addComponent(textOperatore);
		layoutCampiTrattamento.setExpandRatio(textOperatore, 1);
		layoutCampiTrattamento.addComponent(textValoreDurezza);
		layoutCampiTrattamento.setExpandRatio(textValoreDurezza, 1);
		layoutCampiTrattamento.addComponent(textDatamatrix);
		layoutCampiTrattamento.setExpandRatio(textDatamatrix, 1);
		
		layoutCampiTrattamento.setComponentAlignment(textNumeroFornata, Alignment.MIDDLE_CENTER);
		layoutCampiTrattamento.setComponentAlignment(textOperatore, Alignment.MIDDLE_CENTER);
		layoutCampiTrattamento.setComponentAlignment(textValoreDurezza, Alignment.MIDDLE_CENTER);
		layoutCampiTrattamento.setComponentAlignment(textDatamatrix, Alignment.MIDDLE_CENTER);

		HorizontalLayout layoutNumeroFornataEQtaPezzi = new HorizontalLayout();

		lableQtaPzFornataTrattamento = new Label("Qtà totale pz in fornata: 0");
		//lableQtaPzFornataTrattamento.setWidth("800px");
		lableQtaPzFornataTrattamento.setSizeFull();
		lableQtaPzFornataTrattamento.addStyleName(ValoTheme.LABEL_H3);
		lableQtaPzFornataTrattamento.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		layoutNumeroFornataEQtaPezzi.addComponent(lableQtaPzFornataTrattamento);
		layoutNumeroFornataEQtaPezzi.setExpandRatio(lableQtaPzFornataTrattamento, 0.3f);
		layoutNumeroFornataEQtaPezzi.setComponentAlignment(lableQtaPzFornataTrattamento, Alignment.MIDDLE_LEFT);

		Label labelSpazioVuoto = new Label("");
		labelSpazioVuoto.setWidth("600px");
		layoutNumeroFornataEQtaPezzi.addComponent(labelSpazioVuoto);
		layoutNumeroFornataEQtaPezzi.setExpandRatio(labelSpazioVuoto, 0.3f);
		layoutNumeroFornataEQtaPezzi.setComponentAlignment(labelSpazioVuoto, Alignment.MIDDLE_CENTER);
		
		lableNumeroTrattamento = new Label("Numero trattamento : ");
		//lableNumeroTrattamento.setWidth("800px");
		lableNumeroTrattamento.setSizeFull();
		lableNumeroTrattamento.addStyleName(ValoTheme.LABEL_H3);
		lableNumeroTrattamento.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		layoutNumeroFornataEQtaPezzi.addComponent(lableNumeroTrattamento);
		layoutNumeroFornataEQtaPezzi.setExpandRatio(lableNumeroTrattamento, 0.3f);
		layoutNumeroFornataEQtaPezzi.setComponentAlignment(lableNumeroTrattamento, Alignment.MIDDLE_RIGHT);
		
		layoutCampiTrattamento.addComponent(layoutNumeroFornataEQtaPezzi);
		layoutCampiTrattamento.setExpandRatio(layoutNumeroFornataEQtaPezzi, 1);

		addComponent(layoutCampiTrattamento);
		setExpandRatio(layoutCampiTrattamento, 1);
	}

	private void builGridPezziFornata() {

		gridDatiCestaTrattamento.setSizeFull();

		// gridDatiCestaTrattamento.addColumn(v -> v.getNumeroFornata()).setCaption("N.
		// fornata");

		gridDatiCestaTrattamento.addColumn(v -> {
			String nFornata = UtentiUtils.getCurrentUser().getAzienda().getCodiceValoreDurezza()
					+ CommonUtils.fromIntConZeroIniziali(this.numeroFornata, 5);
			return nFornata;
		}).setCaption("N. fornata");

		gridDatiCestaTrattamento.addColumn(v -> v.getDataOra()).setCaption("Data e ora");
		gridDatiCestaTrattamento.addColumn(v -> v.getDataMatrix()).setCaption("Datamatrix / QrCode");
		gridDatiCestaTrattamento.addColumn(v -> v.getProdotto().getCodiceProdotto()).setCaption("Prodotto");
		// gridDatiCestaTrattamento.addColumn(v -> v.getNumero()).setCaption("N. x pezzo");
		gridDatiCestaTrattamento.addColumn(v -> v.getOperatore()).setCaption("Operatore");

		gridDatiCestaTrattamento.addColumn(v -> v.getValoreDurezza()).setCaption("Valore durezza");

		gridDatiCestaTrattamento.addColumn(v -> "Cancella", new ButtonRenderer(clickEvent -> {
			VistaDatamatrixFasiProcessoTT v = (VistaDatamatrixFasiProcessoTT) clickEvent.getItem();
			eliminaDatamatrixTrattamentiTT(v);
		})).setCaption("Elimina").setWidth(120);

		gridDatiCestaTrattamento.setColumnReorderingAllowed(true);
//		gridDatiCestaTrattamento.addSelectionListener(null)
		gridDatiCestaTrattamento.addItemClickListener(item -> {
			VistaDatamatrixFasiProcessoTT vista = item.getItem();
		});
		
		addComponent(gridDatiCestaTrattamento);
		setExpandRatio(gridDatiCestaTrattamento, 1);
	}

	private void setTrattamentoFieldsEnabled(boolean enabled, boolean azzeraCampi) {
		if(azzeraCampi) {
			this.textDatamatrix.clear();
			this.textValoreDurezza.clear();
			this.textOperatore.clear();
		}
		
		this.textValoreDurezza.setEnabled(enabled);
		this.textOperatore.setEnabled(enabled);
		this.textDatamatrix.setEnabled(enabled);
	}

	private void checkDatamatrixInserito() {
		if (this.numeroFornata == null || this.numeroFornata == 0) {
			// Verifico numero fornata
			ViewUtils.showErrorNotification("Inserire numero fornata");
			this.textNumeroFornata.focus();
			return;
		} else if (this.textOperatore.getValue().trim().isEmpty()) {
			// Verifico operatore compilato
			ViewUtils.showErrorNotification("Inserire nome operatore");
			this.textOperatore.focus();
			return;
		} else if (this.textDatamatrix.getValue().trim().isEmpty()) {
			return;
		}

		String dataMatrixInserito = this.textDatamatrix.getValue().trim();

		// verifico formato dmx
		Prodotti prodottoInserito = null;
		try {
			prodottoInserito = this.repositoryProdotti.getProdottoFromDatamatrixFormat(dataMatrixInserito, true);
		} catch (Exception e) {
			ViewUtils.showErrorNotification(e.getMessage());
			return;
		}

		// verifico doppione
		List<VistaDatamatrixFasiProcessoTT> vistaFaseDatamatrixTT = this.repositoryDatamatrixTrattamentiTT
				.getVistaDatamatrixFasiProcessoTT(this.numeroFornata,
						UtentiUtils.getCurrentUser().getAzienda().getIdAzienda(), dataMatrixInserito);
		if (vistaFaseDatamatrixTT != null && vistaFaseDatamatrixTT.size() > 0) {
			ViewUtils.showErrorNotification("Datamartix già inserito in fornata");
			return;
		}

		// verifico numero durezza corretto
		String valoreDurezzaInserito = this.textValoreDurezza.getValue().trim();
		Integer valoreDurezza = null;
		if (valoreDurezzaInserito.isEmpty()) {
		} else {
			valoreDurezza = Integer.parseInt(valoreDurezzaInserito);
			if (valoreDurezza < prodottoInserito.getMinValoreDurezza()) {
				ViewUtils.showErrorNotification("Valore durezza inserito NON corretto. ");
				// return;
			}
		}

		// verifico e inserico prodotto in scatola
		List<Datamatrix> listaDatamatrix = this.repositoryDatamatrix
				.getDataMatrix(new FilterDatamatrix(dataMatrixInserito), null).getResult();
		if (listaDatamatrix == null || listaDatamatrix.size() == 0) {
			ViewUtils.showErrorNotification("Pezzo non esiste nel sistema");
			return;
		}
		Datamatrix datamatrix = listaDatamatrix.get(0);

		// verifico salto processo fasi precedenti
		try {
			FasiProcessoUtils.controlloFasiEseguiteCorrettamente(prodottoInserito, datamatrix, FasiProcessoUtils.FasiProcessoLista.HB.toString());
		} catch (Exception e) {
			ViewUtils.showErrorNotification(e.getMessage());
			return;
		}

		this.salvaAggiornaDatamatrixFaseProcessoTT(datamatrix, valoreDurezza);
		
		textValoreDurezza.selectAll();
		textValoreDurezza.setComponentError(null);
		textValoreDurezza.focus();
	}

	private void salvaAggiornaDatamatrixFaseProcessoTT(Datamatrix dataMatrix, Integer valoreDurezza) {
		List<VistaDatamatrixFasiProcessoTT> vistaFaseDatamatrixTT = this.repositoryDatamatrixTrattamentiTT
				.getVistaDatamatrixFasiProcessoTT(this.numeroFornata,
						UtentiUtils.getCurrentUser().getAzienda().getIdAzienda(), dataMatrix.getDataMatrix());

		DatamatrixFasiProcessoTT dataMatrixFaseProcessoTT = null;
		if (vistaFaseDatamatrixTT == null || vistaFaseDatamatrixTT.size() == 0) {
			// Creo faseProcesso
			DatamatrixFasiProcesso faseProcessoTrattamento = new DatamatrixFasiProcesso();
			faseProcessoTrattamento.setAzienda(UtentiUtils.getCurrentUser().getAzienda());
			
			FasiProcesso faseProcessoHB = RepositoryProvider.repositoryFasiProcesso().getFaseProcessoPerCodice(FasiProcessoUtils.FasiProcessoLista.HB.toString());
			faseProcessoTrattamento.setFaseProcesso(faseProcessoHB);
			faseProcessoTrattamento.setUtenteOperatore(UtentiUtils.getCurrentUser());
			faseProcessoTrattamento.setDataMatrix(dataMatrix);
			faseProcessoTrattamento.setDataOra(new Date());
			faseProcessoTrattamento.setEliminato(false);
			this.repositoryDatamatrixTrattamenti.save(faseProcessoTrattamento);

			Integer idNuovaFaseProcessoTrattamento = this.repositoryDatamatrixTrattamenti.getDatamatrixFasiProcesso(dataMatrix.getIdDataMatrix(), 
														faseProcessoHB.getIdFaseProcesso()).getIdDatamatrixFaseProcesso();
			
			// Creo faseProcessoTT
			dataMatrixFaseProcessoTT = new DatamatrixFasiProcessoTT();
			dataMatrixFaseProcessoTT.setEliminato(false);
			dataMatrixFaseProcessoTT.setNumeroFornata(this.numeroFornata.toString());
			dataMatrixFaseProcessoTT.setIdDatamatrixFaseProcesso(idNuovaFaseProcessoTrattamento);
		} else {
			dataMatrixFaseProcessoTT = this.repositoryDatamatrixTrattamentiTT
					.getDatamatrixFasiProcessoTTById(vistaFaseDatamatrixTT.get(0).getIdDatamatrixFaseProcessoTT());
		}
		dataMatrixFaseProcessoTT.setOperatore(this.textOperatore.getValue().trim());
		dataMatrixFaseProcessoTT.setValoreDurezza(valoreDurezza);

		this.repositoryDatamatrixTrattamentiTT.salvaDatamatrixFasiProcessoTT(dataMatrixFaseProcessoTT);

		this.aggiornaGridDatamatrixTrattamentiTT();
	}

	private void checkNumeroFornataInserito() {
		String numeroFornataInserito = this.textNumeroFornata.getValue().trim();
		if(numeroFornataInserito.isEmpty()) {
			this.numeroFornata = null;
			this.aggiornaGridDatamatrixTrattamentiTT();
			return;
		}
		
		Integer numeroFornataDigitato = null;
		try {
			numeroFornataDigitato = Integer.parseInt(numeroFornataInserito);
		} catch (Exception ex) {
			ViewUtils.showErrorNotification("Formato numero fornata inserito non corretto");
		}

		if (this.numeroFornata == numeroFornataDigitato) { return; }
		else { this.numeroFornata = numeroFornataDigitato; }

		String numeroTrattamentoConAzienda = UtentiUtils.getCurrentUser().getAzienda().getCodiceValoreDurezza() + CommonUtils.fromIntConZeroIniziali(this.numeroFornata, 5);
		this.lableNumeroTrattamento.setValue("Numero trattamento : " + numeroTrattamentoConAzienda);
		this.setTrattamentoFieldsEnabled(true, true);

		this.aggiornaGridDatamatrixTrattamentiTT();
	}

	private void eliminaDatamatrixTrattamentiTT(VistaDatamatrixFasiProcessoTT v) {
		if (v != null) {
			DatamatrixFasiProcessoTT datamatrixFasiProcessoTT = this.repositoryDatamatrixTrattamentiTT.getDatamatrixFasiProcessoTTById(v.getIdDatamatrixFaseProcessoTT());
			datamatrixFasiProcessoTT.setEliminato(true);
			this.repositoryDatamatrixTrattamentiTT.salvaDatamatrixFasiProcessoTT(datamatrixFasiProcessoTT);

			DatamatrixFasiProcesso datamatrixFasiProcesso = this.repositoryDatamatrixTrattamenti.getByIdDatamatrixFasiProcesso(v.getIdDatamatrixFaseProcesso());
			datamatrixFasiProcesso.setEliminato(true);
			this.repositoryDatamatrixTrattamenti.salvaFaseProcesso(datamatrixFasiProcesso);
			
			this.aggiornaGridDatamatrixTrattamentiTT();
		}
	}

	private void aggiornaGridDatamatrixTrattamentiTT() {
		
		List<VistaDatamatrixFasiProcessoTT> dati = null;
		if (this.numeroFornata == null) {
			this.setTrattamentoFieldsEnabled(false, true);
		} else {
			dati = this.repositoryDatamatrixTrattamentiTT.getVistaDatamatrixFasiProcessoTT(this.numeroFornata,UtentiUtils.getCurrentUser().getAzienda().getIdAzienda(), null);
		}
		String numeroFornataConAzienda = this.numeroFornata == null ? "" : (UtentiUtils.getCurrentUser().getAzienda().getCodiceValoreDurezza() + CommonUtils.fromIntConZeroIniziali(this.numeroFornata, 5));
		this.lableNumeroTrattamento.setValue("Numero trattamento : " + numeroFornataConAzienda);
		
		if(dati == null) {  dati = new ArrayList<VistaDatamatrixFasiProcessoTT>(); }
		gridDatiCestaTrattamento.setItems(new ArrayList<VistaDatamatrixFasiProcessoTT>());
		gridDatiCestaTrattamento.setItems(dati);
		lableQtaPzFornataTrattamento.setValue("Qtà totale pz in fornata :  " + String.valueOf(dati.size()));
	}
}
