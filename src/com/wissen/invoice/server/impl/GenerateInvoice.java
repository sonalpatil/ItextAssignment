/**
 * 
 */
package com.wissen.invoice.server.impl;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.wissen.invoice.client.services.PdfService;

/**
 * @author wissen6 This class represents the generation of invoice in pdf file
 *         ith itext.
 */

@SuppressWarnings("serial")
public class GenerateInvoice extends RemoteServiceServlet implements PdfService {
	String username = "root";

	String password = "wissen";

	Connection con = null;

	ResultSet rs = null;

	Statement stmt = null;

	String url = "jdbc:mysql://localhost:3306/Employee";
	String invoice_id, cust_id, cust_name, cust_addr, description, amount,
			subtotal, taxrate, tax, other, total;
	String pdfurl;

	@Override
	/*
	 * This method generates pdf.
	 */
	public String generatePdf() {
		// TODO Auto-generated method stub
		try {
			pdfurl = "http://localhost:8090/itextasgment/Invoice.pdf";
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream("Invoice.pdf"));
			document.open();
			getData();
			BaseColor baseWissen = new BaseColor(0x00, 0x00, 0x00);
			Font fontWissen = new Font(Font.TIMES_ROMAN, 16, Font.BOLD);
			fontWissen.setColor(baseWissen);
			Phrase phraseWissen = new Phrase("    Wissen Labs", fontWissen);
			Paragraph parWissen = new Paragraph();
			parWissen.add(phraseWissen);
			parWissen.setAlignment(Element.ALIGN_LEFT);
			document.add(parWissen);

			BaseColor baseInvoice = new BaseColor(0x83, 0x94, 0xC9);
			Font fontInvoice = new Font(Font.TIMES_ROMAN, 25, Font.BOLD);
			fontInvoice.setColor(baseInvoice);
			Phrase phraseInvoice = new Phrase("INVOICE", fontInvoice);
			Paragraph parInvoice = new Paragraph();
			parInvoice.add(phraseInvoice);
			parInvoice.setSpacingAfter(5);
			parInvoice.setAlignment(Element.ALIGN_RIGHT);
			document.add(parInvoice);

			PdfPTable pdftable = new PdfPTable(2);
			PdfPCell date = new PdfPCell(new Paragraph("DATE :", new Font(
					Font.TIMES_ROMAN, 12, Font.BOLD)));
			date.disableBorderSide(PdfPCell.TOP);
			date.disableBorderSide(PdfPCell.BOTTOM);
			date.disableBorderSide(PdfPCell.RIGHT);
			date.disableBorderSide(PdfPCell.LEFT);
			pdftable.addCell(date);

			// To get today date
			DateFormat dateFormat = new SimpleDateFormat("MMM-d-yyyy");
			java.util.Date todaydate = new java.util.Date();
			String dateStr = dateFormat.format(todaydate);
			PdfPCell dt = new PdfPCell(new Paragraph(dateStr, new Font(
					Font.TIMES_ROMAN, 12, Font.BOLD)));
			dt.disableBorderSide(PdfPCell.RIGHT);
			dt.disableBorderSide(PdfPCell.LEFT);
			dt.disableBorderSide(PdfPCell.TOP);
			dt.setBackgroundColor(baseInvoice);
			dt.setHorizontalAlignment(Element.ALIGN_CENTER);
			pdftable.addCell(dt);

			PdfPCell invoice = new PdfPCell(new Paragraph("INVOICE#", new Font(
					Font.TIMES_ROMAN, 12, Font.BOLD)));
			invoice.disableBorderSide(PdfPCell.TOP);
			invoice.disableBorderSide(PdfPCell.BOTTOM);
			invoice.disableBorderSide(PdfPCell.RIGHT);
			invoice.disableBorderSide(PdfPCell.LEFT);
			pdftable.addCell(invoice);

			PdfPCell inv = new PdfPCell(new Paragraph(invoice_id, new Font(
					Font.TIMES_ROMAN, 12, Font.BOLD)));
			inv.setHorizontalAlignment(Element.ALIGN_CENTER);
			pdftable.addCell(inv);

			PdfPCell customer = new PdfPCell(new Paragraph("CustomerID",
					new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
			customer.disableBorderSide(PdfPCell.TOP);
			customer.disableBorderSide(PdfPCell.BOTTOM);
			customer.disableBorderSide(PdfPCell.RIGHT);
			customer.disableBorderSide(PdfPCell.LEFT);
			pdftable.addCell(customer);

			PdfPCell cust = new PdfPCell(new Paragraph(cust_id, new Font(
					Font.TIMES_ROMAN, 12, Font.BOLD)));
			cust.setHorizontalAlignment(Element.ALIGN_CENTER);
			pdftable.addCell(cust);

			pdftable.setTotalWidth(200f);
			pdftable.setLockedWidth(true);
			pdftable.setHorizontalAlignment(Element.ALIGN_RIGHT);
			PdfContentByte pcb = writer.getDirectContent();

			pdftable.writeSelectedRows(0, 3, 700, 700, pcb);
			document.add(pdftable);

			Phrase address1 = new Phrase("4th Floor, Rajiv Enclave");
			Phrase address2 = new Phrase(
					"New Pandit Colony, Nasik, Maharashtra, India");
			Phrase phone = new Phrase("91 253 301 2029/ 91 253 301 2038 ");
			Paragraph address = new Paragraph();
			address.add(address1);
			address.add(Chunk.NEWLINE);
			address.add(address2);
			address.add(Chunk.NEWLINE);
			address.add(phone);
			address.add(Chunk.NEWLINE);
			address.setAlignment(Element.ALIGN_LEFT);
			document.add(address);

			document.add(Chunk.NEWLINE);

			BaseColor billbase = new BaseColor(0xf9, 0xf3, 0xf3);
			Font fontbill = new Font(Font.COURIER, 13, Font.BOLD);
			fontbill.setColor(billbase);
			Chunk billto = new Chunk("BILL TO :               ", fontbill);
			billto.setBackground(new BaseColor(0x3B, 0x4E, 0x87));
			Phrase bill2 = new Phrase();
			bill2.add(billto);
			bill2.add(Chunk.NEWLINE);
			bill2.add(cust_name);
			bill2.add(Chunk.NEWLINE);
			bill2.add(cust_addr);
			bill2.add(Chunk.NEWLINE);
			bill2.add("Hong Kong");
			Paragraph bill = new Paragraph();
			bill.add(bill2);
			bill.setAlignment(Element.ALIGN_LEFT);
			document.add(bill);

			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);

			float[] widths = { 8f, 2f };
			PdfPTable billtable = new PdfPTable(widths);
			billtable.setWidthPercentage(100);
			billtable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

			PdfPCell cell1 = new PdfPCell(
					new Paragraph("DESCRIPTION", fontbill));
			cell1.setVerticalAlignment(Element.ALIGN_CENTER);
			cell1.setBackgroundColor(new BaseColor(0x3B, 0x4E, 0x87));
			cell1.disableBorderSide(PdfPCell.TOP);
			cell1.disableBorderSide(PdfPCell.BOTTOM);
			cell1.disableBorderSide(PdfPCell.RIGHT);
			cell1.disableBorderSide(PdfPCell.LEFT);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			billtable.addCell(cell1);

			PdfPCell cell2 = new PdfPCell(new Paragraph("AMOUNT", fontbill));
			cell2.setVerticalAlignment(Element.ALIGN_CENTER);
			cell2.disableBorderSide(PdfPCell.TOP);
			cell2.disableBorderSide(PdfPCell.BOTTOM);
			cell2.disableBorderSide(PdfPCell.RIGHT);
			cell2.disableBorderSide(PdfPCell.LEFT);
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBackgroundColor(new BaseColor(0x3B, 0x4E, 0x87));
			billtable.addCell(cell2);

			PdfPCell description = new PdfPCell(new Paragraph(this.description));
			PdfPCell amount = new PdfPCell(new Paragraph(this.amount + ".00"));
			amount.disableBorderSide(Element.ALIGN_LEFT);
			description.disableBorderSide(Element.ALIGN_LEFT);
			description.setExtraParagraphSpace(150f);
			amount.setExtraParagraphSpace(180f);
			description.setHorizontalAlignment(Element.ALIGN_CENTER);
			amount.setHorizontalAlignment(Element.ALIGN_CENTER);
			description.setBorderWidth(1);
			amount.setBorderWidth(1);
			billtable.addCell(description);
			billtable.addCell(amount);
			document.add(billtable);

			PdfPTable calculateTable = new PdfPTable(2);
			calculateTable.setWidthPercentage(40);
			calculateTable.setSpacingBefore(1f);
			calculateTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
			PdfPCell subtotal = new PdfPCell(new Paragraph("SUBTOTAL"));
			subtotal.disableBorderSide(PdfPCell.TOP);
			subtotal.disableBorderSide(PdfPCell.RIGHT);
			subtotal.disableBorderSide(PdfPCell.BOTTOM);
			subtotal.disableBorderSide(PdfPCell.LEFT);
			subtotal.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			calculateTable.addCell(subtotal);

			PdfPCell subtotalval = new PdfPCell(new Paragraph("$"
					+ this.subtotal + ".00"));
			subtotalval.setBackgroundColor(baseInvoice);
			subtotalval.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			subtotalval.disableBorderSide(PdfPCell.TOP);
			subtotalval.disableBorderSide(PdfPCell.BOTTOM);
			subtotalval.disableBorderSide(PdfPCell.RIGHT);
			subtotalval.disableBorderSide(PdfPCell.LEFT);
			calculateTable.addCell(subtotalval);

			PdfPCell taxrate = new PdfPCell(new Paragraph("TAX RATE"));
			taxrate.disableBorderSide(PdfPCell.TOP);
			taxrate.disableBorderSide(PdfPCell.RIGHT);
			taxrate.disableBorderSide(PdfPCell.BOTTOM);
			taxrate.disableBorderSide(PdfPCell.LEFT);
			taxrate.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			calculateTable.addCell(taxrate);

			PdfPCell taxrateval = new PdfPCell(new Paragraph(this.taxrate
					+ ".000%"));
			taxrateval.setBackgroundColor(billbase);
			taxrateval.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			taxrateval.disableBorderSide(PdfPCell.TOP);
			taxrateval.disableBorderSide(PdfPCell.BOTTOM);
			taxrateval.disableBorderSide(PdfPCell.RIGHT);
			taxrateval.disableBorderSide(PdfPCell.LEFT);
			calculateTable.addCell(taxrateval);

			PdfPCell tax = new PdfPCell(new Paragraph("TAX "));
			tax.disableBorderSide(PdfPCell.TOP);
			tax.disableBorderSide(PdfPCell.RIGHT);
			tax.disableBorderSide(PdfPCell.BOTTOM);
			tax.disableBorderSide(PdfPCell.LEFT);
			tax.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			calculateTable.addCell(tax);

			PdfPCell taxval = new PdfPCell(
					new Paragraph("$" + this.tax + ".00"));
			taxval.setBackgroundColor(baseInvoice);
			taxval.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			taxval.disableBorderSide(PdfPCell.TOP);
			taxval.disableBorderSide(PdfPCell.BOTTOM);
			taxval.disableBorderSide(PdfPCell.RIGHT);
			taxval.disableBorderSide(PdfPCell.LEFT);
			calculateTable.addCell(taxval);

			PdfPCell other = new PdfPCell(new Paragraph("OTHER"));
			other.disableBorderSide(PdfPCell.TOP);
			other.disableBorderSide(PdfPCell.RIGHT);
			other.disableBorderSide(PdfPCell.LEFT);
			other.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			calculateTable.addCell(other);

			PdfPCell otherval = new PdfPCell(new Paragraph("$" + this.other
					+ ".00"));
			otherval.setBackgroundColor(billbase);
			otherval.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			otherval.disableBorderSide(PdfPCell.TOP);

			otherval.disableBorderSide(PdfPCell.RIGHT);
			otherval.disableBorderSide(PdfPCell.LEFT);
			calculateTable.addCell(otherval);

			PdfPCell total = new PdfPCell(new Paragraph(Font.BOLD, "TOTAL"));
			total.disableBorderSide(PdfPCell.RIGHT);
			total.disableBorderSide(PdfPCell.BOTTOM);
			total.disableBorderSide(PdfPCell.LEFT);
			total.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			calculateTable.addCell(total);

			PdfPCell totalval = new PdfPCell(new Paragraph("$" + this.total
					+ ".00"));
			totalval.setBackgroundColor(baseInvoice);
			totalval.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			totalval.disableBorderSide(PdfPCell.BOTTOM);
			totalval.disableBorderSide(PdfPCell.RIGHT);
			totalval.disableBorderSide(PdfPCell.LEFT);
			calculateTable.addCell(totalval);

			Font comments = new Font(Font.COURIER, 12, Font.BOLD);
			PdfPCell commentcell = new PdfPCell(new Paragraph("OTHER COMMENTS",
					comments));
			commentcell.setGrayFill(.5f);
			PdfPTable commenttable = new PdfPTable(1);
			commenttable.addCell(commentcell);
			commenttable.setHorizontalAlignment(Element.ALIGN_LEFT);
			commenttable.setWidthPercentage(65);

			Phrase comment = new Phrase("1.Total Payments Due in 30 days.",
					new Font(Font.TIMES_ROMAN, 12));
			comment.add(Chunk.NEWLINE);
			comment.add("2.Please include the invoice no. on your check.");
			comment.add(Chunk.NEWLINE);
			comment.add(Chunk.NEWLINE);
			comment.add(Chunk.NEWLINE);
			PdfPCell commentline = new PdfPCell(comment);
			commenttable.addCell(commentline);
			PdfContentByte commentpcb = writer.getDirectContent();
			commenttable.setTotalWidth(250f);
			commenttable.writeSelectedRows(0, -1, 50, 320, commentpcb);
			commenttable.setLockedWidth(true);
			document.add(calculateTable);
			calculateTable.setSpacingAfter(50f);

			Phrase make = new Phrase();
			make.add("   Make Checks Payable To");
			make.add(Chunk.NEWLINE);
			make.add("Wissen Labs      ");
			Paragraph wissen = new Paragraph();
			wissen.setAlignment(Element.ALIGN_RIGHT);
			wissen.add(make);
			document.add(Chunk.NEWLINE);
			document.add(wissen);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);

			Font font = new Font(Font.TIMES_ROMAN, 16, Font.BOLD);
			font.setColor(new BaseColor(0x00, 0x00, 0x00));
			Chunk footer = new Chunk("Thank You For Your Business!", font);

			Phrase lastmsg = new Phrase(
					"If you have any questions about this invoice,please contact");
			lastmsg.add(Chunk.NEWLINE);
			lastmsg.add("Sushrut Bidwai,");
			Font fontAnchor = new Font();
			fontAnchor.setColor(new BaseColor(0x00, 0x48, 0xFF));
			Chunk emlid = new Chunk("sushrut@wissen.co.in", fontAnchor);
			emlid.setAnchor("http://wissen.co.in");
			lastmsg.add(emlid);
			lastmsg.add(",+91 986 023 8124");
			lastmsg.add(Chunk.NEWLINE);
			lastmsg.add(Chunk.NEWLINE);
			lastmsg.add(footer);
			Paragraph last = new Paragraph();
			last.add(lastmsg);
			last.setAlignment(Element.ALIGN_CENTER);
			document.add(last);
			document.add(Chunk.NEWLINE);

			Paragraph page = new Paragraph("Page1");
			page.setAlignment(Element.ALIGN_CENTER);
			document.add(page);
			document.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return pdfurl;
	}

	/**
	 * This method gets data(cust_name,addr,item description etc) from invoice
	 * database.
	 */
	public void getData() {

		makeconnection();
		try {
			String query = "select * from Invoice where invoice_id=63";
			System.out.println(query);
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				// get values from db
				invoice_id = rs.getString(1);
				cust_id = rs.getString(2);
				cust_name = rs.getString(3);
				cust_addr = rs.getString(4);
				description = rs.getString(5);
				amount = rs.getString(6);
				subtotal = rs.getString(7);
				taxrate = rs.getString(8);
				tax = rs.getString(9);
				other = rs.getString(10);
				total = rs.getString(11);
				System.out.println(invoice_id + cust_id + cust_addr + cust_name
						+ description + amount + subtotal + taxrate + tax
						+ other + total);
			}// if
			else {
				System.out.println("Invalid Invoice ");
			}
			closeconnection();
		}// try
		catch (SQLException e) {
			System.out.println("SQL Error :: " + e);
		}
	}

	/**
	 * This method establish db connection.
	 */
	public void makeconnection() {
		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);

			if (con != null) {
				System.out.println("Connection Established");
			}
		} catch (Exception e) {
			System.out.println("Err" + e);
		}
	}// connection

	/**
	 * This method close invoice db connection.
	 */
	public void closeconnection() {
		try {
			con.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println("Err : " + e);
		}
	}
}
