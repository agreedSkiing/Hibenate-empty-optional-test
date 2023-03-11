package se.agreedskiing.hibernate.optional.hibernate.five;

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
