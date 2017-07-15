package org.evanframework.utils;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

/**
 * xml工具
 * @author shen.wei
 * @version  Date: 2010-9-6 上午11:24:22 
 * @since

 */
//@SuppressWarnings("unchecked")
public class XmlUtils {

	private static Log log = LogFactory.getLog(XmlUtils.class);

	/**
	 * 加载XML文件
	 * 
	 * @param xmlFilePath
	 *            -- 被加载的xml文件的路径，该路径以classpath开始的相对路径
	 *            如xml文件放在classpath根目录下，则getDocument(xmlFileName)
	 *            如xml文件放在classpath/sysconfig/目录下，则getDocument("sysconfig" + / +
	 *            xmlFileName)
	 *  Document
	 */
	public static Document getDocument(String xmlFilePath) {
		SAXReader reader = new SAXReader();

		// URL url = Thread.currentThread().getContextClassLoader().getResource(
		// xmlFilePath);
		// File file = new File(url.getFile());

		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(xmlFilePath);

		Document document = null;

		try {
			document = reader.read(is);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e);
			}
			throw new IllegalStateException(e);
		}

		return document;
	}
}
