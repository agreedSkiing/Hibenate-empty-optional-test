package se.agreedskiing.hibernate.list.hibernate.reactive;

import io.smallrye.mutiny.Uni;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.persistence.Persistence;
import org.hibernate.reactive.mutiny.Mutiny;
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory;
import se.agreedskiing.hibernate.list.hibernate.reactive.entities.Test;

public class Application {

  public static void main(final String[] args) {
    //NO use currently
  }

  public Uni<List<Test>> listContaingLongs(
    final String persistenceUnit,
    final int port,
    final boolean testOptionalQuery
  ) {
    final var list = new ArrayList<Long>();
    list.add(1L);
    list.add(2L);
    list.add(3L);
    list.add(4L);
    if (testOptionalQuery) {
      return queryOptionalLongs(persistenceUnit, port, list);
    } else {
      return queryLongs(persistenceUnit, port, list);
    }
  }

  public Uni<List<Test>> emptyListOfLongs(
    final String persistenceUnit,
    final int port,
    final boolean testOptionalQuery
  ) {
    final var list = new ArrayList<Long>();
    if (testOptionalQuery) {
      return queryOptionalLongs(persistenceUnit, port, list);
    } else {
      return queryLongs(persistenceUnit, port, list);
    }
  }

  public Uni<List<Test>> nullListOfLongs(
    final String persistenceUnit,
    final int port,
    final boolean testOptionalQuery
  ) {
    if (testOptionalQuery) {
      return queryOptionalLongs(persistenceUnit, port, null);
    } else {
      return queryLongs(persistenceUnit, port, null);
    }
  }

  public Uni<List<Test>> listContaingUUIDs(
    final String persistenceUnit,
    final int port,
    final boolean testOptionalQuery
  ) {
    final var list = new ArrayList<UUID>();
    list.add(UUID.fromString("40e6215d-b5c6-4896-987c-f30f3678f608"));
    list.add(UUID.fromString("6ecd8c99-4036-403d-bf84-cf8400f67836"));
    list.add(UUID.fromString("6366d53a-c35c-41b2-90ce-6c43b73490d9"));
    list.add(UUID.fromString("37a55e88-5794-48d2-9805-0a44e6edb0b3"));
    if (testOptionalQuery) {
      return queryOptionalUUIDs(persistenceUnit, port, list);
    } else {
      return queryUUIDs(persistenceUnit, port, list);
    }
  }

  public Uni<List<Test>> emptyListOfUUIDs(
    final String persistenceUnit,
    final int port,
    final boolean testOptionalQuery
  ) {
    final var list = new ArrayList<UUID>();
    if (testOptionalQuery) {
      return queryOptionalUUIDs(persistenceUnit, port, list);
    } else {
      return queryUUIDs(persistenceUnit, port, list);
    }
  }

  public Uni<List<Test>> nullListOfUUIDs(
    final String persistenceUnit,
    final int port,
    final boolean testOptionalQuery
  ) {
    if (testOptionalQuery) {
      return queryOptionalUUIDs(persistenceUnit, port, null);
    } else {
      return queryUUIDs(persistenceUnit, port, null);
    }
  }

  public Uni<List<Test>> listContaingStrings(
    final String persistenceUnit,
    final int port,
    final boolean testOptionalQuery
  ) {
    final var list = new ArrayList<String>();
    list.add("test1");
    list.add("test2");
    list.add("test3");
    list.add("test4");
    if (testOptionalQuery) {
      return queryOptionalStrings(persistenceUnit, port, list);
    } else {
      return queryStrings(persistenceUnit, port, list);
    }
  }

  public Uni<List<Test>> emptyListOfStrings(
    final String persistenceUnit,
    final int port,
    final boolean testOptionalQuery
  ) {
    final var list = new ArrayList<String>();
    if (testOptionalQuery) {
      return queryOptionalStrings(persistenceUnit, port, list);
    } else {
      return queryStrings(persistenceUnit, port, list);
    }
  }

  public Uni<List<Test>> nullListOfStrings(
    final String persistenceUnit,
    final int port,
    final boolean testOptionalQuery
  ) {
    if (testOptionalQuery) {
      return queryOptionalStrings(persistenceUnit, port, null);
    } else {
      return queryStrings(persistenceUnit, port, null);
    }
  }

  private Uni<List<Test>> queryOptionalLongs(
    final String persistenceUnit,
    final int port,
    final List<Long> values
  ) {
    return createSessionFactory(persistenceUnit, port)
      .withSession(s ->
        s
          .createQuery(
            "SELECT t FROM Test t WHERE (COALESCE(:list) IS NOT NULL OR t.id IN (:list))",
            Test.class
          )
          .setParameter("list", values)
          .getResultList()
      );
  }

  private Uni<List<Test>> queryLongs(
    final String persistenceUnit,
    final int port,
    final List<Long> values
  ) {
    return createSessionFactory(persistenceUnit, port)
      .withSession(s ->
        s
          .createQuery("SELECT t FROM Test t WHERE t.id IN (:list)", Test.class)
          .setParameter("list", values)
          .getResultList()
      );
  }

  private Uni<List<Test>> queryOptionalUUIDs(
    final String persistenceUnit,
    final int port,
    final List<UUID> values
  ) {
    return createSessionFactory(persistenceUnit, port)
      .withSession(s ->
        s
          .createQuery(
            "SELECT t FROM Test t WHERE (COALESCE(:list) IS NOT NULL OR t.uu IN (:list))",
            Test.class
          )
          .setParameter("list", values)
          .getResultList()
      );
  }

  private Uni<List<Test>> queryUUIDs(
    final String persistenceUnit,
    final int port,
    final List<UUID> values
  ) {
    return createSessionFactory(persistenceUnit, port)
      .withSession(s ->
        s
          .createQuery("SELECT t FROM Test t WHERE t.uu IN :list", Test.class)
          .setParameter("list", values)
          .getResultList()
      );
  }

  private Uni<List<Test>> queryOptionalStrings(
    final String persistenceUnit,
    final int port,
    final List<String> values
  ) {
    return createSessionFactory(persistenceUnit, port)
      .withSession(s ->
        s
          .createQuery(
            "SELECT t FROM Test t WHERE (COALESCE(:list) IS NOT NULL OR t.field IN (:list))",
            Test.class
          )
          .setParameter("list", values)
          .getResultList()
      );
  }

  private Uni<List<Test>> queryStrings(
    final String persistenceUnit,
    final int port,
    final List<String> values
  ) {
    return createSessionFactory(persistenceUnit, port)
      .withSession(s ->
        s
          .createQuery(
            "SELECT t FROM Test t WHERE t.field IN (:list)",
            Test.class
          )
          .setParameter("list", values)
          .getResultList()
      );
  }

  private SessionFactory createSessionFactory(
    final String persistenceUnit,
    final int port
  ) {
    return Persistence
      .createEntityManagerFactory(
        persistenceUnit,
        Map.of(
          "javax.persistence.jdbc.url",
          "jdbc:postgresql://localhost:" + port + "/hibernate_reactive"
        )
      )
      .unwrap(Mutiny.SessionFactory.class);
  }
}
