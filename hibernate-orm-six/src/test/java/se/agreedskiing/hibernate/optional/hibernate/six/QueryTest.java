package se.agreedskiing.hibernate.optional.hibernate.six;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class QueryTest extends PostgresContainer {

  private static final Logger LOGGER = LoggerFactory.getLogger(QueryTest.class);

  private static final String QUERY_FAILED = "Query failed with an exception instead of working";

  private static EntityManager em;

  @BeforeAll
  static void setUp() {
    em =
      Persistence
        .createEntityManagerFactory(
          "standard",
          Map.of(
            "jakarta.persistence.jdbc.url",
            "jdbc:postgresql://localhost:" + PostgresContainer.DATABASE.getFirstMappedPort() + "/hibernate_orm_six"
          )
        )
        .createEntityManager();
  }

  @Nested
  class get_list_with_optional_query_ {

    // SELECT t FROM Test t WHERE (COALESCE(:list) IS NOT NULL OR t.id IN (:list)) // working query in Hibernate ORM 5
    private static String IDS = "ids";
    private static String IDS_PRESENET = "idsEmpty";
    private static String FIELDS = "fields";
    private static String FIELDS_PRESENET = "fieldsEmpty";
    private static String UUS = "uus";
    private static String UUS_PRESENET = "uusEmpty";
    private static String ALL =
      "SELECT t FROM Test t WHERE (:uusEmpty IS NULL OR t.uu IN :uus) AND (:fieldsEmpty IS NULL OR t.field IN :fields) AND (:idsEmpty IS NULL OR t.id IN :ids)";

    @ParameterizedTest
    @MethodSource
    void that_has_items(
      final String testcase,
      final int results,
      final List<Long> ids,
      final List<String> fields,
      final List<UUID> uus
    ) {
      LOGGER.info("{} should return {}", testcase, results);
      int size = -1;
      try {
        size =
          em
            .createQuery(ALL)
            .setParameter(IDS_PRESENET, Objects.nonNull(ids) && !ids.isEmpty() ? false : null)
            .setParameter(IDS, ids)
            .setParameter(FIELDS_PRESENET, Objects.nonNull(fields) && !fields.isEmpty() ? false : null)
            .setParameter(FIELDS, fields)
            .setParameter(UUS_PRESENET, Objects.nonNull(uus) && !uus.isEmpty() ? false : null)
            .setParameter(UUS, uus)
            .getResultList()
            .size();
      } catch (final Exception e) {
        fail(QUERY_FAILED, e);
      }
      assertEquals(results, size);
    }

    // TODO: Add matching filter next that get one result and multiple results
    static Stream<Arguments> that_has_items() {
      final var ids_one_exists = Arrays.asList(1L, 88L, 51L);
      final var fields_one_exists = Arrays.asList("test2", "mimo", "mango");
      final var uus_one_exists = Arrays.asList(
        UUID.fromString("1ecd8c19-4036-403d-bf84-cf8400f67836"),
        UUID.fromString("ff7efdbd-01c5-4f6c-84ae-729bddc57803"),
        UUID.fromString("9f5959f3-e770-4cb4-91a6-93d5e4f8b466")
      );
      return Stream.of(
        Arguments.of("Ids with others empty", 1, ids_one_exists, new ArrayList<String>(), new ArrayList<UUID>()),
        Arguments.of("Ids with others null", 1, ids_one_exists, null, null),
        Arguments.of("Fields with others empty", 1, new ArrayList<Long>(), fields_one_exists, new ArrayList<UUID>()),
        Arguments.of("Fields with others null", 1, null, fields_one_exists, null),
        Arguments.of("Uus with others empty", 1, new ArrayList<Long>(), new ArrayList<String>(), uus_one_exists),
        Arguments.of("Uus with others null", 1, null, null, uus_one_exists),
        Arguments.of(
          "Ids and Fields with others empty (miss matching filter)",
          0,
          ids_one_exists,
          fields_one_exists,
          new ArrayList<UUID>()
        ),
        Arguments.of(
          "Ids and Fields with others null (miss matching filter)",
          0,
          ids_one_exists,
          fields_one_exists,
          null
        ),
        Arguments.of(
          "Fields and Uus with others empty (miss matching filter)",
          0,
          new ArrayList<Long>(),
          fields_one_exists,
          uus_one_exists
        ),
        Arguments.of(
          "Fields and Uus with others null (miss matching filter)",
          0,
          null,
          fields_one_exists,
          uus_one_exists
        ),
        Arguments.of(
          "Uus and Ids with others empty (miss matching filter)",
          0,
          ids_one_exists,
          new ArrayList<String>(),
          uus_one_exists
        ),
        Arguments.of("Uus and Ids with others null (miss matching filter)", 0, ids_one_exists, null, uus_one_exists),
        Arguments.of("All present (miss matching filter)", 0, ids_one_exists, fields_one_exists, uus_one_exists),
        Arguments.of("Null (No filters)", 3, null, null, null),
        Arguments.of("Empty (No filters)", 3, new ArrayList<Long>(), new ArrayList<String>(), new ArrayList<UUID>())
      );
    }
  }

  @Nested
  @Disabled
  class get_one_with_optional_query {

    private static String ID = "id";
    private static String FIELD = "field";
    private static String UU = "uu";
    private static String ALL =
      "SELECT t FROM Test t WHERE (:uu IS NULL OR t.uu IN :uu) AND (:field IS NULL OR t.field IN :field) AND (:id IS NULL OR t.id IN :id)";

    @org.junit.jupiter.api.Test
    void through_IDs_containt_values() {
      try {
        // final var entity = repo.listContaingLongs(OPTIONAL);
        // assertFalse(entity.isEmpty());
      } catch (final Exception e) {
        fail(QUERY_FAILED, e);
      }
    }

    @org.junit.jupiter.api.Test
    void get_an_empty_list_through_IDs() {
      try {
        // final var entity = repo.emptyListOfLongs(OPTIONAL);
        // assertTrue(entity.isEmpty());
      } catch (final Exception e) {
        fail(QUERY_FAILED, e);
      }
    }

    @org.junit.jupiter.api.Test
    void get_a_empty_list_when_IDs_is_null() {
      try {
        // final var entity = repo.nullListOfLongs(OPTIONAL);
        // assertTrue(entity.isEmpty());
      } catch (final Exception e) {
        fail(QUERY_FAILED, e);
      }
    }

    @org.junit.jupiter.api.Test
    void through_Strings_containt_values() {
      try {
        // final var entity = repo.listContaingStrings(OPTIONAL);
        // assertFalse(entity.isEmpty());
      } catch (final Exception e) {
        fail(QUERY_FAILED, e);
      }
    }

    @org.junit.jupiter.api.Test
    void get_an_empty_list_through_Strings() {
      try {
        // final var entity = repo.emptyListOfStrings(OPTIONAL);
        // assertTrue(entity.isEmpty());
      } catch (final Exception e) {
        fail(QUERY_FAILED, e);
      }
    }

    @org.junit.jupiter.api.Test
    void get_a_empty_list_when_Strings_is_null() {
      try {
        // final var entity = repo.nullListOfStrings(OPTIONAL);
        // assertTrue(entity.isEmpty());
      } catch (final Exception e) {
        fail(QUERY_FAILED, e);
      }
    }

    @org.junit.jupiter.api.Test
    void through_UUIDs_containt_values() {
      try {
        // final var entity = repo.listContaingUUIDs(OPTIONAL);
        // assertFalse(entity.isEmpty());
      } catch (final Exception e) {
        fail(QUERY_FAILED, e);
      }
    }

    @org.junit.jupiter.api.Test
    void get_an_empty_list_through_UUIDs() {
      try {
        // final var entity = repo.emptyListOfUUIDs(OPTIONAL);
        // assertTrue(entity.isEmpty());
      } catch (final Exception e) {
        fail(QUERY_FAILED, e);
      }
    }

    @org.junit.jupiter.api.Test
    void get_a_empty_list_when_UUIDs_is_null() {
      try {
        // final var entity = repo.nullListOfUUIDs(OPTIONAL);
        // assertTrue(entity.isEmpty());
      } catch (final Exception e) {
        fail(QUERY_FAILED, e);
      }
    }
  }
}
