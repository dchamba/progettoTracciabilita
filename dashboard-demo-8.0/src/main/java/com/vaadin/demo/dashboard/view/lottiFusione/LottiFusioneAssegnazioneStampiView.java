package com.vaadin.demo.dashboard.view.lottiFusione;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.vaadin.demo.dashboard.component.NumericField;
import com.vaadin.demo.dashboard.component.style.StyleUtils;
import com.vaadin.demo.dashboard.component.utils.CommonUtils;
import com.vaadin.demo.dashboard.component.utils.CustomPopupWindow;
import com.vaadin.demo.dashboard.component.utils.ViewUtils;
import com.vaadin.demo.dashboard.component.view.MyCustomView;
import com.vaadin.demo.dashboard.data.model.LottiFusioneAssegnazioneStampi;
import com.vaadin.demo.dashboard.data.model.Prodotti;
import com.vaadin.demo.dashboard.data.model.StampiProdotti;
import com.vaadin.demo.dashboard.data.model.VistaDatamatrixFasiProcessoTT;
import com.vaadin.demo.dashboard.data.model.VistaLottiFusioneAssegnazioneStampi;
import com.vaadin.demo.dashboard.data.repository.RepositoryLottiFusioneAssegnazioneStampi;
import com.vaadin.demo.dashboard.data.repository.RepositoryProdotti;
import com.vaadin.demo.dashboard.data.repository.RepositoryProvider;
import com.vaadin.demo.dashboard.data.repository.RepositoryStampiProdotti;
import com.vaadin.demo.dashboard.event.DashboardEventBus;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class LottiFusioneAssegnazioneStampiView extends MyCustomView {
	private RepositoryProdotti repositoryProdotti = null;
	private RepositoryStampiProdotti repositoryStampiProdotti = null;
	private RepositoryLottiFusioneAssegnazioneStampi repositoryLottiFusioneAssegnazioneStampi = null;
	
	private List<Prodotti> listaProdotti = new ArrayList<Prodotti>();
	private List<StampiProdotti> listaStampiProdotti = new ArrayList<StampiProdotti>();
	
	private ComboBox<Integer> yearComboBox = new ComboBox<>("Anno");
	private ComboBox<String> monthComboBox = new ComboBox<>("Mese");
	private ComboBox<Prodotti> prodottiComboBox = new ComboBox<>("Prodotto");
	private ComboBox<StampiProdotti> stampiProdottiComboBox = new ComboBox<>("Stampo");

	private Grid<VistaLottiFusioneAssegnazioneStampi> grid = new Grid<VistaLottiFusioneAssegnazioneStampi>();

	private FormLayout formLayout = new FormLayout();
	private ComboBox<StampiProdotti> fieldComboBoxStampo = new ComboBox<>("Seleziona Stampo");
	private DateField fieldDaData = new DateField("Da Data");
	private DateField fieldAData = new DateField("A Data");
	private NumericField fieldDaProgressivo = new NumericField("Da Progressivo");
	private NumericField fieldAProgressivo = new NumericField("A Progressivo");
	private TextField fieldNote = new TextField("Note");
	private Button newButton = new Button("Nuovo");
	
	private VistaLottiFusioneAssegnazioneStampi currentVistaLottiFusioneAssegnazioneStampi = null;
    
	public LottiFusioneAssegnazioneStampiView() {
		setSizeFull();
		addStyleName("transactions");
		setMargin(false);
		setSpacing(false);
		
		this.repositoryLottiFusioneAssegnazioneStampi = RepositoryProvider.getRepositoryLottiFusioneAssegnazioneStampi();
		this.repositoryStampiProdotti = RepositoryProvider.getRepositoryStampiProdotti();
		this.repositoryProdotti = RepositoryProvider.getRepositoryProdotti();
		
		listaProdotti = this.repositoryProdotti.getProdottiByIdProdotti(Arrays.asList(37,38,46));
		listaStampiProdotti = this.repositoryStampiProdotti.getStampiProdotti();
		
		listaStampiProdotti = listaStampiProdotti.stream()
                .sorted((sp1, sp2) -> sp1.getDescrizioneStampo().compareToIgnoreCase(sp2.getDescrizioneStampo()))
                .collect(Collectors.toList());
		
		DashboardEventBus.register(this);

		buildToolbar();
		// addSpacigComponent(1);
		addFilters();
		//addSpacigComponent(1);
		buildGridAndFormLottiFusioneAssegnazioneStampi();
		// addSpacigComponent(1);
		
		this.aggiornaVistaLottiFusioneAssegnazioneStampi(null);
	}

	private void buildToolbar() {
		HorizontalLayout header = new HorizontalLayout();
		header.addStyleName(StyleUtils.viewHeaderStyle);
		Responsive.makeResponsive(header);

		Label title = new Label(ViewUtils.titoloAssegnazioneStampiLottiFusione);
		title.setSizeUndefined();
		title.addStyleName(ValoTheme.LABEL_H1);
		title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		
		header.addComponent(title);

		addComponent(header);
		setExpandRatio(header, 1);
	}

    private void addFilters() {
    	
	    int currentYear = Year.now().getValue();
	    yearComboBox.setItems(IntStream.rangeClosed(2019, currentYear).boxed().collect(Collectors.toList()));
	    yearComboBox.setEmptySelectionAllowed(false);
	    yearComboBox.setPlaceholder("Anno...");
	    yearComboBox.setWidth("200px");
	    yearComboBox.setHeightUndefined();
        yearComboBox.addValueChangeListener(event -> aggiornaVistaLottiFusioneAssegnazioneStampi(null));
        
        monthComboBox.setItems(ViewUtils.mesiInItaliano);
        monthComboBox.setPlaceholder("Mese...");
        monthComboBox.setEmptySelectionAllowed(true);
        monthComboBox.setWidth("200px");
        monthComboBox.setHeightUndefined();
        monthComboBox.addValueChangeListener(event -> aggiornaVistaLottiFusioneAssegnazioneStampi(null));

        prodottiComboBox.setItems(listaProdotti);
        prodottiComboBox.setItemCaptionGenerator(Prodotti::getCodiceProdotto);
        prodottiComboBox.setPlaceholder("Prodotto...");
        prodottiComboBox.setEmptySelectionAllowed(false);
        prodottiComboBox.setHeightUndefined();
        prodottiComboBox.setWidth("300px");
        prodottiComboBox.addValueChangeListener(event -> aggiornaVistaLottiFusioneAssegnazioneStampi(null));
        
        stampiProdottiComboBox.setItems(listaStampiProdotti);
        stampiProdottiComboBox.setItemCaptionGenerator(StampiProdotti::getDescrizioneStampo);
        stampiProdottiComboBox.setPlaceholder("Stampo...");
        stampiProdottiComboBox.setEmptySelectionAllowed(false);
        stampiProdottiComboBox.setHeightUndefined();
        stampiProdottiComboBox.setWidth("300px");
        stampiProdottiComboBox.addValueChangeListener(event -> aggiornaVistaLottiFusioneAssegnazioneStampi(null));
        
        HorizontalLayout layoutFilter = new HorizontalLayout();
        //layoutFilter.setSizeFull();
        layoutFilter.setHeight("100px");
        
        layoutFilter.addComponent(prodottiComboBox);
        layoutFilter.addComponent(stampiProdottiComboBox);
        layoutFilter.addComponent(yearComboBox);
        layoutFilter.addComponent(monthComboBox);

		addComponent(layoutFilter);
        setComponentAlignment(layoutFilter, ALIGNMENT_DEFAULT.TOP_LEFT);
		setExpandRatio(layoutFilter, 1);
    }

	private void aggiornaVistaLottiFusioneAssegnazioneStampi(Integer idLottoFusione) {
		
		Integer anno = this.yearComboBox.getSelectedItem().orElse(null);
		String mese = this.monthComboBox.getSelectedItem().orElse(null);
		Integer meseNumero = (mese != null && !mese.isEmpty()) ? (ViewUtils.mesiInItaliano.indexOf(mese) + 1) : null;
		
		Optional<LocalDate[]> intervallo = CommonUtils.calcolaIntervallo(anno, meseNumero);
		
		StampiProdotti stampoProdotto = this.stampiProdottiComboBox.getSelectedItem().orElse(null);
		Prodotti prodotto = this.prodottiComboBox.getSelectedItem().orElse(null);
		
		List<VistaLottiFusioneAssegnazioneStampi> dati = null;
		if(idLottoFusione != null && idLottoFusione > 0) {
			dati  = this.repositoryLottiFusioneAssegnazioneStampi.getVistaLottiFusioneAssegnazioneStampiById(idLottoFusione);
		} else {
			dati  = this.repositoryLottiFusioneAssegnazioneStampi.getVistaLottiFusioneAssegnazioneStampi(prodotto, stampoProdotto, intervallo);
		}
		
		if(dati == null) {  dati = new ArrayList<VistaLottiFusioneAssegnazioneStampi>(); }
		grid.setItems(new ArrayList<VistaLottiFusioneAssegnazioneStampi>());
		grid.setItems(dati);
	}

    public void buildGridAndFormLottiFusioneAssegnazioneStampi() {
    	
        grid.setSizeFull();
        grid.addComponentColumn(item -> {
            Button editButton = new Button("", VaadinIcons.EDIT);
            editButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
            editButton.addClickListener(event -> { 
            	this.newButton.setEnabled(true);
            	this.setFormField(item); 
        	});

            Button deleteButton = new Button("", VaadinIcons.TRASH);
            deleteButton.addStyleName(ValoTheme.BUTTON_DANGER);
            deleteButton.addClickListener(event -> { 
            	CustomPopupWindow window = new CustomPopupWindow("Conferma", "Vuoi eliminare ?");
    			window.addCloseListener(new CloseListener() {
					
					@Override
					public void windowClose(CloseEvent e) {
						if(window.getEsito()) {
			    			LottiFusioneAssegnazioneStampi lottoFusione = repositoryLottiFusioneAssegnazioneStampi.getLottiFusioneAssegnazioneStampiById(item.getIdLottoFusioneAssegnazioneStampo());
			    			lottoFusione.setEliminato(true);
			    			repositoryLottiFusioneAssegnazioneStampi.saveOrUpdate(lottoFusione);
			            	aggiornaVistaLottiFusioneAssegnazioneStampi(null);
						} else {
							
						}
					}
				});
    			getUI().addWindow(window);
        	});

            HorizontalLayout actions = new HorizontalLayout(editButton, deleteButton);
            return actions;
        }).setCaption("Azioni").setWidth(180);        
        grid.addColumn(VistaLottiFusioneAssegnazioneStampi::getDescrizioneStampo).setCaption("Stampo");
        grid.addColumn(VistaLottiFusioneAssegnazioneStampi::getCodificaStampo).setCaption("Codifica");
        grid.addColumn(item -> {
        	if(item == null || item.getDaData() == null) return "";
            int month = item.getDaData().getMonth();
            String mese = ViewUtils.mesiInItaliano.get(month);
            return mese;
        }).setCaption("Mese");
        grid.addColumn(v -> CommonUtils.formatDate(v.getaData(), CommonUtils.DATEFORMAT)).setCaption("Da Data");
        grid.addColumn(v -> CommonUtils.formatDate(v.getDaData(), CommonUtils.DATEFORMAT)).setCaption("A Data");
        grid.addColumn(VistaLottiFusioneAssegnazioneStampi::getDaProgressivo).setCaption("Da Prog.");
        grid.addColumn(VistaLottiFusioneAssegnazioneStampi::getaProgressivo).setCaption("A Prog.");
        grid.addColumn(VistaLottiFusioneAssegnazioneStampi::getNote).setCaption("Note");

        grid.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        
        // Form sulla destra
        formLayout.setSizeFull();
        formLayout.setSpacing(true);        

        newButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        newButton.addClickListener(event -> { 
        	this.newButton.setEnabled(false);
        	this.setFormField(null);
        	
    	});
        formLayout.addComponent(newButton);
        formLayout.setComponentAlignment(newButton, ALIGNMENT_DEFAULT.TOP_LEFT);
        
        formLayout.addComponent(new Label(" "));

        fieldComboBoxStampo.setItems(listaStampiProdotti);
        fieldComboBoxStampo.setItemCaptionGenerator(StampiProdotti::getDescrizioneStampo);
        fieldComboBoxStampo.setPlaceholder("Stampo...");
        fieldComboBoxStampo.setEmptySelectionAllowed(false);
        fieldComboBoxStampo.setWidth("300px");
        formLayout.addComponent(fieldComboBoxStampo);
        

        fieldDaData.setDateFormat("dd/MM/yyyy");
        fieldDaData.setLocale(Locale.ITALY);
        formLayout.addComponent(fieldDaData);

        fieldAData.setDateFormat("dd/MM/yyyy");
        fieldAData.setLocale(Locale.ITALY);
        formLayout.addComponent(fieldAData);

		fieldDaProgressivo.setHeightUndefined();
		fieldDaProgressivo.setWidth("200px");
		fieldDaProgressivo.setMaxLength(4);
        formLayout.addComponent(fieldDaProgressivo);

        fieldAProgressivo.setHeightUndefined();
        fieldAProgressivo.setWidth("200px");
        fieldAProgressivo.setMaxLength(4);
        formLayout.addComponent(fieldAProgressivo);

        fieldNote.setWidth("300px");
        fieldNote.setHeight("150px");
        fieldNote.setMaxLength(300);
        fieldNote.addStyleName("align-top"); 
        formLayout.addComponent(fieldNote);

        Button saveButton = new Button("Salva");
        saveButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
    	saveButton.addClickListener(event -> { save(); });
        formLayout.addComponent(saveButton);

    	
		HorizontalLayout layoutGridAndForm = new HorizontalLayout();
		layoutGridAndForm.setSizeFull();
		
		layoutGridAndForm.addComponent(grid);
		layoutGridAndForm.addComponent(formLayout);
		
        // Aggiungere tabella e form all'HorizontalLayout
		layoutGridAndForm.setExpandRatio(grid, 2);
		layoutGridAndForm.setExpandRatio(formLayout, 1);
		
		addComponent(layoutGridAndForm);
		setExpandRatio(layoutGridAndForm, 6);
    }

    public String getStyleForMonth(int month) {
        switch (month) {
            case 1: return "background-color: #f9f9e0; color: #000;"; // Gennaio - Giallo chiaro
            case 2: return "background-color: #e8f3f8; color: #000;"; // Febbraio - Azzurro chiaro
            case 3: return "background-color: #f3e9f5; color: #000;"; // Marzo - Rosa chiaro
            case 4: return "background-color: #eaf7e1; color: #000;"; // Aprile - Verde chiaro
            case 5: return "background-color: #fef5e5; color: #000;"; // Maggio - Beige chiaro
            case 6: return "background-color: #f9ece6; color: #000;"; // Giugno - Albicocca chiaro
            case 7: return "background-color: #fef7e0; color: #000;"; // Luglio - Oro pallido
            case 8: return "background-color: #eaf4f8; color: #000;"; // Agosto - Cielo chiaro
            case 9: return "background-color: #f9f2e5; color: #000;"; // Settembre - Sabbia chiaro
            case 10: return "background-color: #f4e8ee; color: #000;"; // Ottobre - Lavanda chiaro
            case 11: return "background-color: #eaf6f2; color: #000;"; // Novembre - Verde acqua chiaro
            case 12: return "background-color: #f9f0f5; color: #000;"; // Dicembre - Rosa pallido
            default: return "background-color: #ffffff; color: #000;"; // Default - Bianco
        }
    }

    public void setFormField(VistaLottiFusioneAssegnazioneStampi vista) {
    	this.currentVistaLottiFusioneAssegnazioneStampi = vista;
    	if(vista == null ) {
    		this.fieldComboBoxStampo.clear();
    		this.fieldDaData.clear();
    		this.fieldAData.clear();
    		this.fieldDaProgressivo.clear();
    		this.fieldAProgressivo.clear();
    		this.fieldNote.clear();
    	} else {
    	    this.fieldComboBoxStampo.setValue(vista.getStampiProdotto());

    	    this.fieldAData.setValue(vista.getaData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    	    this.fieldDaData.setValue(vista.getDaData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    	    
    	    this.fieldDaProgressivo.setValue(String.valueOf(vista.getDaProgressivo()));
    	    this.fieldAProgressivo.setValue(String.valueOf(vista.getaProgressivo()));
    	    
    	    this.fieldNote.setValue(vista.getNote() != null ? vista.getNote() : "");
    	}
    }
    
    public void save() {
    	// Validazione dei campi obbligatori
	    if (fieldComboBoxStampo.isEmpty()) {
	    	ViewUtils.showErrorNotification("Seleziona uno stampo");
	        return;
	    }

	    if (fieldDaData.isEmpty()) {
	    	ViewUtils.showErrorNotification("Inserisci la data di fine (Da Data)");
	        return;
	    }

	    if (fieldAData.isEmpty()) {
	    	ViewUtils.showErrorNotification("Inserisci la data di fine (A Data)");
	        return;
	    }

	    if (fieldDaProgressivo.isEmpty()) {
	    	ViewUtils.showErrorNotification("Inserisci il valore del Da Progressivo");
	        return;
	    }

	    if (fieldAProgressivo.isEmpty()) {
	    	ViewUtils.showErrorNotification("Inserisci il valore del A Progressivo");
	        return;
	    }

	    // Creazione e popolamento dell'oggetto LottiFusioneAssegnazioneStampi
	    LottiFusioneAssegnazioneStampi nuovoLotto = new LottiFusioneAssegnazioneStampi();
	    if(this.currentVistaLottiFusioneAssegnazioneStampi != null) {
	    	nuovoLotto.setIdLottoFusioneAssegnazioneStampo(this.currentVistaLottiFusioneAssegnazioneStampi.getIdLottoFusioneAssegnazioneStampo());
	    }
	    nuovoLotto.setStampoProdotto(this.fieldComboBoxStampo.getValue());  
	    nuovoLotto.setDaData(java.sql.Date.valueOf(this.fieldDaData.getValue())); // Conversione LocalDate in Date
	    nuovoLotto.setAData(java.sql.Date.valueOf(this.fieldAData.getValue())); // Conversione LocalDate in Date
	    nuovoLotto.setDaProgressivo(Integer.parseInt(this.fieldDaProgressivo.getValue()));
	    nuovoLotto.setAProgressivo(Integer.parseInt(this.fieldAProgressivo.getValue()));

	    List<VistaLottiFusioneAssegnazioneStampi> resultLottiFusioneEsistenti = this.repositoryLottiFusioneAssegnazioneStampi.verificaCorrettezzaDateEProgressivo(nuovoLotto);
	    if(resultLottiFusioneEsistenti != null && resultLottiFusioneEsistenti.size() > 0) {
	    	this.grid.setItems(new ArrayList<VistaLottiFusioneAssegnazioneStampi>());
	    	this.grid.setItems(resultLottiFusioneEsistenti);
	    	
	    	ViewUtils.showErrorNotification("Esistono già lotti per questo stampo / periodo / progressivo. Verifica i dati caricati in griglia.");
	    	
	    	return;
	    }
	    
	    // Campo facoltativo: Note
	    if (!this.fieldNote.isEmpty()) {
	        nuovoLotto.setNote(this.fieldNote.getValue());
	    } else {
	        nuovoLotto.setNote("");
	    }

	    // Salvataggio dell'oggetto nel database
	    try {
	        Integer idLottoFusione = this.repositoryLottiFusioneAssegnazioneStampi.save(nuovoLotto);
	        ViewUtils.showSuccessfullNotification("Il lotto è stato salvato correttamente");
	        
	        this.setFormField(null);
        	this.newButton.setEnabled(true);
	        this.aggiornaVistaLottiFusioneAssegnazioneStampi(idLottoFusione);
	    } catch (Exception e) {
	    	ViewUtils.showErrorNotification("Impossibile salvare il lotto. Eccezzione : " + e.getMessage());
	        e.printStackTrace();
	    }
    }
    
	void addSpacigComponent(Integer expandRatio) {
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSizeFull();
    	addComponents(verticalLayout);
    	setExpandRatio(verticalLayout, expandRatio);
	}
}
