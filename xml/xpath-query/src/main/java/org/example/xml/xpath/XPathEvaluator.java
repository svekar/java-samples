package org.example.xml.xpath;

import java.io.FileInputStream;
import java.io.StringWriter;
import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 * Simple example of evaluating an XPath expression against an XML file.
 * 
 * @author Sven-Jørgen Karlsen <svenjok@gmail.com>
 * 
 */
public final class XPathEvaluator {

	/**
	 * Simple namespace context for the Maven 4.0.0 schema. It binds the prefix
	 * "p" to Maven's top-level schema URI.
	 * 
	 */
	public static enum PomNamespaceContext implements NamespaceContext {
		INSTANCE;

		public String getNamespaceURI(String prefix) {
			if (prefix == null) {
				throw new IllegalArgumentException("Prefix cannot be null.");
			} else if ("p".equals(prefix)) {
				return "http://maven.apache.org/POM/4.0.0";
			}
			return XMLConstants.NULL_NS_URI;
		}

		// Not necessary yet.
		public String getPrefix(String namespaceURI) {
			return null;
		}
		
		// Not necessary yet.
		@SuppressWarnings("rawtypes")
		public Iterator getPrefixes(String namespaceURI) {
			return null;
		}

	}

	/**
	 * Evaluate an XPath expression against an XML file.
	 * 
	 * @param args
	 *            an array of two elements:
	 *            <ul>
	 *            <li>XML input file</li>
	 *            <li>an XPath expression to evaluate.</li>
	 *            </ul
	 */
	public static void main(String[] args) throws Throwable {
		XPath xpath = XPathFactory.newInstance().newXPath();
		InputSource source = new InputSource(new FileInputStream(args[0]));
		xpath.setNamespaceContext(PomNamespaceContext.INSTANCE);
		Node result = (Node) xpath.evaluate(args[1], source,
				XPathConstants.NODE);
		System.out.println(String.format(
				"Evaluatig '%s' against the file %s produced: %s", args[1],
				args[0], printDomNode(result)));
	}

	/**
	 * Format a DOM node as an XML string.
	 * 
	 * @param node
	 *            DOM node to format.
	 * @return the XML representation of <code>node</code> as a string.
	 */
	private static String printDomNode(Node node) {
		Transformer transformer;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
		} catch (TransformerConfigurationException e) {
			throw new IllegalStateException(e);
		}
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		StringWriter stringWriter = new StringWriter();
		StreamResult output = new StreamResult(stringWriter);
		try {
			transformer.transform(new DOMSource(node), output);
		} catch (TransformerException e) {
			throw new IllegalStateException(e);
		}
		return output.getWriter().toString();

	}
}
