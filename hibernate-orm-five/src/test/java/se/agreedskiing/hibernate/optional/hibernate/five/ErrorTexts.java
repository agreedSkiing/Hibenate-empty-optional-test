package se.agreedskiing.hibernate.optional.hibernate.five;

public enum ErrorTexts {
  QUERY_FAILED("Query failed with an exception instead of working");

  public final String explenation;

  private ErrorTexts(final String exeplnation) {
    this.explenation = exeplnation;
  }
}
