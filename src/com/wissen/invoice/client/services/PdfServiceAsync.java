/**
 * 
 */
package com.wissen.invoice.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.StatusCodeException;

/**
 * @author wissen6
 * 
 */
@SuppressWarnings("unused")
public interface PdfServiceAsync {
	void generatePdf(AsyncCallback<String> pdfCallback);
}
