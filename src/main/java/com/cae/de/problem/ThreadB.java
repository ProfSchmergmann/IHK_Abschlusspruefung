package com.cae.de.problem;

import com.cae.de.framework.Observable;
import com.cae.de.framework.Observer;
import com.cae.de.framework.ProcessRunnable;

public class ThreadB<T> extends Observable<T> implements Observer<T>, ProcessRunnable<T> {

  private boolean running;

  @Override
  public T process(T t) {
    return null;
  }

  @Override
  public void run() {}

  @Override
  public void update(T t) {}
}
