# TO DO LIST

<img src="./src/main/resources/static/readme_assets/todolist-home.png">

## What's the project?
A project management tool for creating tasks and lists to organize your daily activities. This application permite create, view, update, mark like done and archive your tasks and lists.

## Clone repository
To clone repository, execute this command:

    git clone https://github.com/MatheusEzaquiel/todolist-api.git

## Configurations
Go to file `application.properties` and set the configurations in According with your database:

    spring.datasource.url=jdbc:postgresql://localhost:5432/<database_name>
    spring.datasource.username=<database_username>
    spring.datasource.password=<database_password>

## Install Dependencies

Make sure you have Java JDK and Apache Maven installed. Then, navigate to the `backend` directory and execute the following command to install dependencies:

    cd backend
    mvn install

## Execute the API

    mvn spring-boot:run

## Technologies

- [Java](https://docs.oracle.com/en/java/javase/17/) - Programming language used for backend development
- [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) - FFramework used to simplify and accelerate Java application development
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html) - Library used to facilitate access to relational data in Java applications
- [Spring Security](https://spring.io/projects/spring-security) - Library used to provide authentication and authorization in Java applications
- [PostgreSQL](https://www.postgresql.org/docs/) - Open-source relational database