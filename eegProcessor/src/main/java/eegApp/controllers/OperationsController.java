package eegApp.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eegApp.configurations.BeansConfiguration;
import eegApp.helpers.BynaryFileToObjectEegSampleTransformer;
import eegApp.helpers.EEGSegmentsGeneratorFromSample;
import eegApp.model.EEGSample;
import eegApp.model.EEGSegment;
import eegApp.model.EEGTrial;
import eegApp.report.PdfReportGeneratorForSegments;
import eegApp.service.EEGSegmentService;

@RestController
@RequestMapping("/operations")
public class OperationsController {

	private static final Logger LOGGER = Logger.getLogger(OperationsController.class);

	private static final String PATH_TO_BYNARY_FILE = "src\\main\\resources\\Data\\Dots_30_001\\Dots_30_001-Ch001.bin";
	private static final String PATH_TO_EVENT_CODE = "src\\main\\resources\\Data\\Dots_30_001\\Dots_30_001-Event-Codes.bin";
	private static final String PATH_TO_EVENT_TIMESTAMP = "src\\main\\resources\\Data\\Dots_30_001\\Dots_30_001-Event-Timestamps.bin";

	@Autowired
	private EEGSegmentService service;

	@RequestMapping(method = RequestMethod.POST)
	public List<EEGSegment> populateDBWithUnlabeledSegments(@RequestParam(value = "step") int step,
			@RequestParam(value = "lengthOfSegment") int lengthOfSegment) {
		EEGSample sample = this.eegSample();
		List<EEGSegment> result = new ArrayList<EEGSegment>();
		EEGSegmentsGeneratorFromSample segmentsGenerator = new EEGSegmentsGeneratorFromSample(step, lengthOfSegment);
		List<EEGSegment> segments = segmentsGenerator.generateSegments(sample);
		for (EEGSegment segment : segments) {
			result.add(service.save(segment));
		}

		return result;
	}

	@RequestMapping(method = RequestMethod.GET)
	public void generatePDFWithSegmentsFromDB(
			@RequestParam(value = " nameOfFileGenerated") String nameOfFileGenerated) {
		PdfReportGeneratorForSegments reportGenerator = new PdfReportGeneratorForSegments();
		List<EEGSegment> segmentsFromDb = service.findAll();
		reportGenerator.genereatePDFReportWithSegmentsForSample(segmentsFromDb, nameOfFileGenerated);
	}

	/**
	 * This method makes an EEGSample object from the binary files.
	 */
	private EEGSample eegSample() {
		BynaryFileToObjectEegSampleTransformer trsf = new BynaryFileToObjectEegSampleTransformer(PATH_TO_BYNARY_FILE,
				PATH_TO_EVENT_CODE, PATH_TO_EVENT_TIMESTAMP);
		try {
			// return trsf.transformToEEGSample();

			EEGTrial trial1 = new EEGTrial();
			trial1.setValues(Arrays.asList(0.5, 1.2, -0.75, -1.5, 0.25, 2.01, 0.30));
			trial1.setEventCode(1);
			EEGTrial trial2 = new EEGTrial();
			trial2.setValues(Arrays.asList(-0.3, -1.2, -1.5, 0.7, 1.25, 1.90, 2.5));
			trial2.setEventCode(2);
			List<EEGTrial> trials = Arrays.asList(trial1, trial2);
			EEGSample sample = new EEGSample();
			sample.setTrials(trials);
			sample.setChannelId(1);
			sample.setSubjectId(2);
			return sample;
		} catch (Exception e) {
			LOGGER.debug(e);
			return null;
		}

	}

}
