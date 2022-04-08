package com.cae.de.utils.la;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Record Kreis für eine primitive Abstandsberechnung.
 * @param p der Mittelpunkt
 * @param r der Radius
 */
public record Kreis(Punkt p, double r) {

  private static final Logger LOGGER = Logger.getLogger(Kreis.class.getName());

  /**
   * Berechnet den Abstand beider Kreise
   * @param k der andere Kreis
   * @return einen positiven Abstand, falls die Kreise sich nicht schneiden,
   * andernfalls ist der Abstand negativ und repräsentiert die Strecke der größten Überschneidung.
   */
  public double getAbstandZwischenKreisen(Kreis k) {
    return new Punkt(k.p().x(), k.p().y()).getAbstand(this.p) - (this.r + k.r);
  }

  /**
   * Berechnet, ob der gegebene Punkt innerhalb des Kreises liegt.
   * @param p der Punkt
   * @return true wenn er innerhalb liegt, andernfalls false
   */
  public boolean isInnerhalb(Punkt p) {
    return p.getAbstand(this.p) < this.r;
  }

}
