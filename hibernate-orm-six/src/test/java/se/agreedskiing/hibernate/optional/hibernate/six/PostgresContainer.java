package se.agreedskiing.hibernate.optional.hibernate.six;

import java.util.Map;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class PostgresContainer {

  protected static final String PASS = "pass";
  protected static final String USER = "user";
  protected static final String DB = "hibernate_orm_six";

  @Container
  protected static final PostgreSQLContainer<?> DATABASE = new PostgreSQLContainer<>(
    "postgres:14.0-alpine"
  )
    .withUsername(USER)
    .withPassword(PASS)
    .withDatabaseName(DB);

  protected static Map<String, String> propertiesUserPassDatabase() {
    return Map.of(
      "jakarta.persistence.jdbc.url",
      new StringBuilder("jdbc:postgresql://localhost:")
        .append(DATABASE.getFirstMappedPort())
        .append("/")
        .append(DB)
        .toString(),
      "jakarta.persistence.jdbc.user",
      USER,
      "jakarta.persistence.jdbc.password",
      PASS
    );
  }
}
