package se.agreedskiing.hibernate.optional.hibernate.five;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class OptionalSingleResultQueryTest extends PostgresContainer {

  private static final Logger LOGGER = LoggerFactory.getLogger(
    OptionalSingleResultQueryTest.class
  );
  private static final String ALL =
    "SELECT t FROM Test t WHERE (:uu IS NULL OR t.uu = :uu) AND (:field IS NULL OR t.field = :field) AND (:id IS NULL OR t.id = :id)"; // FAILES
  private static final String FIELD = "field";
  private static final String ID = "id";
  private static final String UU = "uu";

  private static EntityManager em;

  @BeforeAll
  static void setUp() {
    em =
      Persistence
        .createEntityManagerFactory("standard", propertiesUserPassDatabase())
        .createEntityManager();
  }

  @ParameterizedTest(name = "{index} => {0} shall return {1} results")
  @MethodSource
  void with_one_filter_active(
    final String testcase,
    final int result,
    final Long id,
    final String field,
    final UUID uu
  ) {
    validate(testcase, result, id, field, uu);
  }

  @ParameterizedTest(name = "{index} => {0} shall return {1} results")
  @MethodSource
  void no_filters(
    final String testcase,
    final int result,
    final Long id,
    final String field,
    final UUID uu
  ) {
    validate(testcase, result, id, field, uu);
  }

  @MethodSource
  void with_miss_matching_filter_inputs(
    final String testcase,
    final int result,
    final Long id,
    final String field,
    final UUID uu
  ) {
    validate(testcase, result, id, field, uu);
  }

  @ParameterizedTest(name = "{index} => {0} shall return {1} results")
  @MethodSource
  void with_matching_filter_inputs(
    final String testcase,
    final int result,
    final Long id,
    final String field,
    final UUID uu
  ) {
    validate(testcase, result, id, field, uu);
  }

  private void validate(
    final String testcase,
    final int result,
    final Long id,
    final String field,
    final UUID uu
  ) {
    LOGGER.info("{} should return {}", testcase, result);
    int size = -1;
    try {
      size =
        em
          .createQuery(ALL)
          .setParameter(ID, id)
          .setParameter(FIELD, field)
          .setParameter(UU, uu)
          .getResultList()
          .size();
    } catch (final Exception e) {
      fail(e);
    }
    assertEquals(result, size);
  }

  static Stream<Arguments> with_one_filter_active() {
    final var id = 1L;
    final var fields = "test2";
    final var uus = UUID.fromString("1ecd8c19-4036-403d-bf84-cf8400f67836");
    return Stream.of(
      Arguments.of("Id with other null", 1, id, null, null),
      Arguments.of("Fields with others null", 1, null, fields, null),
      Arguments.of("Uus with others null", 1, null, null, uus)
    );
  }

  static Stream<Arguments> no_filters() {
    return Stream.of(Arguments.of("Null (No filters)", 3, null, null, null));
  }

  static Stream<Arguments> with_miss_matching_filter_inputs() {
    final var id = 1L;
    final var fields = "test2";
    final var uus = UUID.fromString("1ecd8c19-4036-403d-bf84-cf8400f67836");
    return Stream.of(
      Arguments.of("Id and Field with other null", 0, id, fields, null),
      Arguments.of("Fields and Uus with others null", 0, null, fields, uus),
      Arguments.of("Uus and Id with other null", 0, id, null, uus),
      Arguments.of("All filter present", 0, id, fields, uus)
    );
  }

  static Stream<Arguments> with_matching_filter_inputs() {
    final var id = 1L;
    final var field = "test1";
    final var uu = UUID.fromString("40e6215d-b5c6-4896-987c-f30f3678f608");
    return Stream.of(
      Arguments.of("Id and Field with other null", 1, id, field, null),
      Arguments.of("Fields and Uus with others null", 1, null, field, uu),
      Arguments.of("Uus and Id with other null", 1, id, null, uu),
      Arguments.of("All filter present", 1, id, field, uu)
    );
  }
}
