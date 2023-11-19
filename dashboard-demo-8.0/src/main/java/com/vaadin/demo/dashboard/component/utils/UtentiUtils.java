package com.vaadin.demo.dashboard.component.utils;

import com.vaadin.demo.dashboard.data.model.Utenti;
import com.vaadin.server.VaadinSession;

public class UtentiUtils {
	
    public static Utenti getCurrentUser() {
        return (Utenti) VaadinSession.getCurrent().getAttribute(Utenti.class.getName());
    }
}
