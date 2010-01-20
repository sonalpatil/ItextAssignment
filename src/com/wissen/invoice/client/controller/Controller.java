package com.wissen.invoice.client.controller;

import java.util.ArrayList;
import java.util.List;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wissen.invoice.client.controller.Controller;
import com.wissen.invoice.client.observers.PdfObserver;
import com.wissen.invoice.client.services.PdfService;
import com.wissen.invoice.client.services.PdfServiceAsync;

public class Controller {
	private final PdfServiceAsync pdfService = GWT.create(PdfService.class);
	private static Controller _instance;

	private Controller() {
	}

	public static Controller getInstance() {
		// TODO Auto-generated method stub
		if (_instance == null) {
			_instance = new Controller();
		}
		return _instance;

	}

	public PdfServiceAsync getPdfService() {
		return pdfService;
	}

	public void generatePdf() {
		// TODO Auto-generated method stub
		pdfService.generatePdf(pdfCallback);

	}

	private List<PdfObserver> pdfObservers = new ArrayList<PdfObserver>();

	// --------------- Registration/DeRegitration methods --------------
	public void addPdfObserver(PdfObserver observer) {
		pdfObservers.add(observer);
	}

	public void removePdfObserver(PdfObserver observer) {
		pdfObservers.remove(observer);
	}

	AsyncCallback<String> pdfCallback = new AsyncCallback<String>() {
		@Override
		public void onFailure(Throwable caught) {
			GWT.log("Error in conversion", caught);
			for (PdfObserver observer : pdfObservers) {
				observer.notifyReportFailed("Report Generation fail :: "
						+ caught);
			}
		}

		@Override
		public void onSuccess(String url) {
			// TODO Auto-generated method stub
			for (PdfObserver observer : pdfObservers) {
				observer.notifyReportSucceeded(url);
				Window.alert(" pdf generated successfully.....");
			}
		}
	};
}
