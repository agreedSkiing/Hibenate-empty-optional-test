package se.agreedskiing.hibernate.list.hibernate.reactive;

import io.smallrye.mutiny.Uni;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.persistence.Persistence;
import org.hibernate.reactive.mutiny.Mutiny;
import se.agreedskiing.hibernate.list.hibernate.reactive.entities.Test;

public class Application {

  public static void main(final String[] args) {
    //NO use currently
  }

  public Uni<List<Test>> listContaingValues(
    final String persistenceUnit,
    final int port
  ) {
    return query(persistenceUnit, port, List.of(1L, 2L, 3L));
  }

  public Uni<List<Test>> listEmpty(
    final String persistenceUnit,
    final int port
  ) {
    return query(persistenceUnit, port, Collections.emptyList());
  }

  public Uni<List<Test>> listNull(
    final String persistenceUnit,
    final int port
  ) {
    return query(persistenceUnit, port, null);
  }

  private Uni<List<Test>> query(
    final String persistenceUnit,
    final int port,
    final List<Long> values
  ) {
    return Persistence
      .createEntityManagerFactory(
        persistenceUnit,
        Map.of(
          "javax.persistence.jdbc.url",
          "jdbc:postgresql://localhost:" + port + "/hibernate_reactive"
        )
      )
      .unwrap(Mutiny.SessionFactory.class)
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
}
