package com.cae.de.utils.la;

/**
 * Record Punkt für Berechnungen.
 * @param x die x Koordinate
 * @param y die y Koordinate
 */
public record Punkt(double x, double y) {

  public Punkt verschiebeInRichtung(Punkt p, double wert) {
    return this.addiere(
        new Punkt(p.x - this.x, p.y - this.y)
            .normalisiere()
            .multipliziereMitSkalar(wert)
    );
  }

  public Punkt verschiebeInGegenrichtung(Punkt p, double wert) {
    return this.addiere(
        new Punkt(this.x - p.x, this.y - p.y)
            .normalisiere()
            .multipliziereMitSkalar(wert)
    );
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
        "x=" + this.x +
        ", y=" + this.y +
        '}';
  }
}
