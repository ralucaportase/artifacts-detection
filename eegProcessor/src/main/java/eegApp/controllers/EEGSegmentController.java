package eegApp.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import eegApp.model.ArtefactType;
import eegApp.model.EEGSegment;
import eegApp.service.EEGSegmentService;

@RestController
@RequestMapping("/segments")
public class EEGSegmentController {

	@Autowired
	private EEGSegmentService service;

	@RequestMapping(method = RequestMethod.GET)
	public List<EEGSegment> findAllSegments() {
		return service.findAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public EEGSegment addArtefactToTrialFromSample(@RequestParam(value = "id") String segmentId,
			@RequestParam(value = "type") ArtefactType artefactType) {

		EEGSegment segment = service.findById(segmentId);
		if (segment != null) {
			segment.setArtefactType(artefactType);
			return service.save(segment);

		}
		return null;

	}

}
