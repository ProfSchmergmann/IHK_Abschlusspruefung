package com.cae.de.problem;

public class AutoKorrelationsFunktion {

  private String fileName;
  private float fwhm;
  private int indexL;
  private int indexR;
  private double b;
  private double[] xTransformiert;
  private double[] yNormiert;
  private double obereEinhuellende;

  public String getFileName() {
    return this.fileName;
  }
}
