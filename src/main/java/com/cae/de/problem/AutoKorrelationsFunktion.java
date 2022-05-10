package com.cae.de.problem;

/**
 * Datenklasse AutoKorrelationsFunktion, die genutzt wird um die Daten aus der Berechnung weiterzugeben.
 * @param fileName der Name der Eingabedatei
 * @param fwhm die Pulsbreite b, auch (FWHM - full width at half maximum) genannt
 * @param indexL der Index von L (s. Pulsbreite)
 * @param indexR der Index von R (s. Pulsbreite)
 * @param xTransformiert das transformierte x-Array
 * @param yNormiert das normierte y-Array
 * @param obereEinhuellende die obere Einhüllende
 */
public record AutoKorrelationsFunktion(String fileName, float fwhm,
                                       int indexL, int indexR,
                                       double[] xTransformiert,
                                       double[] yNormiert,
                                       double[] obereEinhuellende) {

  /**
   * Methode, die einen String zur Ausgabe zusammenbaut,
   * welcher dann für das Python-Programm "FileViewer.py" lesbar ist.
   * @return den formatierten String
   */
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
