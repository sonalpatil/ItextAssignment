/**
 * 
 */
package com.wissen.invoice.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.wissen.invoice.client.controller.Controller;
import com.wissen.invoice.client.observers.PdfObserver;

/**
 * @author wissen6 
 * This class used to perform click event to generate pdf.
 */
public class GenerateWidget extends Composite implements PdfObserver {
	private HorizontalPanel buttonPanel;
	private Button generatebutton;

	public GenerateWidget() {
		buttonPanel = new HorizontalPanel();
		generatebutton = new Button("Generate Report");
		buttonPanel.add(generatebutton);

		generatebutton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				generatePdf();
			}
		});
		initWidget(buttonPanel);
		Controller.getInstance().addPdfObserver(this);
	}

	private void generatePdf() {
		Controller.getInstance().generatePdf();
	}

	@Override
	public void notifyReportFailed(String errMessage) {
		// TODO Auto-generated method stub
		Window.alert(errMessage);
	}

	@Override
	public void notifyReportSucceeded(String url) {
		// TODO Auto-generated method stub

	}
}
