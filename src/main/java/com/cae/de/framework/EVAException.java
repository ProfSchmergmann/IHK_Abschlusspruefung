package com.cae.de.framework;

/**
 * Selbst geschriebene EVAException, um eine Exception in der Eingabe-Verarbeitung-Ausgabe Kette
 * darzustellen.
 */
public class EVAException extends Exception {

  /**
   * Konstruktor, welcher nur super aufruft.
   *
   * @param message die Nachricht
   */
  public EVAException(String message) {
    super(message);
  }
}
