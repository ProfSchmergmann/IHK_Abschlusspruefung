package com.cae.de.framework;

import java.nio.file.Path;

/**
 * Interface zur Darstellung eines Threads zum Lesen von Daten.
 *
 * @param <T> das zu lesende Objekt
 */
public interface ReadRunnable<T> extends Runnable {

  /**
   * Zu implementierende Methode, welche (aus einer Datei) ein Objekt einlesen soll.
   *
   * @param pathToFile der Pfad zur Datei, wenn der Input aus einer Datei kommt
   * @return das gelesene Objekt
   */
  T read(Path pathToFile);
}
