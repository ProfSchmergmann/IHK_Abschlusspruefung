package com.cae.de.problem;

import com.cae.de.framework.Observable;
import com.cae.de.framework.Observer;
import com.cae.de.framework.ProcessRunnable;

public class ThreadB extends Observable<AutoKorrelationsFunktion>
    implements Observer<ThreadA.Data>, ProcessRunnable<ThreadA.Data, AutoKorrelationsFunktion> {

  private boolean running;

  @Override
  public AutoKorrelationsFunktion process(ThreadA.Data data) {
    return null;
  }

  @Override
  public void run() {}

  @Override
  public void update(ThreadA.Data data) {}
}
