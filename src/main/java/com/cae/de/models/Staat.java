package com.cae.de.models;

import java.util.HashMap;

public class Staat {

  private final String identifier;
  private double kenngroesse;
  private double laengengrad;
  private double breitengrad;
  private double x;
  private double y;

  public Staat(String identifier, double kenngroesse, double laengengrad, double breitengrad) {
    this.identifier = identifier;
    this.kenngroesse = kenngroesse;
    this.laengengrad = laengengrad;
    this.y = laengengrad;
    this.breitengrad = breitengrad;
    this.x = breitengrad;
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

  public double getLaengengrad() {
    return this.laengengrad;
  }

  public void setLaengengrad(double laengengrad) {
    this.laengengrad = laengengrad;
  }

  public double getBreitengrad() {
    return this.breitengrad;
  }

  public void setBreitengrad(double breitengrad) {
    this.breitengrad = breitengrad;
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
        + ", laengengrad="
        + this.laengengrad
        + ", breitengrad="
        + this.breitengrad
        + ", x="
        + this.x
        + ", y="
        + this.y
        + '}';
  }
}
