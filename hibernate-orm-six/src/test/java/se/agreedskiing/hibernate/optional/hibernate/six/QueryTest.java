package se.agreedskiing.hibernate.optional.hibernate.six;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.*;
import java.util.*;
import org.junit.jupiter.api.*;

class QueryTest extends PostgresContainer {

  private static final String QUERY_FAILED =
    "Query failed with an exception instead of working";

  private static EntityManager em;

  @BeforeAll
  static void setUp() {
    em =
      Persistence
        .createEntityManagerFactory(
          "standard",
          Map.of(
            "jakarta.persistence.jdbc.url",
            "jdbc:postgresql://localhost:" +
            PostgresContainer.DATABASE.getFirstMappedPort() +
            "/hibernate_orm_six"
          )
        )
        .createEntityManager();
  }

  @Nested
  class get_list_with_optional_query_ {

    // SELECT t FROM Test t WHERE (COALESCE(:list) IS NOT NULL OR t.id IN (:list)) // working query in Hibernate ORM 5
    private static String IDS = "ids";
    private static String IDS_PRESENET = "idsPresent";
    private static String FIELDS = "fields";
    private static String FIELDS_PRESENET = "fieldsPresent";
    private static String UUS = "uus";
    private static String UUS_PRESENET = "uusPresent";
    private static String IDS_AND_FIELDS =
      "SELECT t FROM Test t WHERE (:idsPresent = 0 OR t.id IN :ids) AND (:fieldsPresent = 0 OR t.id IN :fields)";
    private static String IDS_AND_UUS =
      "SELECT t FROM Test t WHERE (:idsPresent = 0 OR t.id IN :ids) AND (:uusPresent = 0 OR t.id IN :uus)";
    private static String UUS_AND_FIELDS =
      "SELECT t FROM Test t WHERE (:uusPresent = 0 OR t.id IN :uus) AND (:fieldsPresent = 0 OR t.id IN :fields)";
    private static String ALL =
      "SELECT t FROM Test t WHERE (:uusPresent = 0 OR t.id IN :uus) AND (:fieldsPresent = 0 OR t.id IN :fields) AND (:idsPresent = 0 OR t.id IN :ids)";

    @org.junit.jupiter.api.Test
    void through_IDs_and_empty_Fields_containt_values() {
      final var ids = Arrays.asList(1L, 2L, 3L, 4L);
      final var fields = new ArrayList<String>();
      final var query = IDS_AND_FIELDS;
      final var params = Arrays.asList(
        IDS_PRESENET,
        IDS,
        FIELDS_PRESENET,
        FIELDS
      );
      assertAll(
        () ->
          assertDoesNotThrow(
            () ->
              assertFalse(
                em
                  .createQuery(query)
                  .setParameter(params.get(0), ids.isEmpty())
                  .setParameter(params.get(1), ids)
                  .setParameter(params.get(2), fields.isEmpty())
                  .setParameter(params.get(3), fields)
                  .getResultList()
                  .isEmpty(),
                "Empty list failed as seconds query param"
              ),
            QUERY_FAILED
          ),
        () ->
          assertDoesNotThrow(
            () ->
              assertFalse(
                em
                  .createQuery(query)
                  .setParameter(params.get(0), ids.isEmpty())
                  .setParameter(params.get(1), ids)
                  .setParameter(params.get(2), null)
                  .setParameter(params.get(3), null)
                  .getResultList()
                  .isEmpty(),
                "Null list failed as seconds query param"
              ),
            QUERY_FAILED
          )
      );
    }

    @org.junit.jupiter.api.Test
    void through_IDs_and_empty_UUs_containt_values() {
      final var ids = Arrays.asList(1L, 2L, 3L, 4L);
      final var uus = new ArrayList<UUID>();
      final var query = IDS_AND_UUS;
      final var params = Arrays.asList(IDS_PRESENET, IDS, UUS_PRESENET, UUS);
      assertAll(
        () ->
          assertDoesNotThrow(
            () ->
              assertFalse(
                em
                  .createQuery(query)
                  .setParameter(params.get(0), ids.isEmpty())
                  .setParameter(params.get(1), ids)
                  .setParameter(params.get(2), uus.isEmpty())
                  .setParameter(params.get(3), uus)
                  .getResultList()
                  .isEmpty(),
                "Empty list failed as seconds query param"
              ),
            QUERY_FAILED
          ),
        () ->
          assertDoesNotThrow(
            () ->
              assertFalse(
                em
                  .createQuery(query)
                  .setParameter(params.get(0), ids.isEmpty())
                  .setParameter(params.get(1), ids)
                  .setParameter(params.get(2), null)
                  .setParameter(params.get(3), null)
                  .getResultList()
                  .isEmpty(),
                "Null list failed as seconds query param"
              ),
            QUERY_FAILED
          )
      );
    }

    @org.junit.jupiter.api.Test
    void through_Fields_and_empty_UUs_containt_values() {
      final var fields = Arrays.asList(1L, 2L, 3L, 4L);
      final var uus = new ArrayList<UUID>();
      final var query = UUS_AND_FIELDS;
      final var params = Arrays.asList(
        FIELDS_PRESENET,
        FIELDS,
        UUS_PRESENET,
        UUS
      );
      assertAll(
        () ->
          assertDoesNotThrow(
            () ->
              assertFalse(
                em
                  .createQuery(query)
                  .setParameter(params.get(0), fields.isEmpty())
                  .setParameter(params.get(1), fields)
                  .setParameter(params.get(2), uus.isEmpty())
                  .setParameter(params.get(3), uus)
                  .getResultList()
                  .isEmpty(),
                "Empty list failed as seconds query param"
              ),
            QUERY_FAILED
          ),
        () ->
          assertDoesNotThrow(
            () ->
              assertFalse(
                em
                  .createQuery(query)
                  .setParameter(params.get(0), fields.isEmpty())
                  .setParameter(params.get(1), fields)
                  .setParameter(params.get(2), null)
                  .setParameter(params.get(3), null)
                  .getResultList()
                  .isEmpty(),
                "Null list failed as seconds query param"
              ),
            QUERY_FAILED
          )
      );
    }

    @org.junit.jupiter.api.Test
    void through_Fields_and_empty_IDs_containt_values() {
      final var fields = Arrays.asList("test1", "test2", "test3", "test4");
      final var ids = new ArrayList<Long>();
      final var query = IDS_AND_FIELDS;
      final var params = Arrays.asList(
        IDS_PRESENET,
        IDS,
        FIELDS_PRESENET,
        FIELDS
      );
      assertAll(
        () ->
          assertDoesNotThrow(
            () ->
              assertFalse(
                em
                  .createQuery(query)
                  .setParameter(params.get(0), ids.isEmpty())
                  .setParameter(params.get(1), ids)
                  .setParameter(params.get(2), fields.isEmpty())
                  .setParameter(params.get(3), fields)
                  .getResultList()
                  .isEmpty(),
                "Empty list failed as seconds query param"
              ),
            QUERY_FAILED
          ),
        () ->
          assertDoesNotThrow(
            () ->
              assertFalse(
                em
                  .createQuery(query)
                  .setParameter(params.get(0), null)
                  .setParameter(params.get(1), null)
                  .setParameter(params.get(2), fields.isEmpty())
                  .setParameter(params.get(3), fields)
                  .getResultList()
                  .isEmpty(),
                "Null list failed as seconds query param"
              ),
            QUERY_FAILED
          )
      );
    }

    @org.junit.jupiter.api.Test
    void through_UU_and_empty_IDs_containt_values() {
      final var uus = Arrays.asList(
        UUID.fromString("40e6215d-b5c6-4896-987c-f30f3678f608"),
        UUID.fromString("6ecd8c99-4036-403d-bf84-cf8400f67836"),
        UUID.fromString("6366d53a-c35c-41b2-90ce-6c43b73490d9"),
        UUID.fromString("37a55e88-5794-48d2-9805-0a44e6edb0b3")
      );
      final var ids = new ArrayList<Long>();
      final var query = IDS_AND_UUS;
      final var params = Arrays.asList(IDS_PRESENET, IDS, UUS_PRESENET, UUS);
      assertAll(
        () ->
          assertDoesNotThrow(
            () ->
              assertFalse(
                em
                  .createQuery(query)
                  .setParameter(params.get(0), ids.isEmpty())
                  .setParameter(params.get(1), ids)
                  .setParameter(params.get(2), uus.isEmpty())
                  .setParameter(params.get(3), uus)
                  .getResultList()
                  .isEmpty(),
                "Empty list failed as seconds query param"
              ),
            QUERY_FAILED
          ),
        () ->
          assertDoesNotThrow(
            () ->
              assertFalse(
                em
                  .createQuery(query)
                  .setParameter(params.get(0), null)
                  .setParameter(params.get(1), null)
                  .setParameter(params.get(2), uus.isEmpty())
                  .setParameter(params.get(3), uus)
                  .getResultList()
                  .isEmpty(),
                "Null list failed as seconds query param"
              ),
            QUERY_FAILED
          )
      );
    }

    @org.junit.jupiter.api.Test
    void through_UU_and_empty_Fields_containt_values() {
      final var uus = Arrays.asList(
        UUID.fromString("40e6215d-b5c6-4896-987c-f30f3678f608"),
        UUID.fromString("6ecd8c99-4036-403d-bf84-cf8400f67836"),
        UUID.fromString("6366d53a-c35c-41b2-90ce-6c43b73490d9"),
        UUID.fromString("37a55e88-5794-48d2-9805-0a44e6edb0b3")
      );
      final var fields = new ArrayList<String>();
      final var query = UUS_AND_FIELDS;
      final var params = Arrays.asList(
        FIELDS_PRESENET,
        FIELDS,
        UUS_PRESENET,
        UUS
      );
      assertAll(
        () ->
          assertDoesNotThrow(
            () ->
              assertFalse(
                em
                  .createQuery(query)
                  .setParameter(params.get(0), fields.isEmpty())
                  .setParameter(params.get(1), fields)
                  .setParameter(params.get(2), uus.isEmpty())
                  .setParameter(params.get(3), uus)
                  .getResultList()
                  .isEmpty(),
                "Empty list failed as seconds query param"
              ),
            QUERY_FAILED
          ),
        () ->
          assertDoesNotThrow(
            () ->
              assertFalse(
                em
                  .createQuery(query)
                  .setParameter(params.get(0), null)
                  .setParameter(params.get(1), null)
                  .setParameter(params.get(2), uus.isEmpty())
                  .setParameter(params.get(3), uus)
                  .getResultList()
                  .isEmpty(),
                "Null list failed as seconds query param"
              ),
            QUERY_FAILED
          )
      );
    }

    @org.junit.jupiter.api.Test
    void empty_if_all_are_empty() {
      final var fields = new ArrayList<String>();
      final var ids = new ArrayList<Long>();
      final var uus = new ArrayList<UUID>();
      assertDoesNotThrow(
        () ->
          assertFalse(
            em
              .createQuery(ALL)
              .setParameter(FIELDS_PRESENET, fields.isEmpty())
              .setParameter(FIELDS, fields)
              .setParameter(UUS_PRESENET, uus.isEmpty())
              .setParameter(UUS, uus)
              .setParameter(IDS_PRESENET, ids.isEmpty())
              .setParameter(IDS, ids)
              .getResultList()
              .isEmpty(),
            "Empty list failed as seconds query param"
          ),
        QUERY_FAILED
      );
    }

    @org.junit.jupiter.api.Test
    void empty_if_all_are_null() {
      assertDoesNotThrow(
        () ->
          assertFalse(
            em
              .createQuery(ALL)
              .setParameter(FIELDS_PRESENET, null)
              .setParameter(FIELDS, null)
              .setParameter(UUS_PRESENET, null)
              .setParameter(UUS, null)
              .setParameter(IDS_PRESENET, null)
              .setParameter(IDS, null)
              .getResultList()
              .isEmpty(),
            "Empty list failed as seconds query param"
          ),
        QUERY_FAILED
      );
    }
  }

  @Nested
  class get_one_with_optional_query {

    private static String ID = "id";
    private static String FIELD = "field";
    private static String UU = "uu";
    private static String ID_AND_FIELD =
      "SELECT t FROM Test t WHERE (:id IS NULL OR t.id IN :id) AND (:field IS NULL OR t.id IN :field)";
    private static String ID_AND_UU =
      "SELECT t FROM Test t WHERE (:id IS NULL OR t.id IN :id) AND (:uu IS NULL OR t.id IN :uu)";
    private static String UU_AND_FIELD =
      "SELECT t FROM Test t WHERE (:uu IS NULL OR t.id IN :uu) AND (:field IS NULL OR t.id IN :field)";
    private static String ALL =
      "SELECT t FROM Test t WHERE (:uu IS NULL OR t.id IN :uu) AND (:field IS NULL OR t.id IN :field) AND (:id IS NULL OR t.id IN :id)";

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

  @Entity
  @Table(name = "test")
  public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String field; //NOSONAR

    public UUID uu; //NOSONAR
  }
}
