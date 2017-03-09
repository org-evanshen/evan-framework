package com.ancun.core.utils.html.format;

import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.cyberneko.html.filters.DefaultFilter;

/**
 * 
 * @author fish
 * 
 */
public class ElementRemover extends DefaultFilter {

	//
	// Constants
	//

	/** A "null" object. */
	protected static final Object NULL = new Object();

	//
	// Data
	//

	// information

	// ������Ҫ֧�ֵ�������ʽ
	protected Map<String, Map<String, Pattern>> fAttributePattern = new Hashtable<String, Map<String, Pattern>>();

	/** Accepted elements. */
	protected Hashtable fAcceptedElements = new Hashtable();

	/** Removed elements. */
	protected Hashtable fRemovedElements = new Hashtable();

	// state

	/** The element depth. */
	protected int fElementDepth;

	/** The element depth at element removal. */
	protected int fRemovalElementDepth;

	//
	// Public methods
	//

	/**
	 * 
	 * @param element
	 *            - Ԫ�����
	 * @param attribute
	 *            - �������
	 * @param pattern
	 *            - ������Ҫ�����������ʽ
	 */
	public void acceptElementAttribute(String element, String attribute,
			String pattern) {
		Map<String, Pattern> map = fAttributePattern.get(element);
		if (map == null) {
			map = new Hashtable<String, Pattern>();
			fAttributePattern.put(element, map);
		}

		//
		map.put(attribute, Pattern.compile(pattern));
	}

	/**
	 * Specifies that the given element should be accepted and, optionally,
	 * which attributes of that element should be kept.
	 * 
	 * @param element
	 *            The element to accept.
	 * @param attributes
	 *            The list of attributes to be kept or null if no attributes
	 *            should be kept for this element.
	 * 
	 *            see #removeElement
	 */
	public void acceptElement(String element, String[] attributes) {
		Object key = element.toLowerCase();
		Object value = NULL;
		if (attributes != null) {
			String[] newarray = new String[attributes.length];
			for (int i = 0; i < attributes.length; i++) {
				newarray[i] = attributes[i].toLowerCase();
			}
			value = attributes;
		}
		fAcceptedElements.put(key, value);
	} // acceptElement(String,String[])

	/**
	 * Specifies that the given element should be completely removed. If an
	 * element is encountered during processing that is on the remove list, the
	 * element's start and end tags as well as all of content contained within
	 * the element will be removed from the processing stream.
	 * 
	 * @param element
	 *            The element to completely remove.
	 */
	public void removeElement(String element) {
		Object key = element.toLowerCase();
		Object value = NULL;
		fRemovedElements.put(key, value);
	} // removeElement(String)

	//
	// XMLDocumentHandler methods
	//

	// since Xerces-J 2.2.0

	/** Start document. */
	public void startDocument(XMLLocator locator, String encoding,
			NamespaceContext nscontext, Augmentations augs) throws XNIException {
		fElementDepth = 0;
		fRemovalElementDepth = Integer.MAX_VALUE;
		super.startDocument(locator, encoding, nscontext, augs);
	} // startDocument(XMLLocator,String,NamespaceContext,Augmentations)

	// old methods

	/** Start document. */
	public void startDocument(XMLLocator locator, String encoding,
			Augmentations augs) throws XNIException {
		startDocument(locator, encoding, null, augs);
	} // startDocument(XMLLocator,String,Augmentations)

	/** Start prefix mapping. */
	public void startPrefixMapping(String prefix, String uri, Augmentations augs)
			throws XNIException {
		if (fElementDepth <= fRemovalElementDepth) {
			super.startPrefixMapping(prefix, uri, augs);
		}
	} // startPrefixMapping(String,String,Augmentations)

	/** Start element. */
	public void startElement(QName element, XMLAttributes attributes,
			Augmentations augs) throws XNIException {
		if (fElementDepth <= fRemovalElementDepth
				&& handleOpenTag(element, attributes)) {
			super.startElement(element, attributes, augs);
		}
		fElementDepth++;
	} // startElement(QName,XMLAttributes,Augmentations)

	/** Empty element. */
	public void emptyElement(QName element, XMLAttributes attributes,
			Augmentations augs) throws XNIException {
		if (fElementDepth <= fRemovalElementDepth
				&& handleOpenTag(element, attributes)) {
			super.emptyElement(element, attributes, augs);
		}
	} // emptyElement(QName,XMLAttributes,Augmentations)

	/** Comment. */
	public void comment(XMLString text, Augmentations augs) throws XNIException {
		if (fElementDepth <= fRemovalElementDepth) {
			super.comment(text, augs);
		}
	} // comment(XMLString,Augmentations)

	/** Processing instruction. */
	public void processingInstruction(String target, XMLString data,
			Augmentations augs) throws XNIException {
		if (fElementDepth <= fRemovalElementDepth) {
			super.processingInstruction(target, data, augs);
		}
	} // processingInstruction(String,XMLString,Augmentations)

	/** Characters. */
	public void characters(XMLString text, Augmentations augs)
			throws XNIException {
		if (fElementDepth <= fRemovalElementDepth) {
			super.characters(text, augs);
		}
	} // characters(XMLString,Augmentations)

	/** Ignorable whitespace. */
	public void ignorableWhitespace(XMLString text, Augmentations augs)
			throws XNIException {
		if (fElementDepth <= fRemovalElementDepth) {
			super.ignorableWhitespace(text, augs);
		}
	} // ignorableWhitespace(XMLString,Augmentations)

	/** Start general entity. */
	public void startGeneralEntity(String name, XMLResourceIdentifier id,
			String encoding, Augmentations augs) throws XNIException {
		if (fElementDepth <= fRemovalElementDepth) {
			super.startGeneralEntity(name, id, encoding, augs);
		}
	} // startGeneralEntity(String,XMLResourceIdentifier,String,Augmentations)

	/** Text declaration. */
	public void textDecl(String version, String encoding, Augmentations augs)
			throws XNIException {
		if (fElementDepth <= fRemovalElementDepth) {
			super.textDecl(version, encoding, augs);
		}
	} // textDecl(String,String,Augmentations)

	/** End general entity. */
	public void endGeneralEntity(String name, Augmentations augs)
			throws XNIException {
		if (fElementDepth <= fRemovalElementDepth) {
			super.endGeneralEntity(name, augs);
		}
	} // endGeneralEntity(String,Augmentations)

	/** Start CDATA section. */
	public void startCDATA(Augmentations augs) throws XNIException {
		if (fElementDepth <= fRemovalElementDepth) {
			super.startCDATA(augs);
		}
	} // startCDATA(Augmentations)

	/** End CDATA section. */
	public void endCDATA(Augmentations augs) throws XNIException {
		if (fElementDepth <= fRemovalElementDepth) {
			super.endCDATA(augs);
		}
	} // endCDATA(Augmentations)

	/** End element. */
	public void endElement(QName element, Augmentations augs)
			throws XNIException {
		if (fElementDepth <= fRemovalElementDepth
				&& elementAccepted(element.rawname)) {
			super.endElement(element, augs);
		}
		fElementDepth--;
		if (fElementDepth == fRemovalElementDepth) {
			fRemovalElementDepth = Integer.MAX_VALUE;
		}
	} // endElement(QName,Augmentations)

	/** End prefix mapping. */
	public void endPrefixMapping(String prefix, Augmentations augs)
			throws XNIException {
		if (fElementDepth <= fRemovalElementDepth) {
			super.endPrefixMapping(prefix, augs);
		}
	} // endPrefixMapping(String,Augmentations)

	//
	// Protected methods
	//

	/** Returns true if the specified element is accepted. */
	protected boolean elementAccepted(String element) {
		Object key = element.toLowerCase();
		return fAcceptedElements.containsKey(key);
	} // elementAccepted(String):boolean

	/** Returns true if the specified element should be removed. */
	protected boolean elementRemoved(String element) {
		Object key = element.toLowerCase();
		return fRemovedElements.containsKey(key);
	} // elementRemoved(String):boolean

	/** Handles an open tag. */
	protected boolean handleOpenTag(QName element, XMLAttributes attributes) {
		if (elementAccepted(element.rawname)) {
			Object key = element.rawname.toLowerCase();
			Object value = fAcceptedElements.get(key);

			// ���ָ���������Ƿ�����Ҫ��
			Map<String, Pattern> attrPatterns = fAttributePattern.get(key);

			//
			if (value != NULL) {
				String[] anames = (String[]) value;
				int attributeCount = attributes.getLength();
				LOOP: for (int i = 0; i < attributeCount; i++) {
					String aname = attributes.getQName(i).toLowerCase();
					for (int j = 0; j < anames.length; j++) {
						if (anames[j].equals(aname)) {
							// ִ�ж����������ʽ�ļ��
							if (attrPatterns != null) {
								Pattern pattern = attrPatterns.get(aname);
								if (pattern != null) {
									Matcher matcher = pattern
											.matcher(attributes.getValue(i));
									if (matcher.matches()) {
										continue LOOP;
									}
								}
							} else {
								continue LOOP;
							}
						}
					}
					attributes.removeAttributeAt(i--);
					attributeCount--;
				}
			} else {
				attributes.removeAllAttributes();
			}
			return true;
		} else if (elementRemoved(element.rawname)) {
			fRemovalElementDepth = fElementDepth;
		}
		return false;
	} // handleOpenTag(QName,XMLAttributes):boolean

} // class DefaultFilter

