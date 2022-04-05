package com.cae.de.utils.algorithms;

import com.cae.de.models.Landkarte;
import java.util.logging.Logger;

/** Eine erste Strategie, welche wahrscheinlich noch nicht optimal ist, deshalb der Name. */
public class BruteForceStrategy implements IStrategy {

  private static final Logger LOGGER = Logger.getLogger(BruteForceStrategy.class.getName());

  @Override
  public void rechne(Landkarte landkarte) {}

  public String toString() {
    return this.getClass().getName();
  }
}
