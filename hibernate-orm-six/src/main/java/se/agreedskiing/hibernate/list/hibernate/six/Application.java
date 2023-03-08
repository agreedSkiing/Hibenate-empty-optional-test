package se.agreedskiing.hibernate.list.hibernate.six;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import se.agreedskiing.hibernate.list.hibernate.six.entities.Test;

public class Application {

  EntityManagerFactory entityManagerFactory;

  public Application(final String persitanceUnit, final int port) {
    this.entityManagerFactory =
      Persistence.createEntityManagerFactory(
        persitanceUnit,
        Map.of(
          "jakarta.persistence.jdbc.url",
          "jdbc:postgresql://localhost:" + port + "/hibernate_orm_six"
        )
      );
  }

  public static void main(final String[] args) {
    //Not used
  }

  public List<Test> listContaingLongs() {
    return queryLongs(List.of(1L, 2L, 3L));
  }

  public List<Test> emptyListOfLongs() {
    return queryLongs(Collections.emptyList());
  }

  public List<Test> nullListOfLongs() {
    return queryLongs(null);
  }

  public List<Test> listContaingUUIDs() {
    return queryUUIDs(
      List.of(
        UUID.fromString("40e6215d-b5c6-4896-987c-f30f3678f608"),
        UUID.fromString("6ecd8c99-4036-403d-bf84-cf8400f67836"),
        UUID.fromString("6366d53a-c35c-41b2-90ce-6c43b73490d9"),
        UUID.fromString("37a55e88-5794-48d2-9805-0a44e6edb0b3")
      )
    );
  }

  public List<Test> emptyListOfUUIDs() {
    return queryUUIDs(Collections.emptyList());
  }

  public List<Test> nullListOfUUIDs() {
    return queryUUIDs(null);
  }

  public List<Test> listContaingStrings() {
    return queryStrings(List.of("test1", "test2", "test3", "test4"));
  }

  public List<Test> emptyListOfStrings() {
    return queryStrings(Collections.emptyList());
  }

  public List<Test> nullListOfStrings() {
    return queryStrings(null);
  }

  private List<Test> queryLongs(final List<Long> ids) {
    return this.entityManagerFactory.createEntityManager()
      .createQuery(
        "SELECT t FROM Test t WHERE (COALESCE(:list, NULL) IS NOT NULL OR t.id IN (:list))",
        Test.class
      )
      .setParameter("list", ids)
      .getResultList();
  }

  private List<Test> queryUUIDs(final List<UUID> values) {
    return this.entityManagerFactory.createEntityManager()
      .createQuery(
        "SELECT t FROM Test t WHERE (COALESCE(:list) IS NOT NULL OR t.uu IN (:list))",
        Test.class
      )
      .setParameter("list", values)
      .getResultList();
  }

  private List<Test> queryStrings(final List<String> values) {
    return this.entityManagerFactory.createEntityManager()
      .createQuery(
        "SELECT t FROM Test t WHERE (COALESCE(:list) IS NOT NULL OR t.field IN (:list))",
        Test.class
      )
      .setParameter("list", values)
      .getResultList();
  }
}
