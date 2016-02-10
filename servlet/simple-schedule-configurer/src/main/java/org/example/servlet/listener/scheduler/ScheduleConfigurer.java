package org.example.servlet.listener.scheduler;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ScheduleConfigurer implements ServletContextListener {

	public static final String TASK_CLASS_PARAMETER = "ScheduleConfigurer.Task";
	public static final String INTERVAL_PARAMETER =
			"ScheduleConfigurer.interval";

	private final Timer timer;

	public ScheduleConfigurer() {
		timer = new Timer(this.getClass().getSimpleName(), true);
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String taskClassName =
				sce.getServletContext().getInitParameter(TASK_CLASS_PARAMETER);
		long interval = Long.valueOf(
				sce.getServletContext().getInitParameter(INTERVAL_PARAMETER));
		TimerTask timerTask = createTimerTask(taskClassName);
		timer.scheduleAtFixedRate(timerTask, 0, interval);
	}

	private static TimerTask createTimerTask(String taskClassName) {
		try {
			@SuppressWarnings("unchecked")
			Class<? extends TimerTask> taskClass =
					(Class<? extends TimerTask>) Class.forName(taskClassName);
			return taskClass.newInstance();
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			throw new IllegalStateException(
					"Can't load or create timer task class: " + taskClassName
							+ ".",
					e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		timer.cancel();
	}

}
