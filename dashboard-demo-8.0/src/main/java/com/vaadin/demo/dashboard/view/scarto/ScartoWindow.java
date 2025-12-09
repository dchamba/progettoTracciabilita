package com.vaadin.demo.dashboard.view.scarto;

import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Panel;

@SuppressWarnings("serial")
public class ScartoWindow extends Window {
    
    private String codiceDataMatrix;
    private ScartoListener scartoListener;
    
    /**
     * Interfaccia per gestire il callback quando viene selezionato un tipo di scarto
     */
    public interface ScartoListener {
        void onScartoSelezionato(String codiceDataMatrix, String tipoProcesso, String motivoScarto);
    }
    
    public ScartoWindow(String codiceDataMatrix, ScartoListener listener) {
        super("Conferma Scarto");
        this.codiceDataMatrix = codiceDataMatrix;
        this.scartoListener = listener;
        
        initializeWindow();
        buildContent();
    }
    
    private void initializeWindow() {
        setModal(true);
        setWidth("900px");
        setHeight("650px");
        setClosable(false); 
        center();
    }
    private void buildContent() {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        mainLayout.setSizeFull();
     // HEADER con design più rilassante ma con segnale di scarto
        VerticalLayout headerLayout = new VerticalLayout();
        headerLayout.setWidth("100%");
        headerLayout.setSpacing(false);
        headerLayout.setMargin(true);
        headerLayout.addStyleName(ValoTheme.LAYOUT_CARD);

        Label lblTitolo = new Label("⚠️ Dichiarazione Scarto");
        lblTitolo.addStyleName(ValoTheme.LABEL_H2);
        lblTitolo.addStyleName(ValoTheme.LABEL_BOLD);
        lblTitolo.addStyleName(ValoTheme.LABEL_FAILURE);

        Label lblConferma = new Label("Conferma la registrazione dello scarto per:");
        lblConferma.addStyleName(ValoTheme.LABEL_LIGHT);

        Label lblCodice = new Label(codiceDataMatrix);
        lblCodice.addStyleName(ValoTheme.LABEL_H3);
        lblCodice.addStyleName(ValoTheme.LABEL_BOLD);
        lblCodice.addStyleName(ValoTheme.LABEL_FAILURE);  // ROSSO

        headerLayout.addComponents(lblTitolo, lblConferma, lblCodice);

        mainLayout.addComponent(headerLayout);

        
        // Titolo sezione tipologie
        Label lblTipologie = new Label("Seleziona la tipologia di difetto:");
        lblTipologie.addStyleName(ValoTheme.LABEL_H4);
        lblTipologie.addStyleName(ValoTheme.LABEL_BOLD);
        mainLayout.addComponent(lblTipologie);
        
        // Panel scrollabile per le tipologie
        Panel panelTipologie = new Panel();
        panelTipologie.setSizeFull();
        panelTipologie.addStyleName(ValoTheme.PANEL_BORDERLESS);
        
        // Layout a DUE COLONNE per i difetti
        HorizontalLayout layoutDueColonne = new HorizontalLayout();
        layoutDueColonne.setSpacing(true);
        layoutDueColonne.setMargin(true);
        layoutDueColonne.setWidth("100%");
        
        // Colonna SINISTRA
        VerticalLayout colonnaSinistra = new VerticalLayout();
        colonnaSinistra.setSpacing(true);
        colonnaSinistra.setMargin(false);
        colonnaSinistra.setWidth("100%");
        
        colonnaSinistra.addComponent(creaGruppoScarto("FUSIONE", 
            new String[]{"Difetto fusione", "Porosità", "Inclusioni"}));
        
        colonnaSinistra.addComponent(creaGruppoScarto("SBAVATURA", 
            new String[]{"Difetto sbavatura", "Bava residua", "Danneggiamento"}));
        
        colonnaSinistra.addComponent(creaGruppoScarto("LAVORAZIONE MECCANICA", 
            new String[]{"Difetto lavorazione", "Fuori tolleranza dimensionale", "Danneggiamento utensile"}));
        
        // Colonna DESTRA
        VerticalLayout colonnaDestra = new VerticalLayout();
        colonnaDestra.setSpacing(true);
        colonnaDestra.setMargin(false);
        colonnaDestra.setWidth("100%");
        
        colonnaDestra.addComponent(creaGruppoScarto("PROVA TENUTA", 
            new String[]{"Non supera prova tenuta", "Perdita", "Cricca"}));
        
        colonnaDestra.addComponent(creaGruppoScarto("IMPREGNAZIONE", 
            new String[]{"Difetto impregnazione", "Impregnazione incompleta", "Contaminazione"}));
        
        colonnaDestra.addComponent(creaGruppoScarto("CONTROLLO VISIVO", 
            new String[]{"Difetto visivo", "Graffi superficiali", "Ammaccature", "Sporco/Contaminazione"}));
        
        layoutDueColonne.addComponents(colonnaSinistra, colonnaDestra);
        layoutDueColonne.setExpandRatio(colonnaSinistra, 1.0f);
        layoutDueColonne.setExpandRatio(colonnaDestra, 1.0f);
        
        panelTipologie.setContent(layoutDueColonne);
        
        mainLayout.addComponent(panelTipologie);
        mainLayout.setExpandRatio(panelTipologie, 1.0f);
        
        // Footer con bottone annulla
        HorizontalLayout footerLayout = new HorizontalLayout();
        footerLayout.setWidth("100%");
        footerLayout.setSpacing(true);
        footerLayout.setMargin(false);
        
        Button btnAnnulla = new Button("Annulla");
        btnAnnulla.setWidth("150px");
        btnAnnulla.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnAnnulla.addClickListener(e -> close());
        
        footerLayout.addComponent(btnAnnulla);
        footerLayout.setComponentAlignment(btnAnnulla, Alignment.MIDDLE_CENTER);
        
        mainLayout.addComponent(footerLayout);
        
        setContent(mainLayout);
    }
    private VerticalLayout creaGruppoScarto(String nomeProcesso, String[] motiviScarto) {
        VerticalLayout gruppoLayout = new VerticalLayout();
        gruppoLayout.setSpacing(true);
        gruppoLayout.setMargin(true);
        gruppoLayout.setWidth("100%");
        gruppoLayout.addStyleName(ValoTheme.LAYOUT_CARD);
        
        // Titolo del gruppo con icona
        String icona = getIconaProcesso(nomeProcesso);
        Label lblProcesso = new Label(icona + " " + nomeProcesso);
        lblProcesso.addStyleName(ValoTheme.LABEL_H4);
        lblProcesso.addStyleName(ValoTheme.LABEL_BOLD);
        lblProcesso.addStyleName(ValoTheme.LABEL_COLORED);
        lblProcesso.setWidth("100%");
        gruppoLayout.addComponent(lblProcesso);
        
        // Crea bottoni per ogni motivo di scarto con colore giallo chiaro
        for (String motivoScarto : motiviScarto) {
            CssLayout buttonWrapper = new CssLayout();
            buttonWrapper.setWidth("100%");
            buttonWrapper.setStyleName("scarto-button-wrapper");
            
            Button btnScarto = new Button(motivoScarto);
            btnScarto.setWidth("100%");
            
            // Imposta lo stile direttamente nel componente
            btnScarto.setStyleName("v-button");
            btnScarto.addStyleName("v-button-scarto-yellow");
            
            btnScarto.addClickListener(e -> {
                if (scartoListener != null) {
                    scartoListener.onScartoSelezionato(
                        codiceDataMatrix, 
                        nomeProcesso.replace(" ", "_"), 
                        motivoScarto
                    );
                }
                close();
            });
            
            buttonWrapper.addComponent(btnScarto);
            gruppoLayout.addComponent(buttonWrapper);
        }
        
        return gruppoLayout;
    }

    private void injectCustomStyles() {
        Page.getCurrent().getStyles().add(
            ".v-button-scarto-yellow { " +
            "  background-color: #fff9c4 !important; " +
            "  border-color: #f9a825 !important; " +
            "  color: #333 !important; " +
            "} " +
            ".v-button-scarto-yellow:hover { " +
            "  background-color: #fff59d !important; " +
            "}"
        );
    }

    
/**
 * Restituisce un'icona appropriata per ogni processo
 */
private String getIconaProcesso(String nomeProcesso) {
    switch(nomeProcesso) {
        case "FUSIONE":
            return "🔥";
        case "SBAVATURA":
            return "🔧";
        case "LAVORAZIONE MECCANICA":
            return "⚙️";
        case "PROVA TENUTA":
            return "💧";
        case "IMPREGNAZIONE":
            return "🧪";
        case "CONTROLLO VISIVO":
            return "👁️";
        default:
            return "•";
    }
}


    /**
     * Mostra la window nell'UI corrente
     */
    public void show() {
        if (getUI() != null) {
            getUI().addWindow(this);
        }
    }
}
