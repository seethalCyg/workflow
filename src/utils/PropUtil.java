package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

public class PropUtil {

	private static Properties props = null;
	
	private static URL resourceFilePath = null;

	public PropUtil() {
		super();
		// TODO Auto-generated constructor stub

		try {
			// Loading Properties...
			// ?? Can ONLY LOAD FROM CLASSESS AND ITS SUB-FOLDERS, CANNOT LOAD FROM ROOT FOLDERS.
			// OR ADD the Folder in the Class Path?
			//
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("resources.properties");
			props = new Properties();
			props.load(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getProperty(String key) throws Exception {

		if (props.containsKey(key)) {

			String propValue = props.getProperty(key);
			String proppertyValue = "";

			if (propValue.contains("\\:")) {
				proppertyValue = propValue.replace("\\:", ":");
			} else {
				proppertyValue = propValue;

			}
			
			proppertyValue=proppertyValue.trim();

			return proppertyValue;
		} else {
			System.out.println("Key: " + key + " not found in 'resources.properties' file.");
			return "";
		}
	}

	public void insertProperty(String key, String value) throws Exception {
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
												+ new Exception().getStackTrace()[0].getMethodName());
		props.setProperty(key, value);
		URL fURL = this.getClass().getClassLoader().getResource("resources.properties");
		System.out.println("resources.properties fURL.getFile()." + fURL.getFile());
		String filePathwthDecoder = URLDecoder.decode(fURL.getFile(), "UTF-8");
		System.out.println("resources.properties filePathwthDecoder." +filePathwthDecoder);
		OutputStream os1 = new FileOutputStream((new File(filePathwthDecoder)));
		props.store(os1, "Test");
		os1.close();
		System.out.println("Key: " + key + " is updated as" + value + "  in 'resources.properties' file.");

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
												+ new Exception().getStackTrace()[0].getMethodName());
	}
	

}
