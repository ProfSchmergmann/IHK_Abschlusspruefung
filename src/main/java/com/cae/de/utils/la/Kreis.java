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
    var dM1M2 = Math.sqrt((k.x - this.x) * (k.x - this.x) + (k.y - this.y) * (k.y - this.y));
    if (dM1M2 < (k.r + this.r)) return -dM1M2;
    return dM1M2 - (this.r + k.r);
  }

  /**
   * Berechnet ob der gegebene Punkt innerhalb des Kreises liegt.
   * @param p der Punkt
   * @return true wenn er innerhalb liegt, andernfalls false
   */
  public boolean isInnerhalb(Punkt p) {
    return p.getAbstand(new Punkt(this.x, this.y)) <= this.r;
  }

}
