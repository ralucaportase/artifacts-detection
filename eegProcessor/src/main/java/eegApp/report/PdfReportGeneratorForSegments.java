package eegApp.report;

import java.awt.Graphics2D;


import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.DefaultFontMapper;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import eegApp.model.ArtefactType;
import eegApp.model.EEGSegment;
import eegApp.report.model.ReportElement;

/**
 * This class generates an report with all the segments from a sample and the
 * artefacts detected on them.
 * 
 * @author Tolas Ramona
 *
 */
public class PdfReportGeneratorForSegments {

	private static final String NAME_X_AXIS = "Time";
	private static final String NAME_Y_AXIS = "EEG value";
	private static final int WIDTH_OF_ELEMENT = (int) Math.round(PageSize.A4.getWidth());
	private static final int HEIGHT_OF_ELEMENT = (int) Math.round(PageSize.A4.getHeight() / 2);

	public void genereatePDFReportWithSegmentsForSample(List<EEGSegment> segments, String nameOfFileGenerated) {

		writeChartToPDF(obtainReportElementsFromSegments(segments), WIDTH_OF_ELEMENT, HEIGHT_OF_ELEMENT,
				nameOfFileGenerated);
	}

	public JFreeChart generateChart(EEGSegment segment) {
		DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
		List<Double> values = segment.getValues();
		int i = 1;
		for (Double value : values) {
			line_chart_dataset.addValue(value, "values", "" + i);
			i++;
		}

		String title = "Segment " + segment.getId() + ": ch_id= " + segment.getChannelId() + " sbj_id="
				+ segment.getSubjectId() + " trialNr=" + segment.getNrTrialFromSample();

		JFreeChart lineChartObject = ChartFactory.createLineChart(title, NAME_X_AXIS, NAME_Y_AXIS, line_chart_dataset,
				PlotOrientation.VERTICAL, true, true, false);

		return lineChartObject;
	}

	public static void writeChartToPDF(List<ReportElement> elements, int width, int height, String fileName) {
		PdfWriter writer = null;

		Document document = new Document();

		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
			document.open();
			PdfContentByte contentByte = writer.getDirectContent();
			int i = 0;

			for (ReportElement element : elements) {

				JFreeChart chart = element.getChart();
				ArtefactType artefactType = element.getArtefactType();
				chart.setTitle(chart.getTitle().getText() + "---" + artefactType);
				PdfTemplate template = contentByte.createTemplate(width, height);
				Graphics2D graphics2d = template.createGraphics(width, height, new DefaultFontMapper());
				Rectangle2D rectangle2d = new Rectangle2D.Double(i, 0, width, height);
				chart.draw(graphics2d, rectangle2d);
				graphics2d.dispose();
				contentByte.addTemplate(template, 0, 0);
				document.newPage();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		document.close();
	}

	private List<ReportElement> obtainReportElementsFromSegments(List<EEGSegment> segments) {
		List<ReportElement> elementsToDraw = new ArrayList<ReportElement>();

		for (EEGSegment segment : segments) {
			JFreeChart chart = generateChart(segment);
			ArtefactType artefactTYpe = segment.getArtefactType();
			ReportElement element = new ReportElement();
			element.setArtefactType(artefactTYpe);
			element.setChart(chart);
			elementsToDraw.add(element);
		}

		return elementsToDraw;
	}

}
