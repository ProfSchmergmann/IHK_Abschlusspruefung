package com.cae.de.problem;

import com.cae.de.framework.Observable;
import com.cae.de.framework.Observer;
import com.cae.de.framework.ProcessRunnable;

public class ThreadB extends Observable<AutoKorrelationsFunktion>
    implements Observer<Data>, ProcessRunnable<Data, AutoKorrelationsFunktion> {

  private boolean running = false;
  private boolean processing = false;

  /**
   * Implementierung des ersten Algorithmus, welcher die xWerte in Pikosekunden umrechnet und die
   * yWerte normiert.
   *
   * @param xStart das eingelesene Array der x Werte
   * @param yStart das eingelesene Array der y Werte
   * @return ein Paar von xDach und yNorm
   */
  public Pair<double[], double[]> alg1(int[] xStart, int[] yStart) {
    var yMax = 0;
    for (int j : yStart) {
      if (yMax < j) yMax = j;
    }
    var xDach = new double[xStart.length];
    var yNorm = new double[yStart.length];
    var alpha = 266 / (Math.pow(2, 18) - 1);
    for (var i = 0; i < xStart.length; i++) {
      xDach[i] = (alpha * xStart[i]) - 132.3;
      yNorm[i] = (double) yStart[i] / yMax;
    }
    return new Pair<>(xDach, yNorm);
  }

  @Override
  public AutoKorrelationsFunktion process(Data data) {
    this.processing = true;

    this.processing = false;
    return null;
  }

  @Override
  public void run() {
    if (this.running) return;
    this.running = true;
  }

  @Override
  public void update(Data data) {
    if (!this.processing) {
      this.process(data);
    }
  }
}
