package com.cae.de.problem;

import com.cae.de.framework.Observable;
import com.cae.de.framework.Observer;
import com.cae.de.framework.WriteRunnable;

import java.nio.file.Path;

public class ThreadC<T> extends Observable<T> implements Observer<T>, WriteRunnable<T> {

  private final Path pathToOutputFolder;
  private boolean running;

  public ThreadC(Path pathToOutputFolder) {
    this.pathToOutputFolder = pathToOutputFolder;
  }

  @Override
  public void run() {}

  @Override
  public void update(T t) {}

  @Override
  public boolean write(T t) {
    return false;
  }
}
