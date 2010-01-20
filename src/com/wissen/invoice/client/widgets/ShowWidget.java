/**
 * 
 */
package com.wissen.invoice.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.wissen.invoice.client.controller.Controller;
import com.wissen.invoice.client.observers.PdfObserver;

/**
 * @author wissen6 
 * This class show invoice pdf in frame.
 */

public class ShowWidget extends Composite implements PdfObserver {
	private Frame pdfframe;

	public ShowWidget() {
		pdfframe = new Frame();
		pdfframe.setWidth("600%");
		pdfframe.setHeight("500px");
		initWidget(pdfframe);
		Controller.getInstance().addPdfObserver(this);
	}

	@Override
	public void notifyReportFailed(String errMessage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyReportSucceeded(String url) {
		// TODO Auto-generated method stub
		System.out.println(url);
		pdfframe.setUrl(url);

	}
}
