package se.agreedskiing.hibernate.list.hibernate.six;

public enum ErrorTexts {
  QUERY_FAILED("Query failed with an exception instead of working");

  public final String explenation;

  private ErrorTexts(final String exeplnation) {
    this.explenation = exeplnation;
  }
}
