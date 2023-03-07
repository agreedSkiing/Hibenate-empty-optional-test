package se.agreedskiing.hibernate.list.hibernate.reactive.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test")
public class Test {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;
}
