# Personal Blog

Personal blog wen application built around [GWT](http://www.gwtproject.org) 2.7.0 and deployed on [CloudUnit](http://cloudunit.fr).

Blog access: [http://blog-denisc-anonymous.eu4.cloudunit.io](http://blog-denisc-anonymous.eu4.cloudunit.io)


## Pre-requisites
* Java 8
* Maven 3
* IDE: Eclipse, IntelliJ, etc. (tested with `Intellij IDEA 14.1`)


## Configure persistence
In debug environment, the application expects a configuration file named `persistence.properties` in the classpath.  
This file is already ignored by git configuration.

#### Required properties

To fulfill this expectation, execute the following steps:
 1. Create the `persistence.properties` file in the project classpath (most-likely in `src/main/resources` directory).
 2. Declare the following *required* properties inside it:
  * `hibernate.dialect`
  * `hibernate.connection.driver_class`
  * `hibernate.connection.url`
  * `hibernate.connection.username`
  * `hibernate.connection.password`

*Example `persistence.properties` file for `PostgreSQL 9.x` database:*
```
hibernate.dialect=org.hibernate.dialect.PostgreSQL9Dialect
hibernate.connection.driver_class=org.postgresql.Driver
hibernate.connection.url=jdbc:postgresql://localhost:5432/<dbname>
hibernate.connection.username=<username>
hibernate.connection.password=<password>
```

#### Optional properties

You may also want to override one or several *optional* properties:
 * `hibernate.archive.autodetection` (default value set to `class`)
 * `hibernate.hbm2ddl.auto` (default value set to ``)
 * `hibernate.show_sql` (default value set to `true`)
 * `hibernate.format_sql` (default value set to `true`) 
 * `hibernate.c3p0.min_size` (default value set to `1`)
 * `hibernate.c3p0.max_size` (default value set to `15`)
 * `hibernate.c3p0.max_statements` (default value set to `100`)
 * `hibernate.c3p0.timeout` (default value set to `0`)
 * `hibernate.c3p0.acquire_increment` (default value set to `1`)
 * `hibernate.c3p0.numHelperThreads` (default value set to `6`)


## Run application

Run following maven command to run the `SuperDevMode`:
```
mvn gwt:run
```

Once embedded jetty server is ready, simply click on `Launch Default Browser` button to load the app.
