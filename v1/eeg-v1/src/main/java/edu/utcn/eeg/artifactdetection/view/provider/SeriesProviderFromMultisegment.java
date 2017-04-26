package edu.utcn.eeg.artifactdetection.view.provider;

import java.util.ArrayList;
import java.util.List;

import edu.utcn.eeg.artifactdetection.model.MultiChannelSegment;
import edu.utcn.eeg.artifactdetection.model.Segment;
import edu.utcn.eeg.artifactdetection.view.chart.SeriesHolder;

public class SeriesProviderFromMultisegment {
	
	public List<SeriesHolder> obtainSearise( MultiChannelSegment segment){
		List<Segment> segments=segment.getSegments();
		List<SeriesHolder> series=new ArrayList<SeriesHolder>();
		for (Segment s :segments){
			double[] values=s.getValues();
			SeriesHolder seriesHolder=new SeriesHolder();
			seriesHolder.setSeries(values);
			series.add(seriesHolder);
		}
		return series;
	}

}
