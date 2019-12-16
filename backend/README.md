# Flarn Backend

## Built With

Core maven dependencies:

- [Spring Boot Web Starter](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web)
- [Spring Boot Security Starter](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security)
- [Spring Boot ActiveMQ Starter](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-activemq/1.4.0.RELEASE)
- [Spring Boot WebSocket Starter](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-websocket/)
- [PostgreSQL](https://mvnrepository.com/artifact/org.postgresql/postgresql)
- [H2 Database Engine](https://mvnrepository.com/artifact/com.h2database/h2)
- [Lombok](https://mvnrepository.com/artifact/org.projectlombok/lombok)
- [Springfox Swagger UI](https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui)
- [SendGrid Java](https://mvnrepository.com/artifact/com.sendgrid/sendgrid-java)
- [JUnit Jupiter Engine](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-engine)

## Build & Run

```sh
    cd backend
    mvn package
    java -jar target/flarn-<version>.jar
```

This will start a development server on port `8085`.

## API Documentation

We have integrated [SpringFox](https://springfox.github.io/springfox/) for automated REST API documentation. You can view our API documentation [here](https://flarn-baylor.herokuapp.com/swagger-ui.html).
