package com.maxbit.assignment.utililty;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.maxbit.assignment.entity.Project;
import com.maxbit.assignment.entity.UserApplication;

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

	@Value("${project.columnNames}")
	private List<String> projectColumnNames;

	private static Font COURIER = new Font(Font.COURIER, 14, Font.BOLD);
	private static Font COURIER_SMALL = new Font(Font.COURIER, 12, Font.BOLD);
	private static Font COURIER_SMALL_FOOTER = new Font(Font.COURIER, 10, Font.BOLD);

	public ByteArrayInputStream generatePdfReport(List<UserApplication> applications) {

		Document document = new Document(PageSize.A1);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			PdfWriter.getInstance(document, out);
			document.open();
			addDocTitle(document);
			createTable(document, noOfColumns, applications);
			addFooter(document);
			document.close();
			System.out.println("------------------Your PDF Report is ready!-------------------------");

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	public ByteArrayInputStream generateApplicantPdfReport(UserApplication applications) {
		Document document = new Document(PageSize.A1);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			PdfWriter.getInstance(document, out);
			document.open();
			addDocTitle(document);
			createReport(document, applications);
			addFooter(document);
			document.close();
			System.out.println("------------------Your PDF Report is ready!-------------------------");

		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	private void createReport(Document document, UserApplication applications)
			throws DocumentException, IOException {
		Image image = Image.getInstance(new URL("https://avatars.githubusercontent.com/"+applications.getGithubUserName()));
		image.setAbsolutePosition(50, 2200);
		image.scaleAbsolute(100, 100);
		document.add(image);
		Paragraph p1 = new Paragraph();
		leaveEmptyLine(p1, 10);
		document.add(p1);
		Font font = new Font(Font.COURIER, 16, Font.BOLD);
		document.add(new Paragraph("Name : " + applications.getName(),font));
		document.add(new Paragraph("Email : " + applications.getEmail(),font));
		document.add(new Paragraph("Github : " + applications.getGithubUserName(),font));

		document.add(p1);
		PdfPTable projectTable = getProjectTable(applications.getProjects());
		document.add(projectTable);
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
		table.setWidths(new int[] { 50, 50, 50, 200 });
		for (int i = 0; i < noOfColumns; i++) {
			PdfPCell cell = new PdfPCell(new Phrase(columnNames.get(i)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(Color.CYAN);
			cell.setPaddingLeft(0);
			cell.setIndent(0);
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
			PdfPTable nestedTable = getProjectTable(projects);
			cell.addElement(nestedTable);

			table.addCell(cell);
		}
	}

	private PdfPTable getProjectTable(List<Project> projects) {
		PdfPTable nestedTable = new PdfPTable(8);
		createProjectsTable(nestedTable);
		nestedTable.setHeaderRows(1);
		int count = 1;
		for (Project project : projects) {
			nestedTable.addCell("No. " + count++);
			nestedTable.addCell(project.getProjectName());
			nestedTable.addCell(project.getRole());
			nestedTable.addCell(project.getStartYear().toString());
			nestedTable.addCell(project.getTeamSize().toString());
			nestedTable.addCell(project.getType().toString());

			Phrase gitLink = new Phrase("-");
			String gitHubLink = project.getGitHubLink();
			if (gitHubLink == null || "".equals(gitHubLink)) {
				Chunk link = new Chunk("Github Link").setAnchor(gitHubLink);
				gitLink.add(link);
			}

			nestedTable.addCell(gitLink);
			
			Phrase url = new Phrase("-");
			String liveUrl = project.getGitHubLink();
			if (liveUrl == null || "".equals(gitHubLink)) {
				Chunk link = new Chunk("Live URL").setAnchor(liveUrl);
				url.add(link);
			}

			nestedTable.addCell(url);

		}
		return nestedTable;
	}

	private void createProjectsTable(PdfPTable nestedTable) {
		for (String colName : projectColumnNames) {
			nestedTable.setWidthPercentage(100);
			nestedTable.addCell(new PdfPCell(new Phrase(colName)));
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
			paragraph.add(new Paragraph(""));
		}
	}

	private String getPdfNameWithDate() {
		String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(reportFileNameDateFormat));
		return pdfDir + reportFileName + "-" + localDateString + ".pdf";
	}

}