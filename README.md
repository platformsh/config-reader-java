# Platform.sh Config Reader (Java)

[![CircleCI Status](https://circleci.com/gh/platformsh/config-reader-java.svg?style=shield&circle-token=:circle-token)](https://circleci.com/gh/platformsh/config-reader-java)

This library provides a streamlined and easy to use way to interact with a Platform.sh environment. It offers utility methods to access routes and relationships more cleanly than reading the raw environment variables yourself.

This library requires Java 8 or later.

## Install


### Maven 

```xml
<dependency>
    <groupId>sh.platform</groupId>
    <artifactId>config</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### Gradle 

```xml
compile group: 'sh.platform', name: 'config', version: '0.0.1-SNAPSHOT'

```


## API Reference

### Create a config object

```java
import Config;

Config config = new Config();
```

`config` is now a `Config` object that provides access to the Platform.sh environment.


### Read environment variables

The following magic properties return the corresponding environment variable value.  See the [Platform.sh documentation](https://docs.platform.sh/development/variables.html) for a description of each.

The following are available both in Build and at Runtime:

```java
config.getApplicationName();

config.getAppDir();

config.getproject();

config.getTreeID();

config.getProjectEntropy();
```

The following are available only if `in_runtime()` returned `True`:

```java
config.getBranch();

condig.getDocumentRoot();

config.getSmtpHost();

config.getEnvironment();

```

### Reading service credentials

[Platform.sh services](https://docs.platform.sh/configuration/services.html) are defined in a `services.yaml` file, and exposed to an application by listing a `relationship` to that service in the application's `.platform.app.yaml` file.  User, password, host, etc. information is then exposed to the running application in the `PLATFORM_RELATIONSHIPS` environment variable, which is a base64-encoded JSON string.  The following method allows easier access to credential information than decoding the environment variable yourself.

```java
Credential cred = config.getCredential('database')
```

The return value of `getCredentials()` is a dictionary matching the relationship JSON object, which includes the appropriate user, password, host, database name, and other pertinent information.  See the [Service documentation](https://docs.platform.sh/configuration/services.html) for your service for the exact structure and meaning of each property.  In most cases that information can be passed directly to whatever other client library is being used to connect to the service.

## Formatting service credentials

In some cases the library being used to connect to a service wants its credentials formatted in a specific way; it could be a DSN string of some sort or it needs certain values concatenated to the database name, etc.  For those cases you can use "Credential Formatters".  

A Credential Formatter is a functional interface that takes a credentials array and returns any type, since the library may want different types.

```java


Config config = new Config();
CustomCredential credential = config.getCredential("key", CustomCredential::new);

```

The first parameter is the name of a relationship defined in `.platform.app.yaml`.  
The second is a formatter.  
If either the service or formatter is missing an exception will be thrown.  
The type of `formatted` will depend on the formatter function and can be safely passed directly to the client library.

Three formatters are included out of the box:

* `SQLDatabase` returns format to SQL technology such as PostgreSQL and MySQL.
* `MongoDB` returns a MongoClient instance.
* `MySQL` returns a MySQL DataSource.
* `PostgreSQL` returns a PostgreSQL DataSource.
* `Redis` returns a Redis JedisPool.
* `RedisSpring` returns JedisConnectionFactory to Spring Data.

### Reading Platform.sh variables

Platform.sh allows you to define arbitrary variables that may be available at build time, runtime, or both.  They are stored in the `PLATFORM_VARIABLES` environment variable, which is a base64-encoded JSON string.  

The following two methods allow access to those values from your code without having to bother decoding the values yourself:

```java
config.getVariables();
```

This method returns a dictionary of all variables defined. 


### Reading Routes

[Routes](https://docs.platform.sh/configuration/routes.html) on Platform.sh define how a project will handle incoming requests; that primarily means what application container will serve the request, but it also includes cache configuration, TLS settings, etc.  Routes may also have an optional ID, which is the preferred way to access them.

```java
config.getRoutes();
```

The `route()` method takes a single string for the route ID ("main" in this case) and returns the corresponding route array.  If the route is not found it will throw an exception.

To access all routes, or to search for a route that has no ID, the `routes()` method returns an dictionary of routes keyed by their URL.  That mirrors the structure of the `PLATFORM_ROUTES` environment variable.

If called in the build phase an exception is thrown.
