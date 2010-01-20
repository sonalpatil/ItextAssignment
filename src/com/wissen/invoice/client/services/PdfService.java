/**
 * 
 */
package com.wissen.invoice.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.InvocationException;

/**
 * @author wissen6
 * 
 */
@SuppressWarnings("unused")
@RemoteServiceRelativePath("sonal")
public interface PdfService extends RemoteService {
	public String generatePdf();
}
