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
}
