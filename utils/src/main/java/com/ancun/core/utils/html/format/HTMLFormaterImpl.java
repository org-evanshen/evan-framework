package com.ancun.core.utils.html.format;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.xerces.xni.parser.XMLDocumentFilter;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.cyberneko.html.HTMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author fish
 * 
 */
public class HTMLFormaterImpl implements HTMLFormater {

	private Set<String> acceptElements;

	private Set<String> removeElements;

	private Set<String> attributeMatchers;

	private String encoding = "UTF-8";

	private ThreadLocal<NekoFormater> local = new ThreadLocal<NekoFormater>();

	public String formatHTML(String origHTML) {
		if (StringUtils.isBlank(origHTML)) {
			return origHTML;
		}
		NekoFormater formater = local.get();
		if (formater == null) {
			formater = new NekoFormater(acceptElements, removeElements,
					attributeMatchers, encoding);
			local.set(formater);
		}
		return formater.filter(origHTML);
	}

	public Set<String> getAcceptElements() {
		return acceptElements;
	}

	public void setAcceptElements(Set<String> acceptElements) {
		this.acceptElements = acceptElements;
	}

	public Set<String> getRemoveElements() {
		return removeElements;
	}

	public void setRemoveElements(Set<String> removeElements) {
		this.removeElements = removeElements;
	}

	public Set<String> getAttributeMatchers() {
		return attributeMatchers;
	}

	public void setAttributeMatchers(Set<String> attributeMatchers) {
		this.attributeMatchers = attributeMatchers;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	private static class NekoFormater {
		//
		private static Logger log = LoggerFactory.getLogger(NekoFormater.class);

		private String encoding;

		// ���?�������󳤶�16k
		private static int MAX_BUFFER_LENGTH = 1024 * 16;

		// ������
		private XMLParserConfiguration parser;

		// ������������
		private ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream(
				MAX_BUFFER_LENGTH);

		/**
		 * ���캯��
		 * 
		 * @param acceptElements
		 * @param removeElements
		 * @param attributeMatchers
		 */
		private NekoFormater(Set<String> acceptElements,
				Set<String> removeElements, Set<String> attributeMatchers,
				String encoding) {
			this.encoding = encoding;
			try {
				// ��ʼ��������
				ElementRemover remover = new ElementRemover();

				// ��ʼ����Ҫ���ܵı�ǩ
				for (String accept : acceptElements) {
					//
					String[] ret = accept.split(",");
					String[] attrs = null;
					if (ret.length > 1) {
						attrs = (String[]) ArrayUtils.subarray(ret, 1,
								ret.length);
					}
					remover.acceptElement(ret[0], attrs);
				}

				// ��ʼ����Ҫ���˵ı�ǩ
				for (String remove : removeElements) {
					remover.removeElement(remove);
				}

				// ��ʼ����������ʽ�����Լ����
				for (String item : attributeMatchers) {
					String[] ret = item.split(",", 3);
					remover.acceptElementAttribute(ret[0], ret[1], ret[2]);
				}

				// ��ʼ��html���ö���
				parser = new HTMLConfiguration();

				// create writer filter
				org.cyberneko.html.filters.Writer writer = new org.cyberneko.html.filters.Writer(
						outputBuffer, "utf-8");

				// setup filter chain
				XMLDocumentFilter[] filters = { remover, writer };

				//
				parser.setProperty(
						"http://cyberneko.org/html/properties/names/elems",
						"lower");
				parser
						.setProperty(
								"http://cyberneko.org/html/properties/filters",
								filters);

				//
				parser
						.setFeature(
								"http://apache.org/xml/features/scanner/notify-builtin-refs",
								true);
				parser
						.setFeature(
								"http://cyberneko.org/html/features/scanner/notify-builtin-refs",
								true);
				parser
						.setFeature(
								"http://cyberneko.org/html/features/scanner/ignore-specified-charset",
								true);

				//
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		/**
		 * ����һ��ԭʼ��HTML
		 * 
		 * @param origHtml
		 *
		 */
		private String filter(String origHtml) {
			try {
				// ������������
				outputBuffer.reset();
				// �ǳ���֣��ַ��� &* ��β��ʱ�򣬽��������
				origHtml = origHtml + ' ';
				XMLInputSource is = new XMLInputSource(null, null, null,
						new StringReader(origHtml), encoding);
				parser.parse(is);
				String back = new String(outputBuffer.toByteArray(), encoding);
				back = back.trim();
				return back;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

}
