package org.example.xml.validation;

import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

/**
 * Example of simple XML validation against XSD schemas.
 * 
 * @see javax.xml.validation
 * 
 * @author Sven-Jørgen Karlsen <svenjok@gmail.com>
 * 
 */
public final class XmlValidator {

	private XmlValidator() {
	}

	/**
	 * Main driver method for this example.
	 * 
	 * @param args
	 *            an array of N URIs where the N - 1 first are XSD schema URIs,
	 *            and the last element is the URI of an XML input document.
	 * @throws SAXException
	 *             if an error during parsing and validation of XML files occur
	 * @throws IOException
	 *             if there is an IO error retrieving any of the input source
	 *             files.
	 */
	public static void main(String[] args) throws SAXException, IOException {
		int i;
		StreamSource[] schemas = new StreamSource[args.length - 1];
		for (i = 0; i < args.length - 1; i++) {
			schemas[i] = new StreamSource(args[i]);
		}
		SchemaFactory schemaFactory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(schemas);
		Validator validator = schema.newValidator();
		validator.validate(new StreamSource(args[i]));
	}

}
