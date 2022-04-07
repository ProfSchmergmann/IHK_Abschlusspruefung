package com.cae.de.utils.la;

import java.util.HashSet;

/**
 * Record Punkt für Berechnungen aus der linearen Algebra.
 * @param x die x Koordinate
 * @param y die y Koordinate
 */
public record Punkt(double x, double y) {

  /**
   * Berechnet den Mittelpunkt einer gegebenen Menge von Punkten.
   * @param punkte die Menge der Punkte
   * @return den Mittelpunkt
   */
  public static Punkt getMittelpunkt(HashSet<Punkt> punkte) {
    var x = 0.0;
    var y = 0.0;
    for (var punkt: punkte) {
      x+= punkt.x;
      y+= punkt.y;
    }
    return new Punkt(x / punkte.size(), y / punkte.size());
  }

  /**
   * Verschiebt den Punkt in die Richtung des gegebenen Punktes um den Wert.
   * @param p der Punkt in welche Richtung der andere Punkt verschoben werden soll
   * @param wert der Wert, um den der Punkt verschoben werden soll
   * @return den aus der Verschiebung resultierenden Punkt
   */
  public Punkt verschiebeInRichtung(Punkt p, double wert) {
    return this.addiere(
        new Punkt(p.x - this.x, p.y - this.y)
            .normalisiere()
            .multipliziereMitSkalar(wert)
    );
  }

  /**
   * Verschiebt den Punkt in die Gegenrichtung des gegebenen Punktes um den Wert.
   * @param p der Punkt gegen welche Richtung der andere Punkt verschoben werden soll
   * @param wert der Wert, um den der Punkt verschoben werden soll
   * @return den aus der Verschiebung resultierenden Punkt
   */
  public Punkt verschiebeInGegenrichtung(Punkt p, double wert) {
    return this.subtrahiere(
        new Punkt(p.x - this.x, p.y - this.y)
            .normalisiere()
            .multipliziereMitSkalar(wert)
    );
  }

  /**
   * Berechnet den euklidischen Abstand beider Punkte.
   * @param p der andere Punkt
   * @return den euklidischen Abstand
   */
  public double getAbstand(Punkt p) {
    return new Punkt(p.x - this.x,p.y - this.y).getBetrag();
  }

  /**
   * Berechnet die Differenz zweier Punkte, oder auch den Vektor.
   * @param p der andere Punkt
   * @return der resultierende Vektor
   */
  private Punkt subtrahiere(Punkt p) {
    return new Punkt(this.x - p.x, this.y - p.y);
  }

  /**
   * Normalisiert den Punkt auf die Länge 1.
   * @return der normalisierte Punkt
   */
  private Punkt normalisiere() {
    return new Punkt(this.x / this.getBetrag(), this.y / this.getBetrag());
  }

  /**
   * Multipliziert den Punkt mit einem Skalar.
   * @param skalar der Wert
   * @return der resultierende Punkt
   */
  private Punkt multipliziereMitSkalar(double skalar) {
    return new Punkt(this.x * skalar, this.y * skalar);
  }

  /**
   * Berechnet die Addition zweier Punkte, oder auch den Vektor.
   * @param p der andere Punkt
   * @return der resultierende Vektor
   */
  private Punkt addiere(Punkt p) {
    return new Punkt(this.x + p.x, this.y + p.y);
  }


  /**
   * Berechnet den euklidischen Betrag zweier Punkte.
   * @return der euklidische Betrag
   */
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
