package com.mesoft.bitibox;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {

	static Properties configProp = null;

	final static Logger logger = LoggerFactory.getLogger(Util.class);

	public void setConfig(String key, String value) {

		try {
			if (configProp == null) {
				initConfig();
			}

			configProp.setProperty(key, value);

			String storePropFile = this.getClass().getClassLoader()
					.getResource("config.properties").getPath();

			configProp.store((new FileOutputStream(storePropFile)), null);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error while reading file", e);
		}
	}

	public String getConfig(String param) {

		String result = "";

		if (configProp == null) {

			try {
				initConfig();

				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("error while reading file", e);
			}
		}
		result = configProp.getProperty(param);

		return result;
	}

	private void initConfig() throws IOException {
		configProp = new Properties();

		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("config.properties");

		configProp.load(in);
	}

}
