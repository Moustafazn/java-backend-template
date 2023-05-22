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


## YAML Notes
* I added all yaml files into one file as i want to control all apis and manage references. 
* There is an error in the array query param and I fixed. In addition to I added titles for response and requests to avoid inline name. 
* I don't recommend to use specific query param as we can use ([RSQL specification](https://github.com/perplexhub/rsql-jpa-specification)).