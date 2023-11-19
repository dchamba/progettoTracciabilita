package com.vaadin.demo.dashboard;

import java.util.Locale;

import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.demo.dashboard.component.utils.PermessiUtils.PermessiUtentiLista;
import com.vaadin.demo.dashboard.component.utils.ViewUtils;
import com.vaadin.demo.dashboard.component.view.MyCustomView;
import com.vaadin.demo.dashboard.data.model.Utenti;
import com.vaadin.demo.dashboard.data.repository.RepositoryProvider;
import com.vaadin.demo.dashboard.data.repository.RepositoryUtenti;
import com.vaadin.demo.dashboard.event.DashboardEvent.BrowserResizeEvent;
import com.vaadin.demo.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import com.vaadin.demo.dashboard.event.DashboardEvent.UserLoggedOutEvent;
import com.vaadin.demo.dashboard.event.DashboardEvent.UserLoginRequestedEvent;
import com.vaadin.demo.dashboard.event.DashboardEventBus;
import com.vaadin.demo.dashboard.view.LoginView;
import com.vaadin.demo.dashboard.view.MainView;
import com.vaadin.demo.dashboard.view.stampa.StampaEtichettaImballoView;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@Theme("dashboard")
@Widgetset("com.vaadin.demo.dashboard.DashboardWidgetSet")
@Title("NewOlef DataMatrix Tracking")
@SuppressWarnings("serial")
public final class DashboardUI extends UI {

    /*
     * This field stores an access to the dummy backend layer. In real
     * applications you most likely gain access to your beans trough lookup or
     * injection; and not in the UI but somewhere closer to where they're
     * actually accessed.
     */
    private final DashboardEventBus dashboardEventbus = new DashboardEventBus();
    private RepositoryUtenti repositoryUtenti = null;

    @Override
    protected void init(final VaadinRequest request) {
        setLocale(Locale.US);
        
        this.repositoryUtenti = RepositoryProvider.getRepositoryUtenti();

        DashboardEventBus.register(this);
        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);

        updateContent(request.getPathInfo());

        // Some views need to be aware of browser resize events so a
        // BrowserResizeEvent gets fired to the event bus on every occasion.
        Page.getCurrent().addBrowserWindowResizeListener(
                new BrowserWindowResizeListener() {
                    @Override
                    public void browserWindowResized(
                            final BrowserWindowResizeEvent event) {
                        DashboardEventBus.post(new BrowserResizeEvent());
                    }
                });
        
        setPollInterval(3000);
        addPollListener(p -> {
        	try {
        		MyCustomView view = (MyCustomView)UI.getCurrent().getNavigator().getCurrentView();
        		view.runPollEventHandler();
        	} catch (Exception ex) { }
        });
        
        // Configure the error handler for the UI
        UI.getCurrent().setErrorHandler(new DefaultErrorHandler() {
            @Override
            public void error(com.vaadin.server.ErrorEvent event) {
                String cause = "<b>The click failed because:</b><br/>";
                for (Throwable t = event.getThrowable(); t != null; t = t.getCause()) {
                    if (t.getCause() == null) { // We're at final cause
                        cause += t.getClass().getName() + "<br/>";
                    }
                }

                // Do the default error handling (optional)
                doDefault(event);
            }
        });
    }

    /**
     * Updates the correct content for this UI based on the current user status.
     * If the user is logged in with appropriate privileges, main view is shown.
     * Otherwise login view is shown.
     */
    private void updateContent(String pathInfo) {
        Utenti user = (Utenti) VaadinSession.getCurrent().getAttribute(Utenti.class.getName());
        if (user != null) { //TODO
            // Authenticated user
        	if(pathInfo.contains(ViewUtils.nomeStampaEtichettaImballoView)) {
        		setContent(new StampaEtichettaImballoView(pathInfo));
                removeStyleName("loginview");
        	} else {
                setContent(new MainView());
                removeStyleName("loginview");
                getNavigator().navigateTo(getNavigator().getState());
        	}
        } else {       
        	setContent(new LoginView());
            addStyleName("loginview");
        }
    }

    @Subscribe
    public void userLoginRequested(final UserLoginRequestedEvent event) {
    	Utenti user = null;
        boolean emailInserita = event.getUserName() != null && !event.getUserName().isEmpty();
        boolean passwordInserita = event.getPassword() != null && !event.getPassword().isEmpty();
        
        String errorLogin = "Username or password not correct";
        
        if (emailInserita && passwordInserita) {
        	user = this.repositoryUtenti.getUtenteByEmailAndPassword(event.getUserName(), event.getPassword());
        	if(user == null) {
            	ViewUtils.showErrorNotification(errorLogin);
            	return;
        	}
        } else {
        	//user = this.repositoryUtenti.getUtenti().get(0);
        	ViewUtils.showErrorNotification(errorLogin);
        	return;
        }
        
//        String session = VaadinSession.getCurrent().getSession().getId();
//        user.setHashPerSessionID(session);
        
        VaadinSession.getCurrent().setAttribute(Utenti.class.getName(), user);

//      updateContent(); 
        
        //FIXME 28/04/20 - BUG: LOGIN/ACCESSO al programma con PAGINA FASEVIEW già in URL
        // nella view il faseCorrente=null perchè nel manca il click su dashboard menu che imposta DashboardMenu.MENU_SELECTED
        // soluzione -> dopo login rimando alla Home
        // codice precedente -> updateContent()nView());

        setContent(new MainView());
        removeStyleName("loginview");  
        getNavigator().navigateTo(PermessiUtentiLista.DASHBOARD.toString());     
    }

    @Subscribe
    public void userLoggedOut(final UserLoggedOutEvent event) {
        // When the user logs out, current VaadinSession gets closed and the
        // page gets reloaded on the login screen. Do notice the this doesn't
        // invalidate the current HttpSession.
        VaadinSession.getCurrent().close();
        UI.getCurrent().getNavigator().navigateTo(PermessiUtentiLista.DASHBOARD.toString());
        Page.getCurrent().reload();
    }

    @Subscribe
    public void closeOpenWindows(final CloseOpenWindowsEvent event) {
        for (Window window : getWindows()) {
            window.close();
        }
    }

    public static DashboardEventBus getDashboardEventbus() {
        return ((DashboardUI) getCurrent()).dashboardEventbus;
    }
}
