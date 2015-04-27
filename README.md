# Personal Blog

Personal blog wen application built around [GWT](http://www.gwtproject.org) 2.7.0 and deployed on [CloudUnit](http://cloudunit.fr).

Blog access: [http://blog-denisc-anonymous.eu4.cloudunit.io](http://blog-denisc-anonymous.eu4.cloudunit.io)


## Pre-requisites
* Java 8
* Maven 3
* IDE: Eclipse, IntelliJ, etc. (tested with `Intellij IDEA 14.1`)


## Configure persistence
In debug environment, the application expects a configuration file named `persistence.properties` in the classpath.

To do so, execute the following steps:

1. Create this file in your local classpath (most-likely in `src/main/resources` directory).
2. Declare the following required properties inside it:
 * `hibernate.dialect`
 * `hibernate.connection.driver_class`
 * `hibernate.connection.url`
 * `hibernate.username`
 * `hibernate.password`


## Run application

Run following maven command to run the `SuperDevMode`:
```
mvn gwt:run
```

Once embedded jetty server is ready, simply click on `Launch Default Browser` button to load the app.