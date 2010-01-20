/**
 * 
 */
package com.wissen.invoice.client.observers;

/**
 * @author wissen6
 * 
 */
public interface PdfObserver {
	void notifyReportSucceeded(String url);

	void notifyReportFailed(String errMessage);
}
