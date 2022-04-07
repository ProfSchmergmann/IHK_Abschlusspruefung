package com.cae.de.models;

/**
 * Klasse Staat mit den Properties {@link #identifier}, {@link #kenngroesse}, {@link #x} und {@link
 * #y}.
 */
public class Staat {

  private final String identifier;
  private double kenngroesse;
  private double x;
  private double y;

  /**
   * Konstruktor, welcher den Längengrad direkt auf den x-Wert mappt und den Breitengrad direkt auf
   * den y-Wert.
   *
   * @param identifier der Identifier oder das Autokennzeichen
   * @param kenngroesse der Wert der Kenngröße
   * @param laengengrad der Längengrad, oder x
   * @param breitengrad der Breitengrad, oder y
   */
  public Staat(String identifier, double kenngroesse, double laengengrad, double breitengrad) {
    this.identifier = identifier;
    this.kenngroesse = kenngroesse;
    this.x = laengengrad;
    this.y = breitengrad;
  }

  /**
   * Copy Konstruktor.
   *
   * @param s der Staat zum kopieren.
   */
  public Staat(Staat s) {
    this.identifier = s.identifier;
    this.kenngroesse = s.kenngroesse;
    this.x = s.x;
    this.y = s.y;
  }

  /**
   * Getter für den Identifier
   * @return den Identifier
   */
  public String getIdentifier() {
    return this.identifier;
  }

  /**
   * Getter für die Kenngröße.
   * @return die Kenngröße
   */
  public double getKenngroesse() {
    return this.kenngroesse;
  }

  /**
   * Setter für die Kenngröße.
   * @param kenngroesse die Kenngröße
   */
  public void setKenngroesse(double kenngroesse) {
    this.kenngroesse = kenngroesse;
  }

  /**
   * Getter für den x-Wert.
   * @return den x-Wert
   */
  public double getX() {
    return this.x;
  }

  /**
   * Setter für den x-Wert.
   * @param x der x-Wert
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * Getter für den y-Wert.
   * @return den x-Wert
   */
  public double getY() {
    return this.y;
  }

  /**
   * Setter für den y-Wert.
   * @param y der y-Wert
   */
  public void setY(double y) {
    this.y = y;
  }

  @Override
  public String toString() {
    return "Staat{"
        + "identifier='"
        + this.identifier
        + '\''
        + ", kenngroesse="
        + this.kenngroesse
        + ", x="
        + this.x
        + ", y="
        + this.y
        + '}';
  }
}
