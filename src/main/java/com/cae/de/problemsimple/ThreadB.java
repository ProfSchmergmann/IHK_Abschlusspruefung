package com.cae.de.problemsimple;

import com.cae.de.framework.Observable;
import com.cae.de.framework.Observer;
import com.cae.de.framework.ProcessRunnable;
import com.cae.de.framework.ReadRunnable;
import com.cae.de.problem.Algorithms;
import com.cae.de.problem.AutoKorrelationsFunktion;
import com.cae.de.problem.Data;
import com.cae.de.utils.Pair;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementierung eines {@link ProcessRunnable}s, {@link Observer}s und {@link Observable}s. Dieser
 * Thread kümmert sich um die Verarbeitung der Daten, welche von einem {@link ReadRunnable} und
 * {@link Observable} eingelesen und weitergegeben wurden.
 */
public class ThreadB extends Observable<Pair<AutoKorrelationsFunktion, Integer>>
    implements Observer<Pair<Data, Integer>>,
        ProcessRunnable<Pair<Data, Integer>, AutoKorrelationsFunktion> {

  private static final Logger LOGGER = Logger.getLogger(ThreadB.class.getName());
  private final AtomicBoolean running = new AtomicBoolean(false);
  private final AtomicBoolean processing = new AtomicBoolean(false);

  /**
   * Methode für die Bearbeitung eines {@link Data} Objektes.
   *
   * @param dataIntegerPair Paar aus dem Eingabeobjekt und der Gesamtzahl der Daten
   * @return eine {@link AutoKorrelationsFunktion}
   */
  @Override
  public AutoKorrelationsFunktion process(Pair<Data, Integer> dataIntegerPair) {
    LOGGER.log(
        Level.INFO,
        "ThreadB verarbeitet \""
            + dataIntegerPair.key().fileName()
            + "\" und schickt das Ergebnis an ThreadC!");
    this.notifyObservers(
        new Pair<>(Algorithms.solve(dataIntegerPair.key()), dataIntegerPair.value()));
    return null;
  }

  /** Methode, die dafür da ist, den Zustand dieses Threads zu setzen. */
  @Override
  public void run() {
    if (this.running.get()) return;
    this.running.set(true);
  }

  /**
   * Update-Methode, welche testet, ob der Prozess gerade arbeitet. Wenn ja, wird einfach
   * abgebrochen, falls nicht, wird mit dem Objekt weitergerechnet.
   *
   * @param dataIntegerPair das Paar aus einem {@link Data} Objekt und der Größe des Ordners, bzw.
   *     der Anzahl der Daten darin
   */
  @Override
  public void update(Pair<Data, Integer> dataIntegerPair) {
    if (this.processing.get()) return;
    this.processing.set(true);
    this.process(dataIntegerPair);
    this.processing.set(false);
  }
}
