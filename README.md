# gradle-guice-jersey-jetty

Template project with the following technologies:

1. Gradle as build tool
2. Guice for DI
3. Jersey for the RESTful API
4. Jackson for POJO -> JSON mapping
5. Jetty as embedded web server
6. JDBI for access to relational DBs

## How to run

From the commandline, run the following:
```
./gradlew run
```

This will startup the server in the default port `8080`