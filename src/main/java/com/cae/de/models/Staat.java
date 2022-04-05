package com.cae.de.models;

import java.util.HashMap;
import java.util.HashSet;

public class Staat {

  private final String identifier;
  private final double kenngroesse;
  private final HashSet<String> nachbarn;
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
    this.nachbarn = new HashSet<>();
    this.abstossungsKreafte = new HashMap<>();
    this.anziehungsKreafte = new HashMap<>();
    this.laengengrad = laengengrad;
    this.y = laengengrad;
    this.breitengrad = breitengrad;
    this.x = breitengrad;
    this.r = kenngroesse;
  }

  public HashSet<String> getNachbarn() {
    return this.nachbarn;
  }

  public boolean addNachbar(String identifier) {
    return this.nachbarn.add(identifier);
  }

  public double getR() {
    return this.r;
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

  @Override
  public String toString() {
    return "Staat{"
        + "identifier='"
        + this.identifier
        + '\''
        + ", kenngroesse="
        + this.kenngroesse
        + ", nachbarn="
        + this.nachbarn
        + ", abstossungsKreafte="
        + this.abstossungsKreafte
        + ", anziehungsKreafte="
        + this.anziehungsKreafte
        + ", laengengrad="
        + this.laengengrad
        + ", breitengrad="
        + this.breitengrad
        + ", x="
        + this.x
        + ", y="
        + this.y
        + ", r="
        + this.r
        + '}';
  }
}
