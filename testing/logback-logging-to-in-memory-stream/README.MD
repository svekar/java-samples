# How to reconfigure Logback to log to an in-memory stream

Reconfiguring Logback to be able to capture log output during testing, is sometimes necessary when working 
with batch or legacy applications. However, it turned out to be somewhat complicated, particularly due to 
ordering of assignment steps, compared to the simple setup I use with Log4J. For future reference, I decided 
to make an example of it.

This sample works with Logback 1.1.3 and Junit 4.12.

## Building and running the unit test
```
$ mvn clean package
```
