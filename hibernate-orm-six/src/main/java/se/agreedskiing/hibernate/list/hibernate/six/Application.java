package se.agreedskiing.hibernate.list.hibernate.six;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.ArrayList;
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

  public List<Test> listContaingLongs(final boolean testOptionalQuery) {
    final var list = new ArrayList<Long>();
    list.add(1L);
    list.add(2L);
    list.add(3L);
    list.add(4L);
    if (testOptionalQuery) {
      return queryOptionalLongs(list);
    } else {
      return queryLongs(list);
    }
  }

  public List<Test> emptyListOfLongs(final boolean testOptionalQuery) {
    final var list = new ArrayList<Long>();
    if (testOptionalQuery) {
      return queryOptionalLongs(list);
    } else {
      return queryLongs(list);
    }
  }

  public List<Test> nullListOfLongs(final boolean testOptionalQuery) {
    if (testOptionalQuery) {
      return queryOptionalLongs(null);
    } else {
      return queryLongs(null);
    }
  }

  public List<Test> listContaingUUIDs(final boolean testOptionalQuery) {
    final var list = new ArrayList<UUID>();
    list.add(UUID.fromString("40e6215d-b5c6-4896-987c-f30f3678f608"));
    list.add(UUID.fromString("6ecd8c99-4036-403d-bf84-cf8400f67836"));
    list.add(UUID.fromString("6366d53a-c35c-41b2-90ce-6c43b73490d9"));
    list.add(UUID.fromString("37a55e88-5794-48d2-9805-0a44e6edb0b3"));
    if (testOptionalQuery) {
      return queryOptionalUUIDs(list);
    } else {
      return queryUUIDs(list);
    }
  }

  public List<Test> emptyListOfUUIDs(final boolean testOptionalQuery) {
    final var list = new ArrayList<UUID>();
    if (testOptionalQuery) {
      return queryOptionalUUIDs(list);
    } else {
      return queryUUIDs(list);
    }
  }

  public List<Test> nullListOfUUIDs(final boolean testOptionalQuery) {
    if (testOptionalQuery) {
      return queryOptionalUUIDs(null);
    } else {
      return queryUUIDs(null);
    }
  }

  public List<Test> listContaingStrings(final boolean testOptionalQuery) {
    final var list = new ArrayList<String>();
    list.add("test1");
    list.add("test2");
    list.add("test3");
    list.add("test4");
    if (testOptionalQuery) {
      return queryOptionalStrings(list);
    } else {
      return queryStrings(list);
    }
  }

  public List<Test> emptyListOfStrings(final boolean testOptionalQuery) {
    final var list = new ArrayList<String>();
    if (testOptionalQuery) {
      return queryOptionalStrings(list);
    } else {
      return queryStrings(list);
    }
  }

  public List<Test> nullListOfStrings(final boolean testOptionalQuery) {
    if (testOptionalQuery) {
      return queryOptionalStrings(null);
    } else {
      return queryStrings(null);
    }
  }

  private List<Test> queryOptionalLongs(final List<Long> values) {
    //Many ideas from https://stackoverflow.com/questions/2488930/passing-empty-list-as-parameter-to-jpa-query-throws-error
    //Idea from https://www.baeldung.com/spring-data-jpa-null-parameters
    return this.entityManagerFactory.createEntityManager()
      .createQuery(
        "SELECT t FROM Test t WHERE (COALESCE(:list) IS NOT NULL OR t.id IN (:list))", // WORKS
        Test.class
      )
      .setParameter("list", values)
      .getResultList();
  }

  private List<Test> queryLongs(final List<Long> values) {
    return this.entityManagerFactory.createEntityManager()
      .createQuery("SELECT t FROM Test t WHERE t.id IN (:list)", Test.class)
      .setParameter("list", values)
      .getResultList();
  }

  private List<Test> queryOptionalUUIDs(final List<UUID> values) {
    return this.entityManagerFactory.createEntityManager()
      .createQuery(
        "SELECT t FROM Test t WHERE (COALESCE(:list) IS NOT NULL OR t.uu IN (:list))",
        Test.class
      )
      .setParameter("list", values)
      .getResultList();
  }

  private List<Test> queryUUIDs(final List<UUID> values) {
    return this.entityManagerFactory.createEntityManager()
      .createQuery("SELECT t FROM Test t WHERE t.uu IN (:list)", Test.class)
      .setParameter("list", values)
      .getResultList();
  }

  private List<Test> queryOptionalStrings(final List<String> values) {
    return this.entityManagerFactory.createEntityManager()
      .createQuery(
        "SELECT t FROM Test t WHERE (COALESCE(:list) IS NOT NULL OR t.field IN (:list))",
        Test.class
      )
      .setParameter("list", values)
      .getResultList();
  }

  private List<Test> queryStrings(final List<String> values) {
    return this.entityManagerFactory.createEntityManager()
      .createQuery("SELECT t FROM Test t WHERE t.field IN (:list)", Test.class)
      .setParameter("list", values)
      .getResultList();
  }
}
