package se.agreedskiing.hibernate.timezone.hibernate.reactive;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.agreedskiing.hibernate.list.hibernate.reactive.Application;

class ApplicationTest extends PostgresContainer {

  Application repository;

  static final String PERSISTANCE_UNIT = "standard";
  static final int PORT = PostgresContainer.DATABASE.getFirstMappedPort();

  @BeforeEach
  void setUp() {
    repository = new Application();
  }

  @Test
  void get_a_list_containt_values() {
    repository
      .listContaingValues(PERSISTANCE_UNIT, PORT)
      .invoke(entity -> assertFalse(entity.isEmpty()))
      .subscribe()
      .withSubscriber(UniAssertSubscriber.create())
      .awaitItem()
      .assertCompleted();
  }

  @Test
  void get_an_empty_list() {
    repository
      .listEmpty(PERSISTANCE_UNIT, PORT)
      .invoke(entity -> assertTrue(entity.isEmpty()))
      .subscribe()
      .withSubscriber(UniAssertSubscriber.create())
      .awaitItem()
      .assertCompleted();
  }

  @Test
  void get_a_null_list_throws() {
    repository
      .listNull(PERSISTANCE_UNIT, PORT)
      .invoke(entity -> assertTrue(entity.isEmpty()))
      .subscribe()
      .withSubscriber(UniAssertSubscriber.create())
      .awaitItem()
      .assertCompleted();
  }
}
