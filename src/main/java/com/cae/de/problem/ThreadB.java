package com.cae.de.problem;

import com.cae.de.framework.Observable;
import com.cae.de.framework.Observer;
import com.cae.de.framework.ProcessRunnable;

public class ThreadB extends Observable<AutoKorrelationsFunktion>
    implements Observer<Data>, ProcessRunnable<Data, AutoKorrelationsFunktion> {

  private boolean running;

  @Override
  public AutoKorrelationsFunktion process(Data data) {
    return null;
  }

  @Override
  public void run() {}

  @Override
  public void update(Data data) {}
}
