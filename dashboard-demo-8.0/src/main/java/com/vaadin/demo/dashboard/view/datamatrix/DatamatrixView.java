package com.vaadin.demo.dashboard.view.datamatrix;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.google.common.eventbus.Subscribe;
import com.vaadin.addon.pagination.Pagination;
import com.vaadin.addon.pagination.PaginationResource;
import com.vaadin.data.provider.Query;
import com.vaadin.demo.dashboard.component.exporter.tableexport.DefaultGridHolder;
import com.vaadin.demo.dashboard.component.exporter.tableexport.ExcelExport;
import com.vaadin.demo.dashboard.component.exporter.tableexport.TableHolder;
import com.vaadin.demo.dashboard.component.model.FilterDatamatrix;
import com.vaadin.demo.dashboard.component.model.GridPagination;
import com.vaadin.demo.dashboard.component.model.GridResult;
import com.vaadin.demo.dashboard.component.style.StyleUtils;
import com.vaadin.demo.dashboard.component.utils.CommonUtils;
import com.vaadin.demo.dashboard.component.utils.PermessiUtils;
import com.vaadin.demo.dashboard.component.utils.UtentiUtils;
import com.vaadin.demo.dashboard.component.utils.ViewUtils;
import com.vaadin.demo.dashboard.component.view.MyCustomView;
import com.vaadin.demo.dashboard.data.model.Aziende;
import com.vaadin.demo.dashboard.data.model.Datamatrix;
import com.vaadin.demo.dashboard.data.model.Utenti;
import com.vaadin.demo.dashboard.data.repository.RepositoryDatamatrix;
import com.vaadin.demo.dashboard.data.repository.RepositoryProvider;
import com.vaadin.demo.dashboard.event.DashboardEvent.BrowserResizeEvent;
import com.vaadin.demo.dashboard.event.DashboardEventBus;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.TextRenderer;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class DatamatrixView extends MyCustomView {

	private Grid<Datamatrix> grid;
	private Button createReport;
	private RepositoryDatamatrix repositoryDatamatrix = null;
	
	private GridPagination pagination = new GridPagination(); 
	private PaginationResource paginationResource = null;

	private TextField textDatamatrixFilter, textCodiceProdottoFilter;
	private DateField dateFilterFrom, dateFilterTo;

//	private List<Datamatrix> listaDatamatrixDataSource = new ArrayList<Datamatrix>();
//	private ListDataProvider<Datamatrix> dataProvider = DataProvider.ofCollection(listaDatamatrixDataSource);
	
	private List<Column<Datamatrix, ?>> columnList = new ArrayList<Column<Datamatrix, ?>>();
	
	private final Set<Column<Datamatrix, ?>> collapsibleColumns = new LinkedHashSet<>();

	public DatamatrixView() {
		setSizeFull();
		addStyleName("transactions");
		setMargin(false);
		setSpacing(false);

		DashboardEventBus.register(this);
		
		this.repositoryDatamatrix = RepositoryProvider.getRepositoryDatamatrix();

		buildToolbar();
		buildGrid();
	}

	private void buildToolbar() {
		HorizontalLayout header = new HorizontalLayout();
		header.addStyleName(StyleUtils.viewHeaderStyle);
		Responsive.makeResponsive(header);

		Label title = new Label(ViewUtils.titoloDataMatrix);
		title.setSizeUndefined();
		title.addStyleName(ValoTheme.LABEL_H1);
		title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		header.addComponent(title);

		createReport = new Button("Export xls");
		createReport.setDescription("Export data to xls file");
		createReport.addClickListener(event -> createNewReportFromSelection());

		textDatamatrixFilter = new TextField();
		textDatamatrixFilter.setPlaceholder("Datamatrix");
		textDatamatrixFilter.setIcon(FontAwesome.SEARCH);
		textDatamatrixFilter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		textDatamatrixFilter.addShortcutListener(new ShortcutListener("Clear", KeyCode.ESCAPE, null) {
			@Override
			public void handleAction(final Object sender, final Object target) {
				textDatamatrixFilter.setValue("");
			}
		});
		textDatamatrixFilter.addValueChangeListener(event -> {
			if (grid == null) {
				return;
			}
			updateGridDataSource();
		});

		textCodiceProdottoFilter = new TextField();
		textCodiceProdottoFilter.setPlaceholder("Product");
		textCodiceProdottoFilter.setIcon(FontAwesome.SEARCH);
		textCodiceProdottoFilter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		textCodiceProdottoFilter.addShortcutListener(new ShortcutListener("Clear", KeyCode.ESCAPE, null) {
			@Override
			public void handleAction(final Object sender, final Object target) {
				textCodiceProdottoFilter.setValue("");
			}
		});
		textCodiceProdottoFilter.addValueChangeListener(event -> {
			if (grid == null) {
				return;
			}
			updateGridDataSource();
		});
		
		dateFilterFrom = new DateField();
		dateFilterFrom.setPlaceholder("From day");
		dateFilterFrom.setDateFormat(CommonUtils.DATEFORMAT.toString());
		dateFilterFrom.setIcon(FontAwesome.CALENDAR);
		dateFilterFrom.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		dateFilterFrom.addValueChangeListener(event -> {
			if (grid == null) {
				return;
			}
			updateGridDataSource();
		});

		dateFilterTo = new DateField();
		dateFilterTo.setPlaceholder("To day");
		dateFilterTo.setDateFormat(CommonUtils.DATEFORMAT.toString());
		dateFilterTo.setIcon(FontAwesome.CALENDAR);
		dateFilterTo.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		dateFilterTo.addValueChangeListener(event -> {
			if (grid == null) {
				return;
			}
			updateGridDataSource();
		});

		//HorizontalLayout tools = new HorizontalLayout(textDatamatrixFilter, textCodiceProdottoFilter, dateFilterFrom, dateFilterTo, createReport);
		HorizontalLayout tools = new HorizontalLayout(textDatamatrixFilter, textCodiceProdottoFilter, createReport);
		tools.addStyleName(StyleUtils.toolbarStyle);
		header.addComponent(tools);
		
		addComponent(header);
	}

	private void buildGrid() {
		grid = new Grid<Datamatrix>();
		grid.setSizeFull();

		//grid.addColumn(value -> "", new RowIndexRenderer()).setCaption("Row index");
		columnList.add(grid.addColumn(Datamatrix::getDataMatrix).setCaption("Datamatrix").setId("datamatrix"));
		columnList.add(grid.addColumn(Datamatrix::getCodiceProdotto).setCaption("Product").setId("product"));
		columnList.add(grid.addColumn(Datamatrix -> CommonUtils.DATETIMEFORMAT.format(Datamatrix.getDataOraCreazione()), new TextRenderer("")).setCaption("Date/time").setId("dateTime"));
		columnList.add(grid.addColumn(Datamatrix::getNomeUtenteCreatore, new TextRenderer("")).setCaption("RX Operator").setId("user"));
		columnList.add(grid.addColumn(Datamatrix::trattamentoHttNumeroFornataCodificato, new TextRenderer("")).setCaption("Heat number").setId("heatNumber"));
		columnList.add(grid.addColumn(Datamatrix::trattamentoHttValoreDurezzaCodificato, new TextRenderer("")).setCaption("Hardness value").setId("hardnessValue"));
		columnList.add(grid.addColumn(Datamatrix -> CommonUtils.formatDate(Datamatrix.trattamentoHttDataOra(), CommonUtils.DATETIMEFORMAT)).setCaption("Date/time").setId("heatDateTime"));
		columnList.add(grid.addColumn(Datamatrix::trattamentoHttNomeUtente, new TextRenderer("")).setCaption("HT operator").setId("heatOperator"));

		grid.setColumnReorderingAllowed(true);
		
//		DataProvider<Datamatrix, Void> dataProvider = DataProvider.fromCallbacks<Datamatrix>(
//				query -> {
//				    // The index of the first item to load
//				    int offset = query.getOffset();
//
//				    // The number of items to load
//				    int limit = query.getLimit();
//
//				    this.pagination.setLimit(limit);
//				    this.pagination.setOffset(offset);
//				    
//				    List<Datamatrix> listaDatamatrix = this.runFiltersAndSetDataProvider();
//				    //return listaDatamatrix.stream();
//				  },
//				  
//				  // Second callback fetches the number of items for a query
//				  query -> { 
//					  getPersonService().getPersonCount();
//				  }
//	  	);
//		grid.setDataProvider(dataProvider);
		
		
		
		
//		grid.setDataProvider(
//			    (sortOrders, offset, limit) -> {
//			    	
//			    	this.pagination.setOffset(offset);
//			    	this.pagination.setLimit(limit);
//			    	this.pagination.setSortOrders(sortOrders);
//			    	
//			    	return this.runFiltersAndSetDataProvider().stream();
//			    },
//			    () -> {
//			    	return this.runFiltersAndSetDataProvider().size();
//			    }
//			);

		Long totalItems = this.runFiltersAndSetDataProvider().getResultCount();
		
		paginationResource = PaginationResource.newBuilder().setTotal(totalItems).setPage(0).setLimit(ViewUtils.defaultPaginationLimit).build();
		Pagination pagination = new Pagination(paginationResource);
		pagination.setItemsPerPage(10, 20, 50, 100);
		pagination.addPageChangeListener(p -> {
				runFiltersAndSetDataProvider();
				grid.scrollToStart();
			});
		    
		VerticalLayout gridLayout = new VerticalLayout();
		gridLayout.setSizeFull();
		gridLayout.setSpacing(true);
		gridLayout.addComponents(grid, pagination);
		gridLayout.setExpandRatio(grid, 1f);
		
		addComponent(gridLayout);
		setExpandRatio(gridLayout, 1);
		
//		addComponent(grid);
//		setExpandRatio(grid, 1);

//		this.grid.getDataProvider().refreshAll();
		this.updateGridDataSource();
	}

//	public List<Datamatrix> runFiltersAndSetDataProvider(List<QuerySortOrder> sortOrders , Integer offset, Integer limit) {
	private GridResult<Datamatrix> runFiltersAndSetDataProvider() {

		dateFilterTo.setComponentError(null);
		dateFilterFrom.setComponentError(null);
		
		GridResult<Datamatrix> listaDatamatrixAggiornata = this.repositoryDatamatrix.getDataMatrix(getFilterDatamatrix(), this.paginationResource);
		return listaDatamatrixAggiornata;
	}

	private FilterDatamatrix getFilterDatamatrix() {
		String textFilterValue = "";
		if (textDatamatrixFilter.getValue() != null && !textDatamatrixFilter.getValue().trim().isEmpty()) {
			textFilterValue = textDatamatrixFilter.getValue().trim().toLowerCase();
		}

		Date fromDay = null;
		if (dateFilterFrom.getValue() != null) {
			fromDay = CommonUtils.toDate(dateFilterFrom.getValue());
		}

		Date toDay = null;
		if (dateFilterTo.getValue() != null) {
			toDay = CommonUtils.toDate(dateFilterTo.getValue());
		}
		
		String textCodiceProdotto = "";
		if (textCodiceProdottoFilter.getValue() != null && !textCodiceProdottoFilter.getValue().trim().isEmpty()) {
			textCodiceProdotto = textCodiceProdottoFilter.getValue().trim().toLowerCase();
		}

		FilterDatamatrix fitlerDatamatrix = new FilterDatamatrix();
		fitlerDatamatrix.setDatamatrix(textFilterValue);
		fitlerDatamatrix.setDataInizio(fromDay);
		fitlerDatamatrix.setDataFine(toDay);
		fitlerDatamatrix.setCodiceProdotto(textCodiceProdotto);
		fitlerDatamatrix.setIdAziendaUtente(getFiltroIdAzienda());
		
		return fitlerDatamatrix;
	}

	private Integer getFiltroIdAzienda() {
		Integer idAzienda = null;
		Utenti utente = UtentiUtils.getCurrentUser();
		boolean isUtenteSuperUserOAmministratore = utente.getTipoUtente().equals(PermessiUtils.tipoUtenteSuperUser) || 
													utente.getTipoUtente().equals(PermessiUtils.tipoUtenteUtenteAmministratore);
		if(!isUtenteSuperUserOAmministratore) {
			Aziende azienda = utente.getAzienda();
			if (azienda != null) {
				idAzienda = azienda.getIdAzienda();
			}
		}
		return idAzienda;
	}

	@Override
	public void runPollEventHandler() {
		updateGridDataSource();
	}
	
	private void updateGridDataSource() {
		this.grid.setItems(this.runFiltersAndSetDataProvider().getResult());
	}

	private boolean defaultColumnsVisible() {
		boolean result = true;
		for (Column<Datamatrix, ?> column : collapsibleColumns) {
			if (column.isHidden() == Page.getCurrent().getBrowserWindowWidth() < 800) {
				result = false;
			}
		}
		return result;
	}

	@Subscribe
	public void browserResized(final BrowserResizeEvent event) {
		// Some columns are collapsed when browser window width gets small
		// enough to make the table fit better.

		if (defaultColumnsVisible()) {
			for (Column<Datamatrix, ?> column : collapsibleColumns) {
				column.setHidden(Page.getCurrent().getBrowserWindowWidth() < 800);
			}
		}
	}

	public void createNewReportFromSelection() {
        // put the Grid in the TableHolder after the grid is fully baked
		TableHolder tableHolder = new DefaultGridHolder(this.grid);

        ExcelExport excelExport = new ExcelExport(tableHolder, "Datamatrix");
        
        excelExport.setReportTitle("Datamatrix");
        excelExport.setExportFileName("Datamatrix.xls");

        excelExport.setDisplayTotals(false);
        excelExport.setRowHeaders(true);
        //excelExport.setExcelFormatOfProperty("date", excelDateFormat.getValue().toString());
        //excelExport.setDoubleDataFormat(excelNumberFormat.getValue().toString());
        excelExport.export();
	}
	
//    private boolean passesFilter(String subject) {
//        if (subject == null) {
//            return false;
//        }
//        return subject.trim().toLowerCase().contains(textFilterValue);
//    }
}
