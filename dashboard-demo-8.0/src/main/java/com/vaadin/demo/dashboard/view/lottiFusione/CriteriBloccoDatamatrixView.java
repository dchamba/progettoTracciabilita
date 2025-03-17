package com.vaadin.demo.dashboard.view.lottiFusione;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.demo.dashboard.component.view.MyCustomView;
import com.vaadin.demo.dashboard.data.model.CriteriBloccoDatamatrix;
import com.vaadin.demo.dashboard.data.model.Prodotti;
import com.vaadin.demo.dashboard.data.repository.RepositoryCriteriBloccoDatamatrix;
import com.vaadin.demo.dashboard.data.repository.RepositoryProdotti;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class CriteriBloccoDatamatrixView extends MyCustomView {
	    private RepositoryCriteriBloccoDatamatrix repository = new RepositoryCriteriBloccoDatamatrix();
	    private RepositoryProdotti repositoryProdotti = new RepositoryProdotti();
	    
	    private Grid<CriteriBloccoDatamatrix> grid = new Grid<>(CriteriBloccoDatamatrix.class);
	    
	    // Dichiarazione dei campi di filtro
	    private TextField filtroMessaggioField = new TextField("Messaggio Utente");
	    private DateField filtroDaDataField = new DateField("Da Data");
	    private DateField filtroADataField = new DateField("A Data");
	    private TextField filtroDaProgressivoField = new TextField("Da Progressivo");
	    private TextField filtroAProgressivoField = new TextField("A Progressivo");
	    private ComboBox<Prodotti> filtroProdottoField = new ComboBox<>("Prodotto");
	    private CheckBox filtroApplicaPackingListField = new CheckBox("Applica Solo a Packing List");
	    private CheckBox filtroAttivoField = new CheckBox("Attivo");
	    private Button filterButton = new Button("Filtra");
	    
	    // Dichiarazione dei campi del form di modifica
	    private TextField messaggioField = new TextField("Messaggio Utente");
	    private DateField daDataField = new DateField("Da Data");
	    private DateField aDataField = new DateField("A Data");
	    private TextField daProgressivoField = new TextField("Da Progressivo");
	    private TextField aProgressivoField = new TextField("A Progressivo");
	    private ComboBox<Prodotti> prodottoField = new ComboBox<>("Prodotto");
	    private CheckBox applicaPackingListField = new CheckBox("Applica Solo a Packing List");
	    private CheckBox attivoField = new CheckBox("Attivo");
	    private Button saveButton = new Button("Salva");
	    private Button newButton = new Button("Nuovo");
	    private CriteriBloccoDatamatrix currentRecord = null;
	    
	    public CriteriBloccoDatamatrixView() {
	        setSizeFull();
	        setMargin(false);
	        setSpacing(false);
	        
	        buildFilters();
	        configureProductFilters();
	        buildGridAndForm();
	        refreshGrid();
	    }

	    private void configureProductFilters() {
	        List<Prodotti> prodottiList = repositoryProdotti.getProdotti();
	        filtroProdottoField.setItems(prodottiList);
	        filtroProdottoField.setItemCaptionGenerator(Prodotti::getDescrizione);
	        
	        prodottoField.setItems(prodottiList);
	        prodottoField.setItemCaptionGenerator(Prodotti::getDescrizione);
	    }
	    
	    private void buildGridAndForm() {
	        grid.setSizeFull();
	        grid.setColumns("idBloccoDatamatrix", "messaggioUtente", "daData", "aData", "daProgressivo", "aProgressivo", "applicaSoloAPackingList", "attivo");
	        grid.addComponentColumn(this::buildActionButtons).setCaption("Azioni");
	        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::editRecord));
	        
	        FormLayout formLayout = new FormLayout(messaggioField, daDataField, aDataField, daProgressivoField, aProgressivoField, applicaPackingListField, attivoField);
	        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, newButton);
	        
	        saveButton.addClickListener(event -> saveRecord());
	        newButton.addClickListener(event -> clearForm());
	        
	        HorizontalLayout layoutGridAndForm = new HorizontalLayout(grid, formLayout);
	        layoutGridAndForm.setSizeFull();
	        layoutGridAndForm.setExpandRatio(grid, 2);
	        layoutGridAndForm.setExpandRatio(formLayout, 1);
	        
	        addComponents(layoutGridAndForm, buttonLayout);
	    }

	    private void buildFilters() {
	        HorizontalLayout filterLayout = new HorizontalLayout();
	        filterLayout.setSpacing(true);
	        
	        filtroMessaggioField.setPlaceholder("Cerca messaggio...");
	        filtroDaDataField.setPlaceholder("Da Data");
	        filtroADataField.setPlaceholder("A Data");
	        filtroDaProgressivoField.setPlaceholder("Da Progressivo");
	        filtroAProgressivoField.setPlaceholder("A Progressivo");
	        filtroProdottoField.setPlaceholder("Seleziona prodotto");
	        
	        filterButton.addClickListener(event -> refreshGrid());
	        
	        filterLayout.addComponents(filtroMessaggioField, filtroDaDataField, filtroADataField, filtroDaProgressivoField, filtroAProgressivoField, filtroProdottoField, filtroApplicaPackingListField, filtroAttivoField, filterButton);
	        addComponent(filterLayout);
	    }
	    
	    private HorizontalLayout buildActionButtons(CriteriBloccoDatamatrix record) {
	        Button editButton = new Button("Modifica", event -> editRecord(record));
	        Button deleteButton = new Button("Elimina", event -> deleteRecord(record));
	        editButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
	        deleteButton.addStyleName(ValoTheme.BUTTON_DANGER);
	        return new HorizontalLayout(editButton, deleteButton);
	    }
	    
	    private void editRecord(CriteriBloccoDatamatrix record) {
	        currentRecord = record;
	        messaggioField.setValue(record.getMessaggioUtente());
	        daDataField.setValue(record.getDaData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
	        aDataField.setValue(record.getaData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
	        daProgressivoField.setValue(record.getDaProgressivo() != null ? record.getDaProgressivo().toString() : "");
	        aProgressivoField.setValue(record.getaProgressivo() != null ? record.getaProgressivo().toString() : "");
	        applicaPackingListField.setValue(record.getApplicaSoloAPackingList());
	        attivoField.setValue(record.getAttivo());
	    }
	    
	    private void deleteRecord(CriteriBloccoDatamatrix record) {
	        repository.delete(record);
	        refreshGrid();
	    }

	    private void refreshGrid() {
	        List<CriteriBloccoDatamatrix> records = repository.findAll();
	        
	        // Applica i filtri
	        if (!messaggioField.isEmpty()) {
	            String filtroMessaggio = messaggioField.getValue().toLowerCase();
	            records = records.stream()
	                    .filter(r -> r.getMessaggioUtente().toLowerCase().contains(filtroMessaggio))
	                    .collect(Collectors.toList());
	        }
	        
	        if (daDataField.getValue() != null) {
	            records = records.stream()
	                    .filter(r -> !r.getDaData().before(java.sql.Date.valueOf(daDataField.getValue())))
	                    .collect(Collectors.toList());
	        }
	        
	        if (aDataField.getValue() != null) {
	            records = records.stream()
	                    .filter(r -> !r.getaData().after(java.sql.Date.valueOf(aDataField.getValue())))
	                    .collect(Collectors.toList());
	        }
	        
	        if (!daProgressivoField.isEmpty()) {
	            int daProgressivo = Integer.parseInt(daProgressivoField.getValue());
	            records = records.stream()
	                    .filter(r -> r.getDaProgressivo() >= daProgressivo)
	                    .collect(Collectors.toList());
	        }
	        
	        if (!aProgressivoField.isEmpty()) {
	            int aProgressivo = Integer.parseInt(aProgressivoField.getValue());
	            records = records.stream()
	                    .filter(r -> r.getaProgressivo() <= aProgressivo)
	                    .collect(Collectors.toList());
	        }
	        
	        if (filtroProdottoField.getValue() != null) {
	            records = records.stream()
	                    .filter(r -> r.getProdotto().getIdProdotto() == filtroProdottoField.getSelectedItem().get().getIdProdotto())
	                    .collect(Collectors.toList());
	        }
	        
	        if (applicaPackingListField.getValue()) {
	            records = records.stream()
	                    .filter(CriteriBloccoDatamatrix::getApplicaSoloAPackingList)
	                    .collect(Collectors.toList());
	        }
	        
	        if (attivoField.getValue()) {
	            records = records.stream()
	                    .filter(CriteriBloccoDatamatrix::getAttivo)
	                    .collect(Collectors.toList());
	        }
	        
	        grid.setItems(records);
	    }
	    
	    private void clearForm() {
	        currentRecord = null;
	        messaggioField.clear();
	        daDataField.clear();
	        aDataField.clear();
	        daProgressivoField.clear();
	        aProgressivoField.clear();
	        applicaPackingListField.clear();
	        attivoField.clear();
	    }
	    
	    private void saveRecord() {
	        if (messaggioField.isEmpty() || messaggioField.getValue().length() > 300) {
	            Notification.show("Errore", "Il messaggio utente non può essere vuoto e deve avere massimo 300 caratteri.", Notification.Type.ERROR_MESSAGE);
	            return;
	        }
	        
	        if (prodottoField.getValue() == null) {
	            Notification.show("Errore", "Devi selezionare un prodotto valido.", Notification.Type.ERROR_MESSAGE);
	            return;
	        }
	        
	        if (applicaPackingListField.getValue() && messaggioField.isEmpty()) {
	            Notification.show("Errore", "Se attivi 'Applica Solo a Packing List', devi inserire un messaggio utente.", Notification.Type.ERROR_MESSAGE);
	            return;
	        }
	        
	        if (daDataField.getValue() != null && aDataField.getValue() != null && daDataField.getValue().isAfter(aDataField.getValue())) {
	            Notification.show("Errore", "La data iniziale non può essere successiva alla data finale.", Notification.Type.ERROR_MESSAGE);
	            return;
	        }
	        
	        if (!daProgressivoField.isEmpty() && !aProgressivoField.isEmpty()) {
	            int daProgressivo = Integer.parseInt(daProgressivoField.getValue());
	            int aProgressivo = Integer.parseInt(aProgressivoField.getValue());
	            if (daProgressivo > aProgressivo) {
	                Notification.show("Errore", "Il valore del progressivo iniziale deve essere minore o uguale al progressivo finale.", Notification.Type.ERROR_MESSAGE);
	                return;
	            }
	        }
	        
	        List<CriteriBloccoDatamatrix> existingRecords = repository.findByProdottoAndIntervallo(
	            prodottoField.getValue(),
	            java.sql.Date.valueOf(daDataField.getValue()),
	            java.sql.Date.valueOf(aDataField.getValue()),
	            Integer.parseInt(daProgressivoField.getValue()),
	            Integer.parseInt(aProgressivoField.getValue())
	        );
	        if (!existingRecords.isEmpty()) {
	            Notification.show("Errore", "Esiste già un criterio di blocco con gli stessi dati.", Notification.Type.ERROR_MESSAGE);
	            return;
	        }
	        
	        currentRecord = new CriteriBloccoDatamatrix();
	        currentRecord.setMessaggioUtente(messaggioField.getValue());
	        currentRecord.setProdotto(prodottoField.getValue());
	        currentRecord.setDaData(java.sql.Date.valueOf(daDataField.getValue()));
	        currentRecord.setaData(java.sql.Date.valueOf(aDataField.getValue()));
	        currentRecord.setDaProgressivo(Integer.parseInt(daProgressivoField.getValue()));
	        currentRecord.setaProgressivo(Integer.parseInt(aProgressivoField.getValue()));
	        currentRecord.setApplicaSoloAPackingList(applicaPackingListField.getValue());
	        currentRecord.setAttivo(attivoField.getValue());
	        currentRecord.setDataUltimoAggiornamento(new Date());
	        
	        repository.saveOrUpdate(currentRecord);
	        refreshGrid();
	        clearForm();
	    }
	}


