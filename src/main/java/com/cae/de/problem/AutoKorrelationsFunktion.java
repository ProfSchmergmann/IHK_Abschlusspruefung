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

  public String toString() {
    var sb = new StringBuilder();
    sb.append("# FWHM = ")
        .append(this.fwhm)
        .append(", ")
        .append(this.indexL)
        .append(", ")
        .append(this.indexR)
        .append("\n# pos int\tenv");
    for (var i = 0; i < this.xTransformiert.length; i++) {
      sb.append("\n")
          .append(this.xTransformiert[i])
          .append(" ")
          .append(this.yNormiert[i])
          .append(" ")
          .append(this.obereEinhuellende[i]);
    }
    return sb.toString();
  }
}
