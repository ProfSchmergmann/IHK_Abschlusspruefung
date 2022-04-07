package com.cae.de.utils.io;

/**
 * Interface für Reader.
 *
 * @param <T> das Objekt, welches eingelesen werden soll
 */
public interface IReader<T> {

  /**
   * Liest ein Objekt aus der gegebenen Datei.
   *
   * @param pathToFile der Pfad zur Datei
   * @return das Objekt
   */
  T readObject(String pathToFile);

  /**
   * Liest einen kompletten String aus der gegebenen Datei.
   *
   * @param pathToFile der Pfad zur Datei
   * @return die Datei als String Representation
   */
  String read(String pathToFile);
}
