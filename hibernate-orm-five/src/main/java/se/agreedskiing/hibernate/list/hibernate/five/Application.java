package se.agreedskiing.hibernate.list.hibernate.five;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import se.agreedskiing.hibernate.list.hibernate.five.entities.Test;

public class Application {

  EntityManagerFactory entityManagerFactory;

  public Application(final String persitanceUnit, final int port) {
    entityManagerFactory =
      Persistence.createEntityManagerFactory(
        persitanceUnit,
        Map.of(
          "javax.persistence.jdbc.url",
          "jdbc:postgresql://localhost:" + port + "/hibernate_orm_five"
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
    //Many ideas from https://stackoverflow.com/questions/2488930/passing-empty-list-as-parameter-to-jpa-query-throws-error
    //Idea from https://www.baeldung.com/spring-data-jpa-null-parameters
    return this.entityManagerFactory.createEntityManager()
      .createQuery(
        "SELECT t FROM Test t WHERE (COALESCE(:list) IS NOT NULL OR t.id IN (:list))", // WORKS
        Test.class
      )
      .setParameter("list", ids)
      .getResultList();
  }
}
