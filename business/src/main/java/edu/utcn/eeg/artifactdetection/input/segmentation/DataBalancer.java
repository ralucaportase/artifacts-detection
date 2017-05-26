package edu.utcn.eeg.artifactdetection.input.segmentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.utcn.eeg.artifactdetection.model.AbstractSegment;
import edu.utcn.eeg.artifactdetection.model.SegmentRepository;

public class DataBalancer {
	
	
	public List<AbstractSegment> undersample(SegmentRepository clean, SegmentRepository occular, SegmentRepository muscular){
		int szOccular = occular.getSegments().size();
		int szMuscular = muscular.getSegments().size();
		List<AbstractSegment> resultList = new ArrayList<>();
		if(szOccular <szMuscular){
			resultList.addAll(occular.getSegments());
			resultList.addAll(pickRandomly(szOccular, muscular.getSegments()));
			resultList.addAll(pickRandomly(szOccular, clean.getSegments()));
		}else if(szOccular > szMuscular){
			resultList.addAll(muscular.getSegments());
			resultList.addAll(pickRandomly(szMuscular, occular.getSegments()));
			resultList.addAll(pickRandomly(szMuscular, clean.getSegments()));
		}
		else{
			resultList.addAll(muscular.getSegments());
			resultList.addAll(occular.getSegments());
			resultList.addAll(pickRandomly(szMuscular, clean.getSegments()));
		}
		Collections.shuffle(resultList);
		return resultList;
	}
	
	
	private List<AbstractSegment> pickRandomly(int value, List<AbstractSegment> allSegments){
		Collections.shuffle(allSegments);
		return allSegments.subList(0, value);
	}
}
