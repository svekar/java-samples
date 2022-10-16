package org.example.servlet;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.servlet.http.HttpServletResponse;

class JettyServerTest {

	private static int port;
	private final HttpClient httpClient = HttpClient.newHttpClient();

	static {
		try (var ss = new ServerSocket(0)) {
			port = ss.getLocalPort();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@BeforeAll
	static void startServer() throws Exception {
		JettyServer.main(new String[]{Integer.toString(port)});
	}

	@Test
	void getRequestReturns200Ok() throws Exception {
		var req = createHttpRequest("GET");
		HttpResponse<String> response = httpClient.send(req,
				BodyHandlers.ofString());
		assertAll(
				() -> assertEquals(HttpServletResponse.SC_OK,
						response.statusCode()),
				() -> assertEquals(response.body(), String.format("OK%n")));
	}

	@ParameterizedTest
	@ValueSource(strings = {"DELETE", "POST", "PUT"})
	void postPutOrDeleteArentSupported(String method) throws Exception {
		var req = createHttpRequest(method);
		HttpResponse<String> response = httpClient.send(req,
				BodyHandlers.ofString());
		assertEquals(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
				response.statusCode());
	}

	private static HttpRequest createHttpRequest(String method) {
		return HttpRequest.newBuilder()
				.uri(URI.create(String
						.format("http://localhost:%d/metrics/healthz", port)))
				.method(method, BodyPublishers.noBody())
				.build();
	}

}
