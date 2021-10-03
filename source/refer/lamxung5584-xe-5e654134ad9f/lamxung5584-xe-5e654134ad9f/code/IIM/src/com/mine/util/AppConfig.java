package com.mine.util;

import com.google.common.base.Splitter;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppConfig {
	private static AppConfig instance;
    protected static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

	private Multimap<String, String> partitions;
	private List<String> departments;
	
	private Properties props;
	
	private AppConfig() {
		props = new Properties();
		partitions = ArrayListMultimap.create();
		departments = new ArrayList<>();
	}

	public static synchronized AppConfig getInstance() {
		if (instance == null) {
			instance = new AppConfig();
			try {
				instance.loadConfiguration();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		return instance;
	}
	
	public void loadConfiguration() throws IOException {
		InputStream is = null;
		try {
//			fi = new InputStream(resource);
			is = AppConfig.class.getResourceAsStream("/config.properties");
			props.load(is);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
                        logger.error(e.getMessage(), e);
                        }
		}
	}
	
	public String getProperty(String key) {
		String val = props.getProperty(key);
		try {
			return new String(val.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
                    logger.error(e.getMessage(), e);
			return val;
		}
	}

	public void init() {
		String dir = AppConfig.getInstance().getProperty("com.viettel.cus.dir");

		partitions = ArrayListMultimap.create();
		List<String> branchs = Splitter.on(";").trimResults().omitEmptyStrings().splitToList(dir);
		for (String branch : branchs) {
			String part = branch.substring(branch.indexOf('{') + 1, branch.indexOf('}'));
			String sys = branch.substring(0, branch.indexOf('{'));

			List<String> parts = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(part);

			for (String s : parts) {
				partitions.put(sys, s);
			}
		}

/*		String depts = AppConfig.getInstance().getProperty("com.viettel.backupcode.dept");
		departments = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(depts);*/
	}

	public Multimap<String, String> getPartitions() {
		return partitions;
	}

/*	public List<String> getDepartments() {
		return departments;
	}*/
}

