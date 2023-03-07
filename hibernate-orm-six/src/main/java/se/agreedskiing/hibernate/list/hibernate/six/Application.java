package se.agreedskiing.hibernate.list.hibernate.six;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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

  public List<Test> listContaingValues() {
    return query(List.of(1L, 2L, 3L));
  }

  public List<Test> listEmpty() {
    return query(Collections.emptyList());
  }

  public List<Test> listNull() {
    return query(null);
  }

  private List<Test> query(final List<Long> ids) {
    return this.entityManagerFactory.createEntityManager()
      .createQuery(
        //"SELECT t FROM Test t WHERE (COALESCE(:list, NULL) IS NOT NULL OR t.id IN (:list))", //RUN 1
        "SELECT t FROM Test t WHERE (COALESCE(:list, NULL) IS NOT NULL OR t.id IN (:list))", //RUN 2
        Test.class
      )
      .setParameter("list", ids)
      .getResultList();
  }
}
