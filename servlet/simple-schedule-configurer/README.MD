# Simple, Stateless Scheduling with java.util.Timer in a ServletContextListener

Sometimes the overhead and complexity of a full-blown scheduling framework, 
such as Quartz, aren't needed. It turns out to be quite simple to run your own 
schedule in a web application with the help of java.util.Timer. This project 
demonstrates this setup.

Be aware that this example lack any kind of advanced threading support. The 
timer task will run and periodically execute a configured TimerTask in its own 
background thread, but will not delegate work to other threads. In a realistic 
scenario, you should probably configure your own thread pool and dispatch 
long-running, expensive jobs to it. Otherwise, you will bog down the scheduler 
thread itself, and get poor timeliness of the schedule. 

## Building and running the unit test
```
$ mvn clean package
```
