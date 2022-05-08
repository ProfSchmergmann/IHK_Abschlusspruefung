package com.cae.de.framework;

import java.util.List;

/**
 * Abstrakte Producer Klasse, welche nach dem EVA-Prinzip die Eingabe widerspiegelt.
 *
 * @param <P> das zu lesende Objekt
 */
public abstract class Producer<P> {

  /**
   * Methode zum Einlesen der Objekte. Falls aus einem Ordner gelesen werden soll, sollte noch ein
   * zusätzlicher Konstruktor implementiert werden, welcher den Pfad zum Ordner setzt.
   *
   * @return eine Liste der Objekte die eingelesen wurden, welche auch leer sein, oder nur aus einem
   *     Element bestehen kann
   * @throws EVAException selbst geschriebene Exception, falls intern eine {@link
   *     java.io.IOException} passiert, um alles in der {@link com.cae.de.Main} Klasse behandeln zu
   *     können.
   */
  public abstract List<P> readToList() throws EVAException;
}
