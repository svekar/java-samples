package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.OutputStreamAppender;

public class ClassUnderTestTest {

	static {
		assertEquals(Charset.defaultCharset(), Charset.forName("UTF-8"));
	}

	@Test
	public final void testMethod() throws UnsupportedEncodingException {
		ByteArrayOutputStream baos = logToInMemoryStream();
		new ClassUnderTest().method();
		assertEquals(String.format("I am a beast.%n"), baos.toString());
	}

	/**
	 * Reconfigure Logback to log to an in-memory output stream.
	 * 
	 * This method wipes out all existing Logback configuration (from files or
	 * actions of the default BasicConfigurator).
	 * 
	 * @return the in-memory stream used with an output stream appender set up
	 *         in Logback.
	 */
	private static ByteArrayOutputStream logToInMemoryStream() {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		Logger rl = lc.getLogger(Logger.ROOT_LOGGER_NAME);
		rl.detachAndStopAllAppenders();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamAppender<ILoggingEvent> ca =
				createOutputStreamAppender(lc, baos);
		rl.addAppender(ca);
		return baos;
	}

	private static OutputStreamAppender<ILoggingEvent> createOutputStreamAppender(
		LoggerContext lc, ByteArrayOutputStream baos) {
		OutputStreamAppender<ILoggingEvent> ca =
				new OutputStreamAppender<>();
		ca.setContext(lc);
		ca.setName("stream");
		PatternLayoutEncoder pl = createEncoder(lc);
		ca.setEncoder(pl);
		ca.setOutputStream(baos);
		ca.start();
		return ca;
	}

	private static PatternLayoutEncoder createEncoder(LoggerContext lc) {
		PatternLayoutEncoder encoder = new PatternLayoutEncoder();
		encoder.setContext(lc);
		encoder.setPattern("%msg%n");
		encoder.start();
		return encoder;
	}

}
