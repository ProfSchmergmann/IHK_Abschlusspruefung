package com.cae.de.framework;

/**
 * Interface zur Darstellung eines Threads zum Schreiben von Daten.
 *
 * @param <T> das zu schreibende Objekt
 */
public interface WriteRunnable<T> extends Runnable {

  /**
   * Zu implementierende Methode, welche ein Objekt (in eine Datei) schreibt.
   *
   * @param t das zu schreibende Objekt
   * @return true, falls es geklappt hat, false andernfalls
   */
  boolean write(T t);
}
