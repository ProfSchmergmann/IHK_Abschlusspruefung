package com.cae.de.framework;

/**
 * Abstrakte Consumer Klasse, welche nach dem EVA-Prinzip die Ausgabe widerspiegelt.
 *
 * @param <D> das zu schreibende Objekt
 */
public interface IConsumer<D> {

  /**
   * Methode zur Ausgabe des übergebenen Objektes. Intern kann je nach gewünschter Implementierung
   * die Ausgabe in eine Datei o. ä. erfolgen.
   *
   * @param data das Objekt, welches ausgegeben werden soll
   * @return true, falls es geklappt hat, andernfalls false
   * @throws EVAException selbst geschriebene Exception, falls intern eine {@link
   *     java.io.IOException} passiert, um alles in der {@link com.cae.de.Main} Klasse behandeln zu
   *     können.
   */
 boolean write(D data) throws EVAException;
}
