package se.agreedskiing.hibernate.optional.hibernate.reactive;

import java.util.Map;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class PostgresContainer {

  protected static final String PASS = "pass";
  protected static final String USER = "user";
  protected static final String DB = "hibernate_reactive";

  @Container
  protected static final PostgreSQLContainer<?> DATABASE = new PostgreSQLContainer<>(
    "postgres:14.0-alpine"
  )
    .withUsername(USER)
    .withPassword(PASS)
    .withDatabaseName(DB);

  protected static Map<String, String> propertiesUserPassDatabase() {
    return Map.of(
      "javax.persistence.jdbc.url",
      new StringBuilder("jdbc:postgresql://localhost:")
        .append(DATABASE.getFirstMappedPort())
        .append("/")
        .append(DB)
        .toString(),
      "javax.persistence.jdbc.user",
      USER,
      "javax.persistence.jdbc.password",
      PASS
    );
  }
}
