package se.agreedskiing.hibernate.optional.hibernate.six;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "test")
public class Test {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  public String field; //NOSONAR

  public UUID uu; //NOSONAR
}
