package com.maxbit.assignment.utililty;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.maxbit.assignment.model.Project;
import com.maxbit.assignment.model.UserApplication;

@Component
public class InvoiceDataPdfExport {

	private String pdfDir = new File("target\\reports\\").getAbsolutePath();

	@Value("${reportFileName}")
	private String reportFileName;

	@Value("${reportFileNameDateFormat}")
	private String reportFileNameDateFormat;

	@Value("${localDateFormat}")
	private String localDateFormat;

	@Value("${currencySymbol:}")
	private String currencySymbol;

	@Value("${table_noOfColumns}")
	private int noOfColumns;

	@Value("${table.columnNames}")
	private List<String> columnNames;

	private static Font COURIER = new Font(Font.COURIER, 14, Font.BOLD);
	private static Font COURIER_SMALL = new Font(Font.COURIER, 12, Font.BOLD);
	private static Font COURIER_SMALL_FOOTER = new Font(Font.COURIER, 10, Font.BOLD);

	public void generatePdfReport(List<UserApplication> applications) {

		Document document = new Document();

		try {
			PdfWriter.getInstance(document, new FileOutputStream(getPdfNameWithDate()));
			document.open();
			addDocTitle(document);
			createTable(document, noOfColumns, applications);
			addFooter(document);
			document.close();
			System.out.println("------------------Your PDF Report is ready!-------------------------");

		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addDocTitle(Document document) throws DocumentException {
		String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(localDateFormat));
		Paragraph p1 = new Paragraph();
		leaveEmptyLine(p1, 1);
		p1.add(new Paragraph(reportFileName, COURIER));
		p1.setAlignment(Element.ALIGN_CENTER);
		leaveEmptyLine(p1, 1);
		p1.add(new Paragraph("Report generated on " + localDateString, COURIER_SMALL));

		document.add(p1);

	}

	private void createTable(Document document, int noOfColumns, List<UserApplication> applications)
			throws DocumentException {
		Paragraph paragraph = new Paragraph();
		leaveEmptyLine(paragraph, 3);
		document.add(paragraph);

		PdfPTable table = new PdfPTable(noOfColumns);

		for (int i = 0; i < noOfColumns; i++) {
			PdfPCell cell = new PdfPCell(new Phrase(columnNames.get(i)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(Color.CYAN);
			table.addCell(cell);
		}

		table.setHeaderRows(1);
		getDbData(table, applications);
		document.add(table);
	}

	private void getDbData(PdfPTable table, List<UserApplication> applications) {

		for (UserApplication application : applications) {

			table.setWidthPercentage(100);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

			table.addCell(application.getName());
			table.addCell(application.getEmail());
			table.addCell(application.getGithubUserName());
			List<Project> projects = application.getProjects();
			PdfPCell cell = new PdfPCell();
			PdfPTable nestedTable = new PdfPTable(2);
			PdfPCell cell1 = new PdfPCell(new Phrase("projectName"));
			PdfPCell cell2 = new PdfPCell(new Phrase("projectName"));
			nestedTable.addCell(cell1);
			nestedTable.addCell(cell2);
			for (Project project : projects) {

				nestedTable.addCell(project.getProjectName());
				nestedTable.addCell(project.getRole());
//				nestedTable.addCell(project.getProjectName());
				cell.addElement(nestedTable);

			}

			table.addCell(cell);
		}
	}

	private void addFooter(Document document) throws DocumentException {
		Paragraph p2 = new Paragraph();
		leaveEmptyLine(p2, 3);
		p2.setAlignment(Element.ALIGN_MIDDLE);
		p2.add(new Paragraph("------------------------End Of " + reportFileName + "------------------------",
				COURIER_SMALL_FOOTER));

		document.add(p2);
	}

	private static void leaveEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	private String getPdfNameWithDate() {
		String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(reportFileNameDateFormat));
		return pdfDir + reportFileName + "-" + localDateString + ".pdf";
	}

}