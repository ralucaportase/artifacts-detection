package eegApp.helpers;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import eegApp.model.EEGSample;

/**
 * This class reads the data from the binary file and transform it to a
 * EEGSample object.
 * 
 * @author Tolas Ramona
 *
 */
public class BynaryFileToObjectEegSampleTransformer {

	private static final Logger LOGGER = Logger
			.getLogger(BynaryFileToObjectEegSampleTransformer.class);

	private String pathToBynaryFile;
	private String pathToEventCodes;
	private String pathToEventTimestamps;

	public BynaryFileToObjectEegSampleTransformer(String pathToBynaryFile,
			String pathToEventCodes, String pathToEventTimestamps) {
		this.pathToBynaryFile = pathToBynaryFile;
		this.pathToEventCodes = pathToEventCodes;
		this.pathToEventTimestamps = pathToEventTimestamps;
	}

	/**
	 * This method is used for transforming the content of a binary file to
	 * EEGSample object, with some extra information needed for performing the
	 * translation. These informations are event codes and event timestamps
	 * that are also read from a binary file.
	 * 
	 * @throws IOException
	 */
	public EEGSample transformToEEGSample() throws IOException {

		FileToObjectTransformerHelper helper = new FileToObjectTransformerHelper(
				this.getEventCodes(), this.getEventTimestamps(),
				this.getSampleValues());

		return helper.performTransformationToEEGSampleObject();
	}

	/**
	 * This method extracts a list with event codes from the file located at
	 * pathToEventCodes.
	 * 
	 * @throws IOException
	 */
	private List<Integer> getEventCodes() throws IOException {
		List<Integer> codes = new ArrayList<Integer>();
		DataInputStream in = new DataInputStream(new FileInputStream(
				pathToEventCodes));
		try {
			while (true) {
				int code = in.readUnsignedByte();
				codes.add(code);
			}
		} catch (EOFException ignored) {
			LOGGER.info("EOF " + pathToEventCodes);
			LOGGER.info("Event codes tooked from " + pathToEventCodes);
		} finally {
			in.close();
		}

		return codes;
	}

	/**
	 * This method extract all the values from the binary file.
	 * 
	 * @throws IOException
	 */
	private List<Float> getSampleValues() throws IOException {
		List<Float> values = new ArrayList<Float>();
		DataInputStream in = new DataInputStream(new FileInputStream(
				pathToBynaryFile));
		try {
			while (true) {
				float value = in.readFloat();
				values.add(value);
			}
		} catch (EOFException ignored) {
			LOGGER.info("EOF " + pathToBynaryFile);
			LOGGER.info("Values of tension tooked from " + pathToBynaryFile);
		} finally {
			in.close();
		}

		return values;
	}

	/**
	 * This method extracts event timestamps.
	 * 
	 * @throws IOException
	 */
	private List<Integer> getEventTimestamps() throws IOException {
		List<Integer> timestamps = new ArrayList<Integer>();
		DataInputStream in = new DataInputStream(new FileInputStream(
				pathToEventTimestamps));
		try {
			while (true) {
				int timestamp = in.readUnsignedByte();
				timestamps.add(timestamp);
			}
		} catch (EOFException ignored) {
			LOGGER.info("EOF " + pathToEventTimestamps);
			LOGGER.info("Timestamp vector tooked from " + pathToEventTimestamps);
		} finally {
			in.close();
		}
		return timestamps;
	}
}
