package com.cae.de.framework;

/**
 * Interface zur Darstellung eines Threads zur Verarbeitung von Daten.
 *
 * @param <P> der Typ des Parameters zur Verarbeitung
 * @param <R> der Typ des Ergebnisses aus der Verarbeitung
 */
public interface ProcessRunnable<P, R> extends Runnable {

  /**
   * Zu implementierende Methode, welche den Input zum Ergebnis verarbeiten soll.
   *
   * @param p der Input oder auch Parameter
   * @return das Ergebnis
   */
  R process(P p);
}
