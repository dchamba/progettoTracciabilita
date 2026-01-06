package com.vaadin.demo.dashboard.component.view;

import com.vaadin.ui.*;
import com.vaadin.shared.Registration;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.data.HasValue;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.demo.dashboard.data.model.Datamatrix;
import com.vaadin.demo.dashboard.view.scarto.ScartoWindow;
import com.vaadin.navigator.View;

public class DatamatrixScartoComponentFactory {

    public interface ModalitaScartoHandler {
        void onChange(boolean attiva);
        void onDatamatrixInserito(String codice);
    }

    public static class DatamatrixScartoComponents {
        public final HorizontalLayout layout;
        public final TextField textDatamatrix;
        public final CheckBox chkScarto;
        public final ScartoState state;

        DatamatrixScartoComponents(HorizontalLayout layout,
                                   TextField textDatamatrix,
                                   CheckBox chkScarto,
                                   ScartoState state) {
            this.layout = layout;
            this.textDatamatrix = textDatamatrix;
            this.chkScarto = chkScarto;
            this.state = state;
        }
    }

    public static DatamatrixScartoComponents create(String label, ModalitaScartoHandler handler) {

    	ScartoState state = new ScartoState();
    	
        TextField textDatamatrix = new TextField(label);
        textDatamatrix.setPlaceholder(label);
        textDatamatrix.setHeight("65px");
        textDatamatrix.setWidth("700px");

        CheckBox chkScarto = new CheckBox("⚠️ Modalità SCARTO");
        chkScarto.addStyleName(ValoTheme.CHECKBOX_SMALL);
        chkScarto.setHeight("65px");

        // Layout orizzontale
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        layout.setHeight("65px");

        layout.addComponents(textDatamatrix, chkScarto);
        layout.setComponentAlignment(textDatamatrix, Alignment.MIDDLE_LEFT);
        layout.setComponentAlignment(chkScarto, Alignment.BOTTOM_CENTER);

        // Listener datamatrix
        textDatamatrix.addValueChangeListener((HasValue.ValueChangeListener<String>) event -> {
            String codice = textDatamatrix.getValue() != null
                    ? textDatamatrix.getValue().trim()
                    : "";
            if (codice.isEmpty()) {
                return;
            }
            handler.onDatamatrixInserito(codice);
            textDatamatrix.clear();
        });

        // Listener checkbox
        chkScarto.addValueChangeListener(e -> {
        	state.setAttiva(true);
        	handler.onChange(Boolean.TRUE.equals(e.getValue()));
        });

        return new DatamatrixScartoComponents(layout, textDatamatrix, chkScarto, state);
    }
    
    public static void mostraDialogConfermaScarto(Datamatrix dataMatrix, CheckBox chkScarto,  ScartoState state, String faseProcesso, View view) {  
		ScartoWindow scartoWindow = new ScartoWindow(dataMatrix, faseProcesso, 
			(codice, tipoProcesso, motivoScarto) -> {
				state.attiva = false;
				chkScarto.setValue(false);
			});
		view.getViewComponent().getUI().addWindow(scartoWindow);
	}
    
    public static class ScartoState {
        boolean attiva = false;
        
        public boolean isAttiva() {
            return attiva;
        }
        
        public void setAttiva(boolean attiva) {
            this.attiva = attiva;
        }
    }
}
