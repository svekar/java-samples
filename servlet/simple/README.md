# Minimalistic example of a HTTP Servlet

This project contains simple HTTP servlet capable of answering health check 
requests (HTTP GET) similar to those used in Docker and Kubernetes. This use case was 
chosen since it is slightly more interesting than "Hello World". It is coded 
against the Jakarta 6.0 Servlet APIs, but should be easy to adjust dependencies 
down to 3.x of the standard, even older versions since the Servlet 3.x annotations
aren't effectively used for deployment, just present as documentation and hint for 
future improvement.

I looked briefly at how you could configure Jetty to support Servlet 3.x 
annotations from a non-WAR deployment (this project is packaged as a JAR,
for direct deployment in an embedded servlet engine), but it was rather involved
and did not look worthwhile for this simple example.

The `src/test/java` folder contains a couple of examples of how to run the 
servlet in an embedded Jetty or Tomcat container.

## Building and running
1. `$ mvn verify`
2. Launch one of the embedded servlet engine runners, `JettyServer` or `TomcatServer`
3. Hit http://localhost:8080/metrics/healthz in a web browser or curl.

The result should be HTTP status 200 and a string payload of `OK`. 




 
