package com.cae.de.problem;

import com.cae.de.utils.Pair;

/**
 * Klasse Algorithms um alle gegebenen Algorithmen der Aufgabenstellung als statische Methoden zu
 * implementieren und an einem Platz zu haben.
 */
public class Algorithms {

  /**
   * Statische öffentliche Methode, die ein Data Objekt bekommt, welches aus dem Dateinamen und
   * jeweils einem int-Array für x und y besteht. Das Objekt wird dann intern mit Hilfe der vier
   * Algorithmen, welche nacheinander aufgerufen werden, verarbeitet und gibt ein Objekt vom Typen
   * {@link AutoKorrelationsFunktion} zurück.
   *
   * @param data die Eingabedaten
   * @return eine Autokorrelationsfunktion
   */
  public static AutoKorrelationsFunktion solve(Data data) {
    var pair = alg1(data.xStart(), data.yStart());
    var x = alg2(pair.key());
    var oE = alg3(pair.value());
    var res = alg4(x, pair.value(), oE);
    return new AutoKorrelationsFunktion(
        data.fileName(), res.key(), res.value().key(), res.value().value(), x, pair.value(), oE);
  }

  /**
   * Implementierung des ersten Algorithmus, welcher die xWerte in Pikosekunden umrechnet und die
   * yWerte normiert.
   *
   * @param xStart das eingelesene Array der x Werte
   * @param yStart das eingelesene Array der y Werte
   * @return ein Paar von xDach und yNorm
   */
  private static Pair<double[], double[]> alg1(int[] xStart, int[] yStart) {
    var yMax = max(yStart);
    var xDach = new double[xStart.length];
    var yNorm = new double[yStart.length];
    var alpha = 266.3 / (Math.pow(2, 18) - 1);
    for (var i = 0; i < xStart.length; i++) {
      xDach[i] = (alpha * xStart[i]) - 132.3;
      yNorm[i] = (double) yStart[i] / yMax;
    }
    return new Pair<>(xDach, yNorm);
  }

  /**
   * Implementierung des zweiten Algorithmus zur Glättung der Daten in x.
   *
   * @param xDach das Array xDach, was aus der Umrechnung in Pikosekunden entstanden ist
   * @return das Array x
   */
  private static double[] alg2(double[] xDach) {
    int tmp = (int) Math.floor(0.002 * xDach.length);
    int n = tmp % 2 == 0 ? tmp - 1 : tmp;
    int t = (n - 1) / 2;
    double m = (double) 1 / n;
    var res = new double[xDach.length];
    for (int k = 0; k < xDach.length; k++) {
      double xk = 0;
      for (int i = 0; i < n; i++) {
        int index = k - t + i;
        if (index < 0) {
          index = 0;
        } else if (index > xDach.length - 1) {
          index = xDach.length - 1;
        }
        xk += xDach[index];
        res[k] = xk * m;
      }
    }
    return res;
  }

  /**
   * Implementierung des dritten Algorithmus zur Berechnung der oberen Einhüllenden. Da die Werte
   * von y normiert übergeben werden, muss yMax nicht mehr berechnet werden, da es als 1 angenommen
   * werden kann.
   *
   * @param yNorm das normierte y Array
   * @return das Array der oberen Einhüllenden
   */
  private static double[] alg3(double[] yNorm) {
    var yTmp = 0.0;
    var res = new double[yNorm.length];
    var k = 0;
    while (k < yNorm.length && yTmp != 1) {
      if (yTmp < yNorm[k]) yTmp = yNorm[k];
      res[k] = yTmp;
      k++;
    }
    k = yNorm.length - 1;
    yTmp = 0.0;
    while (k >= 0 && yTmp != 1) {
      if (yTmp < yNorm[k]) yTmp = yNorm[k];
      res[k] = yTmp;
      k--;
    }
    return res;
  }

  /**
   * Implementierung des vierten Algorithmus zur Berechnung der Pulsbreite und der Indizes der
   * Punkte R und L.
   *
   * @param x das berechnete x Array
   * @param yNorm das normierte Array der y Werte
   * @param oE das Array der oberen Einhüllenden
   * @return ein Paar von der Pulsbreite b und einem Paar der Werte IndexL und IndexR
   */
  private static Pair<Float, Pair<Integer, Integer>> alg4(double[] x, double[] yNorm, double[] oE) {
    int anzahlPunkte = (int) Math.ceil(yNorm.length * 0.01);
    double yGes = 0.0;
    for (var i = 0; i < anzahlPunkte; i++) {
      yGes += yNorm[i];
    }
    double grundlinie = yGes / anzahlPunkte;
    double a12 = ((1 - grundlinie) / 2) + grundlinie;
    var indexL = 0;
    var y = 0.0;
    for (var i = 0; i < yNorm.length && y < a12; i++) {
      indexL = i;
      y = oE[i];
    }
    var indexR = 0;
    y = 0.0;
    for (var i = yNorm.length - 1; i >= 0 && y < a12; i--) {
      indexR = i;
      y = oE[i];
    }
    return new Pair<>((float) (x[indexR] - x[indexL]), new Pair<>(indexL, indexR));
  }

  /**
   * Methode zur Berechnung des Maximums eines Integer-Arrays. Es wird eine foreach Schleife intern
   * genutzt, da ein Stream wahrscheinlich zu viel Overhead produzieren würde.
   *
   * @param array das Eingabe-Array
   * @return das Maximum des Eingabe-Arrays
   */
  private static int max(int[] array) {
    var max = Integer.MIN_VALUE;
    for (var j : array) if (max < j) max = j;
    return max;
  }
}
