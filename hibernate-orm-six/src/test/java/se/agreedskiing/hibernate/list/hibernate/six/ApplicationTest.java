package se.agreedskiing.hibernate.list.hibernate.six;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicationTest extends PostgresContainer {

  private Application repo;

  @BeforeEach
  void boot() {
    repo =
      new Application(
        "standard",
        PostgresContainer.DATABASE.getFirstMappedPort()
      );
  }

  @Test
  void get_a_list_through_IDs_containt_values() {
    try {
      final var entity = repo.listContaingLongs();
      assertFalse(entity.isEmpty());
    } catch (final Exception e) {
      fail(ErrorTexts.QUERY_FAILED.explenation, e);
    }
  }

  @Test
  void get_an_empty_list_through_IDs() {
    try {
      final var entity = repo.emptyListOfLongs();
      assertTrue(entity.isEmpty());
    } catch (final Exception e) {
      fail(ErrorTexts.QUERY_FAILED.explenation, e);
    }
  }

  @Test
  void get_a_empty_list_when_IDs_is_null() {
    try {
      final var entity = repo.nullListOfLongs();
      assertTrue(entity.isEmpty());
    } catch (final Exception e) {
      fail(ErrorTexts.QUERY_FAILED.explenation, e);
    }
  }

  @Test
  void get_a_list_through_Strings_containt_values() {
    try {
      final var entity = repo.listContaingStrings();
      assertFalse(entity.isEmpty());
    } catch (final Exception e) {
      fail(ErrorTexts.QUERY_FAILED.explenation, e);
    }
  }

  @Test
  void get_an_empty_list_through_Strings() {
    try {
      final var entity = repo.emptyListOfStrings();
      assertTrue(entity.isEmpty());
    } catch (final Exception e) {
      fail(ErrorTexts.QUERY_FAILED.explenation, e);
    }
  }

  @Test
  void get_a_empty_list_when_Strings_is_null() {
    try {
      final var entity = repo.nullListOfStrings();
      assertTrue(entity.isEmpty());
    } catch (final Exception e) {
      fail(ErrorTexts.QUERY_FAILED.explenation, e);
    }
  }

  @Test
  void get_a_list_through_UUIDs_containt_values() {
    try {
      final var entity = repo.listContaingUUIDs();
      assertFalse(entity.isEmpty());
    } catch (final Exception e) {
      fail(ErrorTexts.QUERY_FAILED.explenation, e);
    }
  }

  @Test
  void get_an_empty_list_through_UUIDs() {
    try {
      final var entity = repo.emptyListOfUUIDs();
      assertTrue(entity.isEmpty());
    } catch (final Exception e) {
      fail(ErrorTexts.QUERY_FAILED.explenation, e);
    }
  }

  @Test
  void get_a_empty_list_when_UUIDs_is_null() {
    try {
      final var entity = repo.nullListOfUUIDs();
      assertTrue(entity.isEmpty());
    } catch (final Exception e) {
      fail(ErrorTexts.QUERY_FAILED.explenation, e);
    }
  }
}
