package com.vaadin.demo.dashboard.view.packinglist;

import java.util.ArrayList;
import java.util.Date;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.demo.dashboard.component.style.StyleUtils;
import com.vaadin.demo.dashboard.component.utils.CommonUtils;
import com.vaadin.demo.dashboard.component.utils.ViewUtils;
import com.vaadin.demo.dashboard.component.utils.PermessiUtils.PermessiUtentiLista;
import com.vaadin.demo.dashboard.component.utils.ProdottiUtils.CodiciProdottiLista;
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
public final class PackingListOMR07e08 extends PackingListView {

		private EtichetteImballi etichettaImballoOmr07, etichettaImballoOmr08;

		private Label lableQtaPzOmr07, lableQtaPzOmr08;
		private Label lableEtichettaOmr07, lableEtichettaOmr08;

		public PackingListOMR07e08() { }

		@Override
		void buildDatamatrixForm() {
			HorizontalLayout header = new HorizontalLayout();
			header.addStyleName(StyleUtils.viewHeaderStyle);
			Responsive.makeResponsive(header);

			this.gridOmr07 = new Grid<VistaPackingList>();
			this.gridOmr08 = new Grid<VistaPackingList>();

			Label title = new Label(ViewUtils.titoloFaseProcessoPackingListOmr07e08);
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

			VerticalLayout layoutPezziScatolaOmr07 = new VerticalLayout();
//			layoutPezziScatolaOmr07.setSizeFull();
//	    	layoutPezziScatolaGes1.setWidth("400px");
			VerticalLayout layoutPezziScatolaOmr08 = new VerticalLayout();
//			layoutPezziScatolaOmr08.setSizeFull();
//	    	layoutPezziScatolaGes2.setWidth("400px");
			
			Label titleOmr07 = new Label("OMR0007");
			setPezziScatolaGridTitleStyle(titleOmr07);
			Label titleOmr08 = new Label("OMR0008");
			setPezziScatolaGridTitleStyle(titleOmr08);

			layoutPezziScatolaOmr07.addComponent(titleOmr07);
			layoutPezziScatolaOmr08.addComponent(titleOmr08);

			lableEtichettaOmr07 = new Label("Cod.etic.:");
			setInfoScatolaStyle(lableEtichettaOmr07);
			lableEtichettaOmr08 = new Label("Cod.etic.:");
			setInfoScatolaStyle(lableEtichettaOmr08);
			layoutPezziScatolaOmr07.addComponent(lableEtichettaOmr07);
			layoutPezziScatolaOmr08.addComponent(lableEtichettaOmr08);

//			gridOmr07.setSizeFull();
//			gridOmr08.setSizeFull();
			addColumnToGrid(gridOmr07);
			addColumnToGrid(gridOmr08);
			layoutPezziScatolaOmr07.addComponent(gridOmr07);
			layoutPezziScatolaOmr08.addComponent(gridOmr08);

			lableQtaPzOmr07 = new Label("Qtà pz in scatola: 0");
			setInfoScatolaStyle(lableQtaPzOmr07);
			lableQtaPzOmr08 = new Label("Qtà pz in scatola: 0");
			setInfoScatolaStyle(lableQtaPzOmr08);

			layoutPezziScatolaOmr07.addComponent(lableQtaPzOmr07);
			layoutPezziScatolaOmr08.addComponent(lableQtaPzOmr08);

			Button buttonStampaEtichettaOmr07 = new Button("Stampa etichetta OMR007");
			buttonStampaEtichettaOmr07.setHeight("65px");
			buttonStampaEtichettaOmr07.setWidth("300px");
			buttonStampaEtichettaOmr07.addClickListener(v -> this.stampaEtichetta(CodiciProdottiLista.OMR0007.toString()));
			layoutPezziScatolaOmr07.addComponent(buttonStampaEtichettaOmr07);

			Button buttonStampaEtichettaOmr08 = new Button("Stampa etichetta OMR008");
			buttonStampaEtichettaOmr08.setHeight("65px");
			buttonStampaEtichettaOmr08.setWidth("300px");
			buttonStampaEtichettaOmr08.addClickListener(v -> this.stampaEtichetta(CodiciProdottiLista.OMR0008.toString()));
			layoutPezziScatolaOmr08.addComponent(buttonStampaEtichettaOmr08);

			HorizontalLayout pezziScatolaHorizontalLayout = new HorizontalLayout();
			pezziScatolaHorizontalLayout.addComponent(layoutPezziScatolaOmr07);
			pezziScatolaHorizontalLayout.addComponent(layoutPezziScatolaOmr08);
			pezziScatolaHorizontalLayout.setSizeFull();

//			VerticalLayout fields = new VerticalLayout();
//			fields.setSizeFull();
//			fields.setDefaultComponentAlignment(Alignment.TOP_CENTER);
			addComponent(header);
			addComponents(layoutDatamatrix);
			addComponents(pezziScatolaHorizontalLayout);

			setExpandRatio(header, 0.1f);
			setExpandRatio(layoutDatamatrix, 0.2f);
			setExpandRatio(pezziScatolaHorizontalLayout, 0.7f);

			setSizeFull();
			
//			addComponent(fields);
			// setExpandRatio(fields, 8);

	        this.etichettaImballoOmr07 = this.repositoryImballi.getEtichettaImballo(getIdEtichettaImballoVuotaOmr07());
	        this.etichettaImballoOmr08 = this.repositoryImballi.getEtichettaImballo(getIdEtichettaImballoVuotaOmr08());
			this.aggiornaDatiImballi();
		}

		@Override
		void aggiornaDatiImballi() {
			if (this.etichettaImballoOmr08 != null) {
				gridOmr07.setItems(new ArrayList<VistaPackingList>());
				gridOmr07.setItems(this.repositoryImballi.getVistaPackingListFromEtichettaScatola(null, this.etichettaImballoOmr07.getIdEtichettaImballo()));

				lableEtichettaOmr07.setValue("Cod.etic.:" + this.etichettaImballoOmr07.getCodiceEtichettaImballoSmeup());

				ListDataProvider<VistaPackingList> dataProvider = (ListDataProvider<VistaPackingList>) gridOmr07.getDataProvider();
				lableQtaPzOmr07.setValue("Qtà pz in scatola:  " + String.valueOf(dataProvider.getItems().size()));
			}
			
			if (this.etichettaImballoOmr08 != null) {
				gridOmr08.setItems(new ArrayList<VistaPackingList>());
				gridOmr08.setItems(this.repositoryImballi.getVistaPackingListFromEtichettaScatola(null, this.etichettaImballoOmr08.getIdEtichettaImballo()));

				lableEtichettaOmr08.setValue("Cod.etic.:" + this.etichettaImballoOmr08.getCodiceEtichettaImballoSmeup());

				ListDataProvider<VistaPackingList> dataProvider = (ListDataProvider<VistaPackingList>) gridOmr08.getDataProvider();
				lableQtaPzOmr08.setValue("Qtà pz in scatola:  " + String.valueOf(dataProvider.getItems().size()));
			}
		}

		@Override
		void aggiornaVariabileEtichettaImballo(EtichetteImballi etichettaImballo) {
			if (etichettaImballo.getTipoImballo().getProdotto().getCodiceProdotto()
					.equals(CodiciProdottiLista.OMR0007.toString())) {
				this.etichettaImballoOmr07 = etichettaImballo;
			} else if (etichettaImballo.getTipoImballo().getProdotto().getCodiceProdotto()
					.equals(CodiciProdottiLista.OMR0008.toString())) {
				this.etichettaImballoOmr08 = etichettaImballo;
			}
		}

		@Override
		EtichetteImballi getVariabileEtichettaImballo(String codiceProdotto) {
			if (codiceProdotto.equals(CodiciProdottiLista.OMR0007.toString())) {
				return this.etichettaImballoOmr07;
			} else if (codiceProdotto.equals(CodiciProdottiLista.OMR0008.toString())) {
				return this.etichettaImballoOmr08;
			} else {
				return null;
			}

		}

		@Override
		String getStringPermessoPackingList() {
			return PermessiUtentiLista.FASE_PROCESSO_PACKINGLISTOMR7e8.toString();
		}

		Integer getIdEtichettaImballoVuotaOmr07() {
			return -11;
		}

		Integer getIdEtichettaImballoVuotaOmr08() {
			return -12;
		}

		private void stampaEtichetta(String codiceProdottoStampato) {
			EtichetteImballi etichettaImballoDaStampre = null;
			Prodotti prodottoEtichettaDaStampre = null;
			Integer idEtichettaVuota = null;
			
			if (codiceProdottoStampato.equals(CodiciProdottiLista.OMR0007.toString())) {
				etichettaImballoDaStampre = this.etichettaImballoOmr07;
				prodottoEtichettaDaStampre = this.listaProdottiPackingList.stream().filter(v -> v.getCodiceProdotto().equals(CodiciProdottiLista.OMR0007.toString())).findFirst().get();
				idEtichettaVuota = getIdEtichettaImballoVuotaOmr07();
			} else if (codiceProdottoStampato.equals(CodiciProdottiLista.OMR0008.toString())) {
				etichettaImballoDaStampre = this.etichettaImballoOmr08;
				prodottoEtichettaDaStampre = this.listaProdottiPackingList.stream().filter(v -> v.getCodiceProdotto().equals(CodiciProdottiLista.OMR0008.toString())).findFirst().get();
				idEtichettaVuota = getIdEtichettaImballoVuotaOmr08();
			}
			
			if(etichettaImballoDaStampre != null && etichettaImballoDaStampre.getIdEtichettaImballo() > 0) {
				getUrlStampaEtichettaImballo(etichettaImballoDaStampre);
				return;
			}
			
			if(etichettaImballoDaStampre != null && etichettaImballoDaStampre.getIdEtichettaImballo() <= 0) {
				Integer totPezziPerScatola = etichettaImballoDaStampre.getTipoImballo().getQtaPezziPerScatola();
			   	
				Integer totEtichetteInserite = this.repositoryImballi.getEtichettaPezzoByEtichettaImballo(etichettaImballoDaStampre.getIdEtichettaImballo()).size();
				
				if (totEtichetteInserite.equals(totPezziPerScatola)) {
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
			if(prodottoEtichettaDaVerificare.getCodiceProdotto().equals(CodiciProdottiLista.OMR0007.toString())) {
				if(this.etichettaImballoOmr07== null) return -1;
				int totPzInScatola = ((ListDataProvider<VistaPackingList>) gridOmr07.getDataProvider()).getItems().size();
				pzMancantiInScatola = this.etichettaImballoOmr07.getTipoImballo().getQtaPezziPerScatola() - totPzInScatola;
			} else if(prodottoEtichettaDaVerificare.getCodiceProdotto().equals(CodiciProdottiLista.OMR0008.toString())) {
				if(this.etichettaImballoOmr08 == null) return -1;
				int totPzInScatola = ((ListDataProvider<VistaPackingList>) gridOmr08.getDataProvider()).getItems().size();
				pzMancantiInScatola = this.etichettaImballoOmr08.getTipoImballo().getQtaPezziPerScatola() - totPzInScatola;
			}
			return pzMancantiInScatola;
		}
	}
