package org.example.servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;

public class JettyServer {

	public static void main(String[] args) throws Exception {
		int port = 8080;
		if (args.length == 1) {
			port = Integer.valueOf(args[0]);
		}
		Server server = new Server(port);
		var handler = new ServletContextHandler();
		handler.setContextPath("/metrics");
		handler.addServlet(HealthServlet.class, "/healthz");
		server.setHandler(handler);
		server.start();
		// server.join();
	}
}
