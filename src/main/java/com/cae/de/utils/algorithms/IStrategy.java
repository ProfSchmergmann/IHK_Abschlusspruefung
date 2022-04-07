package com.cae.de.utils.algorithms;

import com.cae.de.models.Landkarte;

/** Interface IStrategie, falls ein anderer Algorithmus genutzt werden soll. */
public interface IStrategy {

  /**
   * Soll die Mittelpunkte der Staaten neu berechnen.
   *
   * @param landkarte die Landkarte
   * @param maxIterationen die maximalen Iterationen bis zum Abbruch
   */
  void rechne(Landkarte landkarte, int maxIterationen);
}
