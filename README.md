# Task Java Template Project

## Prerequisites
* PostgreSQL

  Edit `application-default.properties` and `application-test.properties` and set the correct database name and create
  the database using commands like this:
    ```sql
    create role template with login password 'db_user_name';
    create database db_name with owner db_user_name;
    create database "db-name-tests" with owner db_user_name;
    ```

* JDK 11 ([Amazon Corretto](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html)).

## Things to Rename

* The name of the database and database role in `application-default.properties` and `application-test.properties`.
* Main class name `JavaBackendTemplateApplication`.
* The name of the project in `api.yaml`, and `index.html`.

## Things to Remove

* `SampelMapper.java`.
* `SampleRepository.java`