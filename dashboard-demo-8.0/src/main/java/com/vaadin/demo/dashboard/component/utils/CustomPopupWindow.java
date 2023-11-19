package com.vaadin.demo.dashboard.component.utils;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Notification.Type;

public class CustomPopupWindow extends Window  {
	
	boolean esito = false;
	
	public CustomPopupWindow (String titolo, String messaggio) {
		setCaption(titolo);
        setWidth(700.0f, Unit.PIXELS);
        setHeight(200.0f, Unit.PIXELS);
        setModal(true);
        setClosable(false);
        setDraggable(true);
        setResizable(false);

        
        Label labelMessaggio = new Label(messaggio);

        HorizontalLayout lyoutPulsanti = new HorizontalLayout();
        
        Button buttonConferma = new Button("Si");
        buttonConferma.setWidth(60.0f, Unit.PIXELS);
        buttonConferma.addClickListener(c -> {
        	esito = true;
        	close();
        });
        
        Button buttonAnnulla = new Button("No");
        buttonAnnulla.setWidth(60.0f, Unit.PIXELS);
        buttonAnnulla.addClickListener(c -> {
        	esito = false;
        	close();
        });
        
        lyoutPulsanti.addComponent(buttonConferma);
        lyoutPulsanti.addComponent(buttonAnnulla);

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        content.setMargin(true);
        content.addComponent(labelMessaggio);
        content.addComponent(lyoutPulsanti);

        content.setStyleName(Type.ERROR_MESSAGE.getStyle());
        setContent(content);
	}
	
	public boolean getEsito() {
		return esito;
	}
}
