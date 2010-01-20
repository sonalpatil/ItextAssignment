package com.wissen.invoice.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.wissen.invoice.client.widgets.GenerateWidget;
import com.wissen.invoice.client.widgets.ShowWidget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ItextAsgmnt implements EntryPoint {

	private FlexTable flextable;

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		flextable = new FlexTable();

		flextable.setWidget(1, 0, new GenerateWidget());

		flextable.setWidget(5, 0, new ShowWidget());

		RootPanel.get("pageContent").add(flextable);
	}

}
