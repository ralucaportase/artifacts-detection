package edu.utcn.eeg.artifactdetection.preprocessing.exception;

/**
 * This Exception is thrown when reading the binary file from the path specified
 * could not be done, or the folder specified by path does not contain all
 * necessary binary files for reading a multichannel segment.
 * 
 * @author Tolas Ramona
 *
 */
public class FileReadingException extends Exception {

	private static final long serialVersionUID = -7542685331179484341L;
	private FileReadingErrorCode errorCode;

	public FileReadingException(String message, FileReadingErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public FileReadingErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(FileReadingErrorCode errorCode) {
		this.errorCode = errorCode;
	}

}
