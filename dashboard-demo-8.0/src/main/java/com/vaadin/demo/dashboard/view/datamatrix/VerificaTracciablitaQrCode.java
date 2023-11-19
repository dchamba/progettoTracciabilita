package com.vaadin.demo.dashboard.view.datamatrix;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.demo.dashboard.component.style.StyleUtils;
import com.vaadin.demo.dashboard.component.utils.ViewUtils;
import com.vaadin.demo.dashboard.component.view.MyCustomView;
import com.vaadin.demo.dashboard.data.model.AnimeImballi;
import com.vaadin.demo.dashboard.data.model.Utenti;
import com.vaadin.demo.dashboard.data.repository.RepositoryAnimeImballi;
import com.vaadin.demo.dashboard.data.repository.RepositoryProvider;
import com.vaadin.demo.dashboard.event.DashboardEventBus;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class VerificaTracciablitaQrCode extends MyCustomView {

	RepositoryAnimeImballi repositoryAnimeImballi = null;
		
	AnimeImballi animaImballoCorrente = null;

	Grid<AnimeImballi> gridAnimeImballi = new Grid<AnimeImballi>();
	
	TextField textDatamatrix;
	
	private Label lableQtaPzFia10;
	
	public VerificaTracciablitaQrCode() {
        setSizeFull();
        addStyleName("transactions");
        setMargin(false);
        setSpacing(false);
        
        this.repositoryAnimeImballi = RepositoryProvider.repositoryAnimeImballi();
        
        DashboardEventBus.register(this);
        buildDatamatrixForm();
        aggiornaDatiImballi();
	}
	
    void buildDatamatrixForm() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName(StyleUtils.viewHeaderStyle);
        Responsive.makeResponsive(header);
        
        //this.gridFia10 = new Grid<AnimeImballi>();

        Label title = new Label(ViewUtils.titoloVistaImballiAnime);
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
	    
    	textDatamatrix = new TextField("Verifica usabilità imballo");  
    	textDatamatrix.setPlaceholder("Qrcode imballo");
    	//textDatamatrix.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
    	textDatamatrix.addValueChangeListener(new ValueChangeListener<String>() {
			
			@Override
			public void valueChange(ValueChangeEvent<String> event) {
				String codiceDataMatrixInserito = textDatamatrix.getValue().trim();

		    	textDatamatrix.setComponentError(null);
				if (codiceDataMatrixInserito.isEmpty()) {
					return;
				}
				checkSandcoreUsability(codiceDataMatrixInserito);
						
		    	textDatamatrix.setValue("");
		    	textDatamatrix.setComponentError(null);
			}
		});
    	textDatamatrix.setHeight("65px");
    	textDatamatrix.setWidth("700px");

    	
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

    	addColumnToGrid(gridAnimeImballi);
    	
    	layoutPezziScatolaFia10.addComponent(gridAnimeImballi);
        
        HorizontalLayout pezziScatolaHorizontalLayout = new HorizontalLayout();
        pezziScatolaHorizontalLayout.addComponent(layoutPezziScatolaFia10);
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
	}
    
	void checkSandcoreUsability(String codiceDataMatrixInserito) {
		try {
    		//Verifica doppione/pezzo già inserito
			AnimeImballi imballoAnimeRicercata = this.repositoryAnimeImballi.getAnimeImballiByQrCode(codiceDataMatrixInserito);
    		if(imballoAnimeRicercata == null) {
	            throw new Exception("Qrcode imballo anime non esiste");
    		} else {
    			if(imballoAnimeRicercata.getDataScadenza().before(new Date())) {
    	            throw new Exception("Anime scadute! Non utilizzare! ");
    			} else {
    				List<AnimeImballi> listaAnimeImballi = this.repositoryAnimeImballi.getAnimeImballi();
    				
    				for (AnimeImballi animeImballo : listaAnimeImballi) {
						if(animeImballo.getDataScadenza().before(new Date())) {
							//anima scaduta
						} else {
							if(animeImballo.getDataScadenza().before(imballoAnimeRicercata.getDataScadenza())) {
			    	            throw new Exception("Ci sono anime in scadenza prima di questa! Possiblmente non utilizzare! ");
							}
						}
    				}  
    		    	ViewUtils.showSuccessfullNotification("E' possibile utilizzare questo imballo");	 
    			}
    		} 	
		} catch (Exception e) {
          	ViewUtils.showErrorNotification(e.getMessage());
        	//logging
		}
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	void addColumnToGrid(Grid<AnimeImballi> grid) {
		grid.setSizeFull();

		grid.addColumn("GESTAMP").setCaption("Codice prodotto");
		//grid.addColumn(v -> v.getProddoto().getCodiceProdotto()).setCaption("Codice prodotto");
		grid.addColumn(v -> v.getQrcode()).setCaption("QrCode");
		grid.addColumn(v -> v.getQtaAnime()).setCaption("Qta Anime");
		grid.addColumn(v -> v.getDataProduzione()).setCaption("Data produzione");
		grid.addColumn(v -> v.getGaranziaGiorniAnima()).setCaption("Garanzia gg anima");
		grid.addColumn(v -> v.getDataScadenza()).setCaption("Data scadenza");
		
		
//		grid.addcolumn(v -> "cancella",
//		      new buttonrenderer(clickevent -> { 
//		    	  animeimballi v = (animeimballi)clickevent.getitem();
//		    	  eliminapezzodascatola(v); 
//	    	  })).setcaption("elimina").setwidth(120);
		
		grid.setColumnReorderingAllowed(true);
	}

//	void eliminaPezzoDaScatola(AnimeImballi v) {
//		if(v != null) {
//			EtichettePezzi etichettaPezzo = this.repositoryImballi.getEtichettaPezzo(v.getIdEtichettaPezzo(), null);
//			etichettaPezzo.setEliminato(true);
//			this.repositoryImballi.salva(etichettaPezzo);
//			
//			this.aggiornaDatiImballi();
//		}
//	}

    void aggiornaDatiImballi() {
    	List<AnimeImballi> listaVuota = new ArrayList<AnimeImballi>();
		gridAnimeImballi.setItems(listaVuota);
		gridAnimeImballi.setItems(this.repositoryAnimeImballi.getAnimeImballi());

//    	listdataprovider<animeimballi> dataprovider = (listdataprovider<animeimballi>) this.gridanimeimballi.getdataprovider();
//		lableqtapzfia10.setvalue("qtà pz in scatola:  " + string.valueof(dataprovider.getitems().size()));
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
}
