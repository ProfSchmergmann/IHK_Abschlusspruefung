package com.cae.de.utils.io;

/**
 * Interface für Writer.
 *
 * @param <T> das zu schreibende Objekt
 */
public interface IWriter<T> {

  /**
   * Schreibt ein gegebenes Objekt
   *
   * @param t das zu schreibende Objekt
   * @param pathToFile der Pfad zur Datei
   * @return true falls es geklappt hat, false andernfalls
   */
  boolean write(T t, String pathToFile);

  /**
   * Schreibt ein Objekt zu einem vordefinierten Output-Pfad.
   *
   * @param t das zu schreibende Objekt
   * @return true falls es geklappt hat, false andernfalls
   */
  boolean write(T t);
}
