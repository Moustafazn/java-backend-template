# Task Java Template Project

## Prerequisites

* PostgreSQL

  Edit `application-default.properties` and `application-test.properties` and set the correct database name and create
  the database using commands like this:
    ```sql
    create role template with login password 'db_user_name';
    create database templete with owner template;
    create database "template-tests" with owner templete;
    ```

* JDK 19 ([Amazon Corretto](https://github.com/corretto/corretto-19/releases)).

## Things to Rename

* The name of the database and database role in `application-default.properties` and `application-test.properties`.
* Main class name `JavaBackendTemplateApplication`.
* The name of the project in `api.yaml`, and `index.html`.

# Reads

* https://github.com/OpenAPITools/openapi-generator/blob/master/docs/generators/spring.md


