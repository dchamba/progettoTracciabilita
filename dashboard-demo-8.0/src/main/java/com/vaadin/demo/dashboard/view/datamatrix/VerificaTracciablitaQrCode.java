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
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class VerificaTracciablitaQrCode extends MyCustomView {
	
	TextField textDatamatrix;
	
	public VerificaTracciablitaQrCode() {
        setSizeFull();
        addStyleName("transactions");
        setMargin(false);
        setSpacing(false);
        
        DashboardEventBus.register(this);
        buildDatamatrixForm();
	}
	
    void buildDatamatrixForm() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName(StyleUtils.viewHeaderStyle);
        Responsive.makeResponsive(header);
        
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
				//checkSandcoreUsability(codiceDataMatrixInserito);
						
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

    	//layoutPezziScatolaFia10.addComponent(gridAnimeImballi);
        
//        HorizontalLayout pezziScatolaHorizontalLayout = new HorizontalLayout();
//        pezziScatolaHorizontalLayout.addComponent(layoutPezziScatolaFia10);
//        pezziScatolaHorizontalLayout.setSizeFull();
    	
    	TabSheet tabsheet = new TabSheet();
    	tabsheet.setSizeFull();

    	VerticalLayout vTab1 = new VerticalLayout();
    	VerticalLayout vTab2 = new VerticalLayout();
    	tabsheet.addTab(vTab1).setCaption("My Tab 1");
    	tabsheet.addTab(vTab2).setCaption("My Tab 2");

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
