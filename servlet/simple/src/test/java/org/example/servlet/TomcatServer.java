package org.example.servlet;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

public class TomcatServer {

	private static final String SERVLET_NAME = HealthServlet.class
			.getSimpleName();

	public static void main(String[] args) throws Exception {
		int port = 8080;
		if (args.length == 1) {
			port = Integer.valueOf(args[0]);
		}
		Tomcat server = new Tomcat();
		server.setPort(port);
		server.getConnector();
		Context context = server.addContext("/metrics", null);
		Tomcat.addServlet(context, SERVLET_NAME, new HealthServlet());
		context.addServletMappingDecoded("/healthz", SERVLET_NAME);
		server.start();
	}

}
