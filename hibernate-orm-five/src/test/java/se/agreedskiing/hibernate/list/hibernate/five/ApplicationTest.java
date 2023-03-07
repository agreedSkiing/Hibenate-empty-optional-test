package se.agreedskiing.hibernate.list.hibernate.five;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicationTest extends PostgresContainer {

  private Application repo;

  @BeforeEach
  void start() {
    repo =
      new Application(
        "standard",
        PostgresContainer.DATABASE.getFirstMappedPort()
      );
  }

  @Test
  void get_a_list_containt_values() {
    try {
      final var entity = repo.listContaingValues();
      assertFalse(entity.isEmpty());
    } catch (final Exception e) {
      fail(ErrorTexts.QUERY_FAILED.explenation, e);
    }
  }

  @Test
  void get_an_empty_list() {
    try {
      final var entity = repo.listEmpty();
      assertTrue(entity.isEmpty());
    } catch (final Exception e) {
      fail(ErrorTexts.QUERY_FAILED.explenation, e);
    }
  }

  @Test
  void get_a_null_list_throws() {
    try {
      final var entity = repo.listNull();
      assertTrue(entity.isEmpty());
    } catch (final Exception e) {
      fail(ErrorTexts.QUERY_FAILED.explenation, e);
    }
  }
}
