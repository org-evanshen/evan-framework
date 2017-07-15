package org.evanframework.utils;

import org.dom4j.Document;
import org.junit.Test;


public class XmlUtilsTest {

	@Test
	public void testGetDocument() {	
		String xmlFilePath = "function.xml";
		Document doc = XmlUtils.getDocument(xmlFilePath);	
		doc.nodeCount();
	}
}
