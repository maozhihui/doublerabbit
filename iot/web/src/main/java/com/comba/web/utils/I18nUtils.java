package com.comba.web.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class I18nUtils  {
	public static final String LANG_EN_US = "EN-US";
	public static final String LANG_ZH_CN = "ZH-CN";
	private static Logger logger = Logger.getLogger(I18nUtils.class);
	private static Map<String, Properties> map = new HashMap<String, Properties>();
	private final static String resourceFilePrefix = "MessageResources";
	private static boolean init = false;
	private static final I18nUtils entity;
	
	static {
		entity = new I18nUtils();
	}
	
	public static I18nUtils getInstance() {
		return entity;
	}
	
	public static void reload() {
		init = false;
		loadResource();
	}
	
	public static void init() {
		loadResource();
	}
	
	private synchronized static void loadResource() {
		if (init) {
			return;
		}
		if (map.size() > 0) {
			map.clear();
		}
		InputStream resource = null;
		for (String lang : new String[] { LANG_EN_US, LANG_ZH_CN }) {
			final String key = getKey(lang);
			final Properties prop = new Properties();
			try {
				resource = new FileInputStream(new File(Thread.currentThread()
						.getContextClassLoader().getResource(
								key + ".properties").toURI().getPath()));
				if (resource != null) {
					prop.load(resource);
					map.put(key, prop);
				}
			} catch (Exception e) {
				logger.error("load Locale resource file failed, " + key
						+ ".xml");
			} finally {
				try {
					resource.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		init = true;
	}

	public static String getText(final String key, final String language) {
		if (StringUtils.isBlank(key)) {
			return key;
		}
		if (!init) {
			loadResource();
		}
		final Properties prop = map.get(getKey(language));
		return (prop != null && prop.get(key) != null) ? prop.getProperty(key)
				: key;
	}
	
	public static String getI18nText(final String key) {
		final String lang = CommonProperties.localeLanguage
				.equals(CommonProperties.localeLanguage_enus) ? LANG_EN_US
				: LANG_ZH_CN;
		final String i18nText = getText(key, lang);
		if (StringUtils.isNotBlank(i18nText) && i18nText.indexOf(".")!=-1
				&& key.equals(i18nText)) {
			logger.warn("Not found a label [" + key + "] in locale resources.");
		}
		return i18nText;
	}
	
	public static String getI18nText(final String key, String... items) {
		String i18nText = getI18nText(key);
		for (int i=0; i<items.length; i++) {
			i18nText = i18nText.replaceAll("\\{"+(i+1)+"\\}", items[i]);
		}
		return i18nText;
	}

	private static String getKey(final String language) {
		return resourceFilePrefix + "." + language;
	}
}