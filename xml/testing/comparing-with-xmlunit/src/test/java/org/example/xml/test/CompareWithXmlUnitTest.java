package org.example.xml.test;

import static org.custommonkey.xmlunit.XMLAssert.*;

import org.custommonkey.xmlunit.*;
import org.custommonkey.xmlunit.examples.RecursiveElementNameAndTextQualifier;
import org.junit.Test;

/**
 * Unit test demonstrating some common use cases of XML comparisons with
 * XMLUnit.
 * 
 * @author Sven-Jørgen Karlsen
 *
 */
public final class CompareWithXmlUnitTest {

	// @formatter:off
	private static final String EXPECTATION = "" 
			+ "<x>"
			+ " <y>1</y>"
			+ " <y>2</y>"
			+ " <y>final</y>"
			+ "</x>";
	// @formatter:on

	/**
	 * Literal comparison of identical XML instances.
	 * 
	 * @throws Throwable
	 */
	@Test
	public void compareIdenticalXml() throws Throwable {
		// @formatter:off
		final String expectation =	"" 
				+ "<x>"
				+ " <elt>some value</elt>"
				+ "</x>";
		// @formatter:on
		compareXmlInstances(new Diff(expectation, expectation), true, true);
	}

	private static void compareXmlInstances(Diff diff, boolean isEqual,
		boolean isIdentical) {
		assertXMLEqual(diff, isEqual);
		assertXMLIdentical(diff, isIdentical);
	}

	/**
	 * Compare two similar XML instances.
	 * 
	 * Here <em>similar</em> is identical when disregarding the order of
	 * attributes and elements. This is normally what you want, when the
	 * instances are created with different XML libraries and JVM versions,
	 * which often introduce this kind of variability.
	 * 
	 * @throws Throwable
	 */
	@Test
	public void compareSimilarXml() throws Throwable {
		// @formatter:off
		final String result = ""
				+ "<x>"
				+ " <y>2</y>"
				+ " <y>1</y>"
				+ " <y>final</y>"
				+ "</x>";
		// @formatter:on
		Diff diff = new Diff(EXPECTATION, result);
		diff.overrideElementQualifier(
				new RecursiveElementNameAndTextQualifier());
		compareXmlInstances(diff, true, false);
	}

	/**
	 * Same as {@link #compareSimilarXml()}, but disregarding whitespace.
	 * 
	 * @throws Throwable
	 */
	@Test
	public void compareSimilarXmlIgnoringWhitespace() throws Throwable {
		// @formatter:off
		final String result = ""
				+ "<x>  " 
				+ "<y>2</y>" 
				+ " <y>1</y>  "
				+ "   <y>final</y>" 
				+ "</x>";
		// @formatter:on
		XMLUnit.setIgnoreWhitespace(true);
		Diff diff = new Diff(EXPECTATION, result);
		diff.overrideElementQualifier(
				new RecursiveElementNameAndTextQualifier());
		compareXmlInstances(diff, true, false);		
	}

}
