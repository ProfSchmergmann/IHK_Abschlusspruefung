package com.cae.de.utils.io;

/**
 * Interface for readers.
 *
 * @param <T> the object where the input should be parsed to
 */
public interface Reader<T> {

  /**
   * Reads a specific object from a given source.
   *
   * @param pathToFile the path to file if needed
   * @return the object
   */
  T readObject(String pathToFile);

  /**
   * Reads the whole given source.
   *
   * @param pathToFile the path to the file if needed.
   * @return the file in a string representation
   */
  String read(String pathToFile);
}
