package com.cae.de.problem;

import com.cae.de.framework.Observable;
import com.cae.de.framework.Observer;
import com.cae.de.framework.WriteRunnable;

import java.nio.file.Path;

public class ThreadC extends Observable<AutoKorrelationsFunktion>
    implements Observer<AutoKorrelationsFunktion>, WriteRunnable<AutoKorrelationsFunktion> {

  private final Path pathToOutputFolder;
  private boolean running;

  public ThreadC(Path pathToOutputFolder) {
    this.pathToOutputFolder = pathToOutputFolder;
  }

  @Override
  public void run() {}

  @Override
  public void update(AutoKorrelationsFunktion autoKorrelationsFunktion) {

  }

  @Override
  public boolean write(AutoKorrelationsFunktion autoKorrelationsFunktion) {
    return false;
  }
}
