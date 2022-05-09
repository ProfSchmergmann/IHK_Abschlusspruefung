package com.cae.de.problem;

public class AutoKorrelationsFunktion {

  private String fileName;
  private float fwhm;
  private int indexL;
  private int indexR;
  private double b;
  private double[] xTransformiert;
  private double[] yNormiert;
  private double[] obereEinhuellende;

  public String getFileName() {
    return this.fileName;
  }

  public float getFwhm() {
    return this.fwhm;
  }

  public int getIndexL() {
    return this.indexL;
  }

  public int getIndexR() {
    return this.indexR;
  }

  public double[] getObereEinhuellende() {
    return this.obereEinhuellende;
  }

  public double[] getxTransformiert() {
    return this.xTransformiert;
  }

  public double[] getyNormiert() {
    return this.yNormiert;
  }
}
