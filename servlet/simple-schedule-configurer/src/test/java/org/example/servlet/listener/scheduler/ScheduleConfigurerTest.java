package org.example.servlet.listener.scheduler;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.isA;

import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public final class ScheduleConfigurerTest {

	static final class TestTask extends TimerTask {

		private static int times = 0;

		@Override
		public void run() {
			synchronized (TimerTask.class) {
				times++;
			}
		}

	}

	private final ScheduleConfigurer instance;
	private final ServletContextEvent defaultServletContextEvent;

	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	public ScheduleConfigurerTest() {
		instance = new ScheduleConfigurer();
		defaultServletContextEvent = createServletContextEvent();
	}

	private static ServletContextEvent createServletContextEvent() {
		return createServletContextEvent(TestTask.class.getName());
	}

	private static ServletContextEvent createServletContextEvent(
		String className) {
		ServletContext sc = mock(ServletContext.class);
		when(sc.getInitParameter(ScheduleConfigurer.TASK_CLASS_PARAMETER))
				.thenReturn(className);
		when(sc.getInitParameter(ScheduleConfigurer.INTERVAL_PARAMETER))
				.thenReturn("100");
		return new ServletContextEvent(sc);
	}

	@After
	public void stopTimer() {
		instance.contextDestroyed(null);
		TestTask.times = 0;
	}

	@Test
	public final void testContextInitialized() throws Throwable {
		instance.contextInitialized(defaultServletContextEvent);
		Thread.sleep(110L);
		assertEquals(2, TestTask.times);
	}

	@Test
	public final void initializeWithoutClass() throws Throwable {
		ServletContextEvent sce = createServletContextEvent("no.such.class");
		thrown.expect(IllegalStateException.class);
		thrown.expectMessage("Can't load or create timer task class");
		thrown.expectCause(isA(ClassNotFoundException.class));
		instance.contextInitialized(sce);
	}

	@Test
	public final void testContextDestroyed() throws Throwable {
		instance.contextInitialized(defaultServletContextEvent);
		Thread.sleep(20L);
		instance.contextDestroyed(null);
		Thread.sleep(110L);
		assertEquals(1, TestTask.times);
	}

}
