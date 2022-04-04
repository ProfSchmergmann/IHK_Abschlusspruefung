package com.cae.de.utils.algorithms;

import com.cae.de.models.Landkarte;

/** Eine erste Strategie, welche wahrscheinlich noch nicht optimal ist, deshalb der Name. */
public class BruteForceStrategy implements IStrategy {

  @Override
  public void rechne(Landkarte landkarte) {}

  public String toString() {
    return this.getClass().getName();
  }
}
