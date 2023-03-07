# Hibernate Reactive list IS EMPTY query testing

The purpose of this project is to test if Hibernate Reactive can handle `:list IS EMPTY OR t.id IN :list` with a comparison to Hibernate ORM 5 and 6 base implementations.

## Running the application

**Prerequisite:** Java, maven and docker (testcontainers)

### Tests

Test are ran either from each subproject or form the parent project with the command below

```bash
mvn clean test --fail-at-end
```

Each test launches a postgres in a testcontainer on a random port and each of them loads the `import.sql` script with the help of Hibernate.

#### Errors

The project produces the some errors when doing trying to do the `SELECT t FROM Test t WHERE (COALESCE(:list) IS NOT NULL OR t.id IN (:list))`

All logs have been uploaded in the [run_1.log](run_1.log) and [run_2.log](run_2.log) files. Run_2.log is present since Hibernate ORM 6 produces another error which is not the same for Hibernate Reactive.

## Formatting

**Prerequisite:** Npm

Formatting is done by prettier and its plugins:

```bash script
npm install
npx prettier --write .
```
