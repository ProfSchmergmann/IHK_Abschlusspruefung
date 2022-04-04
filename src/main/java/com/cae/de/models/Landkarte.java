package com.cae.de.models;

import com.cae.de.utils.algorithms.IStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Klasse zur Representation aller gegebenen Staaten und deren Beziehungen. */
public class Landkarte {

  private final ArrayList<Staat> staaten;
  private final String kenngroesse;
  private final HashMap<String, HashSet<String>> beziehungen;
  private final IStrategy strategy;
  private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

  /**
   * Konstruktor, welcher die Liste der Staaten, die Kenngröße, die Beziehungen und die Strategie
   * setzt.
   *
   * @param staaten die Staaten, welche vorher eingelesen werden
   * @param kenngroesse die Kenngröße (z. B.: Bierkonsum)
   * @param beziehungen die Nachbarschaftsbeziehungen
   * @param strategy die Strategie für die Bewegung der Mittelpunkte
   */
  public Landkarte(
      ArrayList<Staat> staaten,
      String kenngroesse,
      HashMap<String, HashSet<String>> beziehungen,
      IStrategy strategy) {
    this.staaten = staaten;
    this.kenngroesse = kenngroesse;
    this.beziehungen = beziehungen;
    this.strategy = strategy;
    LOGGER.log(
        Level.INFO,
        "Initialized new Landkarte with following properties:\n"
            + "Staaten: "
            + this.staaten
            + "\n"
            + "Kenngröße: "
            + this.kenngroesse
            + " \n"
            + "Beziehungen: "
            + this.beziehungen
            + "\n"
            + "Strategie: "
            + this.strategy);
  }

  /**
   * Rechnet mit der gegebenen Strategie und der Anzahl an Iterationen
   *
   * @param iterationen die Anzahl der Iterationen
   */
  public void rechne(int iterationen) {
    for (var i = 0; i < iterationen; i++) {
      LOGGER.log(Level.INFO, "Doing " + i + " Iteration.");
      this.strategy.rechne(this);
    }
    LOGGER.log(Level.INFO, "Finished computing " + iterationen + " Iterations.");
  }
}
