package com.cae.de.models;

public class Staat {

  private final String identifier;
  private double kenngroesse;
  private double x;
  private double y;

  public Staat(String identifier, double kenngroesse, double laengengrad, double breitengrad) {
    this.identifier = identifier;
    this.kenngroesse = kenngroesse;
    this.x = laengengrad;
    this.y = breitengrad;
  }

  public Staat(Staat s) {
    this.identifier = s.identifier;
    this.kenngroesse = s.kenngroesse;
    this.x = s.x;
    this.y = s.y;
  }

  public String getIdentifier() {
    return this.identifier;
  }

  public double getKenngroesse() {
    return this.kenngroesse;
  }

  public void setKenngroesse(double kenngroesse) {
    this.kenngroesse = kenngroesse;
  }

  public double getX() {
    return this.x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return this.y;
  }

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
