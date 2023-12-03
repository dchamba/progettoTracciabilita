package com.vaadin.demo.dashboard.view.packinglist;

import java.util.ArrayList;
import java.util.Date;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.demo.dashboard.component.style.StyleUtils;
import com.vaadin.demo.dashboard.component.utils.CommonUtils;
import com.vaadin.demo.dashboard.component.utils.PermessiUtils.PermessiUtentiLista;
import com.vaadin.demo.dashboard.component.utils.ProdottiUtils.CodiciProdottiLista;
import com.vaadin.demo.dashboard.component.utils.ViewUtils;
import com.vaadin.demo.dashboard.data.model.EtichetteImballi;
import com.vaadin.demo.dashboard.data.model.Prodotti;
import com.vaadin.demo.dashboard.data.model.TipoImballi;
import com.vaadin.demo.dashboard.data.model.VistaPackingList;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class PackingListATTCOView extends PackingListView {

	private EtichetteImballi etichettaImballoAttco01, etichettaImballoAttco02;

	private Label lableQtaPzAttco01, lableQtaPzAttco02;
	private Label lableEtichettaAttco01, lableEtichettaAttco02;

	public PackingListATTCOView() {
	}

	@Override
	void initializeGrid() {
		gridAttco01 = new Grid<VistaPackingList>();
		gridAttco02 = new Grid<VistaPackingList>();
	}

	@Override
	void buildDatamatrixForm() {
		HorizontalLayout header = new HorizontalLayout();
		header.addStyleName(StyleUtils.viewHeaderStyle);
		Responsive.makeResponsive(header);

		this.gridAttco01 = new Grid<VistaPackingList>();
		this.gridAttco02 = new Grid<VistaPackingList>();

		Label title = new Label(ViewUtils.titoloFaseProcessoPackingListATTCO);
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

		VerticalLayout layoutPezziScatolaAtt01 = new VerticalLayout();
//    	layoutPezziScatolaGes1.setWidth("400px");
		VerticalLayout layoutPezziScatolaAtt02 = new VerticalLayout();
//    	layoutPezziScatolaGes2.setWidth("400px");
		
		Label titleAtt01 = new Label("Bodenplatte");
		setPezziScatolaGridTitleStyle(titleAtt01);
		Label titleAtt02 = new Label("Gehaeuse");
		setPezziScatolaGridTitleStyle(titleAtt02);

		layoutPezziScatolaAtt01.addComponent(titleAtt01);
		layoutPezziScatolaAtt02.addComponent(titleAtt02);

		lableEtichettaAttco01 = new Label("Cod.etic.:");
		setInfoScatolaStyle(lableEtichettaAttco01);
		lableEtichettaAttco02 = new Label("Cod.etic.:");
		setInfoScatolaStyle(lableEtichettaAttco02);
		layoutPezziScatolaAtt01.addComponent(lableEtichettaAttco01);
		layoutPezziScatolaAtt02.addComponent(lableEtichettaAttco02);

		addColumnToGrid(gridAttco01);
		addColumnToGrid(gridAttco02);
		layoutPezziScatolaAtt01.addComponent(gridAttco01);
		layoutPezziScatolaAtt02.addComponent(gridAttco02);

		lableQtaPzAttco01 = new Label("Qtà pz in scatola: 0");
		setInfoScatolaStyle(lableQtaPzAttco01);
		lableQtaPzAttco02 = new Label("Qtà pz in scatola: 0");
		setInfoScatolaStyle(lableQtaPzAttco02);

		layoutPezziScatolaAtt01.addComponent(lableQtaPzAttco01);
		layoutPezziScatolaAtt02.addComponent(lableQtaPzAttco02);

		layoutPezziScatolaAtt01.addComponent(lableQtaPzAttco01);
		layoutPezziScatolaAtt02.addComponent(lableQtaPzAttco02);
		layoutPezziScatolaAtt02.addComponent(lableQtaPzAttco02);

		Button buttonStampaEtichettaAttco01 = new Button("Stampa etichetta Bodenplatte");
		buttonStampaEtichettaAttco01.setHeight("65px");
		buttonStampaEtichettaAttco01.setWidth("300px");
		buttonStampaEtichettaAttco01.addClickListener(v -> this.stampaEtichetta(CodiciProdottiLista.ATTCO01.toString()));
		layoutPezziScatolaAtt01.addComponent(buttonStampaEtichettaAttco01);

		Button buttonStampaEtichettaAttco02 = new Button("Stampa etichetta Gehaeuse");
		buttonStampaEtichettaAttco02.setHeight("65px");
		buttonStampaEtichettaAttco02.setWidth("300px");
		buttonStampaEtichettaAttco02.addClickListener(v -> this.stampaEtichetta(CodiciProdottiLista.ATTCO02.toString()));
		layoutPezziScatolaAtt02.addComponent(buttonStampaEtichettaAttco02);

		HorizontalLayout pezziScatolaHorizontalLayout = new HorizontalLayout();
		pezziScatolaHorizontalLayout.addComponent(layoutPezziScatolaAtt01);
		pezziScatolaHorizontalLayout.addComponent(layoutPezziScatolaAtt02);
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
		// setExpandRatio(fields, 8);

        this.etichettaImballoAttco01 = this.repositoryImballi.getEtichettaImballo(getIdEtichettaImballoVuotaBondenplate());
        this.etichettaImballoAttco02 = this.repositoryImballi.getEtichettaImballo(getIdEtichettaImballoVuotaGehauese());
		this.aggiornaDatiImballi();
	}

	@Override
	void aggiornaDatiImballi() {
		if (this.etichettaImballoAttco02 != null) {
			gridAttco01.setItems(new ArrayList<VistaPackingList>());
			gridAttco01.setItems(this.repositoryImballi.getVistaPackingListFromEtichettaScatola(null, this.etichettaImballoAttco01.getIdEtichettaImballo()));

			lableEtichettaAttco01.setValue("Cod.etic.:" + this.etichettaImballoAttco01.getCodiceEtichettaImballoSmeup());

			ListDataProvider<VistaPackingList> dataProvider = (ListDataProvider<VistaPackingList>) gridAttco01.getDataProvider();
			lableQtaPzAttco01.setValue("Qtà pz in scatola:  " + String.valueOf(dataProvider.getItems().size()));
		}
		
		if (this.etichettaImballoAttco02 != null) {
			gridAttco02.setItems(new ArrayList<VistaPackingList>());
			gridAttco02.setItems(this.repositoryImballi.getVistaPackingListFromEtichettaScatola(null, this.etichettaImballoAttco02.getIdEtichettaImballo()));

			lableEtichettaAttco02.setValue("Cod.etic.:" + this.etichettaImballoAttco02.getCodiceEtichettaImballoSmeup());

			ListDataProvider<VistaPackingList> dataProvider = (ListDataProvider<VistaPackingList>) gridAttco02.getDataProvider();
			lableQtaPzAttco02.setValue("Qtà pz in scatola:  " + String.valueOf(dataProvider.getItems().size()));
		}
	}

	@Override
	void aggiornaVariabileEtichettaImballo(EtichetteImballi etichettaImballo) {
		if (etichettaImballo.getTipoImballo().getProdotto().getCodiceProdotto()
				.equals(CodiciProdottiLista.ATTCO01.toString())) {
			this.etichettaImballoAttco01 = etichettaImballo;
		} else if (etichettaImballo.getTipoImballo().getProdotto().getCodiceProdotto()
				.equals(CodiciProdottiLista.ATTCO02.toString())) {
			this.etichettaImballoAttco02 = etichettaImballo;
		}
	}

	@Override
	EtichetteImballi getVariabileEtichettaImballo(String codiceProdotto) {
		if (codiceProdotto.equals(CodiciProdottiLista.ATTCO01.toString())) {
			return this.etichettaImballoAttco01;
		} else if (codiceProdotto.equals(CodiciProdottiLista.ATTCO02.toString())) {
			return this.etichettaImballoAttco02;
		} else {
			return null;
		}

	}

	@Override
	String getStringPermessoPackingList() {
		return PermessiUtentiLista.FASE_PROCESSO_PACKINGLISTATTCO.toString();
	}

	Integer getIdEtichettaImballoVuotaBondenplate() {
		return -3;
	}

	Integer getIdEtichettaImballoVuotaGehauese() {
		return -4;
	}

	private void stampaEtichetta(String codiceProdottoStampato) {
		EtichetteImballi etichettaImballoDaStampre = null;
		Prodotti prodottoEtichettaDaStampre = null;
		Integer idEtichettaVuota = null;
		
		if (codiceProdottoStampato.equals(CodiciProdottiLista.ATTCO01.toString())) {
			etichettaImballoDaStampre = this.etichettaImballoAttco01;
			prodottoEtichettaDaStampre = this.listaProdottiPackingList.stream().filter(v -> v.getCodiceProdotto().equals(CodiciProdottiLista.ATTCO01.toString())).findFirst().get();
			idEtichettaVuota = getIdEtichettaImballoVuotaBondenplate();
		} else if (codiceProdottoStampato.equals(CodiciProdottiLista.ATTCO02.toString())) {
			etichettaImballoDaStampre = this.etichettaImballoAttco02;
			prodottoEtichettaDaStampre = this.listaProdottiPackingList.stream().filter(v -> v.getCodiceProdotto().equals(CodiciProdottiLista.ATTCO02.toString())).findFirst().get();
			idEtichettaVuota = getIdEtichettaImballoVuotaGehauese();
		}
		
		if(etichettaImballoDaStampre != null && etichettaImballoDaStampre.getIdEtichettaImballo() > 0) {
			getUrlStampaEtichettaImballo(etichettaImballoDaStampre);
			return;
		}
		
		if(etichettaImballoDaStampre != null && etichettaImballoDaStampre.getIdEtichettaImballo() <= 0) {
			Integer totPezziPerScatola = etichettaImballoDaStampre.getTipoImballo().getQtaPezziPerScatola();
		   	
			Integer totEtichetteInserite = this.repositoryImballi.getEtichettaPezzoByEtichettaImballo(etichettaImballoDaStampre.getIdEtichettaImballo()).size();
			
			if (totEtichetteInserite == totPezziPerScatola) {
				String codiceScatola = "0";
				codiceScatola += prodottoEtichettaDaStampre.getNumeroDisegno(); 
				codiceScatola += ".";
				codiceScatola += CommonUtils.formatDate(new Date(), CommonUtils.YEARTWODIGITFORMAT);
				codiceScatola += ".";
				codiceScatola += CommonUtils.formatDate(new Date(), CommonUtils.JULIANDAYFORMAT);
				codiceScatola += CommonUtils.formatDate(new Date(), CommonUtils.HOUMMINUTEFIVEDIGITFORMAT);
				
				EtichetteImballi etichettaImballoNuova =  new EtichetteImballi();
        		etichettaImballoNuova.setCodiceEtichetta(codiceScatola);
        		etichettaImballoNuova.setEliminato(false);
        		
        		for (TipoImballi tipoImballoInCiclo : listaTipoImballi) {
        			if(tipoImballoInCiclo.getProdotto().getIdProdotto() == prodottoEtichettaDaStampre.getIdProdotto()) {
        				etichettaImballoNuova.setTipoImballo(tipoImballoInCiclo);
        				break;
        			}
        		}
        		
        		repositoryImballi.salva(etichettaImballoNuova);
				aggiornaVariabileEtichettaImballo(etichettaImballoNuova);
				repositoryImballi.assegnaIdEtichettaImballo(idEtichettaVuota, etichettaImballoNuova);
				aggiornaDatiImballi();
				getUrlStampaEtichettaImballo(etichettaImballoNuova);
			} else {
		    	ViewUtils.showErrorNotification("E' necessario raggiungere qtà pz in scatola : " + totPezziPerScatola);
		    	return;
			}
		}
	}
	
	private void getUrlStampaEtichettaImballo(EtichetteImballi etichettaImballoDaStampare) {
		String url = Page.getCurrent().getLocation().getSchemeSpecificPart();
		url += ViewUtils.nomeStampaEtichettaImballoView;
		if(etichettaImballoDaStampare != null && etichettaImballoDaStampare.getIdEtichettaImballo() > 0) {
			url += etichettaImballoDaStampare.getIdEtichettaImballo();
		}

		getUI().getPage().open(url, "_blank");
	}

	@Override
    boolean verificaCodiceImballo(String codiceDataMatrixInserito) {
		return codiceDataMatrixInserito.length() == 24;
	}

	@Override
	int getPzMancantiInScatolaAttualeRispettoQuellaNuova(Prodotti prodottoEtichettaDaVerificare) {
        int pzMancantiInScatola = 0 ;
		if(prodottoEtichettaDaVerificare.getCodiceProdotto().equals(CodiciProdottiLista.ATTCO01.toString())) {
			if(this.etichettaImballoAttco01== null) return -1;
			int totPzInScatola = ((ListDataProvider<VistaPackingList>) gridAttco01.getDataProvider()).getItems().size();
			pzMancantiInScatola = this.etichettaImballoAttco01.getTipoImballo().getQtaPezziPerScatola() - totPzInScatola;
		} else if(prodottoEtichettaDaVerificare.getCodiceProdotto().equals(CodiciProdottiLista.ATTCO02.toString())) {
			if(this.etichettaImballoAttco02 == null) return -1;
			int totPzInScatola = ((ListDataProvider<VistaPackingList>) gridAttco02.getDataProvider()).getItems().size();
			pzMancantiInScatola = this.etichettaImballoAttco02.getTipoImballo().getQtaPezziPerScatola() - totPzInScatola;
		}
		return pzMancantiInScatola;
	}
}
