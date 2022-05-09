package com.cae.de.problem;

import com.cae.de.framework.Observable;
import com.cae.de.framework.Observer;
import com.cae.de.framework.ProcessRunnable;

public class ThreadB extends Observable<AutoKorrelationsFunktion>
    implements Observer<Data>, ProcessRunnable<Data, AutoKorrelationsFunktion> {

  private boolean running = false;
  private boolean processing = false;

  @Override
  public AutoKorrelationsFunktion process(Data data) {
    this.processing = true;
    // Processing ...
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
