package com.vaadin.demo.dashboard.view;

import java.util.List;

import com.vaadin.demo.dashboard.DashboardNavigator;
import com.vaadin.demo.dashboard.component.utils.PermessiUtils;import com.vaadin.demo.dashboard.component.utils.PermessiUtils.PermessiUtentiLista;
import com.vaadin.demo.dashboard.component.utils.UtentiUtils;
import com.vaadin.demo.dashboard.component.utils.ViewUtils;
import com.vaadin.demo.dashboard.data.model.FasiProcesso;
import com.vaadin.demo.dashboard.data.repository.RepositoryProvider;
import com.vaadin.demo.dashboard.view.DashboardMenu.ValoMenuItemButton;
import com.vaadin.demo.dashboard.view.fasiprocesso.FasiProcessoView;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

/*
 * Dashboard MainView is a simple HorizontalLayout that wraps the menu on the
 * left and creates a simple container for the navigator on the right.
 */
@SuppressWarnings("serial")
public class MainView extends HorizontalLayout {

    public MainView() {
        setSizeFull();
        addStyleName("mainview");
        setSpacing(false);

//        DashboardViewType.listDashboardViewType.clear();
//        DashboardViewType.addValuesToDashboardViewType();
        
        addComponent(new DashboardMenu());

        ComponentContainer content = new CssLayout();
        content.addStyleName("view-content");
        content.setSizeFull();
        addComponent(content);
        setExpandRatio(content, 1.0f);

        new DashboardNavigator(content);
    }
}
