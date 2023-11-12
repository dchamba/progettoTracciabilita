package com.vaadin.demo.dashboard.view.datamatrix;

import java.util.Date;
import java.util.List;

import com.vaadin.demo.dashboard.component.model.FilterDatamatrix;
import com.vaadin.demo.dashboard.component.style.StyleUtils;
import com.vaadin.demo.dashboard.component.utils.ViewUtils;
import com.vaadin.demo.dashboard.component.view.MyCustomView;
import com.vaadin.demo.dashboard.data.model.Datamatrix;
import com.vaadin.demo.dashboard.data.model.Prodotti;
import com.vaadin.demo.dashboard.data.model.Utenti;
import com.vaadin.demo.dashboard.data.repository.RepositoryDatamatrix;
import com.vaadin.demo.dashboard.data.repository.RepositoryProdotti;
import com.vaadin.demo.dashboard.data.repository.RepositoryProvider;
import com.vaadin.demo.dashboard.event.DashboardEventBus;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class NewDatamatrixView extends MyCustomView {

//	VerticalLayout layoutCampiDatamatrix = null;
	private RepositoryDatamatrix repositoryDatamatrix = null;
	private RepositoryProdotti repositoryProdotti = null;
	private TextField textDatamatrix;
	private TextArea textAreaLetturePrecedenti;
	
	public NewDatamatrixView() {
        setSizeFull();
        addStyleName("transactions");
        setMargin(false);
        setSpacing(false);
        
        this.repositoryDatamatrix = RepositoryProvider.getRepositoryDatamatrix();
        this.repositoryProdotti = RepositoryProvider.getRepositoryProdotti();
        
        DashboardEventBus.register(this);

        buildToolbar();
        buildDatamatrixForm();
        //buildLetturePrecedenti();
	}

	private void buildToolbar() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName(StyleUtils.viewHeaderStyle);
        Responsive.makeResponsive(header);

        Label title = new Label(ViewUtils.titoloEntryDataMatrix);
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);
        
        //header.setHeight("100px");

        addComponent(header);
    	setExpandRatio(header, 2);
    }

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
    
    private void registraLetturaCorrente(String datamatrix, String prodotto) {
    	String testoCorrente = "\r\n\t\t RX n.: " + datamatrix.substring(datamatrix.length() - 5) + "\t\t Product: " + prodotto + "\t\t Datamatrix: " + datamatrix;
    	testoCorrente += "\r\n" + this.textAreaLetturePrecedenti.getValue();
    	this.textAreaLetturePrecedenti.setValue(testoCorrente);
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
    	textDatamatrix.setMaxLength(12);
    	textDatamatrix.addValueChangeListener(event ->	checkAndSaveDatamatrix());
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
//    	
    	
    	
    	

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
    
	private void checkAndSaveDatamatrix() {
		String valoreDatamatrixInserito = textDatamatrix.getValue();

    	textDatamatrix.setComponentError(null);
		if (valoreDatamatrixInserito.isEmpty()) {
			return;
		}
		
		boolean datamatrixInseritoSbagliato = valoreDatamatrixInserito.length() != 12;
		
		if (datamatrixInseritoSbagliato) {
	    	//textDatamatrix.setComponentError(new UserError("Incorrect Datamatrix value"));
	    	ViewUtils.showErrorNotification("Incorrect Datamatrix value");
	    	return;
	    }
		  
		String codiceDataMatrix = textDatamatrix.getValue();
    	List<Datamatrix> listaDatamatrix = this.repositoryDatamatrix.getDataMatrix(new FilterDatamatrix(codiceDataMatrix), null).getResult();
    	
    	if (listaDatamatrix != null && listaDatamatrix.size() > 0) {
        	//textDatamatrix.setComponentError(new UserError("Datamatrix doesn't exist"));
        	ViewUtils.showErrorNotification("Datamatrix already exist");
        	return;
    	} 

    	String codiceProdotto = codiceDataMatrix.substring(0, 2);
    	Prodotti prodotto = this.repositoryProdotti.getProdotto(codiceProdotto);
    	if(prodotto == null) {
        	ViewUtils.showErrorNotification("Associated product not found in database");
        	return;
    	}

		//Salvo il datamatrix
    	Datamatrix datamatrixNuovo = new Datamatrix();
    	datamatrixNuovo.setEliminato(false);
    	datamatrixNuovo.setDataMatrix(codiceDataMatrix);
    	datamatrixNuovo.setProddoto(prodotto);
    	datamatrixNuovo.setUtenteCreatore(getCurrentUser());
    	datamatrixNuovo.setDataOraUltimaModifica(new Date());
    	datamatrixNuovo.setUtenteUltimaModifica(getCurrentUser());
    	datamatrixNuovo.setDataOraCreazione(new Date());
    	datamatrixNuovo.setPartNumber("");
    	datamatrixNuovo.setCodiceInternoProdotto("");
    	datamatrixNuovo.setStato("I");
    	datamatrixNuovo.setDataOraCreazione(new Date());
    	
    	this.repositoryDatamatrix.salva(datamatrixNuovo);
    	ViewUtils.showSuccessfullNotification("Datamatrix " + codiceDataMatrix + " treatment saved correctly");
    	registraLetturaCorrente(codiceDataMatrix, prodotto == null ? "" : prodotto.getCodiceProdotto());
    	
    	textDatamatrix.setValue("");
    	textDatamatrix.setComponentError(null);
	}
    
    private Utenti getCurrentUser() {
        return (Utenti) VaadinSession.getCurrent().getAttribute(Utenti.class.getName());
    }

}
