package com.cae.de.utils.la;

import java.util.HashSet;

/**
 * Record Punkt für Berechnungen.
 * @param x die x Koordinate
 * @param y die y Koordinate
 */
public record Punkt(double x, double y) {

  public static Punkt getMittelpunkt(HashSet<Punkt> punkte) {
    var x = 0.0;
    var y = 0.0;
    for (var punkt: punkte) {
      x+= punkt.x;
      y+= punkt.y;
    }
    return new Punkt(x / punkte.size(), y / punkte.size());
  }

  public Punkt verschiebeInRichtung(Punkt p, double wert) {
    return this.addiere(
        new Punkt(p.x - this.x, p.y - this.y)
            .normalisiere()
            .multipliziereMitSkalar(wert)
    );
  }

  public Punkt verschiebeInGegenrichtung(Punkt p, double wert) {
    return this.subtrahiere(
        new Punkt(p.x - this.x, p.y - this.y)
            .normalisiere()
            .multipliziereMitSkalar(wert)
    );
  }

  public double getAbstand(Punkt p) {
    return new Punkt(p.x - this.x,p.y - this.y).getBetrag();
  }

  private Punkt subtrahiere(Punkt p) {
    return new Punkt(this.x - p.x, this.y - p.y);
  }

  private Punkt normalisiere() {
    return new Punkt(this.x / this.getBetrag(), this.y / this.getBetrag());
  }

  private Punkt multipliziereMitSkalar(double skalar) {
    return new Punkt(this.x * skalar, this.y * skalar);
  }

  private Punkt addiere(Punkt p) {
    return new Punkt(this.x + p.x, this.y + p.y);
  }

  private double getSkalarprodukt(Punkt p) {
    return this.x * p.x + this.y * p.y;
  }

  private double getBetrag() {
    return Math.sqrt(this.x * this.x + this.y * this.y);
  }

  @Override
  public String toString() {
    return "Punkt{" +
        "x=" + String.format("%.2f", this.x) +
        ", y=" + String.format("%.2f", this.y) +
        '}';
  }
}
