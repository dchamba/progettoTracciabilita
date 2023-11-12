package com.vaadin.demo.dashboard.component.view;

import com.vaadin.demo.dashboard.event.DashboardEventBus;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class MyCustomView extends VerticalLayout implements View {

	public MyCustomView() { 
//        setSizeFull();
//        setMargin(false);
//        setSpacing(false);
//        addStyleName(StyleUtils.myCustomViewStyle);
	}
	
	@Override
	public void enter(ViewChangeEvent event) { }
	
	@Override
	public void detach() {
        super.detach();
        // A new instance of TransactionsView is created every time it's
        // navigated to so we'll need to clean up references to it on detach.
        DashboardEventBus.unregister(this);
	}
	
	public void runPollEventHandler() { }
}
