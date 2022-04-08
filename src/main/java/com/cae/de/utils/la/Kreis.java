package com.cae.de.utils.la;

/**
 * Record Kreis für eine primitive Abstandsberechnung.
 * @param x der x Wert des Mittelpunktes
 * @param y der y Wert des Mittelpunktes
 * @param r der Radius
 */
public record Kreis(double x, double y, double r) {

  /**
   * Berechnet den Abstand beider Kreise
   * @param k der andere Kreis
   * @return einen positiven Abstand, falls die Kreise sich nicht schneiden,
   * andernfalls ist der Abstand negativ und repräsentiert die Strecke der größten Überschneidung.
   */
  public double getAbstandZwischenKreisen(Kreis k) {
    return new Punkt(k.x, k.y).getAbstand(new Punkt(this.x, this.y)) - (this.r + k.r);
  }

  /**
   * Berechnet, ob der gegebene Punkt innerhalb des Kreises liegt.
   * @param p der Punkt
   * @return true wenn er innerhalb liegt, andernfalls false
   */
  public boolean isInnerhalb(Punkt p) {
    return p.getAbstand(new Punkt(this.x, this.y)) < this.r;
  }

}
