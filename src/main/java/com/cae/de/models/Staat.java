package com.cae.de.models;

import java.util.HashMap;

public class Staat {

  private final String identifier;
  private final double kenngroesse;
  private final HashMap<String, Double> abstossungsKreafte;
  private final HashMap<String, Double> anziehungsKreafte;
  private double laengengrad;
  private double breitengrad;
  private double x;
  private double y;
  private double r;

  public Staat(String identifier, double kenngroesse, double laengengrad, double breitengrad) {
    this.identifier = identifier;
    this.kenngroesse = kenngroesse;
    this.abstossungsKreafte = new HashMap<>();
    this.anziehungsKreafte = new HashMap<>();
    this.laengengrad = laengengrad;
    this.y = laengengrad;
    this.breitengrad = breitengrad;
    this.x = breitengrad;
  }

  public double getR() {
    return this.kenngroesse;
    //    return this.r;
  }

  public void setR(double r) {
    this.r = r;
  }

  public String getIdentifier() {
    return this.identifier;
  }

  public double getKenngroesse() {
    return this.kenngroesse;
  }

  public HashMap<String, Double> getAbstossungsKreafte() {
    return this.abstossungsKreafte;
  }

  public HashMap<String, Double> getAnziehungsKreafte() {
    return this.anziehungsKreafte;
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
}
