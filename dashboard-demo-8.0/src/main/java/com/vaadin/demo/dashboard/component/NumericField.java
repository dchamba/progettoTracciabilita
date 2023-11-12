package com.vaadin.demo.dashboard.component;

import com.vaadin.demo.dashboard.component.utils.ViewUtils;
import com.vaadin.ui.TextField;

public class NumericField extends TextField  { 
	
	public NumericField(String caption) {
		super(caption);
		
	    this.addValueChangeListener(new ValueChangeListener<String>() {
			
			@Override
			public void valueChange(ValueChangeEvent<String> event) {
			    String text = event.getValue();
			    try {
			        new Double(text);
			    } catch (NumberFormatException e) {
			    	ViewUtils.showErrorNotification("Only number value allowed");
			        setValue("");
			    }
			}
		});
	}
}
