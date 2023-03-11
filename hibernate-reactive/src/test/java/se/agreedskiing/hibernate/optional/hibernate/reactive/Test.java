package se.agreedskiing.hibernate.optional.hibernate.reactive;

import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(name = "test")
public class Test {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  public String field; //NOSONAR

  public UUID uu; //NOSONAR
}
