package com.ancun.core.utils;

import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;


@SuppressWarnings("rawtypes")
public class Dom4jTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Document doc = XmlUtils.getDocument("function.xml");

		Element root = doc.getRootElement();
		Element element = null;

		for (Iterator i = root.elementIterator(); i.hasNext();) {
			element = (Element) i.next();
			System.out.println(element.getName());
			
			for (Iterator j = element.attributeIterator(); j.hasNext();) {
				Attribute attribute = (Attribute) j.next();
				System.out.println(attribute.getName() + ":" + attribute.getValue());				
			}
		}
	}
}
		


	
