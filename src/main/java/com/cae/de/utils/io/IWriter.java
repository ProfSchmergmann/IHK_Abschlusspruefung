package com.cae.de.utils.io;

/**
 * Interface for writers.
 *
 * @param <T> the object to be written
 */
public interface IWriter<T> {

  /**
   * Writes a given object.
   *
   * @param t the object to be written
   * @param pathToFile the path to the file if needed
   * @return true if it worked, else false
   */
  boolean write(T t, String pathToFile);

  /**
   * Writes a given object to a predefined output.
   *
   * @param t the object to be written
   * @return true if it worked, else false
   */
  boolean write(T t);
}
