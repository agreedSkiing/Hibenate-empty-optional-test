# Hibernate Reactive list IS EMPTY query testing

The purpose of this project is to test if Hibernate Reactive can handle `:list IS EMPTY OR t.COLUMN IN :list` with a comparison to Hibernate ORM 5 and 6 base implementations.

First of all no the code can't handle `:list IS EMPTY OR t.COLUMN IN :list` ☹, but it seems to be able to handle `SELECT t FROM Test t WHERE (COALESCE(:list) IS NOT NULL OR t.id IN (:list))` for different values.

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

For test results please check the files named `COMMIT-*.md`.
