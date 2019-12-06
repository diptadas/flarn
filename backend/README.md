# Flarn Backend Application

[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2FSpring-Boot-Framework%2FSpring-Boot-Application-Template.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2FSpring-Boot-Framework%2FSpring-Boot-Application-Template?ref=badge_shield)

The only thing better than a Maven archetype is a repo you can fork with everything already setup. Skip the documentation and just fork-and-code.

Delete the sample code, replace with your own and you’re good to go.

## Built With

- [Maven](https://maven.apache.org/) - Dependency Management
- [JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Java™ Platform, Standard Edition Development Kit
- [Spring Boot](https://spring.io/projects/spring-boot) - Framework to ease the bootstrapping and development of new Spring Applications
- [Postgres](https://www.mysql.com/) - Open-Source Relational Database Management System
- [git](https://git-scm.com/) - Free and Open-Source distributed version control system
- [VueJS](https://vuejs.org/) - The Progressive JavaScript Framework for building Single Page Web Applications.
- [Lombok](https://projectlombok.org/) - Never write another getter or equals method again, with one annotation your class has a fully featured builder, Automate your logging variables, and much more.
- [Swagger](https://swagger.io/) - Open-Source software framework backed by a large ecosystem of tools that helps developers design, build, document, and consume RESTful Web services.

## External Tools Used

- [Insomia](https://insomnia.rest) - API Development Environment (Testing Documentation)

## To-Do

- [x] RESTful Web Service (CRUD)
- [x] Bootstrap - CSS
- [ ] Docker
- [ ] HATEOS
- [ ] Spring Boot Admin
- [ ] NoSQL (MongoDB)
- [ ] Micrometer
- [ ] Logger (Console, File, Mail)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.arc.sbtest.SBtemplateApplication` class from your IDE.

- Download the zip or clone the Git repository.
- Unzip the zip file (if you downloaded one)
- Open Command Prompt and Change directory (cd) to folder containing pom.xml
- Open Eclipse
  - File -> Import -> Existing Maven Project -> Navigate to the folder where you unzipped the zip
  - Select the project
- Choose the Spring Boot Application file (search for @SpringBootApplication)
- Right Click on the file and Run as Java Application

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```
