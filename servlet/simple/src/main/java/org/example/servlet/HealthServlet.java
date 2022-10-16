package org.example.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Simple Servlet simulating a Kubernetes/Docker like health check.
 *
 * <p>
 * Responds with 200 OK on a HTTP GET query. Also responds with default servlet
 * container handling on TRACE, HEAD and OPTIONS. Otherwise the response is 405
 * - Method Not Supported.
 */
@WebServlet(description = "Simple health check servlet sample.", urlPatterns = {
"/healtz"})
public final class HealthServlet extends HttpServlet {

	private static final long serialVersionUID = -8312534078866299311L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.getWriter().println("OK");
	}

}
