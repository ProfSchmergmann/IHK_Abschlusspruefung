package com.cae.de.problem;

public record AutoKorrelationsFunktion(String fileName, float fwhm,
                                       int indexL, int indexR,
                                       double[] xTransformiert,
                                       double[] yNormiert,
                                       double[] obereEinhuellende) {

  public String getOutputString() {
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
        .append("\t")
        .append(this.yNormiert[i])
        .append("\t")
        .append(this.obereEinhuellende[i]);
    }
    return sb.toString();
  }
}
