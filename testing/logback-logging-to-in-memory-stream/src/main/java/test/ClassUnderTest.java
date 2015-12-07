package test;

import org.slf4j.LoggerFactory;

/**
 * A simple example of a class returning nothing, and just emitting some
 * logging.
 * 
 * @author Sven-Jørgen Karlsen
 *
 */
public final class ClassUnderTest {

	public void method() {
		LoggerFactory.getLogger(ClassUnderTest.class).info("I am a beast.");
	}

}
