package com.cae.de.framework;

/**
 * Simples Observer Interface zur implementierung des Observer-Observable Musters.
 *
 * @param <T> der Typ, der als Meldung vom Observable kommt
 */
public interface Observer<T> {

  /**
   * Methode um diesen Observer über Änderungen zu informieren.
   *
   * @param t das Objekt was geändert wurde und worüber informiert werden soll
   */
  void update(T t);
}
