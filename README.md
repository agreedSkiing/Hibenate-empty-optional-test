# Hibernate optional query testing

The purpose of this project is to test if Hibernate Reactive can handle optional queries. This idea came by reading https://www.baeldung.com/spring-data-jpa-null-parameters and https://stackoverflow.com/questions/2488930/passing-empty-list-as-parameter-to-jpa-query-throws-error.

**NOTE:** All Hibernate Reactive test are done with Mutiny.

## Formatting

**Prerequisite:** Npm

Formatting is done by prettier and its plugins:

```bash script
npm ci
npx prettier --write .
```

## Running the application

**Prerequisite:** Java, maven and docker (testcontainers)

### Tests

Test are ran either from each subproject or form the parent project with the command below

```bash
mvn clean test --fail-at-end
```

Each test launches a postgres in a testcontainer on a random port and each of them loads the `import.sql` script with the help of Hibernate.
