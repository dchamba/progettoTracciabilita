package com.vaadin.demo.dashboard.view.scarto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.demo.dashboard.component.utils.ViewUtils;
import com.vaadin.demo.dashboard.data.model.*;
import com.vaadin.demo.dashboard.data.repository.RepositoryProvider;
import com.vaadin.demo.dashboard.view.packinglist.*;

@SuppressWarnings("serial")
public class ScartoWindow extends Window {

    private Datamatrix dataMatrix;
    private String codiceFaseProcesso;
    private ScartoListener scartoListener;
    
    private List<TipiDifetto> listaTuttiTipiDifetti = new ArrayList<TipiDifetto>();

    /**
     * Interfaccia per gestire il callback quando viene selezionato un tipo di scarto
     */
    public interface ScartoListener {
        void onScartoSelezionato(String codiceDataMatrix, String tipoProcesso, String motivoScarto);
    }

    public ScartoWindow(Datamatrix dataMatrix, String faseProcesso, ScartoListener listener) {
        super("Conferma Scarto");
        this.dataMatrix = dataMatrix;
        this.codiceFaseProcesso = faseProcesso;
        this.scartoListener = listener;

        initializeWindow();
        injectCustomStyles();
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

        // HEADER
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

        Label lblCodice = new Label(this.dataMatrix.getDataMatrix());
        lblCodice.addStyleName(ValoTheme.LABEL_H3);
        lblCodice.addStyleName(ValoTheme.LABEL_BOLD);
        lblCodice.addStyleName(ValoTheme.LABEL_FAILURE);

        headerLayout.addComponents(lblTitolo, lblConferma, lblCodice);
        mainLayout.addComponent(headerLayout);

        // Titolo sezione
        Label lblTipologie = new Label("Seleziona la tipologia di difetto:");
        lblTipologie.addStyleName(ValoTheme.LABEL_H4);
        lblTipologie.addStyleName(ValoTheme.LABEL_BOLD);
        mainLayout.addComponent(lblTipologie);

        // Panel scrollabile
        Panel panelTipologie = new Panel();
        panelTipologie.setSizeFull();
        panelTipologie.addStyleName(ValoTheme.PANEL_BORDERLESS);

        // Layout 2 colonne
        HorizontalLayout layoutDueColonne = new HorizontalLayout();
        layoutDueColonne.setSpacing(true);
        layoutDueColonne.setMargin(true);
        layoutDueColonne.setWidth("100%");

        VerticalLayout colonnaSinistra = new VerticalLayout();
        colonnaSinistra.setSpacing(true);
        colonnaSinistra.setMargin(false);
        colonnaSinistra.setWidth("100%");

        VerticalLayout colonnaDestra = new VerticalLayout();
        colonnaDestra.setSpacing(true);
        colonnaDestra.setMargin(false);
        colonnaDestra.setWidth("100%");

        // CARICAMENTO DA DB + RAGGRUPPAMENTO PER FASE
        Map<Integer, List<TipiDifetto>> mappaPerFase = caricaTipiDifettoRaggruppatiPerFase();

        if (mappaPerFase.isEmpty()) {
            Label lblVuoto = new Label("Nessun tipo difetto configurato.");
            lblVuoto.addStyleName(ValoTheme.LABEL_FAILURE);
            colonnaSinistra.addComponent(lblVuoto);
        } else {
            int i = 0;
            for (Map.Entry<Integer, List<TipiDifetto>> entry : mappaPerFase.entrySet()) {
                Integer idFaseProcesso = entry.getKey();
                List<TipiDifetto> difetti = entry.getValue();

                String nomeFase = getNomeFaseProcesso(idFaseProcesso); // sostituibile con fetch DB
                VerticalLayout card = creaGruppoScartoDaDb(idFaseProcesso, nomeFase, difetti);

                // distribuzione alternata sulle 2 colonne
                if ((i % 2) == 0) {
                    colonnaSinistra.addComponent(card);
                } else {
                    colonnaDestra.addComponent(card);
                }
                i++;
            }
        }

        layoutDueColonne.addComponents(colonnaSinistra, colonnaDestra);
        layoutDueColonne.setExpandRatio(colonnaSinistra, 1.0f);
        layoutDueColonne.setExpandRatio(colonnaDestra, 1.0f);

        panelTipologie.setContent(layoutDueColonne);
        mainLayout.addComponent(panelTipologie);
        mainLayout.setExpandRatio(panelTipologie, 1.0f);

        // Footer
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

    /**
     * Carica i tipi difetto dal DB e li raggruppa per idFaseProcesso.
     * Usa LinkedHashMap per mantenere l'ordine (id fase crescente).
     */
    private Map<Integer, List<TipiDifetto>> caricaTipiDifettoRaggruppatiPerFase() {
        try {
            listaTuttiTipiDifetti = RepositoryProvider.repositoryTipiDifetto().getTipiDifetti();

            if (listaTuttiTipiDifetti == null) {
                return new LinkedHashMap<>();
            }

            // ordino prima per fase, poi per codice difetto
            Collections.sort(listaTuttiTipiDifetti, new Comparator<TipiDifetto>() {
                @Override
                public int compare(TipiDifetto o1, TipiDifetto o2) {
                    int c = o1.getFaseProcesso().getIdFaseProcesso().compareTo(o2.getFaseProcesso().getIdFaseProcesso());
                    if (c != 0) return c;
                    if (o1.getCodiceDifetto() == null && o2.getCodiceDifetto() == null) return 0;
                    if (o1.getCodiceDifetto() == null) return 1;
                    if (o2.getCodiceDifetto() == null) return -1;
                    return o1.getCodiceDifetto().compareTo(o2.getCodiceDifetto());
                }
            });

            Map<Integer, List<TipiDifetto>> out = new LinkedHashMap<>();
            for (TipiDifetto td : listaTuttiTipiDifetti) {
                Integer fase = td.getFaseProcesso().getIdFaseProcesso();
                if (!out.containsKey(fase)) {
                    out.put(fase, new ArrayList<TipiDifetto>());
                }
                out.get(fase).add(td);
            }
            return out;

        } catch (Exception e) {
            Notification.show("Errore caricamento tipi difetto: " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
            return new LinkedHashMap<>();
        }
    }

    /**
     * Crea un gruppo (card) per una fase processo usando i TipiDifetto letti dal DB.
     */
    private VerticalLayout creaGruppoScartoDaDb(Integer idFaseProcesso, String nomeProcesso, List<TipiDifetto> difetti) {

        VerticalLayout gruppoLayout = new VerticalLayout();
        gruppoLayout.setSpacing(true);
        gruppoLayout.setMargin(true);
        gruppoLayout.setWidth("100%");
        gruppoLayout.addStyleName(ValoTheme.LAYOUT_CARD);

        String icona = getIconaProcesso(nomeProcesso);
        Label lblProcesso = new Label(icona + " " + nomeProcesso);
        lblProcesso.addStyleName(ValoTheme.LABEL_H4);
        lblProcesso.addStyleName(ValoTheme.LABEL_BOLD);
        lblProcesso.addStyleName(ValoTheme.LABEL_COLORED);
        lblProcesso.setWidth("100%");
        gruppoLayout.addComponent(lblProcesso);

        for (TipiDifetto td : difetti) {

            // testo bottone: puoi scegliere se mostrare anche codice
            String caption = td.getDescrizione();
            if (td.getCodiceDifetto() != null && !td.getCodiceDifetto().trim().isEmpty()) {
                caption = td.getCodiceDifetto() + " - " + td.getDescrizione();
            }

            CssLayout buttonWrapper = new CssLayout();
            buttonWrapper.setWidth("100%");
            buttonWrapper.setStyleName("scarto-button-wrapper");

            Button btnScarto = new Button(caption);
            btnScarto.setWidth("100%");
            btnScarto.setStyleName("v-button");
            btnScarto.addStyleName("v-button-scarto-yellow");

            btnScarto.addClickListener(e -> {
            	try {
    		        // Carica fase processo corrente (dalla window o da contesto)
    		        // Adatta questo in base a come identifichi la fase nella window
    		        FasiProcesso faseCorrente = RepositoryProvider.repositoryFasiProcesso().getFaseProcessoPerCodice(this.codiceFaseProcesso);
    		        if (faseCorrente == null) {
    		            ViewUtils.showErrorNotification("Fase processo non identificata");
    		            return;
    		        }

    		        // Carica tipo difetto dal motivoScarto (string che contiene codiceDifetto + descrizione)
    		        // Se nella ScartoWindow usi idTipoDifetto, passa quello invece
    		        // Per ora assumo che motivoScarto = descrizione TipiDifetto
    		        
    		        TipiDifetto difettoSelezionato = listaTuttiTipiDifetti.stream()
    		                .filter(tdList -> tdList.getIdTipoDifetto().equals(td.getIdTipoDifetto()))
    		                .findFirst()
    		                .orElse(null);

    		        // Crea riga DatamatrixFasiProcesso di scarto
    		        DatamatrixFasiProcesso scartoRecord = new DatamatrixFasiProcesso();
    		        scartoRecord.setDataMatrix(this.dataMatrix);
    		        scartoRecord.setFaseProcesso(faseCorrente);
    		        scartoRecord.setDataOra(new Date());
    		        scartoRecord.setAzienda(getCurrentUser().getAzienda());
    		        scartoRecord.setUtenteOperatore(getCurrentUser());
    		        scartoRecord.setIsScarto(true);
//    		        scartoRecord.setMotivoEliminazione(motivoScarto);
//    		        scartoRecord.setDataOraEliminazione(new Date());
//    		        scartoRecord.setUtenteEliminazione(getCurrentUser());
    		        scartoRecord.setTipoDifetto(difettoSelezionato);
    		        scartoRecord.setEliminato(false);

    		        RepositoryProvider.getRepositoryDatamatrixTrattamenti().salvaFaseProcesso(scartoRecord);

    		        ViewUtils.showErrorNotification("Scarto registrato: " + faseCorrente.getDescrizione() + " per codice " + this.dataMatrix.getDataMatrix());

                    if (scartoListener != null) {
                        // mantengo firma attuale: tipoProcesso = nome fase (normalizzato), motivoScarto = descrizione
                        scartoListener.onScartoSelezionato(
                                dataMatrix.getDataMatrix(),
                                nomeProcesso.replace(" ", "_"),
                                td.getDescrizione()
                        );
                    }

    		    } catch (Exception ex) {
    		        ViewUtils.showErrorNotification("Errore durante registrazione scarto: " + ex.getMessage());
    		    }
                close();
            });

            buttonWrapper.addComponent(btnScarto);
            gruppoLayout.addComponent(buttonWrapper);
        }

        return gruppoLayout;
    }

    /**
     * Mapping temporaneo idFaseProcesso -> Nome fase.
     * Se hai una repo FasiProcesso, qui va sostituita con una query DB.
     */
    private String getNomeFaseProcesso(Integer idFaseProcesso) {
        if (idFaseProcesso == null) return "FASE";

        switch (idFaseProcesso.intValue()) {
            case 1: return "TRATTAMENTO TERMICO";
            case 2: return "TEST DUREZZA";
            case 3: return "FUSIONE";
            case 4: return "SABBIATURA";
            case 5: return "SBAVATURA";
            case 6: return "LAVORAZIONE MECCANICA";
            case 7: return "PROVA TENUTA";
            case 8: return "LAVAGGIO / FINALE";
            case 9: return "LIQUIDI PENETRANTI";
            case 10: return "CONTROLLO RX";
            case 11: return "STERRATURA";
            case 12: return "ASSEMBLAGGIO";
            default: return "FASE " + idFaseProcesso;
        }
    }

    private void injectCustomStyles() {
        Page.getCurrent().getStyles().add(
                ".v-button-scarto-yellow { " +
                        " background-color: #fff9c4 !important; " +
                        " border-color: #f9a825 !important; " +
                        " color: #333 !important; " +
                        "} " +
                        ".v-button-scarto-yellow:hover { " +
                        " background-color: #fff59d !important; " +
                        "}"
        );
    }

    /**
     * Restituisce un'icona appropriata per ogni processo
     */
    private String getIconaProcesso(String nomeProcesso) {
        if (nomeProcesso == null) return "•";

        switch (nomeProcesso) {
            case "FUSIONE":
                return "🔥";
            case "SABBIATURA":
                return "🧱";
            case "SBAVATURA":
                return "🔧";
            case "LAVORAZIONE MECCANICA":
                return "⚙️";
            case "PROVA TENUTA":
                return "💧";
            case "LIQUIDI PENETRANTI":
                return "🧪";
            case "CONTROLLO RX":
                return "📷";
            case "ASSEMBLAGGIO":
                return "🧩";
            case "TRATTAMENTO TERMICO":
                return "♨️";
            case "TEST DUREZZA":
                return "🔩";
            case "LAVAGGIO / FINALE":
                return "🧼";
            case "STERRATURA":
                return "🏜️";
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

    Utenti getCurrentUser() {
        return (Utenti) VaadinSession.getCurrent().getAttribute(Utenti.class.getName());
    }
}
