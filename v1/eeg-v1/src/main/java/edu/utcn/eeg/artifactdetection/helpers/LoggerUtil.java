package edu.utcn.eeg.artifactdetection.helpers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import edu.utcn.eeg.artifactdetection.model.Configuration;

public class LoggerUtil {

	public static void configure() throws Exception {
		Properties props = new Properties();
		props.load(LoggerUtil.class.getResourceAsStream("/log4j.properties"));
		props.put("log4j.appender.file.file", Configuration.RESULTS_PATH + "/logs/"+logFileName());
		PropertyConfigurator.configure(props);		
	}

	public static Logger logger(Class<?> class1) {
		return Logger.getLogger(class1.getSimpleName());
	}
	
	private static String logFileName()
	{
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_hh-mm")) + ".txt";
	}

}
