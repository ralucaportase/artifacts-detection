package edu.utcn.eeg.artifactdetection.model;

/**
 * This enum is used for selecting the region of extraction of segments from eeg
 * signal that contain information from more than one channel.
 * 
 * @author Tolas Ramona
 *
 */
public enum MultiChannelSegmentSelector {
	A, B, C, D, TEMPORAL, PARIETAL, OCCIPITAL, FRONTAL;

}
