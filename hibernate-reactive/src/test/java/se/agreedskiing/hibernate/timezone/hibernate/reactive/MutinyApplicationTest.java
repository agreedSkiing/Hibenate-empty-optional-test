package se.agreedskiing.hibernate.timezone.hibernate.reactive;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.agreedskiing.hibernate.list.hibernate.reactive.MutinyApplication;

class MutinyApplicationTest extends PostgresContainer {

  MutinyApplication repository;

  static final String PERSISTANCE_UNIT = "standard";
  static final int PORT = PostgresContainer.DATABASE.getFirstMappedPort();

  @BeforeEach
  void setUp() {
    repository = new MutinyApplication();
  }

  @Test
  void get_a_list_through_IDs_containt_values() {
    repository
      .listContaingLongs(PERSISTANCE_UNIT, PORT)
      .invoke(entity -> assertFalse(entity.isEmpty()))
      .subscribe()
      .withSubscriber(UniAssertSubscriber.create())
      .awaitItem()
      .assertCompleted();
  }

  @Test
  void get_an_empty_list_through_IDs() {
    repository
      .emptyListOfLongs(PERSISTANCE_UNIT, PORT)
      .invoke(entity -> assertTrue(entity.isEmpty()))
      .subscribe()
      .withSubscriber(UniAssertSubscriber.create())
      .awaitItem()
      .assertCompleted();
  }

  @Test
  void get_a_empty_list_when_IDs_is_null() {
    repository
      .nullListOfLongs(PERSISTANCE_UNIT, PORT)
      .invoke(entity -> assertTrue(entity.isEmpty()))
      .subscribe()
      .withSubscriber(UniAssertSubscriber.create())
      .awaitItem()
      .assertCompleted();
  }

  @Test
  void get_a_list_through_Strings_containt_values() {
    repository
      .listContaingStrings(PERSISTANCE_UNIT, PORT)
      .invoke(entity -> assertFalse(entity.isEmpty()))
      .subscribe()
      .withSubscriber(UniAssertSubscriber.create())
      .awaitItem()
      .assertCompleted();
  }

  @Test
  void get_an_empty_list_through_Strings() {
    repository
      .emptyListOfStrings(PERSISTANCE_UNIT, PORT)
      .invoke(entity -> assertTrue(entity.isEmpty()))
      .subscribe()
      .withSubscriber(UniAssertSubscriber.create())
      .awaitItem()
      .assertCompleted();
  }

  @Test
  void get_a_empty_list_when_Strings_is_null() {
    repository
      .nullListOfStrings(PERSISTANCE_UNIT, PORT)
      .invoke(entity -> assertTrue(entity.isEmpty()))
      .subscribe()
      .withSubscriber(UniAssertSubscriber.create())
      .awaitItem()
      .assertCompleted();
  }

  @Test
  void get_a_list_through_UUIDs_containt_values() {
    repository
      .listContaingUUIDs(PERSISTANCE_UNIT, PORT)
      .invoke(entity -> assertFalse(entity.isEmpty()))
      .subscribe()
      .withSubscriber(UniAssertSubscriber.create())
      .awaitItem()
      .assertCompleted();
  }

  @Test
  void get_an_empty_list_through_UUIDs() {
    repository
      .emptyListOfUUIDs(PERSISTANCE_UNIT, PORT)
      .invoke(entity -> assertTrue(entity.isEmpty()))
      .subscribe()
      .withSubscriber(UniAssertSubscriber.create())
      .awaitItem()
      .assertCompleted();
  }

  @Test
  void get_a_empty_list_when_UUIDs_is_null() {
    repository
      .nullListOfUUIDs(PERSISTANCE_UNIT, PORT)
      .invoke(entity -> assertTrue(entity.isEmpty()))
      .subscribe()
      .withSubscriber(UniAssertSubscriber.create())
      .awaitItem()
      .assertCompleted();
  }
}
