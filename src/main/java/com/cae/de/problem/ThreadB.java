package com.cae.de.problem;

import com.cae.de.framework.Observable;
import com.cae.de.framework.Observer;
import com.cae.de.framework.ProcessRunnable;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadB extends Observable<AutoKorrelationsFunktion>
    implements Observer<Data>, ProcessRunnable<Data, AutoKorrelationsFunktion> {

  private static final Logger LOGGER = Logger.getLogger(ThreadB.class.getName());
  private final AtomicBoolean processing = new AtomicBoolean(false);
  private boolean running = false;

  @Override
  public AutoKorrelationsFunktion process(Data data) {
    this.processing.set(true);
    LOGGER.log(Level.INFO, "ThreadB verarbeitet \"" + data.fileName() + "\"!");
    var akf = Algorithms.solve(data);
    this.notifyObserver(akf);
    this.processing.set(false);
    return akf;
  }

  @Override
  public void run() {
    if (this.running) return;
    this.running = true;
  }

  @Override
  public void update(Data data) {
    if (!this.processing.get()) {
      this.process(data);
    }
  }
}
