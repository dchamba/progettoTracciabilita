package com.vaadin.demo.dashboard.view.stampa;

import java.util.List;

import com.vaadin.demo.dashboard.component.utils.ProdottiUtils.CodiciProdottiLista;
import com.vaadin.demo.dashboard.component.utils.ViewUtils;
import com.vaadin.demo.dashboard.data.model.EtichetteImballi;
import com.vaadin.demo.dashboard.data.model.EtichettePezzi;
import com.vaadin.demo.dashboard.data.model.Prodotti;
import com.vaadin.demo.dashboard.data.repository.RepositoryDatamatrix;
import com.vaadin.demo.dashboard.data.repository.RepositoryImballi;
import com.vaadin.demo.dashboard.data.repository.RepositoryProdotti;
import com.vaadin.demo.dashboard.data.repository.RepositoryProvider;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import fi.jasoft.qrcode.QRCode;

/*
 * Dashboard MainView is a simple HorizontalLayout that wraps the menu on the
 * left and creates a simple container for the navigator on the right.
 */
@SuppressWarnings("serial")
public class StampaEtichettaImballoView extends HorizontalLayout {

	RepositoryDatamatrix repositoryDatamatrix = null;
	RepositoryProdotti repositoryProdotti = null;
	RepositoryImballi repositoryImballi = null;
	
    public StampaEtichettaImballoView(String pathInfo) {
        setSizeFull();
        addStyleName("mainview");
        setSpacing(false);

        Integer idImballoBeginIndex = pathInfo.indexOf(ViewUtils.nomeStampaEtichettaImballoView) + ViewUtils.nomeStampaEtichettaImballoView.length();
        Integer idEtichettaImballo = Integer.parseInt(pathInfo.substring(idImballoBeginIndex));

        if(idEtichettaImballo == null) {
        	ViewUtils.showErrorNotificationWithDelay("Etichetta imballo non selezionata", -1);
        }

        this.repositoryDatamatrix = RepositoryProvider.getRepositoryDatamatrix();
        this.repositoryProdotti = RepositoryProvider.getRepositoryProdotti();
        this.repositoryImballi = RepositoryProvider.repositoryImballi();
        
        EtichetteImballi etichettaImballo = this.repositoryImballi.getEtichettaImballo(idEtichettaImballo);
        List<EtichettePezzi> etichettePezzi = this.repositoryImballi.getEtichettaPezzoByEtichettaImballo(idEtichettaImballo);
        Integer qtaPzImballo = etichettePezzi == null ? 0 : etichettePezzi.size();
        Prodotti prodotto = etichettaImballo.getTipoImballo().getProdotto();
        
//        DashboardViewType.listDashboardViewType.clear();
//        DashboardViewType.addValuesToDashboardViewType();
        

        // Create an opener extension
    	//com.vaadin.server.BrowserWindowOpener opener = new com.vaadin.server.BrowserWindowOpener(com.vaadin.demo.dashboard.component.PrintUI.class);
//    	com.vaadin.server.BrowserWindowOpener opener = new com.vaadin.server.BrowserWindowOpener((Resource) getContent());
//        opener.setFeatures("height=200,width=400,resizable");
//        opener.extend(ok);
//        
//        footer.addComponent(ok);
//        footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        
		QRCode qrcodeImballo = new QRCode();
		qrcodeImballo.setValue(etichettaImballo.getCodiceEtichetta());
		qrcodeImballo.setHeight("300px");
		qrcodeImballo.setWidth("300px");
        

    	VerticalLayout layoutValori = new VerticalLayout();

    	Label labelNumeroDisegno = new Label("0" + prodotto.getNumeroDisegno() + "0");
    	Label labelNomeProdotto = new Label(prodotto.getDescrizione());
    	Label labelCodiceProdotto = new Label(prodotto.getCodiceProdotto());
    	Label labelQtaPz = new Label("QTA' : " + qtaPzImballo + " pz" );
    	
    	String stringaLabelSeriale = "Seriale : ";
    	
    	if(prodotto.getCodiceProdotto().equals(CodiciProdottiLista.ATTCO01.toString()) || 
			prodotto.getCodiceProdotto().equals(CodiciProdottiLista.ATTCO02.toString())) {
    		stringaLabelSeriale += etichettaImballo.getCodiceEtichetta();
    	} else {
	    	stringaLabelSeriale += etichettaImballo.getCodiceEtichetta().substring(12, 14);
	    	stringaLabelSeriale += "/";
	    	stringaLabelSeriale += etichettaImballo.getCodiceEtichetta().substring(15, 18);
	    	stringaLabelSeriale += "/";
	    	stringaLabelSeriale += etichettaImballo.getCodiceEtichetta().substring(19);
    	}
    	
    	Label labelSeriale = new Label(stringaLabelSeriale);
    	
    	layoutValori.addComponent(labelCodiceProdotto);
    	layoutValori.addComponent(labelNumeroDisegno);
    	layoutValori.addComponent(labelNomeProdotto);
    	layoutValori.addComponent(labelQtaPz);
    	layoutValori.addComponent(labelSeriale);
		
        addComponent(qrcodeImballo);
        addComponent(layoutValori);

//        ComponentContainer content = new CssLayout();
//        content.addStyleName("view-content");
//        content.setSizeFull();
//        addComponent(content);
//        setExpandRatio(content, 1.0f);
//
//        new DashboardNavigator(content);
    }
    
	void setLabelStyle(Label label) {
		label.setSizeUndefined();
        label.addStyleName(ValoTheme.LABEL_H1);
        label.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        label.addStyleName(ValoTheme.LABEL_BOLD);
	}
}
