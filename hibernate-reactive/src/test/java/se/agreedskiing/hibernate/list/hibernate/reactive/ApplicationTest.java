package se.agreedskiing.hibernate.list.hibernate.reactive;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ApplicationTest extends PostgresContainer {

  Application repository;

  static final String PERSISTANCE_UNIT = "standard";
  static final int PORT = PostgresContainer.DATABASE.getFirstMappedPort();

  @BeforeEach
  void setUp() {
    repository = new Application();
  }

  @Nested
  class test_optional_list_query {

    private static final boolean OPTIONAL = true;

    @Test
    void get_a_list_through_IDs_containt_values() {
      repository
        .listContaingLongs(PERSISTANCE_UNIT, PORT, OPTIONAL)
        .invoke(entity -> assertFalse(entity.isEmpty()))
        .subscribe()
        .withSubscriber(UniAssertSubscriber.create())
        .awaitItem()
        .assertCompleted();
    }

    @Test
    void get_an_empty_list_through_IDs() {
      repository
        .emptyListOfLongs(PERSISTANCE_UNIT, PORT, OPTIONAL)
        .invoke(entity -> assertTrue(entity.isEmpty()))
        .subscribe()
        .withSubscriber(UniAssertSubscriber.create())
        .awaitItem()
        .assertCompleted();
    }

    @Test
    void get_a_empty_list_when_IDs_is_null() {
      repository
        .nullListOfLongs(PERSISTANCE_UNIT, PORT, OPTIONAL)
        .invoke(entity -> assertTrue(entity.isEmpty()))
        .subscribe()
        .withSubscriber(UniAssertSubscriber.create())
        .awaitItem()
        .assertCompleted();
    }

    @Test
    void get_a_list_through_Strings_containt_values() {
      repository
        .listContaingStrings(PERSISTANCE_UNIT, PORT, OPTIONAL)
        .invoke(entity -> assertFalse(entity.isEmpty()))
        .subscribe()
        .withSubscriber(UniAssertSubscriber.create())
        .awaitItem()
        .assertCompleted();
    }

    @Test
    void get_an_empty_list_through_Strings() {
      repository
        .emptyListOfStrings(PERSISTANCE_UNIT, PORT, OPTIONAL)
        .invoke(entity -> assertTrue(entity.isEmpty()))
        .subscribe()
        .withSubscriber(UniAssertSubscriber.create())
        .awaitItem()
        .assertCompleted();
    }

    @Test
    void get_a_empty_list_when_Strings_is_null() {
      repository
        .nullListOfStrings(PERSISTANCE_UNIT, PORT, OPTIONAL)
        .invoke(entity -> assertTrue(entity.isEmpty()))
        .subscribe()
        .withSubscriber(UniAssertSubscriber.create())
        .awaitItem()
        .assertCompleted();
    }

    @Test
    void get_a_list_through_UUIDs_containt_values() {
      repository
        .listContaingUUIDs(PERSISTANCE_UNIT, PORT, OPTIONAL)
        .invoke(entity -> assertFalse(entity.isEmpty()))
        .subscribe()
        .withSubscriber(UniAssertSubscriber.create())
        .awaitItem()
        .assertCompleted();
    }

    @Test
    void get_an_empty_list_through_UUIDs() {
      repository
        .emptyListOfUUIDs(PERSISTANCE_UNIT, PORT, OPTIONAL)
        .invoke(entity -> assertTrue(entity.isEmpty()))
        .subscribe()
        .withSubscriber(UniAssertSubscriber.create())
        .awaitItem()
        .assertCompleted();
    }

    @Test
    void get_a_empty_list_when_UUIDs_is_null() {
      repository
        .nullListOfUUIDs(PERSISTANCE_UNIT, PORT, OPTIONAL)
        .invoke(entity -> assertTrue(entity.isEmpty()))
        .subscribe()
        .withSubscriber(UniAssertSubscriber.create())
        .awaitItem()
        .assertCompleted();
    }
  }

  @Nested
  class test_list_query {

    private static final boolean OPTIONAL = false;

    @Test
    void get_a_list_through_IDs_containt_values() {
      repository
        .listContaingLongs(PERSISTANCE_UNIT, PORT, OPTIONAL)
        .invoke(entity -> assertFalse(entity.isEmpty()))
        .subscribe()
        .withSubscriber(UniAssertSubscriber.create())
        .awaitItem()
        .assertCompleted();
    }

    @Test
    void get_an_empty_list_through_IDs() {
      repository
        .emptyListOfLongs(PERSISTANCE_UNIT, PORT, OPTIONAL)
        .invoke(entity -> assertTrue(entity.isEmpty()))
        .subscribe()
        .withSubscriber(UniAssertSubscriber.create())
        .awaitItem()
        .assertCompleted();
    }

    @Test
    void get_a_empty_list_when_IDs_is_null() {
      repository
        .nullListOfLongs(PERSISTANCE_UNIT, PORT, OPTIONAL)
        .invoke(entity -> assertTrue(entity.isEmpty()))
        .subscribe()
        .withSubscriber(UniAssertSubscriber.create())
        .awaitItem()
        .assertCompleted();
    }

    @Test
    void get_a_list_through_Strings_containt_values() {
      repository
        .listContaingStrings(PERSISTANCE_UNIT, PORT, OPTIONAL)
        .invoke(entity -> assertFalse(entity.isEmpty()))
        .subscribe()
        .withSubscriber(UniAssertSubscriber.create())
        .awaitItem()
        .assertCompleted();
    }

    @Test
    void get_an_empty_list_through_Strings() {
      repository
        .emptyListOfStrings(PERSISTANCE_UNIT, PORT, OPTIONAL)
        .invoke(entity -> assertTrue(entity.isEmpty()))
        .subscribe()
        .withSubscriber(UniAssertSubscriber.create())
        .awaitItem()
        .assertCompleted();
    }

    @Test
    void get_a_empty_list_when_Strings_is_null() {
      repository
        .nullListOfStrings(PERSISTANCE_UNIT, PORT, OPTIONAL)
        .invoke(entity -> assertTrue(entity.isEmpty()))
        .subscribe()
        .withSubscriber(UniAssertSubscriber.create())
        .awaitItem()
        .assertCompleted();
    }

    @Test
    void get_a_list_through_UUIDs_containt_values() {
      repository
        .listContaingUUIDs(PERSISTANCE_UNIT, PORT, OPTIONAL)
        .invoke(entity -> assertFalse(entity.isEmpty()))
        .subscribe()
        .withSubscriber(UniAssertSubscriber.create())
        .awaitItem()
        .assertCompleted();
    }

    @Test
    void get_an_empty_list_through_UUIDs() {
      repository
        .emptyListOfUUIDs(PERSISTANCE_UNIT, PORT, OPTIONAL)
        .invoke(entity -> assertTrue(entity.isEmpty()))
        .subscribe()
        .withSubscriber(UniAssertSubscriber.create())
        .awaitItem()
        .assertCompleted();
    }

    @Test
    void get_a_empty_list_when_UUIDs_is_null() {
      repository
        .nullListOfUUIDs(PERSISTANCE_UNIT, PORT, OPTIONAL)
        .invoke(entity -> assertTrue(entity.isEmpty()))
        .subscribe()
        .withSubscriber(UniAssertSubscriber.create())
        .awaitItem()
        .assertCompleted();
    }
  }
}
