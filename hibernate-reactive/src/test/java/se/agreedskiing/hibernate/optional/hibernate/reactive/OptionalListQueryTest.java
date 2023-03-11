package se.agreedskiing.hibernate.optional.hibernate.reactive;

import static org.junit.jupiter.api.Assertions.*;

import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import java.util.*;
import java.util.stream.Stream;
import javax.persistence.Persistence;
import org.hibernate.reactive.mutiny.Mutiny;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class OptionalListQueryTest extends PostgresContainer {

  private static final Logger LOGGER = LoggerFactory.getLogger(
    OptionalListQueryTest.class
  );
  private static final String ALL =
    "SELECT t FROM Test t WHERE (:uusExists = FALSE OR t.uu IN :uus) AND (:fieldsExists = FALSE OR t.field IN :fields) AND (:idsExists = FALSE OR t.id IN :ids)";
  private static final String FIELDS = "fields";
  private static final String FIELDS_PRESENET = "fieldsExists";
  private static final String IDS = "ids";
  private static final String IDS_PRESENET = "idsExists";
  private static final String UUS = "uus";
  private static final String UUS_PRESENET = "uusExists";

  private static Mutiny.SessionFactory sf;

  @BeforeAll
  static void setUp() {
    sf =
      Persistence
        .createEntityManagerFactory("standard", propertiesUserPassDatabase())
        .unwrap(Mutiny.SessionFactory.class);
  }

  @ParameterizedTest(name = "{index} => {0} shall return {1} results")
  @MethodSource
  void with_one_filter_active(
    final String testcase,
    final int result,
    final List<Long> ids,
    final List<String> fields,
    final List<UUID> uus
  ) {
    validate(testcase, result, ids, fields, uus);
  }

  @ParameterizedTest(name = "{index} => {0} shall return {1} results")
  @MethodSource
  void no_filters(
    final String testcase,
    final int result,
    final List<Long> ids,
    final List<String> fields,
    final List<UUID> uus
  ) {
    validate(testcase, result, ids, fields, uus);
  }

  @MethodSource
  void with_miss_matching_filter_inputs(
    final String testcase,
    final int result,
    final List<Long> ids,
    final List<String> fields,
    final List<UUID> uus
  ) {
    validate(testcase, result, ids, fields, uus);
  }

  @ParameterizedTest(name = "{index} => {0} shall return {1} results")
  @MethodSource
  void with_matching_filter_inputs(
    final String testcase,
    final int result,
    final List<Long> ids,
    final List<String> fields,
    final List<UUID> uus
  ) {
    validate(testcase, result, ids, fields, uus);
  }

  private void validate(
    final String testcase,
    final int result,
    final List<Long> ids,
    final List<String> fields,
    final List<UUID> uus
  ) {
    LOGGER.info("{} should return {}", testcase, result);
    try {
      sf
        .withSession(session ->
          session
            .createQuery(ALL)
            .setParameter(IDS_PRESENET, isPresent(ids))
            .setParameter(IDS, ids)
            .setParameter(FIELDS_PRESENET, isPresent(fields))
            .setParameter(FIELDS, fields)
            .setParameter(UUS_PRESENET, isPresent(uus))
            .setParameter(UUS, uus)
            .getResultList()
        )
        .invoke(list -> assertEquals(result, list.size()))
        .subscribe()
        .withSubscriber(UniAssertSubscriber.create())
        .awaitItem()
        .assertCompleted();
    } catch (final Exception e) {
      fail(e);
    }
  }

  private boolean isPresent(final Collection<?> collection) {
    return Objects.nonNull(collection) && !collection.isEmpty();
  }

  static Stream<Arguments> with_one_filter_active() {
    final var ids_one_exists = Arrays.asList(1L, 88L, 51L);
    final var fields_one_exists = Arrays.asList("test2", "mimo", "mango");
    final var uus_one_exists = Arrays.asList(
      UUID.fromString("1ecd8c19-4036-403d-bf84-cf8400f67836"),
      UUID.fromString("ff7efdbd-01c5-4f6c-84ae-729bddc57803"),
      UUID.fromString("9f5959f3-e770-4cb4-91a6-93d5e4f8b466")
    );
    return Stream.of(
      Arguments.of(
        "Ids with others sfpty",
        1,
        ids_one_exists,
        new ArrayList<String>(),
        new ArrayList<UUID>()
      ),
      Arguments.of("Ids with others null", 1, ids_one_exists, null, null),
      Arguments.of(
        "Fields with others sfpty",
        1,
        new ArrayList<Long>(),
        fields_one_exists,
        new ArrayList<UUID>()
      ),
      Arguments.of("Fields with others null", 1, null, fields_one_exists, null),
      Arguments.of(
        "Uus with others sfpty",
        1,
        new ArrayList<Long>(),
        new ArrayList<String>(),
        uus_one_exists
      ),
      Arguments.of("Uus with others null", 1, null, null, uus_one_exists)
    );
  }

  static Stream<Arguments> no_filters() {
    return Stream.of(
      Arguments.of("Null (No filters)", 3, null, null, null),
      Arguments.of(
        "sfpty (No filters)",
        3,
        new ArrayList<Long>(),
        new ArrayList<String>(),
        new ArrayList<UUID>()
      )
    );
  }

  static Stream<Arguments> with_miss_matching_filter_inputs() {
    final var ids_one_exists = Arrays.asList(1L, 88L, 51L);
    final var fields_one_exists = Arrays.asList("test2", "mimo", "mango");
    final var uus_one_exists = Arrays.asList(
      UUID.fromString("1ecd8c19-4036-403d-bf84-cf8400f67836"),
      UUID.fromString("ff7efdbd-01c5-4f6c-84ae-729bddc57803"),
      UUID.fromString("9f5959f3-e770-4cb4-91a6-93d5e4f8b466")
    );
    return Stream.of(
      Arguments.of(
        "Ids and Fields with others sfpty",
        0,
        ids_one_exists,
        fields_one_exists,
        new ArrayList<UUID>()
      ),
      Arguments.of(
        "Ids and Fields with others null",
        0,
        ids_one_exists,
        fields_one_exists,
        null
      ),
      Arguments.of(
        "Fields and Uus with others sfpty",
        0,
        new ArrayList<Long>(),
        fields_one_exists,
        uus_one_exists
      ),
      Arguments.of(
        "Fields and Uus with others null",
        0,
        null,
        fields_one_exists,
        uus_one_exists
      ),
      Arguments.of(
        "Uus and Ids with others sfpty",
        0,
        ids_one_exists,
        new ArrayList<String>(),
        uus_one_exists
      ),
      Arguments.of(
        "Uus and Ids with others null",
        0,
        ids_one_exists,
        null,
        uus_one_exists
      ),
      Arguments.of(
        "All filter present",
        0,
        ids_one_exists,
        fields_one_exists,
        uus_one_exists
      )
    );
  }

  static Stream<Arguments> with_matching_filter_inputs() {
    final var ids_two_exists = Arrays.asList(1L, 2L, 51L);
    final var fields_two_exists = Arrays.asList("test1", "test2", "mango");
    final var uus_two_exists = Arrays.asList(
      UUID.fromString("40e6215d-b5c6-4896-987c-f30f3678f608"),
      UUID.fromString("6ecd8c99-4036-403d-bf84-cf8400f67836"),
      UUID.fromString("9f5959f3-e770-4cb4-91a6-93d5e4f8b466")
    );
    final var ids_three_exists = new ArrayList<Long>(ids_two_exists);
    ids_three_exists.set(2, 3L);
    final var fields_three_exists = new ArrayList<String>(fields_two_exists);
    fields_three_exists.set(2, "test3");
    final var uus_three_exists = new ArrayList<UUID>(uus_two_exists);
    uus_three_exists.set(
      2,
      UUID.fromString("1ecd8c19-4036-403d-bf84-cf8400f67836")
    );
    return Stream.of(
      Arguments.of(
        "Ids and Fields with others sfpty",
        2,
        ids_two_exists,
        fields_two_exists,
        new ArrayList<UUID>()
      ),
      Arguments.of(
        "Ids and Fields with others null",
        2,
        ids_two_exists,
        fields_two_exists,
        null
      ),
      Arguments.of(
        "Fields and Uus with others sfpty",
        2,
        new ArrayList<Long>(),
        fields_two_exists,
        uus_two_exists
      ),
      Arguments.of(
        "Fields and Uus with others null",
        2,
        null,
        fields_two_exists,
        uus_two_exists
      ),
      Arguments.of(
        "Uus and Ids with others sfpty",
        2,
        ids_two_exists,
        new ArrayList<String>(),
        uus_two_exists
      ),
      Arguments.of(
        "Uus and Ids with others null",
        2,
        ids_two_exists,
        null,
        uus_two_exists
      ),
      Arguments.of(
        "All filter present",
        2,
        ids_two_exists,
        fields_two_exists,
        uus_two_exists
      ),
      Arguments.of(
        "Ids and Fields with others sfpty",
        3,
        ids_three_exists,
        fields_three_exists,
        new ArrayList<UUID>()
      ),
      Arguments.of(
        "Ids and Fields with others null",
        3,
        ids_three_exists,
        fields_three_exists,
        null
      ),
      Arguments.of(
        "Fields and Uus with others sfpty",
        3,
        new ArrayList<Long>(),
        fields_three_exists,
        uus_three_exists
      ),
      Arguments.of(
        "Fields and Uus with others null",
        3,
        null,
        fields_three_exists,
        uus_three_exists
      ),
      Arguments.of(
        "Uus and Ids with others sfpty",
        3,
        ids_three_exists,
        new ArrayList<String>(),
        uus_three_exists
      ),
      Arguments.of(
        "Uus and Ids with others null",
        3,
        ids_three_exists,
        null,
        uus_three_exists
      ),
      Arguments.of(
        "All filter present",
        3,
        ids_three_exists,
        fields_three_exists,
        uus_three_exists
      )
    );
  }
}
